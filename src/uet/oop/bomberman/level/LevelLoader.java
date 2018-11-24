package uet.oop.bomberman.level;

import java.util.ArrayList;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.exceptions.LoadLevelException;

/**
 * Load và lưu trữ thông tin bản đồ các màn chơi
 */
public abstract class LevelLoader {

	protected int _width , _height ; // default values just for testing
	protected int _level;
	protected Board _board;
        
        // mảng đệm để đọc từ map từ file text rồi sẽ dùng để convert sang ma trân 2 chiều chỉ có số     
      //  public ArrayList<String> boardGame = new ArrayList();
       
        
        
	public LevelLoader(Board board, int level) throws LoadLevelException {
		_board = board;
		loadLevel(level);
	}

	public abstract void loadLevel(int level) throws LoadLevelException;

	public abstract void createEntities();

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public int getLevel() {
		return _level;
	}

}
