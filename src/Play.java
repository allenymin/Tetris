import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Play {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Tetris");
		frame.setSize(500, 700);
		frame.setLocation(100,100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Panel p = new Panel();
		Container c = frame.getContentPane();
		c.add(p);
		frame.addKeyListener(p);
		frame.setVisible(true);
	}
}
