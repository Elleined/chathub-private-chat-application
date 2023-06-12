'use strict';

var stompClient;

$(document).ready(function() {
    connect();

    stompClient.subscribe("/user/chat/private-message", function(responseMessage) {
        var json = JSON.parse(responseMessage.body);
        console.log("MESSAGE " + json);
    });
});

function connect() {
    var socket = new SockJS("/websocket");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    console.log("Web Socket Connected!!!");
}

function onError() {
    console.log("Could not connect to WebSocket server. Please refresh this page to try again!");
}