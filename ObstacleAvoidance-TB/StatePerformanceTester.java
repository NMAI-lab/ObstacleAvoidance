import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;


public class StatePerformanceTester {

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
			
			double CPT = Double.valueOf(args[1]);
			double PPT = Double.valueOf(args[2]);
			double PAT = Double.valueOf(args[3]);
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(args[0] + "_performance_state.txt"));
			BufferedWriter lookback = new BufferedWriter(new FileWriter(args[0] + "_lookback.txt"));
			
			for(int ii=1; ii<=25; ii++){
				System.out.println(ii);
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
					
								
					
					DATA best = StatePerformanceTester.stateRetrieve(tester, testbase, casebase, casebase, 0, CPT, PPT, PAT);
					String bestAct = best.m_act;
					int time = best.m_time;
					
					
					lookback.write(time+1 + "\n");
					//if(didmything && tester.getAction().equals(bestAct) && off > 0){
						//lookback.write(off + "\n");
						//System.out.println("I did my thing and failed..." + off + " " + tester.getAction() + " " + bestAct + " ::::: " + DefiningAct);
					//}
					
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
				bw.flush();
				testfile.close();
			}
			
			br.close();
			bw.close();
			lookback.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static DATA stateRetrieve(RoboCase curr, ArrayList<RoboCase> run, ArrayList<RoboCase> nn, ArrayList<RoboCase> cb, int time, double CPT, double PPT, double PAT){
		double threshold;
		if(time == 0){
			threshold = CPT;
		}else{
			threshold = PPT;
		}
		
		ArrayList<RoboCase> possible = new ArrayList<RoboCase>();
		ArrayList<String> nnacts = new ArrayList<String>();
		double bestSim = -1;
		RoboCase bestRun = null;
		
		for(RoboCase train: nn){
			int currentPos = curr.getNum();
			int trainPos = train.getNum();
			
			double sim = -1;
			if(currentPos - time < 0 || trainPos - time < 0){
				sim = -1;
			}else{
				RoboCase actualCurrent = run.get(currentPos - time);
				RoboCase actualTrain = cb.get(trainPos - time);
				
				sim = actualCurrent.sim(actualTrain);
				
				if(sim > bestSim){
					bestSim = sim;
					bestRun = train;
				}
				if(sim > threshold){
					possible.add(train);
					if(!nnacts.contains(train.getAction())){
						nnacts.add(train.getAction());
					}
				}
			}
		}
		
		if(bestRun == null){
			return new DATA(nn.get(0).getAction(), time);
		}
		if(possible.size() == 0){
			return new DATA(bestRun.getAction(),time);
		}else if(nnacts.size() == 1){
			return new DATA(nnacts.get(0), time);
		}else{
			return actionRetrieve(curr, run, possible, cb, time+1, CPT, PPT, PAT);
		}
	}
	
	private static DATA actionRetrieve(RoboCase curr, ArrayList<RoboCase> run, ArrayList<RoboCase> nn, ArrayList<RoboCase> cb, int time, double CPT, double PPT, double PAT){
		double threshold = PAT;
		
		ArrayList<RoboCase> possible = new ArrayList<RoboCase>();
		ArrayList<String> nnacts = new ArrayList<String>();
		double bestSim = -1;
		RoboCase bestRun = null;
		
		for(RoboCase train: nn){
			int currentPos = curr.getNum();
			int trainPos = train.getNum();
			
			double sim = -1;
			if(currentPos - time < 0 || trainPos - time < 0){
				sim = -1;
			}else{
				RoboCase actualCurrent = run.get(currentPos - time);
				RoboCase actualTrain = cb.get(trainPos - time);
				
				sim = RoboCase.actionSim(actualCurrent.getAction(), actualTrain.getAction());
				
				if(sim > bestSim){
					bestSim = sim;
					bestRun = train;
				}
				if(sim > threshold){
					possible.add(train);
					if(!nnacts.contains(train.getAction())){
						nnacts.add(train.getAction());
					}
				}
			}
		}
		
		if(bestRun == null){
			return new DATA(nn.get(0).getAction(), time);
		}
		if(possible.size() == 0){
			return new DATA(bestRun.getAction(),time);
		}else if(nnacts.size() == 1){
			return new DATA(nnacts.get(0), time);
		}else{
			return stateRetrieve(curr, run, possible, cb, time, CPT, PPT, PAT);
		}
	}
	
}
