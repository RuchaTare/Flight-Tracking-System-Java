package model;


import java.io.*;
import java.util.*;

public class ListOfAirlines implements Iterable{
    private Hashtable<String, Airline> listOfAirlines;
    private ListOfFlights listOfFlights;

    public ListOfAirlines(ListOfFlights listOfFlights) {
        this.listOfFlights = listOfFlights;
    }

    public ListOfAirlines(){
        this.listOfAirlines = new Hashtable<String, Airline>();
    }


    public Hashtable<String, Airline> getListOfAirlines() {
        return listOfAirlines;
    }



    public boolean readFileAirline() {
        this.listOfAirlines = new Hashtable<String, Airline>();
        BufferedReader br = null;
        String line = null;

        try{
            File file = new File("src/listofairlines.txt");
            br = new BufferedReader( new FileReader(file));
            while ((line = br.readLine()) != null){
                String parts[] = line.split(";");

                String airlineCode = parts[0];
                String airlineName = parts[1];

                // remove spaces
                airlineCode = airlineCode.trim();
                airlineName = airlineName.trim();

                // create a aeroplane object and add to the list
                Airline v = new Airline (airlineCode, airlineName, 0, 0, 0,new ArrayList<Flight>());
                this.listOfAirlines.put(airlineCode, v);
            }
        }
        // if the file is not found, stop with system exit
        catch (FileNotFoundException fnf) {
            System.out.println("listofairlines.txt file not found ");
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
//
    public void addFlights(){
        String pkey = null;
        Airline pvalue = null;
        for (Map.Entry<String, Airline> entry : listOfAirlines.entrySet()) {
            String key = entry.getKey();
            Airline value = entry.getValue();
            var flightArray = listOfFlights.flightIterator();
            for(Flight f: flightArray){
                value.addFlight(f);
            }
        }
    }

    public String airlineReportList() {
        String list = "All Aeroplanes\n\n";

        Iterator<Map.Entry<String, Airline>> iteratorplanes = listOfAirlines.entrySet().iterator();
        while (iteratorplanes.hasNext()) {
            Map.Entry<String, Airline> entry = iteratorplanes.next();
            Airline a = entry.getValue();
            String details = a.toString();
            list += details +"\n\n";
        }
        return list;
    }

    public Airline retrieveCompanyName(String airlineCode) {
        return listOfAirlines.get(airlineCode);
    }

//    public ListOfFlights getListOfFlights() {
//        return listOfFlights;
//    }

    public ArrayList<Airline> airlineIterator () {
        var airlines = new ArrayList<Airline>();
        Iterator<Map.Entry<String, Airline>> iterator = listOfAirlines.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Airline> entry = iterator.next();
            Airline a = entry.getValue();
            airlines.add(a);
        }
        return airlines;
    }

    @Override
    public Iterator iterator() {
        return null;
    }
}
