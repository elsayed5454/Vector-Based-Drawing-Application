package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


public class Logic implements DrawingEngine {

	private List<Shape> shapes = new ArrayList<Shape>();
	
	@Override
	public void refresh(Graphics canvas) {
		for (Shape shape : shapes) {
			shape.draw(canvas);
		}
	}

	@Override
	public void addShape(Shape shape) {
		shapes.add(shape);
	}

	@Override
	public void removeShape(Shape shape) {
		shapes.remove(shape);
	}

	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		shapes.set(shapes.indexOf(oldShape), newShape);
	}

	@Override
	public Shape[] getShapes() {
		Shape[] drawnShapes = new Shape[shapes.size()];
		return shapes.toArray(drawnShapes);
	}

	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		return null;
	}

	@Override
	public void undo() {

	}

	@Override
	public void redo() {

	}

	@Override
	public void save(String path) {

	}

	@Override
	public void load(String path) {

	}

}
