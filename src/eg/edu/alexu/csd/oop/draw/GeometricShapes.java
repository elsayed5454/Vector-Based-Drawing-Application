package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class GeometricShapes implements Shape {

	protected Point point = new Point();
	protected Map<String, Double> prop = new HashMap<String, Double>();
	protected Color clr;
	protected Color fillClr;
	
	public GeometricShapes() {
		this.prop.put("width", 0.0);
		this.prop.put("height", 0.0);
		this.setProperties(prop);
	}
	
	@Override
	public void setPosition(Point position) {
		this.point = position;
	}

	@Override
	public Point getPosition() {
		return this.point;
	}

	@Override
	public void setProperties(Map<String, Double> properties) {
		this.prop = properties;
	}

	@Override
	public Map<String, Double> getProperties() {
		return this.prop;
	}

	@Override
	public void setColor(Color color) {
		this.clr = color;
	}

	@Override
	public Color getColor() {
		return this.clr;
	}

	@Override
	public void setFillColor(Color color) {
		this.fillClr = color;
	}

	@Override
	public Color getFillColor() {
		return this.fillClr;
	}

	@Override
	public void draw(Graphics canvas) {
		
	}

	@Override
	public Object clone() throws CloneNotSupportedException{
		Shape cloned = this;
		cloned.setPosition(this.getPosition());
		cloned.setProperties(this.getProperties());
		cloned.setColor(this.getColor());
		cloned.setFillColor(this.getFillColor());
		return cloned;
	}

}
