package bouncingsprites;

import javax.swing.JFrame;

/**
 * Responsible for program intialization and execution
 * @author Todd Kelley
 * @version 1.0b
 */
public class BouncingSprites extends JFrame{

	private static final long serialVersionUID = 42L;
	private SpritePanel panel = new SpritePanel();
	
	public BouncingSprites(){
		this.setName("Bouncing Ball");
		this.setSize(400, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel);
		this.setVisible(true);
	}
	public void start(){
		panel.animate();  // never returns due to infinite loop in animate method

	}

	public static void main(String[] args) {
		new BouncingSprites().start();
	}
}
