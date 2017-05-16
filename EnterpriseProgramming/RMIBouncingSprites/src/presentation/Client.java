package presentation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFrame;
import javax.swing.JPanel;

import business.ServerInterface;
/**
 * Class that describes client portion of program. It retrieves remote
 * objects from the server and displays them in a JFrame
 * @author		Chris Enos
 * @version		1.0a
 * @since 		November-8-2016
 */
public class Client extends JPanel{

	/** IDE auto-generated serialID */
	private static final long serialVersionUID = 7446911566889386715L;

	/** ServerInterface object to register connection to server */
	private ServerInterface server;

	/** Color object used to retrieve color of Sprite from server */
	private Color color;

	/** Static String declaring host */
	private static String hostName = "localhost";

	/** Static integer declaring port number */
	private static final int PORT = 8082;

	/** Static String declaring name for the remote reference */
	private static final String REMOTE_REF = "assignment3";

	/** Static integer to determine window size */
	private static final int WINDOW = 500;

	/**
	 * Constructor that locates server-side remote object an instantiates critical fields
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public Client() throws RemoteException, NotBoundException {

		Registry registry = LocateRegistry.getRegistry(hostName, PORT);
		server = (ServerInterface) registry.lookup(REMOTE_REF);
		color = server.getColor();
		addMouseListener(new Mouse());

	}//End of Constructor for Class 'Client'


	/**
	 * Overridden paintComponent method that retrieves the list of remote objects
	 * And paints what sprites are there to the JPanel
	 * @param	g	Graphics object.
	 */
	@Override 
	public void paintComponent(Graphics g){

		super.paintComponent(g);
		try {

			if (server.getSpriteList() != null) {
				for (int i = 0; i < server.getSpriteList().size(); i++) {
					server.getSpriteList().get(i).draw(g);
				}//end 'for' loop
			}//end 'if' block
		} catch(RemoteException ex) {
			ex.printStackTrace();
		}//end 'try-catch' block

	}//End of Method 'paintComponent'

	/**
	 * Inner class Mouse to handle MouseEvent. Sprite created at location of click.
	 */
	private class Mouse extends MouseAdapter {

		@Override
		public void mousePressed(final MouseEvent event){
			try {
				// create new Sprite at location of click
				server.createSprite(event.getX(), event.getY(), color);
			} catch (RemoteException e) {
				e.printStackTrace();
			}//end 'try-catch' block

		}//End of Method 'mousePressed'
	} //End of Class 'Mouse'


	/**
	 * Method infinitely loops to draw sprites
	 */
	public void animate(){
		while (true){	
			repaint();
			try {
				Thread.sleep(40);
			}
			catch(InterruptedException ex) {
				ex.printStackTrace();
			}//end 'try-catch' block
		}//end 'while' loop
	}//End of Method 'animate'

	/**
	 * Main method for program execution on JVM 
	 * Runs client-side of the program
	 * @param	args	command line parameter as a string array(Not Used)
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public static void main(String args[]) throws RemoteException, NotBoundException {

		JFrame frame = new JFrame("Bouncing Sprites");
		Client client = new Client();
		frame.setSize(WINDOW, WINDOW);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(client);
		frame.setVisible(true);
		frame.setResizable(false);
		client.animate();

	}//End of Method 'main'
} //EOF: End of Class 'Client'
