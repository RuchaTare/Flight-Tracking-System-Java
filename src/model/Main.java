package model;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException{
            
        var plane1 = new Aeroplane("B777", "Boeing", 875, 952.78815);
//
//        System.out.println(plane1.getFuelConsumption());
//        System.out.println(plane1.getManufacturer());
//        System.out.println(plane1.getSpeed());
//        System.out.println(plane1.getModel());
//        System.out.println(plane1.toString());
//



//
//        System.out.println(airline1.getAirlineCode());
//        System.out.println(airline1.getAirlineName());
//        System.out.println(airline1.toString());

        var gps1 = new GPSCoordinates("51°28'12\"N 0°27'15\"W");
        var gps2 = new GPSCoordinates("49°0'35\"N 2°32'52\"E");
        var gps3 = new GPSCoordinates("41°46'24\"N 12°14'22\"E");
// //
        var ct1 = new ControlTower("LHR", gps1);
        var ct2 = new ControlTower("CDG", gps2);
        var ct3 = new ControlTower("FCO", gps3);

//        System.out.println(ct1.getLocation());
//        System.out.println(ct1.getLocationName());
//
        var airport1 = new Airport("LHR", "Heathrow", ct1);
        var airport2 = new Airport("CDG", "Paris Charles de Gauelle", ct2);
        var airport3 = new Airport("FCO", "Leonardo Da Vinci", ct3);

//        System.out.println(airport1.getAirportName());
//        System.out.println(airport1.getAirportCode());
//        System.out.println(airport1.toString());

        var fp1 = new FlightPlan();
        fp1.addToPlan(airport1, 0);
        fp1.addToPlan(airport2, 1);
        fp1.addToPlan(airport3, 2);

        var fp2 = new FlightPlan();
        fp2.addToPlan(airport1, 0);
        fp2.addToPlan(airport2, 1);

//        System.out.println(fp1.getFlightPlanTotalDistance());


////
        // var list = new ListOfFlights();
        var airline1 = new Airline("BA", "British Airways", 0, 0, 0, new ArrayList<Flight>());
        var f = new Flight("BA670", airline1, plane1, airport1, airport3, "01/01/2022", "23:00", fp1);
        var f2 = new Flight("BA680", airline1, plane1, airport1, airport2, "01/01/2022", "23:00", fp2);
        // airline1.addFlight(flight1);
        // airline1.addFlight(flight2);

        

        // System.out.println(airline1.toString());
//        System.out.println(flight1.getTotalFuelConsumption());
//        System.out.println(flight1.flightTotalTime());
//        System.out.println(flight1.getCO2Emissions());
//        System.out.println(flight1.getDistance());

        // var list1 = new ListOfAeroplanes();
        // list1.readFilePlanes();


        // var list2 = new ListOfAirports();
        // list2.readFileAirport();
//        System.out.println(list.airportsReportList());

        // var list3 = new ListOfAirlines();
        // list3.readFileAirline();


//        System.out.println(list.airlineReportList());

        // var list4 = new ListOfFlights(list1, list2, list3);
        // list4.readFlightList();
//        System.out.println(list4.flightsReportList());

//        System.out.println(list4.flightIterator());

        // var list5 = new ListOfAirlines(list4);
        // list5.readFileAirline();
        // list5.addFlights();
//        System.out.println(list3.readFileAirline());

//        System.out.println(list5.airlineReportList());

        // var rp = new AirlinesReport(list5, list4);
        // rp.WriteToFile();

//        System.out.println(airline1.toString());

        // f.setInitialPosition(airport1.getControlTower().getLocation());
        // f.setNext(airport3.getControlTower().getLocation());
        f.setName("AF666");
        f.start();
        
        Thread.sleep(60000);
        // System.out.println(f.getFlightNumber()+ " has arrived");
        // System.out.println(f.getFlightPlan().getSize());
        f.interrupt();



    }
}
