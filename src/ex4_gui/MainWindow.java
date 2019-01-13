package ex4_gui;

import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import Coords.Box;
import Coords.MyCoords;
import Geom.Point3D;
import Gis.Fruit;
import Gis.Map;
import Gis.Packman;


import Robot.Play;
import algo.BuildGraph;
import graph.Graph_Algo;
import graph.Node;


public class MainWindow extends JFrame implements MouseListener, Runnable {

	private static final long serialVersionUID = 1L;
	String file_name;
	Play play1 = null;
	public BufferedImage myImage;
	public BufferedImage ImagePackman;
	public BufferedImage ImageFruit;
	public BufferedImage ImageGhosts;
	public BufferedImage ImageMy;
	String map_name = "Ariel1.png";
	double lat = 32.103813D;
	double lon = 35.207357D;
	double alt = 0.0D;
	double dx = 955.5D;
	double dy = 421.0D;
	Point3D cen = new Point3D(lat, lon, alt);
	boolean b= false;
	ArrayList<String> board_data;
	boolean bb = true;
	Packman myPac = null;
	boolean u = true;
	double [] arr = {0,0,0};



	public MainWindow() 
	{
		initGUI();		
		this.addMouseListener(this); 
	}
	private void initGUI() {
		MenuBar menuBar = new MenuBar();
		Menu menu2 = new Menu("Input"); 
		MenuItem item4 = new MenuItem("csv file");
		MenuItem item5 = new MenuItem("run automatic");
		MenuItem item6 = new MenuItem("run by mouse");
		menuBar.add(menu2);
		menu2.add(item4);
		menu2.add(item5);
		menu2.add(item6);
		this.setMenuBar(menuBar);
		item4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter( "CSV file","csv");
				fc.setFileFilter(filter);
				fc.setAcceptAllFileFilterUsed(false);
				if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						file_name = file.getName();
						play1 = new Play (file_name);
						b = true;
						Scanner input = new Scanner(file);
						input.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				repaint();
			}
		});
		MainWindow temp = this;
		item5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread t = new Thread(temp);
				t.start();
			}});
		item6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread t = new Thread(temp);
				t.start();
				u = false;
			}});
		try {
			myImage = ImageIO.read(new File("Ariel1.png"));
			ImagePackman= ImageIO.read(new File("packman.png"));
			ImageFruit =ImageIO.read(new File("Fruit.png"));
			ImageGhosts = ImageIO.read(new File("ghost.png"));
			ImageMy = ImageIO.read(new File("pig.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public void paint(Graphics g)
	{
		g.drawImage(myImage, 0, 0,this.getWidth(), this.getHeight(), this);
		//	Map map = new Map(cen, dx, dy, map_name);
		Map map = new Map();
		if(x != -1 && y != -1) {
			if(button==1) {
				bb = false;
				g.drawImage(ImageMy, x, y,30,30, this);
				Point3D pix = new Point3D(x,y); 
				Point3D PixToPoint = map.pixelsToPoint(pix);
				myPac = new Packman(PixToPoint);
				button = -1;
				play1.setInitLocation(myPac.getGps().x(),myPac.getGps().y());
			}
		}
		if (b) {
			board_data = play1.getBoard();
			Iterator<String> it = board_data.iterator();
			while (it.hasNext()) {
				String line = it.next();
				if (line.startsWith("P")) {
					Packman p = new Packman(line);
					Point3D ans = map.pointToPixels(p.getGps());
					g.drawImage(ImagePackman, ans.ix(), ans.iy(),30,30, this);
				}
				else if (line.startsWith("F")) {
					Fruit f = new Fruit(line);
					Point3D ans = map.pointToPixels( f.getGps());
					g.drawImage(ImageFruit, ans.ix(), ans.iy(),30,30, this);	
				}
				else if (line.startsWith("G")) {
					Packman p = new Packman(line);
					Point3D ans = map.pointToPixels(p.getGps());
					g.drawImage(ImageGhosts, ans.ix(), ans.iy(),30,30, this);
				}
				else if (line.startsWith("B")) {
					Box b = new Box(line);
					Point3D min = map.pointToPixels(b.getMin());
					Point3D max = map.pointToPixels(b.getMax());
					int wight = max.ix() - min.ix();
					int hight = min.iy() -max.iy();
					g.fillRect(min.ix(), max.iy(),wight, hight);
				}
				else if (line.startsWith("M")&& ! bb) {
					myPac = new Packman(line);
					Point3D ans = map.pointToPixels(myPac.getGps());
					g.drawImage(ImageMy, ans.ix(), ans.iy(),30,30, this);
				}
			}
		}

	}
	int x = -1, y= -1 , button = 0;
	@Override
	public void mouseClicked(MouseEvent arg) {
		Map map = new Map();
		MyCoords my = new MyCoords();
		if(bb) {
			button = arg.getButton();
			x = arg.getX();
			y = arg.getY();
			repaint(x,y,30,30);
		}
		else {
			x = arg.getX();
			y = arg.getY();
			Point3D pix = new Point3D(x,y); 
			Point3D PointToPix = map.pixelsToPoint(pix);
			arr = my.azimuth_elevation_dist(PointToPix, myPac.getGps());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void run() {
		if (!u) {
			play1.setIDs(205478431,311483432);
			play1.start();
			while (play1.isRuning()) {
				play1.rotate(arr[0]);
				//			System.out.println("***** "+i+"******");
				//			String info = play1.getStatistics();
				//			System.out.println(info);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				repaint();
			}

			System.out.println("**** Done Game (user stop) ****");
			String info = play1.getStatistics();
			System.out.println(info);
			System.out.println();
			Sql.getsql();
		}
		else {
			play1.setIDs(205478431,311483432,208736827);
			play1.start();
			boolean ismorefruits = true;
			while (play1.isRuning() && ismorefruits) {
				BuildGraph b = new BuildGraph();
				b.getCoords(play1.getBoard());
				b.vertex();
				b.PointsInLine();
				int index = 0;
				double min = Double.MAX_VALUE;
				Graph_Algo.dijkstra(b.getGraph(), b.getPackName() );
				for (int i=0; i<b.getGraph().size(); i++) {
					Node a = b.getGraph().getNodeByIndex(i);
					if (a.get_name().startsWith("F")) {
						double dis = a.getDist();
						if (dis < min ) {
							min = dis;
							index = i;
						}
					}
				}
				ArrayList<String> arrs = b.getGraph().getNodeByIndex(index).getPath();
				arrs.add(b.getGraph().getNodeByIndex(index).get_name());
				MyCoords my = new MyCoords();
				for (int i=1; i<arrs.size(); i++) {
					if (!b.ismorefruits(play1.getBoard())) {
						ismorefruits = false;
						break;
					}
					boolean fruitEaten = false;
					String node = arrs.get(i);
					String [] g = node.split(",");
					double x = Double.parseDouble(g[1]);
					double y = Double.parseDouble(g[2]);
					Point3D point = new Point3D (x,y);
					if (node.startsWith("B")) {
						point = b.change(point);
					}
					boolean t = true;
					Packman pack = new Packman (play1.getBoard().get(0));
					double arr[] = my.azimuth_elevation_dist(point, pack.getGps());
					while (t) {
						play1.rotate(arr[0]);
						pack = new Packman (play1.getBoard().get(0));
						arr = my.azimuth_elevation_dist(point, pack.getGps());
						if(!b.iseat(play1.getBoard(), point) && node.startsWith("F")) {
							t= false;
							fruitEaten = true;
						}
						else if (arr[2] < 1 ) {
							t =false;
						}
						if (!b.ismorefruits(play1.getBoard())) {
							ismorefruits = false;
							break;
						}
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						repaint();
					}
					if(fruitEaten)
						break;
				}
			}
			System.out.println("**** Done Game (user stop) ****");
			String info = play1.getStatistics();
			System.out.println(info);
			System.out.println();
			Sql.getsql();
		}
	}
}
