package dao;

import entity.Facility;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface FacilityDao extends Remote {
    boolean updateFacility(Facility facilityNewInfo) throws RemoteException;
    Map<Facility, Long> countMaintenance() throws RemoteException;
}
