package sensorserver.server;

import sensorserver.engine.events.AbstractEventArgs;

import java.net.InetSocketAddress;

public class MessageReceivedEventArgs extends AbstractEventArgs {
    private String _message;
    private InetSocketAddress _address;

    public MessageReceivedEventArgs(InetSocketAddress address, String message) {
        super();
        _message = message;
        _address = address;
    }

    public String getMessage() {
        return _message;
    }

    public InetSocketAddress getAddress() {
        return _address;
    }
}
