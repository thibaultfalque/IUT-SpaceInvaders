package entities;

import Strategie.StrategieTir;
import niveau.Niveau;

/**
 * An entity representing a shot fired by the player's ship
 * 
 * @author Kevin Glass
 */
public class ShotEntity extends Entity {
	/** The vertical speed at which the players shot moves */
	private double moveSpeed;
	/** The game in which this entity exists */
	private Niveau niveau;
	
	/**Les dégats qu'infligent le missile*/
	private int degat;
	/** True if this shot has been "used", i.e. its hit something */
	private boolean used = false;
	
	
	private StrategieTir tir;
	
	/**
	 * Create a new shot from the player
	 * 
	 * @param game The game in which the shot has been created
	 * @param sprite The sprite representing this shot
	 * @param x The initial x location of the shot
	 * @param y The initial y location of the shot
	 */
	public ShotEntity(Niveau niveau,String sprite,int x,int y,double moveSpeed,int degat,StrategieTir t) {
		super(sprite,x,y);
		this.niveau = niveau;
		this.moveSpeed = moveSpeed;
		dy = moveSpeed;
		this.degat=degat;
		tir=t;
	}

	/**
	 * Request that this shot moved based on time elapsed
	 * 
	 * @param delta The time that has elapsed since last move
	 */
	public void move(long delta) {
		// proceed with normal move
		//super.move(delta);
		
		tir.move(this,delta);
		
		// if we shot off the screen, remove yourselfs
		if (y < -100) {
			niveau.remove(this);
		}
	}
	
	/**
	 * Notification that this shot has collided with another
	 * entity
	 * 
	 * @parma other The other entity with which we've collided
	 */
	public void collidedWith(Entity other) {
		// prevents double kills, if we've already hit something,
		// don't collide
		AlienEntity ae=(AlienEntity)other;
		if (!Entity.collidesWith(this,other) || used || ae.isTouch()) {
			return;
		}
		ae.retirerVie(degat);
		
		// remove the affected entities
		niveau.remove(this);
		if(ae.getVie()<=0){
			niveau.remove(other);
			niveau.notifyAlienKilled();
			ae.setTouch(true);
		}
		used = true;
		
	}

    @Override
    public void doLogic() {
        // FIXME Auto-generated method stub
        
    }

	public double getDegat() {
		return degat;
	}
    public void updatePosition(double x,double y){
    	this.x+=x;
    	this.y+=y;
    }

}