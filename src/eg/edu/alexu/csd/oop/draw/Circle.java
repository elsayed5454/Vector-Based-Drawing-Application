package eg.edu.alexu.csd.oop.draw;

public class Circle extends EllipticalShapes {

	public Circle (double diameter) {
		this.prop.put("width", diameter);
		this.prop.put("height", diameter);
		setProperties(prop);
	}
}
