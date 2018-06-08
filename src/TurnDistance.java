import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;


public class TurnDistance {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			BufferedReader br = new BufferedReader(new FileReader("log.txt"));
			BufferedWriter bw = new BufferedWriter(new FileWriter("turndist.txt"));
		
			String linein = null;
		
			ArrayList<RoboCase> casebase = new ArrayList<RoboCase>();
		
			while((linein = br.readLine()) != null){
				casebase.add(new RoboCase(linein));
			}
			
			int lastTurn = 1;
			for(RoboCase c: casebase){
				if(c.getAction().equals("LE") || c.getAction().equals("RI")){
					bw.write(lastTurn + "\n");
					lastTurn = 1;
				}else{
					lastTurn++;
				}
			}
			
			br.close();
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
