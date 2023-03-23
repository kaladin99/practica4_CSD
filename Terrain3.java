
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;
/**
 * Native monitor based Terrain
 * 
 * @author CSD Juansa Sendra
 * @version 2021
 */
public class Terrain3 implements Terrain {
    Viewer v;
    
    ReentrantLock lock;
    Condition [][] cond;
    

    public  Terrain3 (int t, int ants, int movs, String msg) {
        v=new Viewer(t,ants,movs,msg);
        lock = new ReentrantLock();
        cond = new Condition [t][t];

        for(int i = 0;  i <t; i ++){
            for(int j = 0; j< t ;j++){
                cond[i][j] = lock.newCondition();
            }
           }
            
    
        
       // esperar = lock.newCondition();
        
    }

    public  void hi(int a) {
        lock.lock();
       
        
        try{
            v.hi(a);
        }finally{
            lock.unlock();
        }
       
    }

    public  void bye(int a) {
        lock.lock();
        Pos act = v.getPos(a);
        try{
            cond[act.x][act.y].signalAll();
            v.bye(a);

     }finally{
        lock.unlock();
     }
    
    }


    public  void move(int a) throws InterruptedException {
        v.turn(a); 
        Pos dest =v.dest(a); 
        Pos act = v.getPos(a);

        lock.lock();
       
        try{
           
            while (v.occupied(dest)) {

                try{
                    //Añadimos un timepo de espera al await y si se cumple este, cam
                   if( cond[dest.x][dest.y].await(10000,TimeUnit.MICROSECONDS )== false ){
                     v.chgDir(a);
                     // importante actualizar el destino
                     dest= v.dest(a);
                   }
                   
                }catch(InterruptedException e){}
                    //wait();
                //v.retry(a);
            }

            v.go(a); 
            cond[act.x][act.y].signalAll();

        }finally{lock.unlock();}

       
    } 
}
