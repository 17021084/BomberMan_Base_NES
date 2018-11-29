/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uet.oop.bomberman.entities.character.enemy.ai;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.level.FileLevelLoader;

/**
 *  AI cho bomberman
 * 
 * bomber chỉ đặt 1 quả bom
 * 
 * bot là minvo tốc độ chậm hơn bomber;
 * bot có 1 con
 * 
 * @author DoQuangTrung
 */
public class AIBomberMan extends AI{
    Bomber _bomber;
    Board _board;
    public int heigh = Game.HEIGHTTile; // cột dọc
    public int width  = Game.WIDTHTile;	// cột ngang

    public int[][] node = new int[heigh*width][4];  //ma trận đỉnh kề
    public int numOfNode = heigh*width;	 // số lượng đỉnh tối đa
    public int[][] matrix = new int[heigh][width];  // ma trận đỉnh gồm cái số nguyên âm 0 và nguyên dương. ma trận 2 chiều
    public int radius =Game.getBombRadius();// bán kính vụ nổ
    public ArrayList<Integer> vertexCanPlaceBomb = new ArrayList(); // đỉnh có thể đặt được bom
    
    
    
    public AIBomberMan( Bomber bomber , Board board){
        _bomber=bomber;
        _board= board;
        
    }
    
    /** 
     * load bản đồ từ _Map
     * chuyển sang ma trận
     */
    
    
     public  void getMatrix(){
            
             

              int nameOfVertext=1;
            for ( int i = 0 ; i < heigh ; i++){
                   for ( int j = 0 ; j < width ; j++){
                     if (  FileLevelLoader._map[i][j] =='#'  ){
                         matrix[i][j]=0;  
                     }
                     else if ( FileLevelLoader._map[i][j] =='*' || FileLevelLoader._map[i][j] == 'x' || FileLevelLoader._map[i][j] == 'b' || FileLevelLoader._map[i][j] == 'f' || FileLevelLoader._map[i][j] == 's'||FileLevelLoader._map[i][j] == 'r' ){
                         matrix[i][j]=nameOfVertext*(-1);
                         nameOfVertext++;
                     }else {
                         this.matrix[i][j]=nameOfVertext;
                             nameOfVertext++;
                     }   
                     
                }
                    
             }
             
             
        }
    
     
      /**
        *  cập nhập lại matrix  nhưng viên gạch đã phá
        */
     
     public void updateDestroy_Brick(){
            if ( Brick.Xgachvo.isEmpty() ) return;
            
            for ( int i = 0 ;i < Brick.Xgachvo.size();i++ ){
                int Xgach = Brick.Xgachvo.get(i);
                int Ygach = Brick.Ygachvo.get(i);
                // kiểm tra bị phá chưa
                if ( matrix [Ygach][Xgach] < 0 ){
                    //  nếu chưa phá ( tức đang âm ) thì cho nó  dương ( tức phá rồi)
                    //System.out.println("Sout AI medium da doi thanh cong :  x = " + Xgach + " y = "+ Ygach + " dinh thu " + matrix [Ygach][Xgach]);
                    matrix [Ygach][Xgach] =  matrix [Ygach][Xgach]*(-1);
                    
                }
                
            }
             
        }
     
     
     
    /**
        từ ma trận đỉnh chuyển sang ma trân cạnh kề dùng cho bfs
    
    */
    
    
        public void convertNearNodeMatrix(){
            
             for ( int i =1 ; i < 12 ; i++){  
                    for ( int j=1 ;j< 30 ; j++ ){
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
         * Số lượng vật thể sẽ phá được khi đặt bom vào y x
         * 
         *  xét trên matrix[y][x].
         * số vật thể có thể bị phá hủy
         *
         * 
         * @param y  hàng 
         * @param x cột
         * @return 0 1 2 3 4 số lượng vật thể sẽ 
         */
        
        
        public int numOfDestroyable( int y  ,int x){
             // bán kinh
            int number=0 ; // số lượng vật thể sẽ bị phá
           
              // nếu mà gặp tường , vật thể có thể phá ( tức đỉnh âm ) thì tăng number rồi break 


            // xét ngang
           
                    //phải     
            for ( int j=1 ; j <= radius;j++){             
                if ( this.matrix[y][x+j] < 0   ){
                    number++;
                    break;  
                }     
                // nếu gặp tường thì dừng lại. ko xét nữa
                if ( this.matrix[y][x+j] == 0){                  
                    break;  
                }
            }
            
                   //trai
            for ( int j=1 ; j <= radius; j++){
                if ( this.matrix[y][x-j] <0  ){                 
                   number++;
                    break;     
                 }      
                // nếu gặp tường thì dừng lại. ko xét nữa
                 if ( this.matrix[y][x-j] ==0  ){                                  
                    break;     
                 }     
                
                
            }    
            // xet dọc
                //dưới
            for ( int j = 1 ; j <= radius ; j++){
                if ( this.matrix[y+j][x] < 0  ){                 
                 number++;
                    break;  
                }           
                if ( this.matrix[y+j][x] == 0  ){                                
                    break;  
                }         
            }   
            
            
                //trên
            for ( int j=1 ; j <= radius;j++){
                if ( this.matrix[y-j][x] <0){                 
                    number++;
                    break;  
                }       
                if ( this.matrix[y-j][x] == 0  ){                            
                    break;  
                }         
                
            }
            
            return number;
        }
        
        
        
        
        
        
        
        
        /**
         *  kiểm tra xem liệu số v có trong mảng listV ko
         * @param v
         * @param listV
         * @return true nếu có thuộc trong listV , false nếu thuộc có;
         */
        
        
        public boolean checkVertex( int v , ArrayList<Integer> listV){
            for ( int i =0 ; i < listV.size() ; i++){
                if ( v == listV.get(i)  ) return true;
            }
            return false;
        }
        
        
        /**
         *  lấy ra những đỉnh sẽ đặt đc bom.
         * nó có đường đi an toàn, ko nằm trong flame của bom 
         * 
         * 
         */
        
        public void detectVertexPlaceBomb(){
            // những đỉnh bomber sẽ ko đi được
        ArrayList<Integer> vertextCannotThrough = new ArrayList();
        
        for ( int i =0 ; i < _board.getEnemyMinvo().size() ; i++){
             int Ye= _board.getEnemyMinvo().get(i).getYTile();
             int Xe= _board.getEnemyMinvo().get(i).getXTile();
            vertextCannotThrough.add(matrix[Ye][Xe]);         
            
        }
        
        
        for ( int i = 0 ; i < _bomber.getBombs().size() ; i++ ){
            // tọa độ bomber thứ i
             int yb= _bomber.getBombs().get(i).getYTile();
             int xb=_bomber.getBombs().get(i).getXTile();
            vertextCannotThrough.add(matrix[yb][xb]);   
          
            // xét ngang
            
            for ( int j=1 ; j <= radius;j++){
                if ( this.matrix[yb][xb+j] > 0 ){
                                                                                     
                    // dừng việc add khi gặp tường
                    
                    vertextCannotThrough.add(matrix[yb][xb+j]);
                    
                }else{
                    break;
                }              
            }
                   //trai
            for ( int j=1 ; j <= radius; j++){
                if ( this.matrix[yb][xb-j] > 0){
                    vertextCannotThrough.add(matrix[yb][xb-j]);
                  
                }else{
                    break;
                }              
            }       
            // xet dọc
                //dưới
            for ( int j=1 ; j <= radius;j++){
                if ( this.matrix[yb+j][xb] > 0   ){
                    vertextCannotThrough.add(matrix[yb+j][xb]);
                   
                }else{
                    break;
                }              
            }   
                //trên
            for ( int j=1 ; j <= radius;j++){
                if ( this.matrix[yb-j][xb] > 0 ){
                    vertextCannotThrough.add(matrix[yb-j][xb]);
                }else{
                    break;
               }            
            }
        }
        
            
        
            
            
            
            
            // thêm vào đỉnh mà  chắc chắn có thể đi dược
            
            for ( int i = 1 ; i < heigh-1 ; i++){
                   for ( int j = 1 ; j < width -1; j++){
                       
                       /**
                        * numOfDestroyable(i ,j) là số vật thể bom có thể phá hủy khi đặt tại i j 
                        * check vertex là kiểm tra xem  đỉnh i đó có thuộc tập các đỉnh ko thể qua không
                        */
                        if( this.numOfDestroyable(i, j) !=0 && this.checkVertex( matrix[i][j], vertextCannotThrough) != false ){
                            this.vertexCanPlaceBomb.add(matrix[i][j]);
                        }
                     }   
                     
                }
                   
             
    }
        
       
        
     
          int bfs( int start ) throws IllegalStateException { // exception khi que ko còn chỗ
         // tao cai  Queue node
        Queue<Integer> qNode = new LinkedList<Integer>(); 
        ArrayList<Integer> path = new ArrayList();
        
        
        
        
        
        // khai báo
        int [] parent = new int[numOfNode+1];
        boolean [] visted = new boolean[numOfNode+1];
        
        
        // sét đỉnh cha cho toàn bộ list = -1
        for ( int i = 0 ; i < this.vertexCanPlaceBomb.size() ; i++){   
            parent[i]=-1;
        }
       
        
        
        // sét nhan cho đinh, xét đỉnh cha
        // trường hợp bommer hoặc người chơi trong  khu vực bom tức đỉnh âm thì sẽ đc xử lý ở đây.
        
        if ( start < 0 ) start *=-1;
        //if ( end <0 ) end *=-1;
        
        visted[start] = false;
        parent[start] = -1; // sét mặc định đỉnh cha 
        //parent[end]=-1;
       
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
        
        
        
       
        
        // độ dài đường đi ngắn nhất
        int minPath=31*15;
        // tạm thời gán giá trị max của đường đi . 
        // đường đi dài nhất 2 điểm trong ma trận 31 *15 đó chính là 31*15  ( đường 1 chiều)
        
        // đỉnh sẽ đi tiếp theo só với start
        int nextvertex=1;
        
        
        for ( int i = 0 ; i < vertexCanPlaceBomb.size() ; i++ ){
        
          // end này là đỉnh đi đên , nó thuộc vertexcanPlaceBomb
          
                 int end=vertexCanPlaceBomb.get(i);   
        // p này là đình end . dỉnh kết thúc
                     int p = parent[end];
       
                     
        // thêm node cuối    
                     if (p != -1){
           
                       path.add(end);
                        path.add(p);
                      // độ dài đường đi
                   
                      // 
                      
                      
                      
                      while ( p!=start){ // chu di den goc
                         p = parent[p];
                        path.add(p);
                          System.out.println("bfs AI bot");
                      }
//          

                     if( path.size()<minPath){
                       //return path.get(path.size()-2);
                         minPath = path.size();
                         // đỉnh tạm thời đi tiếp theo
                         nextvertex=path.get(path.size()-2);
                         
                        }               
                           
                 }
                     
        }
       
        return nextvertex;
       
       // không tồn tại đường đi thì sẽ cho đứng im
       
    }
     
    
        
        
    //
    
    
    @Override
    public int calculateDirection() {
        this.getMatrix();
        
        
        this.updateDestroy_Brick();
        this.convertNearNodeMatrix();
         
//            đỉnh bắt đầu 
            int start = this.matrix [this._bomber.getYTile()][this._bomber.getXTile()];
        
         int result = this.bfs(start);
           
        
         if ( result - start == 1 ) return 1; // ben phai
            if ( start -  result == 1) return 3; // ssang trai
            if ( start > result ) return 0; // len tren
            if ( start < result ) return 2; // duoi
                     
            return -1;
           
    
      
    }
    
    
}
