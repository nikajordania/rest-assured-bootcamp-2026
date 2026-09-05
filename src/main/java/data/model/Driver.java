package data.model;

public class Driver {
    private String driverId;
    private int permanentNumber;

    public Driver(String driverId, int permanentNumber) {
        this.driverId = driverId;
        this.permanentNumber = permanentNumber;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public int getPermanentNumber() {
        return permanentNumber;
    }

    public void setPermanentNumber(int permanentNumber) {
        this.permanentNumber = permanentNumber;
    }
}
