package model;

import java.util.ArrayList;

public class Main {
        private ListOfFlights listOfFlights;
        private ListOfAirlines listOfAirlines;
        private ListOfAirports listOfAirports;
        private ListOfAeroplanes listOfAeroplanes;
        private Flight flight;
        private ControlTower controlTower;
        

public void tryThis() throws InterruptedException{
        Object rowData[] = new Object[9];
        ArrayList<Flight> list = listOfFlights.flightIterator();
        for(Flight f: list){
            f.start();
            Thread.sleep(60000);
            f.interrupt();
            
        }}
        
    public static void main(String[] args) throws InterruptedException{
            
        var plane1 = new Aeroplane("B777", "Boeing", 875, 952.78815);
        var gps1 = new GPSCoordinates("51°28'12\"N 0°27'15\"W");
        var gps2 = new GPSCoordinates("49°0'35\"N 2°32'52\"E");
        var gps3 = new GPSCoordinates("41°46'24\"N 12°14'22\"E");
// //
        var ct1 = new ControlTower("LHR", gps1);
        var ct2 = new ControlTower("CDG", gps2);
        var ct3 = new ControlTower("FCO", gps3);

        var airport1 = new Airport("LHR", "Heathrow", ct1);
        var airport2 = new Airport("CDG", "Paris Charles de Gauelle", ct2);
        var airport3 = new Airport("FCO", "Leonardo Da Vinci", ct3);

        var fp1 = new FlightPlan();
        fp1.addToPlan(airport1, 0);
        fp1.addToPlan(airport2, 1);
        fp1.addToPlan(airport3, 2);

        var fp2 = new FlightPlan();
        fp2.addToPlan(airport1, 0);
        fp2.addToPlan(airport2, 1);

        // var list = new ListOfFlights();
        var airline1 = new Airline("BA", "British Airways", 0, 0, 0, new ArrayList<Flight>());
        var f = new Flight("BA670", airline1, plane1, airport1, airport3, "01/01/2022", "23:00", fp1);
        var f2 = new Flight("BA680", airline1, plane1, airport1, airport2, "01/01/2022", "23:00", fp2);
        

    }}
