package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Hashtable;

public class Client {

	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost",6500);
			
			InputStream in = s.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(in);
			final Hashtable<String, Integer> message1 = (Hashtable<String, Integer>) ois.readObject();
			int rep = message1.get("test");
			System.out.println(rep);
			
			final Hashtable<String, Integer> message2 = (Hashtable<String, Integer>) ois.readObject();
			int rep2 = message2.get("timing");
			System.out.println(rep2);
			
		}
		catch (IOException | ClassNotFoundException err) {
			err.printStackTrace();
		}
		
	}
}