package niveau;

import java.util.ArrayList;

import base.Game;
import entities.AlienEntity;

public  abstract class UsineAlien {
	/** The number of aliens left on the screen */
	protected int alienCount;
	protected ArrayList<AlienEntity> entities=new ArrayList<AlienEntity>();
	protected Game game;
	public UsineAlien(Game game){
		this.game=game;
	}
	public int getAlienCount(){
		return alienCount;
	}
	
	public void setAlienCount(int alienCount) {
		this.alienCount = alienCount;

	}
	public ArrayList<AlienEntity> getArrayAlien(){
		return entities;
	}
	public void createArrayAlien(){
		
	}

}
