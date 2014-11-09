package Strategie;

import base.Game;
import entities.AlienEntity;

public class DescenteDoLogic implements StrategieDoLogic {

	@Override
	public void doLogic(Game game, AlienEntity ae) {
		ae.setY(ae.getY()+5);
		if (ae.getY() > 840) 
			game.notifyDeath();
	}

}
