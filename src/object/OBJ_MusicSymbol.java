package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_MusicSymbol extends superObject{
	GamePanel gPanel;
	public OBJ_MusicSymbol(	GamePanel gPanel) {
		name = "MusicSymbol";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/musicSymbol.png"));
			image = uTool.scaleImage(image, gPanel.tileSize, gPanel.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}