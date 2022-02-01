package ppPackage;
import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.GRect;


/**
 * The ppTable class is responsible for creating the initial display and
 * providing utility methods for converting from world to display coordinates.
 *
 */


public class ppTable {

	// Instance Variables
	ppSimPaddle dispRef;

	
	// The table is created in the constructor
	public ppTable(ppSimPaddle dispRef) {
		this.dispRef=dispRef;


		// Create the bottom border
		GRect bottomBorder = new GRect(0,scrHEIGHT,scrWIDTH+OFFSET,3); 				// A thick line HEIGHT pixels down from the top
			bottomBorder.setColor(Color.BLACK);
			bottomBorder.setFilled(true);
			dispRef.add(bottomBorder);

		// Create a wall on the left end of the display
		GRect leftBorder = new GRect(XLWALL*SCALE,0,3,scrHEIGHT); 					// Line drawn from top to ground plane
			leftBorder.setColor(Color.BLUE);
			leftBorder.setFilled(true);
			dispRef.add(leftBorder);
	}
	
	
	// Utility methods
	double toScrX(double X) { 														// X to Screen X
		return X*SCALE;
	}
	
	
	double toScrY(double Y) { 														// Y to Screen Y
		return scrHEIGHT - Y*SCALE; 												// Sy = SH - Y*SCALE
	}
	
	
	double ScrtoX(double ScrX) { 													// ScrX to X
		return ScrX/SCALE;
	}
	
	
	double ScrtoY(double ScrY) {
		return (scrHEIGHT-ScrY)/SCALE;
	}
	
	
	ppSimPaddle getDisplay() { 														// Reference to display
		return dispRef;
	}
		
}