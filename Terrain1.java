
import java.util.concurrent.locks.*;
/**
 * Native monitor based Terrain
 * 
 * @author CSD Juansa Sendra
 * @version 2021
 */
public class Terrain1 implements Terrain {
    Viewer v;
   // creamos un ReentrantLock y una cola condition para la clase 
    ReentrantLock lock;
    Condition cond;
    
    
    public  Terrain1 (int t, int ants, int movs, String msg) {
        v=new Viewer(t,ants,movs,msg);
        lock=new ReentrantLock();
         cond = lock.newCondition();
        //esperar = lock.newCondition();
       
        
    }
    // Como hemos quitado los syncronized de los metodos, los quales hacen el lock y condition de forma implicita

    // tenemos que bloquear y desbloquear de forma manual cada metodo que tenga uns sección critica.

    // Usamos el try finally porque nos tenemos que asegurar de que pase lo que pase dentro de la ejecución del programa
    // el bloqueo se deshaga.

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
        try{
            cond.signalAll();
            v.bye(a);

     }finally{
        lock.unlock();
     }
    
    }


    public  void move(int a) throws InterruptedException {
        v.turn(a); 
        Pos dest=v.dest(a); 

        lock.lock();
       
        try{
           
            while (v.occupied(dest)) {

                try{
                    cond.await(); // simplemente cambiamos el wait del lock por defecto al homonimo del Reentraint
                }catch(InterruptedException e){}
                    //wait();
                v.retry(a);
            }

            v.go(a); 
            cond.signalAll(); // simplemente cambiamos el notifyAll del lock por defectro al homonimo del Reentraint

        }finally{lock.unlock();}

       
    } 
}