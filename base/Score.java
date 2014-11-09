package base;

import java.awt.Color;
import java.awt.Graphics;

public class Score {
	private int score;
	private String stri;
	int width,height;
	public Score(int w,int h){
		score=0;
		width=w;
		height=h;
		stri="Score: "+score;
	}
	public void augmenterScore(int x){
		score+=x;
		stri="Score: "+score;
	}
	public void draw(Graphics g){
		g.setColor(Color.white);
		g.drawString(stri,width-g.getFontMetrics().stringWidth(stri)-10,20);
	}

	public int getScore() {
		// TODO Auto-generated method stub
		return score;
	}
	
}
