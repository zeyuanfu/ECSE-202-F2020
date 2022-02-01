package ppPackage;
import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.GRect;


public class ppTable {

	// Instance Variables
	ppSimPaddleAgent dispRef;
	ppTable myTable;

	
	// The table is created in the constructor
	public ppTable(ppSimPaddleAgent dispRef) {
		this.dispRef=dispRef;

		// Create the bottom border
		GRect bottomBorder = new GRect(0,scrHEIGHT,scrWIDTH+OFFSET,3); 				// A thick line HEIGHT pixels down from the top
			bottomBorder.setColor(Color.BLACK);
			bottomBorder.setFilled(true);
			dispRef.add(bottomBorder);
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
	
	
	void newScreen() {
		dispRef.removeAll();
		myTable = new ppTable (dispRef);
	}
	
	
	ppSimPaddleAgent getDisplay() { 												// Reference to display
		return dispRef;
	}
		
}