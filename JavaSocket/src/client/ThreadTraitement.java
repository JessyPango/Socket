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
		Message  sms;
		while( ! Client.recevesMessage.isEmpty() ) {
			sms = Client.recevesMessage.get(0);
			if(sms.getType().equals("text") ) {
				try {
					System.out.println("\nSMS from "+sms.getSource()+"---> "+ new String(sms.getData(), sms.getEncodage()) );
				} catch (UnsupportedEncodingException e) {
					System.out.println("\nSMS from"+sms.getSource()+": "+  new String(sms.getData()));
				}
			} else {
				File file = new File("file", sms.getNameFile());
				try {
					if( ! file.exists())
						file.createNewFile();
					FileOutputStream out = new FileOutputStream(file);
					out.write(sms.getData());
					out.close();
					System.out.println("\nSMS from "+sms.getSource()+": <Fichier> --->file/"+sms.getNameFile());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Client.recevesMessage.remove(0);
		}
	}
}
