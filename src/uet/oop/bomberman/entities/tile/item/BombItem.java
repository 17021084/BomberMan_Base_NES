package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends Item {
        
	public BombItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e) {
		if(e instanceof Bomber) {
			((Bomber) e).addPowerup(this);
			remove();
			return true;
		}
		return false;
		
	}

    @Override
    public void setValues() {
       this.setActive(true);
       // tăng số bom được đặtt
       Game.addBombRate(1);
        
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	


}
