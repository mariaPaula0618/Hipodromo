package CancionFondo;	
	import java.io.*;
	import java.net.*;
	import javax.sound.sampled.*;

/*
*
*Clase que permite hacer la conexión del cliente para la transimision de la canción
*Es un hilo para que sea parelala la conexión
*/
	public class CancionUDPCliente  extends Thread{
		
		public CancionUDPCliente() {
			
		}
		
	   // public static void main(String[] args) throws Exception {
		
		/*
		*
		*Metodo que permite mantener la conexion abierta
		*
		*/
		public void run() {
	        
	           
	            // play soundfile from server
	            System.out.println("Client: reading from 127.0.0.1:6666");
	            try (Socket socket = new Socket("127.0.0.1", 6666)) {
	            	while(true) {
	                if (socket.isConnected()) {
	                    InputStream in = new BufferedInputStream(socket.getInputStream());
	                    play(in);
	                }
	                }
	            }catch (Exception e) {
	            	
	            }
	        

	        System.out.println("Client: end");
	    }

	/*
	* Método que permite reproducir la cancion y mantenerla sincronizada
	*
	*/
	    private  synchronized void play(final InputStream in) throws Exception {
	        AudioInputStream ais = AudioSystem.getAudioInputStream(in);
	        try (Clip clip = AudioSystem.getClip()) {
	            clip.open(ais);
	            clip.start();
	            Thread.sleep(100); // given clip.drain a chance to start
	            clip.drain();
	        }
	    }
	}
	
