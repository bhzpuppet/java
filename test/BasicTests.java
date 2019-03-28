// Correctness testing for COMP1721 Coursework 1 (Basic Solution)

import java.io.FileNotFoundException;
import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;


public class BasicTests {

  private ZonedDateTime t1, t2, t3, t4;
  private Point p1, p2, p3, p4;
  private Track track1, track2;

  @Before
  public void perTestSetup() {
    t1 = ZonedDateTime.parse("2016-02-17T09:52:39Z");
    t2 = ZonedDateTime.parse("2016-02-17T09:53:31Z");
    t3 = ZonedDateTime.parse("2016-02-17T09:54:29Z");
    t4 = ZonedDateTime.parse("2016-02-17T09:55:31Z");

    p1 = new Point(t1, -1.547720, 53.803941, 69.8);
    p2 = new Point(t2, -1.548531, 53.804616, 72.5);
    p3 = new Point(t3, -1.549418, 53.805238, 68.1);
    p4 = new Point(t4, -1.550828, 53.805469, 70.5);

    track1 = new Track();

    track2 = new Track();
    track2.add(p1);
    track2.add(p2);
    track2.add(p3);
    track2.add(p4);
  }

  // Point class

  @Test
  public void time() {
    assertThat(p1.getTime(), is(t1));
    assertThat(p2.getTime(), is(t2));
  }

  @Test
  public void longitude() {
    assertThat(p1.getLongitude(), closeTo(-1.547720, 0.000001));
    assertThat(p2.getLongitude(), closeTo(-1.548531, 0.000001));
  }

  @Test
  public void latitude() {
    assertThat(p1.getLatitude(), closeTo(53.803941, 0.000001));
    assertThat(p2.getLatitude(), closeTo(53.804616, 0.000001));
  }

  @Test(expected=GPSException.class)
  public void longitudeTooLow() {
    new Point(t1, -180.5, 0.0, 0.0);
  }

  @Test(expected=GPSException.class)
  public void longitudeTooHigh() {
    new Point(t1, 180.5, 0.0, 0.0);
  }

  @Test(expected=GPSException.class)
  public void latitudeTooLow() {
    new Point(t1, 0.0, -90.5, 0.0);
  }

  @Test(expected=GPSException.class)
  public void latitudeTooHigh() {
    new Point(t1, 0.0, 90.5, 0.0);
  }

  @Test
  public void elevation() {
    assertThat(p1.getElevation(), closeTo(69.8, 0.000001));
    assertThat(p2.getElevation(), closeTo(72.5, 0.000001));
  }

  @Test
  public void pointAsString() {
    assertThat(p1.toString(), is("(-1.54772, 53.80394), 69.8 m"));
    assertThat(p2.toString(), is("(-1.54853, 53.80462), 72.5 m"));
  }

  // Track class

  @Test
  public void trackSize() {
    assertThat(track1.size(), is(0));
    assertThat(track2.size(), is(4));
  }

  @Test
  public void getPoint() {
    assertThat(track2.get(0), is(p1));
    assertThat(track2.get(1), is(p2));
    assertThat(track2.get(2), is(p3));
    assertThat(track2.get(3), is(p4));
  }

  @Test(expected=GPSException.class)
  public void getPointEmptyDataset() {
    track1.get(0);
  }

  @Test(expected=GPSException.class)
  public void pointIndexTooLow() {
    track2.get(-1);
  }

  @Test(expected=GPSException.class)
  public void pointIndexTooHigh() {
    track2.get(4);
  }

  @Test(expected=FileNotFoundException.class)
  public void readFileNotFound() throws FileNotFoundException {
    track1.readFile("this_file_does_not_exist");
  }

  @Test(expected=GPSException.class)
  public void readMissingData() throws FileNotFoundException {
    track1.readFile("../data/bad.csv");
  }

  @Test
  public void readFile() throws FileNotFoundException {
    track1.readFile("../data/test.csv");
    assertThat(track1.size(), is(4));
    assertThat(track1.get(0).getElevation(), closeTo(69.8, 0.000001));
    assertThat(track1.get(1).getElevation(), closeTo(72.5, 0.000001));
  }

  @Test
  public void readClearOldData() throws FileNotFoundException {
    track2.readFile("../data/test.csv");
    assertThat(track2.size(), is(4));
  }

  // main

  public static void main(String[] args) {
    JUnitCore core = new JUnitCore();
    core.addListener(new TestReporter());
    Result result = core.run(BasicTests.class);
    System.exit(result.getFailureCount());
  }
}
