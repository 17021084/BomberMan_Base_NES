package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.entities.tile.Tile;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Item extends Tile {
        
        protected boolean  _active = false;
        
	public Item(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
        // kiểm tra có hoạt động không
        public boolean isActive() {
		return _active;
	}

        // setter
	public void setActive(boolean active) {
		this._active = active;
	}
        // hàm abstrac  sét sức mạnh
	public abstract void setValues();


}
