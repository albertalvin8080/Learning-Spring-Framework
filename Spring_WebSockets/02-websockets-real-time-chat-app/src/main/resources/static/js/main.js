"use strict";

const formJoin = document.querySelector("#joinForm");
const btnJoin = document.querySelector("#join");
const inputUserName = document.querySelector("#username");

// const btnLeave = document.querySelector("#leave");
const btnSendMessage = document.querySelector("#sendMessage");
const inputMessage = document.querySelector("#message");

const chatContainer = document.querySelector(".chat-container");
const chatHistory = document.querySelector(".chat-history");

let stompClient = null;
let sockJS = null;

btnJoin.addEventListener("click", joinChat, true);

function joinChat(event)
{
    event.preventDefault();
    sockJS = new SockJS("/ws");
    stompClient = Stomp.over(sockJS);
    stompClient.connect({}, onConnect, onError);
}

function onConnect()
{
    stompClient.subscribe("/topic/public", onMessageReceived);

    const username = inputUserName.value.trim();
    stompClient.send(
        "/app/chat.addUser",
        {},
        JSON.stringify({sender: username, messageType: "JOIN"})
    );

    formJoin.classList.add("hidden");
    chatContainer.classList.remove("hidden");
}

function onMessageReceived(payload)
{
    const chatMessage = JSON.parse(payload.body);

    let newMessage = document.createElement("div");

    if (chatMessage.messageType === "JOIN") {
        newMessage.innerHTML = `&gt;<strong>${chatMessage.sender}</strong>&lt;  joined the chat`;
        newMessage.classList.add("received-message");
    }
    else if (chatMessage.messageType === "LEAVE") {
        newMessage.innerHTML = `&gt;<strong>${chatMessage.sender}</strong>&lt;  left the chat`;
        newMessage.classList.add("received-message");
    }
    else if(chatMessage.messageType === "CHAT") {
        newMessage.innerHTML = `<div><strong>${chatMessage.sender}</div></strong>${chatMessage.content}`;
    }

    if(inputUserName.value.trim() === chatMessage.sender)
        newMessage.classList.add("user-message");

    chatHistory.appendChild(newMessage);
}

function onError()
{
    console.log("Could not connect with WebSocket.");
}

btnSendMessage.addEventListener("click", sendMessage, true);

function sendMessage(event)
{
    event.preventDefault();

    const username = inputUserName.value.trim();
    const message = inputMessage.value.trim();

    const chatMessage = {
        content: message,
        sender: username,
        messageType: "CHAT"
    }

    stompClient.send(
        "/app/chat.sendMessage",
        {},
        JSON.stringify(chatMessage)
    );
}