package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

public class Client {

	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost",6500);
			
			InputStream in = s.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(in);
			
			//final Hashtable<String, Integer> message1 = (Hashtable<String, Integer>) ois.readObject();
			//int rep = message1.get("test");
			Snake message = (Snake)ois.readObject();
			//int[][] tableau = (int[][])ois.readObject();

			
			System.out.println(message.toString());
			
			//final Hashtable<String, Integer> message2 = (Hashtable<String, Integer>) ois.readObject();
			//int rep2 = message2.get("timing");
			Snake message2 = (Snake)ois.readObject();
			//int[][] tableau2 = (int[][])ois.readObject();
			
			System.out.println(message2.getSnakeLenght());
			
		}
		catch (IOException | ClassNotFoundException err) {
			err.printStackTrace();
		}
		
	}
}