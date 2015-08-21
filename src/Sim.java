
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Sim extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int NO_OF_NODES = 2;
	
	private Node[] n;
	private Timer time;
	
	public Sim(){          
		/* create an array of nodes */
		n = new Node[NO_OF_NODES];
		
		/* sets where each node is positioned */
		setNodeCoords();
		
		/* Used for refreshing screen at a set rate */
		time = new Timer(15, this);
		time.start();
	}
	
	/**
	 * sets coordinates of each node depending on node configuration.
	 */
	public void setNodeCoords(){
		for (int i = 0; i < NO_OF_NODES; i++) {
			if (n[i] == null){
				// TODO
			    //if grid{
					n[i] = new Node(getSize().width, getSize().height, NO_OF_NODES, i);
				//}
			}
			else{
				n[i].SetGridCoords(getSize().width, getSize().height, NO_OF_NODES, i);
				n[i].initLEDSensors();
			}
		}
	}
	
	/**
	 * resizes the simulation panel including drawing range, and simulation configuration.
	 * @param width
	 * @param height
	 */
	public void updateSimSize(int width, int height){
		setSize(new Dimension(width, height));
		setNodeCoords();
	}
	
	public void keyPressed(KeyEvent arg0){
		if (arg0.getKeyCode() == KeyEvent.VK_UP){
			// create temp array to copy array, extend in, then replace values.
			Node[] nTemp = n;
			
			n = new Node[NO_OF_NODES+1];
			NO_OF_NODES++;
			
			for (int i = 0; i < NO_OF_NODES-1; i++) {
				n[i] = nTemp[i];
			}
			
			setNodeCoords();
		}
		else if (arg0.getKeyCode() == KeyEvent.VK_DOWN){
			if(NO_OF_NODES>1){
				NO_OF_NODES--;
				setNodeCoords();
			}
		}
		else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT){			
			for (int i = 0; i < NO_OF_NODES; i++) {
				n[i].incSensorNo();
				setNodeCoords();
			}
		}
		else if (arg0.getKeyCode() == KeyEvent.VK_LEFT){
			for (int i = 0; i < NO_OF_NODES; i++) {
				n[i].decSensorNo();
				setNodeCoords();
			}
		}
	}
	
	public void mouseClicked(MouseEvent e){
		for (int i = 0; i < NO_OF_NODES; i++) {
			n[i].mouseClicked(e);
		}
	}
	
	// updates when timer updates.
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < NO_OF_NODES; i++) {
			n[i].nodeCompare(n);
		}
		
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g); 
		Graphics2D g2d = (Graphics2D) g; 
		
		//Background
		g2d.setColor(new Color(200,230,245));
		g2d.fillRect(0,0,getSize().width,getSize().height);
		
		// Nodes
		g2d.setColor(Color.BLACK);
		for (int i = 0; i < NO_OF_NODES; i++) {
			n[i].drawImage(g2d);
		}
	}
}
