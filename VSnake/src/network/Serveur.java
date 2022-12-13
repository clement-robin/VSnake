package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class Serveur {
	public static void main(String[] test) throws IOException {
		try {
			ServerSocket s = new ServerSocket(6500);
			
			Socket client = s.accept();
			
			Hashtable<String, Integer> message = new Hashtable<String, Integer>();
			message.put("test", 1);
			
			OutputStream out = client.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(out);
			
			long begin = System.nanoTime();
		      
			oos.writeObject(message);
			
			long end = System.nanoTime();
			long time = end-begin;
			int timeInt = (int) time;

			Hashtable<String, Integer> message2 = new Hashtable<String, Integer>();
			message2.put("timing",timeInt);
			oos.writeObject(message2);
			
			client.close();
		}
		catch (IOException err) {
			err.printStackTrace();
		}
	}
}