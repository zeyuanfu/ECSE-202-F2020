package ppPackage;
import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.GRect;


public class ppPaddleAgent extends Thread {

	// Instance variables
	double X; 																	// Paddle X location
	double Y; 																	// Paddle Y location
	Color myColor;
	ppTable myTable;															// Instance of pp table.
	ppBall myBall;
	GRect myPaddleAgent; 														// GRect implements paddle

	
	// Constructor argument
	public ppPaddleAgent (double X, double Y, Color myColor, ppTable myTable) {	
		this.X=X;
		this.Y=Y;
		this.myColor=myColor;
		this.myTable=myTable;
				
		// Create screen display for paddle
		double ScrX = myTable.toScrX(X-ppPaddleW/2);
		double ScrY = myTable.toScrY(Y+ppPaddleH/2);
		myPaddleAgent = new GRect(ScrX,ScrY,ppPaddleW*SCALE,ppPaddleH*SCALE);
		myPaddleAgent.setFilled(true);
		myPaddleAgent.setFillColor(myColor);
		myTable.getDisplay().add(myPaddleAgent);
	}
		
	
	public void setX(double X) {
		this.X=X;
		double ScrX = myTable.toScrX(this.X-ppPaddleW/2);
		double ScrY = myTable.toScrY(this.Y+ppPaddleH/2);
		myPaddleAgent.setLocation(ScrX,ScrY); 
	}	

		
	// Updates the Y position of the agent
	public void setY(double Y) {
		this.Y=Y;
		double ScrX = myTable.toScrX(this.X-ppPaddleW/2);
		double ScrY = myTable.toScrY(this.Y+ppPaddleH/2);
		myPaddleAgent.setLocation(ScrX,ScrY); 
		}
	
	
	public void attachBall(ppBall myBall) {
		this.myBall=myBall;
		}
		
	}
