package Strategie;

import entities.AlienEntity;
import base.Game;

public class DiagonaleDoLogic implements StrategieDoLogic{
	public DiagonaleDoLogic(){
		
	}

	@Override
	public void doLogic(Game game, AlienEntity ae) {
		ae.setHorizontalMovement(-(ae.getDx()));
		ae.setVerticalMovement(-(ae.getDy()));
	}
	
}
