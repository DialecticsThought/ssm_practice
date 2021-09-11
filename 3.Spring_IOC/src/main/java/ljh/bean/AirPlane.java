package ljh.bean;

public class AirPlane {
    private String engine;
    private String airfoil;//机翼
    private Integer carryingCapacity;//载客量
    private String captain;//机长

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getAirfoil() {
        return airfoil;
    }

    public void setAirfoil(String airfoil) {
        this.airfoil = airfoil;
    }

    public Integer getCarryingCapacity() {
        return carryingCapacity;
    }

    public void setCarryingCapacity(Integer carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

    public String getCaptain() {
        return captain;
    }

    public void setCaptain(String captain) {
        this.captain = captain;
    }
}
