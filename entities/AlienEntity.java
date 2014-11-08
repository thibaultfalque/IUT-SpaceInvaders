package entities;

import niveau.StrategieDoLogic;
import niveau.StrategieMove;
import base.Game;

/**
 * An entity which represents one of our space invader aliens.
 * 
 * @author Kevin Glass
 */
public class AlienEntity extends Entity {
	/** The speed at which the alient moves horizontally */
	private double moveSpeed = 75;
	/** The game in which the entity exists */
	private Game game;
	
	private StrategieMove move;
	private StrategieDoLogic doLogic;
	/**
	 * Create a new alien entity
	 * 
	 * @param game The game in which this entity is being created
	 * @param ref The sprite which should be displayed for this alien
	 * @param x The intial x location of this alien
	 * @param y The intial y location of this alient
	 */
	public AlienEntity(Game game,String ref,int x,int y,StrategieMove m,StrategieDoLogic dl) {
		super(ref,x,y);
		move=m;
		doLogic=dl;
		this.game = game;
		dx = -moveSpeed;
		//dy = moveSpeed;
	}
	
	
	public void setStrategieMove(StrategieMove stratM){
		move=stratM;
	}
	/**
	 * Request that this alien moved based on time elapsed
	 * 
	 * @param delta The time that has elapsed since last move
	 */
	public void move(long delta) {
		move.move(delta,this,game);
	}
	
	/**
	 * Update the game logic related to aliens
	 */

	public void doLogic() {
		doLogic.doLogic(game, this);
	}
	
	/**
	 * Notification that this alien has collided with another entity
	 * 
	 * @param other The other entity
	 */
	public void collidedWith(Entity other) {
		// collisions with aliens are handled elsewhere
	}
	public void updatePosition(double dx,double dy){
		x+=dx;
		y+=dy;
	}

	public Game getGame() {
		return game;
	}

	public double getMoveSpeed() {
		return moveSpeed;
	}
	
	
}