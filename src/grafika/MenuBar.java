package grafika;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Formatter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.AncestorListener;

import logika.PoljanaZaIgru;

public class MenuBar extends JMenuBar {
	Scanner scan;
	JMenu File = new JMenu("File");
	JMenuItem About = new JMenuItem("About");
	JMenuItem NewGame = new JMenuItem("New Game");
	JMenuItem SaveGame = new JMenuItem("Save Game");
	JMenuItem LoadGame = new JMenuItem("Load Game");
	JMenuItem Exit = new JMenuItem("Exit");
	JMenuItem HighScore = new JMenuItem("High Score");
	JFrame frame = new JFrame();
	Integer n;
	JLabel score = new JLabel();
	JLabel slika = new JLabel();
	ImageIcon image = new ImageIcon();
	Poljana2048 poljana;

	/** Konstruktor, referencom povezuje labelu i okvir sa originalnim u frejmom
	* kojeg ovdje prepravljamo, u konstruktoru se postavlja actionlistner na
	* svaki meniItem
	*/ 
	public MenuBar(JFrame okvir, JLabel skor, JFrame about,
			final Poljana2048 poljanaPom, final PoljanaZaIgru pziPoljana) {

		poljana = poljanaPom;
		score = skor;
		frame = okvir;

		// Action listner na meniItem exit, klikom na exit u file-u gasi igricu
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		/** Dodavanje actionlistnera na menuItem About, u njemu stavlja sliku na
		 labelu koju stavlja u frejm umjesto panela sa poljanom
		 */
		About.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				image = new ImageIcon("Slike\\about.png");
				slika.setIcon(image);
				slika.setVisible(true);
				
				frame.setVisible(false);
				
				final JFrame pomOkvir = new JFrame();
				pomOkvir.addWindowListener(new WindowListener() {
					
					public void windowOpened(WindowEvent arg0) {	
					}
					
					public void windowIconified(WindowEvent arg0) {
					}
					
					
					public void windowDeiconified(WindowEvent arg0) {	
					}
					
					public void windowDeactivated(WindowEvent arg0) {					
					}

					public void windowClosing(WindowEvent arg0) {
						frame.setVisible(true);
						pomOkvir.dispose();			
					}
					
					public void windowClosed(WindowEvent arg0) {
					}
					
					public void windowActivated(WindowEvent arg0) {
					}
				});
				
				pomOkvir.setSize(500, 500);
				pomOkvir.setLocationRelativeTo(null);
				pomOkvir.setResizable(false);
				pomOkvir.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				JPanel pomPanel = new JPanel();
				pomPanel.add(slika);
				pomOkvir.getContentPane().add(pomPanel);
				pomOkvir.setVisible(true);
		
			}
		});

		/** Dodavanje actionlistnera na JMenuItem SaveGame game, trenutno stanje
		 poljane sacuvamo u SaveGame.txt, a u prvom redu sacuvamo skor **/
		SaveGame.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Formatter x = null;
				try {
					x = new Formatter("TextFiles\\SaveGame.txt");
				} catch (Exception ex) {
					System.out.print("You have an error");
				}
				x.format("%d%s", poljana.returnScore(), "\n");
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						x.format("%d ", poljana.poljana[i][j]);
					}
					x.format("%s", "\n");
				}
				x.close();
			}
		});
		/** Dodajemo actionlistner na NewGame i time poljanu resetujemo na
		 pocetnu vrijednost **/
		NewGame.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				frame.setVisible(true);
				slika.setVisible(false); // Labela sa about slikom ide ponovo na false
				
				System.out.println(poljanaPom.isFocusOwner());
				pziPoljana.setPziNula(); // Resetujemo poljanu na pocetnu
												// vrijednost
				pziPoljana.setScore(0); // Resetujemo skor na 0
				score.setText("Score: " + pziPoljana.getScore());
				poljana.updatePoljana();

			}
		});

		/** Dodajemo actionlistner na LoadGame i iz fajla SaveGame.txt
		* prepisujemo stanje u poljanu, i sacuvani skor prepravlja umjesto
		* trenutnog skora **/
		LoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				poljana.setVisible(true);
				slika.setVisible(false);
				try {
					scan = new Scanner(new File("TextFiles\\SaveGame.txt"));

				} catch (Exception ex) {
					System.out.print("Error");
				}

				pziPoljana.setScore(scan.nextInt());
				score.setText("Score: " + pziPoljana.getScore());
				for (int i = 0; i < 4; i++)
					for (int j = 0; j < 4; j++) {
						poljana.poljana[i][j] = scan.nextInt();
					}
				poljana.updatePoljana();
			}
		});

		/** Postavljamo actionlistner na highscore, klikom na njega izbacuje nam
		* messagedialog u kojem pise highscore, highscore je moguc samo u
		* narednom igranju, jer ako se u highscore.txt nalazi 0 kao pocetna
		* vrijednost, onda actionlistner ne radi nista  */
		HighScore.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {
					FileReader fileReader = new FileReader(new File(
							"TextFiles\\HighScore.txt"));
					BufferedReader br = new BufferedReader(fileReader);
					String pom = br.readLine();
					br.close();
					fileReader.close();
					n = Integer.valueOf(pom);
				} catch (Exception ex) {
					System.out.println("Error");
				}
				if (n > 0)
					JOptionPane.showMessageDialog(frame, new JLabel(
							"High Score: " + n, JLabel.CENTER), null,
							JOptionPane.PLAIN_MESSAGE);
			}
		});

		//Dodajemo sve MenuIteme i menie u file i na kraju u JMenuBar
		File.add(NewGame);
		File.add(SaveGame);
		File.add(LoadGame);
		File.add(HighScore);
		File.add(Exit);
		this.add(File);
		this.add(About);
	}

}
