package network;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

import network.GameServer.ReadFromServer;
import network.GameServer.WriteFromServer;

public class PlayerFrame extends JFrame{
	
	private int width, height;
	private Container contentPane;
	private PlayerSprite me;
	private PlayerSprite enemy;
	private DrawingComponent dc;
	private Timer animationTimer;
	private boolean up, down, left, right;
	private Socket socket;
	private int playerID;
	private ReadFromServer rfsRunnable;
	private WriteFromServer wfsRunnable;
	
	
	public PlayerFrame(int parWidth, int parHeight) {
		this.width = parWidth;
		this.height = parHeight;
		up = false;
		down = false;
		left = false;
		right = false;
	}
	
	public void setUpGUI() {
		contentPane = this.getContentPane();
		this.setTitle("Player #" + playerID);
		contentPane.setPreferredSize(new Dimension(this.width, this.height));
		createSprites();
		dc = new DrawingComponent();
		contentPane.add(dc);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
		setUpAnimationTimer();
		setUpKeyListener();
	}
	
	private void createSprites() {
		if(playerID == 1) {
			me = new PlayerSprite(100, 400, 50, Color.BLUE);
			enemy = new PlayerSprite(490, 400, 50, Color.RED);
		}else {
			enemy = new PlayerSprite(100, 400, 50, Color.BLUE);
			me = new PlayerSprite(490, 400, 50, Color.RED);
		}
		
		
	}
	
	private void setUpAnimationTimer() {
		int interval = 10;
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double speed = 20;
				if(up) {
					me.moveV(-speed);
				} else if(down) {
					me.moveV(speed);
				} else if(left) {
					me.moveH(-speed);
				} else if(right) {
					me.moveH(speed);
				}
				dc.repaint();
			}
		};
		animationTimer =  new Timer(interval, al);
		animationTimer.start();
	}
	
	private void setUpKeyListener() {
		KeyListener kl = new KeyListener() {

			public void keyTyped(KeyEvent e) {}

			public void keyPressed(KeyEvent ke) {
				int keyCode = ke.getKeyCode();
				
				switch(keyCode) {
					case KeyEvent.VK_UP :
						up = true;
						break;
					case KeyEvent.VK_DOWN :
						down = true;
						break;
					case KeyEvent.VK_LEFT :
						left = true;
						break;
					case KeyEvent.VK_RIGHT :
						right = true;
						break;
				}
			}

			public void keyReleased(KeyEvent ke) {
				int keyCode = ke.getKeyCode();
				
				switch(keyCode) {
					case KeyEvent.VK_UP :
						up = false;
						break;
					case KeyEvent.VK_DOWN :
						down = false;
						break;
					case KeyEvent.VK_LEFT:
						left = false;
						break;
					case KeyEvent.VK_RIGHT :
						right = false;
						break;
				}
			}
			
		};
		contentPane.addKeyListener(kl);
		contentPane.setFocusable(true);
	}
	
	private void connectToServer() {
		try {
			socket = new Socket("localhost",45371);
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			playerID = in.readInt();
			System.out.println("You are player #" + playerID);
			if(playerID == 1) {
				System.out.println("Waiting for Player #2 to connect...");
			}
			/*rfsRunnable = new ReadFromServer(in);
			wfsRunnable = new WriteFromServer(out);*/
			
		} catch(IOException ex){
			System.out.println("IOException from connectToServer()");
		}
	}
	
	private class DrawingComponent extends JComponent {
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			enemy.drawSprite(g2d);
			me.drawSprite(g2d);
		}
	}
	
	public static void main(String[] args) {
		PlayerFrame pf = new PlayerFrame(640, 480);
		pf.connectToServer();
		pf.setUpGUI();
	}

}
