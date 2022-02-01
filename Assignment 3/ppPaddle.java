package ppPackage;
import static ppPackage.ppSimParams.*;
import acm.graphics.GRect;


/**
 * The paddle object creates a black rectangle on the screen corresponding
 * to a ping-pong paddle. X position is fixed at creation, but paddle can move
 * in Y. Dimensions are fixed parameters.
 *
 */


public class ppPaddle extends Thread {

	// Instance variables
	double X; 																	// Paddle X location
	double Y; 																	// Paddle Y location
	double Vx; 																	// Paddle velocity in X
	double Vy; 																	// Paddle velocity in Y
	double lastX; 																// X position on previous cycle
	double lastY; 																// Y position on previous cycle
	ppTable myTable;															// Instance of pp table.
	GRect myPaddle; 															// GRect implements paddle

	
	// Constructor argument
	public ppPaddle (double X, double Y, ppTable myTable) {	
		this.X=X;
		this.Y=Y;
		this.myTable=myTable;
		lastX=X;
		lastY=Y;

		
		// Create screen display for paddle
		double ScrX = myTable.toScrX(X-ppPaddleW/2);
		double ScrY = myTable.toScrY(Y+ppPaddleH/2);
		myPaddle = new GRect(ScrX,ScrY,ppPaddleW*SCALE,ppPaddleH*SCALE);
		myPaddle.setFilled(true);
		myTable.getDisplay().add(myPaddle);
	}
	
	
	// Velocity calculation and update
	public void run() {
		while (true) {
			Vx=(X-lastX)/TICK;
			Vy=(Y-lastY)/TICK;
			lastX=X;
			lastY=Y;
			myTable.getDisplay().pause(TICK*TIMESCALE); 						// Convert time units to mS
		}

	}
	
	
/**
 * Contact - a method to determine if the surface of the ball is in contact
 * with the surface of the paddle.
 * @param Sx - X coordinate of contacting surface
 * @param Sy - Y coordinate of contacting surface
 * @return - true of in contact, false otherwise.
 */
	
	
	public boolean contact(double Sx, double Sy) {
		return (Sy>=(Y-ppPaddleH/2) && Sy<=(Y+ppPaddleH/2));
	}
	
	
/**
 * getVx
 * @return double - X velocity component of paddle
 */
	
	
	public double getVx() {
		return Vx;
	}
	
	
/**
 * getVy
 * @return double - X velocity component of paddle
 */
	
	
	public double getVy() {
		return Vy;
	}
	
	
/**
 * setX - update position, move on screen
 * @return void
 */
	
	
	public void setX(double X) {
		this.X=X;
		double ScrX = myTable.toScrX(this.X-ppPaddleW/2);
		double ScrY = myTable.toScrY(this.Y+ppPaddleH/2);
		myPaddle.setLocation(ScrX,ScrY); 
	}

	
/**
 * setY
 * @return void
 */

	
	public void setY(double Y) {
		this.Y=Y;
		double ScrX = myTable.toScrX(this.X-ppPaddleW/2);
		double ScrY = myTable.toScrY(this.Y+ppPaddleH/2);
		myPaddle.setLocation(ScrX,ScrY); 
	}
	
	
/**
 * getX
 * @return double X
 */
	
	
	public double getX() {
		return X;
	}
	
	
/**
 * getX
 * @return double X
 */
	

	public double getY() {
		return Y;
	}
	
	
/**
 * getSgnVy()
 * @return double, sign of Vy
 */
	
	
	public double getSgnVy() {
		if (Vy<0)
			return -1.0;
		else
			return 1.0;
	}
	
}