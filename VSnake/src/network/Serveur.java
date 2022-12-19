package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

public class Serveur {
	public static void main(String[] test) throws IOException {
		try {
			ServerSocket s = new ServerSocket(6500);
			
			Socket client = s.accept();
			
			//Hashtable<String, Integer> message = new Hashtable<String, Integer>();
			/*int[][] tableau = new int[10][10];
			for(int i = 0 ; i < 10 ; i++) {
				for(int j = 0 ; j < 10 ; j++) {
					tableau[i][j] = i + j;
				}
			}*/
			/*ArrayList<Integer> message = new ArrayList<Integer>();
			for(int j = 0 ; j < 10 ; j++) {
				message.add(j);
			}*/
			Snake oui = new Snake(new int[192], new int[192],5,"Droite",true);

			
			OutputStream out = client.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(out);
			
			long begin = System.nanoTime();
		      
			oos.writeObject(oui);
			
			long end = System.nanoTime();
			long time = end-begin;
			int timeInt = (int) time;

			//Hashtable<String, Integer> message2 = new Hashtable<String, Integer>();
			//ArrayList<Integer> message2 = new ArrayList<Integer>();
			//int[][] tableau2 = new int[10][10];
			//message2.add(timeInt);
			Snake oui2 = new Snake(new int[192], new int[192],timeInt,"Droite",true);
			oos.writeObject(oui2);
			
			client.close();
		}
		catch (IOException err) {
			err.printStackTrace();
		}
	}
}