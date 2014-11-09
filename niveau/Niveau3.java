package niveau;

import entities.AlienEntity;
import Strategie.DiagonaleDoLogic;
import Strategie.DiagonaleMove;
import base.Game;

public class Niveau3 extends UsineAlien {

	public Niveau3(Game game) {
		super(game);
		// create a block of aliens (3 rows, by 10 aliens, spaced evenly)
		
	}
	@Override
	public void createArrayAlien(){
		alienCount = 0;
		for(int i=0;i<4;i++){
			AlienEntity alien = new AlienEntity(game,"sprites/alien.gif",100+(50*i)+30,50*i+30,new DiagonaleMove(),new DiagonaleDoLogic());
			
			alien.setHorizontalMovement(alien.getMoveSpeed());
			alien.setVerticalMovement(alien.getMoveSpeed());
			entities.add(alien);
			alienCount++;
		}
		for(int i=0;i<4;i++){
			AlienEntity alien2 = new AlienEntity(game,"sprites/alien.gif",550+(50*-i)+30,50*i+30,new DiagonaleMove(),new DiagonaleDoLogic());
			alien2.setHorizontalMovement(-alien2.getMoveSpeed());
			alien2.setVerticalMovement(alien2.getMoveSpeed());
			entities.add(alien2);
			alienCount++;
		}
	}
}
