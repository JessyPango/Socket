package commun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Scanner;
import java.util.StringTokenizer;

import client.Client;

public class Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8977637927945065927L;
	private String type; // Le type de message (texte, image, ...)
	private String encodage; // UTF-8, UNICODE, ...
	private Date dateEvoie; // date de reception par le serveur
	public String[] getDestinataire() {
		return destinataire;
	}

	private Date dateReception; // date de reception par le client destinataire
	private String source; // Client qui envoie 
	private String[] destinataire; // Client destinataire
	private byte[] data;
	private String nameFile ="";
	
	public String getNameFile() {
		return nameFile;
	}
	public String getSource() {
		return source;
	}
	public byte[] getData() {
		return data;
	}
	public String getType() {
		return type;
	}
	public String getEncodage() {
		return encodage;
	}

	public DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
	
	public Message(String sms) throws ParseException {
		StringTokenizer smsToken = new StringTokenizer(sms, "<>");
		this.type = smsToken.nextToken();
		this.source = smsToken.nextToken();
		this.destinataire = smsToken.nextToken().split(" ");
		this.dateEvoie = dateFormat.parse(smsToken.nextToken());
		this.dateReception = dateFormat.parse(smsToken.nextToken());
		this.encodage = smsToken.nextToken();
		this.data = smsToken.nextToken().getBytes();
	}

	public Message(String type, String[] destinataire, byte[] data) 
	{
		this.type = type;
		this.encodage = System.getProperty("file.encoding");
		
		LocalDateTime localDateTime = LocalDateTime.now();
	    ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneOffset.systemDefault());
	    Instant instant = zonedDateTime.toInstant();
	    Date date = Date.from(instant);
	    
		this.dateEvoie = date;
		this.dateReception = date;
		this.source = Client.nom;
		this.destinataire = destinataire;
		this.data = data;
	}
	
	public Message(String type, String[] destinataire,  byte[] data, String nameFile) {
		this.type = type;
		this.encodage = System.getProperty("file.encoding");
		
		LocalDateTime localDateTime = LocalDateTime.now();
	    ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneOffset.systemDefault());
	    Instant instant = zonedDateTime.toInstant();
	    Date date = Date.from(instant);
	    
		this.dateEvoie = date;
		this.dateReception = date;
		this.source = Client.nom;
		this.destinataire = destinataire;
		this.data = data;
		this.nameFile = nameFile;
	}
	public String toString() {
		// TODO Auto-generated method stub
		try {
			return type + "<>" + source + "<>" + destinataire + "<>" + dateFormat.format(dateEvoie) + "<>" +dateFormat.format(dateReception) +"<>"+ encodage + "<>" + new String(data, encodage)+ "\n";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Message readLine(Scanner sc , File file) throws IOException, ParseException {
		
		String sms;
		//BufferedReader sc = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		sms = sc.nextLine();
		return  new Message(sms);
		
	}
	
	public static void transfertClavier(File file, Message sms) throws IOException, URISyntaxException {
		
		FileOutputStream out = new FileOutputStream(file, true);
		out.write(sms.toString().getBytes());
		out.close();
	}
	
	public void transfert(OutputStream out, boolean fermerIn, boolean fermerOut) throws IOException {
		
		ObjectOutputStream oout = new ObjectOutputStream(out);
		
		oout.writeObject(this);
		oout.close();
		if(fermerOut)
			out.close();
	} 
	// Tranfert de message d'un flux d'entree vers un flux de sortie avec demandes de fermeture de flux (in ou out))
		
		
		

}


