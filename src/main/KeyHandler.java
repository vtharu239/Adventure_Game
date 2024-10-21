package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed, spacePressed;
	//DEBUG
	boolean showDebugText = false;
	public boolean godModeOn = false;
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
		
	}
	@Override
	public void keyPressed(KeyEvent e) 
	{
		int code = e.getKeyCode();
		
		//TILE STATE:selecting a menu item//loop for commandNum isn't out 
		if(gp.gameState == gp.tileState) {
			tileState(code);
		}
		//PLAY STATE//has word "else" it means: stop player swinging sword when game starts
		else if(gp.gameState == gp.playState) {
			playState(code);
			}
		//PAUSE STATE
			else if(gp.gameState == gp.pauseState) {
				pauseState(code);
			}
				//DIALOGUE STATE
			else if(gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState) 
			{
				dialogueState(code);
			}
			//CHARACTER STATE
			else if(gp.gameState == gp.characterState) {
				characterState(code);
			}
		// OPTIONS STATE
			else if(gp.gameState == gp.optionsState) 
			{
				optionsState(code);
			}
		
		// GAME OVER STATE
			else if(gp.gameState == gp.gameOverState) 
			{
				gameOverState(code);
			}
		
		// TRADE STATE
			else if(gp.gameState == gp.tradeState) 
			{
				tradeState(code);
			}
		
		// MAP STATE 
			else if(gp.gameState == gp.mapState) 
			{
				mapState(code);
			}
		}
	public void gameOverState(int code)
	{
		if (code == KeyEvent.VK_W)
		{
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0)
			{
				gp.ui.commandNum = 1;
			}
			gp.playSE(9);
		}
		if (code == KeyEvent.VK_S)
		{
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 1)
			{
				gp.ui.commandNum = 0;
			}
			gp.playSE(9);
		}
		if (code == KeyEvent.VK_ENTER)
		{
			if (gp.ui.commandNum == 0)
			{
				gp.gameState = gp.playState;
				gp.resetGame(false);
				gp.playMusic(0);
			}
			else if (gp.ui.commandNum == 1)
			{
				gp.ui.tileScreenState = 0;
				gp.gameState = gp.tileState;
				gp.resetGame(true);
			}
		}
	}
	
	public void tileState(int code) 
	{
		
		if(gp.ui.tileScreenState == 0) 
		{
			if(code== KeyEvent.VK_W) 
			{
				gp.ui.commandNum--;
				if(gp.ui.commandNum<0) 
				{
					gp.ui.commandNum = 2;
				}
			}
			if(code== KeyEvent.VK_S) 
			{
				gp.ui.commandNum++;
				if(gp.ui.commandNum>2) 
				{
					gp.ui.commandNum = 0;
				}
			}
			//EXECUTING MENU ITEMS 
			if(code == KeyEvent.VK_ENTER) 
			{
				if(gp.ui.commandNum == 0) 
				{
					gp.ui.tileScreenState = 1;
//					gp.gameState = gp.playState;
//					gp.playMusic(0);
				}
				if(gp.ui.commandNum == 1) 
				{
					gp.saveLoad.load();
					gp.gameState = gp.playState;
					gp.playMusic(0);
				}
				if(gp.ui.commandNum == 2) 
				{
				    	System.exit(0);
				}
				if(gp.ui.commandNum == 3) 
				{
					
				}
			}
		}
		else if(gp.ui.tileScreenState == 1) 
		{
			if(code== KeyEvent.VK_W) 
			{
				gp.ui.commandNum--;
				if(gp.ui.commandNum<0) 
				{
					gp.ui.commandNum = 2;
				}
			}
			if(code== KeyEvent.VK_S) 
			{
				gp.ui.commandNum++;
				if(gp.ui.commandNum>2) 
				{
					gp.ui.commandNum = 0;
				}
			}
				//EXECUTING MENU ITEMS 
				if(code == KeyEvent.VK_ENTER) {
				    if(gp.ui.commandNum == 0) {
				        gp.player.playerType = "Boy";
				        gp.player.getImage();
				        gp.player.getAttackImage();
				        gp.gameState = gp.playState;
				        gp.playMusic(0);
				    }
				    if(gp.ui.commandNum == 1) {
				        gp.player.playerType = "Girl";
				        gp.player.getImage();
				        gp.player.getAttackImage();
				        gp.gameState = gp.playState;
				        gp.playMusic(0);
				    }
				    if(gp.ui.commandNum == 2) {        
				    	gp.ui.tileScreenState = 0;
				    }
				}
			}
	}
    public void LogoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutBtnActionPerformed
        // TODO add your handling code here:
 
     
    }//GEN-LAST:event_LogoutBtnActionPerformed
    
	public void playState(int code) 
	{
		if(code== KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code== KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code== KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code== KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(code== KeyEvent.VK_P) {
			gp.gameState = gp.pauseState;
		}
		if(code== KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		}
		if(code == KeyEvent.VK_ENTER){
			enterPressed = true;
		}
		if(code == KeyEvent.VK_F){
			shotKeyPressed = true;
		}
		if(code == KeyEvent.VK_ESCAPE)
		{
			gp.gameState = gp.optionsState;
		}
		// MAP
		if(code == KeyEvent.VK_M)
		{
			gp.gameState = gp.mapState;
		}
		
		// MINI MAP
		if(code == KeyEvent.VK_X)
		{
			if (gp.map.miniMapOn == false)
			{
				gp.map.miniMapOn = true;
			}
			else
			{
				gp.map.miniMapOn = false;
			}
		}

		if(code == KeyEvent.VK_SPACE)
		{
			spacePressed = true;
		}
		
		//DEBUG
		if(code== KeyEvent.VK_T) 
		{
			if(showDebugText == false) 
			{
				showDebugText = true;
			}
			else if(showDebugText == true) 
			{
				showDebugText = false;
			}			
		}
		if(code== KeyEvent.VK_R) 
		{
			switch(gp.currentMap)
			{
			case 0: gp.tileM.loadMap("/maps/worldV3.txt", 0); break;
			case 1: gp.tileM.loadMap("/maps/interior01.txt", 1); break;
			}
			
		}
		
		if(code== KeyEvent.VK_G) 
		{
			if(godModeOn == false) 
			{
				godModeOn = true;
			}
			else if(godModeOn == true) 
			{
				godModeOn = false;
			}			
		}
		
	}
	public void pauseState(int code) {
		if(code== KeyEvent.VK_P) {
			gp.gameState = gp.playState;
		}
	}
	public void dialogueState(int code) 
	{
		if(code == KeyEvent.VK_ENTER) 
		{
			enterPressed = true;
		}
	}
	public void characterState(int code) 
	{
		if(code == KeyEvent.VK_C) 
		{
			gp.gameState = gp.playState;
		}
		
		if(code == KeyEvent.VK_ENTER) 
		{
			gp.player.selectItem();
		}
		playerInventory(code);
	}
	public void optionsState(int code) 
	{
		if(code == KeyEvent.VK_ESCAPE) 
		{
			gp.gameState = gp.playState;
		}
		if(code == KeyEvent.VK_ENTER) 
		{
			enterPressed = true;
		}
		
		int maxCommandNum = 0;
		switch(gp.ui.subState) 
		{
		case 0: maxCommandNum = 5; break;
		case 3: maxCommandNum = 1; break;
		}
		if(code== KeyEvent.VK_W) 
		{
			gp.ui.commandNum--;
			gp.playSE(9);
			if(gp.ui.commandNum < 0) 
			{
				gp.ui.commandNum = maxCommandNum;
			}
		}
		if(code== KeyEvent.VK_S) 
		{
			gp.ui.commandNum++;
			gp.playSE(9);
			if(gp.ui.commandNum > maxCommandNum) 
			{
				gp.ui.commandNum = 0;
			}
		}
		
		if (code == KeyEvent.VK_A)
		{
			if (gp.ui.subState == 0)
			{
				if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0)
				{
					gp.music.volumeScale--;
					gp.music.checkVolume();
					gp.playSE(9);
				}
				if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0)
				{
					gp.se.volumeScale--;
					gp.playSE(9);
				}
			}
		}
		if (code == KeyEvent.VK_D)
		{
			if (gp.ui.subState == 0)
			{
				if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5)
				{
					gp.music.volumeScale++;
					gp.music.checkVolume();
					gp.playSE(9);
				}
				if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5)
				{
					gp.se.volumeScale++;
					gp.playSE(9);
				}
			}
		}
	}
	
	public void tradeState(int code)
	{
		if (code == KeyEvent.VK_ENTER)
		{
			enterPressed = true;
		}
		
		if (gp.ui.subState == 0)
		{
			if (code == KeyEvent.VK_W)
			{
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0)
				{
					gp.ui.commandNum = 2;
				}
				gp.playSE(9);
			}
			if (code == KeyEvent.VK_S)
			{
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 2)
				{
					gp.ui.commandNum = 0;
				}
				gp.playSE(9);
			}
		}
		if (gp.ui.subState == 1)
		{
			npcInventory(code);
			if (code == KeyEvent.VK_ESCAPE)
			{
				gp.ui.subState = 0;
			}
		}
		if (gp.ui.subState == 2)
		{
			playerInventory(code);
			if (code == KeyEvent.VK_ESCAPE)
			{
				gp.ui.subState = 0;
			}
		}
		
		if(code == KeyEvent.VK_L) {
            if(gp.ui.playerInventoryPage > 0) {
                gp.ui.playerInventoryPage--;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_R) {
            int maxPages = (gp.player.inventory.size() + 19) / 20;
            if(gp.ui.playerInventoryPage < maxPages - 1) {
                gp.ui.playerInventoryPage++;
                gp.playSE(9);
            }
        }
	}
	public void mapState (int code)
	{
		if (code == KeyEvent.VK_M)
		{
			gp.gameState = gp.playState;
		}
	}
	public void playerInventory (int code)
	{
		if(code== KeyEvent.VK_W) {
			if(gp.ui.playerSlotRow != 0){
			gp.ui.playerSlotRow--;
			gp.playSE(9);
			}
			
		}
		if(code== KeyEvent.VK_A) {
			if(gp.ui.playerSlotCol != 0) {
			gp.ui.playerSlotCol--;
			gp.playSE(9);
			}
		}
		if(code == KeyEvent.VK_S) {
			if(gp.ui.playerSlotRow != 3) {
			gp.ui.playerSlotRow++;
			gp.playSE(9);
			}
		}
		if(code== KeyEvent.VK_D) {
			if(gp.ui.playerSlotCol != 4) {
			gp.ui.playerSlotCol++;
			gp.playSE(9);
			}
		}
		 if(code == KeyEvent.VK_L) {
	            if(gp.ui.playerInventoryPage > 0) {
	                gp.ui.playerInventoryPage--;
	                gp.playSE(9);
	            }
	        }
	        if(code == KeyEvent.VK_R) {
	            int maxPages = (gp.player.inventory.size() + 19) / 20;
	            if(gp.ui.playerInventoryPage < maxPages - 1) {
	                gp.ui.playerInventoryPage++;
	                gp.playSE(9);
	            }
	        }
	}
	
	public void npcInventory (int code)
	{
		if(code== KeyEvent.VK_W) {
			if(gp.ui.npcSlotRow != 0){
			gp.ui.npcSlotRow--;
			gp.playSE(9);
			}
			
		}
		if(code== KeyEvent.VK_A) {
			if(gp.ui.npcSlotCol != 0) {
			gp.ui.npcSlotCol--;
			gp.playSE(9);
			}
		}
		if(code == KeyEvent.VK_S) {
			if(gp.ui.npcSlotRow != 3) {
			gp.ui.npcSlotRow++;
			gp.playSE(9);
			}
		}
		if(code== KeyEvent.VK_D) {
			if(gp.ui.npcSlotCol != 4) {
			gp.ui.npcSlotCol++;
			gp.playSE(9);
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) 
	{
		int code = e.getKeyCode();
		
		if(code== KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code== KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code== KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code== KeyEvent.VK_D) {
			rightPressed = false;
		}
		if(code == KeyEvent.VK_F){
			shotKeyPressed = false;
		}
		if(code == KeyEvent.VK_ENTER){
			enterPressed = false;
		}
		if(code == KeyEvent.VK_SPACE){
			spacePressed = false;
		}
	}
}
	

