package client;

import java.io.InputStream;

public class ThreadAffiche extends Thread{

	private final InputStream entree;
	
	public ThreadAffiche(InputStream entree) {
		this.entree = entree;
	}
	
	@Override
	public void run() {
		//Execute le code pour l'affichage du client
		super.run();
	}
}
