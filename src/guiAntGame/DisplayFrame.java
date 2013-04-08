package guiAntGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;


public class DisplayFrame extends javax.swing.JFrame {
    public DisplayFrame(){
        this.setSize(803, 735); //The window Dimensions
        
        
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new  BorderLayout());
        processing.core.PApplet sketch = new SimulatorView();
        
        panel.add(sketch);
        this.add(panel);
        sketch.init(); //this is the function used to start the execution of the sketch
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
}