package test1;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Set;

public class ServeurMulti extends Thread {
	
	public void run() {
		
		ServerSocket serveurMulti;
		Socket serveurSocket;
		Boolean actif=true;
		Thread threadClient;
		
		try 
		{
			serveurMulti = new ServerSocket(8189);
			
			//Pour limiter le nombre d'attente de la methode accept)
			serveurMulti.setSoTimeout(100);
			
			while( actif)
			{
				try
				{
					serveurSocket = serveurMulti.accept();
					
					ThreadClient.nombreClient ++;
					threadClient= new ThreadClient(serveurSocket, ThreadClient.nombreClient);
					threadClient.start();
				} catch (SocketTimeoutException e) {
					//Après 1s , on passe part ici
					if (interrupted()) // Si le controleur  c'est arrêter
					{
						serveurMulti.close();
						
						//On arrête tous les Threads
						Set<Thread> threadSet = Thread.getAllStackTraces().keySet(); // Dans un ensemble itérable
						Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
						
						for(int i=0 ; i<threadArray.length; i++) {
							threadArray[i].interrupt();
						}
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
}
