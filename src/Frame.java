import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class Frame {
	
	
	
	public static void main(String[] args) {
		final JFrame frame = new JFrame("Simulator");
		final Sim sim = new Sim();
		
		/* add loading screen */
		GameSplashWindow splash = new GameSplashWindow(frame);
		
		/* set frame characteristics */
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		//frame.setResizable(false);
		ImageIcon i = new ImageIcon("simLogo.png");
	    Image Image = i.getImage();
	    frame.setIconImage(Image);
	    
	    /* create sim - may take some time - must be done after frame size is set */
		frame.add(sim);
		
		/* This code is somewhat over-the-top, but ensure JPanel has got the correct size! */
		sim.updateSimSize(frame.getSize().width, frame.getSize().height);
		
		/* destroy loading screen and make sim window visible */
		frame.remove(splash);
		splash.dispose();
		frame.setVisible(true);
		
		/* finish setting up layout after sim window appears (unfortunately cannot be done earlier) */
		frame.setLocationRelativeTo(null); // This line centres window.
		frame.setLayout(null);			   // Added JFrame will now by placed by thier X, Y.
		
		frame.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
				sim.updateSimSize(frame.getSize().width, frame.getSize().height);
		    }
		});
		
		frame.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				sim.keyPressed(arg0);
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}	
		});
		
		frame.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				sim.mouseClicked(e);
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}

