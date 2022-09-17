package host;

import java.net.Socket;
import java.io.*;

public class Intruder {

	public static void main(String[] args) {
		int port;
		String packet[] = new String[2];
		Socket soc;
		ObjectOutputStream out;
		DataInputStream in;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			System.out.print("Enter the port of server: ");
			port = Integer.parseInt(br.readLine());
			System.out.print("Enter a message: ");
			packet[0] = br.readLine();
			System.out.print("Enter the encrypted version of the message: ");
			packet[1] = br.readLine();
			soc = new Socket("localhost", port);
			System.out.println("Successfully connected");
			out = new ObjectOutputStream(soc.getOutputStream());
			in = new DataInputStream(soc.getInputStream());
			out.writeObject(packet);
			System.out.println(in.readUTF());
			soc.close();
			in.close();
			out.close();
			System.out.println("Connection terminated");
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}

}
