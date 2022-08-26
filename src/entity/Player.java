package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{
	KeyHandler keyH;		
	GamePanel gp;
	public final int screenX;
	public final int screenY;
	public int hasKey;
	
	public Player(KeyHandler keyH, GamePanel gp) {
		this.keyH = keyH;
		this.gp = gp;
		screenX = gp.screenWidth/2 - gp.tileSize/2;
		screenY = gp.screenHeight/2 - gp.tileSize/2;
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		hasKey = 0;
		
		setDefaultValue();
		getPlayerImage();
		direction = "down";//default direction of player
	}
	public void setDefaultValue() {
		worldX = gp.tileSize*23;
		worldY = gp.tileSize*21;
		speed = 4;
	}
	public BufferedImage setUp(String imageName) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage scaledImage = null;
		try {
			scaledImage= uTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/"+imageName+".png")), gp.tileSize, gp.tileSize);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return scaledImage;
	}
	public void getPlayerImage() {
		up1 = setUp("boy_up_1");
		up2 = setUp("boy_up_2");
		down1 = setUp("boy_down_1");
		down2 = setUp("boy_down_2");
		left1 = setUp("boy_left_1");
		left2 = setUp("boy_left_2");
		right1 = setUp("boy_right_1");
		right2 = setUp("boy_right_2");
	}
	public void update() {
		//if the user press some key
		if(keyH.downPressed == true) {			
			direction = "down";
		}
		else if(keyH.upPressed == true){			
			direction = "up";
		}
		else if(keyH.leftPressed == true){			
			direction = "left";
		}
		else if(keyH.rightPressed == true){			
			direction = "right";
		}
		if(keyH.rightPressed == true||keyH.leftPressed == true||keyH.downPressed == true||keyH.upPressed == true) {
			spriteCounter++;
			
			//check collision
			collisionOn = false;
			gp.collisionChecker.checkTile(this);
			int index = gp.collisionChecker.checkObject(this, true);
			getObject(index);
			//player can move if no collision happens
			if(collisionOn == false) {
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
		}		
		
		if(spriteCounter > 10) {//change image every ten frames.
			if(spriteNum == 1) {
				spriteNum = 2;//we display two images according to spriteNum to draw a walking player
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
		g2.drawImage(image, screenX, screenY, null);
	}
	
	void getObject(int index) {
		if(index != 999) {
			String objName = this.gp.obj[index].name;
			switch(objName){
				case "Key":
					gp.playSoundEffect(1);
					hasKey++;
					this.gp.obj[index] = null;
					gp.ui.showMessage("You got a key!");
					break;
				case "Door":					
					if(hasKey > 0) {
						gp.playSoundEffect(3);
						this.gp.obj[index] = null;
						hasKey--;
						gp.ui.showMessage("You opened the door!");
					}
					break;
				case "Boots":
					gp.playSoundEffect(2);
					this.speed += 2;
					this.gp.obj[index] = null;
					gp.ui.showMessage("Boots help you walk faster!");
					break;
				case "MusicSymbol":
					this.speed -= 1;
					this.gp.obj[index] = null;
					gp.ui.showMessage("Slow down, enjoy the journey!");
					break;
				case "Chest":
					this.gp.obj[index] = null;
					gp.ui.showMessage("You found the treasure!");
					gp.ui.gameFinished = true;
					gp.stopMusic();
					gp.playSoundEffect(4);
					break;
			}
		}
	}
}
