package eg.edu.alexu.csd.oop.draw;

public class Circle extends EllipticalShapes {

	public Circle (double radius) {
		this.prop.put("width", radius);
		this.prop.put("height", radius);
		setProperties(prop);
	}
}
