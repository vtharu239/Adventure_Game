package main;

import data.Progress;
import entity.Entity;

public class EventHandler 
{
	GamePanel gp;
	EventRect eventRect[][][];
	Entity eventMaster;
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	int tempMap, tempCol, tempRow;
	
	public EventHandler(GamePanel gp) 
	{
		this.gp = gp;
		
		eventMaster = new Entity(gp);
		eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		int map = 0;
		int col = 0;
		int row = 0;
		while(map< gp.maxMap && col< gp.maxWorldCol && row< gp.maxWorldRow) {
			eventRect[map][col][row] = new EventRect();
			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y = 23;
			eventRect[map][col][row].width = 2;
			eventRect[map][col][row].height = 2;
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
			col++;
			if(col == gp.maxWorldCol) {
				col=0;
				row++;
				
				if(row == gp.maxWorldRow)
				{
					row = 0;
					map ++;
				}
			}
		}
		
		setDialogue();
	}
	
	public void setDialogue()
	{
		eventMaster.dialogues[0][0] = "You fail into a pit!";
		
		eventMaster.dialogues[1][0] = "You drink the water.\nYour life and mana have been recovered. \n"
				+ "(The progess has been saved)";
		eventMaster.dialogues[1][1] = "OMG, this is good water.";
		
		eventMaster.dialogues[2][0] = "To enter this area you need to be level 3!";
		
		eventMaster.dialogues[3][0] = "To enter this area you need to be level 5!";
	}
	public void checkEvent()
	{
		//check if the player character is more than 1 tile away from the last event
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		if(distance > gp.tileSize)
		{
			canTouchEvent = true;
		}
		if(canTouchEvent == true) 
		{
			//creating a damage pit event
			//if player hit this tile then he receives damage
			if(hit(0,27,16,"right") == true) {damagePit(gp.dialogueState);}
//			if(hit(23,19,"any") == true) {damagePit(27,16,gp.dialogueState);}
//			if(hit(27,16,"right") == true) {telePort(gp.dialogueState);}//move different place instantly
			else if(hit(0,23,12,"up") == true) {healingPool(gp.dialogueState);}//heart will full when collision water
			
			else if(hit(0,10,39,"up") == true) // to the merchant's house
			{
				if (gp.player.level >= 3)
				{
					// to the merchant's house
					teleport(1,12,13, gp.indoor);
				}
				else
				{
					eventMaster.startDialogue(eventMaster, 2);
				}
			} 
			else if(hit(1,12,13,"any") == true) {teleport(0,10,39, gp.outside);} // to outside
			else if(hit(1,12,9,"up") == true) {speak(gp.npc[1][0]);} // talk with Merchant
			
			else if(hit(0,12,9,"up") == true) // to the dungeon
			{
				if (gp.player.level >= 5)
				{
					// to the dungeon
					teleport(2,9,41, gp.dungeon);
				}
				else
				{
					eventMaster.startDialogue(eventMaster, 3);
				}
						
			} 
			else if(hit(2,9,41,"any") == true) {teleport(0,12,9, gp.outside);} // to outside
			else if(hit(2,8,7,"any") == true) {teleport(3,26,41, gp.dungeon);} // to B2
			else if(hit(3,26,41,"any") == true) {teleport(2,8,7,gp.dungeon);} // to B1
			
			else if(hit(3,25,27,"any") == true) {skeletonLord();} // BOSS
		}
		
	}
	public boolean hit(int map, int col, int row, String reqDirection) 
	{
		boolean hit = false;
		
		if(map == gp.currentMap)
		{
			//getting player's current solidArea positions
			gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
			gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
			
			//getting eventRect's solidArea positions
			eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
			eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;
			
			
			//checking if player's solidArea is colliding with eventRect's solidArea
			//code:eventRect[col][row].eventDone == false MEAN WHEN FAIL INTO PIT Won't fail again
			if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
				if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
					hit = true;
					
					previousEventX = gp.player.worldX;
					previousEventY = gp.player.worldY;
				}
			}
			//after checking the collision reset the solidArea x and y
			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
			eventRect[map][col][row].y =eventRect[map][col][row]. eventRectDefaultY;
		}
		
		return hit;
	}
//	public void telePort(int gameState) {
//		gp.gameState = gameState;
//		gp.ui.currentDialogue = "Teleport!";
//		gp.player.worldX = gp.tileSize*37;//choose place to move
//		gp.player.worldY = gp.tileSize*10;//choose place to move
//		
//	}
	public void damagePit(int gameState) 
	{
		gp.gameState = gameState;
		gp.playSE(6);;
		eventMaster.startDialogue(eventMaster, 0);
		gp.player.life -= 1;
//		eventRect[col][row].eventDone = true;
		canTouchEvent = false;//prevent collision pit again
	}
	//the event only occurs when you press enter key

	public void healingPool(int gameState) 
	{
		if(gp.keyH.enterPressed == true) 
		{
			gp.gameState = gameState;
			gp.player.attackCanceled = true;
			gp.playSE(2);
			eventMaster.startDialogue(eventMaster, 1);
			gp.player.life = gp.player.maxLife;
			gp.player.mana = gp.player.maxMana;
			gp.aSetter.setMonster();
			gp.saveLoad.save();
		}
	}
	
	public void teleport(int map, int col, int row, int area)
	{
		gp.gameState = gp.transitionState;
		gp.nextArea = area;
		tempMap = map;
		tempCol = col;
		tempRow = row;
		
//		gp.currentMap = map;
//		gp.player.worldX = gp.tileSize * col;
//		gp.player.worldY = gp.tileSize * row;
//		previousEventX = gp.player.worldX;
//		previousEventY = gp.player.worldY;
		
		canTouchEvent = false;
		gp.playSE(13);
	}
	public void speak(Entity entity)
	{
		if (gp.keyH.enterPressed == true)
		{
			gp.gameState = gp.dialoqueState;
			gp.player.attackCanceled = true;
			entity.speak();
		}
	}
	
	public void skeletonLord()
	{
		if (gp.bossBattleOn == false && Progress.skeletonLordDefeated == false)
		{
			gp.gameState = gp.cutsceneState;
			gp.csManager.sceneNum = gp.csManager.skeletonLord;
		}
	}
}
