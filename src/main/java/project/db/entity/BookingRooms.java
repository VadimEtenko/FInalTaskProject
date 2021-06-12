package project.db.entity;

import project.db.Fields;

public class BookingRooms extends Entity{

    private int roomNumber;
    private String userLogin;
    private String status;

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

    @Override
    public String toString() {
        return "BookingRooms[" +
                "roomNumber=" + roomNumber +
                ", userLogin='" + userLogin + '\'' +
                ", status='" + status + '\'' +
                ']';
    }
}
