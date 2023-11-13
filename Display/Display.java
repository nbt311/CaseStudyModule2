package Display;

import Input.Input;
import Manage.TypeManage.Hotel;

import java.util.Scanner;

public class Display {
    public static void Start(){
        Scanner scanner = new Scanner(System.in);
        // Tạo một khách sạn với 3 tầng và mỗi tầng có 4 phòng
        Hotel hotel = new Hotel(3, 4);
        // Đọc trạng thái phòng và danh sách khách từ tệp khi chương trình bắt đầu
        hotel.readRoomStatusFromFile();
        hotel.readGuestListFromFile();
        // Hiển thị menu và thực hiện các chức năng
        int choice;
        do {
            System.out.println("======== MENU ========");
            System.out.println("1. Hiển thị danh sách phòng");
            System.out.println("2. Check-in");
            System.out.println("3. Check-out");
            System.out.println("4. Hiển thị danh sách khách trọ");
            System.out.println("5. Đổi phòng");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            choice = Input.Number();
            switch (choice) {
                case 1:
                    hotel.displayRoomList();
//                    hotel.displayRoomStatus();
                    break;
                case 2:
                    // Nhập thông tin và thực hiện check-in
                    System.out.print("Nhập số phòng (ví dụ: 101): ");
                    String roomNumberCheckIn = scanner.nextLine();
                    System.out.print("Nhập thông tin khách: ");
                    String guestNameCheckIn = Input.guestName();
                    hotel.checkIn(roomNumberCheckIn, guestNameCheckIn);
                    break;
                case 3:
                    // Nhập thông tin và thực hiện check-out
                    System.out.print("Nhập số phòng (ví dụ: 101): ");
                    String roomNumberCheckOut = scanner.nextLine();
                    hotel.checkOut(roomNumberCheckOut);
                    break;
                case 4:
                    hotel.displayGuestList();
                    break;
                case 5:
                    // Nhập thông tin và thực hiện đổi phòng
                    System.out.print("Nhập số phòng hiện tại (ví dụ: 101): ");
                    String currentRoomNumber = scanner.nextLine();
                    System.out.print("Nhập số phòng mới (ví dụ: 102): ");
                    String newRoomNumber = scanner.nextLine();
                    hotel.changeRoom(currentRoomNumber, newRoomNumber);
                    break;
                case 0:
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        } while (choice != 0);

        // Khi chương trình kết thúc, cập nhật lại trạng thái phòng và danh sách khách vào tệp
        hotel.updateRoomStatusToFile();
        hotel.updateGuestListToFile();
    }
}

