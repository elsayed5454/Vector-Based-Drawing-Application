package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

public class EllipticalShapes extends GeometricShapes {
	private Map<String, Double> prop = new HashMap<String, Double>();
	
	public EllipticalShapes () {
		this.prop.put("width", 0.0);
		this.prop.put("height", 0.0);
		setProperties(prop);
	}
	
	// Ellipse declaration
	public EllipticalShapes (double width, double height) {
		this.prop.put("width", width);
		this.prop.put("height", height);
		setProperties(prop);
	}
	
	// Circle declaration
	public EllipticalShapes (double radius) {
		this.prop.put("width", radius);
		this.prop.put("height", radius);
		setProperties(prop);
	}
	
	@Override
	public void draw(Graphics canvas) {
		((Graphics2D)canvas).setStroke(new BasicStroke(2.0f));
		((Graphics2D)canvas).fillOval((int)getPosition().getX(), (int)getPosition().getY(), getProperties().get("width").intValue() , getProperties().get("height").intValue());
		((Graphics2D)canvas).setColor(getColor());
		((Graphics2D)canvas).setColor(getFillColor());
		((Graphics2D)canvas).drawOval((int)getPosition().getX(), (int)getPosition().getY(), getProperties().get("width").intValue() , getProperties().get("height").intValue());
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape cloned = new EllipticalShapes();
		cloned.setColor(this.getColor());
		cloned.setFillColor(this.getFillColor());
		cloned.setPosition(this.getPosition());
		cloned.setProperties(this.getProperties());
		return cloned;
	}
}
