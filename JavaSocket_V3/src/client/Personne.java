package client;

public class Personne {
	
	private String nom; // Nom de la personne connecté
	private int id; // Identifiant de la personne
	
	
	public String getNom() {
		return (String) nom + id; // le nom concatener l'identifiant
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
