package Gis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Coords.LatLonAlt;
import Geom.Point3D;
/**
 * this class represent "fruit" , with Geom in real world
 *
 */
public class Fruit {

	Point3D gps;
	//Constructor
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
	public BufferedImage getfruitImage() throws IOException {
		return ImageIO.read(new File("Fruit.png"));
	}

}
