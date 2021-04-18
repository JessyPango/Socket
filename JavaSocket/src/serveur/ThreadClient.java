package serveur;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
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
		try {
			
			Compte compte = new Compte(socketServeur, this);
			
			ObjectInputStream in = compte.getIn();
			ObjectOutputStream out = compte.getOut();
			
			String nameCli = (String) in.readObject();
			compte.setNom(nameCli);
			
			out.writeObject("\n******** Bienvenue "+ nameCli + "*******\n");
 
			ServeurMulti.comptes.add(compte);
			
			while( true)
			{			
				Message sms;
				try
				{	
					if(socketServeur.isClosed())
						break;
					else
					{
						sms = (Message) in.readObject();
						System.out.println("Transfert en cours...");
						
						for ( String name : sms.getDestinataire())
						{
							if(name.equals("group"))
							{
								for( Compte cpt : ServeurMulti.comptes)
								{
									//System.out.println(name + " = " +cpt.getNom() +" : "+ name.equals(cpt.getNom()));
									if(name.equals(cpt.getNom()))
									{
										ObjectOutputStream outp = cpt.getOut();
										outp.writeObject(sms);
										System.out.println("Send to ---> "+ name);
									}
								}
								break;
							}
							else 
								for( Compte cpt : ServeurMulti.comptes)
								{
									//System.out.println(name + " = " +cpt.getNom() +" : "+ name.equals(cpt.getNom()));
									if(name.equals(cpt.getNom()))
									{
										ObjectOutputStream outp = cpt.getOut();
										outp.writeObject(sms);
										System.out.println("Send to ---> "+ name);
									}
								}
						}
					}
			
				} catch (EOFException e) {
				
					e.printStackTrace();
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
}
