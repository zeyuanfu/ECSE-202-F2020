package ppPackage;
import static ppPackage.ppSimParams.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;


/**
 * The ppSimPaddle class implements a simulation of a single paddle and a ping-pong ball.
 * The paddle, located on the right side of the screen, is constrained to moving up and down.
 * The ball can be volleyed with the left wall.
 *
 * ppPaddle creates an instance of a ping-pong ball paddle that exports
 * methods for tracking paddle velocity, determining contact with
 * a surface at a point, and setting paddle location.
 *
 */


@SuppressWarnings("serial")
public class ppSimPaddle extends GraphicsProgram {
	
	
	// Main class
	public static void main(String[] args) { 
		new ppSimPaddle().start(args);
	}
 
	
	// Variable declaration
 	ppBall myBall;
	ppPaddle myPaddle;
	ppTable myTable;

		
	// Run method
	public void init() {
		this.resize(scrWIDTH+OFFSET,scrHEIGHT+OFFSET); 										// Initialization of window size
		addMouseListeners(); 																// Mouse detection

		
		// Display setup
		myTable = new ppTable(this); 														// Table call

		
		// RNG setup
		RandomGenerator rgen = RandomGenerator.getInstance();
		rgen.setSeed(RSEED);
		
		
		// Paddle and ball creation
		myPaddle = new ppPaddle(ppPaddleXinit,ppPaddleYinit,myTable); 						// Paddle call and parameters
		Color iColor = Color.RED; 
		double iYinit = rgen.nextDouble(YinitMIN,YinitMAX); 
		double iLoss = rgen.nextDouble(EMIN,EMAX); 
		double iVel = rgen.nextDouble(VoMIN,VoMAX);
		double iTheta = rgen.nextDouble(ThetaMIN,ThetaMAX); 


		// Ball generation
		myBall = new ppBall(XINIT,iYinit,iVel,iTheta,iColor,iLoss,myTable,myPaddle);
		pause(1000); 
		myBall.start(); 
		myPaddle.start();
	}
		
		
		// Mouse handler event
		public void mouseMoved(MouseEvent e) {
			myPaddle.setY(myTable.ScrtoY((double)e.getY()));
		}
}