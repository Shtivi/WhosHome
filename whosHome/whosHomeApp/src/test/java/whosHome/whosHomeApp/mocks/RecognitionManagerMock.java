package whosHome.whosHomeApp.mocks;

import static org.mockito.Mockito.*;
import whosHome.whosHomeApp.dataAccess.IDevicesDao;
import whosHome.whosHomeApp.dataAccess.IPeopleDao;
import whosHome.whosHomeApp.engine.recognition.PeopleRecognitionManager;
import whosHome.whosHomeApp.models.Person;

import java.util.Date;
import java.util.Optional;

public class RecognitionManagerMock {
    public static final Person P_1 = new Person();
    public static final Person P_2 = new Person();

    static {
        P_1.setID("1");
        P_1.setFirstname("Ido");
        P_1.setLastname("Shtivi");
        P_1.setPhoneNo("054-1111111");
        P_1.setFacebookID("777");
        P_1.setInsertionDate(new Date());

        P_2.setID("2");
        P_2.setFirstname("aaa");
        P_2.setLastname("AAA");
        P_2.setInsertionDate(new Date());
    }

    public static PeopleRecognitionManager createMock() {
        PeopleRecognitionManager mock = mock(PeopleRecognitionManager.class);

        when(mock.recognize(eq(new IdentificationDataMock("1")))).thenReturn(Optional.of(P_1));
        when(mock.recognize(eq(new IdentificationDataMock("2")))).thenReturn(Optional.of(P_2));

        return mock;
    }
}
