package object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Door extends superObject{
	GamePanel gPanel;
	public OBJ_Door(GamePanel gPanel) {
		name = "Door";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
			image = uTool.scaleImage(image, gPanel.tileSize, gPanel.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}

}
