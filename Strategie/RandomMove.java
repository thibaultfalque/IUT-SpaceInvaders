package Strategie;

import java.util.Random;

import niveau.Niveau;
import entities.AlienEntity;

public class RandomMove implements StrategieMove {
	private long tmp=500;
	public RandomMove(){
		
	}

	@Override
	public void move(long delta, AlienEntity ae,Niveau game) {
		Random rd=new Random();
		tmp+=delta;
		int valx=rd.nextInt(2)-1;
		int valy=rd.nextInt(2)-1;
		if(valx==0 )
			valx=1;
		if(valy==0) 
			valy=-1;
		if ((ae.getHorizontalMovement() < 0) && (ae.getX() < 10)){
			game.updateLogic();
			tmp=0;
		}
		if ((ae.getHorizontalMovement() > 0) && (ae.getX() > 1040)){
			game.updateLogic();
			tmp=0;
		}
		if((ae.getVerticalMovement()<0)&&(ae.getY()<10)){
			game.updateLogic();
			tmp=0;
		}
		if((ae.getHorizontalMovement()>0)&&(ae.getY()>600)){
			game.updateLogic();
			tmp=0;
		}
		if(tmp>(1500+Math.random()*(2000-1500))){
			tmp=0;
			ae.setHorizontalMovement(ae.getHorizontalMovement()*valx);
			ae.setVerticalMovement(ae.getVerticalMovement()*valy);
		}
		ae.updatePosition((ae.getHorizontalMovement()*delta)/1000,(ae.getVerticalMovement()*delta)/1000);
		
	}
}
