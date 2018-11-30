package LogicaHipodromo;

import java.util.ArrayList;
import java.util.Random;

public class HiloCaballo extends Thread {
	
	private int[] velocidad;

	private Caballo caballo;
	private ArrayList<Caballo> ganador;

	public HiloCaballo(Caballo caballo, ArrayList<Caballo> caballoGanador) {
		this.caballo=caballo;
		velocidad = new int[] {10,20,30,40,50};
		ganador=caballoGanador;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			int movimiento=0;
			Random rdn= new Random();
		while(movimiento<800) {
			sleep(500);
			caballo.setVelocidad((int)(Math.random()*20)+1);
			movimiento=caballo.getVelocidad();
			System.out.println(movimiento);
			
		}
		System.out.println("Termine");
		ganador.add(caballo);
		
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
