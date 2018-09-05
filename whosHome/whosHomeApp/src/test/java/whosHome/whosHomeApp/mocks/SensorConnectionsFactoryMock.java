package whosHome.whosHomeApp.mocks;

import whosHome.common.models.SensorConnectionMetadata;
import whosHome.common.models.SensorTypeMetadata;
import whosHome.common.sensors.client.ISensorConnection;
import whosHome.whosHomeApp.engine.sensors.ISensorConnectionsFactory;
import whosHome.whosHomeApp.engine.sensors.builders.SensorConnectionInstatiationException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SensorConnectionsFactoryMock implements ISensorConnectionsFactory {
    public static final SensorTypeMetadata Type_1 = new SensorTypeMetadata();
    public static final SensorTypeMetadata Type_2 = new SensorTypeMetadata();
    public static final SensorConnectionMetadata Conn_1_1 = new SensorConnectionMetadata();
    public static final SensorConnectionMetadata Conn_1_2 = new SensorConnectionMetadata();
    public static final SensorConnectionMetadata Conn_2_1 = new SensorConnectionMetadata();

    public static Map<Integer, SensorConnectionMetadata> connectionsMetadata;

    static {
        Type_1.setSensorTypeID(1);
        Type_1.setTitle("ST-1");
        Type_1.setReliability(7);

        Type_2.setSensorTypeID(2);
        Type_2.setTitle("ST-2");
        Type_2.setReliability(3);

        Conn_1_1.setSensorConnectionID(1);
        Conn_1_1.setSensorTypeMetadata(Type_1);
        Conn_1_1.setUrl("ws://xxx");
        Conn_1_1.setPort(3030);
        Conn_1_1.setPath("/abc");
        Conn_1_1.setName("Mocked_1");
        Conn_1_1.setActiveDefaultly(true);

        Conn_1_2.setSensorConnectionID(2);
        Conn_1_2.setSensorTypeMetadata(Type_1);
        Conn_1_2.setUrl("ws://yyy");
        Conn_1_2.setPort(6060);
        Conn_1_2.setPath("/cba");
        Conn_1_2.setName("Mocked_2");
        Conn_1_2.setActiveDefaultly(false);

        Conn_2_1.setSensorConnectionID(3);
        Conn_2_1.setSensorTypeMetadata(Type_2);
        Conn_1_2.setUrl("ws://zzz");
        Conn_1_2.setPort(9090);
        Conn_1_2.setPath("/xxx");
        Conn_2_1.setActiveDefaultly(true);
        Conn_2_1.setName("Mocked_3");

         connectionsMetadata = Stream
                .of(Conn_1_1, Conn_1_2, Conn_2_1)
                .collect(Collectors.toMap(SensorConnectionMetadata::getID, o->o));
    }

    public Map<Integer, ISensorConnection> connections;

    public SensorConnectionsFactoryMock() {
        connections = connectionsMetadata
                .values()
                .stream()
                .collect(Collectors.toMap(SensorConnectionMetadata::getID, conn -> new SensorConnectionMock(conn)));
    }

    @Override
    public ISensorConnection createConnection(int sensorConnectionID) throws SensorConnectionInstatiationException {
        if (connections.containsKey(sensorConnectionID)) {
            return connections.get(sensorConnectionID);
        }
        throw new SensorConnectionInstatiationException("Could not find a data record for connection with id " + sensorConnectionID);
    }

    @Override
    public List<ISensorConnection> createAllConnections() throws SensorConnectionInstatiationException {
        return new ArrayList<ISensorConnection>(connections.values());
    }
}
