import java.util.Scanner;


public class RoboCase {
	private double sonar;
	private double touch;
	private double sound;
	private int num;
	private String action;
	
	public RoboCase(String in){
		Scanner c = new Scanner(in);
		c.useDelimiter(",");
		sonar = c.nextDouble();
		touch = c.nextDouble();
		sound = c.nextDouble();
		num = c.nextInt();
		action = c.next();
	}
	
	public String getAction(){
		return action;
	}
	
	public double sim(RoboCase c){
		
		double simTouch = featureSim(this.touch, c.touch);
		double simSonar = featureSim(this.sonar, c.sonar);
		double simSound = featureSim(this.sound, c.sound);
		
		return (simTouch + simSonar + simSound)/3;
	}
	
	public double featureSim(double f1, double f2){
		double denominator = f1 + f2;
		if(denominator == 0.00){
			return 1.0;
		}else{
			return 1 - Math.abs(f1 - f2)/denominator;
		}
	}
	
	public int getNum(){
		return this.num;
	}
	
	public static double actionSim(String a1, String a2){
		if(a1.equals(a2)){
			return 1.0;
		}else{
			return 0.0;
		}
	}
}
