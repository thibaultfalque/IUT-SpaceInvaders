package Strategie;

import entities.ShotEntity;

public class TirVertical implements StrategieTir {

	@Override
	public void move(ShotEntity m,long delta) {
		m.updatePosition((delta * m.getHorizontalMovement()) / 1000,(delta * m.getVerticalMovement()) / 1000);
	}

}
