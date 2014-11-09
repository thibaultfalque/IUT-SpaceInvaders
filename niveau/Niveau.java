package niveau;

import java.util.ArrayList;

import Strategie.StrategieDoLogic;
import Strategie.StrategieMove;
import base.Game;
import entities.AlienEntity;

public class Niveau {
	/** The number of aliens left on the screen */
	protected int alienCount;
	protected ArrayList<AlienEntity> entities=new ArrayList<AlienEntity>();
	protected Game game;
	protected UsineAlien sf;
	public Niveau(Game game,UsineAlien sf){
		this.game=game;
		this.sf=sf;
		entities=sf.getAlien();
		alienCount=sf.getAlienCount();
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
	public void createArrayAlien(Game game){
		sf.createArrayAlien(game);
	}
}
