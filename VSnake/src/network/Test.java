package network;

import java.io.Serializable;

public class Test implements Serializable{
	private int[] snakePosX;
	private int[] snakePosY;
	private int snakeLength;
	private String direction;
	private boolean isMoving;
	
	public Test (int[] parSnakePosX, int[] parSnakePosY, int parSnakeLength, String parDirection, boolean parisMoving) {
		this.snakePosX = parSnakePosX;
		this.snakePosY = parSnakePosY;
		this.snakeLength = parSnakeLength;
		this.direction = parDirection;
		this.isMoving = parisMoving;
	}
	
	public int getSnakeLenght() {
		return this.snakeLength;
	}
	
	public String toString() {
		return "oui tqt";
	}
}
