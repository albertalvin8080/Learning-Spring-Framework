"use strict";

const formJoin = document.querySelector("#joinForm");
const btnJoin = document.querySelector("#join");
const inputUserName = document.querySelector("#username");
const currentUser = document.querySelector(".current-user");

const usersContainer = document.querySelector(".users");

const chatHistoryBody = document.querySelector(".body");
const chatContainer = document.querySelector(".container");

const btnSendMessage = document.querySelector("#sendMessage");
const inputMessage = document.querySelector("#message");

let nickName = null;
let recipientName = null;
let sockJS = null;
let stompClient = null;

btnJoin.addEventListener("click", onJoin, true);

function onJoin(event)
{
    event.preventDefault();

    nickName = inputUserName.value.trim();

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
    stompClient.subscribe(`/topic/public`, onTopicReceived);

    loadOnlineUsers().then();

    stompClient.send(
        "/app/user.addUser",
        {},
        JSON.stringify({nickName, fullName: "EMPTY", status: "ONLINE"})
    );
}

function onTopicReceived(payload) {
    const user = JSON.parse(payload.body);

    if(user.status === "OFFLINE" || user.nickName === nickName) return;

    const userDiv = document.createElement("div");
    userDiv.classList.add("user");
    userDiv.innerHTML = `
            <div class="user-profile">
                <img class="profile-img" src="images/profile.png">
                <p class="nickName">${user.nickName}</p>
                <p class="last-message">I want to go home...</p>
            </div>
        `;
    userDiv.addEventListener("click", () => loadChat(user.nickName), true);
    usersContainer.appendChild(userDiv);
}

function onDisconnected(payload) {
    const disconnectedUser = JSON.parse(payload.body);
}

function onError()
{
    console.log("Could not connect to socket.");
}

async function loadOnlineUsers()
{
    const response = await fetch("/online-users");
    const users = await response.json();

    for (let user of users) {
        if(user.nickName === nickName) continue;

        const userDiv = document.createElement("div");
        userDiv.classList.add("user");
        userDiv.innerHTML = `
            <div class="user-profile">
                <img class="profile-img" src="images/profile.png">
                <p class="nickName">${user.nickName}</p>
                <p class="last-message">I want to go home...</p>
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

    recipientName = recipientId;
    chatHistoryBody.innerHTML = "";

    for (let message of chatMessages) {
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
}

function onMessageReceived(payload)
{
    const chatMessage = JSON.parse(payload.body);

    const receivedMessage = document.createElement("div");
    receivedMessage.classList.add("message", "received");
    const messageContent = document.createElement("div");
    messageContent.classList.add("message-content");
    messageContent.innerText = chatMessage.content;

    chatHistoryBody.appendChild(receivedMessage);
}

btnSendMessage.addEventListener("click", sendMessage, true);

function sendMessage(event)
{
    const content = inputMessage.value.trim();
    const element = document.querySelector(".user-profile");

    stompClient.send(
        "/app/chat",
        {},
        JSON.stringify({senderId: nickName, recipientId: recipientName, content})
    );
}