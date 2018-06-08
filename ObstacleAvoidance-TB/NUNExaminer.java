import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;


public class NUNExaminer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ArrayList<RoboCase> cases = new ArrayList<RoboCase>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(args[0]));
			String linein = null;
			
			
			while((linein = br.readLine()) != null){
				cases.add(new RoboCase(linein));
			}
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(args[1]));
			
			for(int ii=0; ii< cases.size();ii++){
				RoboCase c = cases.get(ii);
				double nunSim = -1;
				double sameSim = -1;
				String nunAct = null;
				for(int jj=0;jj<cases.size();jj++){
					RoboCase comp = cases.get(jj);
					if(!c.getAction().equals(comp.getAction())){
						double sim = c.sim(comp);
						if(sim > nunSim){
							nunSim = sim;
							nunAct = comp.getAction();
						}
					}else{
						if( ii != jj){
							double sim = c.sim(comp);
							if(sim > sameSim){
								sameSim = sim;
							}
						}
					}
				}
				bw.write(nunSim + "," + sameSim + "," + c.getAction() + "," + nunAct + "\n");
			}
			bw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		

	}

}
