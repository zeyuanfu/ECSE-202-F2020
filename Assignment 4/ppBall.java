package ppPackage;
import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.GObject;
import acm.graphics.GOval;


public class ppBall extends Thread {
	
	// Instance variables
	private double Xinit; 												// Initial position of ball - X
	private double Yinit; 												// Initial position of ball - Y
	private double Vo; 													// Initial velocity (Magnitude)
	private double theta; 												// Initial direction
	private double loss; 												// Energy loss on collision
	@SuppressWarnings("unused")
	private Color color; 												// Color of ball
	private ppTable table; 												// Instance of ping-pong table
	private ppPaddle myPaddle; 											// Instance of ping-pong paddle
	private ppPaddleAgent myPaddleAgent;
	private boolean traceOn;
	boolean running = true;
	GOval myBall; 														// Graphics object representing ball

	
	
	// Initialize instance variables and create an instance of the pp ball
	public ppBall(double Xinit, double Yinit, double Vo, double theta, Color color, double loss, ppTable table, boolean traceOn) {
		this.Xinit=Xinit; 		// Copy constructor parameters to instance variables
		this.Yinit=Yinit;
		this.Vo=Vo;
		this.theta=theta;
		this.color=color;
		this.loss=loss;
		this.table=table;
		this.traceOn=traceOn;
					

		double ScrX = table.toScrX(Xinit-bSize); 						// Cartesian to screen
		double ScrY = table.toScrY(Yinit-bSize);
		myBall = new GOval(ScrX,ScrY,2*bSize*SCALE,2*bSize*SCALE);
		myBall.setColor(color);
		myBall.setFilled(true);
		table.getDisplay().add(myBall);
	 }
	
	
	// Entry point is the run method
	public void run() {
		
		// Initialize simulation parameters
		double time = 0; 												// Time (reset at each interval)
		double Vt = bMass*g / (4*Pi*bSize*bSize*k); 					// Terminal velocity
		double KEx=ETHR,KEy=ETHR; 										// Kinetic energy in X and Y directions
		double PE=ETHR; 												// Potential energy
		double Xo, X, Vx; 												// X position and velocity variables
		double Yo, Y, Vy; 												// Y position and velocity variables

		double Vox=Vo*Math.cos(theta*Pi/180); 							// Initial velocity components in X and Y
		double Voy=Vo*Math.sin(theta*Pi/180);

		Xo=Xinit; 														// Initial X offset
		Yo=Yinit; 														// Initial Y offset
		double ScrX, ScrY;
		
		
		// Main Simulation Loop
		while (running) {

			X = Vox*Vt/g*(1-Math.exp(-g*time/Vt)); 						// Update relative position
			Y = Vt/g*(Voy+Vt)*(1-Math.exp(-g*time/Vt))-Vt*time;
			Vx = Vox*Math.exp(-g*time/Vt);
			Vy = (Voy+Vt)*Math.exp(-g*time/Vt)-Vt;
 
		
		// Collision with ground
		if (Vy<0 && (Yo+Y)<=bSize) {
			KEx = Math.min(0.5*bMass*Vx*Vx*(1-loss), KEmax); 							// Kinetic energy in X direction after collision
			KEy = Math.min(0.5*bMass*Vy*Vy*(1-loss), KEmax); 							// Kinetic energy in Y direction after collision
			PE = 0; 													// Potential energy (at ground)
			Vox = Math.sqrt(2*KEx/bMass); 								// Resulting horizontal velocity
			Voy = Math.sqrt(2*KEy/bMass); 								// Resulting vertical velocity
			if (Vx<0) Vox=-Vox; 										// Preserve sign of Vox

			time=0; 													// Reset current interval time
			Xo+=X; 														// Update X and Y offsets
			Yo=bSize;
			X=0; 														// Reset X and Y for next iteration
			Y=0;
			if ((KEx+KEy+PE)<ETHR) running=false;						 // Terminate if insufficient energy	
		}
		
		
		// Collision with paddle.
		if (Vx>0 && (Xo+X)>=myPaddle.getX()-bSize-ppPaddleW/2) {
		if (myPaddle.contact(Xo+X,Yo+Y)) { 								// Ball contacts paddle

			KEx = Math.min(0.5*bMass*Vx*Vx*(1-loss), KEmax); 							// Kinetic energy in X direction after collision
			KEy = Math.min(0.5*bMass*Vy*Vy*(1-loss), KEmax); 							// Kinetic energy in Y direction after collision
 
			PE = bMass*g*Y; 											// Potential energy
			Vox = -Math.sqrt(2*KEx/bMass); 								// Resulting horizontal velocity
			Voy = Math.sqrt(2*KEy/bMass); 								// Resulting vertical velocity

																		// Boost Vx and Vy of ball on paddle hit
			Voy=Voy*ppPaddleYgain*myPaddle.getSgnVy(); 					// Take motion of paddle into account
			Vox=Vox*ppPaddleXgain; 										// And constant boost to Vx

			// Reset parameters for next parabolic trajectory
			time=0; 													// Reset current interval time
			Xo=myPaddle.getX()-bSize-ppPaddleW/2; 						// Update X and Y offsets
			Yo+=Y;
			X=0;														// Reset X to zero for start of next interval
			Y=0;
		}

		
		// Ball misses paddle
		else {
			System.out.println("Missed!");
			running=false;
		}
		
	}
  
		
		// Collision with agent
		if (Vx<0 && (Xo+X)<=ppAgentXinit+ppPaddleW+bSize) {
			KEx = Math.min(0.5*bMass*Vx*Vx*(1-loss), KEmax); 							// Kinetic energy in X direction after collision
			KEy = Math.min(0.5*bMass*Vy*Vy*(1-loss), KEmax); 							// Kinetic energy in Y direction after collision
			PE = bMass*g*Y; 											// Potential energy
			Vox = Math.sqrt(2*KEx/bMass); 								// Resulting horizontal velocity
			Voy = Math.sqrt(2*KEy/bMass); 								// Resulting vertical velocity
			if (Vy<0) Voy=-Voy; 										// Preserve sign of Voy
			Vox=Vox*ppPaddleXgain;
			
			time=0; 													// Reset current interval time
			Xo=ppAgentXinit+ppPaddleW+bSize; 											// Update X and Y offsets
			Yo+=Y;
			X=0; 														// Reset X to zero for start of next interval
			Y=0;
		}
		
		
		// Update ball position on screen
		ScrX = table.toScrX(Xo+X-bSize); 								// Convert to screen units
		ScrY = table.toScrY(Yo+Y+bSize);
		myBall.setLocation(ScrX,ScrY); 									// Change position of ball in display
		myPaddleAgent.setY(table.ScrtoY(ScrY));
		
		
		// Tracer insertion
		if (traceOn) {
		table.getDisplay().add(new GOval(ScrX+(0.02*SCALE), ScrY+(0.02*SCALE), PD, PD) );		
		}


		// Delay and update clocks
		table.getDisplay().pause(TICK*TIMESCALE); 						// Convert time units to mS
		time+=TICK; 													// Update time
		
		}
	}
	
	
	public GObject getBall() {
		return myBall;
	}
	
	
	public boolean ballInPlay() {
		if (running) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	
	// Paddle setter
	public void setPaddle (ppPaddle myPaddle) {
		this.myPaddle=myPaddle;
	}
	
	
	// Agent setter
	public void setAgent (ppPaddleAgent myPaddleAgent) {
		this.myPaddleAgent=myPaddleAgent;
	}

}
