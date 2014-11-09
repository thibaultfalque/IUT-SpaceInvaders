package Strategie;

import base.Game;
import entities.AlienEntity;

public class GaucheDroiteDoLogic implements StrategieDoLogic {

	@Override
	public void doLogic(Game game, AlienEntity ae) {
		// swap over horizontal movement and move down the
		// screen a bit
		ae.setHorizontalMovement(-(ae.getDx()));
		ae.setY(ae.getY()+10);
				
		// if we've reached the bottom of the screen then the player
		// dies
		if (ae.getY() > 840) 
			game.notifyDeath();
				
		
	}

}
