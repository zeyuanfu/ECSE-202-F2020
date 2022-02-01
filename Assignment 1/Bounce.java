import java.awt.Color;											// Library import arguments
import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;

@SuppressWarnings("serial")
public class Bounce extends GraphicsProgram {					// Class definition argument
	
	private static final int WIDTH = 1280;						// Defines the width of the screen in pixels
	private static final int HEIGHT = 600;						// Distance from top of screen to ground plane
	private static final int OFFSET = 200;						// Distance from bottom of screen to ground plane
	private static final double g = 9.8;						// MKS gravitational constant 9.8 m/s^2
	private static final double k = 0.1316;						// Terminal velocity constant
	private static final double Pi = 3.1416;					// To convert degrees to radians
	private static final double bSize = 0.02;					// Ball radius (m)
	private static final double bMass = 0.0027;					// Ball mass (kg)
	private static final double XMAX = 2.74;					// Maximum value of X
	private static final double YMAX = 1.52; 					// Maximum value of Y
	private static final double Xinit = 0.1; 					// Initial ball location (X)
	private static final double Yinit = YMAX/2;					// Initial ball location (Y)
	private static final double TICK = 0.01; 					// Clock tick duration (seconds)
	private static final double ETHR = 0.001; 					// Energy threshold
	private static final double PD = 1; 						// Trace point diameter
	private static final double SCALE = HEIGHT/YMAX;			// Pixels/meter conversion factor
	private static final boolean TEST = false;					// Prints variables in the console
	
	public void run() {
		
		this.resize(WIDTH+OFFSET,HEIGHT+OFFSET);				// Window resize argument
		
		GLine leftBorder = new GLine(SCALE*Xinit,0,SCALE*Xinit,600);	// Left border creation
		leftBorder.setColor(Color.BLUE);
		GLine rightBorder = new GLine (1080,0,1080,600);				// Right border creation
		rightBorder.setColor(Color.RED);
		GLine bottomBorder = new GLine (0,600,1280,600);				// Bottom border creation 
		GOval ball = new GOval (SCALE*Xinit, SCALE*Yinit, 0.04*SCALE, 0.04*SCALE);		// Ball creation
		ball.setFilled(true);
		ball.setFillColor(Color.RED);
		add(leftBorder);										// Border and ball insertion
		add(rightBorder);
		add(bottomBorder);
		add(ball);
	
		double Vo = readDouble("Enter the inital velocity of the ball in meters/second [0,100]: ");		// User parameter input
		double theta = readDouble("Enter the initial direction in degrees [-90,90]: ");
		double loss = readDouble("Enter the energy loss parameter [0,1]: ");

		boolean hasEnergy; 										// Energy true/false
		double time = 0; 										// Time (reset at each interval)
		double Vt = bMass*g/(4*Pi*bSize*bSize*k); 				// Terminal velocity
		double KEx=ETHR,KEy=ETHR; 								// Kinetic energy in X and Y directions
		double Xo, X, Vx; 										// X position and velocity variables
		double Yo, Y, Vy; 										// Y position and velocity variables
		double Vox = Vo*Math.cos(theta*Pi/180); 				// Initial velocity component in X 
		double Voy = Vo*Math.sin(theta*Pi/180);					// Initial velocity component in Y
		double PE = 0;											// Potential energy variable
		double ScrX = SCALE*Xinit; 								// Position of the ball in X
		double ScrY = SCALE*Yinit;								// Position of the ball in Y
		
		Xo=Xinit; 												// Initial X offset
		Yo=Yinit; 												// Initial Y offset
		hasEnergy=true;											// Initial energy argument
		
		while (hasEnergy) { 									// Simulation loop
		X = Vox*Vt/g*(1-Math.exp(-g*time/Vt));					// Updates X position parameter
		Y = Vt/g*(Voy+Vt)*(1-Math.exp(-g*time/Vt))-Vt*time;		// Updates Y position parameter
		Vx = Vox*Math.exp(-g*time/Vt);							// Updates X velocity parameter
		Vy = (Voy+Vt)*Math.exp(-g*time/Vt)-Vt;					// Updates Y velocity parameter
		time += TICK;											// Increment time
		
			if (Vy < 0 && Yo+Y <= bSize) {						// Ground collision check
				
				KEx=0.5*bMass*Vx*Vx*(1-loss);					// Kinetic and potential energy calculations
				KEy=0.5*bMass*Vy*Vy*(1-loss);
				PE=0;
				Vox=Math.sqrt(2*KEx/bMass);						// Initial velocity calculations
				Voy=Math.sqrt(2*KEy/bMass);
				time=0; 										// Time reset
				Xo+=X; 											// Initial X position reset
				Yo=bSize; 										// Initial Y position reset
				X=0; 											// Change in X reset
				Y=0; 											// Change in Y reset
				if (Vx<0) Vox=-Vox;								// Y speed sign check
				
				}
			
			if (Vx > 0 && Xo+X >= (XMAX-bSize)) {				// Right wall collision check
				
				KEx=0.5*bMass*Vx*Vx*(1-loss);					// Kinetic and potential energy calculations
				KEy=0.5*bMass*Vy*Vy*(1-loss);
				PE=bMass*g*Y;
				Vox=-Math.sqrt(2*KEx/bMass);					// Initial velocity calculations
				Voy=Math.sqrt(2*KEy/bMass);
				time=0; 										// Time reset
				Xo=XMAX-bSize; 									// Initial X position reset
				Yo+=Y; 											// Initial Y position reset
				X=0; 											// Change in X reset
				Y=0; 											// Change in Y reset
				if (Vy<0) Voy=-Voy;								// Y speed sign check
				
				}
			
			if (Vx < 0 &&  Xo+X <= (Xinit+bSize)) {				// Left wall collision check
				
				KEx=0.5*bMass*Vx*Vx*(1-loss);					// Kinetic and potential energy calculations
				KEy=0.5*bMass*Vy*Vy*(1-loss);
				PE=bMass*g*Y;
				Vox=Math.sqrt(2*KEx/bMass);						// Initial velocity calculations
				Voy=Math.sqrt(2*KEy/bMass);
				time=0; 										// Time reset
				Xo=Xinit+bSize;									// Initial X position reset
				Yo+=Y; 											// Initial Y position reset
				X=0; 											// Change in X reset
				Y=0; 											// Change in Y reset
				if (Vy<0) Voy=-Voy;								// Y speed sign check
				
				}
			
		
		ScrX = ((Xo+X-bSize)*SCALE);							// Ball X coordinate change
		ScrY = (HEIGHT-(Yo+Y+bSize)*SCALE);						// Ball Y coordinate change
		add(new GOval(ScrX+(0.02*SCALE), ScrY+(0.02*SCALE), PD, PD) );		// Tracer insertion
		
		ball.setLocation(ScrX,ScrY);							// Ball movement
		
		pause(25);												// Pause argument
		
		if (TEST)												// Prints variable values
			System.out.printf("t: %.2f X: %.2f Y: %.2f Vx: %.2f Vy:%.2f\n",
			time,Xo+X,Y,Vx,Vy);
		
		if ((KEx+KEy+PE)< ETHR) break;							// Breaks loop when total energy is less than the threshold
				
		
		}
		
	}
	
}
