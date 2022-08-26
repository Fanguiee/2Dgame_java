package object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Chest extends superObject{
	GamePanel gPanel;
	public OBJ_Chest(GamePanel gPanel) {
		this.gPanel = gPanel;
		name = "Chest";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
			image = uTool.scaleImage(image, gPanel.tileSize,gPanel.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
