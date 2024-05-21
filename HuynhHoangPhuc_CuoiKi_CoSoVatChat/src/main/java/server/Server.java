package server;


import dao.FacilityDao;
import dao.Impl.FacilityImpl;
import dao.Impl.PersonImpl;
import dao.PersonDao;
import entity.Facility;
import entity.Person;
import entity.Status;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;


public class Server {
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(6541)) {
            System.out.println("Server is running...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                System.out.println("Client IP: " + socket.getInetAddress().getHostAddress());
                Thread t = new Thread(new ClientHandler(socket));
                t.start();
            }
        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }
}
class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) throws RemoteException {
        this.socket = socket;

    }

    @Override
    public void run() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            PersonDao personDao = new PersonImpl();
            FacilityDao facilityDao = new FacilityImpl();
            int choice = 0;
            while (true)
            {
                try {
                    choice = dis.readInt();
                } catch (SocketException | EOFException e) {
                    System.out.println("Client disconnected");
                    break;
                }
                switch (choice)
                {
                    case 1:
                        System.out.println("Cau 1: Liệt kê danh sach những người đã từng bảo trì cơ sở vật chất nào đó"
                        + "Khi biết tên  (Tìm tương đối, không phân biệt hoa thường)");
                        String facilityName = dis.readUTF();
                        List<Person> persons = personDao.getMaintenceWorkers(facilityName);
                        oos.writeObject(persons);
                        oos.flush();
                        break;

                        case 2:
                            System.out.println("Cau 2: Cập nhật thông tin của một cơ sở vật chất");
                            boolean result = false;
                            String id = dis.readUTF();
                            String description = dis.readUTF();
                            double price = dis.readDouble();
                            String statusStr = dis.readUTF();
                            String location = dis.readUTF();

                            Status status = Status.valueOf(statusStr);


                            Facility facility = new Facility(id);
                            facility.setDescription(description);
                            facility.setPrice(price);
                            facility.setStatus(status);
                            facility.setLocation(location);

                            result = facilityDao.updateFacility(facility);

                            oos.writeBoolean(result);
                            oos.flush();
                            break;

                    case 3:
                        System.out.println("Cau 3: Thống kê lượt bảo trì tren từng cơ sở vật chất");
                        Map<Facility, Long> result3 = facilityDao.countMaintenance();
                        oos.writeObject(result3);
                        oos.flush();
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Client exception " + e.getMessage());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}