package ServidorHttps;

import java.net.*;
import java.util.StringTokenizer;
import java.io.*;
public class ClientHandler  implements Runnable {
	
	private final Socket socket;

	public ClientHandler(Socket socket)
	{
		this.socket =  socket;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("\nClientHandler Started for " + this.socket);
		handleRequest(this.socket);
		System.out.println("ClientHanlder Terminated for "+  this.socket + "\n");
	}
	
	public void handleRequest(Socket socket)
	{
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String headerLine = in.readLine();
			// A tokenizer is a process that splits text into a series of tokens
			StringTokenizer tokenizer =  new StringTokenizer(headerLine);
			//The nextToken method will return the next available token
			String httpMethod = tokenizer.nextToken();
			// The next code sequence handles the GET method. A message is displayed on the
			// server side to indicate that a GET method is being processed
			if(httpMethod.equals("GET"))
			{
				System.out.println("Get method processed");
				String httpQueryString = tokenizer.nextToken();
				
				StringBuilder responseBuffer =  new StringBuilder();
				if(httpQueryString.equals("/")) {
					responseBuffer =  DesplegarPagina();
					sendResponse(socket, 200, responseBuffer.toString());				
				}
//				else if(!httpQueryString.equals("/favicon.ico"))  {
//					String a=httpQueryString.replace("/", "");
////						boolean verificar=verificar(a);
//					if(true) {
//						responseBuffer=DesplegarDespuesLogin(a);
//						sendResponse(socket, 200, responseBuffer.toString());	
//					}
//					
//				}
//				responseBuffer
//				.append("<html>")
//				.append("<body bgcolor='black'>")
//				.append("<font color='white'>[0][1][0]</font><br>")
//				.append("<font color='white'>[0][0][1]</font><br>")
//				.append("<font color='white'>[1][1][1]</font><br>")
//				.append("<img src='https://s2.glbimg.com/QJD0YP7szRqJuSEUdGHPF_2Dwqs=/850x446/s.glbimg.com/po/tt/f/original/2012/06/01/pirata-e1314380534977.jpg'>")
//				.append("</body>")
//				.append("</html>");
			}
			else if(httpMethod.equals("POST")) {
				while(!headerLine.equals("")) {
					headerLine=in.readLine();
					System.out.println(headerLine);
				}
				headerLine=in.readLine();
				System.out.println(headerLine);
				String[] data=headerLine.split("=");
				String cedula=data[1];
				StringBuilder responseBuffer=DesplegarDespuesLogin(cedula);
				sendResponse(socket, 200, responseBuffer.toString());	
			}
			else
			{
				System.out.println("The HTTP method is not recognized");
				sendResponse(socket, 405, "Method Not Allowed");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private boolean verificar(String c) {
		boolean existe=false;
		try {
		BufferedReader br= new BufferedReader(new FileReader("BasesDeDatos/cedulas.txt"));
		while (br.ready()) {
		String line=br.readLine();
		}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return existe;
		}
	
	private StringBuilder DesplegarDespuesLogin(String cedula) {
	StringBuilder response= new StringBuilder();
	try{
		System.out.println(cedula);
		BufferedReader br= new BufferedReader(new FileReader("BasesDeDatos/"+cedula+".txt"));
	response.append("<html><body bgcolor='white'>");
	response.append("<br> BIENVENIDO: "+cedula+"</br>");
		while (br.ready()) {
			response.append("<br>");
		String line=br.readLine();
		String[] datos=line.split(" ");
			response.append("Apuesta: "+datos[0]);
			response.append(" Caballo Elegido: :"+datos[1]);
			response.append(" El caballo:"+datos[2]);
			response.append(" Dia: "+datos[3]);
			response.append(" Mes: "+datos[4]);
			response.append(" Año: "+datos[5]);
			response.append("</br>");
		
	}
		response.append("</body></html>");
	}
	catch (Exception e) {
		// TODO: handle exception
	}
		return response;
	}
	
	private StringBuilder DesplegarPagina() {
		StringBuilder response= new StringBuilder();
		try {
			FileReader fr= new FileReader(new File("./Data/RegistroHipodromoHtml.html"));
			BufferedReader br= new BufferedReader(fr);
			
			while(br.ready()) {
				response.append(br.readLine());
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}

	public void sendResponse(Socket socket, int statusCode, String responseString)
	{
		String statusLine;
		String serverHeader = "Server: WebServer\r\n";
		String contentTypeHeader = "Content-Type: text/html\r\n";
		
		try {
			DataOutputStream out =  new DataOutputStream(socket.getOutputStream());
			if (statusCode == 200) 
			{
				statusLine = "HTTP/1.0 200 OK" + "\r\n";
				String contentLengthHeader = "Content-Length: "
				+ responseString.length() + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes(serverHeader);
				out.writeBytes(contentTypeHeader);
				out.writeBytes(contentLengthHeader);
				out.writeBytes("\r\n");
				out.writeBytes(responseString);
				} 
			else if (statusCode == 405) 
			{
				statusLine = "HTTP/1.0 405 Method Not Allowed" + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes("\r\n");
			} 
			else 
			{
				statusLine = "HTTP/1.0 404 Not Found" + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes("\r\n");
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
