package eg.edu.alexu.csd.oop.draw;

public class Ellipse extends EllipticalShapes {
	
	public Ellipse (double width, double height) {
		this.prop.put("width", width);
		this.prop.put("height", height);
		setProperties(prop);
	}

}
