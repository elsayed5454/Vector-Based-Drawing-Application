package eg.edu.alexu.csd.oop.draw;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.*;
import java.util.Iterator;

public class GUI extends JFrame{
	
	JButton brushButton, lineButton, circleButton, ellipseButton, rectButton, squareButton, triangleButton, strokeButton, fillButton;
	int action = 1;
	Color stroke = Color.BLACK, fillClr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new GUI();
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
		
		this.setSize(500, 500);
		this.setTitle("Vector Based Paint");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		Box box = Box.createHorizontalBox();
		
		brushButton = makeButton("./src/brush.png", 1);
		lineButton = makeButton("./src/line.png", 2);
		circleButton = makeButton("./src/circle.png", 3);
		ellipseButton = makeButton("./src/ellipse.png", 4);
		rectButton = makeButton("./src/rectangle.png", 5);
		squareButton = makeButton("./src/square.png", 6);
		triangleButton = makeButton("./src/triangle.png", 7);
		strokeButton = makeButton("./src/stroke.png", 8);
		fillButton = makeButton("./src/fill.png", 9);
		
		box.add(brushButton);
		box.add(lineButton);
		box.add(circleButton);
		box.add(ellipseButton);
		box.add(rectButton);
		box.add(squareButton);
		box.add(triangleButton);
		box.add(strokeButton);
		box.add(fillButton);
		
		panel.add(box);
		this.add(panel, BorderLayout.SOUTH);
		this.add(new DrawCanvas(), BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	public JButton makeButton(String iconFile, final int actionNum) {
		
		JButton button = new JButton();
		Icon buttonIcon = new ImageIcon(iconFile);
		button.setIcon(buttonIcon);
		
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				action = actionNum;
			}
		});
		return button;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private class DrawCanvas extends JComponent {
		
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		ArrayList<Color> shapesFill = new ArrayList<Color>();
		ArrayList<Color> shapesStroke = new ArrayList<Color>();
		
		public DrawCanvas() {
			
			this.addMouseListener(new MouseAdapter() {
				
				public void mousePressed(MouseEvent e) {
					
					Point start = new Point(e.getX(), e.getY());
					Point end = new Point(e.getX(), e.getY());
					repaint();
				}
				
				public void mouseReleased(MouseEvent e) {
					
					Rectangle shape_1 = new Rectangle(10.0,10.0);
					shapes.add(shape_1);
					shapesFill.add(fillClr);
					shapesStroke.add(stroke);
					
					repaint();
					
				}
			});
			this.addMouseMotionListener(new MouseMotionAdapter() {
				
				public void mouseDragged(MouseEvent e) {
					
					Point end = new Point(e.getX(), e.getY());
					repaint();
				}
			});
		}
		
		public void paint(Graphics g) {
			
			Graphics2D settings = (Graphics2D)g;
			settings.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			settings.setStroke(new BasicStroke(2));
			
			Iterator<Color> strokeCnt = shapesStroke.iterator();
			Iterator<Color> fillCnt = shapesFill.iterator();
			
			for (Shape s : shapes) {
				
			}
		}
		
		
	}

}
