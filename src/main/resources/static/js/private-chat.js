'use strict';

let stompClient;
let subscription;

const messageStatusIds = [];

$(document).ready(function() {
    $("#sendPrivateBtnSpinner").hide();
    connect();

    $("#privateMessageForm").on("submit", function(event) {
        event.preventDefault();
        sendPrivateMessage();
        $("#body").val("");
    });

    $(window).on('beforeunload', function() {
        subscription.unsubscribe();
        deleteAllMessageStatusById();
    });

    $(document).on('close', function() {
        subscription.unsubscribe();
        deleteAllMessageStatusById();
    });

    $(window).on('unload', function() {
        subscription.unsubscribe();
        deleteAllMessageStatusById();
    });
});



function connect() {
    const socket = new SockJS("/websocket");
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
   subscription = stompClient.subscribe("/user/chat/private-message", function(responseMessage) {
        const json = JSON.parse(responseMessage.body);
        const messageContainer = $("#messageStatus_" + json.messageStatusId);
        if (json.messageStatus === "INACTIVE") {
            messageContainer.remove();
            return;
        }

        const recipientId = $("#recipientId").val();
        if (json.senderId != recipientId) return;
        showMessage(json);
   });
}

function sendPrivateMessage() {
    const senderUsername = $("#senderUsername").val();
    const body = $("#body").val();
    const recipientId = $("#recipientId").val();

    if (recipientId.trim() === "") {
        alert("Please provide recipient id!!");
        return;
    }

    if (body.trim() === "") {
        alert("Please provide your message!!");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/private-chat",
        contentType: "application/json",
        data: JSON.stringify({
            senderUsername: senderUsername,
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

function deleteMessage(statusId) {
    const senderUsername = $("#senderUsername").val();
    const body = $("#messageBody_" + statusId).text();
    const recipientId = $("#recipientId").val();

    $.ajax({
        type: "DELETE",
        url: "/messageStatuses/api/" + statusId,
        contentType: "application/json",
        data: JSON.stringify({
            senderUsername: senderUsername,
            body: body,
            recipientId: recipientId
        }),
        success: function(response) {
            console.log("Message unsent successfully!");
        },
        error: function(xhr, status, error) {
            alert("Error Occurred! Cannot unsend a message", xhr.responseText);
        }
    });
}

function deleteAllMessageStatusById() {
    $.ajax({
        type: "DELETE",
        url: "/messageStatuses/api/delete-all-by-id",
        data: {
            ids: messageStatusIds.join(","),
        },
        success: function(response) {
            console.log("All Message status sent are now inactive");
        },
        error: function(xhr, status, error) {
            alert("Error Occurred! Cannot unsend list of messages", xhr.responseText);
        }
    });
}

function showMessage(responseMessage) {
        messageStatusIds.push(responseMessage.messageStatusId);
        const messageArea = $("#messageArea");
        const messageElement = $("<li>")
            .attr({
                "class": "chat-message",
                "id": "messageStatus_" + responseMessage.messageStatusId
            })
            .appendTo(messageArea);

        const senderImage = $("<img>").attr({
            "class": "rounded-circle shadow-4-strong",
            "height": "50px",
            "width": "50px",
            "src": "/img/" + responseMessage.senderPicture
        }).appendTo(messageElement);

        const usernameElement = $("<span>")
            .text(responseMessage.senderUsername)
            .appendTo(messageElement);

        const textElement = $("<p>")
            .attr("id", "messageBody_" + responseMessage.messageStatusId)
            .text(responseMessage.messageContent)
            .appendTo(messageElement);

            const removeBtn = $("<button>").attr({
                "type": "button",
                "class": "btn btn-warning"
            }).text("Remove for you").appendTo(messageElement);

            removeBtn.on("click", function(event) {
                event.preventDefault();

                $(this).parent().remove();
            });

        const senderId = $("#senderId").val();
        if (responseMessage.senderId == senderId) {
            const unsendBtn = $("<button>").attr({
                "type": "button",
                "class": "btn btn-danger ms-5"
            }).text("Unsend").appendTo(messageElement);

            unsendBtn.on("click", function(event) {
                event.preventDefault();

                $(this).parent().remove();
                deleteMessage(responseMessage.messageStatusId);
            });
        }
}