package ppPackage;																	//Library import
import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.GLine;
import acm.program.GraphicsProgram;

@SuppressWarnings("serial")
public class ppTable extends GraphicsProgram{
	
	private ppSim dispRef;															//Variable declaration
	
	public ppTable(ppSim dispRef) {													//Constructor argument
		GLine leftBorder = new GLine(SCALE*XLWALL,0,SCALE*XLWALL,600);	
		leftBorder.setColor(Color.BLUE);
		GLine rightBorder = new GLine (SCALE*XRWALL,0,SCALE*XRWALL,600);				
		rightBorder.setColor(Color.RED);
		GLine bottomBorder = new GLine (0,600,1280,600);				 
		dispRef.add(leftBorder);
		dispRef.add(rightBorder);
		dispRef.add(bottomBorder);
		this.dispRef=dispRef;														//Variable initialization
		
		}
	
	
	double toScrX(double X) {														//Conversion methods
		return (X*SCALE);
	}
	
	double toScrY(double Y) {
		return (Y*SCALE);
	}
	
	ppSim getDisplay() {
		return dispRef;
	}

}
