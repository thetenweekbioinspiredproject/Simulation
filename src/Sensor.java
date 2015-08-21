import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;


public class Sensor {
	int ANGLE = 90; /* Angle (degrees) that sensor can see */
	int angleFacing = 0;
	
	private float x;
	private float y;
	
	/**
	 * 
	 * @param angleFacing - angle perturbed clockwise from right horizontal to sensor normal.
	 */
	public Sensor(int angleFacing, float xIn, float yIn){
		updateSensorCoords(angleFacing, xIn, yIn);
	}
	
	public void updateSensorCoords(int angleFacing,float xIn, float yIn){
		setX(xIn);
		setY(yIn);
		
		this.angleFacing = angleFacing;
	}

	
	public int getX() {
		return (int) x;
	}
	
	public int getY() {
		return (int) y;
	}
	
	public void setX(float xIn) {
		this.x = xIn;
	}
	
	public void setY(float yIn) {
		this.y = yIn;
	}
	
	public boolean canSeeLED(LED l) {	
		// TODO this only works for 4 point sensors
		int[] xPoint;
		xPoint = new int[3];
		
		xPoint[0] = getX();
		xPoint[1] = (int)(getX()+10000*Math.cos(Math.toRadians(angleFacing-45)));
		xPoint[2] = (int)(getX()+10000*Math.cos(Math.toRadians(angleFacing+45)));
		
		int[] yPoint;
		yPoint = new int[3];
		
		yPoint[0] = getY();
		yPoint[1] = (int)(getY()+10000*Math.sin(Math.toRadians(angleFacing-45)));
		yPoint[2] = (int)(getY()+10000*Math.sin(Math.toRadians(angleFacing+45)));
		
		// TODO ERROR polygon is stretching well beyond what it should
		System.out.println("x: " + xPoint[0] + ", " + yPoint[0]);
		System.out.println("x: " + xPoint[1] + ", " + yPoint[1]);
		System.out.println("x: " + xPoint[2] + ", " + yPoint[2]);
		System.out.println(" ");
		
		Polygon areaLED = new Polygon (xPoint,yPoint,3);
		
		if(areaLED.contains(l.getX(),l.getY())){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void drawImage(Graphics2D g2d, int x, int y){
		g2d.setColor(Color.RED);
		g2d.drawLine((int)(getX()+0.5+14*Math.cos(Math.toRadians(angleFacing-45))),(int)(getY()+0.5+14*Math.sin(Math.toRadians(angleFacing-45))),
				(int)(getX()+0.5+14*Math.cos(Math.toRadians(angleFacing+45))),(int)(getY()+0.5+14*Math.sin(Math.toRadians(angleFacing+45))));
	}
	
}
