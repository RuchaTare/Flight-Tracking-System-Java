package controller;

import java.util.ArrayList;

import model.AirlinesReport;
import model.Flight;
import model.ListOfAeroplanes;
import model.ListOfAirlines;
import model.ListOfAirports;
import model.ListOfFlights;
import view.FlightGUI;

public class ControllerFlightGUI {
    private ListOfFlights listOfFlights;
    private ListOfAirlines listOfAirlines;
    private ListOfAirports listOfAirports;
    private ListOfAeroplanes listOfAeroplanes;
    private Flight flight;

    public ControllerFlightGUI() {
        //initialise empty list of flight tracker elements

        listOfAeroplanes = new ListOfAeroplanes();
        listOfAeroplanes.readFilePlanes();

        listOfAirports = new ListOfAirports();
        listOfAirports.readFileAirport();

        listOfAirlines = new ListOfAirlines(listOfFlights);
        listOfAirlines.readFileAirline();

        listOfFlights = new ListOfFlights(listOfAeroplanes, listOfAirports, listOfAirlines);
        listOfFlights.readFlightList();

        listOfAirlines = new ListOfAirlines(listOfFlights);
        listOfAirlines.readFileAirline();
        listOfAirlines.addFlights();



    }

    public void createReport(){
        var rep = new AirlinesReport(listOfAirlines, listOfFlights);
        rep.WriteToFile();
    }

    public void showGUI() {
        //create main GUI with gsCompetitorList object
        FlightGUI gui = new FlightGUI(listOfFlights,listOfAirlines,listOfAirports, listOfAeroplanes);
        gui.setVisible(true);
        

    }

    public void tryThis() throws InterruptedException{
        
        ArrayList<Flight> list = listOfFlights.flightIterator();
        for(Flight f: list){
            
            f.start();
            Thread.sleep(60000);
            }
            // f.interrupt();
        }

    public void updateGUI(){
        listOfAeroplanes = new ListOfAeroplanes();
        listOfAeroplanes.readFilePlanes();

        listOfAirports = new ListOfAirports();
        listOfAirports.readFileAirport();

        listOfAirlines = new ListOfAirlines(listOfFlights);
        listOfAirlines.readFileAirline();

        listOfFlights = new ListOfFlights(listOfAeroplanes, listOfAirports, listOfAirlines);
        listOfFlights.readFlightList();

        listOfAirlines = new ListOfAirlines(listOfFlights);
        listOfAirlines.readFileAirline();
        listOfAirlines.addFlights();
        FlightGUI gui = new FlightGUI(listOfFlights, listOfAirlines, listOfAirports, listOfAeroplanes);
    }
}
