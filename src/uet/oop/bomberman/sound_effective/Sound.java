
package uet.oop.bomberman.sound_effective;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

/**
 *  chơi nhạc
 * @author DoQuangTrung
 */


public class Sound {
//    
    public static String bombExplose = "res\\sound\\bomb_bang.wav";
    public static String startStage ="res\\sound\\startstage.wav";   
    public static String bomberDie = "res\\sound\\bomber_die.wav";
    public static String item = "res\\sound\\item.wav";  
    public static String lose = "res\\sound\\lose.mid";
    public static String menu = "res\\sound\\menu.wav";
    public static String mosterDie = "res\\sound\\monster_die.wav";
    public static String newbomb = "res\\sound\\newbomb.wav";
    public static String win = "res\\sound\\win.wav";
    public static String backgroundGame = "res\\sound\\2stepfromhell.wav";
    public static String playGame = "res\\sound\\playgame.mid";
    public static String destroy ="res\\sound\\destroy.wav";
    
    public Sound() {
    }
    
    
    
    public static void play( String filePath){
       InputStream music;
       try{
           
           music = new FileInputStream ( new File ( filePath) );
           AudioStream  audio = new AudioStream (music);
           AudioPlayer.player.start(audio);
           
          
       }catch(IOException e) {
           e.printStackTrace();
       }
        
        
    }
    
   
    
    /**
     * các hàm gọi trong các trường hợp trong game
     * 
     * 
     * 
     */
    
    
    public static void playDestroy(){
        Sound.play(destroy);
    }
    
    public static  void playBombExplose(){
        Sound.play(bombExplose);
    }
    public static void playStartStage(){
        Sound.play(startStage);
    } 
            
    public static void playBomberDie(){
        Sound.play(bomberDie);
    }
    public static void playGetNewItem(){
          Sound.play(item);
    }
    public static void playLose(){
          Sound.play(lose);
    }
    public static void playMenu(){
          Sound.play(menu);
    }
    public static void playMosterDie(){
          Sound.play(mosterDie);
    }
    public static void playPlaceNewBomb(){
          //for ( int i = 0 ; i < 10000 ; i++)
            Sound.play(newbomb);
         // Sound.playBombExplose();
    }
    public static void playWin(){
          Sound.play(win);
    }
    public static void playBackGround(){
          Sound.play(backgroundGame);
    }
    
     
}
