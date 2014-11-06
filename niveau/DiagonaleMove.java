package niveau;

import base.Game;
import entities.AlienEntity;

public class DiagonaleMove implements StrategieMove {
	public DiagonaleMove(){
		
	}

	@Override
	public void move(long delta, AlienEntity ae,Game game) {
	
		if ((ae.getDx() > 0) && (ae.getY() > 550)) 
			game.updateLogic();
		
		if((ae.getDy()<0)&&(ae.getY()<50))
			game.updateLogic();
		ae.updatePosition((ae.getDx()*delta)/1000,(ae.getDy()*delta)/1000);
	}
	
	

}
