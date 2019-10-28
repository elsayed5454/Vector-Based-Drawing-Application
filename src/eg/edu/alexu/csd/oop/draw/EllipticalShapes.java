package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class EllipticalShapes extends GeometricShapes {
	
	public EllipticalShapes () {
		this.prop.put("width", 0.0);
		this.prop.put("height", 0.0);
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
