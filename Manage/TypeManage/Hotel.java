package Manage.TypeManage;
import Input.Input;
import src.Room;
import src.SingleRoom;
import src.DoubleRoom;
import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Hotel {
    private Room[][] rooms;
    private Map<String, String> guestList; // Danh sách khách trọ (số phòng, tên khách)
    private Map<String, Double> revenueMap; // Map<Ngày, Tổng doanh thu>

    public Hotel(int numFloors, int roomsPerFloor) {
        rooms = new Room[numFloors][roomsPerFloor];
        guestList = new HashMap<>();
        revenueMap = new HashMap<>();
        initializeRooms();
    }
    private void initializeRooms() {
        for (int floor = 0; floor < rooms.length; floor++) {
            for (int roomNum = 1; roomNum <= rooms[floor].length; roomNum++) {
                String roomNumber = String.valueOf((floor + 1) * 100 + roomNum);
                if (roomNum % 2 == 1) {
                    rooms[floor][roomNum - 1] = new SingleRoom(roomNumber);
                } else {
                    rooms[floor][roomNum - 1] = new DoubleRoom(roomNumber);
                }
            }
        }
    }
    public Room getRoom(String roomNumber) {
        for (Room[] floor : rooms) {
            for (Room room : floor) {
                if (room.getRoomNumber().equals(roomNumber)) {
                    return room;
                }
            }
        }
        return null;
    }
    public void checkIn(String roomNumber, String guestName) {
        Room room = getRoom(roomNumber);
        if (room == null) {
            System.out.println("Phòng không tồn tại. Không thể check-in.");
            return;
        }
        if (room.isOccupied()) {
            System.out.println("Phòng đã có người ở. Không thể check-in.");
            return;
        }
        // Nhập ngày giờ nhận phòng từ người dùng
        LocalDateTime checkInTime = Input.getLocalDateTimeFromUser("Nhập ngày giờ nhận phòng (dd/MM/yyyy HH:mm): ");
        room.occupy(checkInTime);
        guestList.put(roomNumber, guestName);
        System.out.println("Check-in thành công cho phòng " + roomNumber + " vào lúc " + checkInTime);
        // ... Các xử lý khác sau khi check-in
        updateRoomStatusToFile();
        updateGuestListToFile();
    }
    public void checkOut(String roomNumber) {
        Scanner scanner = new Scanner(System.in);
        Room room = getRoom(roomNumber);
        if (room == null) {
            System.out.println("Phòng không tồn tại. Không thể check-out.");
            return;
        }
        if (!room.isOccupied()) {
            System.out.println("Phòng đang trống. Không thể check-out.");
            return;
        }
        // Nhập ngày giờ trả phòng từ người dùng
        LocalDateTime checkOutTime = Input.getLocalDateTimeFromUser("Nhập ngày giờ trả phòng (dd/MM/yyyy HH:mm): ");
        double bill = calculateBill(room.getCheckInTime(), checkOutTime, roomNumber);
        System.out.println("Tổng tiền cần thanh toán cho phòng " + roomNumber + " là $" + bill);
        // Xác nhận và thực hiện check-out
        System.out.print("Xác nhận check-out (yes/no): ");
        String confirmation = scanner.nextLine().toLowerCase();
        if (confirmation.equals("yes")) {
            room.vacate();
            guestList.remove(roomNumber);
            System.out.println("Check-out thành công cho phòng " + roomNumber + " vào lúc " + checkOutTime);
            // ... Các xử lý khác sau khi check-out
            updateRoomStatusToFile();
            updateGuestListToFile();
        } else {
            System.out.println("Check-out không được xác nhận. Quay lại menu chính.");
        }
    }

    public void changeRoom(String currentRoomNumber, String newRoomNumber) {
        Room currentRoom = getRoom(currentRoomNumber);
        Room newRoom = getRoom(newRoomNumber);

        if (currentRoom == null || newRoom == null) {
            System.out.println("Phòng không tồn tại. Không thể đổi phòng.");
            return;
        }

        if (!currentRoom.isOccupied() || newRoom.isOccupied()) {
            System.out.println("Không thể đổi phòng. Kiểm tra lại trạng thái phòng.");
            return;
        }

        // Xác nhận đổi phòng và tính tiền
        double bill = calculateChangeRoomBill(currentRoom, newRoom);
        System.out.println("Số tiền cần thanh toán khi đổi phòng: $" + bill);

        // Xác nhận đổi phòng và cập nhật thông tin
        System.out.print("Xác nhận đổi phòng (yes/no): ");
        Scanner scanner = new Scanner(System.in);
        String confirmation = scanner.nextLine().toLowerCase();

        if (confirmation.equals("yes")) {
            newRoom.occupy(currentRoom.getCheckInTime());
            currentRoom.vacate();
            // ... Xử lý quá trình đổi phòng
            updateRoomStatusToFile();
        }
    }
    private double calculateChangeRoomBill(Room currentRoom, Room newRoom) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime checkInTime = currentRoom.getCheckInTime();
        double currentRoomBill = calculateBill(checkInTime, currentTime, currentRoom.getRoomNumber());
        double newRoomRate = newRoom.getRatePerHour();
        double newRoomBill = currentRoomBill + (currentRoom.getRatePerHour() * calculateHours(checkInTime, currentTime));
        return newRoomBill;
    }
    public double calculateBill(LocalDateTime checkInTime, LocalDateTime checkOutTime, String roomNumber) {
        Room room = getRoom(roomNumber);
        double ratePerHour = room.getRatePerHour();
        double bill = ratePerHour * calculateHours(checkInTime, checkOutTime);
        // Lưu số tiền đã trả vào revenueMap
        String dateKey = checkInTime.toLocalDate().toString();
        double totalRevenue = revenueMap.getOrDefault(dateKey, 0.0);
        totalRevenue += bill;
        revenueMap.put(dateKey, totalRevenue);
        return bill;
    }
    private long calculateHours(LocalDateTime checkInTime, LocalDateTime checkOutTime) {
        Duration duration = Duration.between(checkInTime, checkOutTime);
        return duration.toHours();
    }
    public void displayRoomStatus() {
        System.out.println("Danh sách trạng thái phòng:");
        for (Room[] floor : rooms) {
            for (Room room : floor) {
                System.out.println("Phòng " + room.getRoomNumber() + ": " + (room.isOccupied() ? "Đang có khách" : "Trống"));
            }
        }
    }

    //Ghi danh sách phòng vào File
    public void updateRoomStatusToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("room_status.txt"))) {
            for (int i = 0; i < rooms.length; i++) {
                for (int j = 0; j < rooms[i].length; j++) {
                    Room room = rooms[i][j];
                    writer.write(room.getRoomNumber() + "," + (room.isOccupied() ? "occupied" : "vacant"));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi cập nhật trạng thái phòng vào tệp.");
        }
    }

    public void displayGuestList() {
        System.out.println("Danh sách khách trọ:");
        for (Map.Entry<String, String> entry : guestList.entrySet()) {
            System.out.println("Phòng " + entry.getKey() + ": " + entry.getValue());
        }
    }
    //Ghi danh sách khách trọ vào File
    public void updateGuestListToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("guest_list.txt"))) {
            for (Map.Entry<String, String> entry : guestList.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi cập nhật danh sách khách vào tệp.");
        }
    }

    public void readRoomStatusFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("room_status.txt"))) {
            String line;
            int row = 0;
            int col = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String roomNumber = parts[0];
                String status = parts[1];
                Room room = getRoom(roomNumber);
                if (room != null) {
                    room.setOccupied("occupied".equals(status));
                }
                col++;
                if (col == rooms[row].length) {
                    col = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc trạng thái phòng từ tệp.");
        }
    }
    public void readGuestListFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("guest_list.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                guestList.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc danh sách khách từ tệp.");
        }
    }
}
