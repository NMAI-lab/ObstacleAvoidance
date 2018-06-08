
import java.util.Observable;
import java.util.Random;


public class EnvironmentModel extends Observable {

	private String[][] brd;
	private int x_robot;
	private int y_robot;
	
	public EnvironmentModel(String[][] brd) {
		this.brd = brd;
		
		for(int ii=0;ii<brd.length;ii++){
			for(int jj=0;jj<brd[ii].length;jj++){
				if(brd[ii][jj].equals("V") || brd[ii][jj].equals("^") || brd[ii][jj].equals("<") || brd[ii][jj].equals(">")){
					this.x_robot = ii;
					this.y_robot = jj;
				}
			}
		}
		
	}
	
	public int getRows(){
		return brd.length;
	}	
	
	public int getCols(){
		return brd[0].length;
	}
	
	public String get(int ii, int jj){
		return brd[ii][jj];
	}
	
	public void turnLeft(){
		String robot = brd[x_robot][y_robot];
		if(robot.equals("^")){
			brd[x_robot][y_robot] = "<";
		}else if(robot.equals("<")){
			brd[x_robot][y_robot] = "V";
		}else if(robot.equals("V")){
			brd[x_robot][y_robot] = ">";
		}else if(robot.equals(">")){
			brd[x_robot][y_robot] = "^";
		}else{
			System.out.println("I don't think I have the right robot position!!");
		}
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void turnRight(){
		String robot = brd[x_robot][y_robot];
		if(robot.equals("^")){
			brd[x_robot][y_robot] = ">";
		}else if(robot.equals("<")){
			brd[x_robot][y_robot] = "^";
		}else if(robot.equals("V")){
			brd[x_robot][y_robot] = "<";
		}else if(robot.equals(">")){
			brd[x_robot][y_robot] = "V";
		}else{
			System.out.println("I don't think I have the right robot position!!");
		}
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void reverse(){
		String robot = brd[x_robot][y_robot];
		if(robot.equals("^")){
			brd[x_robot][y_robot] = "V";
		}else if(robot.equals("<")){
			brd[x_robot][y_robot] = ">";
		}else if(robot.equals("V")){
			brd[x_robot][y_robot] = "^";
		}else if(robot.equals(">")){
			brd[x_robot][y_robot] = "<";
		}else{
			System.out.println("I don't think I have the right robot position!!");
		}
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void backward(){
		String robot = brd[x_robot][y_robot];
		if(robot.equals("<")){
			if(y_robot == brd[x_robot].length -1){
				return;
			}
			if(brd[x_robot][y_robot+1].equals("G")){
				return;
			}
			brd[x_robot][y_robot+1] = "<";
			brd[x_robot][y_robot] = "W";
			y_robot++;
		}else if(robot.equals("^")){
			if(x_robot == brd.length-1){
				return;
			}
			if(brd[x_robot+1][y_robot].equals("G")){
				return;
			}
			brd[x_robot+1][y_robot] = "^";
			brd[x_robot][y_robot] = "W";
			x_robot++;
		}else if(robot.equals(">")){
			if(y_robot == 0){
				return;
			}
			if(brd[x_robot][y_robot-1].equals("G")){
				return;
			}
			brd[x_robot][y_robot-1] = ">";
			brd[x_robot][y_robot] = "W";
			y_robot--;
		}else if(robot.equals("V")){
			if(x_robot == 0){
				return;
			}
			if(brd[x_robot-1][y_robot].equals("G")){
				return;
			}
			brd[x_robot-1][y_robot] = "V";
			brd[x_robot][y_robot] = "W";
			x_robot--;
		}else{
			System.out.println("I don't think I have the right robot position!!");
		}
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void forward(){
		String robot = brd[x_robot][y_robot];
		if(robot.equals("<")){
			if(y_robot == 0){
				return;
			}
			if(brd[x_robot][y_robot-1].equals("G")){
				return;
			}
			brd[x_robot][y_robot-1] = "<";
			brd[x_robot][y_robot] = "W";
			y_robot--;
		}else if(robot.equals("^")){
			if(x_robot == 0){
				return;
			}
			if(brd[x_robot-1][y_robot].equals("G")){
				return;
			}
			brd[x_robot-1][y_robot] = "^";
			brd[x_robot][y_robot] = "W";
			x_robot--;
		}else if(robot.equals(">")){
			if(y_robot == brd[x_robot].length -1){
				return;
			}
			if(brd[x_robot][y_robot+1].equals("G")){
				return;
			}
			brd[x_robot][y_robot+1] = ">";
			brd[x_robot][y_robot] = "W";
			y_robot++;
		}else if(robot.equals("V")){
			if(x_robot == brd.length-1){
				return;
			}
			if(brd[x_robot+1][y_robot].equals("G")){
				return;
			}
			brd[x_robot+1][y_robot] = "V";
			brd[x_robot][y_robot] = "W";
			x_robot++;
		}else{
			System.out.println("I don't think I have the right robot position!!");
		}
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public double getSonar(){
		String robot = brd[x_robot][y_robot];
		Random rnd = new Random();
		if(robot.equals("<")){
			int offset = 1;
			while(y_robot-offset > 0 && !brd[x_robot][y_robot-offset].equals("G")){
				offset++;
			}
			return Math.max(0, (double)offset + rnd.nextGaussian());	
		}else if(robot.equals("^")){
			int offset = 1;
			while(x_robot-offset > 0 && !brd[x_robot-offset][y_robot].equals("G")){
				offset++;
			}
			return Math.max(0, (double)offset + rnd.nextGaussian());
		}else if(robot.equals(">")){
			int offset = 1;
			while(y_robot+offset < brd[x_robot].length -1 && !brd[x_robot][y_robot+offset].equals("G")){
				offset++;
			}
			return Math.max(0, (double)offset + rnd.nextGaussian());
		}else if(robot.equals("V")){
			int offset = 1;
			while(x_robot+offset < brd[x_robot].length -1 && !brd[x_robot+offset][y_robot].equals("G")){
				offset++;
			}
			return Math.max(0, (double)offset + rnd.nextGaussian());
		}else{
			System.out.println("I don't think I have the right robot position!!");
			return -1.0f;
		}

	}
	
	public double getTouch(){
		String robot = brd[x_robot][y_robot];
		if(robot.equals("<")){
			if(y_robot == 0 || brd[x_robot][y_robot-1].equals("G")){
				return 1.0f;
			}else{
				return 0.0f;
			}	
		}else if(robot.equals("^")){
			if(x_robot == 0 || brd[x_robot-1][y_robot].equals("G")){
				return 1.0f;
			}else{
				return 0.0f;
			}
		}else if(robot.equals(">")){
			if(y_robot == brd[x_robot].length -1 || brd[x_robot][y_robot+1].equals("G")){
				return 1.0f;
			}else{
				return 0.0f;
			}
		}else if(robot.equals("V")){
			if(x_robot == brd.length-1 || brd[x_robot+1][y_robot].equals("G")){
				return 1.0f;
			}else{
				return 0.0f;
			}
		}else{
			System.out.println("I don't think I have the right robot position!!");
			return -1.0f;
		}

	}
	
	public double getSound(){
		double rnd = Math.random();
		
		if(rnd < 0.10){
			return 1.0;
		}else if(rnd < 0.20){
			return 2.0;
		}else{
			return 0.0;
		}
	}
	
}
