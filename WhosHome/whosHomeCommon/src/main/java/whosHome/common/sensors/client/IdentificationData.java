package whosHome.common.sensors.client;

import whosHome.common.Identifiable;

public abstract class IdentificationData implements Identifiable<String> {
    @Override
    public boolean equals(Object other) {
        if (super.equals(other)) return true;
        if (!this.getClass().equals(other.getClass())) return false;

        IdentificationData castedOther = (IdentificationData) other;
        if (getIdentificationData().equals(((IdentificationData) other).getIdentificationData())) return  true;
        else return false;
    }

    @Override
    public int hashCode() {
        return getIdentificationData().hashCode();
    }

    @Override
    public String toString() {
        return getIdentificationData();
    }

    public abstract String getIdentificationData();

    @Override
    public String getID() {
        return getIdentificationData();
    }
}
