package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

public class LineSegment extends GeometricShapes {

	private Map<String, Double> prop = new HashMap<String, Double>();
	private Point point1 = new Point();
	private Point point2 = new Point();
	
	public LineSegment() {
		this.prop.put("length", 0.0);
		setProperties(prop);
	}
	
	public LineSegment(Point point1, Point point2) {
		this.point1 = point1;
		this.point2 = point2;
		double length = Point2D.distance(point1.x, point1.y, point2.x, point2.y);
		this.prop.put("length", length);
		setProperties(prop);
	}
	
	@Override
	public void draw(Graphics canvas) {
		((Graphics2D)canvas).setStroke(new BasicStroke(0.2f));
		((Graphics2D)canvas).drawLine(point1.x, point1.y, point2.x, point2.y);
		((Graphics2D)canvas).setColor(getColor());
	}

	@Override
	public Object clone() throws CloneNotSupportedException{
		Shape cloned = new LineSegment();
		cloned.setPosition(getPosition());
		cloned.setColor(getColor());
		cloned.setProperties(getProperties());
		return cloned;
	}
}
