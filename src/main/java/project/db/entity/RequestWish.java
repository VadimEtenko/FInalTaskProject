package project.db.entity;

import java.time.LocalDate;

public class RequestWish extends Entity{

    private long class_id;
    private String roomClass;
    private long user_id;
    private String userNick;
    private int number_of_beds;
    private LocalDate time_in;
    private LocalDate time_out;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getClass_id() {
        return class_id;
    }

    public void setClass_id(long class_id) {
        this.class_id = class_id;
        setRoomClass(ClassOfRooms.values()[(int)class_id-1].name());
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(String roomClass) {
        this.roomClass = roomClass;
    }

    public int getNumber_of_beds() {
        return number_of_beds;
    }

    public void setNumber_of_beds(int number_of_beds) {
        this.number_of_beds = number_of_beds;
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
        return "RequestWish[{]" +
                "class_id=" + class_id +
                ", roomClass='" + roomClass + '\'' +
                ", number_of_beds=" + number_of_beds +
                ", time_in=" + time_in +
                ", time_out=" + time_out +
                ']';
    }


}
