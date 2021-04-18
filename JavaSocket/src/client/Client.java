package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import commun.Message;

public class Client extends Thread{
	
	public static String nom = "";
	public static Message message;
	public static ArrayList<Message>  recevesMessage = new ArrayList<Message>();
	public static Socket socket;
	public static ObjectOutputStream out;
	public static ObjectInputStream in;
	public static Scanner sc;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException 
	{
		socket = new Socket("localhost", 8189);
		out = new ObjectOutputStream(Client.socket.getOutputStream());
		in = new ObjectInputStream(Client.socket.getInputStream());
		sc = new Scanner(System.in);
		ThreadEnvoi threadLecture = new ThreadEnvoi();
		threadLecture.start();
	}
	
}
