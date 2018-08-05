package whosHome.common.models;

import whosHome.common.Identifiable;

import javax.persistence.*;

@Entity
@Table(name = "sensor_connections_metadata")
public class SensorConnectionMetadata implements Identifiable<Integer> {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "sensor_connection_id") private int sensorConnectionID;
    @Column(name = "url") private String url;
    @Column(name = "port") private int port;
    @Column(name = "path") private String path;
    @Column(name = "name") private String name;
    @Column(name = "is_active_defaultly") private boolean isActiveDefaultly;
    @ManyToOne(cascade = CascadeType.ALL) @JoinColumn(name = "sensor_type_id") private SensorTypeMetadata sensorTypeMetadata;

    public SensorConnectionMetadata() {
    }

    public int getSensorConnectionID() {
        return sensorConnectionID;
    }

    public void setSensorConnectionID(int sensorConnectionID) {
        this.sensorConnectionID = sensorConnectionID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActiveDefaultly() {
        return isActiveDefaultly;
    }

    public void setActiveDefaultly(boolean activeDefaultly) {
        isActiveDefaultly = activeDefaultly;
    }

    public SensorTypeMetadata getSensorTypeMetadata() {
        return sensorTypeMetadata;
    }

    public void setSensorTypeMetadata(SensorTypeMetadata sensorTypeMetadata) {
        this.sensorTypeMetadata = sensorTypeMetadata;
    }

    @Override
    public Integer getID() {
        return getSensorConnectionID();
    }
}
