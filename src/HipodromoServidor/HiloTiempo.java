package HipodromoServidor;

public class HiloTiempo extends Thread {
	
	private ServidorHipodromoThread servidor;
	
	public final static int TIEMPO=30;
	

	private int segundos;
	
	
	public HiloTiempo(ServidorHipodromoThread i){
		servidor=i;
		
	}
	@Override
	public void run() {
		super.run();
		segundos=TIEMPO;
		
		while (servidor.cronometro()) {
			try {
				sleep(1000);
				segundos=segundos-1;
				
//				System.out.println(segundos);
				
				if(segundos==0){
					throw new Exception();
				}
				
			} catch (Exception e) {
				servidor.cambiarCronometro();
				servidor.seguir();
			} 
			
		}
	}
	

}

