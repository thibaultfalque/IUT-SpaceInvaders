package entities;

import java.awt.Graphics;

import niveau.Niveau;
import GUI.ProgressBar;
import Strategie.StrategieDoLogic;
import Strategie.StrategieMove;

/**
 * An entity which represents one of our space invader aliens.
 * 
 * @author Kevin Glass
 */
public class AlienEntity extends Entity {
	/** The speed at which the alient moves horizontally */
	private double moveSpeed;
	/**The life of alien**/
	private int vie;
	/** The game in which the entity exists */
	private Niveau niveau;
	
	private StrategieMove move;
	private StrategieDoLogic doLogic;
	private boolean touch=false;
	private ProgressBar pb; 
	/**
	 * Create a new alien entity
	 * 
	 * @param game The game in which this entity is being created
	 * @param ref The sprite which should be displayed for this alien
	 * @param x The intial x location of this alien
	 * @param y The intial y location of this alient
	 */
	public AlienEntity(Niveau niveau,String ref,int x,int y,StrategieMove m,StrategieDoLogic dl,int moveSpeed,int vie) {
		super(ref,x,y);
		this.moveSpeed=moveSpeed;
		this.vie=vie;
		move=m;
		doLogic=dl;
		this.niveau = niveau;
		dx = -moveSpeed;
		pb=new ProgressBar(x-8, y-10);
		pb.setStep(vie);
	}
	
	
	public void setStrategie(StrategieMove stratM,StrategieDoLogic stratD){
		move=stratM;
		doLogic=stratD;
	}
	/**
	 * Request that this alien moved based on time elapsed
	 * 
	 * @param delta The time that has elapsed since last move
	 */
	public void move(long delta) {
		move.move(delta,this,niveau);
		pb.move((int)x-8,(int)y-10);
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
	 * @param other The other entity
	 */
	public void collidedWith(Entity other) {
		if(!Entity.collidesWith(this,other))
			return;
		niveau.updateLogic();
		
	}
	public void updatePosition(double dx,double dy){
		x+=dx;
		y+=dy;
	}


	public double getMoveSpeed() {
		return moveSpeed;
	}


	public boolean isTouch() {
		return touch;
	}


	public void setTouch(boolean touch) {
		this.touch = touch;
	}
	public void retirerVie(int degat){
		vie-=degat;
		pb.setValue(vie);
	}
	public int getVie(){
		return vie;
	}
	public void draw(Graphics g){
		super.draw(g);
		pb.draw(g);
	}
}