package project.db.entity;

/**
 * Entity room from hotel database
 *
 * @author V. Etenko
 *
 */

public class Room extends Entity {

    private int number;
    private String roomClass;
    private int numberOfBeds;
    private double cost;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(String roomClass) {
        this.roomClass = roomClass;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString(){
        return "Room [number=" + number +
                ", roomClass=" + roomClass +
                ", numberOfBeds=" + numberOfBeds+
                ", cost=" + cost + "];";
    }

    public static class Builder implements BuilderInterface {
        private Room room;

        public Builder() {
            room = new Room();
        }

        public Builder withNumber(int number) {
            room.number = number;
            return this;
        }

        public Builder withRoomClass(String roomClass) {
            room.roomClass = roomClass;
            return this;
        }

        public Builder withNumberOfBeds(int numberOfBeds) {
            room.numberOfBeds = numberOfBeds;
            return this;
        }

        public Builder withCost(double cost) {
            room.cost = cost;
            return this;
        }

        public Builder withId(Long id) {
            room.setId(id);
            return this;
        }

        public Room build() {
            return room;
        }

    }

}
