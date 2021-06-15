package project.db.entity;

import project.db.entity.Entity;

public class RequestedForBooking extends Entity {
    
    private String userLogin;
    private int roomNumber;
    private long userId;
    private long roomId;

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

    @Override
    public String toString() {
        return "RequestedForBooking[" +
                "userLogin='" + userLogin + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ']';
    }
}
