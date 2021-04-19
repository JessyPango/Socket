package client;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

import commun.Message;

public class ThreadEnvoi extends Thread{
	
	@Override
	public void run() 
	{
		ThreadReception threadReception = new ThreadReception();
		
		try 
		{	
			//*************************************************************************************
			//*       				Configuration avec le serveur                                **
			//*                                                                                  **
			//*************************************************************************************
			
			
			System.out.println((String) Client.in.readObject()); // Premier message du serveur, Le NOM
			while( true ) 
			{
				Client.nom = Client.sc.nextLine(); 
				Client.out.writeObject( Client.nom ); // Evoie du nom au serveur
				
				String reponce = (String) Client.in.readObject();
				//System.out.println(reponce);
				if( reponce.equals("ErrorName") ) {
					System.out.println("Serveur --> Nom existe déjà " );
				} else {
					System.out.println(reponce); // Reception du message bienvenue de serveur
					break;
				}
			}
			
			System.out.println("Début de comminication avec le serveur");
			//*******************************************************************************************
			//*                      Lancement du thread pour la reception                              *
			//*******************************************************************************************
			threadReception.start();
			
			//*******************************************************************************************
			//*                     			Début de l'envoie                                       *
			//*******************************************************************************************
			while( ! interrupted() )
			{	
				System.out.print("Commande >>> $  ");
				String cmd = Client.sc.nextLine();
				
				try 
				{
					if (cmd.equals("text"))
					{
						System.out.print("Recever name : ");
						String receve = Client.sc.nextLine();
						
						System.out.print("Type text to send :  ");
						byte[] data = Client.sc.nextLine().getBytes();
					    
					    Client.message = new Message("text", receve.split(" "), data);
		
					    File file = new File("file" , "tmp.txt");
					    if( ! file.exists()) 
					    {
					    	try
					    	{
					    		file.createNewFile();
					    	} catch (IOException e) {
					    		File dir = new File("file");
					    		dir.mkdir();
					    		file.createNewFile();
					    	}
					    }
						Message.transfertClavier(file, Client.message);
					} 
					else if(cmd.equals("fichier"))
					{
						System.out.print("Recever name : ");
						String receve = Client.sc.nextLine();
						
						System.out.print("Complete path of file: ");
						String srcPath = Client.sc.nextLine();
						Path src = Paths.get(srcPath);
						
						byte[] data = Files.readAllBytes(src);
						Client.message = new Message("fichier", receve.split(" "),data, src.getFileName().toString());
						
					}
					
					try
					{
						Client.out.writeObject(Client.message);
					} catch (SocketException e) {
						Client.in.close();
						System.out.println("Connection perdue !");
						threadReception.interrupt(); // demande de fermeture du threadReception
						break;
					}
					
					System.out.println();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (EOFException e) {
			System.out.println("Connection perdue !");
			threadReception.interrupted();
			
		} catch (NoSuchElementException e) {
			// Clavier
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
