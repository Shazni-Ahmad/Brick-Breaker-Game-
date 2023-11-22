package brick_breaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Brick_area {
	// this class creates the brick area 
	
	public int map[][]; // creating a 2d array
	public int brickWidth;
	public int brickHeight;
	public Brick_area(int row, int col) {
		map = new int[row][col]; // instantiating the 2d array with no of rows and cols 
		for(int i = 0; i < map.length; i++) { // iterating through the rows
			for(int j=0; j< map[0].length; j++) { // iterating through the columns in a particular row
				map[i][j] = 1; // at positions where map[i][j]=1 bricks will be created
			}
		}
			
		brickWidth = 540/col;
		brickHeight = 150/row;
	}
		public void draw(Graphics2D g) {
			// function to draw the bricks where the value of map[i][j]=1
			for(int i = 0; i < map.length; i++) {
				for(int j=0; j< map[0].length; j++) {
					if(map[i][j]>0) { // if value is 1 draw takes place
						g.setColor(Color.white);
						g.fillRect(j * brickWidth + 80,  i * brickHeight + 50,  brickWidth,  brickHeight);
						
						// creating a border between the bricks to differentiate the bricks
						g.setStroke(new BasicStroke(3));
						g.setColor(Color.black);
						g.drawRect(j * brickWidth + 80,  i * brickHeight + 50,  brickWidth,  brickHeight);
						
					}
					
				}
			}
		}
		public void setBrickValue(int value, int row, int col) {
			// this function is to set 0 or 1 at this position map[row][col]
			map[row][col]=value;
		}

}
