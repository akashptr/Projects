package host;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import cryptotool.CaesarCipher;

public class Server {

	private ServerSocket srvSoc;
	private Socket soc;
	private DataInputStream socIn;
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
			socIn = new DataInputStream(soc.getInputStream());
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
			String request, verCode;
			
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("Enter the key: ");
			Server srv = new Server(9001, Integer.parseInt(userIn.readLine()));
			
			request = srv.socIn.readUTF();
			System.out.println("Got the request from: " + request);
			int nonce = (int)(Math.random() * 10000);
			System.out.println("Generated nonce = " + nonce);
			srv.socOut.writeUTF(String.valueOf(nonce));
			System.out.println("Nonce sent");
			verCode = srv.socIn.readUTF();
			System.out.println("Packet received from client: " + verCode);
			String str = request + String.valueOf(srv.port) + String.valueOf(nonce);
			System.out.println("Constructed packet: " + str);
			str = CaesarCipher.encrypt(str, srv.key);
			System.out.println("Encrypted packet: " + str);
			if(str.equals(verCode))
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
