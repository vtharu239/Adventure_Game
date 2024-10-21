package main;

import entity.Entity;

public class CollisionChecker 
{
	GamePanel gp;
	public CollisionChecker(GamePanel gp)
	{
		this.gp = gp;	
	}
	
	public void checkTile(Entity entity) 
	{
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
				
		int tileNum1, tileNum2;
		
		// Use a temporal direction when it's being knockbacked
		String direction = entity.direction;
		if (entity.knockBack == true)
		{
			direction = entity.knockBackDirection;
		}
		
		switch(direction) 
		{
		case "up":// this case help collision about water, tree, wall can't go and stop
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
			if(gp.tileM.tile[tileNum1].collision == true ||  gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
		break;
		case "down":// this case help collision about water, tree, wall can't go and stop
			entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true ||  gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "left":// this case help collision about water, tree, wall can't go and stop
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true ||  gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "right":// this case help collision about water, tree, wall can't go and stop
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true ||  gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		}
	}
	public int checkObject(Entity entity, boolean player) 
	{
		int index = 999;
		
		String direction = entity.direction;
		if (entity.knockBack == true)
		{
			direction = entity.knockBackDirection;
		}
		
		for(int i = 0; i < gp.obj[1].length; i++) { // FIXED
			if(gp.obj[gp.currentMap][i] != null) { // FIXED
				
				//get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				//get the object's solid area position
				//find object out x, y
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x; // FIXED
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y; // FIXED
				
				switch(direction) 
				{
				case "up":
					//simulating entity's movement and check where it will be after it moved
					entity.solidArea.y -= entity.speed;
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					break;
				case "right":
					entity.solidArea.x += entity.speed;

					break;
				}
				if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) { // FIXED
					if(gp.obj[gp.currentMap][i].collision == true) { // FIXED
						entity.collisionOn = true;
					}
					if(player == true) {
						index = i;
					}
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX; // FIXED
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY; // FIXED
		}
		
	}
		
		return index;
}
	//npc on monster
	public int checkEntity(Entity entity,Entity[][] target)  // FIXED
	{
		int index = 999;
		// Use a temporal direction when it's being knockbacked
		String direction = entity.direction;
		if (entity.knockBack == true)
		{
			direction = entity.knockBackDirection;
		}
				
		for(int i = 0; i < target[1].length; i++) // FIXED
		{ 
			if(target[gp.currentMap][i] != null) // FIXED
			{ 
				
				//get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				//get the object's solid area position
				//find object out x, y
				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x; // FIXED
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y; // FIXED
				
				switch(direction) 
				{
				case "up":
					//simulating entity's movement and check where it will be after it moved
					entity.solidArea.y -= entity.speed; break;
				case "down": entity.solidArea.y += entity.speed;  break;
				case "left": entity.solidArea.x -= entity.speed;  break;
				case "right": entity.solidArea.x += entity.speed; break;
				}		
				
				if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) 
				{
					if(target[gp.currentMap][i] != entity) { // FIXED
						entity.collisionOn = true;
						index = i;
					}
					
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX; // FIXED
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY; // FIXED
		}
		
	}
		
		return index;
	}
	public boolean checkPlayer(Entity entity) { // NO NEED FIX
		boolean contactPlayer = false;
		//get entity's solid area position
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;
		//get the object's solid area position
		//find object out x, y
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		
		switch(entity.direction) {
		case "up": //simulating entity's movement and check where it will be after it moved
			entity.solidArea.y -= entity.speed; break;
		case "down": entity.solidArea.y += entity.speed; break;
		case "left": entity.solidArea.x -= entity.speed; break;
		case "right": entity.solidArea.x += entity.speed; break;
			}
				
	if(entity.solidArea.intersects(gp.player.solidArea)) {
		entity.collisionOn = true;
		contactPlayer = true;
	}
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		
		return contactPlayer;
	}
}
