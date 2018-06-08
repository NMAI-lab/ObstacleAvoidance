
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JTextArea;




public class GUIView implements Observer{

	private static final String TAG = "GUIView";
	
	private JFrame frame;
	private JTextArea[][] squares;
	
	public GUIView(EnvironmentModel mdl) {
		mdl.addObserver(this);
		
		this.frame = new JFrame("RoboSimulate");
		frame.setLayout(new GridLayout(mdl.getRows(), mdl.getCols()));
		this.squares = new JTextArea[mdl.getRows()][mdl.getCols()];
		for(int ii=0;ii<mdl.getRows();ii++){
			for(int jj=0;jj<mdl.getCols();jj++){
				this.squares[ii][jj] = new JTextArea();
				this.squares[ii][jj].setEditable(false);
				this.frame.add(this.squares[ii][jj]);
			}
		}
		reDrawBoard(mdl);
		this.frame.setSize(600, 600);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
		
	}


	@Override
	public void update(Observable o, Object arg) {
		reDrawBoard((EnvironmentModel)o);	
	}


	private void reDrawBoard(EnvironmentModel mdl){
		for(int ii=0;ii<mdl.getRows();ii++){
			for(int jj=0;jj<mdl.getCols();jj++){
				this.squares[ii][jj].setText(" ");
				if(mdl.get(ii,jj).equals("G")){
					this.squares[ii][jj].setBackground(Color.GREEN);
				}else if(mdl.get(ii, jj).equals("^")){
					this.squares[ii][jj].setBackground(Color.RED);
					this.squares[ii][jj].setText("^");
				}else if(mdl.get(ii, jj).equals("<")){
					this.squares[ii][jj].setBackground(Color.RED);
					this.squares[ii][jj].setText("<");
				}else if(mdl.get(ii, jj).equals("V")){
					this.squares[ii][jj].setBackground(Color.RED);
					this.squares[ii][jj].setText("V");
				}else if(mdl.get(ii, jj).equals(">")){
					this.squares[ii][jj].setBackground(Color.RED);
					this.squares[ii][jj].setText(">");
				}else{
					this.squares[ii][jj].setBackground(Color.WHITE);
				}
			}
		}
		this.frame.repaint();
	}
	
	public void close(){
		this.frame.dispose();
	}
}
