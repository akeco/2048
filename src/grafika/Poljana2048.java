package grafika;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Formatter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import logika.PoljanaZaIgru;

public class Poljana2048 extends JPanel {

	private Formatter x;
	public PoljanaZaIgru pzi;
	private ImageIcon image;
	private JLabel[][] labela;
	private JLabel score;
	private JFrame frame;
	private MojListner ml = new MojListner();
	String[] pozadine = { "blur.png", "artwork.png", "sunset.png", "nature.png" };
	private BufferedImage slika;
	private int n = 0;
	private int broj = 0;
	int[][] poljana;
	
	/** Konstruktor
	*/
	public Poljana2048(PoljanaZaIgru pzi, JLabel skor, JFrame okvir) {

		/** Uzimamo skor iz highscore.txt i stavljamo ga u n, koji na kraju
		* koristimo da poredimo skor koji smo postigli sa ovim skorom koji se
		* nalazi u n, ukoliko je uslov poredjenja ispunjen treba da napravi
		* promjenu koju radimo kasnije **/
		try {
			FileReader fileReader = new FileReader(new File("TextFiles\\HighScore.txt"));
			BufferedReader br = new BufferedReader(fileReader);
			String pom = br.readLine();
			br.close();
			fileReader.close();
			n = Integer.valueOf(pom);
		} catch (Exception e) {
			System.out.println("Error");
		}

		score= new JLabel();
		this.pzi = pzi;
		frame = okvir;
		score = skor;
		poljana = this.pzi.getPoljana();
		labela = new JLabel[poljana.length][poljana.length];
		this.setLayout(new GridLayout(poljana.length, poljana[0].length));
		this.setBackground(Color.black);
		Random rnd = new Random();

		/** Za svaku labelu u nizu dodajemo osobine, odredjenu sliku,
		* keylistner, ukoliko je 0 u nizu u labeli za sliku stavlja null, u
		* suprotnom stavlja odredjenu sliku za taj broj **/
		for (int i = poljana.length - 1; i >= 0; i--) {
			for (int j = 0; j < poljana[i].length; j++) {
				labela[i][j] = new JLabel();
				if (poljana[i][j] == 0) {
					labela[i][j].setHorizontalAlignment(SwingConstants.CENTER);
					labela[i][j].setIcon(null);
					labela[i][j].revalidate();
					labela[i][j].addKeyListener(ml);
					labela[i][j].setFocusable(true);
					labela[i][j].requestFocus();
					labela[i][j].requestFocusInWindow();
					this.add(labela[i][j], i, j);
				}

				
			  else {
					labela[i][j].setHorizontalAlignment(SwingConstants.CENTER);
					image = new ImageIcon("Slike\\"+ poljana[i][j] + ".png");
					labela[i][j].setIcon(image);
					labela[i][j].addKeyListener(ml);
					labela[i][j].setFocusable(true);
					labela[i][j].requestFocus();
					labela[i][j].requestFocusInWindow();
					this.add(labela[i][j], i, j);
				}    
			}
		}
			

		/** Bira radnom pozadinu koju dalje stavlja u paint component
		 * 
		 */
		 
		try {
			int n = rnd.nextInt(pozadine.length);
			slika = ImageIO.read(new File("Slike\\"+pozadine[n]));
		} catch (IOException ex) {
			System.out.println("Error");
		}
	}
	
	/** geter od listenera
	 * 
	 * @return
	 */
	 
	public MojListner getMl() {
		return ml;
	}

	/** geter od poljane
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public int getPoljana(int i, int j) {
		return poljana[i][j];
	}

	/** metod za otvaranje score.txt fajla s kojim u narednim metodama radimo
	 * 
	 */
	private void openFile() {
		try {
			x = new Formatter("TextFiles\\Score.txt");
		} catch (Exception e) {
			System.out.print("You have an error");
		}
	}

	/** snima skor u promjenljivu x koja predstavlja fajl score.txt
	 * 
	 */
	private void addRecords() {
		x.format("%d", pzi.getScore());
	}

	/** zatvara fajl
	 * 
	 */
	private void closeFile() {
		x.close();
	}

	/** poredimo trenutni skor sa najvecim skorom snimljenim u highscore.txt, a
	* to taj broj smo stavili u n, ukoliko je trenutni skor veci od highscore,
	* treba u highscore snimiti novi 
	*/
	private void uporedi() {

		// ispitivanje fajla sa skorom
		try {
			FileReader fileReader = new FileReader(new File("TextFiles\\Score.txt"));
			BufferedReader br = new BufferedReader(fileReader);
			String pom = br.readLine();
			br.close();
			fileReader.close();
			broj = Integer.valueOf(pom);
			System.out.println(n);
		} catch (Exception e) {
			System.out.println("Error");
		}
		/** Ako je trenutni skor veci od highscore, trenutni skor snimi u fajl
		* highscore **/
		if (broj > n) {

			try {
				x = new Formatter("TextFiles\\HighScore.txt");
			} catch (Exception e) {
				System.out.print("You have an error");
			}
			x.format("%d", broj);
			x.close();
		}

	}

	/** Refreshuje stanje poljane, te ponovo za svaki broj stavlja odredjenu
	* sliku **/
	public  void updatePoljana() {
		int[][] poljana = pzi.getPoljana();
		for (int i = poljana.length - 1; i >= 0; i--) {
			for (int j = 0; j < poljana[i].length; j++) {

				if (poljana[i][j] == 0) {
					labela[i][j].setIcon(null);
					labela[i][j].revalidate();
				}

				else {
					image = new ImageIcon("Slike\\"+poljana[i][j] + ".png");
					labela[i][j].setIcon(image);
				}
			}
		}
	}
	
	
	/** metod koji nam vraca stalno stanje skora
	 * 
	 * @return
	 */
	public int returnScore() {
		return pzi.getScore();

	}

	/** Keylistner, za odredjeni potez poziva tu metodu za pomjeranje
	 * 
	 * @author Amer
	 *
	 */
	
	class MojListner implements KeyListener {

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 39) {
				pzi.odigrajPotez(PoljanaZaIgru.DESNO);
			}
			if (e.getKeyCode() == 37) {
				pzi.odigrajPotez(PoljanaZaIgru.LIJEVO);
			}
			if (e.getKeyCode() == 38) {
				pzi.odigrajPotez(PoljanaZaIgru.GORE);
			}
			if (e.getKeyCode() == 40) {
				pzi.odigrajPotez(PoljanaZaIgru.DOLE);
			}
			
			updatePoljana();
			score.setText("Score: " + returnScore());

			/** Ispitujemo da li je neko polje 2048, ako jeste, ispisi alert
			* poruku  **/
			if(pzi.pobjeda()==true){
						JOptionPane.showMessageDialog(frame, new JLabel(
								"Congratulation, you won!", JLabel.CENTER),
								null, JOptionPane.PLAIN_MESSAGE);
			}
			

			/** Ako je kraj igre, treba skor da snimi u score.txt, prije toga
			* izbacuje message dialog sa porukom za kraj **/
			if (!pzi.kraj()) {
				JOptionPane.showMessageDialog(frame, new JLabel(
						"Gamer over, your score is " + pzi.getScore(),
						JLabel.CENTER), null, JOptionPane.PLAIN_MESSAGE);
				openFile();
				addRecords();
				closeFile();
				uporedi();
			}
		}

		public void keyReleased(KeyEvent e) {

		}

		public void keyTyped(KeyEvent e) {

		}
	}

	/** Metod koji stavlja pozadinu na JPanel
	 * 
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(slika, 0, 0, null); 
										

	}

}
