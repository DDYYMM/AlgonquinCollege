package business;

import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import persistence.Sprite;

/**
 * Interface to create new remote objects, return a list of remote objects
 * And manage the individual properties of movement and color.
 * @author		Chris Enos
 * @version		1.0a
 * @since 		November-8-2016
 */
public interface ServerInterface extends Remote {
	
	/**
	 * Method to create a new Sprite object
	 * @param x			horizontal position of sprite
	 * @param y			vertical position of sprite
	 * @param color		Color of sprite
	 * @throws RemoteException
	 */
	void createSprite(int x, int y, Color color) throws RemoteException;
	
	/**
	 * Method to get a list containing sprites
	 * @return List<Sprite>
	 * @throws RemoteException
	 */
	List<Sprite> getSpriteList() throws RemoteException;

	/**
	 * Method to get color of sprite
	 * @return Color object
	 * @throws RemoteException
	 */
	Color getColor() throws RemoteException;
	
	/**
	 * Method to manage movement of collection of sprites
	 * @throws RemoteException
	 */
	void moveSprite() throws RemoteException;
	
} //EOF: End Interface 'ServerInterface'
