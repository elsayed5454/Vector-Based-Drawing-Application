package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class Triangle extends Polygons {
	
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
}
