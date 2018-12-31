package Gis;

import Geom.Point3D;

public class Map {

	private Point3D UP = new Point3D(32.106046, 35.202574);
	private Point3D Down =  new Point3D(32.101858, 35.212405);
	private int Width = 1433 , Height = 642;  
	
	
	public Point3D pointToPixels(Point3D pointToPix) {
		double mapLatDiff = UP.x() - Down.x();
		double mapLongDiff = Down.y() - UP.y();

		double latDiff = UP.x() - pointToPix.x();
		double longDiff = pointToPix.y() - UP.y();

		int x = (int) (Width*(longDiff/mapLongDiff));
		int y = (int) (Height*(latDiff/mapLatDiff));

		return new Point3D(x, y);
	}
	public Point3D pixelsToPoint(Point3D Pixel) {
		double mapLatDiff = UP.x() - Down.x();
		double mapLongDiff = Down.y() - UP.y();

		double latDiff = Pixel.y() * mapLatDiff/Height;
		double longDiff = Pixel.x() * mapLongDiff/Width;

		double newLat = UP.x() - latDiff;
		double newLong = UP.y() + longDiff;

		return new Point3D(newLat, newLong);
	}
	
}
