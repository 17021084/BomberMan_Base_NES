package uet.oop.bomberman.entities.tile.destroyable;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Tile;
import uet.oop.bomberman.graphics.Sprite;

/**
 * Đối tượng cố định có thể bị phá hủy
 */
public class DestroyableTile extends Tile {

	private final int MAX_ANIMATE = 7500; 
	private int _animate = 0;
	protected boolean _destroyed = false; // bị phá phá hủy hay không
	protected int _timeToDisapear = 20; // thời gian để biến mất của 1 bức tường gạch
	protected Sprite _belowSprite = Sprite.grass; // hình ảnh bên dưới
	
        
	public DestroyableTile(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	@Override
	public void update() {
            // nếu bị destroy 
		if(_destroyed) {
			if(_animate < MAX_ANIMATE) _animate++; // thay đổi ảnh khi mà bom nổ 
                        else _animate = 0;
			
                        if(_timeToDisapear > 0) 
				_timeToDisapear--;
			
                        else
				remove();
		}
	}

	public void destroy() {
		_destroyed = true;
	}
	
        /**
         * // xử lý khi va chạm với Flame
         * @param e thực thể 
         * @return 
         */
	@Override
	public boolean collide(Entity e) {
            
            if (e instanceof Flame)  // nếu va chạm với flame 
                this.destroy(); // gọi hàm phá tường gạch
                return false;
            
		
	}
	
        
	public void addBelowSprite(Sprite sprite) {
		_belowSprite = sprite;
	}
	
	protected Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2) {
		int calc = _animate % 30;
		
		if(calc < 10) {
			return normal;
		}
			
		if(calc < 20) {
			return x1;
		}
			
		return x2;
	}
	
}
