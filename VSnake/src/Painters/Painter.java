package Painters;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


import main.Food;

public class Painter {
	
	private BufferedImage snakeSheet;
	
	private static final int CELL_SHEET_SIZE = 50;
	
	private static final Point HBody = new Point(1,0);
	private static final Point VBody = new Point(2,1);
	
	private static final Point UHead = new Point(3,0);
	private static final Point DHead = new Point(4,1);
	private static final Point RHead = new Point(4,0);
	private static final Point LHead = new Point(3,1);
	
	private static final Point RUBody = new Point(0,1);
	private static final Point RDBody = new Point(0,0);
	private static final Point LUBody = new Point(2,2);
	private static final Point LDBody = new Point(2,0);
	
	private static final Point UQueue = new Point(4,3);
	private static final Point DQueue = new Point(3,2);
	private static final Point RQueue = new Point(3,3);
	private static final Point LQueue = new Point(4,2);
	
	private static final Point Food = new Point(0,3);
	
	public Painter(String fileSnakeSheet) {
		try {
			snakeSheet = ImageIO.read(new File(fileSnakeSheet));
		} catch (IOException e) {
		}
	}
	
	private BufferedImage getSnakeCell(Point p) {
		return snakeSheet.getSubimage(p.getX() * CELL_SHEET_SIZE, p.getY() * CELL_SHEET_SIZE, CELL_SHEET_SIZE, CELL_SHEET_SIZE);
	}
	
	private void drawCell(int posX, int posY, Point cell, java.awt.Graphics g) {
		g.drawImage(getSnakeCell(cell), posX, posY, null);
	}
	
	public void paintFood(java.awt.Graphics g, Food food) {
		drawCell(food.getPosX(),food.getPosY(), Food, g);
	}
	
	public void paintSnake(java.awt.Graphics g, int snakePosX[], int snakePosY[],int snakeLength) {
		
        drawCell(snakePosX[0],
        		 snakePosY[0],
        		 getCell(-1, snakePosX[0], snakePosX[1],
        				 -1, snakePosY[0], snakePosY[1])
        		 , g);
		
        for (int i = 1; i < snakeLength - 1; i++) {
            drawCell(snakePosX[i],
           		 snakePosY[i],
           		 getCell(snakePosX[i - 1], snakePosX[i], snakePosX[i + 1],
           				 snakePosY[i - 1], snakePosY[i], snakePosY[i + 1])
           		 , g);
        }
        
        drawCell(snakePosX[snakeLength - 1],
          		 snakePosY[snakeLength - 1],
          		 getCell(snakePosX[snakeLength - 2], snakePosX[snakeLength - 1], - 1,
          				 snakePosY[snakeLength - 2], snakePosY[snakeLength - 1], - 1)
          		 , g);        
        
	}
	
	private Point getCell(int px, int x, int nx, int py, int y, int ny) {
		
		if(px == -1 || py == -1) {
			if(x > nx) {
				return RHead;
			} else if (x < nx) {
				return LHead;
			} else if (y > ny) {
				return DHead;
			} else {
				return UHead;
			}
		} else if (nx == -1 || ny == -1) {
			if(x > px) {
				return RQueue;
			} else if (y < py) {
				return UQueue;
			} else if (y > py) {
				return DQueue;
			} else {
				return LQueue;
			}
		} else {
			if(px == nx) {
				return VBody;
			} else if (py == ny) {
				return HBody;
			} else {
				if(px > x || nx > x) {
					if (py > y || ny > y) {
						return RDBody;
					} else {
						return RUBody;
					}
				} else {
					if (py > y || ny > y) {
						return LDBody;
					} else {
						return LUBody;
					}					
				}
			}
		}
	}
}
