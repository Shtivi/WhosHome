package sensorserver.server;

import sensorserver.exceptions.SensorException;

import java.net.InetSocketAddress;

public class ClientNotFoundException extends SensorException {

    public ClientNotFoundException(InetSocketAddress address) {
        super(String.format("client with address '%s' does not exist", address.getAddress().toString()));
    }
}
