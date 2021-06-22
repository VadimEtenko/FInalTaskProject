package project.db.entity;

import java.time.LocalDate;

public class Offer extends Entity {

    private long roomId;
    private long userId;
    private LocalDate time_in;
    private LocalDate time_out;

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public static class Builder implements BuilderInterface {
        private Offer offer;

        public Builder() {
            offer = new Offer();
        }

        public Builder withRoomId(long roomId) {
            offer.roomId = roomId;
            return this;
        }

        public Builder withUserId(long userId) {
            offer.userId = userId;
            return this;
        }

        public Builder withTimeIn(LocalDate time_in) {
            offer.time_in = time_in;
            return this;
        }

        public Builder withTimeOut(LocalDate time_out) {
            offer.time_out = time_out;
            return this;
        }

        public Builder withId(Long id) {
            offer.setId(id);
            return this;
        }

        public Offer build(){
            return offer;
        }
    }
}
