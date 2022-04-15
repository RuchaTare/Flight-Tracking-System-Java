package model;

import java.io.*;
import java.util.*;

public class ListOfAeroplanes {
    private HashMap<String, Aeroplane> listOfAeroplanes;

    public ListOfAeroplanes(){
        this.listOfAeroplanes = new HashMap<String, Aeroplane>();
    }

    public HashMap<String, Aeroplane> getListOfAeroplanes() {
        return listOfAeroplanes;
    }

    private void addToAeroplanesList(String k, Aeroplane v) {
        listOfAeroplanes.put(k, v);
    }


    public boolean readFilePlanes() {
        this.listOfAeroplanes = new HashMap<String, Aeroplane>();
        BufferedReader br = null;
        String line = null;
        try {
            File file = new File("src/listofplanes.txt");
            br = new BufferedReader(new FileReader(file));

            // To execute the jar
            // InputStream data = getClass().getResourceAsStream("src/listofplanes.txt");
            // br = new BufferedReader(new InputStreamReader(data));

            while ((line = br.readLine()) != null) {
                String parts[] = line.split(";");

                String model = parts[0];
                String manufacturer = (parts[1]);
                String speed = parts[2];
                String fuel = parts[3];

                // remove spaces
                model = model.trim();
                manufacturer = manufacturer.trim();
                speed = speed.trim();
                fuel = fuel.trim();

                // parsing to required formats
                int cruiseSpeed = Integer.parseInt(speed);
                Double fuelConsumption = Double.parseDouble(fuel);

                // create a aeroplane object and add to the list
                Aeroplane v = new Aeroplane(model, manufacturer, cruiseSpeed, fuelConsumption);
                this.listOfAeroplanes.put(model, v);
            }

        }
        // if the file is not found, stop with system exit
        catch (FileNotFoundException fnf) {
            System.out.println("listofplanes.txt file not found ");
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

    public ArrayList<Aeroplane> aeroplanesIterator () {
        var planes = new ArrayList<Aeroplane>();
        Iterator<Map.Entry<String, Aeroplane>> iteratorplanes = listOfAeroplanes.entrySet().iterator();
        while (iteratorplanes.hasNext()) {
            Map.Entry<String, Aeroplane> entry = iteratorplanes.next();
            Aeroplane a = entry.getValue();
            planes.add(a);
        }
        return planes;
    }

    public String aeroplanesReportList() {
        String list = "All Aeroplanes\n\n";

        Iterator<Map.Entry<String, Aeroplane>> iteratorplanes = listOfAeroplanes.entrySet().iterator();
        while (iteratorplanes.hasNext()) {
            Map.Entry<String, Aeroplane> entry = iteratorplanes.next();
            Aeroplane a = entry.getValue();
            String details = a.toString();
            list += details +"\n\n";
        }
        return list;
    }
}
