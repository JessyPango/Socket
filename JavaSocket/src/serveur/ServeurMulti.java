package serveur;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Set;

public class ServeurMulti extends Thread {
	
	public static ArrayList<Compte> comptes = new ArrayList<Compte>();
	
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
						System.out.println("Le serveur veut s'arrêter");
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
			System.out.println(e.getMessage());
		}
	}
	
	public static void diffuse(String sms, ThreadClient th) {
		
	}
}
