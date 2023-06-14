'use strict';

var stompClient;

const colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

$(document).ready(function() {
    $("#sendPrivateBtnSpinner").hide();
    connect();

    $("#privateMessageForm").on("submit", function(event) {
        event.preventDefault();
        sendPrivateMessage();
        $("#body").val("");
    });
});

function connect() {
    var socket = new SockJS("/websocket");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    console.log("Web Socket Connected!!!");
    connectToUser();
    $(".connecting").text("Connected...");
}

function onError() {
    console.log("Could not connect to WebSocket server. Please refresh this page to try again!");
}

function connectToUser() {
   stompClient.subscribe("/user/chat/private-message", function(responseMessage) {
        var json = JSON.parse(responseMessage.body);
        showMessage(json);
   });
}

function sendPrivateMessage() {
    const sender = $("#sender").val();
    const body = $("#body").val();
    const recipientId = $("#recipientId").val();

    if (recipientId.trim() === "") {
        alert("Please provide recipient id!!");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/private-chat",
        contentType: "application/json",
        data: JSON.stringify({
            sender: sender,
            body: body,
            recipientId: recipientId
        }),
        beforeSend: function() {
            $("#sendPrivateBtn").hide();
            $("#sendPrivateBtnSpinner").show();
        },
        complete: function() {
            $("#sendPrivateBtn").show();
            $("#sendPrivateBtnSpinner").hide();
        },
        success: function(responseMessage, response) {
            console.log("Private message sent successfully  " + responseMessage.messageContent);
            showMessage(responseMessage);
        },
        error: function(xhr, status, error) {
            alert("Error Occurred! " + xhr.responseText);
        }
    });
}

function showMessage(responseMessage) {
        const messageArea = $("#messageArea");
        const messageElement = $("<li>")
            .attr("class", "chat-message")
            .appendTo(messageArea);

        const senderImage = $("<img>").attr({
            "class": "rounded-circle shadow-4-strong",
            "height": "50px",
            "width": "50px",
            "src": "/img/" + responseMessage.senderPicture
        }).appendTo(messageElement);

        const usernameElement = $("<span>")
            .text(responseMessage.sender)
            .appendTo(messageElement);

        const textElement = $("<p>")
            .text(responseMessage.messageContent)
            .appendTo(messageElement);
}