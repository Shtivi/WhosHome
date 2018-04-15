var WebSocket = require("ws");
var ArpMonitor = require('arp-monitor');

// Start web-socket service
console.log("Starting the sensor service...")

var wss = new WebSocket.Server({ port: 5010 }, () => {
    console.log("Sensor websocket server started at :" + wss.address().port);
});

wss.broadcast = (data) => {
    wss.clients.forEach((socket) => {
        if (socket.readyState == WebSocket.OPEN) {
            socket.send(JSON.stringify(data));
        }
    })
}

wss.on("error", (err) => {
    console.error(err);
})

wss.on("connection", (socket, req) => {
    console.log(req.connection.remoteAddress + " connected");

    socket.on("close", (code, reason) => {
        console.log("disconnection");
    })
})

function broadcastEvent(eventType, eventData) {
    eventData.eventType = eventType.toUpperCase();

    wss.broadcast(eventData);
}

// Start the montor
if (process.argv.indexOf('--debug') != -1) {
    console.log("starting in debug mode...");

    var data = {
        "mac": "94-e9-79-67-68-54",
        "ip": "172.20.10.7"
    }

    setInterval(() => {
        broadcastEvent('in', data);
    }, 6000);

    setTimeout(() => {
        setInterval(() => {
            broadcastEvent('out', data);
        }, 6000);
    }, 3000);
} else {
    console.log("Starting monitor");

    // Start the monitor
    var monitor = new ArpMonitor();

    monitor.on("in", (data) => {
        console.log("[" + new Date() + "] IN: " + data.ip + " " + data.mac);
        broadcastEvent('in', data);
    })

    monitor.on("out", (data) => {
        console.log("[" + new Date() + "] OUT: " + data.ip + " " + data.mac);
        broadcastEvent('out', data);
    })
}

// Catch program exit
function cleanUp() {
    // Close connections
    wss.clients.forEach(function each(client) {
        if (client.readyState === WebSocket.OPEN) {
          client.terminate();
        }
    });
}

process.on('SIGINT', () => {
    console.log("Disconnecting all clients before exiting...");
    cleanUp();    
    process.exit(0);
})