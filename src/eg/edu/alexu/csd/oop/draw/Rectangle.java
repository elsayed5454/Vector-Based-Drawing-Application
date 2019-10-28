package eg.edu.alexu.csd.oop.draw;

public class Rectangle extends Polygons {
	
	public Rectangle(double length, double width) {
		this.prop.put("length", length);
		this.prop.put("width", width);
		setProperties(prop);
	}

}
