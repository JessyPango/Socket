package serveur;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import commun.Message;

public class ThreadClient  extends Thread {

	private Socket socketServeur;
	
	public ThreadClient(Socket serveurSocket) {
		
		this.start();
		this.socketServeur = serveurSocket;
	}

	public void run()
	{
		Compte compte=null;
		try 
		{
			compte = ServeurMulti.nouveauCompte(socketServeur, this); // Création et configuration d'un compte
			
			//*************************************************************************************
			//*       		Début de comminication avec le client                          		 **
			//*                                                                                  **
			//*************************************************************************************
			while( !interrupted() ) // Tantque le serveurMulti n'envoie pas de signal d'interruption
			{			
				Message sms;
				try
				{
					sms = (Message) compte.getIn().readObject(); // Récupération du message
					System.out.println("Transfert en cours...");
					
					for ( String name : sms.getDestinataire()) // Parcourt de la liste des destinataires dans le message
					{ 
						for( Compte cpt : ServeurMulti.comptes) // Parcourt de la liste des comptes
						{
							if(name.equals(cpt.getNom())) // Si le compte est celui du destinataire
							{
								ObjectOutputStream outp = cpt.getOut(); // récupération de sont output
								outp.writeObject(sms); // Transfert du sms
								System.out.println(sms.getSource()+" Send to ---> "+ name); // Affiche dans l'interface du serveur Multi
							}
						}
					}
				} catch (EOFException e) {
				
					break;
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}catch (IOException | ClassNotFoundException e1) {
			System.out.println("Un échec de configuration");
		}
		finally {
			
			try {
				socketServeur.close(); // fermeture de ce socket
				String nomClient= compte.getNom();
				ServeurMulti.comptes.remove(compte); // Suppressions client dans la liste des comptes
				
				ServeurMulti.sendNotify("Déconnection",nomClient);  // Envoie de la notification vers tout les clients connectés
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
