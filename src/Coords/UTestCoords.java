package Coords;

import Geom.Point3D;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UTestCoords {
    private Point3D Point1;
    private Point3D Point2;
    private Point3D local_vector_in_meter; 
    MyCoords myCoords = new MyCoords();

    @Before
    public void setup() {
        Point1 = new Point3D(32.103315, 35.209039, 670);
        Point2 = new Point3D(32.106352, 35.205225, 650);
        local_vector_in_meter = new Point3D(337.6989920612881, -359.24920693881893, -20.0);
    }

    @Test
    public void addTest() {
        Point3D actual = myCoords.add(Point1, local_vector_in_meter);
        Point3D expected = new Point3D(Point2);
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testDistance3D() {
        double distance_asAppears_example = 493.457;
        double actual = myCoords.distance3d(Point1, Point2);
        Assert.assertEquals(distance_asAppears_example, actual, 0.02);
    }

    @Test
    public void azimuthElevationDistanceTest() {
        double[] azimuth_elevation_dist = myCoords.azimuth_elevation_dist(Point1, Point2);
        double[] expected = {313.2304, -2.324, 493.457};
        Assert.assertArrayEquals(expected, azimuth_elevation_dist, 0.02);
    }

    @Test
    public void testVector3D() {
        Point3D actual = myCoords.vector3D(Point1, Point2);
        Point3D expected = new Point3D(337.6989920612881, -359.24920693881893, -20.0);
        Assert.assertTrue(actual.equals(expected));
    }

    @Test
    public void testIsValid() {
        boolean expected = false;
        Assert.assertEquals(myCoords.isValid_GPS_Point(new Point3D("95,-194,1000")), expected);
    }

}