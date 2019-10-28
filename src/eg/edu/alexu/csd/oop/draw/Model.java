package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


public class Model implements DrawingEngine {
	
	private List<Shape> shapes = new ArrayList<Shape>(20);
	
	@Override
	public void refresh(Graphics canvas) {
		
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
		Shape[] tmp = new Shape[20];
		return shapes.toArray(tmp);
	}

	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load(String path) {
		// TODO Auto-generated method stub
		
	}

}
