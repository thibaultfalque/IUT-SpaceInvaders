package niveau;

import entities.AlienEntity;
import base.Game;

public class Niveau3 extends UsineAlien {

	public Niveau3(Game game) {
		super(game);
		// create a block of aliens (3 rows, by 10 aliens, spaced evenly)
		alienCount = 0;
		/*for (int row=0;row<3;row++) {
			for (int x=0;x<10;x++) {
				Entity alien = new AlienEntity(game,"sprites/alien.gif",100+(x*50),(50)+row*30,new DiagonaleMove(),new DiagonaleDoLogic());
				entities.add(alien);
				alienCount++;
			}
		}*/
		AlienEntity alien = new AlienEntity(game,"sprites/alien.gif",100+50,(50)+30,new DiagonaleMove(),new DiagonaleDoLogic());
		alien.setVerticalMovement(alien.getMoveSpeed());
		entities.add(alien);
		alienCount++;
	}
	@Override
	public void createArrayAlien(){
		
	}
}
