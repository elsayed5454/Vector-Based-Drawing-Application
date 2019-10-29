package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Triangle extends Polygons {
	
	private int[] x;
	private int[] y;

	public Triangle(Point p1, Point p2, Point p3) {
		
		x[0] = p1.x;
		x[1] = p2.x;
		x[2] = p3.x;
		y[0] = p1.y;
		y[1] = p2.y;
		y[2] = p3.y;
	}
	
	@Override
	public void draw(Graphics canvas) {
		((Graphics2D)canvas).setStroke(new BasicStroke(2.0f));
		((Graphics2D)canvas).drawPolygon(x, y, 3);
		((Graphics2D)canvas).setColor(getColor());
		((Graphics2D)canvas).setColor(getFillColor());
		((Graphics2D)canvas).fillPolygon(x, y, 3);
	}
}
