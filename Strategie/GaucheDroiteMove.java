package Strategie;

import base.Game;
import entities.AlienEntity;

public class GaucheDroiteMove implements StrategieMove {

	@Override
	public void move(long delta, AlienEntity ae,Game game) {
		// if we have reached the left hand side of the screen and
		// are moving left then request a logic update 
		if ((ae.getDx() < 0) && (ae.getX() < 10)) 
			game.updateLogic();
		
		// and vice vesa, if we have reached the right hand side of 
		// the screen and are moving right, request a logic update
		if ((ae.getDx() > 0) && (ae.getX() > 750)) 
			game.updateLogic();
		// proceed with normal move
		ae.updatePosition((ae.getDx()*delta)/1000,(ae.getDy()*delta)/1000);

	}

}
