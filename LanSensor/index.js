var WebSocket = require("ws");
var arpMonitor = require("arp-monitor");

// Start web-socket service
var wss = new WebSocket.Server({ port: 5010 });

wss.on("connection", (socket) => {
    console.log("connection");

    var msg = {
        "eventType": "IN",
        "mac": "94-e9-79-67-68-54",
        "ip": "172.20.10.7"
    }

    setInterval(() => socket.send(JSON.stringify(msg)), 3000);
})