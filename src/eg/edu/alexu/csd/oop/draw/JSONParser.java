package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class JSONParser {
	public JSONParser() {}
	
	@SuppressWarnings("unchecked")
	public void JSONSave(File file, ArrayList<Shape> list) {
		try {
            FileWriter writer = new FileWriter(file, true);
            writer.write("{\""+ list.getClass().getName() + "\": [" + System.lineSeparator());
            for (int i = 0; i < list.size(); i++) {
                writer.write("{\"className\": \"" + list.get(i).getClass().getSimpleName() + "\", ");
                writer.write("\"x\": \"" + Double.toString(list.get(i).getPosition().getX()) + "\", ");
                writer.write("\"y\": \"" + Double.toString(list.get(i).getPosition().getY()) + "\", ");
                writer.write("\"color\": \"" + Integer.toString(list.get(i).getColor().getRGB()) + "\", ");
                writer.write("\"fillColor\": \"" + Integer.toString(list.get(i).getFillColor().getRGB()) + "\", ");
                Iterator<?> it = list.get(i).getProperties().entrySet().iterator();
                while (it.hasNext()) {
                	Map.Entry<String, Double> pair = (Map.Entry<String, Double>)it.next();
                	writer.write("\""+ pair.getKey() +"\": \"" + Double.toString(pair.getValue()) + "\"");
                	if (it.hasNext()) {
                		writer.write(", ");
                	}
				}
                writer.write("}");
                if (i != list.size() - 1) {
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
	
	public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }
	
	public ArrayList<Shape> JSONLoad(File file) {
		Shape shape = null;
		ArrayList<Shape> shapes = new ArrayList<Shape>();
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
		return shapes;
	}
}
