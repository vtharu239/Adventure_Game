package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity {

    public static final String objName = "Tent";
    GamePanel gp;
    private boolean providesInvisibility = false;

    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_consumable;
        name = objName;
        down1 = setup("/objects/tent", gp.tileSize, gp.tileSize);
        description = "[Tent]\nYou can sleep until\nnext morning.\nProvides invisibility during day.";
        price = 300;
        stackable = true;
    }

    public boolean use(Entity entity) {
        gp.player.setInvisible(true);
        providesInvisibility = true;
        gp.playSE(14); // Âm thanh kích hoạt tàn hình
        gp.ui.addMessage("You are now invisible for 10 seconds!");
        
        if (gp.eManager.lighting.dayState != gp.eManager.lighting.day) {
            gp.gameState = gp.sleepState;
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.player.getSleepingImage(down1);
        }
        return true;
    }
    // Phương thức để kiểm tra xem lều có đang cung cấp tàn hình hay không
    public boolean isProvidingInvisibility() {
        return providesInvisibility;
    }

    // Phương thức để tắt tàn hình
    public void deactivateInvisibility() {
        providesInvisibility = false;
        gp.player.setInvisible(false);
        gp.ui.addMessage("Your invisibility has worn off!");
    }
}