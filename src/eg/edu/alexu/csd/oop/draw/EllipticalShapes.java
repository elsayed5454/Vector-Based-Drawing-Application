package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class EllipticalShapes extends GeometricShapes {
	
	public EllipticalShapes () {
	}	
	
	@Override
	public void draw(Graphics canvas) {
		((Graphics2D)canvas).setStroke(new BasicStroke(5f));
		((Graphics2D)canvas).setColor(getColor());
		((Graphics2D)canvas).setColor(getFillColor());
		((Graphics2D)canvas).drawOval((int)getPosition().getX(), (int)getPosition().getY(), getProperties().get("width").intValue() , getProperties().get("height").intValue());
		((Graphics2D)canvas).fillOval((int)getPosition().getX(), (int)getPosition().getY(), getProperties().get("width").intValue() , getProperties().get("height").intValue());
	}
}
