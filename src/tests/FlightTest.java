// package tests;

// import static org.junit.Assert.assertEquals;
// import static org.junit.jupiter.api.Assertions.*;

// import model.*;
// import org.junit.jupiter.api.Test;

// class FlightTest {

//     @Test
//     public void testTotalFuel() {

//         int expected = 28;
//         String message  = "Failed for fuel consumption(per 100) = 14 and the flight plan given";
//         Airline a = new Airline("AAA","BBB");
//         GPSCoordinates g = new GPSCoordinates("78°14'09\"N 15°29'29\"E");
//         GPSCoordinates g1 = new GPSCoordinates("79°14'09\"N 15°29'29\"E");
//         GPSCoordinates g2 = new GPSCoordinates("80°14'09\"N 15°29'29\"E");
//         ControlTower t = new ControlTower("EEE", g);
//         ControlTower t1 = new ControlTower("EEE", g1);
//         ControlTower t2 = new ControlTower("EEE", g2);
//         var a1 = new Airport("EEE", "Ello", t);
//         var a2 = new Airport("EEE", "Ello", t1);
//         var a3 = new Airport("EEE", "eLLO", t2);
//         Aeroplane m = new Aeroplane("MMM","DDD", 10, 14.00);
//         FlightPlan p = new FlightPlan();
//         p.addToPlan(a1, 0);
//         p.addToPlan(a2, 1);
//         p.addToPlan(a3, 2);
//         Flight f = new Flight("AAA",a, m,"DDD","AAA", "2009-12-30","23:00:00",p);
//         int actual = f.getTotalFuelConsumption();
//         assertEquals(message, expected, actual);

//         Double expected1 = 200.00;
//         String message1  = "Failed for fuel consumption(per 100) = 100 and the flight plan given";
//         GPSCoordinates g3 = new GPSCoordinates("78°14'09\"N 15°29'29\"E");
//         GPSCoordinates g4 = new GPSCoordinates("79°14'09\"N 15°29'29\"E");
//         GPSCoordinates g5 = new GPSCoordinates("80°14'09\"N 15°29'29\"E");
//         ControlTower t3 = new ControlTower("EEE", g3);
//         ControlTower t4 = new ControlTower("EEE", g4);
//         ControlTower t5 = new ControlTower("EEE", g5);
//         Aeroplane m1 = new Aeroplane("MMM","DDD", 10, 100.00);
//         FlightPlan p1 = new FlightPlan();
//         p1.addToPlan(a1, 0);
//         p1.addToPlan(a2, 1);
//         p1.addToPlan(a3, 2);
//         Flight f1 = new Flight("AAA", a,m1,"DDD","AAA", "2009-12-30","23:00:00",p1);
//         int actual1 = f1.getTotalFuelConsumption();
//         assertTrue(expected1 != actual1);
//     }

//     @Test
//     public void testTotalTime() {
//         String expected2 = "22.00";
//         String message2  = "Failed for speed =10 and flight plan given below";
//         Airline a = new Airline("AAA","BBB");
//         GPSCoordinates g = new GPSCoordinates("78°14'09\"N 15°29'29\"E");
//         GPSCoordinates g1 = new GPSCoordinates("79°14'09\"N 15°29'29\"E");
//         GPSCoordinates g2 = new GPSCoordinates("80°14'09\"N 15°29'29\"E");
//         ControlTower t = new ControlTower("EEE", g);
//         ControlTower t1 = new ControlTower("EEE", g1);
//         ControlTower t2 = new ControlTower("EEE", g2);
//         var a1 = new Airport("EEE", "Ello", t);
//         var a2 = new Airport("EEE", "Ello", t1);
//         var a3 = new Airport("EEE", "eLLO", t2);
//         Aeroplane m = new Aeroplane("MMM","DDD", 10, 14.00);
//         FlightPlan p = new FlightPlan();
//         p.addToPlan(a1, 0);
//         p.addToPlan(a2, 1);
//         p.addToPlan(a3, 2);
//         Flight f = new Flight("AAA",a, m,"DDD","AAA", "2009-12-30","23:00:00",p);
//         String actual2 = f.flightTotalTime();
//         assertEquals(message2, expected2, actual2);

//         String expected3 = "2.00";
//         String message3  = "Failed for speed= 100 and flightPlan given below ";
//         GPSCoordinates g3 = new GPSCoordinates("78°14'09\"N 15°29'29\"E");
//         GPSCoordinates g4 = new GPSCoordinates("79°14'09\"N 15°29'29\"E");
//         GPSCoordinates g5 = new GPSCoordinates("80°14'09\"N 15°29'29\"E");
//         ControlTower t3 = new ControlTower("EEE", g3);
//         ControlTower t4 = new ControlTower("EEE", g4);
//         ControlTower t5 = new ControlTower("EEE", g5);
//         Aeroplane m1 = new Aeroplane("MMM","DDD", 100, 200.00);
//         FlightPlan p1 = new FlightPlan();
//         p1.addToPlan(a1, 0);
//         p1.addToPlan(a2, 1);
//         p1.addToPlan(a3, 2);
//         Flight f1 = new Flight("AAA",a, m1,"DDD","AAA", "2009-12-20","23:00:00",p1);
//         String actual3 = f1.flightTotalTime();
//         assertEquals(message3, expected3, actual3);

//     }

//     @Test
//     public void testC02Emissions() {
//         int expected4 = 70;
//         String message4  = "Failed for fuel consumption(per 100) = 14 and the flight plan given";
//         Airline a = new Airline("AAA","BBB");
//         GPSCoordinates g = new GPSCoordinates("78°14'09\"N 15°29'29\"E");
//         GPSCoordinates g1 = new GPSCoordinates("79°14'09\"N 15°29'29\"E");
//         GPSCoordinates g2 = new GPSCoordinates("80°14'09\"N 15°29'29\"E");
//         ControlTower t = new ControlTower("EEE", g);
//         ControlTower t1 = new ControlTower("EEE", g1);
//         ControlTower t2 = new ControlTower("EEE", g2);
//         var a1 = new Airport("EEE", "Ello", t);
//         var a2 = new Airport("EEE", "Ello", t1);
//         var a3 = new Airport("EEE", "eLLO", t2);
//         Aeroplane m = new Aeroplane("MMM","DDD", 10, 14.00);
//         FlightPlan p = new FlightPlan();
//         p.addToPlan(a1, 0);
//         p.addToPlan(a2, 1);
//         p.addToPlan(a3, 2);
//         Flight f = new Flight("AAA",a, m,"DDD","AAA", "2009-12-30","23:00:00",p);
//         int actual4 = f.getCO2Emissions();
//         assertEquals(message4, expected4, actual4);

//         int expected5 = 1008;
//         String message5  = "Failed for fuel consumption(per 100) = 100 and the flight plan given";
//         GPSCoordinates g3 = new GPSCoordinates("78°14'09\"N 15°29'29\"E");
//         GPSCoordinates g4 = new GPSCoordinates("79°14'09\"N 15°29'29\"E");
//         GPSCoordinates g5 = new GPSCoordinates("80°14'09\"N 15°29'29\"E");
//         ControlTower t3 = new ControlTower("EEE", g3);
//         ControlTower t4 = new ControlTower("EEE", g4);
//         ControlTower t5 = new ControlTower("EEE", g5);
//         Aeroplane m1 = new Aeroplane("MMM","DDD", 100, 200.00);
//         FlightPlan p1 = new FlightPlan();
//         p1.addToPlan(a1, 0);
//         p1.addToPlan(a2, 1);
//         p1.addToPlan(a3, 2);
//         Flight f1 = new Flight("AAA",a, m1,"DDD","AAA", "2009-12-20","23:00:00",p1);
//         int actual5 = f1.getCO2Emissions();
//         assertEquals(message5, expected5, actual5);

//     }

// }

