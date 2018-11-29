/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.character.enemy.Minvo;

/**
 *  dùng AIRandom cho Minvo  cho màn có  AIBomber  chơi
 * @author DoQuangTrung
 */
public class AIRandom extends AI{

    

   

    @Override
    public int calculateDirection() {       
        return random.nextInt(4);
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
