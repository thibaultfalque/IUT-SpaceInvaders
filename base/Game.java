package base;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import niveau.Niveau;
import niveau.UsineAlien;
import Strategie.DiagonaleDoLogic;
import Strategie.DiagonaleMove;
import Strategie.GaucheDroiteDoLogic;
import Strategie.GaucheDroiteMove;
import Strategie.TirVerticalBas;
import Strategie.TirVerticalHaut;

/**
 * The main hook of our game. This class with both act as a manager for the
 * display and central mediator for the game logic.
 * 
 * Display management will consist of a loop that cycles round all entities in
 * the game asking them to move and then drawing them in the appropriate place.
 * With the help of an inner class it will also allow the player to control the
 * main ship.
 * 
 * As a mediator it will be informed when entities within our game detect events
 * (e.g. alient killed, played died) and will take appropriate game actions.
 * 
 * @author Kevin Glass
 */
public class Game extends Canvas implements Observateur {



	/** The stragey that allows us to use accelerate page flipping */
	private BufferStrategy strategy;

	/** True if the game is currently "running", i.e. the game loop is looping */
	private boolean gameRunning = true;

	/** The list of entities that need to be removed from the game this loop */

	/** The message to display which waiting for a key press */
	private String message = "";

	private ArrayList<Niveau> arrayNiveau = new ArrayList<Niveau>();
	private int indexNiveauCourant = 0;
	private Niveau niveauCourant;
	private Score score=new Score();
	private Sprite background;
	private KeyInputHandler keyboard;
	private boolean death = false;
	private JFrame container;

		/**
		 * Construct our game and set it running.
		 */
		public Game() {
	
			// create a frame to contain our game
			container = new JFrame();
			// get hold the content of the frame and set up the resolution of the
			// game
			JPanel panel = (JPanel) container.getContentPane();
			panel.setPreferredSize(new Dimension(Constante.WIDTH, Constante.HEIGHT));
			panel.setLayout(null);
	
			// setup our canvas size and put it into the content of the frame
			setBounds(0, 0, Constante.WIDTH, Constante.HEIGHT);
			panel.add(this);
			
			// Tell AWT not to bother repainting our canvas since we're
			// going to do that our self in accelerated mode
			setIgnoreRepaint(true);
	
			// finally make the window visible
	
			container.pack();
			container.setResizable(false);
			container.setVisible(true);
	
			// add a listener to respond to the user closing the window. If they
			// do we'd like to exit the game
			container.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
	
			// add a key input system (defined below) to our canvas
			// so we can respond to key pressed
			keyboard = new KeyInputHandler();
			keyboard.setWaitingForKeyPress(true);
			keyboard.add(this);
			addKeyListener(keyboard);
	
			// request the focus so key events come to us
			requestFocus();
	
			// create the buffering strategy which will allow AWT
			// to manage our accelerated graphics
			createBufferStrategy(2);
			strategy = getBufferStrategy();
	
			// initialise the level
			

			arrayNiveau.add(new Niveau(this, new UsineAlien(
					"fichierFormation/FormationV.level", "sprites/alien.png", 40,
					75, new DiagonaleMove(), new DiagonaleDoLogic(),
					"sprites/laser.png", 20, 300, new TirVerticalBas()),
					"sprites/ship.png", 100, 300, "sprites/laser3_haut.png", 20,
					300, new TirVerticalHaut()));
	
			arrayNiveau.add(new Niveau(this, new UsineAlien(
					"fichierFormation/FormationRectangle.level",
					"sprites/alien.png", 75, 75, new GaucheDroiteMove(),
					new GaucheDroiteDoLogic(), "sprites/laser.png", 25, 300,
					new TirVerticalBas()), "sprites/ship.png", 200, 300,
					"sprites/laser3_haut.png", 20, 300, new TirVerticalHaut()));
	
			arrayNiveau.add(new Niveau(this, new UsineAlien(
					"fichierFormation/FormationCoeur.level", "sprites/alien2.png",
					100, 100, new GaucheDroiteMove(), new GaucheDroiteDoLogic(),
					"sprites/laser.png", 25, 300, new TirVerticalBas()),
					"sprites/ship.png", 200, 300, "sprites/laser3_haut.png", 25,
					300, new TirVerticalHaut()));
			
			arrayNiveau.add(new Niveau(this, new UsineAlien(
					"fichierFormation/boss.level", "sprites/boss.png",
					500, 150, new GaucheDroiteMove(), new GaucheDroiteDoLogic(),
					"sprites/laser1.png", 50, 300, new TirVerticalBas()),
					"sprites/ship.png", 200, 300, "sprites/laser3_haut.png", 25,
					300, new TirVerticalHaut()));
			
			arrayNiveau.add(new Niveau(this, new UsineAlien(
					"fichierFormation/boss.level", "sprites/boss.png", 750,
					75, new GaucheDroiteMove(), new GaucheDroiteDoLogic(),
					"sprites/laser1.png", 30, 300, new TirVerticalBas()),
					"sprites/ship.png", 200, 300, "sprites/laser3_haut.png", 20,
					300, new TirVerticalHaut()));
			
	
			niveauCourant = arrayNiveau.get(indexNiveauCourant);
	
	
			background = SpriteStore.get().getSprite("sprites/back.png");
	
		}
	
	/**
	 * Start a fresh game, this should clear out any old data and create a new
	 * set.
	 */
	private void startGame() {
		score.initScore();
		death = false;
		niveauCourant.start();
	}

	/**
	 * Notification that the player has died.
	 */
	public void notifyDeath() {
		message = "Oh no! They got you, try again?";
		keyboard.setWaitingForKeyPress(true);
		keyboard.add(this);
		death = true;
	}

	/**
	 * Notification that the player has won since all the aliens are dead.
	 */
	public void notifyWin() {
		indexNiveauCourant++;
		if (indexNiveauCourant >= arrayNiveau.size()) {
			message = "YOU HAVE FINISH THE GAME";
			indexNiveauCourant = 0;
			niveauCourant = arrayNiveau.get(0);
			score.initScore();
		} else {
			message = "Well done! You Win in !\n Your score is "
					+ score.getScore();
		}
		niveauCourant = arrayNiveau.get(indexNiveauCourant);
		keyboard.setWaitingForKeyPress(true);
		keyboard.add(this);
	}

	/**
	 * The main game loop. This loop is running during all game play as is
	 * responsible for the following activities:
	 * <p>
	 * - Working out the speed of the game loop to update moves - Moving the
	 * game entities - Drawing the screen contents (entities, text) - Updating
	 * game events - Checking Input
	 * <p>
	 */
	public void gameLoop() {
		long lastLoopTime = System.currentTimeMillis();

		// keep looping round til the game ends
		while (gameRunning) {
			// work out how long its been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop
			long delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();

			// Get hold of a graphics context for the accelerated
			// surface and blank it out
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, Constante.WIDTH, Constante.HEIGHT);
			background.draw(g, 0, 0);

			if (!keyboard.isWaitingForKeyPress() && !death) {
				g.setColor(Color.white);
				g.drawString("Niveau: " + (indexNiveauCourant + 1), 10, 20);
				score.draw(g);

				g.setColor(Color.white);
				g.drawString("Vie " + niveauCourant.getLifeOfPlayer(), 150, 20);

				niveauCourant.loop(delta, g, keyboard);
			} else {
				g.setColor(Color.white);
				g.drawString(message, (Constante.WIDTH - g.getFontMetrics()
						.stringWidth(message)) / 2, 250);
				g.drawString("Press any key", (Constante.WIDTH - g
						.getFontMetrics().stringWidth("Press any key")) / 2,
						300);
			}

			// finally, we've completed drawing so clear up the graphics
			// and flip the buffer over
			g.dispose();
			strategy.show();

			// finally pause for a bit. Note: this should run us at about
			// 100 fps but on windows this might vary each loop due to
			// a bad implementation of timer
			try {
				Thread.sleep(5);
			} catch (Exception e) {
			}
		}
	}


	public void changeScore(int x) {
		score.augmenterScore(x);
	}

	@Override
	public void handleEvent(KeyInputHandler keyboard) {
		if (!keyboard.isWaitingForKeyPress()) {
			startGame();
			keyboard.remove();
		}
	}
}
