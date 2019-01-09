package Gis;

import Coords.LatLonAlt;
import Geom.Point3D;

public class Fruit {

	Point3D gps;
	
	public Fruit(Point3D gps) {
		this.gps = gps;
	}
	  public Fruit(String line) { String[] arr = line.split(",");
	    
	    String p = arr[2] + "," + arr[3] + "," + arr[4];
	    gps = new Point3D(p);
	  }
	  

	public Point3D getGps() {
		return gps;
	}
	

}
