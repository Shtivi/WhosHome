package sensorserver.server;

import sensorserver.engine.events.AbstractEventArgs;

import java.net.InetSocketAddress;

public class ClientConnectionEventArgs extends AbstractEventArgs {
    private InetSocketAddress _address;

    public ClientConnectionEventArgs(InetSocketAddress address) {
        super();
        _address = address;
    }

    public InetSocketAddress getAddress() {
        return _address;
    }
}
