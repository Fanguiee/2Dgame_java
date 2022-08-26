package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.desktop.ScreenSleepEvent;

import javax.swing.JPanel;

import entity.Player;
import object.superObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	//SCREEN SETTINGS
	final int originalTileSize = 16;// 16*16
	final int scale = 3;//16 is too small. 16*3 is ok
	
	public final int tileSize = originalTileSize*scale;
	public final int maxScreenCol = 8;
	public final int maxScreenRow = 6;
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
	
	//System
	KeyHandler keyH = new KeyHandler();
	TileManager tileManager = new TileManager(this);
	Sound music = new Sound();
	Sound soundEffect = new Sound();
	public CollisionChecker collisionChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread;
	
	//entity and objects
	public Player player = new Player(keyH, this);
	public superObject obj[] = new superObject[10];
	
	//states
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);//so that the game has better performance		
		this.addKeyListener(keyH);
		this.setFocusable(true);//the panel can be focused to receive key event
	}
	
	public void setupGame() {
		aSetter.setObject();
		playMusic(0);
		gameState = playState;
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
		
		//debug
		long drawStart = 0, drawEnd = 0;
		if(keyH.checkDrawTime == true) {
			drawStart = System.nanoTime();			
		}
		tileManager.draw(g2);//must paint tile before player.
		//draw objects
		for(int i= 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		player.draw(g2);
		ui.draw(g2);
		
		//debug
		if(keyH.checkDrawTime == true) {
			drawEnd = System.nanoTime();
			long timeToDraw = drawEnd - drawStart;
			g2.drawString("TimeToDraw: "+ timeToDraw, tileSize*5, tileSize*2);
			System.out.println("TimeToDraw: "+ timeToDraw);
		}
		
		g2.dispose();
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
		
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSoundEffect(int i) {
		soundEffect.setFile(i);
		soundEffect.play();
	}
}
