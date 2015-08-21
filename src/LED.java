import java.awt.Color;
import java.awt.Graphics2D;


public class LED {
	private int LED_RADIUS = 175;
	
	private float x;
	private float y;
	
	private boolean on = false;
	
	private Color color = Color.YELLOW;
	
	public LED(float xIn, float yIn){
		updateLEDCoords(xIn, yIn);
	}
	
	public void updateLEDCoords(float xIn, float yIn){
		setX(xIn);
		setY(yIn);
	}
	
	public int getRadius() {
		return LED_RADIUS;
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
	
	public void setColor(Color c) {
		color = c;
	}
	
	public void toggleLed(){
		on = !on;
	}
	
	public void turnOn(){
		on = true;
	}
	
	public boolean isOn(){
		return on;
	}
	
	public void drawImage(Graphics2D g2d, int x, int y){
		if (on == true){
			g2d.setColor(color);
			g2d.fillOval(getX()-3,getY()-3,6,6); //LED	 
			
			g2d.setColor(new Color(1,1,0,0.2f));
			g2d.fillOval(getX()-LED_RADIUS/6,getY()-LED_RADIUS/6,LED_RADIUS/3,LED_RADIUS/3); // INNER CIRCLE
			
			g2d.setColor(new Color(1,1,0,0.1f));
			g2d.fillOval(getX()-LED_RADIUS/2,getY()-LED_RADIUS/2,LED_RADIUS,LED_RADIUS); //OUTER CIRCLE
			g2d.setColor(Color.YELLOW);
			g2d.drawOval(getX()-LED_RADIUS/2,getY()-LED_RADIUS/2,LED_RADIUS,LED_RADIUS); //OUTER CIRCLE
		}
		else{
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fillOval(getX()-3,getY()-3,6,6);
		}
		
	}

}
