package host;

import java.net.Socket;
import java.io.*;
import cryptotool.CaeserCipher;

public class Client {

	private Socket soc;
	private DataInputStream socIn;
	private ObjectOutputStream socOut;
	private int port, key;
	
	public Client(int port, int key)
	{
		try
		{
			this.port = port;
			this.key = key;
			System.out.println("Client started");
			this.soc = new Socket("localhost", port);
			socIn = new DataInputStream(soc.getInputStream());
			socOut = new ObjectOutputStream(soc.getOutputStream());
			System.out.println("Connection established with port: " + port);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		try
		{
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			
			int srvPort, srvKey;
			System.out.print("Enter the port address of the server: ");
			srvPort = Integer.parseInt(userIn.readLine());
			System.out.print("Enter the key(should be known for authentic client): ");
			srvKey = Integer.parseInt(userIn.readLine());
			
			Client clt = new Client(srvPort, srvKey);
			
			String[] transMsg = new String[2];
			String response;
			
			System.out.print("Enter the message: ");
			transMsg[0] = userIn.readLine();
			transMsg[1] = transMsg[0] + clt.port;
			transMsg[1] = CaeserCipher.encrypt(transMsg[1], clt.key);
			clt.socOut.writeObject(transMsg);
			response = clt.socIn.readUTF();
			System.out.println(response);
			clt.socOut.close();
			clt.socIn.close();
			clt.soc.close();
			userIn.close();
			System.out.println("Communication terminated");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
