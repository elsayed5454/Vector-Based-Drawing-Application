package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

public class Polygons extends GeometricShapes {
	private Map<String, Double> prop = new HashMap<String, Double>();
	
	public Polygons() {
		this.prop.put("length", 0.0);
		this.prop.put("width", 0.0);
		setProperties(prop);
	}
	
	// Rectangle declaration
	public Polygons(double length, double width) {
		this.prop.put("length", length);
		this.prop.put("width", width);
		setProperties(prop);
	}
	
	// Square declaration
	public Polygons(double side) {
		this.prop.put("length", side);
		this.prop.put("width", side);
		setProperties(prop);
	}
	
	@Override
	public void draw(Graphics canvas) {
		((Graphics2D)canvas).setStroke(new BasicStroke(2.0f));
		((Graphics2D)canvas).fillRect((int)getPosition().getX(), (int)getPosition().getY(), this.getProperties().get("length").intValue(), this.getProperties().get("width").intValue());
		((Graphics2D)canvas).setColor(getColor());
		((Graphics2D)canvas).setColor(getFillColor());
		((Graphics2D)canvas).drawRect((int)getPosition().getX(), (int)getPosition().getY(), this.getProperties().get("length").intValue(), this.getProperties().get("width").intValue());
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape cloned = new Polygons();
		cloned.setColor(this.getColor());
		cloned.setFillColor(this.getFillColor());
		cloned.setPosition(this.getPosition());
		cloned.setProperties(this.getProperties());
		return cloned;
	}
}
