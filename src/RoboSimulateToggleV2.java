
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//This agent only uses Sonar as a way to determine the actions it performs. 
public class RoboSimulateToggleV2 extends Thread {

	private EnvironmentModel model;
	private boolean nextLeft = false;
	private int cycle = 0;
	BufferedWriter bw;
	
	public RoboSimulateToggleV2(EnvironmentModel mdl, String s) {
		this.model = mdl;
		try {
			bw = new BufferedWriter(new FileWriter("NewData/log_toggle" + s + ".txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String[][] brd = new String[50][50];

		try {
			BufferedReader br = new BufferedReader(new FileReader("DATA/map" + args[0] + ".txt"));
			String line = null;
			int ii = 0;
			while((line = br.readLine()) != null){
				for(int jj=0;jj<line.length();jj++){
					brd[49- ii][49 - jj] = line.charAt(jj) + "";
					//brd[ii][jj] = line.charAt(jj) + "";
				}
				ii++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EnvironmentModel mdl = new EnvironmentModel(brd);
		//GUIView gui = new GUIView(mdl);
		RoboSimulateToggleV2 robo = new RoboSimulateToggleV2(mdl, args[0]);

		boolean run = true;
		while(run){
			run = robo.move();
		}
		
		//gui.close();
	}

	
	public boolean move(){
		
		double sonar = this.model.getSonar();
		double touch = this.model.getTouch();
		double sound = this.model.getSound();
		
		String toout = sonar + "," + touch + "," + sound + "," + cycle + ",";
				
		if(sonar < 1.0){
			toout += "B";
			model.backward();
		}else if(sonar < 2.0){
			toout += "R";
			model.reverse();
		}else{
			if(sonar < 3.0){
				if(nextLeft){
					toout += "LE";
					model.turnLeft();
					nextLeft = false;
				}else{
					toout += "RI";
					model.turnRight();
					nextLeft = true;
				}
			}else{
				toout += "F";
				model.forward();
			}
		}
		
		try {
			bw.write(toout + "\n");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.cycle++;
		if(cycle == 2500){
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		/**
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return true;
	}	
}
