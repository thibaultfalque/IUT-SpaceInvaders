package Strategie;

import java.util.Random;

import base.Game;
import entities.AlienEntity;

public class RandomMove implements StrategieMove {
	private long tmp=500;
	public RandomMove(){
		
	}

	@Override
	public void move(long delta, AlienEntity ae,Game game) {
		Random rd=new Random();
		tmp+=delta;
		int valx=rd.nextInt(2)-1;
		int valy=rd.nextInt(2)-1;
		if(valx==0 )
			valx=1;
		if(valy==0)
			valy=-1;
		if ((ae.getDx() < 0) && (ae.getX() < 10)){
			game.updateLogic();
			tmp=0;
		}
		if ((ae.getDx() > 0) && (ae.getX() > 1040)){
			game.updateLogic();
			tmp=0;
		}
		if((ae.getDy()<0)&&(ae.getY()<10)){
			game.updateLogic();
			tmp=0;
		}
		if((ae.getDy()>0)&&(ae.getY()>600)){
			game.updateLogic();
			tmp=0;
		}
		if(tmp>(1500+Math.random()*(2000-1500))){
			tmp=0;
			ae.setHorizontalMovement(ae.getDx()*valx);
			ae.setVerticalMovement(ae.getDy()*valy);
		}
		ae.updatePosition((ae.getDx()*delta)/1000,(ae.getDy()*delta)/1000);
		
	}
}
