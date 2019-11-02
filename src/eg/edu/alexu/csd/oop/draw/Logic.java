package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
		shapes.removeIf(element -> (shape.getPosition() == element.getPosition()));
	}

	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		shapes.add(newShape);
		undoShapes.push(newShape);
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
		else if (!shapes.isEmpty()){
			shapes.clear();
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
		/*File file = new File(path, "save.json");
		try {
			FileWriter writer = new FileWriter(file);
			writer.write("{\"shapes\": " + System.lineSeparator());
			for (int i = 0; i < shapes.size(); i++) {
				writer.write("[{\"className\": \"" + shapes.get(i).getClass().toString() + "\", ");
				writer.write("\"position\": \"" + shapes.get(i).getPosition().toString() + "\", ");
				writer.write("\"color\": \"" + shapes.get(i).getColor().toString() + "\", ");
				writer.write("\"fillColor\": \"" + shapes.get(i).getFillColor().toString() + "\", ");
				writer.write("\"width\": \"" + shapes.get(i).getProperties().get("width").toString() + "\", ");
				writer.write("\"height\": \"" + shapes.get(i).getProperties().get("height").toString() + "\"}]");
				if (i != shapes.size() - 1) {
					writer.write("," + System.lineSeparator());
				}
				else {
					writer.write(System.lineSeparator());
				}
				
			}
			writer.write("}");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	@Override
	public void load(String path) {

	}

}
