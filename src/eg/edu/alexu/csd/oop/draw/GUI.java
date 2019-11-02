package eg.edu.alexu.csd.oop.draw;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

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

	DrawingEngine engine = new Logic();
	private int action = -1;
	boolean secondClick = false, thirdClick = false, actionAttempt = false;
	Point firstPoint, secondPoint;
	Color clr = Color.BLACK, fillClr = Color.WHITE;
	Shape toMove = null;
	/**
	 * Initialize the contents of the frame.
	 */
	
	private Shape contains(Point selected) {
		Shape[] shapes = engine.getShapes();
		for (int i = shapes.length -1 ; i>= 0 ; i--) {
			if (shapes[i].getClass().toString().contains("LineSegment")) {
				if (Point.distance(selected.getX(), selected.getY(), shapes[i].getProperties().get("x1"), shapes[i].getProperties().get("y1")) + Point.distance(selected.getX(), selected.getY(), shapes[i].getProperties().get("x2"), shapes[i].getProperties().get("y2")) >= shapes[i].getProperties().get("length") * 0.998 
				&& Point.distance(selected.getX(), selected.getY(), shapes[i].getProperties().get("x1"), shapes[i].getProperties().get("y1")) + Point.distance(selected.getX(), selected.getY(), shapes[i].getProperties().get("x2"), shapes[i].getProperties().get("y2")) <= shapes[i].getProperties().get("length") * 1.001 ) {
					return shapes[i];
				}
			}
			else if (shapes[i].getClass().toString().contains("Triangle")) {
				if(Math.abs((((selected.getX())*(shapes[i].getProperties().get("y2")-shapes[i].getProperties().get("y3")))+((shapes[i].getProperties().get("x2"))*(shapes[i].getProperties().get("y3")-selected.getY()))+((shapes[i].getProperties().get("x3"))*(selected.getY()-shapes[i].getProperties().get("y2"))))/2.0) + 
				 Math.abs((((shapes[i].getProperties().get("x1"))*(selected.getY()-shapes[i].getProperties().get("y3")))+((selected.getX())*(shapes[i].getProperties().get("y3")-shapes[i].getProperties().get("y1")))+((shapes[i].getProperties().get("x3"))*(shapes[i].getProperties().get("y1")-selected.getY())))/2.0) +
				 Math.abs((((shapes[i].getProperties().get("x1"))*(shapes[i].getProperties().get("y2")-selected.getY()))+((shapes[i].getProperties().get("x2"))*(selected.getY()-shapes[i].getProperties().get("y1")))+((selected.getX())*(shapes[i].getProperties().get("y1")-shapes[i].getProperties().get("y2"))))/2.0) <= shapes[i].getProperties().get("area") * 1.001
				 && Math.abs((((selected.getX())*(shapes[i].getProperties().get("y2")-shapes[i].getProperties().get("y3")))+((shapes[i].getProperties().get("x2"))*(shapes[i].getProperties().get("y3")-selected.getY()))+((shapes[i].getProperties().get("x3"))*(selected.getY()-shapes[i].getProperties().get("y2"))))/2.0) + 
				 Math.abs((((shapes[i].getProperties().get("x1"))*(selected.getY()-shapes[i].getProperties().get("y3")))+((selected.getX())*(shapes[i].getProperties().get("y3")-shapes[i].getProperties().get("y1")))+((shapes[i].getProperties().get("x3"))*(shapes[i].getProperties().get("y1")-selected.getY())))/2.0) +
				 Math.abs((((shapes[i].getProperties().get("x1"))*(shapes[i].getProperties().get("y2")-selected.getY()))+((shapes[i].getProperties().get("x2"))*(selected.getY()-shapes[i].getProperties().get("y1")))+((selected.getX())*(shapes[i].getProperties().get("y1")-shapes[i].getProperties().get("y2"))))/2.0) >= shapes[i].getProperties().get("area") * 0.998){
					return shapes[i];
				}
			}
			else if (shapes[i].getClass().toString().contains("Circle")) {
				if (Point.distance(selected.getX(), selected.getY(), shapes[i].getPosition().getX() + shapes[i].getProperties().get("width")/2, shapes[i].getPosition().getY() + shapes[i].getProperties().get("height")/2) <= shapes[i].getProperties().get("width") / 2) {
					return shapes[i];

				}
			}
			else if (shapes[i].getClass().toString().contains("Ellipse")) {
				if ((Math.sqrt(selected.getX() - (shapes[i].getPosition().getX() + shapes[i].getProperties().get("width")/2)) / Math.sqrt(shapes[i].getProperties().get("width")/2))+(Math.sqrt(selected.getY() - (shapes[i].getPosition().getY() + shapes[i].getProperties().get("height")/2)) / Math.sqrt(shapes[i].getProperties().get("height")/2)) <= 1) {
					return shapes[i];

				}
			}
			else if (shapes[i].getClass().toString().contains("Rectangle")) {
				if (Math.abs(selected.getX() - (shapes[i].getPosition().getX() + shapes[i].getProperties().get("width")/2)) <= shapes[i].getProperties().get("width")/2 
				&& Math.abs(selected.getY() - (shapes[i].getPosition().getY() + shapes[i].getProperties().get("height")/2)) <= shapes[i].getProperties().get("height")/2) {
					return shapes[i];

				}
			}
			else if (shapes[i].getClass().toString().contains("Square")) {
				if (Math.abs(selected.getX() - (shapes[i].getPosition().getX() + shapes[i].getProperties().get("width")/2)) <= shapes[i].getProperties().get("width")/2 
						&& Math.abs(selected.getY() - (shapes[i].getPosition().getY() + shapes[i].getProperties().get("height")/2)) <= shapes[i].getProperties().get("height")/2) {
					return shapes[i];
				}
			}
		}
		return null;
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0,frame.getToolkit().getScreenSize().width + 30 ,frame.getToolkit().getScreenSize().height - 30);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		Canvas canvas = new Canvas();
		canvas.setLocation(0, 101);
		canvas.setSize(1284, 543);
		frame.getContentPane().add(canvas);
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// Line action
				if (action == 1) {
					if (!secondClick) {
						firstPoint = e.getPoint();
						secondClick = true;
					}
					else {
						Shape line = new LineSegment(firstPoint, e.getPoint());
						line.setColor(clr);
						engine.addShape(line);
						secondClick = false;
						action = -1;
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
						Point topLeft = new Point();
						topLeft.setLocation(firstPoint.getX() - radius, firstPoint.getY() - radius);
						Shape circle = new Circle(radius*2, topLeft);
						circle.setColor(clr);
						circle.setFillColor(fillClr);
						engine.addShape(circle);
						secondClick = false;
						action = -1;
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
						Point topLeft = new Point();
						topLeft.setLocation(firstPoint.getX() - halfWidth, firstPoint.getY() - halfHeight);
						Shape ellipse = new Ellipse(2*halfWidth, 2*halfHeight, topLeft);
						ellipse.setColor(clr);
						ellipse.setFillColor(fillClr);
						engine.addShape(ellipse);
						secondClick = false;
						thirdClick = false;
						action = -1;
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
						triangle.setColor(clr);
						triangle.setFillColor(fillClr);
						engine.addShape(triangle);
						secondClick = false;
						thirdClick = false;
						action = -1;
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
						Shape rectangle = new Rectangle(width, height, topLeft);
						rectangle.setColor(clr);
						rectangle.setFillColor(fillClr);
						engine.addShape(rectangle);
						secondClick = false;
						action = -1;
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
						Shape square = new Square(side, topLeft);
						square.setColor(clr);
						square.setFillColor(fillClr);
						engine.addShape(square);
						secondClick = false;
						action = -1;
					}
				}
				// Remove action
				else if (action == 7) {
					Point selected = e.getPoint();
					Shape found = contains(selected);
					if (found != null) {
						engine.removeShape(found);
					}
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					action = -1;
				}
				//coloring
				else if (action == 8) {
					Point selected = e.getPoint();
					Shape found = contains(selected);
					if (found != null) {
						try {
							Shape colored = (Shape) found.clone();
							colored.setColor(clr);
							colored.setFillColor(fillClr);
							engine.updateShape(found, colored);
						} catch (CloneNotSupportedException e1) {
							e1.printStackTrace();
						}
					}
					action = -1;
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				}
				else {
					actionAttempt = true;
					firstPoint = e.getPoint();
				}
				engine.refresh(canvas.getGraphics());
			}
					
			@Override
			public void mouseReleased(MouseEvent e) {
				if (actionAttempt) {
					toMove = contains(firstPoint);
					if (toMove != null) {
						if (toMove.getClass().toString().contains("LineSegment")) {
							double x1,y1,x2,y2;
							if (firstPoint.getX() <= e.getX()) {
								x1 = toMove.getProperties().get("x1") + ( e.getX() - firstPoint.getX() );
								x2 = toMove.getProperties().get("x2") + ( e.getX() - firstPoint.getX() );
							}
							else {
								x1 = toMove.getProperties().get("x1") - ( firstPoint.getX() - e.getX() );
								x2 = toMove.getProperties().get("x2") - ( firstPoint.getX() - e.getX() );
							}
							if (firstPoint.getY() <= e.getY()) {
								y1 = toMove.getProperties().get("y1") + ( e.getY() - firstPoint.getY() );
								y2 = toMove.getProperties().get("y2") + ( e.getY() - firstPoint.getY() );
							}
							else {
								y1 = toMove.getProperties().get("y1") - ( firstPoint.getY() - e.getY() );
								y2 = toMove.getProperties().get("y2") - ( firstPoint.getY() - e.getY() );
							}
							Point first = new Point();
							Point second = new Point();
							first.setLocation(x1,y1);
							second.setLocation(x2,y2);
							Shape moved = new LineSegment(first,second);
							engine.updateShape(toMove, moved);
						}
						else if (toMove.getClass().toString().contains("Triangle")) {
							double x1,y1,x2,y2,x3,y3;
							if (firstPoint.getX() <= e.getX()) {
								x1 = toMove.getProperties().get("x1") + ( e.getX() - firstPoint.getX() );
								x2 = toMove.getProperties().get("x2") + ( e.getX() - firstPoint.getX() );
								x3 = toMove.getProperties().get("x3") + ( e.getX() - firstPoint.getX() );
							}
							else {
								x1 = toMove.getProperties().get("x1") - ( firstPoint.getX() - e.getX() );
								x2 = toMove.getProperties().get("x2") - ( firstPoint.getX() - e.getX() );
								x3 = toMove.getProperties().get("x3") - ( firstPoint.getX() - e.getX() );
							}
							if (firstPoint.getY() <= e.getY()) {
								y1 = toMove.getProperties().get("y1") + ( e.getY() - firstPoint.getY() );
								y2 = toMove.getProperties().get("y2") + ( e.getY() - firstPoint.getY() );
								y3 = toMove.getProperties().get("y3") + ( e.getY() - firstPoint.getY() );
							}
							else {
								y1 = toMove.getProperties().get("y1") - ( firstPoint.getY() - e.getY() );
								y2 = toMove.getProperties().get("y2") - ( firstPoint.getY() - e.getY() );
								y3 = toMove.getProperties().get("y3") - ( firstPoint.getY() - e.getY() );
							}
							Point first = new Point();
							Point second = new Point();
							Point third = new Point();
							first.setLocation(x1,y1);
							second.setLocation(x2,y2);
							third.setLocation(x3,y3);
							Shape moved = new Triangle(first,second,third);
							engine.updateShape(toMove, moved);
						}
						else {
							double x1,y1;
							if (firstPoint.getX() <= e.getX()) {
								x1 = toMove.getPosition().getX() + ( e.getX() - firstPoint.getX() );
							}
							else {
								x1 = toMove.getPosition().getX() - ( firstPoint.getX() - e.getX() );
							}
							if (firstPoint.getY() <= e.getY()) {
								y1 = toMove.getPosition().getY() + ( e.getY() - firstPoint.getY() );
							}
							else {
								y1 = toMove.getPosition().getY() - ( firstPoint.getY() - e.getY() );
							}
							Point newPosition = new Point();
							newPosition.setLocation(x1,y1);
							try {
								Shape moved = (Shape) toMove.clone();
								moved.setPosition(newPosition);
								engine.updateShape(toMove, moved);
							} catch (CloneNotSupportedException e1) {
								e1.printStackTrace();
							}
						}
						toMove = null;
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					}
					
				}
				actionAttempt = false;
				engine.refresh(canvas.getGraphics());
			}
		});
		
		// Start Buttons
		JButton btnLine = new JButton("Line Segment");
		btnLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				secondClick = false;
				action = 1;
			}
		});
		btnLine.setBounds(304, 650, 112, 23);
		frame.getContentPane().add(btnLine);
		
		JButton btnCircle = new JButton("Circle");
		btnCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				secondClick = false;
				action = 2;
			}
		});
		btnCircle.setBounds(416, 650, 112, 23);
		frame.getContentPane().add(btnCircle);
		
		JButton btnEllipse = new JButton("Ellipse");
		btnEllipse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				secondClick = false;
				thirdClick = false;
				action = 3;
			}
		});
		btnEllipse.setBounds(528, 650, 112, 23);
		frame.getContentPane().add(btnEllipse);
		
		JButton btnTriangle = new JButton("Triangle");
		btnTriangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				secondClick = false;
				thirdClick = false;
				action = 4;
			}
		});
		btnTriangle.setBounds(640, 650, 112, 23);
		frame.getContentPane().add(btnTriangle);
		
		JButton btnRect = new JButton("Rectangle");
		btnRect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				secondClick = false;
				action = 5;
			}
		});
		btnRect.setBounds(752, 650, 112, 23);
		frame.getContentPane().add(btnRect);
		
		JButton btnSqre = new JButton("Square");
		btnSqre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				secondClick = false;
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
		
		JButton btnColor = new JButton("");
		btnColor.setBackground(Color.BLACK);
		btnColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clr = JColorChooser.showDialog(null, "Choose the stroke color", Color.BLACK);
				btnColor.setBackground(clr);
			}
		});
		btnColor.setBounds(640, 11, 50, 50);
		frame.getContentPane().add(btnColor);
		
		JButton btnFillColor = new JButton("");
		btnFillColor.setBackground(Color.WHITE);
		btnFillColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillClr = JColorChooser.showDialog(null, "Choose the fill color", Color.WHITE);
				btnFillColor.setBackground(fillClr);
			}
		});
		btnFillColor.setBounds(702, 11, 50, 50);
		frame.getContentPane().add(btnFillColor);
		
		JButton btnUndo = new JButton("Undo");
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				engine.undo();
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				engine.refresh(canvas.getGraphics());
			}
		});
		btnUndo.setBounds(1290, 282, 70, 23);
		frame.getContentPane().add(btnUndo);
		
		JButton btnRedo = new JButton("Redo");
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				engine.redo();
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				engine.refresh(canvas.getGraphics());
			}
		});
		btnRedo.setBounds(1290, 319, 70, 23);
		frame.getContentPane().add(btnRedo);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(1290, 356, 70, 23);
		frame.getContentPane().add(btnSave);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.setBounds(1290, 393, 70, 23);
		frame.getContentPane().add(btnLoad);
		
		JButton btnSetColor = new JButton("Set Color");
		btnSetColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action = 8 ;
			}
		});
		btnSetColor.setBounds(640, 72, 112, 23);
		frame.getContentPane().add(btnSetColor);
	}
}
