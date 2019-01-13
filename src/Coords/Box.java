package Coords;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Geom.Point3D;
/**
 * this class represents a "Box" , with min and max geom in the real world
 *
 */
public class Box {

	  private Point3D min;
	  private Point3D max;
	//constructors  
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
		  }

	public Point3D getMin() {
		return min;
	}
	public Point3D getMax() {
		return max;
	}
}
