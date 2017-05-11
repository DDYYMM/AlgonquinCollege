package bouncingsprites;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Class is responsible for the majority of visual components in program
 * (i.e.: window, panel, sprites etc...)
 * Responsible for thread synchronization
 * @author Chris Enos
 * @since 2016-09-09
 * @version 1.0b
 */
public class SpritePanel extends JPanel{

	private static final long serialVersionUID = 42L;

	/**
	 * Reference to Sprite object
	 */
	Sprite sprite;

	/**
	 * Container for Sprite objects
	 */
	ArrayList<Sprite> spriteList = new ArrayList<Sprite>();

	/**
	 * Tracks number of sprite in room
	 */
	int spriteCounter = 0;

	/**
	 * Constructor that initializes mouseListner to panel 
	 */
	public SpritePanel(){
		addMouseListener(new Mouse());
	}//end of constructor

	/**
	 * Creates new reference to sprite object
	 * Gives reference a Thread and adds it to spriteList
	 * @param event reference to MouseEvent object 
	 */
	private void newSprite (MouseEvent event){
		sprite = new Sprite(this);
		/* Create a sprite with set boundaries
		 * sprite.setX(60);
		 * sprite.setY(65);
		 * sprite.setDx(-5);
		 * sprite.setDy(0);
		 */
		//attaches each sprite to it's own thread
		Thread thread = new Thread(sprite);
		thread.start();
		spriteList.add(sprite);
		System.out.println("New ball created");
	}//end of method 'newSprite'

	/**
	 * Responsible for repainting every frame to give 
	 * movement an animated effect
	 */
	public void animate(){
		while (true){
			repaint();
			//sleep while waiting to display the next frame of the animation
			try {
				Thread.sleep(40);  // wake up roughly 25 frames per second
			}
			catch ( InterruptedException exception ) {
				exception.printStackTrace();
			}
		}//end of while block
	}//end of method 'animate'

	/**
	 * Dictates action to be taken by MouseListner
	 * upon mouseclick
	 * @author Todd Kelly
	 * @version 1.0a
	 */
	private class Mouse extends MouseAdapter {
		@Override
		public void mousePressed( final MouseEvent event ){
			newSprite(event);
		}//end of method 'mousePressed'
	}//end of class 'Mouse'

	/**
	 * Paints components in List and Room
	 * @param g
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//draws room
		g.drawRect(50, 50, 250, 250);
		for(Sprite sprite: spriteList){
			sprite.draw(g);
		}//end of for-each block
	}//end of method 'paintComponent'

	/**
	 * Checks to see if the sprite is in set coordinates
	 * @param x
	 * @param y
	 * @return coordinates of rectangle
	 */
	public boolean inRoomBoundary(int x, int y){
		return (x>50 && x < 285 && y > 50 && y < 285 );
	}//end of method 'inRoomBoundary'

	/**
	 * Dictates what will happen if sprite is leaving
	 * @throws InterruptedException
	 */
	public synchronized void produce() throws InterruptedException{

		while(spriteCounter<2){
			try{	
				wait();
			}
			catch( InterruptedException exception ){
				exception.printStackTrace();
			}
		}//end of while block
		//update count and notify threads of change
		spriteCounter --;
		notifyAll();
	}//end of method 'produce'

	/**
	 * Dictates what ball will do while entering
	 * @throws InterruptedException
	 */
	public synchronized void consume() throws InterruptedException{

		while(spriteCounter==2){
			try{	
				wait();
			}
			catch( InterruptedException exception ){
				exception.printStackTrace();
			}
		}//end of while block
		//update count and notify threads of change
		spriteCounter ++;
		notifyAll();
	}//end of method 'consume'
}//EOF:end class SpritePanel
