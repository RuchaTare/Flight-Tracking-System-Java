package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class logger 
{
	
	private static logger logging = null;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();

    // Creating a name for the log file generation
    private final String logFile = "src/logs/flights-logger-" + dateFormat.format(cal.getTime()) + ".log";
	private PrintWriter writer;
	
	private logger() {
		try {
			    FileWriter fw = new FileWriter(logFile);
		        writer = new PrintWriter(fw, true);
		    } 
        catch (IOException e) {}
	}
	
	public static synchronized logger getInstance(){
		if(logging == null)
        logging = new logger();
		return logging;
	}
	

    /**
        Logger to capture the details on new added flights
     */
	public void logAddFlight (String flightDetails) {
		writer.println("NEW-FLIGHT-ADDED-TO-SCHEDULE: " + flightDetails);
	}
	

    /**
        Logger to capture the details for the Flights in progress
     */
	public void logOnFlight (String flightNumber, 
                             String position, 
                             double distance, 
                             String duration, 
                             String controlTower, 
                             String status) {
        writer.println("ON-FLIGHT: \nFlight-Number: " + flightNumber + ", Current-Position: " + position + 
                       ", Distance-Travelled: " + distance + ", Flight-Duration: " + duration + 
                       ", Control-Tower: " + controlTower + ", Status: " + status);
        }

    /**
        Logger to capture the details for the Flights landed
     */
	public void logFlightLanded (String flightNumber,
                                 String position, 
                                 double distance, 
                                 String duration, 
                                 String controlTower, 
                                 String status) {
        writer.println("FLIGHT-LANDED: \nFlight-Number: " + flightNumber + ", Current-Position: " + position + 
                       ", Distance-Travelled: " + distance + ", Flight-Duration: " + duration + 
                       ", Control-Tower: " + controlTower + ", Status: " + status);
	}
}
