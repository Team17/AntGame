package guiAntGame;

import java.awt.BorderLayout;
import antgame.core.World;

/**
 * A class used to put the JApplets inside a JFrame
 * @author Doniyor
 */
public class DisplayFrame extends javax.swing.JFrame {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new display frame with the SimulatorView PApplet inside it.
	 *
	 * @param w the world to simulate inside the simulator view.
	 */
	public DisplayFrame(World w){
        this.setSize(803, 745); //The window Dimensions
        this.setMinimumSize(getSize());
        
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new  BorderLayout());
        SimulatorView sketch = new SimulatorView();

        sketch.setWorld(w);
        panel.add(sketch);
        this.add(panel);
        sketch.init(); //this is the function used to start the execution of the sketch
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(true);
        this.setVisible(true);
    }
    
    /**
     * Instantiates a new display frame with the MainMenu PApplet inside it.
     */
    public DisplayFrame(){
        this.setSize(600, 530); //The window Dimensions

        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new  BorderLayout());
        MainMenu sketch = new MainMenu();
        panel.add(sketch);
        this.add(panel);

        sketch.init(); //this is the function used to start the execution of the sketch
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
    
    /**
     * Instantiates a new display frame with the Tournament PApplet inside it.
     * @param null
     */
    public DisplayFrame(int i){
        this.setSize(405, 530); //The window Dimensions

        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new  BorderLayout());
        Tournament sketch = new Tournament();
        panel.add(sketch);
        this.add(panel);

        sketch.init(); //this is the function used to start the execution of the sketch
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

}