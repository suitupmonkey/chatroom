var socket,
    msg,
    receiveChannel,
    chatWindow = document.querySelector('.chat-window'),
    chatWindowMessage = document.querySelector('.chat-window-message'),
    chatThread = document.querySelector('.chat-thread');
    timeThread = document.querySelector('.chat-thread time');

if (!window.WebSocket) {
    window.WebSocket = window.MozWebSocket;
}

if (window.WebSocket) {
    socket = new WebSocket("ws://localhost:8082/websocket");
    socket.onmessage = function (event) {
        var data = JSON.parse(event.data)

        var chatNewThread = document.createElement('li'),
            chatNewMessage = document.createTextNode(data.content);

        var span = document.createElement('span');
            deliveryDate = document.createTextNode(data.sendDate);
            span.className = 'name';

        // Add message to chat thread and scroll to bottom
        chatNewThread.appendChild(chatNewMessage);

        span.appendChild(deliveryDate)
        chatNewThread.appendChild(span);
        chatThread.appendChild(chatNewThread);
        chatThread.scrollTop = chatThread.scrollHeight;

        // Clear text value
        chatWindowMessage.value = '';


        // var ta = document.getElementById('responseText');
        // ta.value = "";
        // ta.value = event.data
    };
    socket.onopen = function (event) {

    };
    socket.onclose = function (event) {

    };
}
else {
    alert("抱歉，您的浏览器不支持WebSocket协议!");
}

function send(message) {
    if (!window.WebSocket) {
        return;
    }
    if (socket.readyState == WebSocket.OPEN) {
        socket.send(message);
    }
    else {
        alert("WebSocket连接没有建立成功!");
    }
}



// On form submit, send message
chatWindow.onsubmit = function (e) {
    msg = $('#msg').val();
    send(msg);
    return false;
};

$("#chatWindow").keydown(function() {
    if (event.keyCode == "13") {//keyCode=13是回车键
        $('#msg').click();
    }
});