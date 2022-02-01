package ppPackage;																	//Library import
import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.GObject;
import acm.graphics.GOval;


public class ppBall extends Thread{

	// Instance variables
	private double Xinit; 															// Initial position of ball - X
	private double Yinit; 															// Initial position of ball - Y
	private double Vo; 																// Initial velocity (Magnitude)
	private double theta; 															// Initial direction
	private double loss; 															// Energy loss on collision
	private Color color; 															// Color of ball
	private ppTable table; 															// Instance of ping-pong table
	GOval myBall; 																	// Graphics object representing ball
	
	/**
	* The constructor for the ppBall class copies parameters to instance variables, creates an
	* instance of a GOval to represent the ping-pong ball, and adds it to the display.
	*
	* @param Xinit - starting position of the ball X (meters)
	* @param Yinit - starting position of the ball Y (meters)
	* @param Vo - initial velocity (meters/second)
	* @param theta - initial angle to the horizontal (degrees)
	* @param color - ball color (Color)
	* @param loss - loss on collision ([0,1])
	* @param table - a reference to the ppTable class used to manage the display
	*/
	
	public ppBall(double Xinit, double Yinit, double Vo, double theta, Color color, double loss, ppTable table) {
		this.Xinit=Xinit; 															// Copy constructor parameters to instance variables
		this.Yinit=Yinit;
		this.Vo=Vo;
		this.theta=theta;
		this.color=color;
		this.loss=loss;
		this.table=table;
		myBall = new GOval(SCALE*Xinit, SCALE*Yinit, 0.04*SCALE, 0.04*SCALE);		//myBall initialization and arguments
		myBall.setFilled(true);
		myBall.setFillColor(color);
		
		}

	
	public void run() {
		
		
		// Initialize simulation parameters
		boolean hasEnergy; 															// Energy true/false
		double time = 0; 															// Time (reset at each interval)
		double Vt = bMass*g/(4*Pi*bSize*bSize*k); 									// Terminal velocity
		double KEx=ETHR,KEy=ETHR; 													// Kinetic energy in X and Y directions
		double Xo, X, Vx; 															// X position and velocity variables
		double Yo, Y, Vy; 															// Y position and velocity variables
		double Vox = Vo*Math.cos(theta*Pi/180); 									// Initial velocity component in X 
		double Voy = Vo*Math.sin(theta*Pi/180);										// Initial velocity component in Y
		double PE = 0;																// Potential energy variable
		double ScrX = SCALE*Xinit; 													// Position of the ball in X
		double ScrY = SCALE*Yinit;													// Position of the ball in Y
		
		
		// Main simulation loop
		Xo=Xinit; 																	// Initial X offset
		Yo=Yinit; 																	// Initial Y offset
		hasEnergy=true;																// Initial energy argument
		
		while (hasEnergy) { 														// Simulation loop
		X = Vox*Vt/g*(1-Math.exp(-g*time/Vt));										// Updates X position parameter
		Y = Vt/g*(Voy+Vt)*(1-Math.exp(-g*time/Vt))-Vt*time;							// Updates Y position parameter
		Vx = Vox*Math.exp(-g*time/Vt);												// Updates X velocity parameter
		Vy = (Voy+Vt)*Math.exp(-g*time/Vt)-Vt;										// Updates Y velocity parameter
		time += TICK;																// Increment time
		
			if (Vy < 0 && Yo+Y <= bSize) {											// Ground collision check
				
				KEx=0.5*bMass*Vx*Vx*(1-loss);										// Kinetic and potential energy calculations
				KEy=0.5*bMass*Vy*Vy*(1-loss);
				PE=0;
				Vox=Math.sqrt(2*KEx/bMass);											// Initial velocity calculations
				Voy=Math.sqrt(2*KEy/bMass);
				time=0; 															// Time reset
				Xo+=X; 																// Initial X position reset
				Yo=bSize; 															// Initial Y position reset
				X=0; 																// Change in X reset
				Y=0; 																// Change in Y reset
				if (Vx<0) Vox=-Vox;													// Y speed sign check
				
				}
			
			if (Vx > 0 && Xo+X >= (XRWALL- bSize)) {								// Right wall collision check
				
				KEx=0.5*bMass*Vx*Vx*(1-loss);										// Kinetic and potential energy calculations
				KEy=0.5*bMass*Vy*Vy*(1-loss);
				PE=bMass*g*Y;
				Vox=-Math.sqrt(2*KEx/bMass);										// Initial velocity calculations
				Voy=Math.sqrt(2*KEy/bMass);
				time=0; 															// Time reset
				Xo=XMAX-bSize; 														// Initial X position reset
				Yo+=Y; 																// Initial Y position reset
				X=0; 																// Change in X reset
				Y=0; 																// Change in Y reset
				if (Vy<0) Voy=-Voy;													// Y speed sign check
				
				}
			
			if (Vx < 0 &&  Xo+X <= (XINIT)) {										// Left wall collision check
				
				KEx=0.5*bMass*Vx*Vx*(1-loss);										// Kinetic and potential energy calculations
				KEy=0.5*bMass*Vy*Vy*(1-loss);
				PE=bMass*g*Y;
				Vox=Math.sqrt(2*KEx/bMass);											// Initial velocity calculations
				Voy=Math.sqrt(2*KEy/bMass);
				time=0; 															// Time reset
				Xo=XINIT;														// Initial X position reset
				Yo+=Y; 																// Initial Y position reset
				X=0; 																// Change in X reset
				Y=0; 																// Change in Y reset
				if (Vy<0) Voy=-Voy;													// Y speed sign check
				
				}
			
			
			// Update and display
			ScrX = (table.toScrX(Xo+X-bSize));										// Ball X coordinate change
			ScrY = (scrHEIGHT-table.toScrY((Yo+Y+bSize)));							// Ball Y coordinate change
			table.getDisplay().add(new GOval(ScrX+(0.02*SCALE), ScrY+(0.02*SCALE), PD, PD) );		// Tracer insertion
			myBall.setLocation(ScrX,ScrY);											// Ball movement
			
			
			// Pause display
			table.getDisplay().pause(TICK*1500);
	
			
			// Prints variable values
			if (DEBUG)												
				System.out.printf("t: %.2f X: %.2f Y: %.2f Vx: %.2f Vy:%.2f\n",
				time,Xo+X,Y,Vx,Vy);
			
			
			// Breaks loop when total energy is less than the threshold
			if ((KEx+KEy+PE)< ETHR) break;							
			
			}
					
		}
	
	
		public GObject getBall() {					//Used to return graphics objects, such as GOval, created within a ppBall instance.
			return myBall; 
		
	}

}
