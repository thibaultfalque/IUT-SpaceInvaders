package niveau;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Strategie.StrategieDoLogic;
import Strategie.StrategieMove;
import base.Game;
import entities.AlienEntity;


public  abstract class UsineAlien {
	protected StrategieMove move;
	protected StrategieDoLogic doLogic;
	protected String[] tabFormation;
	protected final static int TAILLE=9;
	protected ArrayList<AlienEntity> alien;
	protected int alienCount =0;
	public UsineAlien(String path,StrategieMove move,StrategieDoLogic dl){
		tabFormation=new String[TAILLE];
		alien=new ArrayList<AlienEntity>();
		try {
			BufferedReader br=ouvrirFichier(path);
			lireFichier(br);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.move=move;
		this.doLogic=dl;
	}
	public UsineAlien(StrategieMove move,StrategieDoLogic dl){
		alien=new ArrayList<AlienEntity>();
		this.move=move;
		this.doLogic=dl;
	}
	private BufferedReader ouvrirFichier(String fichier) throws Exception{
		try{
			InputStream ips=new FileInputStream(fichier);
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			return br;
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
		return null;
	}
	private void lireFichier(BufferedReader br) throws IOException{
		String ligne;
		int i=0;
		while((ligne=br.readLine())!=null && i<TAILLE){
			tabFormation[i]=ligne;
			i++;
		}
		br.close();
	}
	public ArrayList<AlienEntity> getAlien() {
		return alien;
	}
	public int getAlienCount() {
		return alienCount;
	}
	public abstract void createArrayAlien(Game game);
	

}
