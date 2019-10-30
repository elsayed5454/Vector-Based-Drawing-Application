package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Logic implements DrawingEngine {

	private List<Shape> shapes = new ArrayList<Shape>();
	private Stack<Shape> undoShapes = new Stack<Shape>();
	private Stack<Shape> redoShapes = new Stack<Shape>();	
	
	@Override
	public void refresh(Graphics canvas) {
		for (Shape shape : shapes) {
			shape.draw(canvas);
		}
	}

	@Override
	public void addShape(Shape shape) {
		shapes.add(shape);
		undoShapes.push(shape);
	}

	@Override
	public void removeShape(Shape shape) {
		if (shapes.contains(shape)) {
			shapes.remove(shape);
			undoShapes.push(shape);
		}
	}

	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		if (shapes.contains(oldShape)) {
			shapes.add(newShape);
			undoShapes.push(newShape);
		}
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
		if (!undoShapes.isEmpty()) {
			Shape shape = undoShapes.pop();
			redoShapes.push(shape);
			//if it is found, then it needs to be removed and go back on step
			if (shapes.contains(shape)) {
				shapes.remove(shape);
			}
			//if it isn't found, then it was just removed and needs to get back
			else {
				shapes.add(shape);
			}
		}
	}

	@Override
	public void redo() {
		if (!redoShapes.isEmpty()) {
			Shape shape = redoShapes.pop();
			undoShapes.push(shape);
			if (shapes.contains(shape)) {
				shapes.remove(shape);
			}
			else {
				shapes.add(shape);
			}
		}
		
	}

	@Override
	public void save(String path) {

	}

	@Override
	public void load(String path) {

	}

}
