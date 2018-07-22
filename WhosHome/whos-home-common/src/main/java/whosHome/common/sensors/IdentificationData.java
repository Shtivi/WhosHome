package whosHome.common.sensors;

import whosHome.common.Identifiable;

public abstract class IdentificationData implements Identifiable<String> {
    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract int hashCode();

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
