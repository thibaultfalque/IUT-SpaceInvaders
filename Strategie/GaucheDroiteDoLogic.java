package Strategie;

import niveau.Niveau;
import entities.AlienEntity;

public class GaucheDroiteDoLogic implements StrategieDoLogic {

	@Override
	public void doLogic(Niveau game, AlienEntity ae) {
		// swap over horizontal movement and move down the
		// screen a bit
		ae.setHorizontalMovement(-(ae.getHorizontalMovement()));
		ae.setY(ae.getY()+10);
				
		// if we've reached the bottom of the screen then the player
		// dies
		if (ae.getY() > 650) 
			game.notifyDeath();
				
		
	}

}
