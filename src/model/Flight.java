package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TimerTask;
import java.util.ResourceBundle.Control;

import view.FlightGUI;

public class Flight extends Thread{
    private String flightNumber;
    private Airline airline;
    private Aeroplane aeroplane;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private String departureDate;
    private String departureTime;
    private FlightPlan flightPlan;
    

    public Flight(String flightNumber, Airline airline, Aeroplane aeroplane, Airport departureAirport, Airport arrivalAirport, String departureDate, String departureTime, FlightPlan flightPlan) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.aeroplane = aeroplane;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.flightPlan = flightPlan;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Airline getAirline() {
        return airline;
    }

    public Aeroplane getAeroplane() {
        return aeroplane;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public FlightPlan getFlightPlan() {
        return flightPlan;
    }

    public int getTotalFuelConsumption() {
        double totalFC = (aeroplane.getFuelConsumption() * (flightPlan.getFlightPlanTotalDistance() / 100));
        return (int) totalFC;
    }

    public String flightTotalTime() {
        double totalTT = flightPlan.getFlightPlanTotalDistance() / aeroplane.getSpeed();
        int hour = (int) totalTT;
        int minutes = (int)(60 *(totalTT - hour));
        String duration = hour + " hours" + " : " + minutes  + " minutes";

        return duration;
    }

    public int getCO2Emissions() {
        double totalCO2 = 2.52 * getTotalFuelConsumption();
        return (int) totalCO2;
    }


    public double getDistance() {
        return flightPlan.getFlightPlanTotalDistance();
    }

    @Override
    public String toString() {
        return "Flight: " +flightNumber + "\n" +
                "Airline: " + airline.getAirlineName() + "\n" +
                "Aeroplane: " + aeroplane.getModel() + "\n" +
                "Departure Airport: " + departureAirport + "\n"+
                "Arrival Airport: " + arrivalAirport + "\n" +
                "Departure date: " + departureDate + "\n"+
                "Departure time: " + departureTime + "\n";
    }

    //CODE FOR THREADS
    //To be used in threads
    GPSCoordinates position;// current
    GPSCoordinates next;// next position
    double distanceTravelled;
    double currentFuelConsumption;
    double currentCO2;
    String currentDuration;
    String towerName;
    String nextTower;
    String status;

    public void setNext(GPSCoordinates next) {
		this.next = next;
	}

    public GPSCoordinates getNext(){
        return next;
    }

    public GPSCoordinates getPosition() {
		return position;
	}

    public double getCurrentFuelConsumption(){
        return currentFuelConsumption;
    }
    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    public String getCurrentDuration(){
        return currentDuration;
    }
    public void setInitialPosition(GPSCoordinates position) {
		this.position = position;
	}

    public double getCurrentC02(){
        return currentCO2;
    }

    public String getTowerName(){
        return towerName;
    }

    public String getTowername(GPSCoordinates g){
        return towerName;
    }
    public String getStatus(){
        return status;
    }

	public void run() {
		try {	
            //initialise the variables
            distanceTravelled = 0.0;
            currentFuelConsumption = 0.0;
            currentCO2= 0.0;
            int i = 0;
            int a = 0;
            next = getFlightPlan().getAtIndex(a+1).getControlTower().getLocation();
            GPSCoordinates prev = getFlightPlan().getAtIndex(i+1).getControlTower().getLocation();
            double distance = (getAeroplane().getSpeed() * 0.25/15);
            position = getDepartureAirport().getControlTower().getLocation();
            

			while(status!="Arrived") {
                
                if(i==(getFlightPlan().getSize()-1)){
                    i=i-1;
                }

                //get the current control tower that the flight is communicating with    
                ControlTower obj = flightPlan.getAtIndex(i).getControlTower();
                
                //get the name of the previous and next tower locations
                // prev = getFlightPlan().getAtIndex(a).getControlTower().getLocation();
                // next = getFlightPlan().getAtIndex(a+1).getControlTower().getLocation();

                //Code to direct the flight to the next control tower in the flight plan
                if((position.circleDistance(next)<distance)&&(a!=((getFlightPlan().getSize()-2)))){
                    next = getFlightPlan().getAtIndex(a+2).getControlTower().getLocation();
                    a++;

                }else {
                    next = getFlightPlan().getAtIndex(a+1).getControlTower().getLocation();
                }

                //Update the flights position 
                position = position.addCircleDistance(next, distance);
                

                //update fuel consumption, distance and duration
                distanceTravelled +=  distance;
                double z =(aeroplane.getFuelConsumption() * (distanceTravelled / 100));
                currentFuelConsumption = z;
                double y =(2.52*z);
                currentCO2 = y;

                //update time
                double TT = distanceTravelled / aeroplane.getSpeed();
                int hrs = (int) TT;
                int mins = (int)(60 *(TT - hrs));
                currentDuration = hrs + " hours" + " : " + mins  + " minutes";

                //get the distances from the previous and next control towers
                double A = position.circleDistance(prev);
                double B = position.circleDistance(next);

                if (A<B){
                    obj = flightPlan.getAtIndex(i).getControlTower();

                }
                else {
                    obj = flightPlan.getAtIndex(i+1).getControlTower();
                    i++;
                    
                }

                //update status
                if (distanceTravelled<getDistance()){
                status = "In flight";
                }

                else{
                    status ="Arrived";
                }
                
                //Send the infomation back to the control tower
                towerName =obj.getLocationName();
                
				// ControlTower obj = ControlTower.getControlTower();
				obj.update(this);
				Thread.sleep(60000);
            } 
            
		} 
        catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("TIME: "+flightTotalTime());
        System.out.println("DISTANCE: "+getDistance());
        System.out.println("FUEL: "+getTotalFuelConsumption());
        System.out.println("CO2: "+getCO2Emissions());  
	}
}
