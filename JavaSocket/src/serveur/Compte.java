package serveur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Compte {
	
	private String nom; // Nom de la personne connecté
	private Socket socket; // Socket avec lequelle le client est connecté au serveur
	private ObjectInputStream in ;
	private ObjectOutputStream out;
	private ThreadClient thread; // Thread avec lequelle le client communique
	
	public Compte(Socket socket, ObjectInputStream in, ObjectOutputStream out) throws IOException {
		super();
		this.socket = socket;
		this.in =  new ObjectInputStream(socket.getInputStream());
		this.out =  new ObjectOutputStream(socket.getOutputStream());
	}
	public Compte(String nom, Socket socket, ThreadClient thread) {
		super();
		this.nom = nom;
		this.socket = socket;
		this.thread = thread;
	}

	public Compte(Socket socketServeur, ThreadClient threadClient) throws IOException {
		this.socket = socketServeur;
		this.thread = threadClient;
		this.in =  new ObjectInputStream(socketServeur.getInputStream());
		this.out =  new ObjectOutputStream(socketServeur.getOutputStream());
	}
	
	public ObjectInputStream getIn() {
		return in;
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public ThreadClient getThread() {
		return thread;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket; // Le serveur modifie le nom si la personne c'est déjà connecté avant
	}
	public String getNom() {
		return (String) nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
}
