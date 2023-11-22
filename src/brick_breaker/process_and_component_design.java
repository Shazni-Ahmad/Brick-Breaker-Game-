package brick_breaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;


public class process_and_component_design extends JPanel implements KeyListener, ActionListener {
	// key listener to detect the keys pressed
		// action listener to detect the movements 
		
		private boolean play = false; // to ensure that the game doesnt start by itself
		private int score =0; // the score of the player is set to zero as default 
		
		private int totalBricks = 28; // the number of bricks displayed in the gaming area
		
		private Timer timer; // timer is used to control the speed of the ball and slider
		private int delay ; // speed fed to the timer
		
		private int playerX = 310; // initial position of slider
		
		private int ballposX = 120; // setting the initial position of ball
		private int ballposY = 350;
		private int ballXdir = -1; // setting the direction of movement of the ball
		private int ballYdir = -2;
		
		private Brick_area  map; // creating brickarea variable
		
		public process_and_component_design() {
			map = new Brick_area(4,7); // creating brickarea object inside process function 
			addKeyListener(this); // adding the key listener
			setFocusable(true);
			setFocusTraversalKeysEnabled(false);
		}
		
		public void paint(Graphics g) {
			// this function is used to create different types of shapes and objects
			// setting the game background
			g.setColor(Color.black);
			g.fillRect(1,1,692,592); // creating the background shape
			
			//function to draw the area of bricks
			map.draw((Graphics2D)g);
			
			//displaying score
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString(""+score,590,30);
			
			//select level of play
			g.setColor(Color.blue);
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("select level of play: 1-Easy, 2-Medium, 3-Hard",10,30);
			
			//creating the slider
			g.setColor(Color.green);
			g.fillRect(playerX, 550, 100, 8); // creating the object off slider
			
			//designing the ball
			g.setColor(Color.yellow);
			g.fillOval(ballposX, ballposY, 20, 20); // creating the object ball
			
			if(totalBricks <=0) { // managing the event when the game finishes
				play = false;
				ballXdir = 0;
				ballYdir = 0;
				g.setColor(Color.RED);
				g.setFont(new Font("serif", Font.BOLD, 30));
				g.drawString("You Won: ",260,300);
				
				g.setFont(new Font("serif", Font.BOLD, 20));
				g.drawString("Press Enter to Restart",230,350);
			
			}
			
			if(ballposY > 570) { // checking whether the ball has missed the slider and touched the bottom side o the game window
				play = false; // if so play is stopped
				ballXdir = 0; // ball motion is stopped
				ballYdir = 0;
				g.setColor(Color.RED);
				g.setFont(new Font("serif", Font.BOLD, 30));
				g.drawString("Game Over, Your score is:"+score,170,300);
				
				g.setFont(new Font("serif", Font.BOLD, 20));
				g.drawString("Press Enter and level of play to Restart",170,350);
			}
			
			g.dispose();
			
		}
	

		public void actionPerformed(ActionEvent e) {
			timer.start();
			if(play) { // checks whether the play is started
				
				ballposX += ballXdir; // movement of ball in x dir
				ballposY += ballYdir; // movement of ball in y dir
				
				//when  the ball hits the top left and right sides of the window the ball dir is reverted
				
				if(ballposX <0) { // this checks for the instance where the ball touches then left side of window 
					ballXdir = -ballXdir;
				}
				if(ballposY <0) { // this checks for the instance where the ball touches then top side of window
					ballYdir = -ballYdir;
				}
				if(ballposX >670) {// this checks for the instance where the ball touches then right side of window
					ballXdir = -ballXdir;
				}
				
				
				// a rectangle is created around the ball to determine the intersection of ball with the slider
				if(new Rectangle(ballposX, ballposY, 20,20).intersects(new Rectangle(playerX,550,100,8))) {
					ballYdir = -ballYdir; // when intersected ball's Y direction is reverted
				}
				
				A: for(int i =0; i<map.map.length; i++) {  // iterating through the rows
					for(int j=0; j<map.map[0].length; j++){ // iterating through the columns in a particular row
						if(map.map[i][j]>0) {
							// this if loop ensures that intersection is detected only for the bricks which are shown that is only when map[i][j]=1
							// map.map means that we are accessing the map 2d array in brickarea using the object map in process
							// inorder to find the intersection positions of ball and brick has to be found these are stored in the following varables
							int brickX = j* map.brickWidth +80;
							int brickY = i * map.brickHeight + 50;
							int brickWidth = map.brickWidth;
							int brickHeight = map.brickHeight;
							
							Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight); // creating rectangle around brick to detect intersection
							Rectangle ballRect = new Rectangle(ballposX, ballposY, 20,20); // creating rectangle around ball to detect intersection
							Rectangle brickRect = rect;
							
							if(ballRect.intersects(brickRect)) {
								// on intersection the brick vanishes by setting map[i][j]=0
								map.setBrickValue(0, i, j);
								totalBricks--; // when a brick vanishes the tot no of bricks should e decremented
								score += 5; // this increments the score when ball hits the bricks
								
								// for left and right intersections
								if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
									ballXdir = -ballXdir;
								// for to and bottom intersection	
								}else {
									ballYdir = -ballYdir;
								}
								break A; // break from the loop A 
							}
						}
					}
				}
				
				
			}
			
			repaint(); // this function recalls the function paint, because when the slider or ball is moved it becomes necessary to draw the ball or slider in the new location as well
			
		}

		public void keyTyped(KeyEvent e) {
		
		}

		public void keyPressed(KeyEvent e) {
			// detecting the keys which are pressed
			if(e.getKeyCode()== KeyEvent.VK_RIGHT) {
				// when right key is pressed the slider should move to the right
				if(playerX >=600) {
					playerX = 600; // this is to make sure that the slider doesnt go beyond the game area
				}else {
					moveRight(); // function to move slider to right in x direction
				}
			}
		
			if(e.getKeyCode()== KeyEvent.VK_LEFT){
				if(playerX <10) {
					playerX = 10; // this is to make sure that the slider doesnt go beyond the game area
				}else {
					moveLeft(); // function to move slider to left in x direction
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				if(!play) { // function to restart game
					//play = true;
					ballposX = 120;
					ballposY = 350;
					ballXdir = -1;
					ballYdir = -2;
					playerX = 310;
					score = 0;
					totalBricks = 21;
					map = new Brick_area(4,7);
					
					repaint();
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_1){
				play = true;
				delay = 1 ;
				timer = new Timer(delay, this); // creating the timer object  
				timer.start(); // starts the timer
			}
			if(e.getKeyCode() == KeyEvent.VK_2){
				play = true;
				delay = 5 ;
				timer = new Timer(delay, this); // creating the timer object  
				timer.start(); // starts the timer
			}
			if(e.getKeyCode() == KeyEvent.VK_3){
				play = true;
				delay = 10 ;
				timer = new Timer(delay, this); // creating the timer object  
				timer.start(); // starts the timer
			}
		}

		public void keyReleased(KeyEvent e) {
		
		}
		
		public void moveRight() {
			play = true; // this will start the game on right key press
			playerX+=20; // a single press of right key will move slider by 20 pixels to right
		}
		public void moveLeft() {
			play = true; // this will start the game on left key press
			playerX-=20; // a single press of left key will move slider by 20 pixels to the left
		}
	

}
