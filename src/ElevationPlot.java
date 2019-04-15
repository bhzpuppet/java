import java.io.FileNotFoundException;
import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.stage.Stage; 
import javafx.scene.chart.LineChart; 
import javafx.scene.chart.NumberAxis; 
import javafx.scene.chart.XYChart; 

         

public class ElevationPlot extends Application { 

   @Override 

   public void start(Stage stage) throws FileNotFoundException, GPSException{
	  //read data
	   Track t = new Track();
	  t.readFile("../data/walk.csv");
	  
      //Defining the x axis             
      NumberAxis xAxis = new NumberAxis(0, 2000, 100); 
      xAxis.setLabel("Distance (m)"); 
        
      //Defining the y axis   

      NumberAxis yAxis = new NumberAxis(20, 80, 5); 
      yAxis.setLabel("Elevation (m)"); 
     
      //Creating the line chart 
      LineChart linechart = new LineChart(xAxis, yAxis);  
        
      //Prepare XYChart.Series objects by setting data 
      XYChart.Series series = new XYChart.Series(); 
      series.setName("Walk.csv"); 
      
	  //Setting data
      series.getData().add(new XYChart.Data(0, t.get(0).getElevation()));
	  for(int i = 1; i < t.size(); i = i+1) {
		  double dis = 0;
		  for(int k = 0; k < i; k = k+1) {
			  dis = dis + Point.greatCircleDistance(t.get(k), t.get(k+1));
		  }
	         double ele = t.get(i).getElevation();
	         series.getData().add(new XYChart.Data(dis, ele));
	  }
	  
           
      //Setting the data to Line chart    
      linechart.getData().add(series);        
       
      //Creating a Group object  
      Group root = new Group(linechart);          

      //Creating a scene object 
      Scene scene = new Scene(root, 600, 400);  
      
      //Setting title to the Stage 
      stage.setTitle("Elevation Plot"); 
         
      //Adding scene to the stage 
      stage.setScene(scene);	   

      //Displaying the contents of the stage 
      stage.show();         

   } 

   public static void main(String args[]) throws FileNotFoundException, GPSException{ 
	   
      launch(args); 

   } 

}
