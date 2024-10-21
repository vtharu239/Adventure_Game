package tile_interactive;

import java.awt.Color;
import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class IT_DryTree extends InteractiveTile
{
	GamePanel gp;

	public IT_DryTree(GamePanel gp, int col, int row) {
		super(gp,col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		down1 = setup("/tiles_interactive/drytree",gp.tileSize,gp.tileSize);
		destructible = true;
		life = 2; // cut down 2 times
	}
	
	public boolean isCorrectItem(Entity entity) {//add axe 
		boolean isCorrectItem = false;
		if(entity.currentWeapon.type == type_axe) {
			isCorrectItem = true;
		}
		return isCorrectItem;
	}
	
	public void playSE() {
		gp.playSE(11);
	}
	public InteractiveTile getDestroyForm() {// after destroy tree and then change into drytree
		InteractiveTile tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
		return tile;
	}
	public Color getParticleColor() {
		Color color = new Color(65,50,30);
		return color;
	}
	public int getParticleSize() {
		int size = 6;
		return size;
	}
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}
	public void checkDrop() 
	{
		//CAST A DIE
		int i = new Random().nextInt(100)+1;
		
		//SET THE MONSTER DROP
		if( i < 50){
			dropItem(new OBJ_Coin_Bronze(gp));
		}
		if( i >= 50 && i < 75){
			dropItem(new OBJ_Heart(gp));
		}
		if( i >= 75 && i < 100){
			dropItem(new OBJ_ManaCrystal(gp));
		}
	}
}
