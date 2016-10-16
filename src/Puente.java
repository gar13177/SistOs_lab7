import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Puente {
	
	private Random rnd = new Random();
	private final Lock lock = new ReentrantLock();
	private final Condition izquierda  = lock.newCondition(); 
	private final Condition derecha = lock.newCondition(); 
	
	
	int pc;//personas caminando
	int ei;//esperando hacia la izquierda
	int ed;//esperando hacia la derecha
	int da;//direccion actual
	
	public Puente(){
		this.pc = 0;
		this.ei = 0;
		this.ed = 0;
		this.da = -1;//0 = derecha, 1 = izquierda
	}
	
	public void AccederAlPuente (int direccion) {
		lock.lock();
		try {
			boolean condicion = (pc >= 4) || (da != direccion && da != -1);
			while ( condicion ) {
				if ( direccion == 0 ){// 0 = derecha
					ed++;
					derecha.await();
					ed--;
				}else{// 1 = izquierda
					ei++;
					izquierda.await();
					ei--;
				}
				condicion = (pc >= 4) || (da != direccion && da != -1);
			}			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		pc++;
		da = direccion;
		if ( direccion == 0 ){// 0 = derecha
			System.out.println("Thread "+Thread.currentThread().getName()+" camina a la derecha");
		}else{// 1 = izquierda
			System.out.println("Thread "+Thread.currentThread().getName()+" camina a la izquierda");
		}
		
		lock.unlock();
		try {
			Thread.sleep(Long.valueOf((long)rnd.nextDouble()*2000+1000));
		} catch (Exception e){
			e.printStackTrace();
		}
		SalirDelPuente();
	}
	
	public void SalirDelPuente () {
		lock.lock();
		System.out.println("Saliendo Thread "+Thread.currentThread().getName());
		pc--;
		if ( pc <= 0 ) da = -1;
		if ( ed > 0 ) derecha.signal();
		if ( ei > 0 ) izquierda.signal();
		lock.unlock();
	}

}
