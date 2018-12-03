package HipodromoServidor;

import java.io.IOException;
import java.net.*;

public class UDPMulticastClient extends Thread{
	private MulticastSocket multicastSocket;
	private InetAddress inetAddress;
	private DatagramPacket packet;
	private byte[] data;
	ClienteHipodromo principal;

	public UDPMulticastClient(ClienteHipodromo i)
	
	{
		
		principal=i;
		System.out.println("UDP Multicast Time Client Started");
		try 
		{
			/**
			 * We are going to make an InetAddress instance using the multicast address of 228.5.6.7.
			 * The client then joins the multicast group using the joinGroup method
			 */
			 multicastSocket = new MulticastSocket(9877);
			 inetAddress = InetAddress.getByName("230.0.0.1");
			multicastSocket.joinGroup(inetAddress);
			
			/**
			 * A DatagramPacket instance is needed to receive messages that were sent to the client. 
			 */
			 data =  new byte[256];
			 packet =  new DatagramPacket(data, data.length);
			
			/**
			 * The client application then enters an infinite loop where it blocks at the receive method
			 * until the server sends a message. Once the message has arrived, the message is displayed
			 */
		}catch(IOException e) {
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while(true)
		{
			try {
				
				multicastSocket.receive(packet);
				/**
				* receive information about the race, can be the position of the horses or the winner
				*/
				String message = new String(packet.getData(), 0, packet.getLength(),"US-ASCII");
			String[] op= message.split(",");
				/**
				*In this case modify the position of the race in the frame of the clients
				*/
			if(op.length>1) {
				int[] posiscion=  new int[] { Integer.parseInt(op[0]),Integer.parseInt(op[1]),Integer.parseInt(op[2]),Integer.parseInt(op[3]),Integer.parseInt(op[4]),Integer.parseInt(op[5])};
			
				principal.modificarPosicionCaballos(posiscion);
			}
				/**
				*In this case show up a message with the winner horse
				*/
			else {
				principal.ganador(message);
			}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
			
			
	
}
