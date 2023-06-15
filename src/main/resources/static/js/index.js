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

        const notificationItem = $("#notificationItem_" + json.senderId);
        if (notificationItem.length) {
            const messageCount = $("#messageCount_" + json.senderId);
            const newMessageCount = parseInt(messageCount.text()) + 1;
            messageCount.text(newMessageCount + "+");
            return;
        }
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
    const notificationContainer = $("#notificationContainer");


    const notificationItem = $("<li>")
        .attr({
            "class": "d-inline-flex position-relative ms-2 dropdown-item",
            "id": "notificationItem_" + responseMessage.senderId
        })
        .appendTo(notificationContainer);

    const messageCount = $("<span>")
        .attr({
            "class": "position-absolute top-0 start-100 translate-middle p-1 bg-success border border-light rounded-circle",
            "id": "messageCount_" + responseMessage.senderId
        })
        .text(responseMessage.messageCount + "+")
        .appendTo(notificationItem);

    const senderImage = $("<img>").attr({
        "class": "rounded-4 shadow-4",
        "src": "/img/" + responseMessage.senderPicture,
        "style": "width: 50px; height: 50px;"
        }).appendTo(notificationItem);

    const notificationLink = $("<a>")
        .attr("href", "/private-chat/" + responseMessage.senderId)
        .appendTo(notificationItem);

    const notificationMessage = $("<p>")
        .attr("class", "lead mt-2 ms-2 me-2")
        .text(responseMessage.notificationMessage)
        .appendTo(notificationLink);

    const br = $("<br>").appendTo(notificationContainer);
}