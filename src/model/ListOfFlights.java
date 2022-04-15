package model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ListOfFlights {
    private HashMap<String, Flight> listOfFlights;
    private ListOfAeroplanes listOfAeroplanes;
    private ListOfAirports listOfAirports;
    private ListOfAirlines listOfAirlines;

    public ListOfFlights() {
        this.listOfFlights = new HashMap<String, Flight>();
    }

    public HashMap<String, Flight> getListOfFlights() {
        return listOfFlights;
    }

    public ListOfFlights(ListOfAeroplanes listOfAeroplanes, ListOfAirports listOfAirports, ListOfAirlines listOfAirlines) {
        this.listOfAeroplanes = listOfAeroplanes;
        this.listOfAirports = listOfAirports;
        this.listOfAirlines = listOfAirlines;
    }

    public boolean readFlightList() {
        this.listOfFlights = new HashMap<String, Flight>();
        BufferedReader br = null;
        String line = null;
        try {
            File file = new File("src/listofflights.txt");
            br = new BufferedReader(new FileReader(file));

            // To execute the jar
            // InputStream data = getClass().getResourceAsStream("src/listofflights.txt");
            // br = new BufferedReader(new InputStreamReader(data));
            
            while ((line = br.readLine()) != null) {
                String parts[] = line.split(";");

                String flightNumber = parts[0].trim();

                String departAirport = parts[3].trim();
                Airport depart = this.listOfAirports.getListOfAirports().get(departAirport.trim());

                String arriveAirport = parts[4].trim();
                Airport arrive = this.listOfAirports.getListOfAirports().get(arriveAirport.trim());

                String airlineName = parts[1].trim();
                Airline airline = this.listOfAirlines.getListOfAirlines().get(flightNumber.substring(0, 2));

                String planeCode = parts[2].trim();
                Aeroplane plane = this.listOfAeroplanes.getListOfAeroplanes().get(planeCode.trim());

                String departDate = parts[5].trim();
                String departTime = parts[6].trim();

                var flightPlan = new FlightPlan();
                for (int i = 7; i < parts.length; i++) {
                    Airport a = listOfAirports.getListOfAirports().get(parts[i].trim());
                    flightPlan.addToPlan(a, i - 7);
                }

                Flight v = new Flight(flightNumber, airline, plane, depart, arrive, departDate, departTime,
                        flightPlan);
                this.listOfFlights.put(flightNumber, v);
                ;
            }

        }
        // if the file is not found, stop with system exit
        catch (FileNotFoundException fnf) {
            System.out.println("listofflights.txt file not found ");
            System.exit(0);
        }
        // this catches trying to convert a String to an integer
        catch (NumberFormatException e) {
            String error = "Number conversion error in '" + line + "' - "
                    + e.getMessage();
            System.out.println(error);
        }
        // catches error if Buffered reader fails
        catch (IOException e) {
            String error = "Error in BufferedReader while reading a file"
                    + e.getMessage();
            System.out.println(error);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
                ;
            }
        }
        return true;
    }


        public void AddFlight (Flight flight){
            if (flight == null)
                throw new IllegalArgumentException("This is not a valid flight");
            else {
                //If flight code is null then there isn't a valid key
                if (flight.getFlightNumber() != null) {
                    this.listOfFlights.put(flight.getFlightNumber(), flight);
                } else
                    throw new IllegalArgumentException("This flight doesn't have a valid Flight Code");
            }

        }


        public String flightsReportList () {
            String list = "All Flights\n\n";

            Iterator<Map.Entry<String, Flight>> iteratorplanes = listOfFlights.entrySet().iterator();
            while (iteratorplanes.hasNext()) {
                Map.Entry<String, Flight> entry = iteratorplanes.next();
                Flight a = entry.getValue();
                String details = a.toString();
                list += details + "\n\n";
            }
            return list;
        }

        public double getTotalDistance () {
            double total = 0.0;
            Iterator<Map.Entry<String, Flight>> iterator = listOfFlights.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Flight> entry = iterator.next();
                Flight f = entry.getValue();
                total += f.getDistance();
            }
            return total;
        }

        public double getEmissions () {
            double total = 0.0;
            Iterator<Map.Entry<String, Flight>> iterator = listOfFlights.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Flight> entry = iterator.next();
                Flight f = entry.getValue();
                total += f.getCO2Emissions();
            }
            return total;
        }

        public double getFuelLitres () {
            double total = 0.0;
            Iterator<Map.Entry<String, Flight>> iterator = listOfFlights.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Flight> entry = iterator.next();
                Flight f = entry.getValue();
                total += f.getTotalFuelConsumption();
            }
            return total;
        }

        public ArrayList<Flight> flightIterator () {
            var flights = new ArrayList<Flight>();
            Iterator<Map.Entry<String, Flight>> iteratorplanes = listOfFlights.entrySet().iterator();
            while (iteratorplanes.hasNext()) {
                Map.Entry<String, Flight> entry = iteratorplanes.next();
                Flight a = entry.getValue();
               flights.add(a);
            }
            return flights;
        }

        public int getSize () {
            return listOfFlights.size();
        }

        public void add (String key, Flight v){
            listOfFlights.put(key, v);
        }

        public Flight get(String fKey) {
             return listOfFlights.get(fKey);
         }
}
