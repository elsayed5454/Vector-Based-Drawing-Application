package eg.edu.alexu.csd.oop.draw;

public class Square extends Polygons {
	
	public Square(double side) {
		this.prop.put("width", side);
		this.prop.put("height", side);
		setProperties(prop);
	}

}
