package persistence;

import java.awt.Color;
import java.awt.Graphics;
import java.rmi.RemoteException;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
/**
 * Class that give Sprite jdbc properties as well as handling of movement in JFrame
 * @author		Todd Kelley, Chris Enos
 * @version		1.0a
 * @since 		November-8-2016
 */
@Entity
@Table(name="Sprites")
public class Sprite implements Serializable {

	/** IDE auto-generated serialID */
	private static final long serialVersionUID = 3359791215162952905L;

	/** Random object used to determine values of initial speed and direction */
	private static final Random RANDOM = new Random();

	/** Determines size/speed upper limits */
	private static final int SIZE = 10;
	private static final int MAX_SPEED = 5;

	private static final int BOUNDARY = 500;
	
	/** Determines Sprite position on frame */
	private int x;
	private int y;

	/** Determines Sprite speed/direction on frame */
	private int dx;
	private int dy;

	/** Determines color of sprite */
	private Color color;

	/** Determines Sprite's ID */
	private int id;

	/**
	 * Parameterized constructor.
	 * @param	x		int x-axis position
	 * @param	y		int y-axis position
	 * @param	color	Color object specifying the Sprite's color
	 */
	public Sprite(int x, int y, Color color) {
		
		this.x = x;
		this.y = y;
		this.color = color;
		dx = RANDOM.nextInt(2*MAX_SPEED) - MAX_SPEED;
		dy = RANDOM.nextInt(2*MAX_SPEED) - MAX_SPEED;
	
	}//End of Constructor for Class 'Sprite'

	/**
	 * Method gets Sprite's horizontal position
	 * @return x int vertical position
	 */
	public int getX() { return x; }

	/**
	 * Method sets Sprite's horizontal position
	 * @param x int horizontal position
	 */
	public void setX(int x) { this.x = x; }

	/**
	 * Method gets Sprite's vertical position
	 * @return y int vertical position.
	 */
	public int getY() { return y; }

	/**
	 * Method sets Sprite's vertical position
	 * @param y int vertical position.
	 */
	public void setY(int y) { this.y = y; }

	/**
	 * Method gets Sprite's horizontal direction/speed
	 * @return dx int speed/direction
	 */
	public int getDX() { return dx;	}

	/**
	 * Method sets Sprite's horizontal direction/speed
	 * @param dx int speed/direction
	 */
	public void setDX(int dx) { this.dx = dx; }
	
	/**
	 * Method gets Sprite's veritcal direction/speed
	 * @return dy int speed/direction
	 */
	public int getDY() { return dy;	}

	/**
	 * Method sets Sprite's veritcal direction/speed
	 * @return dy int speed/direction
	 */
	public void setDY(int dy) {	this.dy = dy; }

	/**
	 * Method gets color of sprite
	 * @return color Color object
	 */
	public Color getColor() { return color; }

	/**
	 * Method sets color of Sprite
	 * @param color Color object
	 */
	public void setColor(Color color) { this.color = color;	}

	/**
	 * Method gets Sprite's ID. Hibernate auto-generated value
	 * @return id int
	 */
	@Id
	@GeneratedValue
	public int getID() { return id; }

		
	/**
	 * Method sets Sprite ID(Hibernate does this automatically) 
	 * @param	id	int 
	 */
	public void setID(int id) { this.id = id; }

	/**
	 * Method draws sprite
	 * @param	g	Graphics object
	 */
	public void draw(Graphics g) {
		
		g.setColor(color);
		g.fillOval(x, y, SIZE, SIZE);
		
	}//End of Method 'draw'

	/**
	 * Method determines how sprite object will handle movement
	 */
	public void move() throws RemoteException {       

		if (x < 0 && dx < 0) {
			//bounce off left wall
			x = 0;
			dx = -dx;
		}

		if (y < 0 && dy < 0) {
			//bounce off top wall
			y = 0;
			dy = -dy;
		}

		if (x > BOUNDARY - SIZE && dx > 0) {
			//bounce off right wall
			x = BOUNDARY - SIZE;
			dx = - dx;
		}

		if (y > BOUNDARY - SIZE && dy > 0) {
			//bounce off the bottom wall
			y = BOUNDARY - SIZE;
			dy = -dy;
		}
		//move sprite
		x += dx;
		y += dy;

	} //End of Method 'move'
} //EOF: End of Class 'Sprite'
