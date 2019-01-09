package algo;

import java.util.ArrayList;
import java.util.Iterator;

import Coords.Box;
import Coords.LatLonAlt;
import Geom.Point3D;
import Robot.Game;

public class BuildGraph {

	private Point3D UP = new Point3D(32.106046, 35.202574);   
	private Point3D Down =  new Point3D(32.101858, 35.212405);
	private Game game;
	private ArrayList<Point3D> boxPoint = new ArrayList<Point3D>();
	private ArrayList<Box>boxes = new ArrayList<Box>();

	public void getBoxCoords(Game game){

		for(int i = 0; i < game.sizeB(); i++) {
			LatLonAlt max = game.getBox(i).getMax();
			Point3D  P = new Point3D(max.lat(), max.lon(),0);
			LatLonAlt min = game.getBox(i).getMin();
			Point3D  P1 = new Point3D(min.lat(), min.lon(),0);
			Box b = new Box(P,P1);
			boxes.add(b);
		}	
	}
	public void vertex(){
		ArrayList<Point3D> c = new ArrayList<Point3D>();

		for(int i = 0 ; i < boxes.size(); i++) {

			Box b = boxes.get(i);
			Point3D max = b.getMax();
			Point3D min = b.getMin();
			Point3D max2 = new Point3D(max.x(),min.y(),0);
			Point3D min2 = new Point3D(min.x(), max.y(),0);
			for (int j = 0; j < boxes.size(); j++) {
				Box box = boxes.get(j);
				if(b == box) {
					continue;
				}
				boolean bol = isIn2D(max, box);
				boolean bol1 = isIn2D(max2, box);
				boolean bol2 = isIn2D(min, box);
				boolean bol3 = isIn2D(min2, box);
				if(bol) {c.add(max);}
				if(bol1) {c.add(max2);}
				if(bol2) {c.add(min);}
				if(bol3) {c.add(min2);}
			}
		}
		for(int i = 0 ; i < boxes.size(); i++) {

			Box b = boxes.get(i);
			Point3D max = b.getMax();
			Point3D min = b.getMin();
			Point3D max2 = new Point3D(max.x(),min.y(),0);
			Point3D min2 = new Point3D(min.x(), max.y(),0);
			boolean bolMax = true;
			boolean bolMax2 = true;
			boolean bolMin = true;
			boolean bolMin2 = true;
			Iterator<Point3D> d = c.iterator();
			while(d.hasNext()) {
				Point3D current = d.next();
				if(current.equals(max)) {
					bolMax = false;
				}
				if(current.equals(max2)) {
					bolMax2 = false;
				}
				if(current.equals(min)) {
					bolMin = false;
				}
				if(current.equals(min2)) {
					bolMin2 = false;
				}
			}
			if(bolMax) {boxPoint.add(max);}
			if(bolMax2) {boxPoint.add(max2);}
			if(bolMin) {boxPoint.add(min);}
			if(bolMin2) {boxPoint.add(min2);}
		}

	}
	public boolean isIn2D(Point3D q , Box box) { 
		boolean ans = false;

		if ((q.x() > box.getMin().x()) && (q.y() > box.getMin().y()) && (q.x() < box.getMax().x()) && (q.y() < box.getMax().y())) {
			ans = true;
		}
		if(q.x() < Down.x() || (q.y() > Down.y() || q.x() > UP.x()) || q.y() < UP.y()) {
			ans = true;
		}

		return ans;
	}
	public ArrayList<Point3D> getBoxPoint() {
		return boxPoint;
	}
	public ArrayList<Box> getBoxes() {
		return boxes;
	}

}
