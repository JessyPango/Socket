package client;

import java.io.OutputStream;

public class ThreadLecture extends Thread{
	
	private final OutputStream sortie;
	
	public ThreadLecture(OutputStream sortie) {
		this.sortie = sortie;
	}
	
	@Override
	public void run() {
		// Execute le code pour la lecture du message du client;
	}
}
