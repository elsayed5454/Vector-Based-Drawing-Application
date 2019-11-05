package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class LineSegment extends GeometricShapes {
	
	public LineSegment() {
		this.prop.put("length" , 0.0);
		this.prop.put("x1" , 0.0);
		this.prop.put("y1" , 0.0);
		this.prop.put("x2" , 0.0);
		this.prop.put("y2" , 0.0);
		setProperties(prop);
		setColor(clr);
		setFillColor(fillClr);
	}
	
	public LineSegment(Point point1, Point point2) {
		this.setPosition(point1);
		this.prop.put("length" , Point.distance(point1.getX(), point1.getY(), point2.getX(), point2.getY()));
		this.prop.put("x1" , point1.getX());
		this.prop.put("y1" , point1.getY());
		this.prop.put("x2" , point2.getX());
		this.prop.put("y2" , point2.getY());
		this.setProperties(prop);
	}
	
	@Override
	public void draw(Graphics canvas) {
		((Graphics2D)canvas).setStroke(new BasicStroke(5f));
		((Graphics2D)canvas).setColor(getColor());
		((Graphics2D)canvas).drawLine(this.getProperties().get("x1").intValue() ,this.getProperties().get("y1").intValue(), this.getProperties().get("x2").intValue(), this.getProperties().get("y2").intValue());
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException{
		Shape cloned = new LineSegment();
		cloned.setColor(this.getColor());
		cloned.setFillColor(this.getFillColor());
		cloned.setPosition(this.getPosition());
		cloned.setProperties(this.getProperties());
		return cloned;
	}
}
