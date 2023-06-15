'use strict';

let stompClient;
let notificationSubscription;

$(document).ready(function() {
    connect();

    $(window).on('beforeunload', function() {
        notificationSubscription.unsubscribe();
    });

    $(document).on('close', function() {
        notificationSubscription.unsubscribe();
    });

    $(window).on('unload', function() {
        notificationSubscription.unsubscribe();
    });
});

function connectToPrivateNotification() {
    notificationSubscription = stompClient.subscribe("/user/chat/private-notification", function(responseMessage) {
        const json = JSON.parse(responseMessage.body);
        generateNotificationBlock(json);
   });
}

function connect() {
    const socket = new SockJS("/websocket");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    console.log("Web Socket Connected!!!");
    connectToPrivateNotification();
}

function onError() {
    console.log("Could not connect to WebSocket server. Please refresh this page to try again!");
}

function generateNotificationBlock(responseMessage) {
    alert("Message count " + responseMessage.messageCount + " Notification message " + responseMessage.notificationMessage);
    const notificationContainer = $("#notificationContainer");

}