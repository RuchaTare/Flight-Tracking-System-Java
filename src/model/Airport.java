package model;

import java.util.Objects;

public class Airport {
    private String airportCode;
    private String airportName;
    private ControlTower controlTower;

    public Airport(String airportCode, String airportName, ControlTower controlTower) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.controlTower = controlTower;
    }

    public Airport(String airportCode, String airportName) {
        this.airportCode = airportCode;
        this.airportName = airportName;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public ControlTower getControlTower() {
        return controlTower;
    }

    public void setControlTower(ControlTower controlTower) {
        this.controlTower = controlTower;
    }

    @Override
    public String toString() {
        return "Airport: " + airportName + "\n"+
                "Code: " + airportCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return airportCode.equals(airport.airportCode) && airportName.equals(airport.airportName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airportCode, airportName);
    }


}
