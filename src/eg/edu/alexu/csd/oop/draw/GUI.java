package eg.edu.alexu.csd.oop.draw;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GUI {
	
	DrawingEngine engine = new Logic();
	
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

	private int action = -1;
	boolean secondClick = false, thirdClick = false;
	Point firstPoint, secondPoint;
	
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
		Canvas canvas = new Canvas();
		canvas.setLocation(0, 0);
		canvas.setSize(1256, 650);
		frame.getContentPane().add(canvas);
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Line action
				if (action == 1) {
					if (!secondClick) {
						firstPoint = e.getPoint();
						secondClick = true;
					}
					else {
						Shape line = new LineSegment(firstPoint, e.getPoint());
						engine.addShape(line);
						secondClick = false;
					}
				}
				// Circle action
				else if (action == 2) {
					if (!secondClick) {
						firstPoint = e.getPoint();
						secondClick = true;
					}
					else {
						Point boundary = e.getPoint();
						double radius = Point.distance(firstPoint.getX(), firstPoint.getY(), boundary.getX(), boundary.getY());
						Shape circle = new Circle(radius*2);
						Point topLeft = new Point();
						topLeft.setLocation(firstPoint.getX() - radius, firstPoint.getY() - radius);
						circle.setPosition(topLeft);
						engine.addShape(circle);
						secondClick = false;
					}
				}
				// Ellipse action
				else if (action == 3) {
					if (!secondClick) {
						firstPoint = e.getPoint();
						secondClick = true;
					}
					else if (!thirdClick) {
						secondPoint = e.getPoint();
						thirdClick = true;
					}
					else {
						double halfWidth = Math.abs(firstPoint.getX() - secondPoint.getX());
						double halfHeight = Math.abs(firstPoint.getY() - e.getPoint().getY());
						Shape ellipse = new Ellipse(2*halfWidth, 2*halfHeight);
						Point topLeft = new Point();
						topLeft.setLocation(firstPoint.getX() - halfWidth, firstPoint.getY() - halfHeight);
						ellipse.setPosition(topLeft);
						engine.addShape(ellipse);
						secondClick = false;
						thirdClick = false;
					}
				}
				// Triangle action
				else if (action == 4) {
					if (!secondClick) {
						firstPoint = e.getPoint();
						secondClick = true;
					}
					else if (!thirdClick) {
						secondPoint = e.getPoint();
						thirdClick = true;
					}
					else {
						Shape triangle = new Triangle(firstPoint, secondPoint, e.getPoint());
						engine.addShape(triangle);
						secondClick = false;
						thirdClick = false;
					}
				}
				// Rectangle action
				else if (action == 5) {
					if (!secondClick) {
						firstPoint = e.getPoint();
						secondClick = true;
					}
					else {
						Point topLeft = new Point();
						double minX = Math.min(firstPoint.getX(), e.getX());
						double minY = Math.min(firstPoint.getY(), e.getY());
						topLeft.setLocation(minX, minY);
						double width = Math.abs(firstPoint.getX() - e.getX());
						double height = Math.abs(firstPoint.getY() - e.getY());
						Shape rectangle = new Rectangle(width, height);
						rectangle.setPosition(topLeft);
						engine.addShape(rectangle);
						secondClick = false;
					}
				}
				// Square action
				else if (action == 6) {
					if (!secondClick) {
						firstPoint = e.getPoint();
						secondClick = true;
					}
					else {
						Point topLeft = new Point();
						double minX = Math.min(firstPoint.getX(), e.getX());
						double minY = Math.min(firstPoint.getY(), e.getY());
						topLeft.setLocation(minX, minY);
						// Diagonal * cos(45 degrees) = side of square
						double side = Point.distance(firstPoint.getX(), firstPoint.getY(), e.getX(), e.getY()) * Math.cos(Math.toRadians(45));
						Shape square = new Square(side);
						square.setPosition(topLeft);
						engine.addShape(square);
						secondClick = false;
					}
				}
				// Remove action
				else if (action == 7) {
					Point selected = e.getPoint();
					Shape[] shapes = engine.getShapes();
					double minX, minY, maxX, maxY;
					for (int i = shapes.length - 1; i >= 0; i--) {
						
						minX = shapes[i].getPosition().getX();
						minY = shapes[i].getPosition().getY();
						maxX = minX + shapes[i].getProperties().get("width");
						maxY = minY + shapes[i].getProperties().get("height");
								
						if (selected.getX() >= minX && selected.getX() <= maxX && selected.getY() >= minY && selected.getY() <= maxY) {
							engine.removeShape(shapes[i]);
							canvas.getGraphics().clearRect((int)minX - 1, (int)minY - 1, (int)maxX, (int)maxY);
							break;
						}
					}
				}
				engine.refresh(canvas.getGraphics());
			}
		});
		
		JButton btnLine = new JButton("Line Segment");
		btnLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action = 1;
			}
		});
		btnLine.setBounds(304, 650, 112, 23);
		frame.getContentPane().add(btnLine);
		
		JButton btnCircle = new JButton("Circle");
		btnCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action = 2;
			}
		});
		btnCircle.setBounds(416, 650, 112, 23);
		frame.getContentPane().add(btnCircle);
		
		JButton btnEllipse = new JButton("Ellipse");
		btnEllipse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action = 3;
			}
		});
		btnEllipse.setBounds(528, 650, 112, 23);
		frame.getContentPane().add(btnEllipse);
		
		JButton btnTriangle = new JButton("Triangle");
		btnTriangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action = 4;
			}
		});
		btnTriangle.setBounds(640, 650, 112, 23);
		frame.getContentPane().add(btnTriangle);
		
		JButton btnRect = new JButton("Rectangle");
		btnRect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action = 5;
			}
		});
		btnRect.setBounds(752, 650, 112, 23);
		frame.getContentPane().add(btnRect);
		
		JButton btnSqre = new JButton("Square");
		btnSqre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action = 6;
			}
		});
		btnSqre.setBounds(864, 650, 112, 23);
		frame.getContentPane().add(btnSqre);
		
		JButton btnRmv = new JButton("Remove");
		btnRmv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action = 7;
			}
		});
		btnRmv.setBounds(976, 650, 112, 23);
		frame.getContentPane().add(btnRmv);
		
		JButton btnUndo = new JButton("Undo");
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				engine.undo();
				engine.refresh(canvas.getGraphics());
			}
		});
		btnUndo.setBounds(1260, 282, 89, 23);
		frame.getContentPane().add(btnUndo);
		
		JButton btnRedo = new JButton("Redo");
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				engine.redo();
				engine.refresh(canvas.getGraphics());
			}
		});
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
