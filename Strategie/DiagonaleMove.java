package Strategie;

import niveau.Niveau;
import entities.AlienEntity;

public class DiagonaleMove implements StrategieMove {
	public DiagonaleMove(){
		
	}

	@Override
	public void move(long delta, AlienEntity ae,Niveau game) {
	
		if ((ae.getHorizontalMovement() > 0) && (ae.getX() > 550)) 
			game.updateLogic();
		if ((ae.getHorizontalMovement() < 0) && (ae.getX() < 10)) 
			game.updateLogic();
		if((ae.getVerticalMovement()<0)&&(ae.getY()<50))
			game.updateLogic();
		if((ae.getVerticalMovement()>0)&&(ae.getY()>750))
			game.updateLogic();
		ae.updatePosition((ae.getHorizontalMovement()*delta)/1000,(ae.getVerticalMovement()*delta)/1000);
	}
	
	

}
