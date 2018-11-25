package uet.oop.bomberman.entities.character;

import java.util.ArrayList;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;

import java.util.Iterator;
import java.util.List;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.sound_effective.Sound;

public class Bomber extends Character {

    private List<Bomb> _bombs;
    protected Keyboard _input;
    //list lưu khả ngang
    public static List<Item> _powerups = new ArrayList<Item>();
    
    
    public void addPowerup(Item item) {
       // kiểm tra xem đã bị xóa chưa
        if(item.isRemoved()) return; // xóa rồi thì break
        // chưa xóa thì thêm item vào
        
        _powerups.add(item);
        // goi set value để thêm kha năng vào
        
        item.setValues();
    }
    
    
    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
     */
    protected int _timeBetweenPutBombs = 0;

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
      //  super(0, 0, board);
      // tọa độ của boomber
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right;
    }

    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }
        
        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;

        animate();

        calculateMove();

        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive)
            chooseSprite();
        else
            _sprite = Sprite.player_dead1;

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt bom tại vị trí hiện tại của Bomber
     */
    private void detectPlaceBomb() {
     
        // kiểm tra  nút đăt bom ,
        // số quả đặt bomrate >0
        // thời gian giữa 2 lần đăt bom lien tiếp    
        if( _timeBetweenPutBombs < 0 && Game.getBombRate() > 0 &&  _input.space == true ) { 
           
            // toa độ trung tâm của nhân vật ( trung tâm của) trong tâm 
            int xg = Coordinates.pixelToTile(_x + _sprite.getSize() / 2);
            int yg = Coordinates.pixelToTile( (_y + _sprite.getSize() / 2) - _sprite.getSize() ); 
            
          //  System.out.println("x= " + this.getXTile() + " y = " + this.getYTile());
           // this.getAllInfor();
            //System.out.println("radius= " this._bombs.get(i));
            // đặt bom 
            placeBomb(xg,yg);
            // giảm số bom có thể đặt đi 1 quả
            Game.addBombRate(-1);	
            // set thời gian chờ cho đợt bom tới
            _timeBetweenPutBombs = 25;
	}
        
        
        
    }

    protected void placeBomb(int x, int y) {
        // thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
        Bomb bom = new Bomb(x, y, _board);
        // thêm bom vào board
        _board.addBomb(bom);
        Sound.playPlaceNewBomb();
        
//        for ( int i = 0 ; i < Brick.Ygachvo.size() ; i++ ){
//           System.out.println( i + " SOUT trong bomber toa do x = " + Brick.Xgachvo.get(i) + " y = " +Brick.Ygachvo.get(i)  );
//         }
        
        
    }

    private void clearBombs() {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill() {
        if (!_alive) return;
        _alive = false;
        Sound.playBomberDie();
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            _board.endGame();
        }
    }

    @Override
    protected void calculateMove() {
        int xa = 0, ya = 0;
         // xử lý nhận tín hiệu điều khiển hướng đi từ _input 	
	if(_input.left) xa--;	
        if(_input.right) xa++;
        if(_input.up) ya--;
	if(_input.down) ya++;
	// kiểm tra xem đã ấn nút để di chuyển chưa? rồi  gọi move() để thực hiện di chuyển 	
	if(xa != 0 || ya != 0)  {
                // di chuyển phụ thộc tốc độ
		move(xa * Game.getBomberSpeed(), ya * Game.getBomberSpeed());
		// cập nhâp lại cờ là di chuyển
                _moving = true;
                
	} else {
             // cập nhập lại là chưa di chuyển
		_moving = false;              
	}
		       
        
        
    }

    /**
     * kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
     * @return true nếu đi dược
     * @return false nếu ko đi được
     */
    
    
    @Override
    public boolean canMove(double x, double y) {
         
      /*
       x y nhân 2 3 giá trị  0 +- 1    tương ứng 3 trạng thái đứng im và di chuyển   
        _x _y top left của nhân vật
       */
        for (int c = 0; c < 4; c++) { //kiểm tra 4 góc 
            //chia cho toan độ 1 vi
            double xt = ((_x + x ) + c % 2 * 11 ) / Game.TILES_SIZE;
            double yt = ((_y + y ) + c / 2 * 12 - 13 ) / Game.TILES_SIZE; 
           /* từ tọa độ của boober _x, _y trong R^2 thì ta chuyển nó về tọa độ của ô 
            dọc 13 ô , ngang 31/ 15 ô
            c0 c1 //  trên 
            c2 c3 //  dưới
            */         
          
            
           
            Entity a = _board.getEntity(xt, yt, this);
			
		if(!a.collide(this)) 
			return false;
	}
        
    
             
        return true;
       
    }

    @Override
    public void move(double xa, double ya) {

        
        /*
            direction = 
              1 sang trái
              3 sang phải
              2 lên trên
              0 xuống dưới
              thông số đê choosepire chọn
        */
        
        if(xa > 0) _direction = 1;
        if(xa < 0) _direction = 3;
	if(ya > 0) _direction = 2;
	if(ya < 0) _direction = 0;
		
        // kiểm tra xem liệu nước tiếp theo  có đi được không ? 
	if(canMove(0, ya)) {           
            _y += ya;
	}
		
	if(canMove(xa, 0)) {
            _x += xa;
	}   
             
    }

    @Override
    public boolean collide(Entity e) {                
        //xử lý va chạm với Enemy
        if ( e instanceof Enemy ){
            kill();
            return true;           
        }
        // xư lí va chạm với mép ngoài cùng của tia lửa
        
         if(e instanceof FlameSegment) {
            kill();
        }
         
         // xử lý va chạm với Flame của bom 
        if(e instanceof Flame  ) {
            kill();
            return false;
	}
       if ( e instanceof Bomb ){
          if ( ((Bomb) e ).isExploded() == true && ((Bomb)e).isAllowedToPassThroght()==true ){
           kill();   
          }
       }

        return true;
    }

    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }

    public void getAllInfor(){
        System.out.println("radius= " + Game.getBombRadius());
        for ( int i =0 ; i < this._bombs.size();i++){
            System.out.println("x= " + this._bombs.get(i).getXTile()+ "y= " + this._bombs.get(i).getYTile());
            
        }
    }
    // thêm hàm

    public List<Bomb> getBombs() {
        return _bombs;
    }
    
     
}
