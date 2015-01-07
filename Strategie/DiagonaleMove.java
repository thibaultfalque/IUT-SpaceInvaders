package Strategie;

import base.Constante;
import niveau.Niveau;
import entities.AlienEntity;

public class DiagonaleMove implements StrategieMove {
	@Override
	public void move(long delta, AlienEntity ae,Niveau game) {
		ae.updatePosition((ae.getHorizontalMovement()*delta)/1000,(ae.getVerticalMovement()*delta)/1000);
		if(ae.getY()<20)
			game.updateLogic();
	}

	@Override
	public void init(AlienEntity ae) {
		if(ae.getX()<Constante.WIDTH/2){
			ae.setHorizontalMovement(ae.getMoveSpeed());
			ae.setVerticalMovement(ae.getMoveSpeed());
		}
		else{
			ae.setHorizontalMovement(-ae.getMoveSpeed());
			ae.setVerticalMovement(ae.getMoveSpeed());
		}
	}
	
	

}
