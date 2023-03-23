
import java.util.concurrent.locks.*;
/**
 * Native monitor based Terrain
 * 
 * @author CSD Juansa Sendra
 * @version 2021
 */
public class Terrain2 implements Terrain {
    Viewer v;
    
    ReentrantLock lock;
    Condition [][] cond;
    

    public  Terrain2 (int t, int ants, int movs, String msg) {
        v=new Viewer(t,ants,movs,msg);
        lock = new ReentrantLock();

        // Generamos una matriz vacia de condition con el tamaño del terreno

        cond = new Condition [t][t];

        // Rellenamos la matriz asociando a cada condition el lock
        for(int i = 0;  i <t; i ++){
            for(int j = 0; j< t ;j++){
                cond[i][j] = lock.newCondition();
            }
           }
            
        
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
        // Obtenemos la posición actual de la hormiga
        Pos act = v.getPos(a);
        try{
            //Avisamos a la cola de la celda actual de la hormiga 
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
                    // nos esperamos en la cola de la celda destino de la hormiga
                    cond[dest.x][dest.y].await();
                }catch(InterruptedException e){}
                    //wait();
                //v.retry(a);
            }

            v.go(a); 
            //Avisamos a la cola de la celda actual de la hormiga 
            cond[act.x][act.y].signalAll();

        }finally{lock.unlock();}

       
    } 
}
