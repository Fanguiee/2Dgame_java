package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
	GamePanel gp;
	Font font_20 = new Font("Arial", Font.PLAIN, 20);
	Font font_45B = new Font("Arial", Font.BOLD, 45);
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	BufferedImage keyImage;
	public UI(GamePanel gp) {
		this.gp = gp;
		OBJ_Key key= new OBJ_Key(gp);
		keyImage = key.image;
	}
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	public void draw(Graphics2D g2) {
		//treasure found
		if(gameFinished == true) {
			g2.setColor(Color.magenta);
			g2.setFont(font_45B);
			g2.drawString("Congratulations!", gp.tileSize - 10, gp.tileSize*5);
			gp.gameThread = null;
		}
		else {
			//CAUTION: dont instantiate in game loop, it takes time
			//g2.setFont(new Font("Arial", Font.PLAIN, 40));
			g2.setFont(font_20);
			g2.setColor(Color.white);
			g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2,gp.tileSize,gp.tileSize, null);
			g2.drawString("x " + gp.player.hasKey, 72, 65);//literal
			
			//time
			playTime += (double)1/60;
			g2.drawString("Time: "+dFormat.format(playTime), gp.tileSize*(gp.maxScreenCol-2), gp.tileSize*(gp.maxScreenRow - 3));
			//message
			if(messageOn == true) {
				g2.setColor(Color.pink);
				g2.setFont(g2.getFont().deriveFont(15F));
				g2.drawString(message, gp.tileSize, gp.tileSize*4);
				
				messageCounter++;
				if(messageCounter > 120) {
					messageCounter = 0;
					messageOn = false;
				}
			}
		}
	}
}
