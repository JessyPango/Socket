package serveur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import commun.Message;

public class ServeurMulti extends Thread {
	
	public static ArrayList<Compte> comptes = new ArrayList<Compte>();
	public static Message smsNotifyForAll;
	
	public static String[] getNameList() {
		
		String[] listNameArray = new String[comptes.size()]; 
		int i=0;
		for( Compte compte : comptes)
		{
			listNameArray[i] = compte.getNom();
			i++;
		}
		
		return listNameArray;
	}
	
	public void run() {
		
		ServerSocket serveurMulti;
		Socket serveurSocket;
		Boolean actif=true;
		
		try 
		{
			serveurMulti = new ServerSocket(8189); 
			
			serveurMulti.setSoTimeout(100); //Pour limiter le nombre d'attente de la methode accept
			
			while( actif)
			{
				try
				{
					// Le serveur est bloqué ici pour 100ms en attente de connection pourtant
					// il faut prendre l'information si le controleur veux s'arrête ou pas
					serveurSocket = serveurMulti.accept();
					
					new ThreadClient(serveurSocket); // démarage du threadClient
					
				} catch (SocketTimeoutException e) { //Après 100ms , on passe part ici
					
					if (interrupted()) // Si le controleur  c'est arrêter
					{
						//On arrête tous les Threads enfant
						Set<Thread> threadSet = Thread.getAllStackTraces().keySet(); // Dans un ensemble itérable
						Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
						
						for(int i=0 ; i<threadArray.length; i++) {
							threadArray[i].interrupt(); // envoie le signal a tout les threads
						}
						System.out.println(threadArray.length + " Threads arrêté! ");
						
						serveurMulti.close(); // Le serveur s'arretê
						actif = false;
					}
				} 	
			}
		} catch (BindException e) {
			System.out.println("Le serveur est déjà démarer!!! ---->"+e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println( e.getMessage() );
		}
	}

	public static void sendNotify(String type, String nameCli) throws IOException {
		//*******************************************************************************************************************
		//* Envoie des notifications de "type" connection ou déconnection  du client "namecli" a tous les clients connectés *
		//*******************************************************************************************************************
		ServeurMulti.smsNotifyForAll = new Message("notify", ServeurMulti.getNameList() , ("\n **"+type+"** --> "+ nameCli).getBytes()); // Message de type notify
		for( Compte cpt : ServeurMulti.comptes) // Envoie de la notification a tous les client
		{
			ObjectOutputStream outp = cpt.getOut(); // ouput de ce compte
			outp.writeObject(ServeurMulti.smsNotifyForAll); // Envoie de ce message de notification
			System.out.println(nameCli + " --->("+ type + "notify Send to) "+ cpt.getNom());
		}
		
	}

	public static Compte nouveauCompte(Socket socketServeur, ThreadClient threadClient) throws IOException, ClassNotFoundException {
		//*************************************************************************************
		//*       Création d'un nouveau compte et envoie des notifications de connection     **
		//*                      autre clients connectés                                     **
		//*************************************************************************************
		Compte compte = new Compte(socketServeur, threadClient);
		ObjectInputStream in = compte.getIn(); // input de cette connection
		ObjectOutputStream out = compte.getOut(); // output de cette connection
		
		out.writeObject("Server --> Your Name: "); // Demmande du nom du client, Le nom doit être unique 
		
		String nameCli;
		while( true ) // Tant que le compte n'est pas configuré (Problème de nom)
		{
			nameCli = (String) in.readObject(); // Recuperation du nom
			if( Arrays.asList(ServeurMulti.getNameList()).contains(nameCli)) // test si le nom existe déjà
			{
				out.writeObject("ErrorName");//Erreur de nom
			}
			else 
			{
				out.writeObject("\n******** Bienvenue "+ nameCli + " *********\n "
						+ "Protocole communication:\n\t\t "
						+ "text--> Message Texte\n\t\t "
						+ "fichier--> image, video, pdf...\n\t\t"
						+ "notify --> notification"); // Message de bienvenue
				
				ServeurMulti.sendNotify("Connection",nameCli);  // Envoie de la notification vers tout les clients connectés
				
				compte.setNom(nameCli); // Configuration du nom
				ServeurMulti.comptes.add(compte); // Et finnalement, ajout de client dans la liste des comptes
				break;
			}
		}
		System.out.println(nameCli+ " connecté");
		
		return compte;
	}
}
