package ljh.factory;

import ljh.bean.AirPlane;

/*静态工厂*/
public class AirPlaneStaticFactory {
    public static AirPlane getAirPlane(String captain){
        System.out.println("AirPlaneStaticFactory 正在造飞机");
        AirPlane airPlane = new AirPlane();
        airPlane.setEngine("太行");
        airPlane.setAirfoil("airfoil");
        airPlane.setCaptain("captain");
        airPlane.setCarryingCapacity(200);
        return airPlane;
    }
}
