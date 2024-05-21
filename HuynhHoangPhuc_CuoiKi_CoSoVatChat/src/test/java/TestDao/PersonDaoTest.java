package TestDao;


import dao.Impl.PersonImpl;
import dao.PersonDao;
import org.junit.jupiter.api.*;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonDaoTest {

    private PersonDao personDao;

    @BeforeAll
    void setUp() throws RemoteException {
        personDao = new PersonImpl();
    }

    // Câu a
    @Test
    void getMaintenceWorkers2() throws RemoteException {
        String facilityName = "Air Conditioner H91 - 1";
        assertEquals(3, personDao.getMaintenceWorkers(facilityName).size());
    }



    @AfterAll
    void tearDown() throws RemoteException {
        personDao = null;
    }
}