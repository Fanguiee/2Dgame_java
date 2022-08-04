package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	GamePanel gp;
	Tile[] tile;
	int tileNum[][];
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
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
		} catch (IOException e) {
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
		
		while(worldCol < gp.maxScreenCol && worldRow < gp.maxScreenRow) {
			int num = tileNum[worldRow][worldCol];
			
			int worldX = worldCol*gp.tileSize;
			int worldY = worldRow*gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			g2.drawImage(tile[num].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			System.out.println("start:");
			System.out.println(num+" "+worldRow+","+worldCol);
			System.out.println(gp.maxWorldCol);
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
