$(function(){
    var socket ;

    window.onload = function(){
        if (!window.WebSocket){
            alert("您的浏览器不支持webSocket协议！");
        }else {
            socket = new WebSocket("ws://localhost:8080/websocket");

            socket.onmessage = function (event) {
                $("#inputId").val(event.data);
            }

            socket.onopen = function (event) {
                $("#inputId").val("打开 webSocket服务器正常！ 浏览器支持webSocket协议！");
            }

            socket.onclose = function (event) {
                $("#inputId").val("webSocket服务器关闭");
            }
        }
    }
});


function sendMsg() {
    if (!window.WebSocket){
        return;
    }
    var msg = $("#inputId").val();

    if (socket.readyState == WebSocket.OPEN){
        socket.send(msg);
    }else {
        alert("webSocket连接没有建立成功！");
    }
}