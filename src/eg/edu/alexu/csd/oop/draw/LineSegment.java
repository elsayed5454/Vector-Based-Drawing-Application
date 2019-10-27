package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

public class LineSegment extends GeometricShapes {

	private Map<String, Double> prop = new HashMap<String, Double>() ;
	
	public LineSegment() {
		this.prop.put("length", 0.0);
		setProperties(prop);
	}
	
	@Override
	public void draw(Graphics canvas) {
		((Graphics2D)canvas).setStroke(new BasicStroke(5));
		((Graphics2D)canvas).drawLine((int)getPosition().getX(), (int)getPosition().getY(), (int)getPosition().getX(), (int)getPosition().getY());
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
