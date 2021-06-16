package project.db.entity;

import project.db.Fields;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class BookingRooms extends Entity{

    private int roomNumber;
    private String userLogin;
    private String status;
    private LocalDate time_in ;
    private LocalDate time_out;

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    private long statusId;

    public String getStatus() {
        return status;
    }

    public void setStatus(int status) {

        switch (status){
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
                this.status = "not available";
                break;
        }
        setStatusId(status);
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
}
