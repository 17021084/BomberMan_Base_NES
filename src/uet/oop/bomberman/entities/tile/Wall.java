package uet.oop.bomberman.entities.tile;


import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Wall extends Tile {

	public Wall(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
        
        @Override
	public boolean collide(Entity e) {
            // nếu tính năng đảo ngược kích hoạt . thì bomber có thể đi  xuyên tường
            // tránh đi ra ngoài map
//            if ( Game.REVERSE == true ){
//               
//                   if( e instanceof Bomber){
//                        if(( ((Bomber)e).getXTile() <Game.WIDTH-1) && ( ((Bomber)e).getXTile() > 1) && ( ((Bomber)e).getYTile()< Game.HEIGHT -1) && (((Bomber)e).getYTile() >1 ) ){
//                                     return true;                      
//                        }
//                }
//            }
                return false;
	}
        
}
