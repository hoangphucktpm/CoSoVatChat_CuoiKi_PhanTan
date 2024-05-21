package dao.Impl;

import dao.FacilityDao;
import entity.Facility;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FacilityImpl extends UnicastRemoteObject implements FacilityDao {

    private static final long serialVersionUID = 1L;
    private EntityManager em;

    public FacilityImpl() throws RemoteException {
        em = Persistence.createEntityManagerFactory("SQLdb").createEntityManager();
    }

    // Câu b
    // Cập nhật thông tin của một cơ sở vật chất
    // các thuộc tính có thể cập nhật: mô tả, giá, trạng thái, vị trí
    // Trong đó: Giá phải nhỏ hơn giá hiện tại và chỉ cập nhật nếu cơ sở vật chất đó tồn tại
    // Các thuộc tính khác không được phép cập nhật
    // updateFacility(facilityNewInfo: Facility): boolean

    public boolean updateFacility(Facility facilityNewInfo) throws RemoteException {
        try {
            em.getTransaction().begin();
            Facility facility = em.find(Facility.class, facilityNewInfo.getId());
            if (facility != null) {
                if (facility.getPrice() > facilityNewInfo.getPrice() && facilityNewInfo.getDescription() != null && facilityNewInfo.getLocation() != null && facilityNewInfo.getStatus() != null) {
                    facility.setDescription(facilityNewInfo.getDescription());
                    facility.setPrice(facilityNewInfo.getPrice());
                    facility.setStatus(facilityNewInfo.getStatus());
                    facility.setLocation(facilityNewInfo.getLocation());
                    em.getTransaction().commit();
                    return true;
                }
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;

    }

    // Câu c
    // Thống kê lượt bảo trì tren từng cơ sở vật chất
    // kết quả sap xep tăng dần theo tên
    // countMaintenance(): Map<Facility, Long>

    public Map<Facility, Long> countMaintenance() throws RemoteException {
        Map<Facility, Long> result = new LinkedHashMap<>();
        em.createQuery("SELECT f, COUNT(m) " +
                        "FROM Maintenance m " +
                        "JOIN m.facility f " +
                        "GROUP BY f ORDER BY f.name", Object[].class)
                .getResultList()
                .forEach(objects -> result.put((Facility) objects[0], (Long) objects[1]));
        return result;
    }
}
