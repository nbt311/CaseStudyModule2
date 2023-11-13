package src;

public class DoubleRoom extends Room {
    public DoubleRoom(String roomNumber) {
        super(roomNumber);
    }

    @Override
    public double getRatePerHour() {
        return 2.0;     // Giá phòng đôi 2$ 1 giờ
    }
}
