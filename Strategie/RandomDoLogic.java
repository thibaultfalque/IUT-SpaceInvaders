package Strategie;

import base.Game;
import entities.AlienEntity;

public class RandomDoLogic implements StrategieDoLogic {

	@Override
	public void doLogic(Game game, AlienEntity ae) {
		if(ae.getX()<10 || ae.getX()>1040)
			ae.setHorizontalMovement(-(ae.getDx()));
		else{ if(ae.getY()<10 || ae.getY()<600)
				ae.setVerticalMovement(-(ae.getDy()));
			  else{
				  ae.setHorizontalMovement(-(ae.getDx()));
				  ae.setVerticalMovement(-(ae.getDy()));
			}
		}
	}

}
