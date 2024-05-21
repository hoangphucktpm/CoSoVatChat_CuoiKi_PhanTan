package TestDao;


import dao.FacilityDao;
import dao.Impl.FacilityImpl;
import entity.Facility;
import entity.Status;
import org.junit.jupiter.api.*;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FacilityDaoTest {

    private FacilityDao facilityDao;

    @BeforeAll
    void setUp() throws RemoteException {
        facilityDao = new FacilityImpl();
    }

    // Câu b
    @Test
    void updateFacility() throws RemoteException {
        Facility facility = new Facility("H91Ai01");
        facility.setName("Air Conditioner H91 - 1");
        facility.setDescription("Air Conditioner H91 - 1");
        facility.setPrice(400);
        facility.setStatus(Status.AVAILABLE);
        facility.setLocation("H91");
        assertTrue(facilityDao.updateFacility(facility));
    }



    // Giá lớn hơn giá hiện tại: false
    @Test
    void updateFacility2() throws RemoteException {
        Facility facility = new Facility("H91Ai01");
        facility.setName("Air Conditioner H91 - 1");
        facility.setDescription("Air Conditioner H91 - 1");
        facility.setPrice(500);
        facility.setStatus(Status.AVAILABLE);
        facility.setLocation("H91");
        assertFalse(facilityDao.updateFacility(facility));
    }


    // Câu c
    @Test
    void countMaintenance() throws RemoteException {
        assertEquals(17, facilityDao.countMaintenance().size());
    }

    @Test
    void countMaintenance2() throws RemoteException {
        assertNotNull(facilityDao.countMaintenance().size());
    }


    @AfterAll
    void tearDown() throws RemoteException {
        facilityDao = null;
    }
}