package model;


public class ControlTower {
    private String locationName;
    private GPSCoordinates location;

    public ControlTower(String airportCode, GPSCoordinates location) {
        this.locationName = airportCode;
        this.location = location;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public GPSCoordinates getLocation() {
        return location;
    }

    public void setLocation(GPSCoordinates location) {
        this.location = location;
    }
    static ControlTower tower = null;

	private ControlTower() {
	}

	public static ControlTower getControlTower() {
		if (tower == null) {
			synchronized (ControlTower.class) {
				if (tower == null) {
					tower = new ControlTower();
				}
			}
		}
		return tower;
	}

	public void update(Flight f) {

        logger logs = logger.getInstance();

        String flightNumber = f.getFlightNumber();
        String position = f.getPosition().toString();
        double distance = f.getDistanceTravelled();
        String duration = f.getCurrentDuration();
        String controlTower = f.getTowerName();
        String status = f.getStatus();

		System.out.println(" Flight: " + f.getFlightNumber() + ": " +  f.getPosition() + 
                           " Distance travelled: " + f.getDistanceTravelled() +
                           " Current Fuel Consumption: " + (int)f.getCurrentFuelConsumption() +
                           " CO2 Emissions: " + (int)f.getCurrentC02() +  
                           " Current Flight Duration: " + f.getCurrentDuration() + 
                           " Control Tower: "+ f.getTowerName() + 
                           " Next: " + f.getNext() + 
                           " STATUS: " + f.getStatus());
        
        if (f.getStatus() == "In flight"){
            logs.logOnFlight(flightNumber, position, distance, duration, controlTower, status);
        }
        else{  
                if(f.getStatus() == "Arrived"){
                    logs.logFlightLanded(flightNumber, position, distance, duration, controlTower, status);
                }
        }
	}
}
