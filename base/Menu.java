package base;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Menu extends JPanel implements ActionListener{
		private JFrame fenetre;
		private JButton jouer;
		public Menu(JFrame j){
			fenetre=j;
		
			this.setBackground(Color.black);
			jouer=new JButton();
			
			ImageIcon im=new ImageIcon("sprites/bouton_jouer.png");
			
			jouer.setIcon(im);
			jouer.setSize(new Dimension(300,100));
			jouer.addActionListener(this);
			this.add(jouer);
			fenetre.getContentPane().add(this);
			
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source=e.getSource();
			if(source==jouer){
				//fenetre.getContentPane().setVisible(false);
				fenetre.setContentPane(new JPanel());
				Game g=new Game(fenetre);
				System.out.println("test");
				g.gameLoop();
			}
			
		}
}
