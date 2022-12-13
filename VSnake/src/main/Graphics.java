package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Painters.*;
import Painters.Painter;

public class Graphics extends JPanel implements ActionListener {

    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    static final int TICK_SIZE = 50;
    static final int BOARD_SIZE = (WIDTH * HEIGHT) / (TICK_SIZE * TICK_SIZE);

    final Font font = new Font("TimesRoman", Font.BOLD, 30);

    int[] snake1PosX;
    int[] snake1PosY;
    int snake1Length;
    
    int[] snake2PosX;
    int[] snake2PosY;
    int snake2Length;

    Food food;
    int foodEaten1;
    int foodEaten2;
    
    Painter painter1;
    Painter painter2;

    String direction1;
    boolean isMoving1 = false;
    
    String direction2;
    boolean isMoving2 = false;
    final Timer timer = new Timer(700, this);

    public Graphics() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(new Color(169, 215, 81));
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isMoving1 && isMoving2) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            if (direction1 != "Droite") {
                                direction1 = "Gauche";
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (direction1 != "Gauche") {
                                direction1 = "Droite";
                            }
                            break;
                        case KeyEvent.VK_UP:
                            if (direction1 != "Bas") {
                                direction1 = "Haut";
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if (direction1 != "Haut") {
                                direction1 = "Bas";
                            }
                            break;
                        case KeyEvent.VK_Q:
                            if (direction2 != "Droite") {
                                direction2 = "Gauche";
                            }
                            break;
                        case KeyEvent.VK_D:
                            if (direction2 != "Gauche") {
                                direction2 = "Droite";
                            }
                            break;
                        case KeyEvent.VK_Z:
                            if (direction2 != "Bas") {
                                direction2 = "Haut";
                            }
                            break;
                        case KeyEvent.VK_S:
                            if (direction2 != "Haut") {
                                direction2 = "Bas";
                            }
                            break;
                    }
                } else {
                    start();
                }
            }
        });

        start();
    }

    protected void start() {
    	
    	
        snake1PosX = new int[BOARD_SIZE];      
        snake1PosY = new int[BOARD_SIZE];
        snake1Length = 5;
        foodEaten1 = 0;
        direction1 = "Droite";
        isMoving1 = true;
        
        snake2PosX = new int[BOARD_SIZE];
        snake2PosX[0] = 0;
        
        snake2PosY = new int[BOARD_SIZE];
        snake2PosY[0] = (HEIGHT - TICK_SIZE) /2;
        snake2PosY[1] = (HEIGHT - TICK_SIZE) /2;
        snake2PosY[2] = (HEIGHT - TICK_SIZE) /2;
        snake2PosY[3] = (HEIGHT - TICK_SIZE) /2;

        
        snake2Length = 5;
        foodEaten2 = 0;
        direction2 = "Droite";
        isMoving2 = true;
        
        spawnFood();
        timer.start();
        painter1 = new Painter("snake-graphics.png");
        painter2 = new Painter("snake-graphics2.png");
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        
        g.setColor(new Color(162, 209, 73));
        int ligne = 0;
        for (int i = 0; i < WIDTH; i+=TICK_SIZE) {
        	int pair = 0;
        	if(ligne%2==0)	pair = TICK_SIZE;
        	for (int j = pair; j < HEIGHT; j+= (TICK_SIZE*2)) {
        		g.fillRect(i, j, TICK_SIZE, TICK_SIZE);
        	}
        	ligne++;
        }

        if (isMoving1 && isMoving2) {
        	painter1.paintFood(g, food);
        	painter1.paintSnake(g, snake1PosX, snake1PosY, snake1Length);
        	painter2.paintSnake(g, snake2PosX, snake2PosY, snake2Length);


        } else {
            String scoreText = String.format("The End... Press any key to play again!");
            g.setColor(Color.BLACK);
            g.setFont(font);
            g.drawString(scoreText, (WIDTH - getFontMetrics(g.getFont()).stringWidth(scoreText)) / 2, HEIGHT / 2);
        }
    }

    protected void move() {
        for (int i = snake1Length; i > 0; i--) {
            snake1PosX[i] = snake1PosX[i-1];
            snake1PosY[i] = snake1PosY[i-1];
        }

        switch (direction1) {
            case "Haut" -> snake1PosY[0] -= TICK_SIZE;
            case "Bas" -> snake1PosY[0] += TICK_SIZE;
            case "Gauche" -> snake1PosX[0] -= TICK_SIZE;
            case "Droite" -> snake1PosX[0] += TICK_SIZE;
        }
        
        for (int i = snake2Length; i > 0; i--) {
            snake2PosX[i] = snake2PosX[i-1];
            snake2PosY[i] = snake2PosY[i-1];
        }
        
        switch (direction2) {
	        case "Haut" -> snake2PosY[0] -= TICK_SIZE;
	        case "Bas" -> snake2PosY[0] += TICK_SIZE;
	        case "Gauche" -> snake2PosX[0] -= TICK_SIZE;
	        case "Droite" -> snake2PosX[0] += TICK_SIZE;
	    }
        
    }

    protected void spawnFood() {
        food = new Food();


    }

    protected void eatFood() {
        if ((snake1PosX[0] == food.getPosX()) && (snake1PosY[0] == food.getPosY())) {
            snake1Length++;
            foodEaten1++;
            spawnFood();
        }
        
        if ((snake2PosX[0] == food.getPosX()) && (snake2PosY[0] == food.getPosY())) {
            snake2Length++;
            foodEaten2++;
            spawnFood();

        }
    }

    protected void collisionTest() {
        for (int i = snake1Length; i > 0; i--) {
            if ((snake1PosX[0] == snake1PosX[i]) && (snake1PosY[0] == snake1PosY[i])) {
                isMoving1 = false;
                break;
            }
        }
        
        for (int i = snake2Length; i > 0; i--) {
            if ((snake2PosX[0] == snake2PosX[i]) && (snake2PosY[0] == snake2PosY[i])) {
                isMoving2 = false;
                break;
            }
        }

        if (snake1PosX[0] < 0 || snake1PosX[0] > WIDTH - TICK_SIZE || snake1PosY[0] < 0 || snake1PosY[0] > HEIGHT - TICK_SIZE) {
            isMoving1 = false;
        }
        
        if (snake2PosX[0] < 0 || snake2PosX[0] > WIDTH - TICK_SIZE || snake2PosY[0] < 0 || snake2PosY[0] > HEIGHT - TICK_SIZE) {
            isMoving2 = false;
        }

        if (!isMoving1) {
            timer.stop();
        }
        
        if (!isMoving2) {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isMoving1 && isMoving2) {
            move();
            collisionTest();
            eatFood();
        }

        repaint();
    }
}