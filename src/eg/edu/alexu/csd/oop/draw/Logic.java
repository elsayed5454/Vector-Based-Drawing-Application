package eg.edu.alexu.csd.oop.draw;
 
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
 
 
public class Logic implements DrawingEngine {
 
    private ArrayList<Shape> shapes = new ArrayList<Shape>();
    private ArrayList<ArrayList<Shape>> undoShapes = new ArrayList<ArrayList<Shape>>(Arrays.asList(new ArrayList<Shape>()));
    private ArrayList<ArrayList<Shape>> redoShapes = new ArrayList<ArrayList<Shape>>(); 
    private List<Class<? extends Shape>> supportedShapes = new ArrayList<Class<? extends Shape>>
    (Arrays.asList(LineSegment.class,Circle.class,Ellipse.class,Rectangle.class,Square.class,Triangle.class));
    		
    @Override
    public void refresh(Graphics canvas) {
        for (Shape shape : shapes) {
            shape.draw(canvas);
        }
    }
 
    @Override
    public void addShape(Shape shape) {
        shapes.add(shape);
        if (undoShapes.size() <= 20) {
            undoShapes.add(new ArrayList<Shape>(shapes));
        }
        else {
            undoShapes.remove(0);
            undoShapes.add(new ArrayList<Shape>(shapes));
        }
    }
 
    @Override
    public void removeShape(Shape shape) {
        shapes.remove(shape);
        if (undoShapes.size() <= 20) {
            undoShapes.add(new ArrayList<Shape>(shapes));
        }
        else {
            undoShapes.remove(0);
            undoShapes.add(new ArrayList<Shape>(shapes));
        }
    }
 
    @Override
    public void updateShape(Shape oldShape, Shape newShape) {
        shapes.set(shapes.indexOf(oldShape), newShape);
        if (undoShapes.size() <= 20) {
            undoShapes.add(new ArrayList<Shape>(shapes));
        }
        else {
            undoShapes.remove(0);
            undoShapes.add(new ArrayList<Shape>(shapes));
        }
    }
 
    @Override
    public Shape[] getShapes() {
        return shapes.toArray(new Shape[shapes.size()]);
    }
 
	@SuppressWarnings("unchecked")
	@Override
    public List<Class<? extends Shape>> getSupportedShapes() {
		Package pack = Shape.class.getPackage();
		String path = pack.getName();
    	path = path.replace('.', '/');
		ClassLoader loader = Shape.class.getClassLoader();
		try {
			Enumeration<URL> resources = loader.getResources(path);
			List<File> dirs = new ArrayList<File>();
	        while (resources.hasMoreElements()) {
	            URL resource = resources.nextElement();
	            dirs.add(new File(resource.getFile()));
	        }
	        File[] files = dirs.get(0).listFiles();
	        for (File file : files) {
	        	if (file.getName().contains(".jar")) {
	        		URL url = new URL(String.format("jar:file:%s!/", file.getName()));
	        		JarURLConnection connection = (JarURLConnection) url.openConnection();
	    			JarFile jarFile = connection.getJarFile();	        	
	    			Enumeration<JarEntry> entries =  jarFile.entries();
	    			while (entries.hasMoreElements()) {
	    				JarEntry e = entries.nextElement();
	    				if (e.getName().endsWith(".class")) {
	    					String name = e.getName().replace(".class", "").replaceAll("/", ".");
	    					Class<?> clazz = Class.forName(name);
	    					if (Shape.class.isAssignableFrom(clazz) && !Modifier.isInterface(clazz.getModifiers()) 
	    			        && !Modifier.isAbstract(clazz.getModifiers()) && Modifier.isPublic(clazz.getModifiers())
	    			        && !supportedShapes.contains(clazz)) {
	    			        	supportedShapes.add((Class<? extends Shape>) clazz);
	    			        }
	    				}
	    			}
	        	}
	        }		
	    } catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        return supportedShapes;
    }
 
    @Override
    public void undo() {
        if (undoShapes.size() > 1) {
            if (redoShapes.size() <= 20) {
                redoShapes.add(new ArrayList<Shape>(undoShapes.remove(undoShapes.size()-1)));
            }
            else {
                redoShapes.remove(0);
                redoShapes.add(new ArrayList<Shape>(undoShapes.remove(undoShapes.size()-1)));
            }
            shapes = new ArrayList<Shape>(undoShapes.get(undoShapes.size()-1));
        }
    }
 
    @Override
    public void redo() {
        if (!redoShapes.isEmpty()) {
            shapes = new ArrayList<Shape>(redoShapes.get(redoShapes.size()-1));
            if (undoShapes.size() <= 20) {
                undoShapes.add(new ArrayList<Shape>(redoShapes.remove(redoShapes.size()-1)));
            }
            else {
                undoShapes.remove(0);
                undoShapes.add(new ArrayList<Shape>(redoShapes.remove(redoShapes.size()-1)));
            }
        }
    }
 
	@Override
    public void save(String path) {
    	File file = new File(path);
    	if (path.endsWith("json")) {
            JSONParser jsonParser = new JSONParser();
            jsonParser.JSONSave(file, shapes);
    	}
    	else {
    		XMLParser xmlParser = new XMLParser();
    		xmlParser.XMLSave(file, shapes);
    	}
    }
 
    @Override
    public void load(String path) {
    	File file = new File(path);
    	shapes.clear();
		undoShapes.clear();
		redoShapes.clear();
    	if (path.endsWith("json")) {
    		JSONParser jsonParser = new JSONParser();
    		shapes = new ArrayList<Shape>(jsonParser.JSONLoad(file));
    	}
    	else {
    		XMLParser xmlParser = new XMLParser();
    		shapes = new ArrayList<Shape>(xmlParser.XMLLoad(file));
    	}
    }
 
}	