package model;

public class CentralMonitor {

	static CentralMonitor monitor = null;

	// private CentralMonitor() {

	// }

	public static CentralMonitor getMonitor() {
		if (monitor == null) {
			synchronized (CentralMonitor.class) {
				if (monitor == null) {
					monitor = new CentralMonitor();
				}
			}
		}

		return monitor;
	}

	public void update(Flight f) {
		System.out.println("Flight:" + f.getName() + ": " +  f.getPosition() + "   Distance travelled: "+f.getDistanceTravelled()+"  Current Fuel Consumption; "+f.getCurrentFuelConsumption()
		+" CO2 Emissions: "+f.getCurrentFuelConsumption()+ " Current Flight Duration: "+ f.getCurrentDuration());
	}
}


