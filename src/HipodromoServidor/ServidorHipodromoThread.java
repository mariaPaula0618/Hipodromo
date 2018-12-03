package HipodromoServidor;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import javax.net.ssl.SSLServerSocketFactory;
import javax.swing.JFrame;

import CancionFondo.CancionUDPServer;
import LogicaHipodromo.Caballo;
import LogicaHipodromo.HiloCaballo;
import ServidorHttps.WebServer;


public class ServidorHipodromoThread extends JFrame{

	private 
	static boolean cronometroActivo;
	static HiloTiempo hiloTiempo;
	static ServerSocket server;
	static boolean audioActivo;
	static AudioUDPServer audio;
	static ArrayList<Caballo> ganadores;

	static volatile Caballo caballoGanador;
	
	

	//LLave para implementar el SSLServerSocket
	//	public static final String KEYSTORE_LOCATIO= "C:/Program Files (x86)/Java/jre1.8.0_121/bin/keystore.jks";

	public static final String KEYSTORE_LOCATION = "C:/Windows/System32/keystore.jks";
	public static final String KEYSTORE_PASSWORD = "123456";


	private static Caballo caballoA;
	private static Caballo caballoB;
	private static Caballo caballoC;
	private static Caballo caballoD;
	private static Caballo caballoE;
	private static Caballo caballoF;

	private static Caballo[] caballos;
	private static CancionUDPServer cancion;
	private static WebServer servidorHttps;

	/**
	*This class handle all the services offered by the server
	* keep the information about the horses in race
	*initialize the streaming 
	* initialize the web server 
	*/
	public ServidorHipodromoThread() {



		caballoGanador=new Caballo("Marcador", 0);
		cronometroActivo=true;
		audioActivo=true;
		cancion = new CancionUDPServer();

		caballoA= new Caballo("A", 0);
		caballoB= new Caballo("B", 0);
		caballoC= new Caballo("C", 0);
		caballoD= new Caballo("D", 0);
		caballoE= new Caballo("E", 0);
		caballoF= new Caballo("F", 0);
		
		ganadores= new ArrayList<>();

		caballos=new Caballo[6];
		caballos[0]=caballoA;
		caballos[1]=caballoB;
		caballos[2]=caballoC;
		caballos[3]=caballoD;
		caballos[4]=caballoE;
		caballos[5]=caballoF;



		audio= new AudioUDPServer();
		servidorHttps= new WebServer();

	}


	public static void main(String[] args) throws Exception 
	{


		//Sentencias para activar la encriptacion de la información	
		//		System.setProperty("javax.net.ssl.keyStore", KEYSTORE_LOCATION);
		//		System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASSWORD);
		//		SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		//		ServerSocket ssf= new ServerSocket(port)
		try {
			ServidorHipodromoThread i=	new ServidorHipodromoThread();
			hiloTiempo= new HiloTiempo(i);
			//				ServerSocket server = new ServerSocket(8030);

			server= new ServerSocket(8030);
			/*
			*Start the time
			*/
			hiloTiempo.start();
			/*
			*Start the trasmission of the song
			*/
			cancion.start();
			/*
			*Open the conexion with the web server
			*/
			servidorHttps.start(); 
			
			while(cronometroActivo) {

				Socket c = server.accept();
				HiloApuestasHipodromo thread = new HiloApuestasHipodromo(c, caballos);
				thread.start();


			}





		}catch (IOException e) {

			e.printStackTrace();

		}

	}
	public boolean cronometro() {
		return cronometroActivo;
	}
	public void cambiarCronometro() {
		cronometroActivo=false;
	}
	public void cambiarAudio() {
		audioActivo=false;
	}
	
	
	/*
	*this method allows to show up the race to the clients
	*all the client have to see the same race
	*/
	public void seguir() {

		audio.start();
		for (int i = 0; i < caballos.length; i++) {

			System.out.println(caballos[i].getNombreCaballo()+" --"+caballos[i].getApuesta());
		}

		HiloCaballo cab1=new HiloCaballo(caballoA, ganadores);
		HiloCaballo cab2=new HiloCaballo(caballoB, ganadores);
		HiloCaballo cab3=new HiloCaballo(caballoC, ganadores);
		HiloCaballo cab4=new HiloCaballo(caballoD,ganadores );
		HiloCaballo cab5=new HiloCaballo(caballoE,ganadores );
		HiloCaballo cab6=new HiloCaballo(caballoF,ganadores );


		cab1.start();
		cab2.start();
		cab3.start();
		cab4.start();
		cab5.start();
		cab6.start();

		try {
			MulticastSocket socket =  new MulticastSocket();
			InetAddress adress= InetAddress.getByName("230.0.0.1");
			socket.joinGroup(adress);

			boolean termino=true;
			while(ganadores.size()<6) {
				termino=(caballoGanador.getNombreCaballo().equals("Marcador"));
				String mensaje= caballoA.getVelocidad()+","+caballoB.getVelocidad()+","+caballoC.getVelocidad()+","+caballoD.getVelocidad()+","
						+caballoE.getVelocidad()+","+caballoF.getVelocidad();
				byte[] dato= mensaje.getBytes();

				DatagramPacket posiso= new DatagramPacket(dato, dato.length, adress,9877);
				socket.send(posiso);
				Thread.sleep(500);
				
			}
			String gandor=ganadores.get(0).getNombreCaballo();
			System.out.println(gandor);
			byte[] dato= gandor.getBytes();

			DatagramPacket posiso= new DatagramPacket(dato, dato.length, adress,9877);
			socket.send(posiso);
		} catch (Exception e) {
			// TODO Auto-generated catch block

		}




	}

	public void inciarMovimientos() {

	}



}


