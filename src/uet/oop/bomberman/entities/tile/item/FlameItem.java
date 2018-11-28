package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound_effective.Sound;

public class FlameItem extends Item {

	public FlameItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e) {
		// xử lý Bomber ăn Item
                //  xử lý Bomber ăn Item  cộng flam
                if(e instanceof Bomber) { // kiểm tra entity có phải là bomberman ko
                        // add thêm  sức mạnh của flame
                        ((Bomber) e).addPowerup(this); 
			remove();
                        Sound.playGetNewItem();// thêm âm nhạc
			return true;
		}
		return false;
	}

    @Override
    public void setValues() { 
                
                this.setActive(true);
		// thăng bán kính bom
               // Game.addBombRate(1);
               Game.addBombRadius(1);
             //  System.out.println("Flamitem");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
