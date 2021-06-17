package project.db.entity;

import java.time.LocalDate;

/**
 *
 * An entity representing a user's request
 * to find a room according to his wishes
 *
 * @author V. Etenko
 *
 */

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

    public static class Builder implements BuilderInterface{
        private RequestWish requestWish;

        public Builder() {
            requestWish = new RequestWish();
        }

        public Builder withUserNick(String userNick){
            requestWish.userNick = userNick;
            return this;
        }

        public Builder withClass_id(int class_id){
            requestWish.class_id = class_id;
            return this;
        }

        public Builder withRoomClass(String roomClass){
            requestWish.roomClass = roomClass;
            return this;
        }

        public Builder withUser_id(long user_id){
            requestWish.user_id = user_id;
            return this;
        }

        public Builder withNumberOfBeds(int number_of_beds){
            requestWish.number_of_beds = number_of_beds;
            return this;
        }

        public Builder withTime_in(LocalDate timeIn){
            requestWish.time_in = timeIn;
            return this;
        }

        public Builder withTime_out(LocalDate timeOut){
            requestWish.time_out = timeOut;
            return this;
        }

        public Builder withId(Long id) {
            requestWish.setId(id);
            return this;
        }

        public RequestWish build(){
            return requestWish;
        }

    }


}
