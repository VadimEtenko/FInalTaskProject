package project.db.entity;

import java.time.LocalDate;

public class RequestedForBooking extends Entity {
    
    private String userLogin;
    private int roomNumber;
    private long userId;
    private long roomId;
    private LocalDate time_in;
    private LocalDate time_out;

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

    public LocalDate getTime_in() {
        return time_in;
    }

    public void setTime_in(LocalDate time_in) {
        this.time_in = time_in;
    }

    public LocalDate getTime_out() {
        return time_out;
    }

    public void setTime_out(LocalDate time_out) {
        this.time_out = time_out;
    }

    @Override
    public String toString() {
        return "RequestedForBooking[" +
                "userLogin='" + userLogin + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", time_in=" + time_in +
                ", time_out=" + time_out +
                ']';
    }
}
