package ljh.factory;

import ljh.bean.AirPlane;

/*实例工厂*/
public class AirPlaneIntanceFactory {
    //要先new  AirPlaneIntanceFactory().getAirPlane
    public AirPlane getAirPlane(String captain){
        System.out.println("AirPlaneIntanceFactory 正在造飞机");
        AirPlane airPlane = new AirPlane();
        airPlane.setEngine("太行");
        airPlane.setAirfoil("airfoil");
        airPlane.setCaptain("captain");
        airPlane.setCarryingCapacity(200);
        return airPlane;
    }
}
