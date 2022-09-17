package host;

import java.net.ServerSocket;
import java.net.Socket;

import cryptotool.CaeserCipher;

import java.io.*;

public class Server {
	private ServerSocket srvSoc;
	private Socket soc;
	private ObjectInputStream socIn;
	private DataOutputStream socOut;
	private int key, port;
	
	public Server(int port, int key)
	{
		try
		{
			this.port = port;
			this.key = key;
			this.srvSoc = new ServerSocket(port);
			System.out.println("Server started at port: " +
			port + "\nWaiting for the client...");
			this.soc = srvSoc.accept();
			System.out.println("Connection established");
			socIn = new ObjectInputStream(soc.getInputStream());
			socOut = new DataOutputStream(soc.getOutputStream());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		try
		{
			String msg;
			String[] transMsg = new String[2];
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("Enter the key: ");
			Server srv = new Server(9001, Integer.parseInt(userIn.readLine()));
			
			transMsg = (String[]) srv.socIn.readObject();
			System.out.println("Incomming packet from client contains:\n" +
			transMsg[0] + "\n" + transMsg[1]);
			msg = transMsg[0] + String.valueOf(srv.port);
			msg = CaeserCipher.encrypt(msg, srv.key);
			if(msg.equals(transMsg[1]))
			{
				srv.socOut.writeUTF("Authentication successful");
				System.out.println("Authentication successful");
			}
			else
			{
				srv.socOut.writeUTF("Authentication unsuccessful");
				System.out.println("Authentication unsuccessful");
			}
				
			srv.socOut.close();
			srv.socIn.close();
			srv.soc.close();
			userIn.close();
			System.out.println("Communication terminated");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
