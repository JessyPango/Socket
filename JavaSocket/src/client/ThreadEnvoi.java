package client;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import commun.Message;

public class ThreadEnvoi extends Thread{
	
	@Override
	public void run() 
	{
		try 
		{
			System.out.print("Welcome ! Your name: ");
			Client.nom = Client.sc.nextLine();
			Client.out.writeObject(Client.nom);
			System.out.println((String) Client.in.readObject());
			
			ThreadReception th = new ThreadReception();
			th.start();
			
			while( ! interrupted())
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
					
					Client.out.writeObject(Client.message);
					System.out.println();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}