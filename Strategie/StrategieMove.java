package Strategie;

import base.Game;
import entities.AlienEntity;

public interface StrategieMove {
	public void move(long delta,AlienEntity ae,Game game);
}
