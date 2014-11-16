package Strategie;

import niveau.Niveau;
import entities.AlienEntity;

public class DescenteDoLogic implements StrategieDoLogic {

	@Override
	public void doLogic(Niveau game, AlienEntity ae) {
		ae.setY(ae.getY()+5);
		if (ae.getY() > 840) 
			game.notifyDeath();
	}

}
