import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.time.ZonedDateTime;

public class Track {
	private Point[] aPoint = new Point[1000];
	private int numberOfPoint = 0;
	private int currentIndex = 0;
	public Track () {
		
	}
	public void readFile (String f) throws FileNotFoundException, GPSException {
		// Çå¿ÕÊı¾İ
		//aPoint[0] = null;  //????????????????????????
		numberOfPoint = 0;
		currentIndex = 0;
		File file = new File(f);
		Scanner input = new Scanner(file);
		String header = input.nextLine();
		while (input.hasNext()) {
			String s = input.nextLine();
			String[] p = s.split(",");
			if (p.length != 4) {
				throw new GPSException("bad data");
			}
//			ZonedDateTime timeStamp = ZonedDateTime.parse(p[0]);
			Point p0 = new Point(ZonedDateTime.parse(p[0]), Double.valueOf(p[1]), Double.valueOf(p[2]), Double.valueOf(p[3]));
			this.add(p0);
		}
	}
	public void add(Point p) {
		
		aPoint[currentIndex] = p;
		currentIndex ++;
		numberOfPoint++;
	}
	public int size() {
		return numberOfPoint;
	}
	public Point get(int index) throws GPSException {
		if (index>3 || index <0) {
			throw new GPSException ("Index of point out of range");
		}
		if (aPoint[index]==null) {
			throw new GPSException ("Index of point out of range");
		}
		return aPoint[index];
	}
}
