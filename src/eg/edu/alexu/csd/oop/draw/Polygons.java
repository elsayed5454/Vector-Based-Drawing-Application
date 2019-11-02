package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class Polygons extends GeometricShapes {
	
	public Polygons() {
	}
	
	@Override
	public void draw(Graphics canvas) {
		((Graphics2D)canvas).setStroke(new BasicStroke(2.0f));
		((Graphics2D)canvas).setColor(getColor());
		((Graphics2D)canvas).setColor(getFillColor());
		((Graphics2D)canvas).drawRect((int)getPosition().getX(), (int)getPosition().getY(), this.getProperties().get("width").intValue(), this.getProperties().get("height").intValue());
		((Graphics2D)canvas).fillRect((int)getPosition().getX(), (int)getPosition().getY(), this.getProperties().get("width").intValue(), this.getProperties().get("height").intValue());
	}
}