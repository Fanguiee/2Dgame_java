package main;

import entity.Entity;

public class CollisionChecker {
	public GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		switch (entity.direction){
		case "up": 
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileManager.tileNum[entityTopRow][entityLeftCol];
			tileNum2 = gp.tileManager.tileNum[entityTopRow][entityRightCol];
			if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true ) {
				entity.collisionOn = true;
			}
			break;
	
		case "down": 
			entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileManager.tileNum[entityBottomRow][entityLeftCol];
			tileNum2 = gp.tileManager.tileNum[entityBottomRow][entityRightCol];
			if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true ) {
				entity.collisionOn = true;
			}
			break;
		case "left": 
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileManager.tileNum[entityTopRow][entityLeftCol];
			tileNum2 = gp.tileManager.tileNum[entityBottomRow][entityLeftCol];
			if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true ) {
				entity.collisionOn = true;
			}
			break;
		case "right": 
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileManager.tileNum[entityTopRow][entityRightCol];
			tileNum2 = gp.tileManager.tileNum[entityTopRow][entityRightCol];
			if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true ) {
				entity.collisionOn = true;
			}
			break;
		}		
	}
	
	public int checkObject(Entity entity, boolean isPlayer) {
		int index = 999;
		
		for(int i = 0; i<gp.obj.length; i++) {
			if(gp.obj[i] != null) {
				//get coordinates of solidArea of entity
				entity.solidArea.x = entity.solidArea.x + entity.worldX;
				entity.solidArea.y = entity.solidArea.y + entity.worldY;
				//get coordinates of solidArea of obj
				gp.obj[i].solidArea.x = gp.obj[i].solidArea.x + gp.obj[i].worldX;
				gp.obj[i].solidArea.y = gp.obj[i].solidArea.y + gp.obj[i].worldY;
				
				//get future position of entity
				switch(entity.direction) {					
					case "left":
						entity.solidArea.x = entity.solidArea.x - entity.speed;
						if(entity.solidArea.intersects(gp.obj[i].solidArea)) {					
							if(gp.obj[i].collision == true) {
								entity.collisionOn = true;
								System.out.println("collision!");
							}
							if(isPlayer == true) {
								index = i;
							}
						}
					break;
					case "right":
						entity.solidArea.x = entity.solidArea.x + entity.speed;
						if(entity.solidArea.intersects(gp.obj[i].solidArea)) {					
							if(gp.obj[i].collision == true) {
								entity.collisionOn = true;
								System.out.println("collision!");
							}
							if(isPlayer == true) {
								index = i;							}
						}
					break;
					case "up":
						entity.solidArea.y = entity.solidArea.y - entity.speed;	
						if(entity.solidArea.intersects(gp.obj[i].solidArea)) {					
							if(gp.obj[i].collision == true) {
								entity.collisionOn = true;
								System.out.println("collision!");
							}
							if(isPlayer == true) {
								index = i;
							}
						}
					break;
					case "down":
						entity.solidArea.y = entity.solidArea.y + entity.speed;	
						if(entity.solidArea.intersects(gp.obj[i].solidArea)) {					
							if(gp.obj[i].collision == true) {
								entity.collisionOn = true;
								System.out.println("collision!");
							}
							if(isPlayer == true) {
								index = i;
							}
						}
					break;
				}
				
				
				//restore solidArea of obj
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;	
				//restore solidArea of entitys
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
			}
		}
		

		return index;
	}
}
