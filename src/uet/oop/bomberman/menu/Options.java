


package uet.oop.bomberman.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.gui.Frame;


/**
 * dùng java Swing
 * @author DoQuangTrung
 */
public class Options extends JMenu implements ChangeListener{
    Frame _frame;
	
	public Options(Frame frame) {
		super("Pause/Resume");
		
		_frame = frame;
		// pause
		JMenuItem pause = new JMenuItem("Pause");
		pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		pause.addActionListener(new MenuActionListener(frame));
		add(pause);
		
                // résume
		JMenuItem resume = new JMenuItem("Resume");
		resume.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		resume.addActionListener(new MenuActionListener(frame));
		add(resume);
		
		
	}

   
	
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
	    if (!source.getValueIsAdjusting()) {
	        int fps = (int)source.getValue();
	        
	        Game.SCALE = fps;
	        System.out.println( Game.SCALE);
	        
	        _frame._gamepane.changeSize();
			  _frame.revalidate();
			  _frame.pack();
	    }
	}
	
	class MenuActionListener implements ActionListener {
		public Frame _frame;
		public MenuActionListener(Frame frame) {
			_frame = frame;
		}
		
		  @Override
		public void actionPerformed(ActionEvent e) {
			  
			  if(e.getActionCommand().equals("Pause")) {
				  _frame.pauseGame();
			  }
				  
			  if(e.getActionCommand().equals("Resume")) {
				  _frame.resumeGame();
			  }
		  }
	}
    
    
    
}
