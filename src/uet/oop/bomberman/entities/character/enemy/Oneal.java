package uet.oop.bomberman.entities.character.enemy;


import static java.lang.Math.random;
import static java.lang.Math.sqrt;
import static java.lang.StrictMath.random;
import java.util.ArrayList;
import java.util.Random;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemy.ai.AIAdvance;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
	      
        
	public Oneal(int x, int y, Board board) {
		super(x, y, board, Sprite.oneal_dead, Game.getBomberSpeed()*1.1 , 200);
                
                _sprite = Sprite.oneal_left1;		
		_ai = new AIAdvance(_board.getBomber(), this, board);
		_direction  = _ai.calculateDirection();

        }
	
	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
			case 1:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, _animate, 30);
				else
					_sprite = Sprite.oneal_left1;
				break;
			case 2:
			case 3:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, 30 );
				else
					_sprite = Sprite.oneal_left1;
				break;
		}
	}
}
