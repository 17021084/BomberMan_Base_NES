package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;


public class FlameSegment extends Entity {

	protected boolean _last;

	/**
	 *
	 * @param x
	 * @param y
	 * @param direction
	 * @param last cho biết segment này là cuối cùng của Flame hay không,
	 *                segment cuối có sprite khác so với các segment còn lại
	 */
	public FlameSegment(int x, int y, int direction, boolean last) {
		_x = x;
		_y = y;
		_last = last;

		switch (direction) {
			case 0:
				if(!last) {
					_sprite = Sprite.explosion_vertical2;
				} else {
					_sprite = Sprite.explosion_vertical_top_last2;
				}
			break;
			case 1:
				if(!last) {
					_sprite = Sprite.explosion_horizontal2;
				} else {
					_sprite = Sprite.explosion_horizontal_right_last2;
				}
				break;
			case 2:
				if(!last) {
					_sprite = Sprite.explosion_vertical2;
				} else {
					_sprite = Sprite.explosion_vertical_down_last2;
				}
				break;
			case 3: 
				if(!last) {
					_sprite = Sprite.explosion_horizontal2;
				} else {
					_sprite = Sprite.explosion_horizontal_left_last2;
				}
				break;
		}
	}

  
	
	@Override
	public void render(Screen screen) {
		int xt = (int)_x << 4;
		int yt = (int)_y << 4;
		
		screen.renderEntity(xt, yt , this);
	}
	
	@Override
	public void update() {}

        
        /**
         * 
         * @param e  thực thể, có thể là emnemy hoặc bomberman
         * @return 
         */
	@Override
	public boolean collide(Entity e) {
		// xử lý  mép  ngoài lửa chạm vaò chạm với enemy
                // tạm thời tắt
//            if(e instanceof Enemy) {
//		((Enemy)e).kill();
//            }    
                // xử lí va chạm với bomber chạm vào cái mép cuối vụ nổ
             if(e instanceof Bomber) {
		((Bomber) e).kill();
            }
           
             if ( e instanceof Bomb){
                 
                 ( (Bomb) e).explode();
             }
             
            return true;
	
	}
	

}