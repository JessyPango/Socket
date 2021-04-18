package client;

import java.io.EOFException;
import java.io.IOException;

import commun.Message;

public class ThreadReception extends Thread{

	@Override
	public void run() 
	{
		while( ! interrupted() )
		{			
			Message sms;
			ThreadTraitement threadTraitement = new ThreadTraitement();
			try
			{	
					sms = (Message) Client.in.readObject();
					Client.recevesMessage.add(sms);
					threadTraitement.start();
		
			} catch (EOFException e) {
				
				try {
					Client.out.close();
					Client.in.close();
					threadTraitement.interrupt();
					System.out.println("Le serveur est indisponible! ");
					break;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}
