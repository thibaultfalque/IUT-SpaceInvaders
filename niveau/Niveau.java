package niveau;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Strategie.RandomDoLogic;
import Strategie.RandomMove;
import Strategie.TirVertical;
import base.Constante;
import base.Game;
import base.KeyInputHandler;
import entities.AlienEntity;
import entities.Entity;
import entities.ShipEntity;
import entities.ShotEntity;

public class Niveau{
	/** The number of aliens left on the screen */
	private int alienCount;
	private ArrayList<AlienEntity> alienEntities=new ArrayList<AlienEntity>();
	private ArrayList<ShotEntity> missileAlien=new ArrayList<ShotEntity>();
	private ArrayList<ShotEntity> missileShip=new ArrayList<ShotEntity>();
	private ArrayList<Entity> removeArray=new ArrayList<Entity>();
	private UsineAlien sf;
	private Game game;
	private ShipEntity ship;
	
	/** True if game logic needs to be applied this loop, normally as a result of a game event */
	private boolean logicRequiredThisLoop=false;
	/** The time at which last fired a shot */
	private long lastFire=0;
	
	private long timeDebut;
	private long timeFin;
	
	public Niveau(Game game,UsineAlien sf){
		this.sf=sf;
		this.game=game;
		ship = new ShipEntity(this,"sprites/ship.png",Constante.WIDTH/2,Constante.HEIGHT-150,300,100);
	}

	/**
	 * Notification from a game entity that the logic of the game
	 * should be run at the next opportunity (normally as a result of some
	 * game event)
	 */
	public void updateLogic(){
		logicRequiredThisLoop=true;
	}
	public void notifyDeath(){
		game.notifyDeath();
	}
	public void notifyWin(){
		timeFin=System.currentTimeMillis();
		game.changeScore((int) (timeFin/1000-timeDebut/1000));
		game.notifyWin();
		
	}
	public void notifyAlienKilled(){
		alienCount=alienCount-1;
		if(alienCount==0){
			notifyWin();
			return;
		}
		for(AlienEntity alien : alienEntities){
			alien.setHorizontalMovement(alien.getHorizontalMovement() * 1.02);
			//S'il reste moins de 5 aliens on change la strategie de déplacement
			if(alienCount==5){
				alien.setStrategie(new RandomMove(),new RandomDoLogic());
				alien.setVerticalMovement(alien.getHorizontalMovement());
			}
			if(alienCount<=5){
				alien.setHorizontalMovement(alien.getHorizontalMovement() * 1.1);
			}
		}
	}
	
	public void remove(Entity other){
		removeArray.add(other);
	}
	
	public int getLifeOfPlayer(){
		return ship.getVie();
	}
	
	public void moveShip(KeyInputHandler keyboard){
		ship.setHorizontalMovement(0);
		if(keyboard.isKeyPressed(KeyEvent.VK_LEFT) && !keyboard.isKeyPressed(KeyEvent.VK_RIGHT) )
			ship.setHorizontalMovement(-(ship.getMoveSpeed()));
		if(!keyboard.isKeyPressed(KeyEvent.VK_LEFT) && keyboard.isKeyPressed(KeyEvent.VK_RIGHT) )
			ship.setHorizontalMovement(ship.getMoveSpeed());
		if(keyboard.isKeyPressed(KeyEvent.VK_SPACE))
			tryToFire();
	}
	
	
	public void start(){
		alienEntities.clear();
		missileAlien.clear();
		missileShip.clear();
		removeArray.clear();
		createArrayAlien();
		timeDebut=System.currentTimeMillis();
	}
	
	public void loop(long delta,Graphics g,KeyInputHandler keyboard){
		
		moveManager(delta);
		drawManager(g);
		collisionManager();
		removeList();
		if(logicRequiredThisLoop)
			executeLogic();
		moveShip(keyboard);
		alienFire();
	}
	
	private void alienFire() {
		for(AlienEntity alien:alienEntities){
			//if(alien.getX()<Constante.WIDTH/2)
				//missileAlien.add(new ShotEntity(this, , x, y, moveSpeed, degat, t))
		}
		
	}

	private void collisionManager(){
		for(ShotEntity missile: missileShip)
			for(AlienEntity alien:alienEntities){	
				missile.collidedWith(alien);
				if(Entity.collidesWith(missile, alien))
					game.changeScore(100);
			}
		for(ShotEntity missile:missileAlien)
			ship.collidedWith(missile);
		for(AlienEntity alien:alienEntities){
			if(Entity.collidesWith(alien,ship))
				notifyDeath();
				
		}
		
	}
	private void moveManager(long delta){
		for(AlienEntity alien:alienEntities)
			alien.move(delta);
		for(ShotEntity missile:missileShip)
			missile.move(delta);
		ship.move(delta);
	}
	private void drawManager(Graphics g){
		for(AlienEntity alien:alienEntities)
			alien.draw(g);
		for(ShotEntity missile:missileShip)
			missile.draw(g);
		ship.draw(g);
	}
	
	private void executeLogic(){
		for(AlienEntity alien:alienEntities)
			alien.doLogic();
		logicRequiredThisLoop=false;
	}
	private void tryToFire(){
		if (System.currentTimeMillis() - lastFire < Constante.FIRING_INTERVAL) {
			return;
		}
		lastFire = System.currentTimeMillis();
		missileShip.add(new ShotEntity(this,"sprites/laser3_haut.png",ship.getX()+15,ship.getY()-10,-300,20,new TirVertical()));
	}
	private void createArrayAlien(){
		sf.createArrayAlien(this);
		alienEntities=sf.getAlien();
		alienCount=sf.getAlienCount();
	}
	private void removeList(){
		if(removeArray.isEmpty() || alienEntities.isEmpty() || missileShip.isEmpty())
			return;
		alienEntities.removeAll(removeArray);
		missileShip.removeAll(removeArray);
		removeArray.clear();	
	}
}
