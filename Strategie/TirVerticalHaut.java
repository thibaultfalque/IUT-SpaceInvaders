package Strategie;

import entities.ShotEntity;

public class TirVerticalHaut implements StrategieTir {

	@Override
	public void move(ShotEntity m,long delta) {
		m.updatePosition((delta * m.getHorizontalMovement()) / 1000,(delta * m.getVerticalMovement()) / 1000);
	}

	@Override
	public void init(ShotEntity m) {
		m.setVerticalMovement(-m.getMoveSpeed());
	}
}
