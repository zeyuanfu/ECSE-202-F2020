package ppPackage;														//Library import
import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

@SuppressWarnings("serial")
public class ppSim extends GraphicsProgram {

	public void run() {
		this.resize(scrWIDTH+OFFSET,scrHEIGHT+OFFSET);					//Screen resize
		
		ppTable myTable = new ppTable(this);							//Table call
		
		RandomGenerator rgen = RandomGenerator.getInstance();			//RNG call and seed argument
		rgen.setSeed(RSEED);
		
		for (int i=0; i<NUMBALLS; i++) {								//10x iterations of ball generation
	
			Color iColor = rgen.nextColor();
			double iYinit = rgen.nextDouble(YinitMIN, YinitMAX);
			double iLoss = rgen.nextDouble(EMIN, EMAX);
			double iVel = rgen.nextDouble(VoMIN, VoMAX);
			double iTheta = rgen.nextDouble(ThetaMIN, ThetaMAX);
		
			ppBall iBall = new ppBall(XINIT,iYinit,iVel,iTheta,iColor,iLoss,myTable);
			add(iBall.getBall());
			iBall.start();
						
		}
		
	}
	
}