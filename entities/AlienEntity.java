package entities;

import java.awt.Graphics;

import niveau.Niveau;
import Strategie.StrategieDoLogic;
import Strategie.StrategieMove;
import Strategie.StrategieTir;

/**
 * An entity which represents one of our space invader aliens.
 * 
 * @author Kevin Glass
 */
public class AlienEntity extends Entity {
	/** The life of alien **/
	private int vie;
	/** The game in which the entity exists */
	private Niveau niveau;

	private StrategieMove move;
	private StrategieDoLogic doLogic;

	private StrategieTir tir ;
	private String pathTextureMissile;
	private double moveSpeedMissile;
	private int degat;

	private boolean touch = false;
	private LifeBar pb;
	
	private long lastFire=0;

	/**
	 * Create a new alien entity
	 * 
	 * @param game
	 *            The game in which this entity is being created
	 * @param ref
	 *            The sprite which should be displayed for this alien
	 * @param x
	 *            The intial x location of this alien
	 * @param y
	 *            The intial y location of this alien
	 */
	public AlienEntity(Niveau niveau, String ref, int x, int y,
			StrategieMove m, StrategieDoLogic dl, double moveSpeed, int vie,
			String pathTextureMissile, int degat, double moveSpeedMissile,StrategieTir t) {
		
		super(ref, x, y,moveSpeed);
		this.vie = vie;
		this.niveau = niveau;
		this.pathTextureMissile = pathTextureMissile;
		this.degat = degat;
		this.moveSpeedMissile = moveSpeedMissile;
		
		tir=t;
		move = m;
		move.init(this);
		doLogic = dl;

		pb = new LifeBar(x +sprite.getWidth()/2, y - 10);
		pb.setStep(vie);
	}

	public void setStrategie(StrategieMove stratM, StrategieDoLogic stratD) {
		move = stratM;
		doLogic = stratD;
		move.init(this);
	}
	
	public long getLastFire(){
		return lastFire;
	}
	public ShotEntity getMissileFire(){
		lastFire = System.currentTimeMillis();
		return new ShotEntity(niveau, pathTextureMissile, x+sprite.getWidth()/2, y+sprite.getHeight()-10, moveSpeedMissile, degat, tir);
	}
	
	/**
	 * Request that this alien moved based on time elapsed
	 * 
	 * @param delta
	 *            The time that has elapsed since last move
	 */
	public void move(long delta) {
		move.move(delta, this, niveau);
		pb.move((int) x - 8, (int) y - 10);
	}

	/**
	 * Update the game logic related to aliens
	 */

	public void doLogic() {
		doLogic.doLogic(niveau, this);
	}

	/**
	 * Notification that this alien has collided with another entity
	 * 
	 * @param other
	 *            The other entity
	 */
	public void collidedWith(Entity other) {
		if (!Entity.collidesWith(this, other))
			return;
		niveau.updateLogic();

	}

	public void updatePosition(double dx, double dy) {
		x += dx;
		y += dy;
	}
	public boolean isTouch() {
		return touch;
	}

	public void setTouch(boolean touch) {
		this.touch = touch;
	}

	public void retirerVie(int degat) {
		vie -= degat;
		pb.setValue(vie);
	}

	public int getVie() {
		return vie;
	}
	
	public void setPosition(double x,double y){
		setX(x);
		setY(y);
		move.init(this);
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		pb.draw(g);
	}
}