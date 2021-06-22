package project.db.entity;

import project.db.Fields;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 *  Entity of records with reserved rooms
 *
 * @author V. Etenko
 *
 */

public class BookingRooms extends Entity{

    private int roomNumber;
    private String userLogin;
    private String status;
    private LocalDate time_in ;
    private LocalDate time_out;
    private long statusId;

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }


    /**
     * @param statusId
     *      status id according to the database
     */

    public void setStatus(int statusId) {
        switch (statusId){
            case Fields.BOOKED_ROOM_STATUS__FREE:
                this.status = "free";
                break;

            case Fields.BOOKED_ROOM_STATUS__BOOKED:
                this.status = "booked";
                break;

            case Fields.BOOKED_ROOM_STATUS__OCCUPIED:
                this.status = "occupied";
                break;

            default:
                this.status = "notavailable";
                break;
        }

        setStatusId(statusId);
    }

    public void setStatus(String status){
        this.status = status;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
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
        return "BookingRooms[" +
                "roomNumber=" + roomNumber +
                ", userLogin=" + userLogin+
                ", status=" + status +
                ", time_in=" + time_in +
                ", time_out=" + time_out +
                ']';
    }

    public static class Builder implements BuilderInterface{
        private BookingRooms bookingRooms;

        public Builder() {
            bookingRooms = new BookingRooms();
        }

        public Builder withRoomNumber(int roomNumber){
            bookingRooms.roomNumber = roomNumber;
            return this;
        }

        public Builder withUserLogin(String userLogin){
            bookingRooms.userLogin = userLogin;
            return this;
        }

        public Builder withStatus(int status){
            switch (status){
                case Fields.BOOKED_ROOM_STATUS__FREE:
                    bookingRooms.status = "free";
                    break;

                case Fields.BOOKED_ROOM_STATUS__BOOKED:
                    bookingRooms.status = "booked";
                    break;

                case Fields.BOOKED_ROOM_STATUS__OCCUPIED:
                    bookingRooms.status = "occupied";
                    break;

                default:
                    bookingRooms.status = "notavailable";
                    break;
            }
            bookingRooms.statusId = status;
            return this;
        }

        public Builder withTime_in(LocalDate time_in){
            bookingRooms.time_in = time_in;
            return this;
        }

        public Builder withTime_out(LocalDate time_out){
            bookingRooms.time_out = time_out;
            return this;
        }

        public Builder withId(Long id) {
            bookingRooms.setId(id);
            return this;
        }

        public BookingRooms build(){
            return bookingRooms;
        }

    }

}
