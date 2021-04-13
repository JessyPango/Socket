package clientTest;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	

	private static Scanner sc=new Scanner(System.in);
	
	public static void main(String[] args) throws ClassNotFoundException{
		
		Socket socketClient;
		ObjectOutputStream sortie;
		ObjectInputStream entree;
		
		try {
			
			socketClient = new Socket("localhost", 8189);
			
			try {
				String ligne;
				String nom;
				
				sortie = new ObjectOutputStream(socketClient.getOutputStream());
				entree = new ObjectInputStream(socketClient.getInputStream());
				
				ligne = (String) entree.readObject();
				System.out.println(ligne);
				
				nom = sc.nextLine();
				sortie.writeObject(nom);
				
				ligne = (String) entree.readObject();
				System.out.println(ligne);
				
				echange(entree, sortie);
			} finally {
				socketClient.close();
			}
		} catch (ConnectException e) {
			System.out.println("Le Serveur est indisponible! " + e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		sc.close();
	}

	private static void echange(ObjectInputStream entree, ObjectOutputStream sortie) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		
		String message="", reponce="";
		
		while(! reponce.equals("close"))
		{
			message = sc.nextLine();
			sortie.writeObject(message);
			if(message.equals("close"))
				reponce = "close";
		}
		System.out.println("******Alert : Communication interrompue! ****");
	}

}