import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;


public class CBRPerformanceTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(args[0] + "999.txt"));
			
			String linein = null;
			
			ArrayList<RoboCase> casebase = new ArrayList<RoboCase>();
			
			while((linein = br.readLine()) != null){
				casebase.add(new RoboCase(linein));
			}
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(args[0] + "_performance.txt"));
			//BufferedWriter exeTime = new BufferedWriter(new FileWriter("exeTime.txt"));
			
			for(int ii=1; ii<=25; ii++){
				BufferedReader testfile = new BufferedReader(new FileReader(args[0] + ii + ".txt"));
				
				ArrayList<RoboCase> testbase = new ArrayList<RoboCase>();
				linein = null;
				while((linein = testfile.readLine()) != null){
					testbase.add(new RoboCase(linein));
				}
				
				int numFor = 0;
				int corFor = 0;
				int numBack = 0;
				int corBack = 0;
				int numLeft = 0;
				int corLeft = 0;
				int numRight = 0;
				int corRight = 0;
				int numRev = 0;
				int corRev = 0;
				
				for(RoboCase tester: testbase){
					if(tester.getAction().equals("F")){
						numFor++;
					}else if(tester.getAction().equals("LE")){
						numLeft++;
					}else if(tester.getAction().equals("RI")){
						numRight++;
					}else if(tester.getAction().equals("R")){
						numRev++;
					}else if(tester.getAction().equals("B")){
						numBack++;
					}else{
						System.out.println("Was not expecting: " + tester.getAction());
					}
					
					double bestSim = -1;
					String bestAct = "";
					
					long startTime = System.currentTimeMillis();
					for(RoboCase training: casebase){
						double sim = tester.sim(training);
						if(sim > bestSim){
							bestSim = sim;
							bestAct = training.getAction();
						}
					}
					long endTime = System.currentTimeMillis();
					
					long time = endTime - startTime;
					//exeTime.write(time + "\n");
					
					if(tester.getAction().equals(bestAct)){
						if(tester.getAction().equals("F")){
							corFor++;
						}else if(tester.getAction().equals("LE")){
							corLeft++;
						}else if(tester.getAction().equals("RI")){
							corRight++;
						}else if(tester.getAction().equals("R")){
							corRev++;
						}else if(tester.getAction().equals("B")){
							corBack++;
						}else{
							System.out.println("Was not expecting: " + tester.getAction());
						}
					}
				}
				
				double accFor = 0;
				double accBack = 0;
				double accLeft = 0;
				double accRight = 0;
				double accRev = 0;
				
				if(numFor == 0){
					accFor = 1.0;
				}else{
					accFor = (double)corFor/(double)numFor;
				}
				
				if(numBack == 0){
					accBack = 1.0;
				}else{
					accBack = (double)corBack/(double)numBack;
				}
				
				if(numLeft == 0){
					accLeft = 1.0;
				}else{
					accLeft = (double)corLeft/(double)numLeft;
				}
				
				if(numRight == 0){
					accRight = 1.0;
				}else{
					accRight = (double)corRight/(double)numRight;
				}
				
				if(numRev == 0){
					accRev = 1.0;
				}else{
					accRev = (double)corRev/(double)numRev;
				}
				
				bw.write(accFor + "," + accBack + "," + accLeft + "," + accRight + "," + accRev + "\n");
				testfile.close();
			}
			
			br.close();
			bw.close();
			//exeTime.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

}
