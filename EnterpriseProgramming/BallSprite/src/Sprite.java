package bouncingsprites;
//%W%	%G%
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * Class is responsible for all properties of the Sprite object
 * (i.e.: movement, size, speed) and implements the Runnable 
 * Interface to execute it's movement on threads
 * @author Chris Enos
 * @since 2016-09-09
 * @version 1.0b
 */
public class Sprite implements Runnable {

	/**
	 * Reference to random object for speed values
	 */
	public final static Random random = new Random();

	/**
	 * Sets speed ceiling and size 
	 */
	final static int SIZE = 10;
	final static int MAX_SPEED = 5;

	/**
	 * Sprite panel object  
	 */
	SpritePanel panel;

	/**
	 * Provides coordinates for sprites
	 */
	private int x;
	private int y;

	/**
	 * Provides movement speed of sprites
	 */
	private int dx;
	private int dy;

	/**
	 * Sets color of sprites
	 */
	private Color color = Color.BLUE;

	/**
	 * Switch to change status of sprites
	 */
	boolean inRoom;

	/**
	 * Constructor giving a reference to object of type Sprite
	 * upon creation a random location and speed of movement
	 * @param panel
	 */
	public Sprite (SpritePanel panel){
			this.panel = panel;
			x = random.nextInt(panel.getWidth());
			y = random.nextInt(panel.getHeight());
			dx = random.nextInt(2*MAX_SPEED) - MAX_SPEED;
			dy = random.nextInt(2*MAX_SPEED) - MAX_SPEED;
		
	}//end of constructor

	/**
	 * Draws the sprite on the panel
	 * @param g reference to Graphic
	 */
	public void draw(Graphics g){
		g.setColor(color);
		g.fillOval(x, y, SIZE, SIZE);
	}//end of method 'draw'

	/**
	 * Movement of sprite
	 */
	public void move(){
		// check for bounce and make the ball bounce if necessary
		//
		if (x < 0 && dx < 0){
			//bounce off the left wall 
			x = 0;
			dx = -dx;
		}
		if (y < 0 && dy < 0){
			//bounce off the top wall
			y = 0;
			dy = -dy;
		}
		if (x > panel.getWidth() - SIZE && dx > 0){
			//bounce off the right wall
			x = panel.getWidth() - SIZE;
			dx = - dx;
		}       
		if (y > panel.getHeight() - SIZE && dy > 0){
			//bounce off the bottom wall
			y = panel.getHeight() - SIZE;
			dy = -dy;
		}

		//make the ball move
		x += dx;
		y += dy;
	}//end of method 'move'

	/**
	 * Checks to see if sprite is in room or not
	 * @throws InterruptedException in case of thread lock
	 */
	private void checkStatus() throws InterruptedException{
		try{
			if (panel.inRoomBoundary(x, y) && !inRoom){

				panel.consume();
				inRoom = true;
			}
			if(!panel.inRoomBoundary(x,y) && inRoom){

				panel.produce();
				inRoom = false;
			}
		}
		catch( InterruptedException exception ){
			exception.printStackTrace();
		}
	}//end of method 'checkStatus'



	/**
	 * Controls sprite objects concurrent movement path
	 */
	@Override
	public void run(){
		try {
			while (true){
				checkStatus();
				move();
				Thread.sleep(40);
			}
		}
		catch ( InterruptedException exception ) {
			exception.printStackTrace();
		}
	}//end of method 'run'


	@Deprecated
	public void setX(int x) {
		this.x = x;
	}

	
	@Deprecated
	public void setY(int y) {
		this.y = y;
	}

	
	@Deprecated
	public void setDx(int dx) {
		this.dx = dx;
	}

	
	@Deprecated
	public void setDy(int dy) {
		this.dy = dy;
	}

}//EOF:end of class Sprite
