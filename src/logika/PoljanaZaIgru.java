package logika;

/**
 * KOMPARATIVNA ANALIZA PROGRAMSKIH JEZIKA
 * Asistent: Letic Vedad
 * Student: Amer Keèo
 * Maj, 2014
 **/

import java.util.Random;


public class PoljanaZaIgru {

	public static final char LIJEVO = '4';
	public static final char DESNO = '6';
	public static final char DOLE = '2';
	public static final char GORE = '8';
	private int[][] poljana;
	private int x, y;
	private int pom;
	private boolean kraj = false;
	private int score = 0;

	Random rnd;
/** konstruktor
 * 
 * @param x
 * @param y
 */
	public PoljanaZaIgru(int x, int y) {
		poljana = new int[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				poljana[i][j] = 0;
			}
		}

		izbacivanje();
		izbacivanje();
	}
	

	/** Bira random polje na poljani koje je prazno i stavlja 2 ili 4
	 * 
	 */
	private void izbacivanje() {
		pom = -1;
		do {
			x = (int) (Math.random() * 4);
			y = (int) (Math.random() * 4);

			// System.out.println((x + 1) + " ," + (y + 1));
			if (poljana[x][y] == 0) {
				poljana[x][y] = Math.random() < 0.9 ? 2 : 4;
				pom = 1;
			}
		} while (pom != 1);
	}

	/** Geter od poljane
	 * 
	 * @return
	 */
	public int[][] getPoljana() {
		return poljana;
	}

	/** Metod za pomjeranje polja u stranu zavisno od poteza
	 * 
	 * @param potez
	 */
	private void pomjeri(int potez) {

		// Ako je potez lijevo, pomjera lijevo ako se ispred broja nalazi 0
		if (potez == LIJEVO) {

			for (int k = 1; k < 4; k++) {
				for (int j = 1; j < 4; j++) {
					for (int i = 0; i < 4; i++) {
						if (poljana[i][j] != 0)
							if (poljana[i][j - 1] == 0) {
								poljana[i][j - 1] = poljana[i][j];
								poljana[i][j] = 0;

							}
					}
				}
			}
		}

		/** Ako je potez desno, pomjera desno ako se poslije broja nalazi 0
		 * 
		 */
		if (potez == DESNO) {
			for (int k = 1; k < 4; k++) {
				for (int j = 2; j >= 0; j--) {
					for (int i = 0; i < 4; i++) {
						if (poljana[i][j] != 0)
							if (poljana[i][j + 1] == 0) {
								poljana[i][j + 1] = poljana[i][j];
								poljana[i][j] = 0;

							}

					}
				}
			}

		}

		/** Ako je potez dole, pomjera dole ako poslije broja nalazi 0
		 * 
		 */
		if (potez == DOLE) {
			for (int k = 0; k < 4; k++) {
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 4; j++) {
						if (poljana[i][j] != 0)
							if (poljana[i + 1][j] == 0) {
								poljana[i + 1][j] = poljana[i][j];
								poljana[i][j] = 0;

							}
					}
				}
			}

		}

		/** Ako je potez gore, pomjera gore ako se ispred broja nalazi 0
		 * 
		 */
		if (potez == GORE) {

			for (int k = 0; k < 4; k++) {
				for (int i = 1; i < 4; i++)
					for (int j = 0; j < 4; j++) {
						if (poljana[i][j] != 0) {
							if (poljana[i - 1][j] == 0) {
								poljana[i - 1][j] = poljana[i][j];
								poljana[i][j] = 0;
							}
						}
					}
			}

		}

	}

	/** Funkcija sabira ista medjusobna polja, zavisno od poteza u koju stranu
	 * 
	 * @param potez
	 */
	private void saberi(int potez) {
		/** Ako je potez lijevo, krece s lijeve strane i poredi broj sa brojem
		* ispred njega, ukoliko su jednaki, treba ih sabrati **/
		if (potez == LIJEVO) {

			for (int j = 1; j < 4; j++) {
				for (int i = 0; i < 4; i++) {
					if (poljana[i][j] != 0)
						if (poljana[i][j - 1] == poljana[i][j]) {
							poljana[i][j - 1] += poljana[i][j];
							score += poljana[i][j];
							/** Redom iznad score povecamo za onaj broj koji
							* saberemo
							* tj ako saberemo 2x2 tada score povecamo za 2
							* ako saberemo 2x4 score povecamo za 4 i tako dalje
							* Analogne vrijedi i za ostale pravce kada sabiramo **/
							poljana[i][j] = 0;
						}
				}
			}
		}

		/** Ako je potez desno, ispituje da li je broj jednak sa brojem poslije
		* njega, ako su jednaki treba ih sabrati **/
		if (potez == DESNO) {

			for (int j = 2; j >= 0; j--) {
				for (int i = 0; i < 4; i++) {
					if (poljana[i][j] != 0)
						if (poljana[i][j + 1] == poljana[i][j]) {
							poljana[i][j + 1] += poljana[i][j];
							score += poljana[i][j];
							// System.out.println("score>"+score);
							poljana[i][j] = 0;
						}
				}
			}
		}

		/** Ako je potez dole, poredi broj sa onim poslije njega, ukoliko su
		* jednaki treba da ih sabere **/
		if (potez == DOLE) {

			for (int i = 2; i >= 0; i--) {
				for (int j = 0; j < 4; j++) {
					if (poljana[i][j] != 0)
						if (poljana[i + 1][j] == poljana[i][j]) {
							poljana[i + 1][j] += poljana[i][j];
							score += poljana[i][j];
							// System.out.println("score>"+score);
							poljana[i][j] = 0;

						}
				}
			}
		}

		/** Ako je potez gore, poredi broj sa brojem ispred njega, ukoliko su
		* jednaki, treba ih sabrati **/
		if (potez == GORE) {

			for (int i = 1; i < 4; i++)
				for (int j = 0; j < 4; j++) {
					if (poljana[i][j] != 0) {
						if (poljana[i - 1][j] == poljana[i][j]) {
							poljana[i - 1][j] += poljana[i][j];
							score += poljana[i][j];
							// System.out.println("score>"+score);
							poljana[i][j] = 0;
						}
					}
				}
		}
		System.out.println("Vas score je: " + score + "\n");

	}

	/** Metod koji ispituje da li je potez moguc i samim tim izvrsava sve naredne
	* metode vezane za taj potez **/
	public void odigrajPotez(int potez) {

		if (potez == LIJEVO) {
			if (dozvoljeno(potez)) {
				pomjeri(LIJEVO);
				saberi(LIJEVO);
				pomjeri(LIJEVO);
				izbacivanje();
				pobjeda();
			}
		}

		if (potez == DESNO) {
			if (dozvoljeno(potez)) {
				pomjeri(DESNO);
				saberi(DESNO);
				pomjeri(DESNO);
				izbacivanje();
				pobjeda();
			}
		}

		if (potez == DOLE) {
			if (dozvoljeno(potez)) {
				pomjeri(DOLE);
				saberi(DOLE);
				pomjeri(DOLE);
				izbacivanje();
				pobjeda();
			}

		}

		if (potez == GORE) {
			if (dozvoljeno(potez)) {
				pomjeri(GORE);
				saberi(GORE);
				pomjeri(GORE);
				izbacivanje();
				pobjeda();
			}

		}

	}

	/** Geter od promjenljive skor
	 * 
	 * @return
	 */
	public int getScore() {
		return score;
	}

	/** Seter od skor, koristimo ga u MeniBaru kada idemo new game, treba
	* resetovati skor na 0
	*/
	public void setScore(int score) {
		this.score = score;
	}

	/** Postavljanje poljane na 0, koristimo je u MeniBaru kada zelimo new game
	 * 
	 */
	public void setPziNula() {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				poljana[i][j] = 0;
			}
		izbacivanje();
		izbacivanje();
	}

	/** Ispitujemo da li se na nekom polju nalazi broj 2048, ukoliko se nalazi,
	* metod treba da vrati true  **/
	public boolean pobjeda() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (poljana[i][j] == 2048) {
					return true;
				}
			}
		}
		return false;
	}

	/** Metod ispituje da li je potez moguc, tj da li je moguce pomjeriti polja
	* ili sabrati, ukoliko je moguce, onda se potez izvrsava, u suprotnom
	* preskace i ne radi nista  **/
	private boolean dozvoljeno(int potez) {

		/** Ako se izmedju brojeva nalazi prazno mjesto, da se pomjeranje izvrsi
		* ili ako se nalaze dva medjusobno jednaka broja, dopusta da se saberu,
		* isto se odnosi za svaki smjer pomjeranja **/
		if (potez == LIJEVO) {
			for (int i = 0; i < 4; i++)
				for (int j = 1; j < 4; j++) {
					if (poljana[i][j] != 0) {
						if (poljana[i][j - 1] == 0)
							return true;
						if (poljana[i][j - 1] == poljana[i][j])
							return true;
					}
					if (poljana[i][j] == 0) {
						if (j + 1 < 4 && poljana[i][j + 1] != 0)
							return true;
						if (j + 2 < 4 && poljana[i][j + 2] != 0)
							return true;
						if (j + 3 < 4 && poljana[i][j + 3] != 0)
							return true;

					}
				}
			return false;
		}

		if (potez == DESNO) {
			for (int i = 0; i < 4; i++)
				for (int j = 2; j >= 0; j--) {
					if (poljana[i][j] != 0) {
						if (poljana[i][j + 1] == 0)
							return true;
						if (poljana[i][j + 1] == poljana[i][j])
							return true;
					}
					if (poljana[i][j] == 0) {
						if (j - 1 >= 0 && poljana[i][j - 1] != 0)
							return true;
						if (j - 2 >= 0 && poljana[i][j - 2] != 0)
							return true;
						if (j - 3 >= 0 && poljana[i][j - 3] != 0)
							return true;
					}
				}
			return false;
		}

		if (potez == GORE) {
			for (int i = 1; i < 4; i++)
				for (int j = 0; j < 4; j++) {
					if (poljana[i][j] != 0) {
						if (poljana[i - 1][j] == 0)
							return true;
						if (poljana[i - 1][j] == poljana[i][j])
							return true;
					}
					if (poljana[i][j] == 0) {
						if (i + 1 < 4 && poljana[i + 1][j] != 0)
							return true;
						if (i + 2 < 4 && poljana[i + 2][j] != 0)
							return true;
						if (i + 3 < 4 && poljana[i + 3][j] != 0)
							return true;

					}
				}
			return false;
		}

		if (potez == DOLE) {
			for (int i = 2; i >= 0; i--)
				for (int j = 0; j < 4; j++) {
					if (poljana[i][j] != 0) {
						if (poljana[i + 1][j] == 0)
							return true;
						if (poljana[i + 1][j] == poljana[i][j])
							return true;
					}
					if (poljana[i][j] == 0) {
						if (i - 1 >= 0 && poljana[i - 1][j] != 0)
							return true;
						if (i - 2 >= 0 && poljana[i - 2][j] != 0)
							return true;
						if (i - 3 >= 0 && poljana[i - 3][j] != 0)
							return true;
					}
				}
			return false;
		}

		return true;
	}

	/** Metod ispituje da li je kraj igre, kraj igre je ako je svih 16 mjesta
	* poljane popunjeno brojevima i ni jedan broj se ne moze sabrati, te vraca
	* false, u suprotnom igra nije gotova i vraca true **/
	public boolean kraj() {
		int pom = 0;
		int brojac = 0;

		for (int k = 0; k < 4; k++)
			for (int z = 0; z < 4; z++)
				if (poljana[k][z] != 0)
					brojac++;

		if (brojac == 16) {
			for (int i = 0; i < 4; i++)
				for (int j = 1; j < 4; j++) {
					if (poljana[i][j - 1] == poljana[i][j]) {
						pom++;
						return true;
					}
				}

			for (int i = 2; i >= 0; i--)
				for (int j = 0; j < 4; j++) {
					
					if (poljana[i + 1][j] == poljana[i][j]) {
						pom++;
						return true;
					}
					

				}
		}

		if (brojac < 16) {
			return true;
		}

		else {
			return false;
		}
	}

}
