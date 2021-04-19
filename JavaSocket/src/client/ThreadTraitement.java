package client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import commun.Message;

public class ThreadTraitement extends Thread{

	@Override
	public void run() 
	{
		//**************************************************************************************************
		//                Traitement des messages et affichage                                             *
		//**************************************************************************************************
		Message  sms;
		while( ! Client.recevesMessage.isEmpty() )  // Tant qu'il y'a un message stocké
		{
			sms = Client.recevesMessage.get(0);
			if(sms.getType().equals("text") ) {
				try {
					System.out.println("\nSMS from "+sms.getSource()+"---> "+ new String(sms.getData(), sms.getEncodage()) );
				} catch (UnsupportedEncodingException e) {
					System.out.println("\nSMS from"+sms.getSource()+"--> "+  new String(sms.getData()));
				}
			} else if( sms.getType().equals("fichier") )
			{
				File file = new File("file", sms.getNameFile());
				try {
					if( ! file.exists())
						try
				    	{
				    		file.createNewFile();
				    	} catch (IOException e) {
				    		File dir = new File("file");
				    		dir.mkdir();
				    		file.createNewFile();
				    	}
					FileOutputStream out = new FileOutputStream(file);
					out.write(sms.getData());
					out.close();
					System.out.println("\nSMS from "+sms.getSource()+": <Fichier> --->file/"+sms.getNameFile());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if ( sms.getType().equals("notify") )
			{
				try {
					System.out.println(new String(sms.getData(), sms.getEncodage()) );
				} catch (UnsupportedEncodingException e) {
					System.out.println("\nSMS from"+sms.getSource()+": "+  new String(sms.getData()));
				}
			}
			Client.recevesMessage.remove(0);
		}
	}
}
