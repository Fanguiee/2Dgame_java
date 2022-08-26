package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Key extends superObject{
	GamePanel gPanel;
	public OBJ_Key(	GamePanel gPanel) {
		this.gPanel = gPanel;
		name = "Key";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
			image = uTool.scaleImage(image, gPanel.tileSize, gPanel.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
