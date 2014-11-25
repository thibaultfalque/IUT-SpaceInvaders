package Strategie;

import niveau.Niveau;
import entities.AlienEntity;

public class RandomDoLogic implements StrategieDoLogic {

	@Override
	public void doLogic(Niveau game, AlienEntity ae) {

		if(ae.getX()<10 || ae.getX()>1040)
			ae.setHorizontalMovement(-(ae.getHorizontalMovement()));

		if(ae.getY()<10 || ae.getY()>600)
			ae.setVerticalMovement(-(ae.getVerticalMovement()));
		
	}

}
