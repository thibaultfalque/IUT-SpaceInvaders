package base;

import javax.swing.JFrame;

public class Fenetre extends JFrame{
	protected Menu menu;
	public Fenetre(){
		this.setSize(Constante.WIDTH,Constante.HEIGHT);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("SPACE INVADERS");
		menu=new Menu(this);
	}
}
