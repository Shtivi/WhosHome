var WebSocket = require("ws");
var arpMonitor = require("arp-monitor");

// Start web-socket service
var wss = new WebSocket.Server({ port: 5001 });

wss.on("connection", (socket) => {
    console.log("Connection");
})