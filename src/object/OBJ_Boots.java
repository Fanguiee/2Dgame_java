package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Boots extends superObject{
	GamePanel gPanel;
	public OBJ_Boots(GamePanel gPanel) {
		this.gPanel = gPanel;
		name = "Boots";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
			image = uTool.scaleImage(image, gPanel.tileSize, gPanel.tileSize);
			}catch(IOException e) {
			e.printStackTrace();
		}
	}

}