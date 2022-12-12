package network;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class PlayerSprite {
	private double x, y, size;
	private Color color;
	
	public PlayerSprite (double parX, double parY, double parSize, Color parColor) {
		this.x = parX;
		this.y = parY;
		this.size = parSize;
		this.color = parColor;
	}
	
	public void drawSprite(Graphics2D g2d) {
		Rectangle2D.Double square = new Rectangle2D.Double(x,y,size,size);
		g2d.setColor(color);
		g2d.fill(square);
	}
	
	public void moveH(double n) {
		x += n;
	}
	
	public void moveV(double n) {
		y += n;
	}
	
	public void setX(double n) {
		x = n;
	}
	
	public void setY(double n) {
		y = n;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}

}
