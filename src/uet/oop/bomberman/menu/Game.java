/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uet.oop.bomberman.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import uet.oop.bomberman.gui.*;
import uet.oop.bomberman.input.IOClass;

/**
 *
 * @author DoQuangTrung
 */
public class Game extends JMenu{
    
    
        
	public Frame frame;
	
	public Game(Frame frame) {
		super("Game");
		this.frame = frame;
		
		/*
		 * New Game
		 */
		JMenuItem newgame = new JMenuItem("New Game");
		newgame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newgame.addActionListener((ActionListener) new MenuActionListener(frame));
		add(newgame);
		
		/*
		 * Scores
		 */
                // điểm cao
                Integer highScore = new Integer( IOClass.Read());
		
                JMenuItem scores = new JMenuItem("High Scores : " +highScore);
                
                
//		scores.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
		//scores.addActionListener(new MenuActionListener(frame));
		add(scores);
		
		
	}
	
	class MenuActionListener implements ActionListener {
		public Frame _frame;
		public MenuActionListener(Frame frame) {
			_frame = frame;
		}
		
		  @Override
		public void actionPerformed(ActionEvent e) {
			  
			  if(e.getActionCommand().equals("New Game")) {
				  _frame.newGame();
			  }
			
		  }
		}

}

