package konzola;

import java.io.DataInputStream;
import java.io.IOException;

import logika.PoljanaZaIgru;

public class Igrica {
	
	static PoljanaZaIgru pzi;
	
	public static void main(String[] args) throws CloneNotSupportedException, IOException {
		Igrica igrica = new Igrica();
		igrica.pzi = new PoljanaZaIgru(4, 4);
		
	    //Trazi zahtjev za potezom sve dok je to moguce
		do {
			prikaziStanjePoljane(igrica.pzi.getPoljana());
			
			DataInputStream in = new DataInputStream(System.in);
			byte b = in.readByte();
			char ch=(char)b;
			
				if (ch=='6') {
					igrica.pzi.odigrajPotez(PoljanaZaIgru.DESNO);
				}
				if (ch=='4') {
					igrica.pzi.odigrajPotez(PoljanaZaIgru.LIJEVO);
					}
					
				if (ch=='8') {
					igrica.pzi.odigrajPotez(PoljanaZaIgru.GORE);
				}
				if (ch=='2') {
					igrica.pzi.odigrajPotez(PoljanaZaIgru.DOLE);
				
				}
				
	       	}  while(igrica.pzi.kraj()==true);
		System.out.println(igrica.pzi.kraj());
  }
	/**Metod prikazuje stanje poljane
	 * 
	 * @param poljana
	 */
	private static void prikaziStanjePoljane(int[][] poljana) {
		for (int i = 0; i < poljana.length; i++) {
			for (int j = 0; j < poljana[i].length; j++) {
				if (poljana[i][j] == 0) {
					System.out.print("." + "\t");
				} else {
					System.out.print(poljana[i][j] + "\t");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
}