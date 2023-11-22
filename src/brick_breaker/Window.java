package brick_breaker;

import javax.swing.JFrame;

public class Window {
	
	public static void main(String[] args) {
		JFrame obj = new JFrame(); // the window which contains the game
		process_and_component_design process_and_component_design = new process_and_component_design(); // adding the process_and_component_design panel into the window
		obj.setBounds(300, 70, 700, 600);// this determines the size of the window
		obj.setTitle("Brick Breaker"); // setting the title of the application
		obj.setResizable(false); // window resizable or not 
		obj.setVisible(true); // to make the window explicitly visible
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(process_and_component_design);
	}

}
 