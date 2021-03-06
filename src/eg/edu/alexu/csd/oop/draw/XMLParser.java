package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

public class XMLParser {
	
	public XMLParser() {}
	
	// Saving the contents of the file in XML format
	public void XMLSave(File file, ArrayList<Shape> list) {
		
		// Initializing DOM and elements
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		Element shape, name, x, y, clr, fillClr, property;
		
		// Creating nodes to save the properties of shapes
		try {
			docBuilder = docFactory.newDocumentBuilder();
			Document dom = docBuilder.newDocument();
			Element root = dom.createElement("shapes");
			for (int i = 0; i < list.size(); i++) {
				shape = dom.createElement("Shape");
				name = dom.createElement("name");
				name.appendChild(dom.createTextNode(list.get(i).getClass().getSimpleName()));
				shape.appendChild(name);
				
				// Checking if a property is null before creating the node
				if (list.get(i).getPosition() == null) {
					x = dom.createElement("x");
					x.appendChild(dom.createTextNode(Double.toString(0.0)));
					shape.appendChild(x);
					
					y = dom.createElement("y");
					y.appendChild(dom.createTextNode(Double.toString(0.0)));
					shape.appendChild(y);
				}
				else {
					x = dom.createElement("x");
					x.appendChild(dom.createTextNode(Double.toString(list.get(i).getPosition().getX())));
					shape.appendChild(x);
					
					y = dom.createElement("y");
					y.appendChild(dom.createTextNode(Double.toString(list.get(i).getPosition().getY())));
					shape.appendChild(y);
				}
				
				if (list.get(i).getColor() == null) {
					clr = dom.createElement("clr");
					clr.appendChild(dom.createTextNode(Integer.toString(Color.BLACK.getRGB())));
					shape.appendChild(clr);
				}
				else {
					clr = dom.createElement("clr");
					clr.appendChild(dom.createTextNode(Integer.toString(list.get(i).getColor().getRGB())));
					shape.appendChild(clr);
				}
				
				if (list.get(i).getFillColor() == null) {
					fillClr = dom.createElement("fillClr");
					fillClr.appendChild(dom.createTextNode(Integer.toString(Color.WHITE.getRGB())));
					shape.appendChild(fillClr);
				}
				else {
					fillClr = dom.createElement("fillClr");
					fillClr.appendChild(dom.createTextNode(Integer.toString(list.get(i).getFillColor().getRGB())));
					shape.appendChild(fillClr);
				}
				
				// Looping through all the specific properties of a shape and adding them
				if (list.get(i).getProperties() != null) {
					for (Map.Entry<String, Double> mapEntry : list.get(i).getProperties().entrySet()) {
						property = dom.createElement(mapEntry.getKey());
						property.appendChild(dom.createTextNode(mapEntry.getValue().toString()));
						shape.appendChild(property);
					}
				}
				root.appendChild(shape);
			}
			dom.appendChild(root);
			
			// Transforming the DOM into the file with specific encoding and indentation
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
	
	// Loading the contents from a file in XML format
	public ArrayList<Shape> XMLLoad(File file) {
		
		// Initializing the reader and input stream
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		Shape shape = null;
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
		
		// Initializing the DOM and node list
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document dom = docBuilder.parse(inputSource);
			NodeList nodeList = dom.getElementsByTagName("Shape");
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					NodeList childNodeList = node.getChildNodes();
					
					// Checking which shape is it
					if (childNodeList.item(1).getTextContent().equals("Circle")) {
						shape = new Circle();
					}
					else if (childNodeList.item(1).getTextContent().equals("Ellipse")) {
						shape = new Ellipse();
					}
					else if (childNodeList.item(1).getTextContent().equals("LineSegment")) {
						shape = new LineSegment();
					}
					else if (childNodeList.item(1).getTextContent().equals("Rectangle")) {
						shape = new Rectangle();
					}
					else if (childNodeList.item(1).getTextContent().equals("Square")) {
						shape = new Square();
					}
					else if (childNodeList.item(1).getTextContent().equals("Triangle")) {
						shape = new Triangle();
					}
					
					// Get the position of the shape and check if it's null
					double x, y;
					if (childNodeList.item(3).getTextContent() == null) {
						x = 0.0;
					}
					else {
						x = Double.parseDouble(childNodeList.item(3).getTextContent());
					}
					if (childNodeList.item(5).getTextContent() == null) {
						y = 0.0;
					}
					else {
						y = Double.parseDouble(childNodeList.item(5).getTextContent());
					}
					Point position = new Point();
					position.setLocation(x, y);
					shape.setPosition(position);
					
					// Get color and fill color
					Color clr = new Color(Integer.parseInt(childNodeList.item(7).getTextContent()));
					shape.setColor(clr);
					Color fillClr = new Color(Integer.parseInt(childNodeList.item(9).getTextContent()));
					shape.setFillColor(fillClr);
					
					// Get specific properties of each shape
					Map<String, Double> prop = new HashMap<String, Double>();
					for (int j = 11; j < childNodeList.getLength(); j+=2) {
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
		return shapes;
	}
}
