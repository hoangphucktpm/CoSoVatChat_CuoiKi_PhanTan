package dao.Impl;

import dao.PersonDao;
import entity.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class PersonImpl extends UnicastRemoteObject implements PersonDao {

    private static final long serialVersionUID = 1L;
    private EntityManager em;

    public PersonImpl() throws RemoteException {
        em = Persistence.createEntityManagerFactory("SQLdb").createEntityManager();
    }

    // Câu a
    // Liệt kê danh sach những người đã từng bảo trì cơ sở vật chất nào đó
    // Khi biết tên  (Tìm tương đối, không phân biệt hoa thường)
    // và có chi phí > 0
    // getMaintenceWorkers(facilityName: String): List<Person>

    public List<Person> getMaintenceWorkers(String facilityName) throws RemoteException {
        return em.createQuery("SELECT p " +
                        "FROM Maintenance m " +
                        "JOIN m.person p " +
                        "JOIN m.facility f " +
                        "WHERE UPPER(f.name) LIKE UPPER(:name) " +
                        "AND m.cost > 0", Person.class)
                .setParameter("name", "%" + facilityName + "%")
                .getResultList();
    }
}
