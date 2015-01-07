package Strategie;

import entities.ShotEntity;

public interface StrategieTir {
	public void init(ShotEntity m);
	public void move(ShotEntity m,long delta);
}
