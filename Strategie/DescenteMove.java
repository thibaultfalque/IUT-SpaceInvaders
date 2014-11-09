package Strategie;

import base.Game;
import entities.AlienEntity;

public class DescenteMove implements StrategieMove {
	private int lastTime;
	@Override
	public void move(long delta, AlienEntity ae, Game game) {
		lastTime+=delta;
		if(lastTime>2000){
			game.updateLogic();
			lastTime=0;
		}
	}

}
