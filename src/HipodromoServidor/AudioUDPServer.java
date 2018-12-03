package HipodromoServidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

/*
*Class  that is responsible to the transmission of the audio to the client through  protocol UDP
* all the clients have to listen the same audio
*/


public class AudioUDPServer extends Thread {

	private final byte audioBuffer[] = new byte[10000];
	private TargetDataLine targetDataLine;
	private MulticastSocket socket;
	private InetAddress inetAddress;
	private int port;
	
/*
*builder of AudioUDPServer class
*
*/
	public AudioUDPServer() {
		// TODO Auto-generated constructor stub

		setupAudio();
		broadcastAudio();
	}
	
	/*
	*Method that is reponsible to get the audio format 
	*
	*
	*/

	private AudioFormat getAudioFormat() {
		float sampleRate = 16000F;
		int sampleSizeInBits = 16;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = false;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

	/*
	*Method that is reponsible to the broadcast audio to all clients conect 
	*
	*/
	private void broadcastAudio() {
		try {
			socket = new MulticastSocket(8000);
			inetAddress = InetAddress.getByName("228.5.6.7");
			socket.joinGroup(inetAddress);
			
			// ...
			

		} catch (Exception ex) {
			// Handle exceptions
		}
	}
	
	/*
	*This method allow to have a constant connection
	*
	*/
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while (true) {
			int count = targetDataLine.read(audioBuffer, 0, audioBuffer.length);
			if (count > 0) {
				DatagramPacket packet = new DatagramPacket(audioBuffer, audioBuffer.length, inetAddress, 8000);
				try {
					socket.send(packet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}



	private void setupAudio() {
		try {
			AudioFormat audioFormat = getAudioFormat();
			DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
			targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
			targetDataLine.open(audioFormat);
			targetDataLine.start();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new AudioUDPServer();
	}

}
