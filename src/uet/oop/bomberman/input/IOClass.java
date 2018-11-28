/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uet.oop.bomberman.input;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author DoQuangTrung
 */
public class IOClass {
        public static String path = "res\\highscore.txt";
    
    
    public static String Read(){
        // init
        String str = null;
        File file =new File (path);
     
        try {
                // Input class
                Scanner sc = new Scanner(file);
             // loop read whole file
               while (sc.hasNext())  {
           
          
           str = sc.nextLine();// real all file
           
           }
           
       }catch (Exception e){     
           e.getMessage(); // get error mess
       }  
           return str;
        
    }
    
     public static void write( int point)  { 
         File file =new File (path);
         
        
         
        
          
          // fiewriter , in case  true  write at endof file, false overwrite
        try (FileWriter fw = new FileWriter (file,false);
                BufferedWriter bf = new BufferedWriter(fw); // buffer
                PrintWriter pw = new PrintWriter(bf) ){
            
          pw.println(point);
        }
        catch (Exception e){
        }
       
       
        
    }
    
    
   
}
