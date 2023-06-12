'use strict';

var stompClient;

$(document).ready(function() {
    connect();

    $("#privateMessageForm").on("submit", function(event) {
        const body = $("#body").val();

        stompClient.send("/app/send-private-message",
            {},
            JSON.stringify({body: body})
        );

        body.val("");
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
   stompClient.subscribe("/user/chat/private-message", function(responseMessage) {
        var json = JSON.parse(responseMessage.body);
        console.log("MESSAGE " + json.messageContent);
   });
}