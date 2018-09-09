package whosHome.whosHomeApp.mocks;

import static org.mockito.Mockito.*;
import whosHome.whosHomeApp.dataAccess.IDevicesDao;
import whosHome.whosHomeApp.dataAccess.IPeopleDao;
import whosHome.whosHomeApp.engine.recognition.PeopleRecognitionManager;
import whosHome.whosHomeApp.models.Person;

import java.util.Date;
import java.util.Optional;

public class RecognitionManagerMock {
    public static final Person P_1 = new Person
            .Builder("Ido", "Shtivi")
            .withID("1")
            .withInsertionDate(new Date())
            .withFacebookID("777")
            .withPhoneNo("111-2223334")
            .build();

    public static final Person P_2 = new Person
            .Builder("XXX", "xxx")
            .withID("2")
            .withInsertionDate(new Date())
            .build();

    public static PeopleRecognitionManager createMock() {
        PeopleRecognitionManager mock = mock(PeopleRecognitionManager.class);

        when(mock.recognize(eq(new IdentificationDataMock("1")))).thenReturn(Optional.of(P_1));
        when(mock.recognize(eq(new IdentificationDataMock("2")))).thenReturn(Optional.of(P_2));

        return mock;
    }
}
