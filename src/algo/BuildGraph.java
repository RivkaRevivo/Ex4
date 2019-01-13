package algo;

import java.util.ArrayList;
import java.util.Iterator;

import Coords.Box;
import Geom.Point3D;
import Gis.Fruit;
import Gis.Packman;
import graph.Graph;
import graph.Node;
/**
 * this class build a Graph containing all the vertexes (fruits , edges of the boxes and the player)
 * @author Yuval Cohen and Amit Znaft and Rivka Revivo
 *
 */
public class BuildGraph {

	private Point3D UP = new Point3D(32.106046, 35.202574);   
	private Point3D Down =  new Point3D(32.101858, 35.212405);
	private ArrayList<Point3D> boxPoint = new ArrayList<Point3D>();
	private ArrayList<Box>boxes = new ArrayList<Box>();
	private ArrayList<Node> vertexes = new ArrayList<Node>();
	private Graph h = new Graph();
	private Packman p;
	/**
	 * this function gets an array of String from the server (playBoard) and save the boxes in arrayList, 
	 * save the Fruits in arrayList <Node> and add each Fruit and the player to the graph
	 * @param s = playBoard
	 */
	public void getCoords(ArrayList<String> s) {
		for (int i=0; i<s.size(); i++) {
			String a = s.get(i);
			if (a.startsWith("B")) {
				Box b = new Box(a);
				boxes.add(b);
			}
			else if (a.startsWith("F")) {
				Fruit fruit = new Fruit(a);
				Node node = new Node("F,"+fruit.getGps().x() + ","+fruit.getGps().y());
				h.add(node);
				vertexes.add(node);
			}
			else if (a.startsWith("M")) {
				p = new Packman(a);
				Node node = new Node("M,"+p.getGps().x() + ","+p.getGps().y());
				vertexes.add(node);
				h.add(node);
			}
		}
	}
	/**
	 * The function runs on the arrayList<Box> and checks for each vertex of a 
	 * box whether a vertex is inside another box or outside the map
	 * each valid vertex added to arrayList <Node> and to the graph
	 */
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

		for (Point3D point : boxPoint){
			Node node = new Node("B,"+point.x()+","+point.y());
			h.add(node);
			vertexes.add(node);
		}

	}
	/**
	 * this function gets point and box and check if the point is inside the box or outside the map
	 * @param q = point to check
	 * @param box = box we get
	 * @return true if the point is inside the box or outside the map
	 * else return false --> valid point 
	 */
	private boolean isIn2D(Point3D q , Box box) { 
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
	/**
	 * this function build edges between vertexes on the graph
	 */
	public void PointsInLine(){
		ArrayList<Point3D> toreturn = null;
		for (int i=0; i<vertexes.size(); i++) {
			Node a = vertexes.get(i);
			for (int j=0; j<vertexes.size(); j++) {
				Node b = vertexes.get(j);
				if (a.get_name().equals(b.get_name())) {
					continue;
				}
				else if (a.hasEdge(b.get_id())) {
					continue;
				}
				else {
					String [] s = a.get_name().split(",");
					String [] s2 = b.get_name().split(",");
					double d = Double.parseDouble(s[1]);
					double d2 = Double.parseDouble(s[2]);
					double d3 = Double.parseDouble(s2[1]);
					double d4 = Double.parseDouble(s2[2]);
					Point3D p = new Point3D (d,d2);
					Point3D p2 = new Point3D (d3,d4);
					toreturn = PointsInLine2(p,p2,7);
					boolean bo = isIn(toreturn, boxes);
					if (!bo) {
						h.addEdge(a.get_name(), b.get_name(), p.distance2D(p2));
					}
				}
			}
		}
	}
	/**
	 * this Recursive function gets 2 points and make new point in straight line between them in   
	 * @param p = first point
	 * @param p2 = second point
	 * @param size = Stop conditions for loop 
	 * @return new points in straight line between them 
	 */
	private ArrayList<Point3D> PointsInLine2(Point3D p, Point3D p2, int size){
		ArrayList<Point3D> toreturn = new ArrayList<Point3D>();
		if (size>0) {
			double x = ((p.x() + p2.x()) / 2 );
			double y = ((p.y() + p2.y()) / 2 );
			Point3D p3 = new Point3D(x,y);
			toreturn.add(p3);
			ArrayList<Point3D> toreturn1 = PointsInLine2(p3,p,size-1);
			ArrayList<Point3D> toreturn2 = PointsInLine2(p3,p2,size-1);
			for (int i=0; i<toreturn1.size(); i++) {
				toreturn.add(toreturn1.get(i));
			}
			for (int i=0; i<toreturn2.size(); i++) {
				toreturn.add(toreturn2.get(i));
			}
		}
		return toreturn;
	}
	/**
	 * this function is the same like the function "isIn2D" , for collection of points and boxes 
	 * @param p = ArrayList<Point>
	 * @param boxes = ArrayList<Box>
	 * @return true if one point is inside a box or outside the map
	 * else return false --> valid point 
	 */
	public boolean isIn(ArrayList<Point3D> p,ArrayList<Box> boxes) {
		boolean ans = false;
		for (Point3D point : p) {
			for(Box b : boxes) {
				if (isIn2D(point,b)) {
					return true;
				}
			}
		}
		return ans;
	}
	public Graph getGraph () {
		return this.h;

	}
	public String getPackName () {
		return "M,"+this.p.getGps().x() + ","+this.p.getGps().y();
	}
	public Point3D getPack () {
		return this.p.getGps();
	}
	/**
	 * this function gets vertex of box , check which edge the point is on 
	 * the box and move it a little bit out of the box
	 * @param b = vertex of box
	 * @return the new Point3D which is located a little bit out of the box
	 */
	public Point3D change (Point3D b) {
		for (Box box: boxes) {
			Point3D max_right = box.getMax();
			Point3D min_left = box.getMin();
			Point3D min_right = new Point3D(max_right.x(),min_left.y(),0);
			Point3D max_left = new Point3D(min_left.x(), max_right.y(),0);
			if (b.equalsXY(max_right)) {
				return new Point3D (b.x()+0.0002, b.y()+0.0002);
			}
			else if (b.equalsXY(min_right)) {
				return new Point3D (b.x()+0.0002, b.y()-0.0002);
			}
			else if (b.equalsXY(max_left)) {
				return new Point3D (b.x()-0.0002, b.y()+0.0002);
			}
			else if (b.equalsXY(min_left)) {
				return new Point3D (b.x()-0.0002, b.y()-0.0002);
			}
		}
		return b;
	}

	/**
	 * this function check if a fruit is eaten
	 * @param s = the play board
	 * @param node = a fruit on the game
	 * @return true if the fruit eaten, else false
	 */
	public boolean iseat (ArrayList<String> s , Point3D node ) {
		for (int i=0; i<s.size(); i++) {
			if (s.get(i).startsWith("F") ) {
					String [] g = s.get(i).split(",");
					double x = Double.parseDouble(g[1]);
					double y = Double.parseDouble(g[2]);
					if (node.x() == x && node.y() == y) {
						return true;
					}
		}
	}
	return false;
}
	/**
	 * this function check if there is more fruit on the game
	 * @param s = the play borad
	 * @return true if there is no more fruits on the game, else false
	 */
	public boolean ismorefruits (ArrayList<String> s) {
		for (int i=0; i<s.size(); i++) {
			if (s.get(i).startsWith("F")) {
				return true;
			}
		}
		return false;
	}
}
