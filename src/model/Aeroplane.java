package model;

public class Aeroplane {
    private String model;
    private String manufacturer;
    private double speed;
    private double fuelConsumption;

    public Aeroplane(String model, String manufacturer, double speed, double fuelConsumption) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.speed = speed;
        this.fuelConsumption = fuelConsumption;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    @Override
    public String toString() {
        return "Aeroplane model: " + model + "\n" +
                "Manufacturer: " + manufacturer + "\n" +
                "Cruise Speed: " + speed + "\n" +
                "Fuel Consumption(per 100km): " + fuelConsumption;
    }
}
