package eg.edu.alexu.csd.oop.draw;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

public class GUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0,frame.getToolkit().getScreenSize().width + 30 ,frame.getToolkit().getScreenSize().height - 30);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnLine = new JButton("Line Segment");
		btnLine.setBounds(304, 650, 112, 23);
		frame.getContentPane().add(btnLine);
		
		JButton btnCircle = new JButton("Circle");
		btnCircle.setBounds(416, 650, 112, 23);
		frame.getContentPane().add(btnCircle);
		
		JButton btnEllipse = new JButton("Ellipse");
		btnEllipse.setBounds(528, 650, 112, 23);
		frame.getContentPane().add(btnEllipse);
		
		JButton btnTriangle = new JButton("Triangle");
		btnTriangle.setBounds(640, 650, 112, 23);
		frame.getContentPane().add(btnTriangle);
		
		JButton btnRect = new JButton("Rectangle");
		btnRect.setBounds(752, 650, 112, 23);
		frame.getContentPane().add(btnRect);
		
		JButton btnSqre = new JButton("Square");
		btnSqre.setBounds(864, 650, 112, 23);
		frame.getContentPane().add(btnSqre);
		
		JButton btnRmv = new JButton("Remove");
		btnRmv.setBounds(976, 650, 112, 23);
		frame.getContentPane().add(btnRmv);
		
		JButton btnUndo = new JButton("Undo");
		btnUndo.setBounds(1260, 282, 89, 23);
		frame.getContentPane().add(btnUndo);
		
		JButton btnRedo = new JButton("Redo");
		btnRedo.setBounds(1260, 319, 89, 23);
		frame.getContentPane().add(btnRedo);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(1260, 356, 89, 23);
		frame.getContentPane().add(btnSave);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.setBounds(1260, 393, 89, 23);
		frame.getContentPane().add(btnLoad);
	}
}
