package eg.edu.alexu.csd.oop.draw;

public class Rectangle extends Polygons {
	
	public Rectangle(double width, double height) {
		this.prop.put("width", width);
		this.prop.put("height", height);
		setProperties(prop);
	}

}
