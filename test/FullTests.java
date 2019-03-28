// Class correctness testing for COMP1721 Coursework 1 (Full Solution)

import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;


public class FullTests {

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

  // Track class

  @Test
  public void lowestPoint() {
    assertThat(track2.lowestPoint(), is(p3));
  }

  @Test(expected=GPSException.class)
  public void lowestPointNotEnoughData() {
    track1.lowestPoint();
  }

  @Test
  public void highestPoint() {
    assertThat(track2.highestPoint(), is(p2));
  }

  @Test(expected=GPSException.class)
  public void highestPointNotEnoughData() {
    track1.highestPoint();
  }

  @Test
  public void totalDistance() {
    assertThat(track2.totalDistance(), closeTo(278.53495, 0.00001));
  }

  @Test(expected=GPSException.class)
  public void totalDistanceNotEnoughData() {
    Track t = new Track();
    t.add(p1);
    t.totalDistance();
  }

  @Test
  public void averageSpeed() {
    assertThat(track2.averageSpeed(), closeTo(1.61939, 0.00001));
  }

  @Test(expected=GPSException.class)
  public void averageSpeedNotEnoughData() {
    Track t = new Track();
    t.add(p1);
    t.averageSpeed();
  }

  // main

  public static void main(String[] args) {
    JUnitCore core = new JUnitCore();
    core.addListener(new TestReporter());
    Result result = core.run(FullTests.class);
    System.exit(result.getFailureCount());
  }
}
