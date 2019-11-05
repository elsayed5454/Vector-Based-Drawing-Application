package eg.edu.alexu.csd.oop.draw;
 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
 
 
public class Logic implements DrawingEngine {
 
    private ArrayList<Shape> shapes = new ArrayList<Shape>();
    private Stack<ArrayList<Shape>> undoShapes = new Stack<ArrayList<Shape>>();
    private Stack<ArrayList<Shape>> redoShapes = new Stack<ArrayList<Shape>>(); 
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
 
    @SuppressWarnings("unchecked")
	@Override
    public void save(String path) {
    	File file = new File(path);
    	if (path.endsWith("json")) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write("{\"shapes\": [" + System.lineSeparator());
                for (int i = 0; i < shapes.size(); i++) {
                    writer.write("{\"className\": \"" + shapes.get(i).getClass().getSimpleName() + "\", ");
                    writer.write("\"x\": \"" + Double.toString(shapes.get(i).getPosition().getX()) + "\", ");
                    writer.write("\"y\": \"" + Double.toString(shapes.get(i).getPosition().getY()) + "\", ");
                    writer.write("\"color\": \"" + Integer.toString(shapes.get(i).getColor().getRGB()) + "\", ");
                    writer.write("\"fillColor\": \"" + Integer.toString(shapes.get(i).getFillColor().getRGB()) + "\", ");
                    Iterator<?> it = shapes.get(i).getProperties().entrySet().iterator();
                    while (it.hasNext()) {
                    	Map.Entry<String, Double> pair = (Map.Entry<String, Double>)it.next();
                    	writer.write("\""+ pair.getKey() +"\": \"" + Double.toString(pair.getValue()) + "\"");
                    	if (it.hasNext()) {
                    		writer.write(", ");
                    	}
					}
                    writer.write("}");
                    if (i != shapes.size() - 1) {
                        writer.write("," + System.lineSeparator());
                    }
                    else {
                        writer.write(System.lineSeparator());
                    }
                }
                writer.write("]}");
                writer.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
    	}
    	else {
    		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder;
    		Element shape, name, x, y, clr, fillClr, property;
			try {
				docBuilder = docFactory.newDocumentBuilder();
				Document dom = docBuilder.newDocument();
				Element root = dom.createElement("shapes");
				for (int i = 0; i < shapes.size(); i++) {
					shape = dom.createElement("Shape");
					
					name = dom.createElement("name");
					name.appendChild(dom.createTextNode(shapes.get(i).getClass().getSimpleName()));
					shape.appendChild(name);
					
					x = dom.createElement("x");
					x.appendChild(dom.createTextNode(Double.toString(shapes.get(i).getPosition().getX())));
					shape.appendChild(x);
					
					y = dom.createElement("y");
					y.appendChild(dom.createTextNode(Double.toString(shapes.get(i).getPosition().getY())));
					shape.appendChild(y);
					
					clr = dom.createElement("clr");
					clr.appendChild(dom.createTextNode(Integer.toString(shapes.get(i).getColor().getRGB())));
					shape.appendChild(clr);
					
					fillClr = dom.createElement("fillClr");
					fillClr.appendChild(dom.createTextNode(Integer.toString(shapes.get(i).getFillColor().getRGB())));
					shape.appendChild(fillClr);
					
					for (Map.Entry<String, Double> mapEntry : shapes.get(i).getProperties().entrySet()) {
						property = dom.createElement(mapEntry.getKey());
						property.appendChild(dom.createTextNode(mapEntry.getValue().toString()));
						shape.appendChild(property);
					}
					root.appendChild(shape);
				}
				dom.appendChild(root);
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				try {
					Transformer transformer = transformerFactory.newTransformer();
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
					DOMSource domSource = new DOMSource(dom);
					transformer.transform(domSource, new StreamResult(file));
				} catch (TransformerConfigurationException e) {
					System.out.println(e.getMessage());
				} catch (TransformerException e) {
					System.out.println(e.getMessage());
				}
				
			} catch (ParserConfigurationException e) {
				System.out.println(e.getMessage());
			}
    	}
    }
    
    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }
 
    @Override
    public void load(String path) {
    	File file = new File(path);
    	shapes.clear();
		undoShapes.clear();
		redoShapes.clear();
		Shape shape = null;
		
    	if (path.endsWith("json")) {
    		try {
				Scanner sc = new Scanner(file);
				String s = sc.nextLine();
				while (sc.hasNext()) {
					s = sc.nextLine();
					if (s.equals("]}")) {
						break;
					}
					int nthOccurrence = 3;
					ArrayList<String> basicProp = new ArrayList<String>();
					for (int k = 0; k < 5; k++) {
						String input = s.substring(ordinalIndexOf(s, "\"", nthOccurrence)+1, ordinalIndexOf(s, "\"", nthOccurrence + 1));
						nthOccurrence += 4;
						basicProp.add(input);
					}
					nthOccurrence -= 2;
					
					if (basicProp.get(0).equals("Circle")) {
						shape = new Circle();
					}
					else if (basicProp.get(0).equals("Ellipse")) {
						shape = new Ellipse();
					}
					else if (basicProp.get(0).equals("LineSegment")) {
						shape = new LineSegment();
					}
					else if (basicProp.get(0).equals("Rectangle")) {
						shape = new Rectangle();
					}
					else if (basicProp.get(0).equals("Square")) {
						shape = new Square();
					}
					else if (basicProp.get(0).equals("Triangle")) {
						shape = new Triangle();
					}
					Point position = new Point();
					position.setLocation(Double.parseDouble(basicProp.get(1)), Double.parseDouble(basicProp.get(2)));
					shape.setPosition(position);
					shape.setColor(new Color(Integer.parseInt(basicProp.get(3))));
					shape.setFillColor(new Color(Integer.parseInt(basicProp.get(4))));
					Map<String, Double> prop = new HashMap<String, Double>();
					int i = ordinalIndexOf(s, "\"", nthOccurrence+1);
					while (s.charAt(i + 1) != '}') {
						String key = s.substring(ordinalIndexOf(s, "\"", nthOccurrence)+1, ordinalIndexOf(s, "\"", nthOccurrence+1));
						nthOccurrence += 2;
						i = ordinalIndexOf(s, "\"", nthOccurrence+1);
						String value = s.substring(ordinalIndexOf(s, "\"", nthOccurrence)+1, i);
						nthOccurrence += 2;
						prop.put(key, Double.parseDouble(value));
					}
					shape.setProperties(prop);
					shapes.add(shape);
				}
				sc.close();
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
    	}
    	else {
    		Reader reader = null;
    		try {
				InputStream inputStream = new FileInputStream(file);
				reader = new InputStreamReader(inputStream, "ISO-8859-1");
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (UnsupportedEncodingException e) {
				System.out.println(e.getMessage());
			}
    		InputSource inputSource = new InputSource(reader);
    		inputSource.setEncoding("ISO-8859-1");
    		
    		try {
    			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document dom = docBuilder.parse(inputSource);
				NodeList nodeList = dom.getElementsByTagName("Shape");
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node node = nodeList.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						NodeList childNodeList = node.getChildNodes();
						if (childNodeList.item(0).getTextContent().equals("Circle")) {
							shape = new Circle();
						}
						else if (childNodeList.item(0).getTextContent().equals("Ellipse")) {
							shape = new Ellipse();
						}
						else if (childNodeList.item(0).getTextContent().equals("LineSegment")) {
							shape = new LineSegment();
						}
						else if (childNodeList.item(0).getTextContent().equals("Rectangle")) {
							shape = new Rectangle();
						}
						else if (childNodeList.item(0).getTextContent().equals("Square")) {
							shape = new Square();
						}
						else if (childNodeList.item(0).getTextContent().equals("Triangle")) {
							shape = new Triangle();
						}
						
						Color clr = new Color(Integer.parseInt(childNodeList.item(3).getTextContent()));
						shape.setColor(clr);
						Point position = new Point();
						double x = Double.parseDouble(childNodeList.item(1).getTextContent());
						double y = Double.parseDouble(childNodeList.item(2).getTextContent());
						position.setLocation(x, y);
						shape.setPosition(position);
						
						Color fillClr = new Color(Integer.parseInt(childNodeList.item(4).getTextContent()));
						shape.setFillColor(fillClr);
						Map<String, Double> prop = new HashMap<String, Double>();
						for (int j = 5; j < childNodeList.getLength(); j++) {
							String key = childNodeList.item(j).getNodeName();
							Double value = Double.parseDouble(childNodeList.item(j).getTextContent());
							prop.put(key, value);
						}
						shape.setProperties(prop);
						shapes.add(shape);
					}
				}
			} catch (ParserConfigurationException e) {
				System.out.println(e);
			} catch (SAXException e) {
				System.out.println(e);
			} catch (IOException e) {
				System.out.println(e);
			}
    	}
    }
 
}	