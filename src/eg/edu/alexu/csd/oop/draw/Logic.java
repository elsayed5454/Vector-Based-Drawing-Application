package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Logic implements DrawingEngine {

	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private Stack<ArrayList<Shape>> undoShapes = new Stack<ArrayList<Shape>>();
	private Stack<ArrayList<Shape>> redoShapes = new Stack<ArrayList<Shape>>();

	
	@Override
	public void refresh(Graphics canvas) {
		for (Shape shape : shapes) {
			shape.draw(canvas);
		}
	}

	@Override
	public void addShape(Shape shape) {
		shapes.add(shape);
		if (undoShapes.size() < 20) {
			undoShapes.push(new ArrayList<Shape>(shapes));
		}
		else {
			undoShapes.remove(0);
			undoShapes.push(new ArrayList<Shape>(shapes));
		}
	}

	@Override
	public void removeShape(Shape shape) {
		shapes.remove(shape);
		if (undoShapes.size() < 20) {
			undoShapes.push(new ArrayList<Shape>(shapes));
		}
		else {
			undoShapes.remove(0);
			undoShapes.push(new ArrayList<Shape>(shapes));
		}
	}

	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		shapes.remove(oldShape);
		shapes.add(newShape);
		if (undoShapes.size() < 20) {
			undoShapes.push(new ArrayList<Shape>(shapes));
		}
		else {
			undoShapes.remove(0);
			undoShapes.push(new ArrayList<Shape>(shapes));
		}
	}

	@Override
	public Shape[] getShapes() {
		return shapes.toArray(new Shape[shapes.size()]);
	}

	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		return null;
	}

	@Override
	public void undo() {
		if (!undoShapes.isEmpty()) {
			if (redoShapes.size() < 20) {
				redoShapes.push(new ArrayList<Shape>(undoShapes.pop()));
			}
			else {
				redoShapes.remove(0);
				redoShapes.push(new ArrayList<Shape>(undoShapes.pop()));
			}
			if (!undoShapes.isEmpty()) {
				shapes = new ArrayList<Shape>(undoShapes.peek());
			}
			else {
				shapes = new ArrayList<Shape>();
			}
		}
	}

	@Override
	public void redo() {
		if (!redoShapes.isEmpty()) {
			shapes = new ArrayList<Shape>(redoShapes.peek());
			if (undoShapes.size() < 20) {
				undoShapes.push(new ArrayList<Shape>(redoShapes.pop()));
			}
			else {
				undoShapes.remove(0);
				undoShapes.push(new ArrayList<Shape>(redoShapes.pop()));
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
