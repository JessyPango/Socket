package serveur;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Controleur {

	public static void main(String[] args) throws IOException {

		ServeurMulti serveur ;
		
		serveur = new ServeurMulti();
		serveur.start();
		
		System.out.println("Le Serveur a demarer.... ");
		System.out.println("Entrez <<Entrer>> Pour l'arrêter !");
		
		Scanner sc = new Scanner(System.in);
		
		try {
			
			sc.nextLine();
			
		} catch (NoSuchElementException e) {
			
		} finally {
			
			sc.close();
			serveur.interrupt(); // interrupted renvoi false
			System.out.println("\n***Alert :Le Serveur s'arrête quand tous les clients se déconnecterons ***");
		}
	}

}
