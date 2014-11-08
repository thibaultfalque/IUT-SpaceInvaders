package niveau;

import java.util.Random;

import base.Game;
import entities.AlienEntity;

public class RandomMove implements StrategieMove {
	public RandomMove(){
		
	}

	@Override
	public void move(long delta, AlienEntity ae,Game game) {
		Random rd=new Random();

		if ((ae.getDx() > 0) && (ae.getY() > 550)) 
			game.updateLogic();
		
		if((ae.getDy()<0)&&(ae.getY()<50))
			game.updateLogic();
		ae.updatePosition(((rd.nextInt(6)-3)*delta)/1000,((rd.nextInt(6)-3)*delta)/1000);
	}
	
	

}
