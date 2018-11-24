package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Item {

	public SpeedItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e) {
		  if(e instanceof Bomber) { // kiểm tra entity có phải là bomberman ko
                        // add thêm  sức mạnh của flame
                        ((Bomber) e).addPowerup(this); 
			remove();
			return true;
		}
		
		return false;
	}

    @Override
    public void setValues() {
        this.setActive(true);
        
		Game.addBomberSpeed(0.1);
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
