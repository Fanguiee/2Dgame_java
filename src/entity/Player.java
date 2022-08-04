package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	KeyHandler keyH;		
	GamePanel gp;
	public final int screenX;
	public final int screenY;
	public Player(KeyHandler keyH, GamePanel gp) {
		this.keyH = keyH;
		this.gp = gp;
		screenX = gp.screenWidth/2 - gp.tileSize/2;
		screenY = gp.screenHeight/2 - gp.tileSize/2;
		setDefaultValue();
		getPlayerImage();
		direction = "down";
	}
	public void setDefaultValue() {
		worldX = gp.tileSize*0;
		worldY = gp.tileSize*0;
		speed = 4;
	}
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void update() {
		//System.out.println(worldX+", "+worldY);
		if(keyH.downPressed == true) {
			worldY += speed;
			direction = "down";
		}
		if(keyH.upPressed == true){
			worldY -= speed;
			direction = "up";
		}
		if(keyH.leftPressed == true){
			worldX -= speed;
			direction = "left";
		}
		if(keyH.rightPressed == true){
			worldX += speed;
			direction = "right";
		}
		if(keyH.rightPressed == true||keyH.leftPressed == true||keyH.downPressed == true||keyH.upPressed == true) {
			spriteCounter++;
		}
		if(spriteCounter > 10) {//change image every ten frames.
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum == 2) {
				spriteNum = 1;
			}	
			spriteCounter = 0;
		}
	}
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		switch(direction){
			case "up":
				if(spriteNum == 1) {
					image = up1;
				}
				if(spriteNum == 2) {
					image = up2;
				}
				break;
			case "down":
				if(spriteNum == 1) {
					image = down1;
				}
				if(spriteNum == 2) {
					image = down2;
				}
				break;
			case "left":
				if(spriteNum == 1) {
					image = left1;
				}
				if(spriteNum == 2) {
					image = left2;
				}
				break;
			case "right":
				if(spriteNum == 1) {
					image = right1;
				}
				if(spriteNum == 2) {
					image = right2;
				}
				break;				
		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize,null);
	}
}
