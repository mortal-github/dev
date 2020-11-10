package mortal.learn.java.annotation.introduce;

import javax.swing.*;
import java.awt.*;

public class ButtonFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    private JPanel panel;
    private JButton yellowButton;
    private JButton blueButton;
    private JButton redButton;

    public static void main(String[] args){
        JFrame frame = new ButtonFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public ButtonFrame(){
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        this.panel = new JPanel();
        this. add(panel);

        this.yellowButton = new JButton("Yellow");
        this.blueButton = new JButton("Blue");
        this.redButton = new JButton("Red");

        this.panel.add(this.yellowButton);
        this.panel.add(this.blueButton);
        this.panel.add(this.redButton);

        ActionListenerInstaller.processAnnotations(this);
    }

    @ActionListenerFor(source="yellowButton")
    public void yellowBackground(){
        this.panel.setBackground(Color.YELLOW);
    }

    @ActionListenerFor(source="blueButton")
    public void blueBackground(){
        this.panel.setBackground(Color.BLUE);
    }

    @ActionListenerFor(source="redButton")
    public void redBackground(){
        this.panel.setBackground(Color.RED);
    }
}
