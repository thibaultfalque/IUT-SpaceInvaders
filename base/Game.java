package base;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import niveau.FormationCoeur;
import niveau.FormationRectangle;
import niveau.Niveau;
import Strategie.RandomDoLogic;
import Strategie.RandomMove;
import Strategie.GaucheDroiteDoLogic;
import Strategie.GaucheDroiteMove;
import entities.AlienEntity;
import entities.Entity;
import entities.ShipEntity;
import entities.ShotEntity;

/**
 * The main hook of our game. This class with both act as a manager
 * for the display and central mediator for the game logic. 
 * 
 * Display management will consist of a loop that cycles round all
 * entities in the game asking them to move and then drawing them
 * in the appropriate place. With the help of an inner class it
 * will also allow the player to control the main ship.
 * 
 * As a mediator it will be informed when entities within our game
 * detect events (e.g. alient killed, played died) and will take
 * appropriate game actions.
 * 
 * @author Kevin Glass
 */
public class Game extends Canvas {


	/** The stragey that allows us to use accelerate page flipping */
	private BufferStrategy strategy;
	
	/** True if the game is currently "running", i.e. the game loop is looping */
	private boolean gameRunning = true;
	
	//private ArrayList<AlienEntity> entities=new ArrayList<AlienEntity>();
	
	/** The list of entities that need to be removed from the game this loop */
	private ArrayList<Entity> removeList = new ArrayList<Entity>();
	
	/** The entity representing the player */
	private Entity ship;
	/** The speed at which the player's ship should move (pixels/sec) */
	private double moveSpeed = 300;
	/** The time at which last fired a shot */
	private long lastFire = 0;
	/** The interval between our players shot (ms) */
	private long firingInterval = 500;

	/** The message to display which waiting for a key press */
	private String message = "";
	/** True if we're holding up game play until a key has been pressed */
	private boolean waitingForKeyPress = true;
	/** True if the left cursor key is currently pressed */
	private boolean leftPressed = false;
	/** True if the right cursor key is currently pressed */
	private boolean rightPressed = false;
	/** True if we are firing */
	private boolean firePressed = false;
	/** True if game logic needs to be applied this loop, normally as a result of a game event */
	private boolean logicRequiredThisLoop = false;
	

	
	//paramètre après modif code
	private ArrayList<Niveau> niveau=new ArrayList<Niveau>();
	private int niveauCourant=0;
	private ArrayList<ShotEntity> missile=new ArrayList<ShotEntity>();
	public final static int WIDTH=1100;
	public final static int HEIGHT=700;
	public Score score;
	/**
	 * Construct our game and set it running.
	 */
	public Game(JFrame j) {
		// create a frame to contain our game
		JFrame container = j;
		
		// get hold the content of the frame and set up the resolution of the game
		JPanel panel=(JPanel)container.getContentPane();
		panel.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		panel.setLayout(null);
		
		// setup our canvas size and put it into the content of the frame
		setBounds(0,0,WIDTH,HEIGHT);
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
		addKeyListener(new KeyInputHandler());
		
		// request the focus so key events come to us
		requestFocus();

		// create the buffering strategy which will allow AWT
		// to manage our accelerated graphics
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		// initialise the entities in our game so there's something
		// to see at startup
		niveau.add(new Niveau(this,new FormationRectangle(this,new GaucheDroiteMove(),new GaucheDroiteDoLogic())));
		niveau.add(new Niveau(this,new FormationCoeur(this,"fichierFormation/FormationCoeur.level",new GaucheDroiteMove(),new GaucheDroiteDoLogic())));

		initEntities();
		
		score=new Score();
		
		gameLoop();
	}
	
	/**
	 * Start a fresh game, this should clear out any old data and
	 * create a new set.
	 */
	private void startGame() {
		// clear out any existing entities and intialise a new set
		if(niveauCourant!=0)
			niveau.get(niveauCourant).getArrayAlien().clear();
		missile.clear();
		missile=new ArrayList<ShotEntity>();
		removeList.clear();
		initEntities();
		// blank out any keyboard settings we might currently have
		leftPressed = false;
		rightPressed = false;
		firePressed = false;
	}
	
	/**
	 * Initialise the starting state of the entities (ship and aliens). Each
	 * entitiy will be added to the overall list of entities in the game.
	 */
	private void initEntities() {
		// create the player ship and place it roughly in the center of the screen
		ship = new ShipEntity(this,"sprites/ship.png",WIDTH/2,HEIGHT-150);
		if(niveauCourant!=0)
			niveau.get(niveauCourant).createArrayAlien(this);
		//entities=niveau.get(niveauCourant).getArrayAlien();
		
	}
	
	/**
	 * Notification from a game entity that the logic of the game
	 * should be run at the next opportunity (normally as a result of some
	 * game event)
	 */
	public void updateLogic() {
		logicRequiredThisLoop = true;
	}
	
	/**
	 * Remove an entity from the game. The entity removed will
	 * no longer move or be drawn.
	 * 
	 * @param entity The entity that should be removed
	 */
	public void remove(Entity entity) {
		removeList.add(entity);
	}
	/*public void removeMissile(ShotEntity entity) {
		removeList.add(entity);
	}*/
	
	/**
	 * Notification that the player has died. 
	 */
	public void notifyDeath() {
		message = "Oh no! They got you, try again?";
		waitingForKeyPress = true;
	}
	
	/**
	 * Notification that the player has won since all the aliens
	 * are dead.
	 */
	public void notifyWin() {
		niveauCourant++;
		if(niveauCourant>=niveau.size())
			message="YOU HAVE FINISH THE GAME";
		else
			message = "Well done! You Win in !\n Your score is "+score.getScore();
		waitingForKeyPress = true;
	}
	
	/**
	 * Notification that an alien has been killed
	 */
	public void notifyAlienKilled() {
		// reduce the alient count, if there are none left, the player has won!
		int alienCount=niveau.get(niveauCourant).getAlienCount();
		niveau.get(niveauCourant).setAlienCount(alienCount-1);
		alienCount=niveau.get(niveauCourant).getAlienCount();
		if (alienCount == 0) {
			notifyWin();
			return;
		}
		// if there are still some aliens left then they all need to get faster, so
		// speed up all the existing aliens
		for(AlienEntity entity : niveau.get(niveauCourant).getArrayAlien()){
			entity.setHorizontalMovement(entity.getHorizontalMovement() * 1.02);
			//S'il reste moins de 5 aliens on change la strategie de déplacement
			if(alienCount==5){
				entity.setStrategie(new RandomMove(),new RandomDoLogic());
				entity.setVerticalMovement(entity.getDx());
			}
			if(alienCount<=5){
				entity.setHorizontalMovement(entity.getHorizontalMovement() * 1.1);
			}
		}
		
		score.augmenterScore(200);
		
	}
	
	/**
	 * Attempt to fire a shot from the player. Its called "try"
	 * since we must first check that the player can fire at this 
	 * point, i.e. has he/she waited long enough between shots
	 */
	public void tryToFire() {
		// check that we have waiting long enough to fire
		if (System.currentTimeMillis() - lastFire < firingInterval) {
			return;
		}
		
		// if we waited long enough, create the shot entity, and record the time.
		lastFire = System.currentTimeMillis();
		ShotEntity shot = new ShotEntity(this,"sprites/laser3_haut.png",ship.getX()+15,ship.getY()-10);
		ShotEntity shot2 = new ShotEntity(this,"sprites/laser3_haut.png",ship.getX()+55,ship.getY()-10);
		missile.add(shot);
		missile.add(shot2);
	}
	
	/**
	 * The main game loop. This loop is running during all game
	 * play as is responsible for the following activities:
	 * <p>
	 * - Working out the speed of the game loop to update moves
	 * - Moving the game entities
	 * - Drawing the screen contents (entities, text)
	 * - Updating game events
	 * - Checking Input
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
			g.fillRect(0,0,WIDTH,HEIGHT);
			
			g.setColor(Color.white);
			g.drawString("Niveau: "+niveauCourant+1,10,20);
			score.draw(g);
			
			// cycle round asking each entity to move itself
			if (!waitingForKeyPress) {
				for(AlienEntity ae : niveau.get(niveauCourant).getArrayAlien())
					ae.move(delta);	
				for(ShotEntity m : missile)
					m.move(delta);
				ship.move(delta);
			}
			
			// cycle round drawing all the entities we have in the game
            if(niveau.get(niveauCourant).getArrayAlien()!=null)
            	try{
            		for(AlienEntity entity : niveau.get(niveauCourant).getArrayAlien())
            			entity.draw(g);
            	}
            	catch(Exception e){
            		
            	}
            		
            if(missile!=null)
            	for(ShotEntity m:missile)
            		m.draw(g);
			ship.draw(g);
			// brute force collisions, compare every entity against
			// every other entity. If any of them collide notify 
			// both entities that the collision has occured
			if(!waitingForKeyPress){
				if(missile!=null){
					for(ShotEntity m:missile)
						for(AlienEntity ae:niveau.get(niveauCourant).getArrayAlien()){
							m.collidedWith(ae);
							//ae.collidedWith(m);
						}
					for(AlienEntity ae:niveau.get(niveauCourant).getArrayAlien())
						ship.collidedWith(ae);
				}
				for(int i=0;i<niveau.get(niveauCourant).getArrayAlien().size();i++){
					AlienEntity ae1=niveau.get(niveauCourant).getArrayAlien().get(i);
					for(int j=i+1;j<niveau.get(niveauCourant).getArrayAlien().size();j++){
						AlienEntity ae2=niveau.get(niveauCourant).getArrayAlien().get(j);
						ae1.collidedWith(ae2);
						ae2.collidedWith(ae1);
					}
				}
				
			}
			// remove any entity that has been marked for clear up
			
			if(!removeList.isEmpty()){ 
				niveau.get(niveauCourant).getArrayAlien().removeAll(removeList);
				missile.removeAll(removeList);
				removeList.clear();
			}

			// if a game event has indicated that game logic should
			// be resolved, cycle round every entity requesting that
			// their personal logic should be considered.
			if (logicRequiredThisLoop) {
			    for(Entity entity : niveau.get(niveauCourant).getArrayAlien()) {
					entity.doLogic();
				}
				logicRequiredThisLoop = false;
			}
			
			// if we're waiting for an "any key" press then draw the 
			// current message 
			if (waitingForKeyPress) {
				g.setColor(Color.white);
				g.drawString(message,(800-g.getFontMetrics().stringWidth(message))/2,250);
				g.drawString("Press any key",(800-g.getFontMetrics().stringWidth("Press any key"))/2,300);
			}
			
			// finally, we've completed drawing so clear up the graphics
			// and flip the buffer over
			g.dispose();
			strategy.show();
			
			// resolve the movement of the ship. First assume the ship 
			// isn't moving. If either cursor key is pressed then
			// update the movement appropraitely
			ship.setHorizontalMovement(0);
			
			if ((leftPressed) && (!rightPressed)) {
				ship.setHorizontalMovement(-moveSpeed);
			} else if ((rightPressed) && (!leftPressed)) {
				ship.setHorizontalMovement(moveSpeed);
			}
			
			// if we're pressing fire, attempt to fire
			if (firePressed) {
				tryToFire();
			}
			
			// finally pause for a bit. Note: this should run us at about
			// 100 fps but on windows this might vary each loop due to
			// a bad implementation of timer
			try { Thread.sleep(5); } catch (Exception e) {}
		}
	}
	
	
	
	
	/**
	 * A class to handle keyboard input from the user. The class
	 * handles both dynamic input during game play, i.e. left/right 
	 * and shoot, and more static type input (i.e. press any key to
	 * continue)
	 * 
	 * This has been implemented as an inner class more through 
	 * habbit then anything else. Its perfectly normal to implement
	 * this as seperate class if slight less convienient.
	 * 
	 * @author Kevin Glass
	 */
	private class KeyInputHandler extends KeyAdapter {
		/** The number of key presses we've had while waiting for an "any key" press */
		private int pressCount = 1;
		
		/**
		 * Notification from AWT that a key has been pressed. Note that
		 * a key being pressed is eqniveaul to being pushed down but *NOT*
		 * released. Thats where keyTyped() comes in.
		 *
		 * @param e The details of the key that was pressed 
		 */
		public void keyPressed(KeyEvent e) {
			// if we're waiting for an "any key" typed then we don't 
			// want to do anything with just a "press"
			if (waitingForKeyPress) {
				return;
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				firePressed = true;
			}
		} 
		
		/**
		 * Notification from AWT that a key has been released.
		 *
		 * @param e The details of the key that was released 
		 */
		public void keyReleased(KeyEvent e) {
			// if we're waiting for an "any key" typed then we don't 
			// want to do anything with just a "released"
			if (waitingForKeyPress) {
				return;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				firePressed = false;
			}
		}

		/**
		 * Notification from AWT that a key has been typed. Note that
		 * typing a key means to both press and then release it.
		 *
		 * @param e The details of the key that was typed. 
		 */
		public void keyTyped(KeyEvent e) {
			// if we're waiting for a "any key" type then
			// check if we've recieved any recently. We may
			// have had a keyType() event from the user releasing
			// the shoot or move keys, hence the use of the "pressCount"
			// counter.
			System.out.println("appui");
			if (waitingForKeyPress) {
				if (pressCount == 1) {
					// since we've now recieved our key typed
					// event we can mark it as such and start 
					// our new game
					waitingForKeyPress = false;
					startGame();
					pressCount = 0;
				} else {
					pressCount++;
				}
			}
			
			// if we hit escape, then quit the game
			if (e.getKeyChar() == 27) {
				System.exit(0);
			}
		}
	}
	

}
