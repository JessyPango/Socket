package test1;

import java.io.IOException;
import java.util.Scanner;

public class Controleur {

	public static void main(String[] args) throws IOException {

		ServeurMulti serveur ;
		
		serveur = new ServeurMulti();
		serveur.start();
		
		System.out.println("Le Serveur a demarer....");
		System.out.println("Entrez <<Entrer>> Pour l'arrêter !");
		
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		sc.close();
		
		serveur.interrupt(); // interrupted renvoi false
		System.out.println("\n***Alert :Le Serveur s'arrête quend tous les client se déconnecterons ***");
	}

}
