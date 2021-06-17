package project.db.entity;

import java.time.LocalDate;


/**
 * The entity of the user's
 * request to reserve a room
 *
 * @author V.Etenko
 *
 */

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

    public static class Builder implements BuilderInterface{
        private RequestedForBooking requestedForBooking;

        public Builder() {
            requestedForBooking = new RequestedForBooking();
        }

        public Builder withUserLogin(String userLogin){
            requestedForBooking.userLogin = userLogin;
            return this;
        }

        public Builder withRoomNumber(int roomNumber){
            requestedForBooking.roomNumber = roomNumber;
            return this;
        }

        public Builder withUserId(long userId){
            requestedForBooking.userId = userId;
            return this;
        }

        public Builder withRoomId(long roomId){
            requestedForBooking.roomId = roomId;
            return this;
        }

        public Builder withTime_in(LocalDate timeIn){
            requestedForBooking.timeIn = timeIn;
            return this;
        }

        public Builder withTime_out(LocalDate timeOut){
            requestedForBooking.timeOut = timeOut;
            return this;
        }

        public Builder withId(Long id) {
            requestedForBooking.setId(id);
            return this;
        }

        public RequestedForBooking build(){
            return requestedForBooking;
        }

    }
}
