

/**

for(RoboCase training: casebase){
	double sim = tester.sim(training);
	if(sim > bestSim){
		bestSim = sim;
		bestAct = training.getAction();
	}
	if(sim > threshold){
		possible.add(training);
	}
}

boolean done = false;
boolean didmything = true;
int off = 0;
String DefiningAct = null;
while(!done){
	if(possible.size() > 0){
		boolean foundDiff = false;
		String firstAction = null;
		for(RoboCase poss: possible){
			if(firstAction == null){
				firstAction = poss.getAction();
			}else{
				if(!poss.getAction().equals(firstAction)){
					foundDiff = true;
				}
			}
		}
		
		if(!foundDiff){
			bestAct = firstAction;
			done = true;
		}else{
			off++;
			int caseNum = tester.getNum();
			if(caseNum - off < 0){
				done = true;
			}else{
				String prevAct = testbase.get(caseNum-off).getAction();
				DefiningAct = prevAct;
				ArrayList<RoboCase> nextpos = new ArrayList<RoboCase>();
				if(testbase.get(caseNum-off).getNum() != caseNum-off){
					System.out.println("Something is off...");
				}
				for(RoboCase testpop: possible){
					int posNum = testpop.getNum();
					if(posNum - off >= 0){
						String posAct = casebase.get(posNum-off).getAction();
						if(prevAct.equals(posAct)){
							nextpos.add(testpop);
						}
					}
				}
				possible = new ArrayList<RoboCase>(nextpos);
			}
		}
									
	}else{
		done = true;
		DefiningAct = null;
		if(off == 0){
			didmything = false;
		}
	}
}

*/


public class DATA{
	public String m_act;
	public int m_time;
	public DATA(String act, int time){
		m_act = act;
		m_time = time;
	}
}