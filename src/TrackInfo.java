import java.io.FileNotFoundException;

public class TrackInfo {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("please enter the file name after the TrackInfo");
			System.exit(0);
		}
		else {
			Track t = new Track();
			try {
				t.readFile("../data/"+args[0]);
				System.out.println(t.size()+" points in track");
				System.out.println("Lowest point is (" + String.format("%.5f", t.lowestPoint().getLongitude()) + ", " + String.format("%.5f", t.lowestPoint().getLatitude()) + "), " + t.lowestPoint().getElevation() +" m");
				System.out.println("Highest point is (" + String.format("%.5f", t.highestPoint().getLongitude()) + ", " + String.format("%.5f", t.highestPoint().getLatitude()) +"), " + t.highestPoint().getElevation() +" m");
				System.out.println("Total distance = " + String.format("%.3f", t.totalDistance()/1000) + " km");
				System.out.println("Average speed = " + String.format("%.3f", t.averageSpeed ()) + " m/s");
				t.writeKML("Path.kml");
			} 
			catch (FileNotFoundException e) {
				System.out.println(e.toString());
				System.exit(0);
			}
			catch (GPSException e) {
				System.out.println(e.toString());
				System.exit(0);
			}
			catch (java.io.IOException e) {
				System.out.println(e.toString());
				System.exit(0);
			}
		}
		
	}
}
