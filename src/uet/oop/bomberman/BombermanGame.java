package uet.oop.bomberman;

import uet.oop.bomberman.gui.Frame;
import uet.oop.bomberman.sound_effective.Sound;

public class BombermanGame {
	
	public static void main(String[] args) {
          Sound.playStartStage();
          new Frame();
	}
}
