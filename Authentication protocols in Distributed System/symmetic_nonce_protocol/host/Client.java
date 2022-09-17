package host;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import cryptotool.CaesarCipher;

public class Client {

	private Socket soc;
	private DataInputStream socIn;
	private DataOutputStream socOut;
	private int serverPort, localPort, key;
	
	public Client(int srvPort, int localPort, int key)
	{
		try
		{
			this.serverPort = srvPort;
			this.localPort = localPort;
			this.key = key;
			System.out.println("Client started");
			this.soc = new Socket(InetAddress.getLocalHost(), this.serverPort, InetAddress.getLocalHost(), this.localPort);
			socIn = new DataInputStream(soc.getInputStream());
			socOut = new DataOutputStream(soc.getOutputStream());
			System.out.println("Local port address: " + this.localPort);
			System.out.println("Connection established with port: " + this.serverPort);
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
			
			Client clt = new Client(srvPort, 9010, srvKey);
			
			clt.socOut.writeUTF(String.valueOf(clt.localPort));
			System.out.println("Request sent: " + clt.localPort);
			String nonce = clt.socIn.readUTF();
			System.out.println("Got nonce: " + nonce);
			String verCode = String.valueOf(clt.localPort) + String.valueOf(clt.serverPort) + nonce;
			System.out.println("Generated packet: " + verCode);
			verCode = CaesarCipher.encrypt(verCode, clt.key);
			System.out.println("Encrypted packet: " + verCode);
			clt.socOut.writeUTF(verCode);
			System.out.println("Packet sent");
			String response = clt.socIn.readUTF();
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
