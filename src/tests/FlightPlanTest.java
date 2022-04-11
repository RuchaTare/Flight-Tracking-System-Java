// package tests;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.junit.Assert.assertEquals;
// import static org.junit.jupiter.api.Assertions.*;

// import model.*;
// import org.junit.jupiter.api.Test;


// import org.junit.jupiter.api.Test;

// class FlightPlanTest {

//     @Test
//     void testFlightPlanDistance() {
//         int expected = 222;
//         String message = "Failed for fuel consumption(per 100) = 14 and the flight plan given";

//         //create an instance of Flight plan for testing

//         GPSCoordinates g = new GPSCoordinates("78°14'09\"N 15°29'29\"E");
//         GPSCoordinates g1 = new GPSCoordinates("79°14'09\"N 15°29'29\"E");
//         GPSCoordinates g2 = new GPSCoordinates("80°14'09\"N 15°29'29\"E");
//         ControlTower t = new ControlTower("EEE", g);
//         ControlTower t1 = new ControlTower("EEE", g1);
//         ControlTower t2 = new ControlTower("EEE", g2);
//         Airport a = new Airport("AAA", "BBB", t);
//         Airport a1 = new Airport("AAA", "BBB", t1);
//         Airport a2 = new Airport("AAA", "BBB", t2);
//         Aeroplane m = new Aeroplane("MMM", "DDD", 10, 14.00);
//         FlightPlan p = new FlightPlan();
//         p.addToPlan(a, 0);
//         p.addToPlan(a1, 1);
//         p.addToPlan(a2, 2);
//         int actual = p.getFlightPlanTotalDistance();
//         assertEquals(message, expected, actual);


//     }

//     @Test
//     void testAdd() {
//         int expected7 = 28;
//         String message7 = "Failed for fuel consumption(per 100) = 14 and the flight plan given";
//         int actual7 = 28;
//         assertEquals(message7, expected7, actual7);

//     }

// }

