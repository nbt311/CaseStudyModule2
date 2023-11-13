package src;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Room implements Serializable {
    private String roomNumber;
    private boolean isOccupied;
    private LocalDateTime checkInTime;
    public Room(String roomName) {
        this.roomNumber = roomName;
        this.isOccupied = false;
        this.checkInTime = null;
    }
    public String getRoomNumber() {return roomNumber;}
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    public boolean isOccupied() {return isOccupied;}
    public void setOccupied(boolean occupied) {isOccupied = occupied;}
    public abstract double getRatePerHour();
    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }
    public void occupy(LocalDateTime checkInTime) {
        isOccupied = true;
        this.checkInTime = checkInTime;
    }
    public void vacate() {
        isOccupied = false;
        checkInTime = null;
    }
}
