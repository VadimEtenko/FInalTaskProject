package project.db.entity;


public enum ClassOfRooms{
    JuniorSuite,
    Suite,
    DeLuxe,
    Duplex;

    private String classType;

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

}
