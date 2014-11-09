package niveau;

import base.Game;
import entities.AlienEntity;
import Strategie.StrategieDoLogic;
import Strategie.StrategieMove;

public class FormationCoeur extends UsineAlien{

	public FormationCoeur(Game game,String path,StrategieMove move,StrategieDoLogic dl) {
		super(path,move,dl);
		createArrayAlien(game);
	}

	@Override
	public void createArrayAlien(Game game) {
		alienCount=0;
		for(int i=0;i<tabFormation.length;i++){
			for(int j=0;j<tabFormation[i].length();j++){
				if(tabFormation[i].charAt(j)=='1'){
					AlienEntity a=new AlienEntity(game, "sprites/alien.png", j*64, i*64, move, doLogic);
					a.setHorizontalMovement(0);
					alien.add(a);
					alienCount++;
				}
			}
		}

	}

}
