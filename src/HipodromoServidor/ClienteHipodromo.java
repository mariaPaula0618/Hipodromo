package HipodromoServidor;

import java.awt.BorderLayout;
import java.io.*;
import java.net.*;
import java.util.Calendar;
import java.util.Scanner;

import javax.net.ssl.SSLSocketFactory;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import CancionFondo.CancionUDPCliente;
import InterfazHipodromo.PanelApuestas;
import InterfazHipodromo.PanelBanner;
import InterfazHipodromo.PanelCarrera;
import LogicaHipodromo.Caballo;

public class ClienteHipodromo extends JFrame{


	//	public static final String TRUSTTORE_LOCATION = "C:/Program Files (x86)/Java/jdk1.8.0_121/bin/keystore.jks";
	public static final String TRUSTTORE_LOCATION ="C:/Windows/System32/keystore.jks";


	static AudioInputStream audioInputStream;
	static SourceDataLine sourceDataLine;

	private static Socket cliente;
	public static BufferedReader lectorC;
	public static PrintWriter escritorC;
	public static 	String apuesta;
	public static String cedula;
	public static String nombreC;
	public static String apuestaC;
	public static String gano;
	public static boolean apuestaAbierta;
	
	
	
	private PanelApuestas panelApuestas;
	private PanelCarrera panelCarrera;
	private PanelBanner panelBanner;

	private CancionUDPCliente cancion;
	private static UDPMulticastClient caballosMoviendose; 

	
	public ClienteHipodromo() {
		apuestaAbierta=true;
		cancion= new CancionUDPCliente();
		caballosMoviendose= new UDPMulticastClient(this);
		panelApuestas= new PanelApuestas(this);
		panelCarrera= new PanelCarrera(this);
		panelBanner= new PanelBanner();

		JPanel panelAux= new JPanel();
		panelAux.setLayout(new BorderLayout());
		panelAux.add(panelApuestas, BorderLayout.CENTER);


		setLayout(new BorderLayout());

		add(panelBanner, BorderLayout.NORTH);
		add(panelCarrera, BorderLayout.CENTER);
		add(panelApuestas, BorderLayout.SOUTH);
		setSize(950, 600);
		
		cancion.start();
		caballosMoviendose.start();

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		
		
	}


	public static void main(String[] args) {
		
		ClienteHipodromo i=new ClienteHipodromo();
		i.setVisible(true);
		i.setResizable(false);
		
		cedula= JOptionPane.showInputDialog(null, "Ingrese su cédula");

		//Sentencias para implementar ssl
//		System.setProperty("javax.net.ssl.trustStore", TRUSTTORE_LOCATION);
//		SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();

		//Lee lo que esta en consola
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));





		try {

			//			cliente = new Socket("127.0.0.1", 8030);
			cliente= new Socket("localhost", 8030);
			
			//Lee lo que el servidor escribe en el printWriter de el
			lectorC = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			//Escribir en el stream de salida del cliente es decir lo que va a leer el servidor
			escritorC = new PrintWriter(cliente.getOutputStream(), true);



//			System.out.print("Digite el número del caballo y separado por ',' su apuesta "+"\n");

			//Solo se ejecuta una vez pero necesito mantener viva la conexión
			//			apuesta = in.readLine();
			//			escritorC.println(apuesta);

		
			initiateAudio();
			
			



		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void conexionPosisionCaballos() {
		
	}
	
	public void apostarCliente() {
		panelApuestas.desabilidarCaballo();
		nombreC=obtenerNombre();
		apuestaC=obtenerApuesta();
		
		String[] a= new String[] {nombreC,apuestaC };
		apostar(a);
		panelCarrera.ponerNombre(nombreC);

		}
		public String obtenerNombre() {
			return panelApuestas.nombreCaballo();
		}
		public String obtenerApuesta() {
			return panelApuestas.apuestaCaballo();


		}
		public void modificarPosicionCaballos(int[] pos) {
			panelCarrera.ponerPosicion(pos);
			panelCarrera.repaint();
			panelCarrera.updateUI();
			this.repaint();
		}

	
	public void apostar(String[] apuestaX) {
		String a=apuestaX[0];
		String b=apuestaX[1];
		String c=cedula;
		String apuesta=a+","+b+","+c;
		
		escritorC.println(apuesta);
	}
	
	
	
	public static  AudioFormat getAudioFormat() {
		float sampleRate = 16000F;
		int sampleSizeInBits = 16;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = false;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

	public static  void playAudio() {
		byte[] buffer = new byte[10000];
		try {
			int count;
			while ((count = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
				if (count > 0) {
					sourceDataLine.write(buffer, 0, count);
				}
			}
		} catch (Exception e) {
			// Handle exceptions
		}
	}

	public static void initiateAudio() {
		try {
			MulticastSocket multicastSocket = new MulticastSocket(8000);
			InetAddress inetAddress = InetAddress.getByName("228.5.6.7");
			multicastSocket.joinGroup(inetAddress);
			
//			DatagramSocket socket = new DatagramSocket(9786);
			byte[] audioBuffer = new byte[10000];
			// ...
			while (true) {
				DatagramPacket packet = new DatagramPacket(audioBuffer, audioBuffer.length);
				multicastSocket.receive(packet);
				// ...

				try {
					byte audioData[] = packet.getData();
					InputStream byteInputStream = new ByteArrayInputStream(audioData);
					AudioFormat audioFormat = getAudioFormat();
					audioInputStream = new AudioInputStream(byteInputStream, audioFormat,
							audioData.length / audioFormat.getFrameSize());
					DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);

					sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
					sourceDataLine.open(audioFormat);
					sourceDataLine.start();
					playAudio();
				} catch (Exception e) {
					// Handle exceptions
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
public void escribirArchivo() {
	try {
	File filedData= new File("BasesDeDatos/"+cedula+".txt");
	boolean archivoNuevo = !filedData.exists();
	BufferedWriter base= new BufferedWriter(new FileWriter(filedData, true));
	Calendar c= Calendar.getInstance();
	String dia=Integer.toString(c.get(Calendar.DATE));
	String mes=Integer.toString(c.get(Calendar.MONTH));
	String annio=Integer.toString(c.get(Calendar.YEAR));
	if(!archivoNuevo) {
		base.newLine();		
	}
	base.write(apuestaC+" "+nombreC+" "+gano+" "+dia+" "+mes+" "+annio);
	base.close();
	}catch (Exception e) {
		// TODO: handle exception
	}
}
	public void cambiarApuesta() {
		apuestaAbierta=false;
	}

	/**
	 * @return the cliente
	 */
	public static Socket getCliente() {
		return cliente;
	}


	/**
	 * @param cliente the cliente to set
	 */
	public static void setCliente(Socket cliente) {
		ClienteHipodromo.cliente = cliente;
	}


	/**
	 * @return the lectorC
	 */
	public static BufferedReader getLectorC() {
		return lectorC;
	}


	/**
	 * @param lectorC the lectorC to set
	 */
	public static void setLectorC(BufferedReader lectorC) {
		ClienteHipodromo.lectorC = lectorC;
	}


	/**
	 * @return the escritorC
	 */
	public static PrintWriter getEscritorC() {
		return escritorC;
	}


	/**
	 * @param escritorC the escritorC to set
	 */
	public static void setEscritorC(PrintWriter escritorC) {
		ClienteHipodromo.escritorC = escritorC;
	}


	/**
	 * @return the apuesta
	 */
	public static String getApuesta() {
		return apuesta;
	}


	/**
	 * @param apuesta the apuesta to set
	 */
	public static void setApuesta(String apuesta) {
		ClienteHipodromo.apuesta = apuesta;
	}
	
	public void ganador(String a) {
		if(nombreC.equals(a)) {
			gano="Ganó";
		}else {
			gano="No Ganó";
		}
		escribirArchivo();
	JOptionPane.showMessageDialog(this, a, "Ganador Carrera",JOptionPane.INFORMATION_MESSAGE);	
	}
	
	

}
