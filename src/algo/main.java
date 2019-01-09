package algo;

import java.util.ArrayList;
import java.util.Iterator;

import Coords.Box;
import Geom.Point3D;
import Robot.Game;
import Robot.Play;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
       Play play1 = new Play("Ex4_OOP_example9.csv");
       Game game = new Game("Ex4_OOP_example9.csv");
       BuildGraph build = new BuildGraph();
       build.getBoxCoords(game);
	   Iterator <Box> b = build.getBoxes().iterator();
	//   while(b.hasNext()) {
		//   System.out.println(b.next());
	  // }
	   build.vertex();
	   Iterator<Point3D> p = build.getBoxPoint().iterator();
	 int i = 0;
	   while(p.hasNext()) {
		   System.out.println(p.next() + "              "+ i);
		   i++;
	   }
	   System.out.println(build.getBoxPoint().size());
	   for(Point3D deleteIfDuplicate:build.getBoxPoint()) {
		   for(Point3D p2 : build.getBoxPoint()) {
			   if(deleteIfDuplicate!=p && deleteIfDuplicate.equals(p)) {
				   build.getBoxPoint().remove(p);
			   }
		   }
	   }
	   System.out.println(build.getBoxPoint().size());
//		Point3D p = new Point3D(32.3928, 35.392819, 0);
//        Point3D p1 = new Point3D(32.394, 35.39291, 0);
//        Point3D p3 = new Point3D(32.3934, 35.3923428, 0);
//        Point3D p4 = new Point3D(32.3324829, 35.3924392, 0);
//		
//        Box b = new Box(p,p1);
//		Box b1 = new Box(p4,p3);
		
		
	}
}
