import java.time.ZonedDateTime;

import static java.lang.Math.*;

/**
 * Represents a point in space and time, recorded by a GPS sensor.
 *
 * @author Nick Efford & YOUR NAME
 */
public class Point {
  // Constants useful for bounds checking, etc

  private static final double MIN_LONGITUDE = -180.0;
  private static final double MAX_LONGITUDE = 180.0;
  private static final double MIN_LATITUDE = -90.0;
  private static final double MAX_LATITUDE = 90.0;
  private static final double MEAN_EARTH_RADIUS = 6.371009e+6;

  // TODO: Define fields for time, longitude, latitude and elevation
  private ZonedDateTime timeStamp;
  private double Longitude;
  private double Latitude;
  private double Elevation;
  
  // TODO: Define a constructor
  public Point (ZonedDateTime timeStamp, double Longitude, double Latitude, double Elevation) throws GPSException{
	  if (Longitude<MIN_LONGITUDE ||Longitude>MAX_LONGITUDE || Latitude<MIN_LATITUDE || Latitude>MAX_LATITUDE) {
		  throw new GPSException("value out of range");
	  }
	  this.timeStamp = timeStamp;
	  this.Longitude = Longitude;
	  this.Latitude = Latitude;
	  this.Elevation = Elevation;
	  
  } 
  //   TODO: Define getters for the fields
  public double getLongitude(){
	  return Longitude;
  }
  public double getLatitude() {
	  return Latitude;
  }
  public ZonedDateTime getTime() {
	  return timeStamp;
  }
  public double getElevation() {
	  return Elevation;
  }
  // TODO: Define a toString() method that meets requirements
  public String toString() {
	  double round_Longitude= (double)Math.round(Longitude*100000)/100000;
	  double round_Latitude = (double)Math.round(Latitude*100000)/100000;
	  return "("+round_Longitude+", "+round_Latitude+"), "+ Elevation +" m";
  }
  // Do not alter anything beneath this comment

  /**
   * Computes the great-circle distance or orthodromic distance between
   * two points on a spherical surface, using Vincenty's formula.
   *
   * @param p First point
   * @param q Second point
   * @return Distance between the points, in metres
   */
  
  public static double greatCircleDistance(Point p, Point q) {
    double phi1 = toRadians(p.getLatitude());
    double phi2 = toRadians(q.getLatitude());

    double lambda1 = toRadians(p.getLongitude());
    double lambda2 = toRadians(q.getLongitude());
    double delta = abs(lambda1 - lambda2);

    double firstTerm = cos(phi2)*sin(delta);
    double secondTerm = cos(phi1)*sin(phi2) - sin(phi1)*cos(phi2)*cos(delta);
    double top = sqrt(firstTerm*firstTerm + secondTerm*secondTerm);

    double bottom = sin(phi1)*sin(phi2) + cos(phi1)*cos(phi2)*cos(delta);

    return MEAN_EARTH_RADIUS * atan2(top, bottom);
  }
}
