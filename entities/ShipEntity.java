package entities;

import niveau.Niveau;
import Strategie.StrategieTir;
import base.Constante;

/**
 * The entity that represents the players ship
 * 
 * @author Kevin Glass
 */
public class ShipEntity extends Entity {
	/** The level in which the ship exists */
	private Niveau niveau;
	
	private int vie;
	private int vieDepart;
	private StrategieTir tir;
	private String pathTextureMissile;
	private double moveSpeedMissile;
	private int degat;
	/** The time at which last fired a shot */
	private long lastFire=0;
	
	/**
	 * Create a new entity to represent the players ship
	 *  
	 * @param game The game in which the ship is being created
	 * @param ref The reference to the sprite to show for the ship
	 * @param x The initial x location of the player's ship
	 * @param y The initial y location of the player's ship
	 * @param moveSpeed vitesse du joueur
	 * @param vie la vie initiale du joueur
	 */
	public ShipEntity(Niveau niveau,String ref,int x,int y,double moveSpeed,int vie,String pathTextureMissile, int degat, double moveSpeedMissile,StrategieTir tir) {
		super(ref,x,y,moveSpeed);
		this.niveau = niveau;
		this.vie=vie;
		this.vieDepart=vie;
		this.pathTextureMissile = pathTextureMissile;
		this.degat = degat;
		this.moveSpeedMissile = moveSpeedMissile;
		this.tir=tir;
		
	}
	
	/**
	 * Request that the ship move itself based on an elapsed ammount of
	 * time
	 * 
	 * @param delta The time that has elapsed since last move (ms)
	 */
	public void move(long delta) {
		// if we're moving left and have reached the left hand side
		// of the screen, don't move
		if ((dx < 0) && (x < 0)) {
			return;
		}
		// if we're moving right and have reached the right hand side
		// of the screen, don't move
		if ((dx > 0) && (x > Constante.WIDTH-85)) {
			return;
		}
		
		super.move(delta);
	}
	
	/**
	 * Notification that the player's ship has collided with something
	 * 
	 * @param other The entity with which the ship has collided
	 */
	public void collidedWith(Entity other) {
		// if its an alien, notify the game that the player
		// is dead
		if(((ShotEntity)other).isUsed() || !Entity.collidesWith(this,other))
			return;
		vie-=((ShotEntity)other).getDegat();
		((ShotEntity)other).setUsed(true);
		niveau.remove(other);
		if(vie<=0)
			niveau.notifyDeath();
		
	}

    @Override
    public void doLogic() {
        // FIXME Auto-generated method stub
        
    }

    public void init(){
    	vie=vieDepart;
    	x=Constante.WIDTH/2;
    	y=Constante.HEIGHT-50;
    }
	public int getVie() {
		return vie;
	}
	public ShotEntity getMissileFire(){
		lastFire=System.currentTimeMillis();
		return new ShotEntity(niveau, pathTextureMissile, x, y, moveSpeedMissile, degat, tir);
	}

	public long getLastFire() {
		return lastFire;
	}
	
    
    
}