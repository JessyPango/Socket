package client;

import java.io.EOFException;
import java.io.IOException;

import commun.Message;

public class ThreadReception extends Thread{

	@Override
	public void run() 
	{
		while(! Client.socket.isClosed())
		{			
			Message sms;
			try
			{	
				if(Client.socket.isClosed())
					break;
				else
				{
					sms = (Message) Client.in.readObject();
					Client.recevesMessage.add(sms);
					
					ThreadTraitement threadTraitement = new ThreadTraitement();
					threadTraitement.start();
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
		
		
	}
}
