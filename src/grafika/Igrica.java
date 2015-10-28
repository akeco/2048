package grafika;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import logika.PoljanaZaIgru;
public class Igrica extends JFrame {
//Klasa nasljeðuje frejm u koji stavljamo sve panele
	
	private static JLabel score;
	private static JFrame about; 
	
	public static void main(String[] args) {

		score = new JLabel();
		about= new JFrame("2048");
		about.setVisible(false);
		JFrame okvir= new JFrame("2048");
		okvir.setLayout(new BorderLayout());
		
		okvir.setSize(500, 500);
		okvir.setLocationRelativeTo(null);
		okvir.setResizable(false);
		okvir.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//Panel u kojem se nalazi meni sa opcijama i labela sa skorom
		JPanel menuScore = new JPanel(); 
		menuScore.setLayout(new BorderLayout());
	
		//Labela u kojoj se poslije svakog poteza refeshuje skor
		
		PoljanaZaIgru pzi = new PoljanaZaIgru(4, 4);
		Poljana2048 poljana = new Poljana2048(pzi, score, okvir);
		
		score.setText("Score: " + poljana.returnScore());
		score.setHorizontalAlignment(SwingConstants.CENTER);
		
		MenuBar meni = new MenuBar(okvir, score, about, poljana, pzi);
		menuScore.add(meni, BorderLayout.WEST);
		menuScore.add(score, BorderLayout.EAST);
		
		okvir.getContentPane().add(menuScore, BorderLayout.NORTH);
	    okvir.getContentPane().add(poljana, BorderLayout.CENTER);
	    okvir.setVisible(true);
	   
	}	
	
}
