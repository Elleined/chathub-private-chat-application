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

        const body = $("#body").val();
        const recipientId = $("#recipient").val();
        sendPrivateMessage(recipientId, body);

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
   const messageArea = $("#messageArea");
   stompClient.subscribe("/user/chat/private-message", function(responseMessage) {
        var json = JSON.parse(responseMessage.body);
        showMessage("Denielle", json.messageContent);
   });
}

function sendPrivateMessage(recipientId, body) {
    if (recipientId.trim() === "") {
        alert("Please provide recipient id!!");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/private-chat/send-private-message/" + recipientId,
        contentType: "application/json",
        data: JSON.stringify({
            body: body
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
            showMessage("Denielle", responseMessage.messageContent);
        },
        error: function(xhr, status, error) {
            alert("Error Occurred! " + xhr.responseText);
        }
    });
}

function showMessage(sender, body) {
        const messageArea = $("#messageArea");
        const messageElement = $("<li>")
            .attr("class", "chat-message")
            .appendTo(messageArea);

        const avatarElement = $("<i>")
            .text(sender[0])
            .css("background-color", getAvatarColor(sender))
            .appendTo(messageElement);

        const usernameElement = $("<span>")
            .text(sender)
            .appendTo(messageElement);

        const textElement = $("<p>")
            .text(body)
            .appendTo(messageElement);
}

function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}