package business;

import java.awt.Color;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;

import persistence.Sprite;

/**
 * Class that describes the server side of the application. It creates and handles
 * the properties of remote objects which are delegated through a remote interface.
 * @author		Chris Enos
 * @version		1.0a
 * @since 		November-8-2016
 */
public class RemoteServer extends UnicastRemoteObject implements ServerInterface, Runnable {
	
	/** IDE auto-generated serialID */
	private static final long serialVersionUID = 1348108122884691556L;

	/** List holds Sprites objects */
	private List<Sprite> spriteList;
	
	/** SessionFactory object */
	private SessionFactory factory;
	
	/**
	 * Constructor builds resources for Hibernate
	 * @throws RemoteException
	 */
	public RemoteServer() throws RemoteException {
		spriteList = new ArrayList<Sprite>();
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try {
			MetadataImplementor meta = (MetadataImplementor) new MetadataSources(registry)
			.addAnnotatedClass(Sprite.class).buildMetadata();
			// new SchemaExport(meta).create(true, true);
			factory = meta.buildSessionFactory();
		} catch (Exception ex) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}//End of Constructor for Class 'RemoteServer'

	/**
	 * Main method for program execution on JVM
	 * Runs server-side of program
	 * @param	args	command line parameter as a string array(Not Used)
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	public static void main(String args[]) throws RemoteException, AlreadyBoundException {
		String remoteName = "assignment3";
		String hostName = "localhost";
		int portNum = 8082;
		
		try {
			System.setProperty("java.server.rmi.hostname", hostName);
			RemoteServer server = new RemoteServer();
		
			Registry registry = LocateRegistry.createRegistry(portNum);
			registry.rebind(remoteName, server);
			System.out.print("Server is now running at " + hostName +".");
		} catch (Exception ex){
			ex.printStackTrace();
		}//end of 'try-catch' block
	}//End of Method 'main'

	/**
	 * Overridden method that creates a new Sprite. Uses Hibernate
	 * to keep remote object persistent
	 * @param	x		int horizontal position of new Sprite
	 * @param	y		int vertical position of new Sprite
	 * @param	color	Color object of the new Sprite
	 * @throws	RemoteException
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override 
	public void createSprite(int x, int y, Color color) throws RemoteException {
		//Session setup
		Session s = factory.getCurrentSession();
		Sprite newSprite = new Sprite(x, y, color);
		try {
			s.beginTransaction();
			s.save(newSprite);
			spriteList = (ArrayList<Sprite>) s.createCriteria(Sprite.class).list();
			s.getTransaction().commit();
			//try to save sprite in session and add to ArrayList
		} catch(Exception ex) {
			// rollback if Error is encountered
			ex.printStackTrace();
			s.getTransaction().rollback();
		}//end of 'try-catch' block
		new Thread(this).start();
	}//End of Method 'createSprite'

	/**
	 * Overridden method that gets the list of currently active Sprites
	 * @return List<Sprite>  list of active Sprites
	 * @throws	RemoteException
	 */
	@Override 
	public List<Sprite> getSpriteList() throws RemoteException {
		return spriteList;		
	}//End of Method 'getSpriteList'

	/**
	 * Overridden get method that returns a random Color object for Sprite color
	 * @return a random Color object.
	 */
	@Override
	public Color getColor(){	
		Random rand = new Random();
		int r = rand.nextInt(254-0);
		int g = rand.nextInt(254-0);
		int b = rand.nextInt(254-0);
		Color color = new Color(r, g, b);
		return color;
	}// End of Method 'getColor'

	/**
	 * Method that loops through list of sprites with the 'move' method
	 * while updating the status to Hibernate
	 */
	@Override 
	public void moveSprite() throws RemoteException {
		// get the current session
		Session session = factory.getCurrentSession();
		try {
			// move the sprite and update the persistent instance
			session.beginTransaction();
			for(Sprite sprite : spriteList){
				sprite.move();
				session.update(sprite);
			}//end of 'for' loop
			session.getTransaction().commit();
		} catch(Exception ex) {
			// rollback if there was a problem
			ex.printStackTrace();
			session.getTransaction().rollback();
		}//end of 'try-catch' block

		try {
			Thread.sleep(40); // wake up roughly 25 frames per second
		} catch(InterruptedException ex) {
			ex.printStackTrace();
		}//end of 'try-catch' block
	}//End of Method 'moveSprite'

	/**
	 * Overridden run method which handles thread execution of moveSprite
	 */
	@Override
	public void run() {
		while(true) {
			try {
				moveSprite();
			} catch (RemoteException ex) {
				ex.printStackTrace();
			}//end of 'try-catch' block
		}//end of 'while' loop
	}//End of Method 'run'
}//EOF: End of Class 'RemoteServer'
