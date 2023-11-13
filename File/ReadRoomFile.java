package File;
import src.Room;

import java.io.*;

public class ReadRoomFile {
    public static Room[][] readRoomsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("rooms.dat"));
             BufferedReader reader = new BufferedReader(new FileReader("room_status.txt"))) {
            Room[][] rooms = (Room[][]) ois.readObject();
            // Đọc trạng thái phòng từ file room_status.txt
            for (int i = 0; i < rooms.length; i++) {
                for (int j = 0; j < rooms[i].length; j++) {
                    String line = reader.readLine();
                    if (line != null) {
                        String[] parts = line.split(",");
                        boolean isOccupied = parts[1].equals("occupied");
                        rooms[i][j].setOccupied(isOccupied);
                    }
                }
            }
            return rooms;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Lỗi khi đọc thông tin phòng từ tệp.");
            return null;
        }
    }
}
