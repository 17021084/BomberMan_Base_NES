/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uet.oop.bomberman.menu;

import javax.swing.JMenuBar;
import uet.oop.bomberman.gui.Frame;

/**
 *
 * @author DoQuangTrung
 */
public class Menu  extends JMenuBar {
    public Menu(Frame frame) {
		add( new Game(frame));
		add( new Options(frame) );
		
	}
    
    
}
