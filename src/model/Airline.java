package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class Airline {
    private String airlineCode;
    private String airlineName;
    private double totalDistance;
    private double totalFuelConsumption;
    private double totalEmissions;
    public ArrayList<Flight> flightArrayList;


    public Airline(String airlineCode, String airlineName) {
        this.airlineCode = airlineCode;
        this.airlineName = airlineName;
    }

    public Airline(String airlineCode, String airlineName, double totalDistance, double totalFuelConsumption, double totalEmissions, ArrayList<Flight> flightArrayList) {
        this.airlineCode = airlineCode;
        this.airlineName = airlineName;
        this.totalDistance = totalDistance;
        this.totalFuelConsumption = totalFuelConsumption;
        this.totalEmissions = totalEmissions;
        this.flightArrayList = flightArrayList;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public int getNumberOfFlights() {
        return flightArrayList.size();
    }

    public double getTotalDistance() {
        double td =  0;
        for(Flight f: flightArrayList){
            double d = f.getDistance();
            td += d;
        }
        return td;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getTotalFuelConsumption() {
        double td =  0;
        for(Flight f: flightArrayList){
            double d = f.getTotalFuelConsumption();
            td += d;
        }
        return td;
    }

    public void setTotalFuelConsumption(double totalFuelConsumption) {
        this.totalFuelConsumption = totalFuelConsumption;
    }

    public double getTotalEmissions() {
        double td =  0;
        for(Flight f: flightArrayList){
            double d = f.getCO2Emissions();
            td += d;
        }
        return td;
    }

    public void setTotalEmissions(double totalEmissions) {
        this.totalEmissions = totalEmissions;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public void addFlight(Flight e){
        if(e.getFlightNumber().substring(0,2).equals(airlineCode))
            flightArrayList.add(e);
    }

    @Override
    public String toString() {
        return "Airline: " + airlineName + "\n" +
                "Code: " + airlineCode + "\n" +
                "Number of Flights: " + getNumberOfFlights() +"\n" +
                "Total Distance: " + getTotalDistance() + "\n" +
                "Total Fuel Consumption: " + getTotalFuelConsumption() + "\n" +
                "Total Emissions: " + getTotalEmissions();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airline airline = (Airline) o;
        return airlineCode.equals(airline.airlineCode) && airlineName.equals(airline.airlineName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airlineCode, airlineName);
    }
}
