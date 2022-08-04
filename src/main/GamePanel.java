package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.desktop.ScreenSleepEvent;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	//SCREEN SETTINGS
	final int originalTileSize = 16;// 16*16
	final int scale = 3;//16 is too small. 16*3 is ok
	
	public final int tileSize = originalTileSize*scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth= tileSize*maxScreenCol;//768 pixels
	public final int screenHeight = tileSize*maxScreenRow;//576 pixels
	
	//world settings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth= tileSize*maxWorldCol;
	public final int worldHeight = tileSize*maxWorldRow;
	// frame per second.
	//if we don't set it, the thread runs too fast->press a key and player go out of the panel
	int FPS = 60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public Player player = new Player(keyH, this);
	TileManager tileManager = new TileManager(this);
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);//so that the game has better performance		
		this.addKeyListener(keyH);
		this.setFocusable(true);//the panel can be focused to receive key event
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	@Override
	public void run() {
		while(gameThread != null) {
			double drawInterval = 1000_000_000/FPS;
			double nextDrawTime = System.nanoTime()+drawInterval;
			
			update();
			
			repaint();//call paintComponent;
			
			try {
				double remainingTime = (nextDrawTime - System.nanoTime())/1000_000;
				if(remainingTime < 0)
					remainingTime = 0;
				Thread.sleep((long) remainingTime);
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}
//	@Override
//	public void run() {
//		double drawInterval = 1000_000_000/FPS;
//		double lastTime = System.nanoTime();
//		double delta = 0;
//		double currentTime;
//		while(gameThread != null) {
//			currentTime = System.nanoTime();
//			delta += (currentTime - lastTime)/drawInterval;
//			lastTime = currentTime;
//			if(delta >= 1) {
//				update();
//				repaint();
//				delta--;				
//			}
//		}
//	}
	
	public void update() {
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);// superclass of me(in our case Jpanel) paints sth.
		Graphics2D g2 = (Graphics2D)g;// Graphics2D is child of Graphics
		tileManager.draw(g2);//must paint tile before player.
		player.draw(g2);
		g2.dispose();
	}
}
