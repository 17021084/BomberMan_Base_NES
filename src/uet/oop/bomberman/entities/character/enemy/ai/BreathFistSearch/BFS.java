
package uet.oop.bomberman.entities.character.enemy.ai.BreathFistSearch;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import uet.oop.bomberman.Game;

/**
 *
 * @author DoQuangTrung
 */
public class BFS {

    /**
     * @param args the command line arguments
     */
      public ArrayList<Integer> path = new ArrayList();
      
      public int width =Game.WIDTH; // số đỉnh dọc
      public int height = Game.HEIGHT; // số đỉnh ngang       
      public int nameOfVertext;
      public int numOfNode= (width+1)*(height) ;
     
     /**
      * mảng node làm ma trận đỉnh kê
      *  số hàng=số đỉnh.
      *  vì mỗi đỉnh có tối đa 4 cạnh kề
      * 0 là không kề với đỉnh nào , node lưu đỉnh kề
      */
      
      
      
      public int[][] node = new int[numOfNode][4]; 
      
      // gia tri matrix[x][y] la ten cua dinh tuong ung voi vi tri x y
      public int[][] matrix = new int [1000][1000];
      public String linePath ="board.txt";
      public ArrayList<String> boardGame = new ArrayList();
      
      
      /**
       * đọc map từ file
       */
      public  void  readLevelFromFile(){
        // init
        String str = null;
        File file =new File (this.linePath);
     
        try {
                // Input class
                Scanner sc = new Scanner(file);
                
             // loop read whole file
            // sc.nextLine();
            while (sc.hasNext())  {
           
          
                str = sc.nextLine();// real all file
                boardGame.add(str);
          
            }
                     
       }catch (Exception e){     
           e.getMessage(); // lấy lỗi
       }  
          
    }
      
     /**
       *   chuyển nếu # thì sẽ là 0  không đi được
       *    Chuyeenr neeus *x thì tương ứng là đỉnh âm vì có hể đi đươc trong tương lai
       *    còn lại là đi được tương ứng với các dỉnh
       *
       */
       
    public void convertToMatrix(){
       
        int nameOfVertext=1;
         for ( int i =1 ; i <this.boardGame.size() ; i++){
              for ( int j=0 ;j<this.boardGame.get(i).length() ; j++ )
                    
                if (  this.boardGame.get(i).charAt(j)=='#'  ){
                            this.matrix[i][j]=0;
                            
               }else if ( this.boardGame.get(i).charAt(j)=='*'|| this.boardGame.get(i).charAt(j)=='x' ){
                   
                   this.matrix[i][j]=nameOfVertext*(-1);
                             nameOfVertext++;
             
                     }else{
                             this.matrix[i][j]=nameOfVertext;
                             nameOfVertext++;
                }
              
         }
         
         for ( int i =1 ; i <= 13 ; i++){
              for ( int j=0 ;j<31 ; j++ ){
                  System.out.print(this.matrix[i][j] +"\t\t");  
              }
              System.out.println("\n");
         }
         
         
    }
    /**
        từ ma trận đỉnh chuyển sang ma trân cạnh kề
    
    */
    
    
        public void convertNearNodeMatrix(){
            
             for ( int i =1 ; i < this.height ; i++){  
                    for ( int j=1 ;j<this.width ; j++ ){
                         if ( this.matrix[i][j] > 0 ){
                             // cùng hàng
                             
                                // bên trái
                             if (this.matrix[i][j-1] > 0 ){
                                 this.node[ this.matrix[i][j] ][0]=this.matrix[i][j-1];
                             }else{
                                 this.node[this.matrix[i][j]][0]=0; // không có đỉnh kể
                             }
                                 //bên phải
                             if (this.matrix[i][j+1] > 0 ){
                                 this.node[ this.matrix[i][j] ][1]=this.matrix[i][j+1];
                             }else{
                                 this.node[this.matrix[i][j]][1]=0;
                             }
                             
                             //cùng cột
                                   //bên trên
                             
                             if (this.matrix[i-1][j] > 0 ){
                                 this.node[ this.matrix[i][j] ][2]=this.matrix[i-1][j];
                             }else{
                                 this.node[this.matrix[i][j]][2]=0;
                             }
                             
                                   //bên dưới
                             if (this.matrix[i+1][j] > 0 ){
                                 this.node[ this.matrix[i][j] ][3]=this.matrix[i+1][j];
                             }else{
                                 this.node[this.matrix[i][j]][3]=0;
                             }
                             
                         }
                        
                      }
             }
            
            
            
        }
       
        
        
        
        
        
    /**
     * 
     * @param start : đỉnh ban đầu
     * @param end   : đỉnh kết thúc
     * @throws IllegalStateException 
     */   
      
     
      
    void bfs( int start , int end  ) throws IllegalStateException { // exception khi que ko còn chỗ
         // tao cai  Queue node
        Queue<Integer> qNode = new LinkedList<Integer>(); 

        
        
        
        int [] parent = new int[numOfNode+1];
        boolean [] visted = new boolean[numOfNode+1];
        
        // System.out.println("");
        
        
        // sét nhan cho đinh, xét đỉnh cha
        visted[start] = false;
        parent[start] = -1; // sét mặc định đỉnh cha 
        parent[end]=-1;
       
        // thêm đỉnh start vào đầu
        qNode.add(start);
        
        while ( !qNode.isEmpty()){
            // dequeue phanaf tử đầu tiên ra 
            int currentNode = qNode.poll();
          
            // duyệt toàn bộ đỉnh kể với current , nếu chưa visit thì dán cho là visit
            for ( int i = 0 ; i<4 ; i++ ){
                if ( visted[node[currentNode][i]]==false && node[currentNode][i]!=0 ) {
                    // dán nhãn đã thăm
                    visted[node[currentNode][i]]= true;
                    // gán đỉnh cha
                    parent[node[currentNode][i]] = currentNode;
                    // cho vào queue
                    qNode.add(node[currentNode][i]);
                    
                }
                  
            }         
        }
        
        
        
        // xuất đường đi ngắn nhất ra
        int p = parent[end];
        
        // thêm node cuối
      
       if (p == -1){
           System.out.println(" ko co duong di");
       }else {
             path.add(end);
             path.add(p);
             while ( p!=start){ // chu di den goc
                 p = parent[p];
                 path.add(p);
            }
             for ( int i =0 ; i < path.size() ; i++ ){
                System.out.println(path.get(i)+ " ");
            }
       }
      
       
       
    }
    

       
       
       
       
//// test
//    public static void main (String [] args ){
//        BFS b = new BFS();
//      
//       b.readLevelFromFile();
//       for ( int i =1 ; i <b.boardGame.size() ; i++){
//               System.out.println( b.boardGame.get(i));
//            }
//      b.convertToMatrix();
//      b.convertNearNodeMatrix();
//      //  System.out.println( b.node[198][0] );
//              b.bfs(1, 180);
//       
//       
//       
//    }   
    
}
