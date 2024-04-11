"use strict";

const formJoin = document.querySelector("#joinForm");
const btnJoin = document.querySelector("#join");
const inputUserName = document.querySelector("#username");
const currentUser = document.querySelector(".current-user");

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

    loadOnlineUsers().then();

    stompClient.send(
        "/app/user.addUser",
        {},
        JSON.stringify({nickName, fullName: "EMPTY", status: "ONLINE"})
    );
}

function onTopicReceived(payload)
{
    const user = JSON.parse(payload.body);

    if (user.nickName === nickName) return;

    let listOfNickNames = [...document.querySelectorAll(".nickName")];
    // checking if there's not already a user with this nickName
    // .filter()
    if (listOfNickNames.some(p => p.innerText === user.nickName)) return;

    const userDiv = document.createElement("div");
    userDiv.classList.add("user");
    userDiv.innerHTML = `
            <div class="user-profile">
                <img class="profile-img" src="images/profile.png">
                <p class="nickName">${user.nickName}</p>
                <p class="status">${user.status}</p>
            </div>
        `;
    userDiv.addEventListener("click", () => loadChat(user.nickName), true);
    usersContainer.appendChild(userDiv);
}

function onDisconnected(payload)
{
    const disconnectedUser = JSON.parse(payload.body);
}

function onError()
{
    console.log("Could not connect to socket.");
}

async function loadOnlineUsers()
{
    const response = await fetch("/all-users");
    const users = await response.json();

    for (let user of users) {
        if (user.nickName === nickName) continue;

        const userDiv = document.createElement("div");
        userDiv.classList.add("user");
        userDiv.innerHTML = `
            <div class="user-profile">
                <img class="profile-img" src="images/profile.png">
                <p class="nickName">${user.nickName}</p>
                <p class="status">${user.status}</p>
            </div>
        `;
        userDiv.addEventListener("click", () => loadChat(user.nickName), true);
        usersContainer.appendChild(userDiv);
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
    if (message.senderId === recipientName)
        appendMessage(message);
}

btnSendMessage.addEventListener("click", sendMessage, true);

function sendMessage(event)
{
    const content = inputMessage.value.trim();

    stompClient.send(
        "/app/chat",
        {},
        JSON.stringify({senderId: nickName, recipientId: recipientName, content})
    );

    inputMessage.value = "";
    const message = {senderId: nickName, content};
    appendMessage(message);
}