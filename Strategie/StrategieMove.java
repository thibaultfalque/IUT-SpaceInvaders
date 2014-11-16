package Strategie;

import niveau.Niveau;
import entities.AlienEntity;

public interface StrategieMove {
	public void move(long delta,AlienEntity ae,Niveau niveau);
}
