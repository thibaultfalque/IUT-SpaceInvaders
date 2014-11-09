package base;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	/**
	 * The entry point into the game. We'll simply create an
	 * instance of class which will start the display and game
	 * loop.
	 * 
	 * @param argv The arguments that are passed into our game
	 */
	
	private final static int WIDTH=800;
	private final static int HEIGHT=600;
	public static void main(String argv[]) {
		
		JFrame j=new JFrame("Space Invaders 101");
		j.setSize(WIDTH,HEIGHT);
		//j.setVisible(true);
		
		
		//Menu m=new Menu(j);
		Game g =new Game(j);

		// Start the main game loop, note: this method will not
		// return until the game has finished running. Hence we are
		// using the actual main thread to run the game.
		g.gameLoop();
	}
}
