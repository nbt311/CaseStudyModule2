package src;

public class SingleRoom extends Room {

    public SingleRoom(String roomNumber) {
        super(roomNumber);
    }

    @Override
    public double getRatePerHour() {
        return 1.0;         // Giá phòng đơn 1$ mỗi giờ
    }
}
