package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.AnimatedEntitiy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.sound_effective.Sound;

public class Bomb extends AnimatedEntitiy {

	protected double _timeToExplode = 120; //2 seconds
	public int _timeAfter = 20; // thời gian cho vụ nổ kết thúc
	
	protected Board _board;
	protected Flame[] _flames; // tầm ảnh hưởng của bom 
	protected boolean _exploded = false; // đã nổ ?
	protected boolean _allowedToPassThroght = true; // có thể đi qua

    public boolean isExploded() {
        return _exploded;
    }

    public boolean isAllowedToPassThroght() {
        return _allowedToPassThroght;
    }
	
        
        
        
        
	public Bomb(int x, int y, Board board) {
		        //  System.out.println(" xbom=" + _x +" ybom = " + y  );
                _x = x;
		_y = y;
		_board = board;
		_sprite = Sprite.bomb;
	}
	
	@Override
	public void update() {
		if(_timeToExplode > 0) 
			_timeToExplode--;
		else {
			if(!_exploded) 
				explode();
			else
				updateFlames();
			
			if(_timeAfter > 0) 
				_timeAfter--;
			else
				remove();
		}
			
		animate();
	}
	
	@Override
	public void render(Screen screen) {
		if(_exploded) {
			_sprite =  Sprite.bomb_exploded2;
			renderFlames(screen);
		} else
			_sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);
		
		int xt = (int)_x << 4;
		int yt = (int)_y << 4;
		
		screen.renderEntity(xt, yt , this);
	}
	
	public void renderFlames(Screen screen) {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].render(screen);
		}
	}
	
	public void updateFlames() {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].update();
                       // System.out.println("flame lengt= " + _flames.length);
		}
	}

    /**
     * Xử lý Bomb nổ
     */
	protected void explode() {
            _exploded = true;
            _allowedToPassThroght = true;
             //  xử lý khi Character đứng tại vị trí Bomb
              
            // xử lí khi nhân vật ở bom khi nó phát nổ.
            int Xb = this._board.getBomber().getXTile();
            int Yb = this._board.getBomber().getYTile();
            // nếu tọa độ trùng nhau thì giết
            if ( Xb == this.getXTile() && this.getYTile()== Yb  ){
                    this._board.getBomber().kill();
                    Sound.playBomberDie();
            }
            
            //  tạo các Flame nổ 4 chiều
            _flames = new Flame[4];
            for (int i = 0; i < _flames.length; i++) {
		_flames[i] = new Flame((int)_x, (int)_y, i, Game.getBombRadius(), _board);
            }
		Sound.playBombExplose();
		
		
	}
	
        
	public FlameSegment flameAt(int x, int y) {
		if(!_exploded) return null;
		
		for (int i = 0; i < _flames.length; i++) {
			if(_flames[i] == null) return null;
			FlameSegment e = _flames[i].flameSegmentAt(x, y);
			if(e != null) return e;
		}
		
		return null;
	}

        
        /**
         * 
         * @param e thực thể  có thể là bomber hoặc flam của quả bom mới nỏ
         * @return true nếu qua được bom
         * @return flase nếu ko qua được bom
         */
        
	@Override      
	public boolean collide(Entity e) {
        // xử lý khi Bomber đi ra sau khi vừa đặt bom (_allowedToPassThroght)
           
            if(e instanceof Bomber) { // thực thể có phải là bomberman
                double diffX = e.getX() - Coordinates.tileToPixel(getX());
                double diffY = e.getY() - Coordinates.tileToPixel(getY());
			
                // kiểm tra xem thằng boomber đã rời vị chí quả bom vừa đặt lúc nãy chưa
                if(!( diffY >= 1 && diffY <= 30 && diffX >= -10 && diffX < 16 )) { 
                            // trả về ko thê qua vị chí đó đc nữa			
                     _allowedToPassThroght = false;
                }
                              
                
                   // trả về true ; qua dược
                return _allowedToPassThroght;
                    
	    }
            if ( e instanceof Flame){
                this._timeToExplode=0;
                // cho quả bom này thời gian nổ về 0
                return true;
            }
            if ( e instanceof FlameSegment){
                this._timeToExplode=0;
                // cho bom có thời gian nổ về 0
                return true;
            }
            
            
            if ( e instanceof Bomb ){
                //truong hop bom dat cung 1 luc
               // if ( ((Bomb) e).isExploded()==false) return true;
                if ( ((Bomb) e).isExploded()== true) {
                    this._timeToExplode=0;
                    return true;
                }
                    
                
            }
           
            return false;
	}
        
        
        // thêm 2 hàm cho AI
        
        /** 
         * 
         * @return tọa độ x  trong hệ tọa độ ô vuông
         */
        @Override
        public int getXTile(){
            return (int) this._x;
        }
        /**
         * 
         * @return  tọa độ y trong hệ tọa dộ ô vuông
         */
        @Override
        public int getYTile(){
            return (int) this._y;
        }
        
        
}
