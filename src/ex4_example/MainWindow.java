package ex4_example;

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

import Coords.GeoBox;
import Coords.LatLonAlt;
import Coords.Map;
import Geom.Point3D;
import Robot.Fruit;
import Robot.Game;
import Robot.Packman;
import Robot.Play;


public class MainWindow extends JFrame implements MouseListener, Runnable {

	//	private static final long serialVersionUID = 1L;
	String file_name;
	Play play1 = null;
	public BufferedImage myImage;
	public BufferedImage ImagePackman;
	public BufferedImage ImageFruit;
	public BufferedImage ImageGhosts;
	public BufferedImage ImageMy;
	Game game = null;
	String map_name = "Ariel1.png";
	double lat = 32.103813D;
	double lon = 35.207357D;
	double alt = 0.0D;
	double dx = 955.5D;
	double dy = 421.0D;
	LatLonAlt cen = new LatLonAlt(lat, lon, alt);
	boolean b= false;
	ArrayList<String> board_data;



	public MainWindow() 
	{
		initGUI();		
		this.addMouseListener(this); 
	}
	private void initGUI() {
		MenuBar menuBar = new MenuBar();
		Menu menu2 = new Menu("Input"); 
		MenuItem item4 = new MenuItem("csv file");
		MenuItem item5 = new MenuItem("run");
		menuBar.add(menu2);
		menu2.add(item4);
		menu2.add(item5);
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
						game = new Game (file.getName());
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
		Map map = new Map(cen, dx, dy, map_name);
		if (b) {
			//			for (int i=0; i<game.sizeT(); i++) {
			//				Fruit target = game.getTarget(i);
			//				Point3D ans = map.world2frame((LatLonAlt) target.getGeom());
			//				g.drawImage(ImageFruit, ans.ix(), ans.iy(),30,30, this);
			//			}
			//			for (int i=0; i<game.sizeR(); i++) {
			//				Packman a = game.getPackman(i);
			//				Point3D ans = map.world2frame((LatLonAlt) a.getGeom());
			//				g.drawImage(ImagePackman, ans.ix(), ans.iy(),30,30, this);
			//			}
			//			for (int i=0; i<game.sizeG(); i++) {
			//				Packman a = game.getGhosts(i);
			//				Point3D ans = map.world2frame((LatLonAlt) a.getGeom());
			//				g.drawImage(ImageGhosts, ans.ix(), ans.iy(),30,30, this);
			//			}
			board_data = play1.getBoard();
			Iterator<String> it = board_data.iterator();
//			for (int i=0; i<board_data.size(); i++) {
			while (it.hasNext()) {
//				String line = board_data.get(i);
				String line = it.next();
				if (line.startsWith("P")) {
					Packman p = new Packman(line);
					Point3D ans = map.world2frame((LatLonAlt) p.getGeom());
					g.drawImage(ImagePackman, ans.ix(), ans.iy(),30,30, this);
				}
				else if (line.startsWith("F")) {
					Fruit f = new Fruit(line);
					Point3D ans = map.world2frame((LatLonAlt) f.getGeom());
					g.drawImage(ImageFruit, ans.ix(), ans.iy(),30,30, this);	
				}
				else if (line.startsWith("G")) {
					Packman p = new Packman(line);
					Point3D ans = map.world2frame((LatLonAlt) p.getGeom());
					g.drawImage(ImageGhosts, ans.ix(), ans.iy(),30,30, this);
				}
				else if (line.startsWith("B")) {
					GeoBox b = new GeoBox(line);
					Point3D min = map.world2frame(b.getMin());
					Point3D max = map.world2frame(b.getMax());
					int wight = max.ix() - min.ix();
					int hight = min.iy() -max.iy();
					g.fillRect(min.ix(), max.iy(),wight, hight);
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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
		play1.setIDs(1111,2222,3333);
		play1.setInitLocation(32.1040,35.2061);
		play1.start();
		int i=0;
		while (play1.isRuning()) {
			i++;
			play1.rotate(36*i); 
//			System.out.println("***** "+i+"******");
//			String info = play1.getStatistics();
//			System.out.println(info);
			try {
				Thread.sleep(350);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
		
		System.out.println("**** Done Game (user stop) ****");
		String info = play1.getStatistics();
		System.out.println(info);
	}



}
