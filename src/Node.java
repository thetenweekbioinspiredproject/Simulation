import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;


public class Node {
	
	private float x;
	private float y;
	
	private int positionNumber;
	
	private Sensor[] s;
	private int NO_OF_SENSORS = 4;
	
	private LED l;
	
	public Node(int xIn, int yIn) {		
		setX(xIn);
		setY(yIn);
		
		initLEDSensors();
	}
	
	public Node(int xWidth, int yWidth, int noOfNodes, int positionNumber) {		
		SetGridCoords(xWidth, yWidth, noOfNodes, positionNumber);
		initLEDSensors();
	}
	
	public void initLEDSensors(){
		// Only build new sensor if it does not exist, else just update coords.
		if (s == null){
			s = new Sensor[NO_OF_SENSORS];
		}
		
		for (int i = 0; i < NO_OF_SENSORS; i++) {
			if(s[i]==null){
				s[i] = new Sensor((i)*(360/NO_OF_SENSORS),getX(),getY());
			}
			else{
				s[i].updateSensorCoords((i)*(360/NO_OF_SENSORS),getX(), getY());
			}
		}
		
		// Only build new LED if it does not exist, else just update coords.
		if (l == null){	
			l = new LED(getX(),getY());
		}
		else{
			l.updateLEDCoords(getX(), getY());
		}
	}
	
	
	public int getX() {
		return (int) x;
	}
	
	public int getY() {
		return (int) y;
	}
	
	public void setX(int xIn) {
		this.x = xIn;
	}
	
	public void setY(int yIn) {
		this.y = yIn;
	}
	
	public void decSensorNo() {
		if(NO_OF_SENSORS>1){
			NO_OF_SENSORS--;
		}
	}
	
	public void incSensorNo() {
		Sensor[] sTemp = s;
		
		s = new Sensor[NO_OF_SENSORS+1];
		NO_OF_SENSORS++;
		
		for (int i = 0; i < NO_OF_SENSORS-1; i++) {
			s[i] = sTemp[i];
		}
	}
	
	public int getSensorNo() {
		return NO_OF_SENSORS;
	}
	
	public LED getLED() {
		return l;
	}
	
	public void drawImage(Graphics2D g2d){
		g2d.setColor(Color.BLACK);
		g2d.fillOval(getX()-8,getY()-8,16,16);
		
		l.drawImage(g2d, getX(),getY());
		
		for (int i = 0; i < NO_OF_SENSORS; i++) {
			s[i].drawImage(g2d, getX(),getY());
		}
	}
	
	public void SetGridCoords(double xWidth, double yWidth, int noOfNodes, int positionNumber){
		this.positionNumber = positionNumber;
		int myRow = 1;                     /* Working variable to determine row number of node */
		int myColumn = 1;                  /* Working variable to determine column number of node */
		int myNumber = 1;                  /* Working variable to cycle through grid until position number is hit */
		float rows = 1;                    /* Working variable to determine optimum rows in grid */
		float columns = 1;                 /* Working variable to determine optimum columns in grid */
		double columnInc = (xWidth/yWidth);/* Working variable to determine rate at which columns should increase to rows */
		

	    /* The number of columns to rows increases linearly. The first value is just the rate of increase */
		columns *= columnInc;
		
		/* keep increasing grid size at correct ratio until all nodes can fit in */
		while ((rows*columns)<noOfNodes){
			columns += columnInc;
			rows += 1;
		}
		
		/* cycle through grid to find place where node should sit */
		while(myNumber <= positionNumber){
			/* first cycle through a row until end is reached */
			if(myColumn<columns){
				myColumn++;
				myNumber++;
			}
			/* cycle down a row, and start back at the first node in that row */
			else{
				myColumn = 1;
				myRow++;
				myNumber++;
			}
		}
		
		/* set the X and Y locations of the node ready for drawing */	
		setX(myColumn*(int)(xWidth/(columns+2)));
		setY(myRow*(int)(yWidth/(rows+2)));

	}
	
	public void nodeCompare(Node[] n){
		int lightNeededForChange = 2;
		int lightNeededForChangeCounter = 0;
		
		for (int i = 0; i < n.length; i++) {
			if (i == positionNumber){
				// do nothing - dont want to read data from self!
			}
			else{

				// If led is on, and then if node is in the radius of another node's LED, register a detection.
				if (n[i].getLED().isOn()==true){
					
 					float a11 = x - n[i].getX(); 
 					float a12 = a11*a11;
					
 					float a21 = y - n[i].getY();
 					float a22 = a21*a21;
					
 					float a = a12 + a22;
				
 					float b1 =(float) (0.5*(float)(n[i].l.getRadius()));
 					float b = b1*b1;

 					/* enters if inside LED radius of another node */
					if(a<b){
						/* then if sensor sees LED */
						for (int j = 0; j < NO_OF_SENSORS; j++) {
							if(s[j].canSeeLED(getLED())){
								lightNeededForChangeCounter++;
								System.out.print(i+": ");
								System.out.println(j);
							}
						}
					}
				}
			}
		}
		
		// if multiple LEDs are detected colour LED red, else colour is yellow
		if(lightNeededForChangeCounter >= lightNeededForChange){
			l.setColor(Color.RED);
		}
		else{
			l.setColor(Color.YELLOW);
		}
	}

	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			if((e.getX()<(getX()+20))&&(e.getX()>(getX()-5))&&(e.getY()<(getY()+40))&&(e.getY()>(getY()+15))){
				l.toggleLed();
			}
		}
		else if (e.getButton() == MouseEvent.BUTTON3) {
			l.toggleLed();
		}
		else if (e.getButton() == MouseEvent.BUTTON2) {
			l.turnOn();
		}
	}
}
