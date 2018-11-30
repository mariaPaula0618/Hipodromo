package LogicaHipodromo;

public class Caballo {

	private String nombreCaballo;
	private double apuesta;
	private boolean estaEnMovimiento;
	private int velocidad;



	public Caballo(String nombreCaballo, int velocidad) {
		this.nombreCaballo=nombreCaballo;
		apuesta=0;
		this.velocidad=velocidad;
		estaEnMovimiento=true;
	}



	/**
	 * @return the nombreCaballo
	 */
	public String getNombreCaballo() {
		return nombreCaballo;
	}



	/**
	 * @param nombreCaballo the nombreCaballo to set
	 */
	public void setNombreCaballo(String nombreCaballo) {
		this.nombreCaballo = nombreCaballo;
	}



	/**
	 * @return the apuesta
	 */
	public double getApuesta() {
		return apuesta;
	}



	/**
	 * @param ap the apuesta to set
	 */
	public void setApuesta(double ap) {
		this.apuesta += ap;
	}



	/**
	 * @return the estaEnMovimiento
	 */
	public boolean isEstaEnMovimiento() {
		if(velocidad==800) {
			estaEnMovimiento=false;
		}
		return estaEnMovimiento;
	}



	/**
	 * @param estaEnMovimiento the estaEnMovimiento to set
	 */
	public void setEstaEnMovimiento(boolean estaEnMovimiento) {
		this.estaEnMovimiento = estaEnMovimiento;
	}



	/**
	 * @return the velocidad
	 */
	public int getVelocidad() {
		return velocidad;
	}



	/**
	 * @param velocidad the velocidad to set
	 */
	public void setVelocidad(int velocidad) {
		this.velocidad += velocidad;
	}

}
