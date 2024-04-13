"use strict";

const formJoin = document.querySelector("#joinForm");
const btnJoin = document.querySelector("#join");
const inputUserName = document.querySelector("#username");
const currentUser = document.querySelector(".current-user");
const btnLogout = document.querySelector("#logOut");

const usersContainer = document.querySelector(".users");

const chatHistoryBody = document.querySelector(".body");
const chatHistoryHead = document.querySelector(".head");
const chatContainer = document.querySelector(".container");

const btnSendMessage = document.querySelector("#sendMessage");
const inputMessage = document.querySelector("#message");

let nickName = null;
let recipientName = null;
let sockJS = null;
let stompClient = null;

// scroll down when a new message is added
chatHistoryBody.addEventListener('DOMNodeInserted', scrollToBottom);

function scrollToBottom()
{
    chatHistoryBody.scrollTop = chatHistoryBody.scrollHeight;
}

btnJoin.addEventListener("click", onJoin, true);

function onJoin(event)
{
    event.preventDefault();

    nickName = inputUserName.value.replaceAll(/\s+/g, "");

    sockJS = new SockJS("/ws");
    stompClient = Stomp.over(sockJS);
    stompClient.connect({}, onConnect, onError);

    formJoin.classList.add("hidden");
    chatContainer.classList.remove("hidden");

    currentUser.querySelector(".nickName").innerText = nickName;
}

function onConnect()
{
    stompClient.subscribe(`/user/${nickName}/queue/messages`, onMessageReceived);
    stompClient.subscribe(`/user/topic/public`, onTopicReceived);

    loadAllUsers().then();

    stompClient.send(
        "/app/user.addUser",
        {},
        JSON.stringify({nickName, fullName: "EMPTY", status: "ONLINE"})
    );
}

async function onTopicReceived(payload)
{
    const user = JSON.parse(payload.body);

    if (user.nickName === nickName) return;

    let listOfNickNames = [...document.querySelectorAll(".nickName")];
    // checking if there's not already a user with this nickName
    const filterElement = listOfNickNames.filter(p => p.innerText === user.nickName);

    if (filterElement.length !== 0) {
        const nickNameElement = filterElement[0];
        const parent = nickNameElement.parentElement;
        const statusElement = parent.querySelector(".status");
        statusElement.innerText = user.status;
        statusElement.classList.add(user.status === "ONLINE" ? "ONLINE" : "OFFLINE");
        statusElement.classList.remove(user.status !== "ONLINE" ? "ONLINE" : "OFFLINE");
    }
    else {
        await createNewUserDiv(user);
    }
}

function onError()
{
    console.log("Could not connect to socket.");
}

async function createNewUserDiv(user) {
    const response = await fetch(`/last-message/${nickName}/${user.nickName}`);
    const lastMessage = await response.text();

    const userDiv = document.createElement("div");
    userDiv.classList.add("user");
    userDiv.innerHTML = `
            <div class="user-profile">
                <img class="profile-img" src="images/profile.png">
                <p class="nickName">${user.nickName}</p>
                <p class="status ${user.status}">${user.status}</p>
                <p class="last-message">${lastMessage}</p>
            </div>
        `;
    userDiv.addEventListener("click", () => loadChat(user.nickName), true);
    usersContainer.appendChild(userDiv);
}

async function loadAllUsers()
{
    const users = await fetch("/all-users")
        .then(resp => resp.json());

    for (let user of users) {
        if (user.nickName === nickName) continue;
        await createNewUserDiv(user);
    }
}

async function loadChat(recipientId)
{
    const response = await fetch(`/messages/${nickName}/${recipientId}`);
    const chatMessages = await response.json();

    chatHistoryHead.innerHTML = `
        <img class="profile-img" src="images/profile2.png">
        <p class="nickName">${recipientId}</p>
    `;

    recipientName = recipientId;
    chatHistoryBody.innerHTML = "";

    for (let message of chatMessages) {
        appendMessage(message);
    }
}

function appendMessage(message)
{
    const senderId = message.senderId;
    // const recipient = message.recipient;

    const receivedMessage = document.createElement("div");
    const messageContent = document.createElement("div");

    if (senderId !== nickName)
        receivedMessage.classList.add("message", "received");
    else
        receivedMessage.classList.add("message", "sent");

    messageContent.classList.add("message-content");
    messageContent.innerHTML = message.content;

    receivedMessage.appendChild(messageContent);
    chatHistoryBody.appendChild(receivedMessage);
}

function onMessageReceived(payload)
{
    const message = JSON.parse(payload.body);
    if (message.senderId === recipientName) {
        appendMessage(message);
    }
    const array = [...document.querySelectorAll(".nickName")]
    const filterElement = array.filter(p => p.innerText === message.senderId)[0];
    filterElement.parentElement.querySelector(".last-message").innerText = message.content;
}

btnSendMessage.addEventListener("click", sendMessage, true);

function sendMessage(event)
{
    const content = inputMessage.value.trim();
    inputMessage.value = "";

    const message = {senderId: nickName, recipientId: recipientName, content};
    stompClient.send(
        "/app/chat",
        {},
        JSON.stringify(message)
    );

    appendMessage(message);
    const array = [...document.querySelectorAll(".nickName")]
    const filterElement = array.filter(p => p.innerText === recipientName)[0];
    filterElement.parentElement.querySelector(".last-message").innerText = message.content;
}

btnLogout.addEventListener("click", onLogout, true);

function onLogout()
{
    stompClient.send("/app/user.disconnectUser",
        {},
        JSON.stringify({nickName: nickName, fullName: "EMPTY", status: "OFFLINE"})
    );
    window.location.reload();
}