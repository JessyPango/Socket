package message;

import java.sql.Date;

public class Message {
	
	private String type; // Le type de message (texte, image, ...)
	private String encodage; // UTF-8, UNICODE, ...
	private Date dateEvoie; // date de reception par le serveur
	private Date dateReception; // date de reception par le client destinataire
	private String source; // Client qui envoie 
	private String destinataire; // Client destinataire
	
	public Message(String type, String encodage, Date dateEvoie, Date dateReception, String source,String destinataire) {
		this.type = type;
		this.encodage = encodage;
		this.dateEvoie = dateEvoie;
		this.dateReception = dateReception;
		this.source = source;
		this.destinataire = destinataire;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEncodage() {
		return encodage;
	}

	public void setEncodage(String encodage) {
		this.encodage = encodage;
	}

	public Date getDateEvoie() {
		return dateEvoie;
	}

	public void setDateEvoie(Date dateEvoie) {
		this.dateEvoie = dateEvoie;
	}

	public Date getDateReception() {
		return dateReception;
	}

	public void setDateReception(Date dateReception) {
		this.dateReception = dateReception;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(String destinataire) {
		this.destinataire = destinataire;
	}

}
