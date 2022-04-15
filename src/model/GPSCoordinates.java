package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * GPS position expressed with two angles latitude and longitude in degrees.
 * These are the spherical coordinates with the radius being equal to earth radius 6371 km.
 * GPS coordinates are sometimes given in DMS (degree, minute, second) format.
 * Such coordinates would be given in a single String, example: "78°14'09"N 15°29'29"E".
 * Or in two separate strings: "78°14'09"N" and  "15°29'29"E".
 * If both cases, we need to parse the string coordinates and convert to double degree values.
 * This is done in the two constructors: public GPS(String dmsCoord) and public GPS(String dms_lat, String dms_lon)
 *
 * @author batatia
 *
 */
public class GPSCoordinates {
    /**
     * latitude in degrees, between -90° to +90°
     * with respect to equator
     */
    double lat;
    /**
     * longitude in degrees, between -180° to +180°
     * zero centred at prime meridian
     */
    double lon;
    /**
     * earth radius
     */
    static final double R= 6371.0;

    /**
     * constructor with lat and lon expressed in double degrees
     * @param t
     * @param g
     */
    public GPSCoordinates(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * constructor taking a string with latitude and longitude as one string
     * in DMS format
     *
     * @param dmsCoord example: "78°14'09"N 15°29'29"E"
     */
    public GPSCoordinates(String dmsCoord) {
        double[] latlon = convert(dmsCoord);
        this.lat = latlon[0];
        this.lon = latlon[1];
    }



    /**
     * @return the lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * @return the lon
     */
    public double getLon() {
        return lon;
    }

    /**
     * return latitude in radian
     * @return radian
     */
    public double radianLat() {
        return lat * Math.PI/180;
    }

    /**
     * return longitude in radian
     * @return radian
     */
    public double radianLon() {
        return lon * Math.PI/180;
    }


    /**
     * Calculate the great circle distance between this GPS and the given GPS position.
     * See illustration: <img src="./doc-files/great_circle.png" />
     * @param g the second GPS position
     * @return
     */
    public double circleDistance(GPSCoordinates g) {
        //convert bot GPS coordinates to radians
        double phi1 = radianLat(); //radian
        double theta1 = radianLon(); //radian
        double phi2 = g.radianLat(); //radian
        double theta2 = g.radianLon(); //radian
        //calculate differences
        double dphi = phi2 - phi1;
        double dtheta = theta2 - theta1;
        //calculate the great circle arc distance
        double d1 = Math.pow(Math.sin(dphi/2), 2) + Math.cos(phi1) * Math.cos(phi2) * Math.pow(Math.sin(dtheta/2),2);
        double d2 = 2 * Math.asin(Math.sqrt(d1));
        return R * d2;
    }


    /**
     * Calculate the bearing between between this GPS and the GPS position g.
     * The bearing is the angle between the meridian (North, south) and the line made by the two points (this, g)
     * @param g the second GPS position
     * @return the bearing angle in radian
     */
    public double bearing(GPSCoordinates g) {
        //convert lat, lon to radian
        double r_lat1 = this.radianLat();
        double r_lat2 = g.radianLat();
        double r_lon1= this.radianLon();
        double r_lon2 = g.radianLon();

        //calulate bearing
        double delta_lon = r_lon2 - r_lon1;
        double x = Math.cos(r_lat2) * Math.sin(delta_lon);
        double y = Math.cos(r_lat1) * Math.sin(r_lat2) - Math.sin(r_lat1) * Math.cos(r_lat2) * Math.cos(delta_lon);
        double bearing = Math.atan2(x,y); //probably should be Math.atan2(y,x) - need to be checked experimentally
        return bearing;
    }

    /**
     * Add a distance d to the current GPS in the direction of the second GPS (g).
     * Calculate the corresponding intermediate GPS position.
     * The method is more precise as it takes into consideration the curvature of the line between this and g.
     * @param g the direction towards which we add d
     * @param d the added distance in km
     * @return
     */
    public GPSCoordinates addCircleDistance(GPSCoordinates g, double d) {
        //convert lat, lon to radian
        double r_lat1 = this.radianLat();
        double r_lat2 = g.radianLat();
        double r_lon1= this.radianLon();
        double r_lon2 = g.radianLon();
        //calculate intermediate results
        double angular = d/R;
        double f = d/circleDistance(g);
        double a = Math.sin(angular*(1-f))/Math.sin(angular);
        double b = Math.sin(angular * f) / Math.sin(angular);

        //calculate Cartesian coordinates
        double x = a * Math.cos(r_lat1)* Math.cos(r_lon1) + b * Math.cos(r_lat2) * Math.cos(r_lon2);
        double y = a * Math.cos(r_lat1)* Math.sin(r_lon1) + b * Math.cos(r_lat2) * Math.sin(r_lon2);
        double z = a *  Math.sin(r_lat1) + b * Math.sin(r_lat2);

        //calculate lat and lon for intermediate point
        double r_latx = Math.atan2(z,  Math.sqrt(x*x + y*y));
        double r_lonx = Math.atan2(y, x);

        return new GPSCoordinates(toLatDegree(r_latx), toLonDegree(r_lonx));
    }

    /**
     * Regex pattern to match GPS coordinates in DSM format
     * This pattern is limited. It cannot handle spaces between DMS items.
     * It cannot handle decimals in the seconds.
     * It should be extended to handle those situations.
     */
    private final static Pattern DMS_PATTERN = Pattern.compile(
            "(-?)([0-9]{1,2})°([0-5]?[0-9])'([0-5]?[0-9])\"([NS])\\s*" +
                    "(-?)([0-1]?[0-9]{1,2})°([0-5]?[0-9])'([0-5]?[0-9])\"([EW])");


    /**
     * Uses the matched  DMS regex pattern of latitude or longitude.
     * converts the string to a double value corresponding to degrees.
     * @param m regex matcher built from the appropriate pattern
     * @param offset an int used to skip latitude when converting a full string for lat long
     * @return
     */
    private double toDouble(Matcher m, int offset) {
        int sign = "".equals(m.group(1 + offset)) ? 1 : -1;
        double degrees = Double.parseDouble(m.group(2 + offset));
        double minutes = Double.parseDouble(m.group(3 + offset));
        double seconds = Double.parseDouble(m.group(4 + offset));
        int direction = "NE".contains(m.group(5 + offset)) ? 1 : -1;

        return sign * direction * (degrees + minutes / 60 + seconds / 3600);
    }

    /**
     * Parse DMS regex pattern in a string made of the latitude and longitude.
     * The method parses the string using the pattern DMS_PATTERN
     * and converts the string to two double values corresponding to latitude and longitude in degrees.
     * @param dms a string for the form "78°14'09"N 15°29'29"E"
     * @return an array with two double values, the first is lat the second lon
     */
    public double[] convert(String dms) {
        Matcher m = DMS_PATTERN.matcher(dms.trim());

        if (m.matches()) {
            double latitude = toDouble(m, 0);
            double longitude = toDouble(m, 5);

            if ((Math.abs(latitude) > 90) || (Math.abs(longitude) > 180)) {
                throw new NumberFormatException("Invalid latitude or longitude");
            }

            return new double[] { latitude, longitude };
        } else {
            throw new NumberFormatException(
                    "Malformed degrees/minutes/seconds/direction coordinates");
        }
    }


    /**
     * convert longitude from radians to degrees
     * @param radLon
     * @return
     */
    public static double toLonDegree(double radLon) {
        double degree = radLon * 180 / Math.PI;
        if(degree > 180) {
            degree = degree - 360;
        }
        return degree;
    }

    /**
     * convert latitude from radians to degrees
     * @param radLat
     * @return
     */
    public static double toLatDegree(double radLat) {
        double degree = radLat * 180 / Math.PI;
        if(degree >= 270) {
            degree = degree - 360;
        }
        return degree;
    }

    @Override
    public String toString() {
        return "GPS [lat = " + String.format("%.2f", lat) + ", lon = " + String.format("%.2f", lon) + "]";
    }
}

