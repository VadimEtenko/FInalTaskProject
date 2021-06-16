package project.db.entity;

import java.time.LocalDate;

public class RequestedForBooking extends Entity {
    
    private String userLogin;
    private int roomNumber;
    private long userId;
    private long roomId;
    private LocalDate timeIn;
    private LocalDate timeOut;

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public LocalDate getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(LocalDate timeIn) {
        this.timeIn = timeIn;
    }

    public LocalDate getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(LocalDate timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    public String toString() {
        return "RequestedForBooking[" +
                "userLogin='" + userLogin + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", time_in=" + timeIn +
                ", time_out=" + timeOut +
                ']';
    }
}
