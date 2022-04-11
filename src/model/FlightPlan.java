package model;

import java.util.LinkedList;

public class FlightPlan {
    private LinkedList<Airport> flightPlan;

    public LinkedList<Airport> getFlightPlan() {
        return flightPlan;
    }

    public FlightPlan() {
        this.flightPlan = new LinkedList<Airport>();
    }

    public void addToPlan(Airport a, int i) {
        if (flightPlan.size() < 6) {
            flightPlan.add(i, a);
        }
        else {
            System.out.println("The Plan has reached its limit!");
        }
    }

    public void removeFromPlan(Airport a, int i) {
        flightPlan.remove(i);
    }

    public int getSize() {
        return flightPlan.size();
    }

    public Airport getAtIndex(int index) {
        return flightPlan.get(index);}

    public String getFlightPlanList() {
        String plan = "";
        for (Airport a : flightPlan)
            plan += a.getAirportCode() + " ";
        return plan;
    }

    public int getFlightPlanTotalDistance(){
        int totalDistance = 0;
        for(int i = 0; i < flightPlan.size() - 1; i++){
            GPSCoordinates gps1 = flightPlan.get(i).getControlTower().getLocation();
            GPSCoordinates gps2 = flightPlan.get(i + 1).getControlTower().getLocation();
            totalDistance += gps1.circleDistance(gps2);
        }
        return totalDistance;
    }

    @Override
    public String toString() {
        String path = "";
        for(Airport a : flightPlan)
          path += a.getAirportName() + "\n";
        return path;
    }
}
