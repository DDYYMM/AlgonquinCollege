package server;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

/**
 * Simple Echo multi-threaded server implemented in Java 
 * @author Christopher Enos
 * @version 1.3a
 * @since 18/10/16
 */
public class EchoServer implements Runnable{

	/**
	 * final int var used to create the a default port number
	 */
	public static final int DEFAULT_PORT = 8081;

	/**
	 * int var to set port number during main
	 */
	private int portNum;

	/**
	 * Main method allowing default values in eclipse or command argument of port number
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			(new EchoServer(Integer.parseInt(args[0]))).run();
		} else {
			(new EchoServer(DEFAULT_PORT)).run();
		}//End of 'if-else' block
	}//End of Method 'main'

	/**
	 * Constructor assigning field to arguments
	 * @param portNum :int for command argument
	 */
	public EchoServer(int portNum) {
		this.portNum = portNum;
	}//End of Constructor for Class 'EchoServer'

	/**
	 * Override 'run' method that handles the acceptance of client connections
	 * via ServerSocketConnection and multi-threading
	 */
	@Override
	public void run() {
		System.out.println("Echo Server Started...");

		try (ServerSocket server = new ServerSocket(DEFAULT_PORT)){

			while(server != null){
				Socket sock = server.accept();
				System.out.println("Connected");
				new Thread(new ServerSocketConnection(sock)).start();
			}//End of 'while' loop

		} catch (IOException e) {
			e.printStackTrace();
		}//End of 'try-with-resources' block 
	}//End of Override Method 'run'
}//EOF: End of Class 'EchoServer'
