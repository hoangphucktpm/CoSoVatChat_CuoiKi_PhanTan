package dao;

import entity.Person;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PersonDao extends Remote {
    List<Person> getMaintenceWorkers(String facilityName) throws RemoteException    ;
}
