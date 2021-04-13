package test1;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadClient  extends Thread {
	public static int nombreClient=0;
	private int id;
	private Socket socketServeur;
	
	public ThreadClient(Socket socketServeur, int id) 
	{
		this.socketServeur = socketServeur;
		this.id = id;
	}
	
	public void run()
	{
		ObjectInputStream entree;
		ObjectOutputStream sortie;
		String nom = "";
		
		try 
		{
			entree = new ObjectInputStream(socketServeur.getInputStream());
			sortie = new ObjectOutputStream(socketServeur.getOutputStream());
			
			sortie.writeObject("Votre nom : ");
			nom = (String) entree.readObject();
			sortie.writeObject("Bienvenue " + nom + " !");
			System.out.println(" \n **** Alert : " + nom + " connecté Id: " + id +"******\n");
			
			echange(entree, sortie, nom);
		
		} catch (EOFException e) {
			
			System.out.println(" \n****Alert :  " + nom +" c'est déconnecté.*******\n");
			
		}
		catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
			try {
				socketServeur.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void echange( ObjectInputStream entree, ObjectOutputStream sortie, String nom) throws IOException, ClassNotFoundException
	{
		String message;
		
		
		
		while(! interrupted()) 
		{
			message = (String) entree.readObject();
			if(! message.equals("close"))
				System.out.println(nom + " : " + message);
		}
		
		sortie.writeObject("close");
		socketServeur.close();
	}
}