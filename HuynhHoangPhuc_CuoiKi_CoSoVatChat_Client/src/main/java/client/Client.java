package client;




import entity.Facility;
import entity.Person;
import entity.Status;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost", 6541);
            Scanner scanner = new Scanner(System.in);
        ){
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server");
            int choice =0;
            while (true)
            {
                System.out.println("1.Liệt kê danh sach những người đã từng bảo trì cơ sở vật chất nào đó, tìm tương đối theo tên cơ sở vật chất");
                System.out.println("2. Cập nhat thong tin của một cơ sở vật chất");
                System.out.println("3. Thong ke lượt bảo trì của từng cơ sở vật chất, kết quả sắp xếp theo tên cơ sở vật chất");
                System.out.println("4. Thoát");
                System.out.println("Nhập lựa chọn của bạn: ");
                choice = scanner.nextInt();
                dos.writeInt(choice);
                dos.flush();
                switch (choice)
                {
                    case 1:
                        scanner.nextLine();
                        System.out.println("Nhập tên cơ sở vật chất: ");
                        String facilityName = scanner.nextLine();
                        dos.writeUTF(facilityName);
                        dos.flush();
                        List< Person> persons = (List<Person>) ois.readObject();
                        persons.forEach(System.out::println);
                        break;

                    case 2:
                        scanner.nextLine();
                        System.out.println("Nhập id của cơ sở vật chất:");
                        String facilityID = scanner.nextLine().trim();

                        if (facilityID.isEmpty()) {
                            System.out.println("Id không được để trống");
                            break;
                        }

                        System.out.println("Nhập mô tả:");
                        String description = scanner.nextLine().trim();

                        if (description.isEmpty()) {
                            System.out.println("Mô tả không được để trống");
                            break;
                        }

                        System.out.println("Nhập giá:");
                        double price = scanner.nextDouble();

                        if (price < 0) {
                            System.out.println("Giá không được nhỏ hơn 0");
                            break;
                        }

                        if (price == 0) {
                            System.out.println("Giá không được bằng 0");
                            break;
                        }

                        scanner.nextLine();

                        System.out.println("Nhập trạng thái (AVAILABLE, IN_USE, UNDER_REPAIR):"); // Đảm bảo người dùng nhập đúng giá trị enum
                        String statusStr = scanner.nextLine().trim();

                        Status status;
                        try {
                            status = Status.valueOf(statusStr); // Chuyển đổi chuỗi thành giá trị enum
                        } catch (IllegalArgumentException e) {
                            System.out.println("Giá trị trạng thái không hợp lệ");
                            break;
                        }

                        System.out.println("Nhập vị trí:");
                        String location = scanner.nextLine().trim();

                        dos.writeUTF(facilityID);
                        dos.writeUTF(description);
                        dos.writeDouble(price);
                        dos.writeUTF(status.toString()); // Chuyển đổi enum thành chuỗi trước khi gửi
                        dos.writeUTF(location);
                        dos.flush();

                        boolean result = ois.readBoolean();
                        if (result) {
                            System.out.println("Cập nhật thành công");
                        } else {
                            System.out.println("Cập nhật thất bại");
                        }
                        break;


                    case 3:
                        scanner.nextLine();
                        System.out.println("Thống kê lượt bảo trì của từng cơ sở vật chất");
                        Map<Facility, Long> result3 = (Map<Facility, Long>) ois.readObject();
                        result3.forEach((k,v)-> System.out.println(k + " : " + v));
                        break;

                    case 4:
                        System.out.println("Thoát");
                        break;
                    default:
                        System.out.println("Nhập sai. Vui lòng nhập lại");
                        break;
                }


            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}