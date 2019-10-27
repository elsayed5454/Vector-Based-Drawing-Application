package eg.edu.alexu.csd.oop.draw;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Polygons extends GeometricShapes {
	
	@Override
	public void draw(Graphics canvas) {
		((Graphics2D)canvas).setStroke(new BasicStroke(5));
		((Graphics2D)canvas).setColor(getColor());
		((Graphics2D)canvas).fillPolygon(null);
		((Graphics2D)canvas).setColor(getFillColor());
	}
}
