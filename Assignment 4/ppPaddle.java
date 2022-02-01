package ppPackage;
import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.GRect;


public class ppPaddle extends Thread {

	// Instance variables
	double X; 																	// Paddle X location
	double Y; 																	// Paddle Y location
	double Vx; 																	// Paddle velocity in X
	double Vy; 																	// Paddle velocity in Y
	double lastX; 																// X position on previous cycle
	double lastY; 																// Y position on previous cycle
	Color myColor;
	ppTable myTable;															// Instance of pp table.
	GRect myPaddle; 															// GRect implements paddle

	
	// Constructor argument
	public ppPaddle (double X, double Y, Color myColor, ppTable myTable) {	
		this.X=X;
		this.Y=Y;
		this.myColor=myColor;
		this.myTable=myTable;
		lastX=X;
		lastY=Y;
			
		// Create screen display for paddle
		double ScrX = myTable.toScrX(X-ppPaddleW/2);
		double ScrY = myTable.toScrY(Y+ppPaddleH/2);
		myPaddle = new GRect(ScrX,ScrY,ppPaddleW*SCALE,ppPaddleH*SCALE);
		myPaddle.setFilled(true);
		myPaddle.setFillColor(myColor);
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
	
	
	// Method to determine if the ball is in contact with the paddle
	public boolean contact(double Sx, double Sy) {
		return (Sy>=(Y-ppPaddleH/2) && Sy<=(Y+ppPaddleH/2));
	}
	
	
	// Returns the X velocity of the paddle
	public double getVx() {
		return Vx;
	}
	
	
	// Returns the Y velocity of the paddle
	public double getVy() {
		return Vy;
	}
	
	
	// Updates X position of the paddle
	public void setX(double X) {
		this.X=X;
		double ScrX = myTable.toScrX(this.X-ppPaddleW/2);
		double ScrY = myTable.toScrY(this.Y+ppPaddleH/2);
		myPaddle.setLocation(ScrX,ScrY); 
	}

	
	// Updates Y position of the paddle
	public void setY(double Y) {
		this.Y=Y;
		double ScrX = myTable.toScrX(this.X-ppPaddleW/2);
		double ScrY = myTable.toScrY(this.Y+ppPaddleH/2);
		myPaddle.setLocation(ScrX,ScrY); 
	}
	
	
	// Returns X coordinate of paddle
	public double getX() {
		return X;
	}
	
	
	// Returns Y coordinate of paddle
	public double getY() {
		return Y;
	}
	
	
	// Returns the sign of the speed of the paddle in Y
	public double getSgnVy() {
		if (Vy<0)
			return -1.0;
		else
			return 1.0;
	}
	
}