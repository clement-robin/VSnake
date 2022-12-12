package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
	
	private ServerSocket ss;
	private int numPlayers;
	private int maxPlayers; 
	
	private Socket p1Socket;
	private Socket p2Socket;
	private ReadFromClient p1ReadRunnable;
	private ReadFromClient p2ReadRunnable;
	private WriteFromClient p1WriteRunnable;
	private WriteFromClient p2WriteRunnable;
	
	public GameServer() {
		System.out.println("===== GAME SERVER =====");
		numPlayers = 0;
		maxPlayers = 2;
		
		try {
			ss = new ServerSocket(45371);
		} catch(IOException ex) {
			System.out.println("IOException from GameServer constructor");
		}
	}
	
	public void acceptConnections() {
		try {
			System.out.println("Waiting to connection ... ");
			
			while(numPlayers < maxPlayers) {
				Socket s = ss.accept();
				DataInputStream in = new DataInputStream(s.getInputStream());
				DataOutputStream out = new DataOutputStream(s.getOutputStream());
				
				numPlayers++;
				
				out.write(numPlayers);
				System.out.println("Player #" + numPlayers + " has connected");
				
				ReadFromClient rfc = new ReadFromClient(numPlayers,in);
				WriteFromClient wfc = new WriteFromClient(numPlayers,out);
				
				if(numPlayers == 1) {
					p1Socket = s;
					p1ReadRunnable = rfc;
					p1WriteRunnable = wfc;
				} else {
					p2Socket = s;
					p2ReadRunnable = rfc;
					p2WriteRunnable = wfc;
				}
				
			}
			System.out.println("No longer accepting connections");
			
		} catch(IOException ex) {
			System.out.println("IOException from acceptConnections() ");
		}
	}
	
	public class ReadFromClient implements Runnable {
		private int playerID;
		private DataInputStream dataIn;
		
		public ReadFromClient(int pid, DataInputStream in) {
			playerID = pid;
			dataIn = in;
			System.out.println("RFC " + playerID + " Runnable created");
		}
		
		public void run() {
			
		}
	}
	
	public class WriteFromClient implements Runnable {
		private int playerID;
		private DataOutputStream dataOut;
		
		public WriteFromClient(int pid, DataOutputStream out) {
			playerID = pid;
			dataOut = out;
			System.out.println("WFC " + playerID + " Runnable created");
		}
		
		public void run() {
			
		}
	}
	
	public class ReadFromServer implements Runnable {
		
		private DataInputStream dataIn;
		
		public ReadFromServer(DataInputStream in) {
			dataIn = in;
			System.out.println("RFS Runnable created");
		}
		
		public void run() {
			
		}
	}
	
	public class WriteFromServer implements Runnable {
		
		private DataOutputStream dataOut;
		
		public WriteFromServer(DataOutputStream out) {
			dataOut = out;
			System.out.println("WTS Runnable created");
		}
		
		public void run() {
			
		}
	}
	
	public static void main(String[] args) {
		GameServer gs = new GameServer();
		gs.acceptConnections();
	}

}
