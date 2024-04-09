"use strict";

const btnJoin = document.querySelector("#join");
const btnLeave = document.querySelector("#leave");
const btnSendMessage = document.querySelector("#sendMessage");
const inputMessage = document.querySelector("#message");

let stompClient = null;
let sockJS = null;

btnJoin.addEventListener("click", joinChat, true);
function joinChat(event)
{
    sockJS = new SockJS("/ws");
    stompClient = Stomp.over(sockJS);
    stompClient.connect({}, onConnect, onError);
}

function onConnect()
{
    stompClient.subscribe("/topic/public", onMessageReceived);
    stompClient.send(
        "/app/chat.addUser",
        {},
        JSON.stringify({sender: "Lucasss", type: "JOIN"})
    );
}

function onMessageReceived(payload)
{
    console.log(payload.body);
}

function onError()
{
    console.log("Could not connect with WebSocket.");
}

btnSendMessage.addEventListener("click", sendMessage, true);
function sendMessage()
{
    const message = inputMessage.value.trim();
    const chatMessage = {
        content: message,
        sender: "Lucasss",
        type: "CHAT"
    }

    stompClient.send(
        "/app/chat.sendMessage",
        {},
        JSON.stringify(chatMessage)
    );
}