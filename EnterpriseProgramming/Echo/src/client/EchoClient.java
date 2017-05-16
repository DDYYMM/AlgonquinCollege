package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Simple EchoClient using socket connections in java
 * @author Todd Kelley
 */
public class EchoClient implements Runnable{

	/**
	 * Socket var that handles connection
	 */
	private Socket connection;

	/**
	 * ObjectOutputStream var for input stream on connection
	 */
	private ObjectOutputStream output;

	/**
	 * ObjectInputStream var for input stream on connection
	 */
	private ObjectInputStream input;

	/**
	 * String var for messages sent and recieved to display as string
	 */
	private String message = "";

	/**
	 * String var for constructor arguments
	 */
	private String serverName;

	/**
	 * final String var to determine server name
	 */
	public static final String DEFAULT_SERVER_NAME = "localhost";

	/**
	 * int var to set port number during main
	 */
	private int portNum;

	/**
	 * BufferedReader var to read input from keyboard
	 */
	BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Main method that gives 3 options of use 2 for console arguments and one for default Eclipse run
	 * @param args :Array of String for console arguments
	 */
	public static void main(String[] args) {
		switch (args.length) {
		case 2:
			(new EchoClient(args[0], Integer.parseInt(args[1]))).run();
			break;
		case 1:
			(new EchoClient(DEFAULT_SERVER_NAME, Integer.parseInt(args[0]))).run();
			break;
		default:
			(new EchoClient(DEFAULT_SERVER_NAME, server.EchoServer.DEFAULT_PORT)).run();
		}//End of 'switch' block
	}End of Method 'main'

	/**
	 * Constructor assigning field to arguments
	 * @param serverName :String declaring server name
	 * @param portNum :int declaring port
	 */
	public EchoClient(String serverName, int portNum) {
		this.serverName = serverName;
		this.portNum = portNum;
	}//End of Constructor for Class 'EchoClient'

	/**
	 * Override 'run' method to handle the client behavior
	 * when connecting to server
	 */
	@Override
	public void run() {
		try {

			/*
			 * Assignment of socket var to a servername and port constructor
			 */
			connection = new Socket(InetAddress.getByName(serverName), portNum);

			/*
			 * Assignment of ObjectOutputStream to a connection
			 */
			output = new ObjectOutputStream(connection.getOutputStream());

			/*
			 * Assignment of a ObjectInputStream to a connection
			 */
			input = new ObjectInputStream(connection.getInputStream());


			System.out.println("To Quit, enter EOF (^Z on Windows; ^D on Linux/Mac)");


			do {//Handles I/O on client end
				System.out.print("Input> ");
				message = keyboard.readLine();
				if (message != null){
					output.writeObject(message);
					output.flush();
					message = (String) input.readObject();
					System.out.println(message);
				}//End of 'if' block
			} while (message != null); //End of 'do-while' loop

		} catch (IOException ioException) {			
			ioException.printStackTrace();
		} catch (ClassNotFoundException exception) { 
			exception.printStackTrace();
		}finally{ //finally block to ensure proper closure of resources
			if(output != null){ try { output.close();} catch (IOException e) { e.printStackTrace();} }
			if(input != null){ try { input.close();} catch (IOException e) { e.printStackTrace();} }
			if(connection != null){ try { connection.close();} catch (IOException e) { e.printStackTrace();} }
		}//End of 'try-catch-finally' block
	}//End of Override Method 'run'
}//EOF: End of Class 'EchoClient'
