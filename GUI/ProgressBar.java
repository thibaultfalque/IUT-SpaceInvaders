package GUI;

import java.awt.Color;
import java.awt.Graphics;

public class ProgressBar {
	private int x,y;
	private float step;
	private static float widthIitiale=64;
	private float width=widthIitiale;
	public ProgressBar(int x,int y){
		this.x=x;
		this.y=y;
		
	}
	public void setStep(int s){
		step=widthIitiale/s;
	}
	public void setValue(int vie){
		width=vie*step;
	}
	
	public void move(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.GRAY);
		g.fillRect(x, y, (int) widthIitiale,5);
		g.setColor(Color.GREEN);
		g.fillRect(x, y, (int) width,5);
		
	
	}
}
