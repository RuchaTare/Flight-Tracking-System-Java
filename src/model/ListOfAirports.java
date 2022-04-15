package model;

import java.io.*;
import java.util.*;

public class ListOfAirports {
    private Hashtable<String, Airport> listOfAirports;
    private ListOfFlights listOfFlights;

    public ListOfAirports(ListOfFlights listOfFlights) {
        this.listOfFlights = listOfFlights;
    }

    public ListOfAirports() {
        this.listOfAirports = new Hashtable<String, Airport>();
    }


    public Hashtable<String, Airport> getListOfAirports() {
        return listOfAirports;
    }

    public boolean readFileAirport() {
        this.listOfAirports = new Hashtable<String, Airport>();
        BufferedReader br = null;
        String line = null;

        try{
            File file = new File("src/listofairports.txt");
            br = new BufferedReader( new FileReader(file));

            // To execute the jar
            // InputStream data = getClass().getResourceAsStream("src/listofairports.txt");
            // br = new BufferedReader(new InputStreamReader(data));

            while ((line = br.readLine()) != null){
                String parts[] = line.split(";");
                String airportCode = parts[0];
                String airportName = parts[1];
                String coords = parts[2];

                var gps = new GPSCoordinates(coords);

                var controlTower = new ControlTower(airportCode, gps);

                // create a aeroplane object and add to the list
                Airport v = new Airport (airportCode, airportName, controlTower);
                this.listOfAirports.put(airportCode, v);
            }
        }
        // if the file is not found, stop with system exit
        catch (FileNotFoundException fnf) {
            System.out.println("listofplanes.txt file not found ");
            System.exit(0);
        }
        // catches error if Buffered reader fails
        catch (IOException e) {
            String error = "Error in BufferedReader while reading a file"
                    + e.getMessage();
            System.out.println(error);
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                }
                catch (Exception e) {
                };
            }
        }
        return true;
    }

    public String airportsReportList() {
        String list = "All Airports\n\n";

        Iterator<Map.Entry<String, Airport>> iteratorplanes = listOfAirports.entrySet().iterator();
        while (iteratorplanes.hasNext()) {
            Map.Entry<String, Airport> entry = iteratorplanes.next();
            Airport a = entry.getValue();
            String details = a.toString();
            list += details +"\n\n";
        }
        return list;
    }

    public ArrayList<Airport> airportsIterator () {
        var airs = new ArrayList<Airport>();
        Iterator<Map.Entry<String, Airport>> iterator= listOfAirports.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Airport> entry = iterator.next();
            Airport a = entry.getValue();
            airs.add(a);
        }
        return airs;
    }

    public Airport getAirportDetails(String airportCode) {
        return listOfAirports.get(airportCode);
    }

    public boolean CheckIfValExists(Airport airport) {
        if (listOfAirports.containsValue(airport))
            return true;
        else
            return false;
    }

    public boolean CheckIfKeyExists(String airportCode) {
        if (listOfAirports.containsKey(airportCode))
            return true;
        else
            return false;
    }
}







