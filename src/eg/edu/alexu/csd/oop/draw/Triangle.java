package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;


public class Triangle extends Polygons {
	
	public Triangle(Point p1, Point p2, Point p3) {
		this.prop.put("x1", p1.getX());
		this.prop.put("y1", p1.getY());
		this.prop.put("x2", p2.getX());
		this.prop.put("y2", p2.getY());
		this.prop.put("x3", p3.getX());
		this.prop.put("y3", p3.getY());
		
		double minX = Math.min(p1.getX(), p2.getX());
		minX = Math.min(minX, p3.getX());
		double maxX = Math.max(p1.getX(), p2.getX());
		maxX = Math.max(maxX, p3.getX());
		
		double minY = Math.min(p1.getY(), p2.getY());
		minY = Math.min(minY, p3.getY());
		double maxY = Math.max(p1.getY(), p2.getY());
		maxY = Math.max(maxY, p3.getY());
		
		Point topLeft = new Point();
		topLeft.setLocation(minX, minY);
		this.setPosition(topLeft);
		this.prop.put("width", maxX - minX);
		this.prop.put("height", maxY - minY);
		
		this.setPosition(p1);
		this.setProperties(prop);
	}
	
	@Override
	public void draw(Graphics canvas) {
		((Graphics2D)canvas).setStroke(new BasicStroke(2.0f));
		int[] x = {getProperties().get("x1").intValue(), getProperties().get("x2").intValue(), getProperties().get("x3").intValue()};
		int[] y = {getProperties().get("y1").intValue(), getProperties().get("y2").intValue(), getProperties().get("y3").intValue()};
		((Graphics2D)canvas).setColor(getColor());
		((Graphics2D)canvas).setColor(getFillColor());
		((Graphics2D)canvas).drawPolygon(x, y, 3);
		((Graphics2D)canvas).fillPolygon(x, y, 3);
	}
}
