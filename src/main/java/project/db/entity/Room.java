package project.db.entity;

public class Room extends Entity {

    private Integer number;
    private String roomClass;
    private Integer numberOfBeds;
    private Double cost;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(String roomClass) {
        this.roomClass = roomClass;
    }

    public Integer getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(Integer numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public String toString(){
        return "Room [number=" + number +
                ", roomClass=" + roomClass +
                ", numberOfBeds=" + numberOfBeds+
                ", cost=" + cost + "];";
    }


}
