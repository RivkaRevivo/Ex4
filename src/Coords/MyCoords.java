package Coords;

import Geom.Point3D;
/**
 * this class calculate distance azimuth ,elevation ,between 2 Point3D coordinates and Point3D coordinate and Point3D meters 
 * @author User
 *
 */
public class MyCoords implements coords_converter {
	int radius = 6371000;
	
	public double distance3d(Point3D gps0, Point3D gps1) {
		double LonNorm = Math.cos(gps0.x() * Math.PI / 180);
		double diffLat = gps1.x() - gps0.x();
		double diffLon = gps1.y() - gps0.y();
		double diffalt = gps1.z() - gps0.z();
		
		double radLat = Math.toRadians(diffLat);
		double radLon = Math.toRadians(diffLon);
		
		double meterLat = Math.sin(radLat) * radius;
		double meterLon = Math.sin(radLon) * radius * LonNorm;
		
		double xy = Math.sqrt(meterLat*meterLat + meterLon*meterLon);
		double meter = Math.sqrt(xy*xy + diffalt*diffalt);
		
		return meter;
			
	}
	
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		double LonNorm = Math.cos(gps.x() * Math.PI / 180);
		
		double xrad = local_vector_in_meter.x() / radius;
		xrad = Math.asin(xrad);
		double x = Math.toDegrees(xrad);
		double yrad = local_vector_in_meter.y() / radius / LonNorm;
		yrad = Math.asin(yrad);
		double y = Math.toDegrees(yrad);
		
	
		Point3D r = new Point3D(gps.x()+x, gps.y()+y, gps.z()+local_vector_in_meter.z());
		return r;
		
	}
	
	
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
		double LonNorm = Math.cos(gps0.x() * Math.PI / 180);
		
		double diffLat = gps1.x() - gps0.x();
		double diffLon = gps1.y() - gps0.y();
		double diffAlt = gps1.z() - gps0.z();
		
		double radLat = Math.toRadians(diffLat);
		double radLon = Math.toRadians(diffLon);
		
		double meterLat = Math.sin(radLat) * radius;
		double meterLon = Math.sin(radLon) * radius * LonNorm;
		
		Point3D r = new Point3D(meterLat,meterLon,diffAlt);
		return r;
		
	}

	
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
		double [] azimuth_elevation_dist = new double[3];
		azimuth_elevation_dist [0] =Azimuth(gps0, gps1);
		azimuth_elevation_dist [1] = Math.toDegrees((gps1.z() - gps0.z()) /distance3d(gps0,gps1) -  distance3d(gps0,gps1)/(2*radius) );
		azimuth_elevation_dist [2] = distance3d(gps0,gps1);
		return azimuth_elevation_dist;
	}
	
	
	public boolean isValid_GPS_Point(Point3D p) {
		if (p.x() < -90 || p.x() > 90 || p.y() < -180 || p.y() > 180 ||  p.z() < -450 ) { return false ;}
		return true;
	}
	
	private double Azimuth(Point3D gps0, Point3D gps1) { 
		double diffLon = gps1.y() - gps0.y();   
		
		double a = Math.sin(Math.toRadians(diffLon)) * Math.cos(Math.toRadians(gps1.x()));
		double b = Math.cos(Math.toRadians(gps0.x())) * Math.sin(Math.toRadians(gps1.x())) - 
				(Math.sin(Math.toRadians(gps0.x())) * Math.cos(Math.toRadians(gps1.x())) * Math.cos(Math.toRadians(diffLon)));
		
		double c =  Math.toDegrees(Math.atan2(a, b))+180;
		if (c<0) {
			return c +360;
		}
		return c;
	}
}
