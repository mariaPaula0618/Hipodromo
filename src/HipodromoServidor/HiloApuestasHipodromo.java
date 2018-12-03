package HipodromoServidor;
import java.io.*;
import java.net.*;
import java.util.Calendar;
import java.util.Scanner;

import LogicaHipodromo.Caballo;

public class HiloApuestasHipodromo extends Thread{
	Socket client;
	BufferedReader readerHS;
	PrintWriter writerHS;

	private  Caballo[] caballos;
	
	private boolean carreraTerminada;
	
/**
*
* This class handle all related to the logical of the server
*Keep information about the amount wareged to every horse
* and store the cliente  in the database
*/
	public HiloApuestasHipodromo(Socket request, Caballo[] caballo) {
		super();
		client = request;
		this.caballos=caballo;
	
	}


	public void run()
	{
		
		try {
			//lee lo que el cliente esta escribiendo
			readerHS = new BufferedReader(new InputStreamReader(client.getInputStream()));
			//Escribe en el stream de salida del servidor lo que lee el cliente
			writerHS = new PrintWriter(client.getOutputStream(), true);
			String[] apuesta = readerHS.readLine().split(",");

			double ap= Double.parseDouble((String) apuesta[1]);
			String nombreCaballo=(String) apuesta[0];
			File fileData= new File("BasesDeDatos/cedulas.txt");
			BufferedWriter base= new BufferedWriter(new FileWriter(fileData, true));
			base.newLine();
			base.write(apuesta[2]);
			base.close();
		
			while(true) {
				
					
					
					
						if(nombreCaballo.equals(caballos[0].getNombreCaballo())) {
							
							caballos[0].setApuesta(ap);
							
						}
						if(nombreCaballo.equals(caballos[1].getNombreCaballo())) {
							
							caballos[1].setApuesta(ap);
							
						}
						if(nombreCaballo.equals(caballos[2].getNombreCaballo())) {
							
							caballos[2].setApuesta(ap);
							
						}
						if(nombreCaballo.equals(caballos[3].getNombreCaballo())) {
						
							caballos[3].setApuesta(ap);
							
						}
						if(nombreCaballo.equals(caballos[4].getNombreCaballo())) {
							
							caballos[4].setApuesta(ap);
							
						}if(nombreCaballo.equals(caballos[5].getNombreCaballo())) {
							
							caballos[5].setApuesta(ap);
							
						}
						
					}
					
					//Al comparar valores String debo usar equal no "=="



				}

			



		 catch (IOException e) {
			// TODO Auto-generated catch block
			
		}

	}

	





}
