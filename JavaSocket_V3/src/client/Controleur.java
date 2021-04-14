package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import message.Message;

public class Controleur extends Thread{
	
	private String nom; // Nom du client 
	private int id; // identiffiant du client
	private Message message; // Message en cours d'envoie par le client  actuel
	private ArrayList<Message> smsEnAttenteEnvoie = new ArrayList<Message>(); // Message en attente d'envoie
	private Message smsReceve; // Message reçue
	private ArrayList<Message> smsEnCoursTelechargement = new ArrayList<Message>(); // Message en cours de telechargement
	private ArrayList<Personne> personConnectList = new ArrayList<Personne>(); // Liste des personnes connecté
	
	public static void main(String[] args) throws IOException {
		
		Socket socket = new Socket("localhost", 1234); // socket de communication
		InputStream entree =  socket.getInputStream(); // Objet d'ecoute
		OutputStream sortie = socket.getOutputStream(); // Objet d'envoie
		
		ThreadLecture threadLecture = new ThreadLecture(sortie);
		threadLecture.start();
		
		ThreadAffiche threadAffiche = new ThreadAffiche(entree);
		threadAffiche.start();
		

	}

}
