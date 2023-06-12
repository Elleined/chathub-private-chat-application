'use strict';

var stompClient;

$(document).ready(function() {
    connect();

    $("#privateMessageForm").on("submit", function(event) {
        const privateMessageSection = $("#privateMessageSection");
        const body = $("#body").val();
        const recipientId = $("#recipient").val();

        sendPrivateMessage(recipientId, body);

        privateMessageSection.append("<li>" +  body + "</li>");
        $("#body").val("");
        $("#recipient").val("");
        event.preventDefault();
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
}

function onError() {
    console.log("Could not connect to WebSocket server. Please refresh this page to try again!");
}

function connectToUser() {
   const privateMessageSection = $("#privateMessageSection");
   stompClient.subscribe("/user/chat/private-message", function(responseMessage) {
        var json = JSON.parse(responseMessage.body);
        privateMessageSection.append("<li>" +  json.messageContent + "</li>");
   });
}

function sendPrivateMessage(recipientId, body) {
    $.ajax({
        type: "POST",
        url: "/send-private-message/" + recipientId,
        contentType: "application/json",
        data: JSON.stringify({
            body: body
        }),
        success: function(responseMessage, response) {
            console.log("Success " + responseMessage.messageContent);
        },
        error: function(xhr, status, error) {
            alert("Error Occurred!" + xhr.responseText);
        }
    });
}