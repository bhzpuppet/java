import java.awt.SecondaryLoop;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class Track {
	private Point[] aPoint = new Point[200];
	private int numberOfPoint = 0;
	public Track () {
		
	}
	public void readFile (String f) throws FileNotFoundException, GPSException {
		// clear data
		for (int i = 0; i<200; i++) {
			aPoint[i] = null;
		}
		numberOfPoint = 0;
		// readfile
		File file = new File(f);
		Scanner input = new Scanner(file);
		// delete first line
		String header = input.nextLine();
		while (input.hasNext()) {
			String s = input.nextLine();
			String[] p = s.split(",");
			if (p.length != 4) {
				throw new GPSException("bad data");
			}
//			Why use valueof
			Point p0 = new Point(ZonedDateTime.parse(p[0]), Double.valueOf(p[1]), Double.valueOf(p[2]), Double.valueOf(p[3]));
			this.add(p0);
		}
	}
	public void writeKML (String f) throws java.io.IOException, GPSException {
		java.io.File file = new File (f);
		// write KML file
		try (
			java.io.PrintWriter output = new java.io.PrintWriter (file);
		){
			
			output.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			output.println("<kml><Document>");
			output.println("<name>Paths</name>");
			output.println("<description>path in leeds</description> <Style id=\"yellowLineGreenPoly\">");
			output.println("<LineStyle><color>7f00ffff</color><width>4</width></LineStyle>");
			output.println("<PolyStyle><color>7f00ff00</color></PolyStyle> </Style> <Placemark>");
			output.println("<styleUrl>#yellowLineGreenPoly</styleUrl>");
			output.println("<LineString> <coordinates> ");
			for (int i = 0; i < numberOfPoint; i++){
				output.println(aPoint[i].getLongitude()+","+ aPoint[i].getLatitude()+","+aPoint[i].getElevation());
			}
			output.println("</coordinates></LineString></Placemark></Document></kml>");
			
		}
		
	}
	public void add(Point p) {
		aPoint[numberOfPoint] = p;
		numberOfPoint++;
	}
	public int size() {
		return numberOfPoint;
	}
	public Point get(int index) throws GPSException {
		// Index of point out of range
		if (index > numberOfPoint || index <0) {
			throw new GPSException ("Index of point out of range");
		}
		if (aPoint[index]==null) {
			throw new GPSException ("Index of point out of range");
		}
		return aPoint[index];
	}
	public Point lowestPoint () throws GPSException {
		if (numberOfPoint < 1) {  //exception
			throw new GPSException ("no enough points");
		}
		ZonedDateTime t1 = ZonedDateTime.parse("2016-02-17T09:52:39Z");
		Point min = new Point(t1, -1.547720, 53.803941, 10000);
		for (int x=0; x<numberOfPoint; x++) {
			if (aPoint[x].getElevation() < min.getElevation() ) {
				min = aPoint[x];
			}
		}
		return min;
	}
	public Point highestPoint () throws GPSException {
		if (numberOfPoint < 1) {  //exception
			throw new GPSException ("no enough points");
		}
		ZonedDateTime t1 = ZonedDateTime.parse("2016-02-17T09:52:39Z");
		Point max = new Point(t1, -1.547720, 53.803941, -10000);
		for (int x=0; x < numberOfPoint; x++) {
			if (aPoint[x].getElevation() > max.getElevation() ) {
				max = aPoint[x];
			}
		}
		return max;
	}
	public double totalDistance () throws GPSException {
		if (numberOfPoint < 2) { //exception
			throw new GPSException ("no enough points");
		}
		double totaldistance = 0;
		// add each distance of two adjacent points
		for (int x=0; x < numberOfPoint-1; x++) {
			totaldistance = totaldistance + Point.greatCircleDistance(aPoint[x], aPoint[x+1]);
		}
		return totaldistance;
	}
	public double averageSpeed () throws GPSException {
		if (numberOfPoint < 2) { //exception
			throw new GPSException ("no enough points");
		}
		double seconds = ChronoUnit.SECONDS.between(aPoint[0].getTime(), aPoint[numberOfPoint-1].getTime());
		return this.totalDistance()/seconds;
	}
}
