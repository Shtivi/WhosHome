package whosHome.whosHomeApp.mocks;

import whosHome.common.sensors.client.IdentificationData;

public class IdentificationDataMock extends IdentificationData {
    private String data;

    public static IdentificationDataMock of(String data) {
        return new IdentificationDataMock(data);
    }

    public IdentificationDataMock(String data) {
        this.data = data;
    }

    @Override
    public String getIdentificationData() {
        return this.data;
    }
}
