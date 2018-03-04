var WebSocket = require("ws");
var arpMonitor = require("arp-monitor");

// Start web-socket service
var wss = new WebSocket.Server({ port: 5010 });

wss.on("connection", (socket) => {
    console.log("connection");
    setInterval(() => socket.send("Interval message"), 3000);
})