package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 * This class provides the behavior of a single server connection.
 * @author Christopher Enos
 * @version 1.0a
 * @since 18/10/16
 */
public class ServerSocketConnection implements Runnable{

	/**
	 * Socket var used to tie i/o to a connection
	 */
	private Socket clientSocket;

	/**
	 * String var used to hold input and output streams
	 * display
	 */
	private String message;

	/**
	 * Interger var used to keep track of message numbers
	 * for display
	 */
	private int messageNum;

	/**
	 * ServerSocketConnection constructor assigning the class var
	 * to the constructor param
	 * @param clientSocket :Accepts argument of type Socket
	 */
	public ServerSocketConnection(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}//End of Constrctor for Class 'ServerSocketConnection'

	/**
	 * Override 'run' method to handle the server behavior
	 * when Clients connect
	 */
	@Override
	public void run() {
		try( 	
				/* 
				 * ObjectOutputStream to assign output stream to client socket
				 */
				ObjectOutputStream output = 
				new ObjectOutputStream(clientSocket.getOutputStream());

				/* 
				 * ObjectInputStream to assign input stream to client socket
				 */
				ObjectInputStream input = 
						new ObjectInputStream(clientSocket.getInputStream())){

			/*
			 * Loop that handles the output input streams
			 */
			do {
				try {
					message = (String) input.readObject();
				}catch (EOFException e){
					message = null;
				}//End of 'try-catch' block
				if (message != null){
					output.writeObject(messageNum++ + " FromServer> " + message);
					output.flush();
				}//End of 'if' block
			} while (message != null);//End of 'do-while' loop

		} catch (IOException exception) {
			exception.printStackTrace();
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		}finally{ 
			if(clientSocket != null){ try { clientSocket.close();} catch (IOException e) { e.printStackTrace();} }
		}//End of 'try-with-resources'/'try-catch-finally' block
	}//End of Override Method 'run'
}//EOF: End of Class 'ServerSocketConnection'
