package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;
import uet.oop.bomberman.menu.Menu;
/**
 * Swing Frame chứa toàn bộ các component
 */
public class Frame extends JFrame {
	
        
	public GamePanel _gamepane;
	private JPanel _containerpane;
	private InfoPanel _infopanel;
	
	private Game _game;

	public Frame() {
		 setJMenuBar(new Menu(this));
	
                _containerpane = new JPanel(new BorderLayout());
		_gamepane = new GamePanel(this);
		_infopanel = new InfoPanel(_gamepane.getGame());
		
		_containerpane.add(_infopanel, BorderLayout.PAGE_START);
		_containerpane.add(_gamepane, BorderLayout.PAGE_END);
		
		_game = _gamepane.getGame();
		
		add(_containerpane);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);	
		
		_game.start();
	}
	
	public void setTime(int time) {
		_infopanel.setTime(time);
	}
	
	public void setPoints(int points) {
		_infopanel.setPoints(points);
	}
	// plause game
        public void pauseGame() {
		_game.getBoard().gamePause();
	}
        public void resumeGame() {
		_game.getBoard().gameResume();
	}
        public boolean isRunning() {
		return _game.isRunning();
	}
        // game
            // gọi ván mới
        public void newGame() {
      
        _game.getBoard().newGame();
// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
       
}
