package niveau;

import entities.AlienEntity;
import base.Game;

public class Niveau1 extends UsineAlien {
	public Niveau1(Game game){
		super(game);
		// create a block of aliens (3 rows, by 10 aliens, spaced evenly)
		createArrayAlien();
	}
	@Override
	public void createArrayAlien(){
		alienCount = 0;
		for (int row=0;row<3;row++) {
			for (int x=0;x<10;x++) {
				AlienEntity alien = new AlienEntity(game,"sprites/alien.gif",100+(x*50),(50)+row*30,new GaucheDroiteMove(),new GaucheDroiteDoLogic());
				entities.add(alien);
				alienCount++;
			}
		}
	}
}
