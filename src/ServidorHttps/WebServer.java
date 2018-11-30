package ServidorHttps;

import java.io.IOException;
import java.net.*;

public class WebServer extends Thread {
	
	public WebServer()
	{
		System.out.println("Webserver Started");
		
	}
	public void run() {
		super.run();
		try {
			ServerSocket serverSocket =  new ServerSocket(80);
			while(true) 
			{
				System.out.println("Waiting for the client request");
				Socket remote = serverSocket.accept();
				System.out.println("Connection made");
				new Thread(new ClientHandler(remote)).start();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

}
