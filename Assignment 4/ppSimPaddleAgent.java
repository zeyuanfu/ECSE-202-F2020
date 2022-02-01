package ppPackage;
import static ppPackage.ppSimParams.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

@SuppressWarnings("serial")
public class ppSimPaddleAgent extends GraphicsProgram {

	// Main class
	public static void main(String[] args) { 
			new ppSimPaddleAgent().start(args);
	}
	 		
		// Variable declaration
	 	ppBall myBall;
		ppPaddle myPaddle;
		ppPaddleAgent myPaddleAgent;
		ppTable myTable;
		RandomGenerator rgen;
		JToggleButton Trace;
		
		
		// Run method
		public void init() {
			
			// Add buttons to canvas
			add (new JButton("Clear"), SOUTH);
			add (new JButton("New Serve"), SOUTH);
			JToggleButton Trace = new JToggleButton("Trace");
			this.Trace = Trace;
			add(Trace, SOUTH);
			Trace.setSelected(true);
			add (new JButton("Quit"), SOUTH);
			
			this.resize(scrWIDTH+OFFSET,scrHEIGHT+OFFSET); 										// Initialization of window size
			addMouseListeners(); 																// Mouse detection
			addActionListeners();
			
			// RNG setup
			RandomGenerator rgen = RandomGenerator.getInstance();
			this.rgen=rgen;
			rgen.setSeed(RSEED);
			
			myTable = new ppTable(this); 														// Table call
			myBall = newBall(); 																// Ball call
			
			myPaddle = new ppPaddle(ppPaddleXinit,ppPaddleYinit,ColorPaddle,myTable);			// Paddle and agent call
			myPaddleAgent = new ppPaddleAgent(ppAgentXinit,ppAgentYinit,ColorAgent,myTable);
			
			// Setters for paddle and agent
			myBall.setPaddle(myPaddle);
			myBall.setAgent(myPaddleAgent);
			myPaddleAgent.attachBall(myBall);
			
			pause(1000); 
			add(myBall.getBall()); 
			
			myPaddleAgent.start();
			myPaddle.start();
			myBall.start();
			
		}
		
		
		// Mouse detection
		public void mouseMoved(MouseEvent e) {
			myPaddle.setY(myTable.ScrtoY((double)e.getY()));
		}
		
		
		// Button detection
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if (command.equals("Clear")) {
				myBall.running=false;
				myTable.newScreen();
		}
			
			else if (command.equals("New Serve")) {
				if (!(myBall.ballInPlay())) {
					myTable.newScreen();
					newBall();
					myPaddle = new ppPaddle(ppPaddleXinit,ppPaddleYinit,ColorPaddle,myTable);
					myPaddleAgent = new ppPaddleAgent(ppAgentXinit,ppAgentYinit,ColorAgent,myTable);
					myBall.setPaddle(myPaddle);
					myBall.setAgent(myPaddleAgent);
					myPaddleAgent.attachBall(myBall);
					myPaddleAgent.start();
					myPaddle.start();
					myBall.start();
				}
			}
			
							
			else if (command.equals("Quit")) {
				System.exit(0);
			}
		}
		
		
		// Method to create a new ball
		ppBall newBall() {
			Color iColor = Color.RED; 
			double iYinit = rgen.nextDouble(YinitMIN,YinitMAX); 
			double iLoss = rgen.nextDouble(EMIN,EMAX); 
			double iVel = rgen.nextDouble(VoMIN,VoMAX);
			double iTheta = rgen.nextDouble(ThetaMIN,ThetaMAX);
			double Xinit = XINIT;
			
			myBall = new ppBall(XINIT,iYinit,iVel,iTheta,iColor,iLoss,myTable,Trace.isSelected());
		
			return myBall;
			}
		
}
