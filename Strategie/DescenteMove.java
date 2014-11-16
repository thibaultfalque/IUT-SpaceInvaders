package Strategie;

import niveau.Niveau;
import entities.AlienEntity;

public class DescenteMove implements StrategieMove {
	private int lastTime;
	@Override
	public void move(long delta, AlienEntity ae, Niveau game) {
		lastTime+=delta;
		if(lastTime>2000){
			game.updateLogic();
			lastTime=0;
		}
	}

}
