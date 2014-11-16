package Strategie;

import niveau.Niveau;
import entities.AlienEntity;

public class DiagonaleDoLogic implements StrategieDoLogic{
	public DiagonaleDoLogic(){
		
	}

	@Override
	public void doLogic(Niveau game, AlienEntity ae) {
		ae.setHorizontalMovement(-(ae.getHorizontalMovement()));
		ae.setVerticalMovement(-(ae.getHorizontalMovement()));
	}
	
}
