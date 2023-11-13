package File;
import src.Room;

import java.io.*;
public class RoomFileWriterUtil {
    public static void writeRoomsToFile(Room[][] rooms) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("rooms.dat"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("room_status.txt"))) {
            oos.writeObject(rooms);
            // Ghi trạng thái phòng vào file room_status.txt
            for (int i = 0; i < rooms.length; i++) {
                for (int j = 0; j < rooms[i].length; j++) {
                    Room room = rooms[i][j];
                    writer.write(room.getRoomNumber() + "," + (room.isOccupied() ? "occupied" : "vacant"));
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Lỗi khi ghi thông tin phòng vào tệp.");
        }
    }
}
