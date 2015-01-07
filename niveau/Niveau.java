package niveau;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import Strategie.RandomDoLogic;
import Strategie.RandomMove;
import Strategie.StrategieTir;
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

	
	private long timeDebut;
	private long timeFin;
	
	public Niveau(Game game,UsineAlien sf,String pathTexture,int vie, double moveSpeed,String pathTextureMissile,int degat,double moveSpeedMissile,StrategieTir tir){
		this.sf=sf;
		this.game=game;
		ship = new ShipEntity(this,pathTexture,Constante.WIDTH/2,Constante.HEIGHT-50,moveSpeed,vie,pathTextureMissile,degat,moveSpeedMissile,tir);
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
		ship.init();
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
			if (System.currentTimeMillis() - alien.getLastFire() < Constante.FIRING_INTERVAL) {
				return;
			}
			Random rd=new Random();
			if(rd.nextInt(100)<=2)
				missileAlien.add(alien.getMissileFire());
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
		
		for(AlienEntity alien:alienEntities)
			if(Entity.collidesWith(alien,ship))
				notifyDeath();
		
		for(int i=0;i<alienEntities.size();i++){
			AlienEntity ae1=alienEntities.get(i);
			for(int j=i+1;j<alienEntities.size();j++){
				AlienEntity ae2=alienEntities.get(j);
				ae1.collidedWith(ae2);
			}
		}
		
	}
	private void moveManager(long delta){
		for(AlienEntity alien:alienEntities)
			alien.move(delta);
		for(ShotEntity missile:missileShip)
			missile.move(delta);
		for(ShotEntity missile:missileAlien)
			missile.move(delta);
		ship.move(delta);
	}
	private void drawManager(Graphics g){
		for(AlienEntity alien:alienEntities)
			alien.draw(g);
		for(ShotEntity missile:missileShip)
			missile.draw(g);
		for(ShotEntity missile:missileAlien)
			missile.draw(g);
		ship.draw(g);
	}
	
	private void executeLogic(){
		for(AlienEntity alien:alienEntities)
			alien.doLogic();
		logicRequiredThisLoop=false;
	}
	private void tryToFire(){
		if (System.currentTimeMillis() - ship.getLastFire() < Constante.FIRING_INTERVAL) {
			return;
		}
		missileShip.add(ship.getMissileFire());
	}
	private void createArrayAlien(){
		sf.createArrayAlien(this);
		alienEntities=sf.getAlien();
		alienCount=sf.getAlienCount();
	}
	private void removeList(){
		
		alienEntities.removeAll(removeArray);
		missileShip.removeAll(removeArray);
		missileAlien.removeAll(removeArray);
		removeArray.clear();	
	}
}
