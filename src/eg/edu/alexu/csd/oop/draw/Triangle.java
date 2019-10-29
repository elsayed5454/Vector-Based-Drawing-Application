package eg.edu.alexu.csd.oop.draw;

<<<<<<< HEAD
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

||||||| merged common ancestors
=======
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

>>>>>>> 78988d26ab5fb3a0e1bec269420983ff97717d53
public class Triangle extends Polygons {
	
	private int[] x;
	private int[] y;

<<<<<<< HEAD
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
||||||| merged common ancestors
=======
	public Triangle () {
	this.prop.put("x1", getPosition().getX());
	this.prop.put("y1", getPosition().getY());
	this.prop.put("x2", getPosition().getX());
	this.prop.put("y2", getPosition().getY());
	this.prop.put("x3", getPosition().getX());
	this.prop.put("y3", getPosition().getY());
	setProperties(prop);
	}
	
	@Override
	public void draw(Graphics canvas) {
		((Graphics2D)canvas).setStroke(new BasicStroke(2.0f));
		int[] x = { getProperties().get("x1").intValue(), getProperties().get("x2").intValue(), getProperties().get("x3").intValue() } ;
		int[] y = { getProperties().get("y1").intValue(), getProperties().get("y2").intValue(), getProperties().get("y3").intValue() } ;
		((Graphics2D)canvas).fillPolygon( x , y , 3);
		((Graphics2D)canvas).setColor(getColor());
		((Graphics2D)canvas).setColor(getFillColor());
		((Graphics2D)canvas).drawPolygon( x , y , 3);
	}
>>>>>>> 78988d26ab5fb3a0e1bec269420983ff97717d53
}
