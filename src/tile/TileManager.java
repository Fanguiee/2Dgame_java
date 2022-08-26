package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.AsynchronousCloseException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[]tile;
	public int tileNum[][];
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[6];
		tileNum = new int [gp.maxWorldRow][gp.maxWorldCol];
		getTileImage();
		loadMap("/maps/world01.txt");
		System.out.println("matrix:");
		for(int i[]:tileNum) {
			for(int j: i)
				System.out.print(j+" ");
			System.out.println();
		}
		System.out.println("matrix."
				+ "");
	}
	public void getTileImage() {
			setUp(0, "grass", false);
			setUp(1, "wall", true);
			setUp(2, "water", true);
			setUp(3, "earth", false);
			setUp(4, "tree", true);			
			setUp(5, "sand", false);
			
	}
	public void setUp(int index, String imageName, boolean collision) {
		UtilityTool uTool = new UtilityTool();
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+imageName+".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();
				while(col < gp.maxWorldCol) {
					String nums[] = line.split(" ");
					/*
					for(String s: nums)
						System.out.print(s+",");
					System.out.println();
					*/
					int num = Integer.parseInt(nums[col]);
					tileNum[row][col] = num;
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}				
			}
			br.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			int num = tileNum[worldRow][worldCol];
			
			int worldX = worldCol*gp.tileSize;
			int worldY = worldRow*gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if(	worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				g2.drawImage(tile[num].image, screenX, screenY, null);
				
			}
			
			if(worldCol == gp.maxWorldCol - 1) {
				worldCol = 0;
				worldRow++;
				}
			else {
				worldCol++;
			}
		}
		
	}
	
}
