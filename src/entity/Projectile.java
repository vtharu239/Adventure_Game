	package entity;

import main.GamePanel;

public class Projectile extends Entity
{
	Entity user;
	
	public Projectile(GamePanel gp)
	{
		super(gp);
	
	}
	public void set(int worldX,int worldY, String direction, boolean alive, Entity user)
	{
		
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		this.life = this.maxLife;	//reset the life to the max value every time you shoot it	
	}
	public void update() 
	{
		if(user == gp.player) // player attack monster
		{
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			if(monsterIndex != 999) 
			{
				// If the projectile hits a monster, it dies ( disappears)
				// The fire ball damage increases in every 2 levels
				gp.player.damageMonster(monsterIndex, this, attack*(gp.player.level/2), knockBackPower);//this is a parent class of fireball class
				generateParticle(user.projectile, gp.monster[gp.currentMap][monsterIndex]);
				alive = false;
			}
		}
		if(user != gp.player) //not player mean MONSTER attack PLAYER
		{
			boolean contactPlayer = gp.cChecker.checkPlayer(this);
			if(gp.player.invincible == false && contactPlayer == true) 
			{
				damagePlayer(attack);
				generateParticle(user.projectile, user.projectile);
				alive = false;
			}
		}
		switch(direction) {
		case "up": worldY -= speed;	break;
		case "down": worldY += speed; break;
		case "left": worldX -= speed; break;
		case "right": worldX += speed; break;
		}
		
		life--;
		if(life <= 0) {
			alive = false;
		}
		spriteCounter++;
		if(spriteCounter > 12) {//each frame will change step
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		 }
	}
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		return haveResource;
	}
	public void substractResource(Entity user) {}
}
