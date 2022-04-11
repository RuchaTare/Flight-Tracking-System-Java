package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class AirlinesReport {
    private ListOfAirlines listOfAirlines;
    private ListOfFlights listOfFlights;

    public AirlinesReport(ListOfAirlines listOfAirlines, ListOfFlights listOfFlights) {
        this.listOfAirlines = listOfAirlines;
        this.listOfFlights = listOfFlights;


    }

    public boolean WriteToFile() {
        try {
            FileWriter fileWriter = new FileWriter("ReportFile");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                bufferedWriter.write(listOfAirlines.airlineReportList());
                bufferedWriter.newLine();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
