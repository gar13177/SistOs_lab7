import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Runner {
	
	private Random rnd = new Random();
	private Puente puente;
	private int personas;
	Scanner sc = new Scanner(System.in);
	private ArrayList<Thread> caminantes;
	
	public Runner( ){
		System.out.print("Ingrese la cantidad de personas ");
		this.personas = sc.nextInt();
		this.puente = new Puente();
		this.caminantes = new ArrayList<Thread>();
		
		for (int i = 0; i < this.personas; i ++){
			Thread a = new Thread(new Caminante(rnd.nextInt(2), puente));
			a.setName(""+i);
			caminantes.add(a);
		}
		
		for (Thread th: caminantes){
			th.start();
		}
		
		for (Thread th: caminantes) {
			try{
				th.join();
			}catch (InterruptedException  e){
				e.printStackTrace();
			}
		}
		System.out.println("\nEjecucion finalizada");
	}
	

}
