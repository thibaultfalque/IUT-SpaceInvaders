package niveau;

import entities.AlienEntity;
import base.Constante;
import base.Game;
import Strategie.GaucheDroiteDoLogic;
import Strategie.GaucheDroiteMove;
import Strategie.StrategieDoLogic;
import Strategie.StrategieMove;

public class FormationRectangle extends UsineAlien{
	private final static int LIGNE=3;
	private final static int COLONNE=10;
	public FormationRectangle(Game game,StrategieMove m,StrategieDoLogic dl){
		super(m,dl);
		createArrayAlien(game);
	}

	@Override
	public void createArrayAlien(Game game) {
		alienCount = 0;
		for (int row=0;row<LIGNE;row++) {
			for (int x=0;x<COLONNE;x++) {
				AlienEntity a = new AlienEntity(game,"sprites/alien.png",(Constante.WIDTH-COLONNE*64)/2+x*64+x*10,100+row*64+row*10,new GaucheDroiteMove(),new GaucheDroiteDoLogic());
				alien.add(a);
				alienCount++;
			}
		}
	}
}
