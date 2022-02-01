package ppPackage;

public class ppSimParams {

	// 1. Parameters defined in screen coordinates (pixels, acm coordinates)
	static final int scrWIDTH = 1080; 						// n.b. screen coordinates
	static final int scrHEIGHT = 600;
	static final int OFFSET = 200;

	// 2. Parameters defined in simulation coordinates (MKS, x-->range, y-->height)
	static final double g = 9.8; 							// MKS
	static final double k = 0.1316; 						// Terminal velocity constant
	static final double Pi = 3.1416;						// Pi constant
	static final double XMAX = 2.74; 						// Maximum value of X (pp table)
	static final double YMAX = 1.52; 						// Maximum value of Y (height above table)
	
	static final double XLWALL = 0.1; 						// position of left wall
	static final double XRWALL = XMAX; 						// position of right wall
	static final double bSize = 0.02; 						// pp ball radius
	static final double bMass = 0.0027; 					// pp ball mass
	static final double XINIT = XLWALL+bSize; 				// Initial ball location (X)
	static final double TICK = 0.01; 						// Clock tick duration (sec)
	static final double ETHR = 0.001; 						// Minimum ball energy needed to move
	static final double YINIT = YMAX/2; 					// Initial ball location (Y)
	static final double PD = 1; 							// Trace point diameter
	static final double SCALE = scrHEIGHT/YMAX; 			// Pixels/meter
	
	// 3. Parameters used by the ppSim (main) class
	static final int NUMBALLS = 10; 						// # pp balls to simulate
	static final double YinitMAX = 0.75*YMAX; 				// Max inital height at 75% of range
	static final double YinitMIN = 0.25*YMAX; 				// Min inital height at 25% of range
	static final double EMIN = 0.2; 						// Minimum loss coefficient
	static final double EMAX = 0.4; 						// Maximum loss coefficient
	static final double VoMIN = 1.0; 						// Minimum velocity
	static final double VoMAX = 15.0; 						// Maximum velocity
	static final double ThetaMIN = 0.0; 					// Minimum launch angle
	static final double ThetaMAX = 20.0; 					// Maximum launch angle
	
	// 4. Miscellaneous
	static final boolean DEBUG = true; 						// Enable print to console
	static final long RSEED = 8976232; 						// Random number generator seed value

	}
