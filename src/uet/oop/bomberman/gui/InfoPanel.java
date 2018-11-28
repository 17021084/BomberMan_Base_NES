package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Swing Panel hiển thị thông tin thời gian, điểm mà người chơi đạt được
 */
public class InfoPanel extends JPanel {
	// nhãn 
	private JLabel timeLabel;
	private JLabel pointsLabel;
        private JLabel livesLabel;
        private JLabel highScore;
       
	public InfoPanel(Game game) {
		setLayout(new GridLayout());
		
		timeLabel = new JLabel("Times: " + game.getBoard().getTime());
		timeLabel.setForeground(Color.WHITE);
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		
		pointsLabel = new JLabel("Points: " + game.getBoard().getPoints());
		pointsLabel.setForeground(Color.cyan);
		pointsLabel.setHorizontalAlignment(JLabel.CENTER);
		
                livesLabel = new JLabel("Lives: " + game.getBoard().getLives());
		livesLabel.setForeground(Color.red);
		livesLabel.setHorizontalAlignment(JLabel.CENTER);
		
                highScore = new JLabel("High Scores : " + game.getBoard().getHighScores());
		highScore.setForeground(Color.GREEN);
		highScore.setHorizontalAlignment(JLabel.CENTER);
		
                           
                
                
                add(timeLabel);
		add(pointsLabel);
		add(livesLabel);
		add(highScore);
                
                        setBackground(Color.black);
		setPreferredSize(new Dimension(0, 40));
	}
	// hiển thị thời gian
	public void setTime(int t) {
		timeLabel.setText("Time: " + t);
	}
        // hiển thị điểm
	public void setPoints(int t) {
		pointsLabel.setText("Score: " + t);
	}
	// hiển thị mạng
        public void setLives(int t) {
		livesLabel.setText("Lives: " + t);
		
	}
        
}
