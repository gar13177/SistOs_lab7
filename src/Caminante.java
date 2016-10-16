
public class Caminante implements Runnable {
	
	private int direccion;
	private Puente puente;
	
	public Caminante(int direccion, Puente puente){
		this.direccion = direccion;
		this.puente = puente;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		puente.AccederAlPuente( direccion );
	}

}
