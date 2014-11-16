package Strategie;

import niveau.Niveau;
import entities.AlienEntity;

public class GaucheDroiteMove implements StrategieMove {

	@Override
	public void move(long delta, AlienEntity ae,Niveau game) {
		// if we have reached the left hand side of the screen and
		// are moving left then request a logic update 
		if ((ae.getHorizontalMovement() < 0) && (ae.getX() < 10)) 
			game.updateLogic();
		
		// and vice vesa, if we have reached the right hand side of 
		// the screen and are moving right, request a logic update
		if ((ae.getHorizontalMovement() > 0) && (ae.getX() > 1040)) 
			game.updateLogic();
		// proceed with normal move
		ae.updatePosition((ae.getHorizontalMovement()*delta)/1000,(ae.getVerticalMovement()*delta)/1000);

	}

}
