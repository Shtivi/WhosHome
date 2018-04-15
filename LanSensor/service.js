var WebSocket = require("ws");
var ArpMonitor = require('arp-monitor');

// Start web-socket service
console.log("Starting the sensor service...")
var wss = new WebSocket.Server({ port: 5010 }, () => {
    console.log("Sensor websocket server started at :" + wss.address().port);
});

wss.on("error", (err) => {
    console.error(err);
})

wss.on("connection", (socket, req) => {
    console.log(req.connection.remoteAddress + " connected");

    socket.on("close", (code, reason) => {
        console.log("disconnection");
    })
})

function broadcastEvent(event, clients) {
    clients.forEach((socket) => {
        if (socket.readyState == WebSocket.OPEN) {
            socket.send(JSON.stringify(event));
        }
    })
}

// Start the montor
if (process.argv.indexOf('--debug') != -1) {
    console.log("starting in debug mode...");

    var IN = {
        "eventType": "IN",
        "mac": "94-e9-79-67-68-54",
        "ip": "172.20.10.7"
    }

    var OUT = {
        "eventType": "OUT",
        "mac": "94-e9-79-67-68-54",
        "ip": "172.20.10.7"
    }

    setInterval(() => {
        broadcastEvent(wss.clients, IN);
    }, 6000);

    setTimeout(() => {
        setInterval(() => {
            broadcastEvent(wss.clients, OUT);
        }, 6000);
    }, 3000);
} else {
    console.log("Starting monitor");

    // Start the monitor
    var monitor = new ArpMonitor();

    ArpMonitor.on("in", (data) => {
        console.log("[" + new Date() + "] IN: " + data.ip + " " + data.mac);
        broadcastEvent({
            eventType: 'IN',
            mac: data.mac,
            ip: data.ip
        })
    })

    ArpMonitor.on("out", (data) => {
        console.log("[" + new Date() + "] OUT: " + data.ip + " " + data.mac);
        broadcastEvent({
            eventType: 'OUT',
            mac: data.mac,
            ip: data.ip
        })
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