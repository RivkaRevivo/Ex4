package Coords;

import java.util.ArrayList;

import Geom.Point3D;

public class Box {

	  private Point3D min;
	  private Point3D max;
	  
	  public Box(Point3D max, Point3D min) { 
		 this.min = min;
		 this.max = max;
		  }
	  public Box(String line) {
		    String[] arr = line.split(",");
		    String p = arr[2] + "," + arr[3] + "," + arr[4];
		    min = new Point3D(p);
		    p = arr[5] + "," + arr[6] + "," + arr[7];
		    max = new Point3D(p);
		    Box box = new Box(max,min); 
		  }

	public boolean isIn2D(Point3D q) { boolean ans = false;
	    if ((q.x() > min.x()) && (q.y() > min.y()) && (q.x() < max.x()) && (q.y() < max.y())) {
	      ans = true;
	    }
	    return ans;
	  }

	public Point3D getMin() {
		return min;
	}



	public void setMin(Point3D min) {
		this.min = min;
	}



	public Point3D getMax() {
		return max;
	}



	public void setMax(Point3D max) {
		this.max = max;
	}
}
