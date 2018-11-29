package uet.oop.bomberman;

import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.gui.Frame;
import uet.oop.bomberman.input.Keyboard;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import static sun.net.www.http.HttpClient.resetProperties;
import uet.oop.bomberman.input.IOClass;
import uet.oop.bomberman.sound_effective.Sound;

/**
 * Tạo vòng lặp cho game, lưu trữ một vài tham số cấu hình toàn cục,
 * Gọi phương thức render(), update() cho tất cả các entity
 */
public class Game extends Canvas {
        
    
        //------------------------------------------------------------------------------
            
            //Hằng số mặc định
    
	public static final int TILES_SIZE = 16, // độ to của 1 ô gạch
                               
                        // thông số dài rộng theo cái titles size
				WIDTH = TILES_SIZE * (31/2), // 13 ô chiều ngang
				HEIGHT = 13 * TILES_SIZE; // 13 viên  chiều dọc
                         
        public static int WIDTHTile = 31;
        public static int HEIGHTTile = 13;     
        
        // tỉ lệ ? 
	public static int SCALE = 3;
	
        // tên cửa sổ
	public static final String TITLE = "Bomberman_Game_Upgrade_by_Do_Quang_Trung";
	
        // chỉ số vể nhân vật là hằng số  mặc định
	private static final int BOMBRATE = 1; // số bom có thể đặt
	private static final int BOMBRADIUS =1; // độ dài của vụ nổ
	private static final double BOMBERSPEED = 1.0; // tốc độ của nhân vật bomber
	
	public static final int TIME = 200; // thời gian hết một màn
	public static final int POINTS = 0; // điểm đạt được    
	public static  int LIVES = 3; // mang
     
        // đổ lại hướng di chuyển
        public static  boolean REVERSE = false;
        
        
        public static final int HIGHSCORE = ( new Integer(IOClass.Read())); // đọc và điểm cao nhất
        
	protected static int SCREENDELAY = 3; // delay màn hình
        
        //----------------------------------------------------------------------
        
        
        
       
        
        // chỉ số nhân vật ,  chắc là  thể thay đổi trong tương lai
	protected static int bombRate = BOMBRATE;   // số quả bom đc dặt
	protected static int bombRadius = BOMBRADIUS;
	protected static double bomberSpeed = BOMBERSPEED;
	protected static int lives=LIVES;
	
	protected int _screenDelay = SCREENDELAY;
	//-------------------------------------------
        
        
	private Keyboard _input;
        //nút pause
	private boolean _running = false;
	private boolean _paused = true;
	
        
	private Board _board;
	private Screen screen;
	private Frame _frame;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game(Frame frame) {
                // sét khung hình
		_frame = frame;
		_frame.setTitle(TITLE); // tên 
		// thông số của màn hình
		screen = new Screen(WIDTH, HEIGHT);
		// sự kiện bàn phím
                _input = new Keyboard();
		
		_board = new Board(this, _input, screen);
		addKeyListener(_input);
	}
	
	
	private void renderGame() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		//xóa
		screen.clear();
		// hiển thị lại
		_board.render(screen);
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen._pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		_board.renderMessages(g);
		
		g.dispose();
		bs.show();
	}
	
	private void renderScreen() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		
		Graphics g = bs.getDrawGraphics();
		
		_board.drawScreen(g);

		g.dispose();
		bs.show();
	}

	private void update() {
            
		_input.update();
		_board.update();
	}
	
	public void start() {
		_running = true;
		
		long  lastTime = System.nanoTime(); // thời gian thực trong quá khư , vòng loob cũ
		long timer = System.currentTimeMillis();
                final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second ( tần số f) vậy 1/ns là thời gian load 1 frame
       		double delta = 0;
		int frames = 0;
		int updates = 0;
                
		requestFocus();
                Sound.playBackGround();
                Keyboard _input = new Keyboard();
                
                
		while(_running) {
//                    if ( this._paused!=true ){
//                        if (_input.plause){
//                            System.out.println("plause game");
//                           // this.pause();
//                        }                            
//                    }
                    
			long now = System.nanoTime(); // thời gian bây h
			delta += (now - lastTime) / ns;   // denta t tức là thời gian  vòng loob cũ .  đơn vị  s^2 
                        // denta =1 ; now -lastTime = ns  => thời gian của của 1 vòng loob = T chu kì chuẩn
                        
                        
			lastTime = now; // để last time  cho vòng loob while sau tính
                        
                        // đo vòng while  loob lớn  trong bao lâu
                        
                        // nếu denta t >=1  thì thơi gian mỗi vòng loob sẽ = với T thời gian 1 vòng loob chuẩn
                        
			while(delta >= 1) {
				update();
				updates++;
				delta--;
			}
			
                        //------------------------------------------------------
                        // nút pause
			if(_paused) {
				if(_screenDelay <= 0) { // nếu thời gian plause  hết thì tắt nút plause
					_board.setShow(-1);
					_paused = false;
				}
                                
                                // hiển thị cái screen
				renderScreen();
			} else {
                            // hển thị game bình thường
				renderGame();
                                if(_input.plause){
                                
                                }
			}
                        //------------------------------------------------------
                        
                        
			frames++;
                        
			if(System.currentTimeMillis() - timer > 1000) {
				_frame.setTime(_board.subtractTime());
				_frame.setPoints(_board.getPoints());
                                _frame.setLives(_board.getLives());
                               // quy trình : info goi Pannel goi frame goi trong day 
                                
				timer += 1000;
                                // in thông số 
				_frame.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");
				updates = 0;
				frames = 0;
				
				if(_board.getShow() == 2)
					--_screenDelay;
			}
                        
		}
                
	}
	
        //----------------------------------------------------------------------
        // getter method
        
        public Board getBoard() {
		return _board;
	}
        
	public static double getBomberSpeed() {
		return bomberSpeed;
	}
	
	public static int getBombRate() {
		return bombRate;
	}
	
	public static int getBombRadius() {
		return bombRadius;
	}
        
        //----------------------------------------------------------------------
        
        // khi ăn iteam sẽ tăng tốc lên i
	
	public static void addBomberSpeed(double i) {
		bomberSpeed += i;
	}
	
	public static void addBombRadius(int i) {
		bombRadius += i;             
	}
	
	public static void addBombRate(int i) {
		bombRate += i;
	}
        // theem mang
        public static void addLives(int i){
            lives += i;
        }
        
        //---------------------------------------------------------------------
        
	public void resetScreenDelay() {
		_screenDelay = SCREENDELAY;
	}

	// nút pause
        //----------------------------------------------------------------------

	public boolean isPaused() {
		return _paused;
	}
	
	public void pause() {
		_paused = true;
	}
	
        public void unpause(){
            _paused=false;
        }

        public void run() {
		_running = true;
		_paused = false;
	}
        
        public boolean isRunning() {
		return _running;
	}
        
        // khi chết đi sống lại
        public static void resestAllPower(){
            bomberSpeed =BOMBERSPEED;
            bombRadius  = BOMBRADIUS;
            bombRate = BOMBRATE;
            Game.REVERSE = false;
        }
        public static void setReverser(){
            Game.REVERSE = true;
        }
               
}
