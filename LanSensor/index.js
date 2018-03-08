var WebSocket = require("ws");
var arpMonitor = require("arp-monitor");

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
        if (socket.readyState == WebSocket.OPEN) 
            socket.send(JSON.stringify(IN))
    }, 6000);

    setTimeout(() => {
        setInterval(() => {
            if (socket.readyState == WebSocket.OPEN)
                socket.send(JSON.stringify(OUT));
        }, 6000);
    }, 3000);

    socket.on("close", (code, reason) => {
        console.log("disconnection");
    })
})

