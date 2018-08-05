package whosHome.common.models;

import whosHome.common.Identifiable;

import javax.persistence.*;

@Entity
@Table(name = "sensor_types_metadata")
public class SensorTypeMetadata implements Identifiable<Integer> {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "sensor_type_id") private int sensorTypeID;
    @Column(name = "title") private String title;
    @Column(name = "reliability") private int reliability;

    public SensorTypeMetadata() {}

    public int getSensorTypeID() {
        return sensorTypeID;
    }

    public void setSensorTypeID(int sensorTypeID) {
        this.sensorTypeID = sensorTypeID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReliability() {
        return reliability;
    }

    public void setReliability(int reliability) {
        this.reliability = reliability;
    }

    @Override
    public Integer getID() {
        return getSensorTypeID();
    }
}
