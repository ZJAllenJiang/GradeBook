package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map;


import model.Course;
import model.GradeableCategory;
import model.GradeableComponent;
import model.Student;
import model.StudentEntry;
import model.TextCategory;
import model.TextComponent;
import model.GradeableComponent.DataEntryMode;
import model.Category;
import model.CategoryComponent;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DataUtil {
	private static final String root = "";
	private static final String ctypeText = "Text";
	private static final String ctypeGradeable = "Gradeable";
	private static final char fileSeparator = File.separatorChar;
	
	
	//  ------- save Course -------   // 
	protected static void save(Course course) {
		String path = root + "database";
		if (!checkDirExist(path)) 
			createDir(path);
		
		String coursePath = path + "/" + course.getCode() + '_' 
							+ course.getName() + '_'
							+ Integer.toString(course.getYear());
		
		// if exist, drop it and create a new one
		if(checkDirExist(coursePath)) {
			dropDir(coursePath);
			if (!checkDirExist(coursePath))
				createDir(coursePath);
		} else 
			createDir(coursePath);
		
		// create Status
		createFile(coursePath + "/" + "Status");
		if (checkFileExist(coursePath + "/" + "Status")) {
			if (course.status())
				writeText(coursePath + "/" + "Status", "Active");
			else
				writeText(coursePath + "/" + "Status", "InActive");
		}
		
		// create Student.csv
		createFile(coursePath + "/" + "Student.csv");
		if (checkFileExist(coursePath + "/" + "Student.csv"))
			writeToCSV(coursePath + "/" + "Student.csv", course.getAllStudents());
		
		// create Summary.csv
		createFile(coursePath + "/" + "Summary.csv");
		if (checkFileExist(coursePath + "/" + "Summary.csv"))
			writeToCSV(coursePath + "/" + "Summary.csv", 
					course.getSummary().getStudentEntries());
		
		// create directory named Category
		// Category has two sub directory /TextCategory and /GradeableCategory
		String categoryPath = coursePath + "/" + "Category"; 
		createDir(categoryPath);
		createDir(categoryPath + "/" + "GradeableCategory");
		createDir(categoryPath + "/" + "TextCategory");
		
		// dealing with each category
		for (Category category : course.getAllCategories()) {
			String currentPath = categoryPath;
			if (category instanceof TextCategory)
				currentPath = currentPath + "/" + "TextCategory";
			else if (category instanceof GradeableCategory)
				currentPath = currentPath + "/" + "GradeableCategory";
			
			// each category has its own directory
			currentPath = currentPath + "/" + category.getName();
			handleCategory(currentPath, category);
		}
	}
	
	//  ------- load Course -------   // 
	protected static Course load(String name, String code, int year) {
		String path = root + "database";
		if (!checkDirExist(path)) {
			System.out.println("[DataUtil load] directory of /database doesn't exist!");
			return null;
		}
		
		String coursePath = path + "/" + getCourseDir(name, code, year);
		if (!checkDirExist(coursePath)) {
			System.out.println("[DataUtil load] " + coursePath + " doesn't exist!");
			return null;
		}
		
		// load Active / Inactive status
		Course res = new Course(name, code, year);
		res.setStatus(readStatus(coursePath + "/" + "Status"));
		
		// load Student.csv
		ArrayList<Student> studentlist = readStudent(coursePath + "/" + "Student.csv"); 
		for (Student student : studentlist)
			res.addStudent(student.getSid(), student.getFirstName(), 
				student.getMiddleName(), student.getLastName());

		// load Summary.csv
		
		// load categories
		String GradeableCategory = coursePath + "/" + "Category" + "/" + "GradeableCategory";
		String TextCategory = coursePath + "/" + "Category" + "/" + "TextCategory";
		// organize gradeable categories
		for (String categorypath : lookForDir(GradeableCategory)) {
			String categoryname = categorypath;
			double weight = readOverallWeight(GradeableCategory + "/" +
					categorypath + "/" + "overweight");
			ArrayList<CategoryComponent> components = readXML(GradeableCategory + "/" +
					categorypath + "/attributes.xml");
//			ArrayList<StudentEntry> studententries = readStudentEntries(GradeableCategory + "/" +
//					categorypath + "/" + categorypath + ".csv", studentlist, components);
			
			Category c = new GradeableCategory(weight, categoryname, studentlist);
			for (CategoryComponent component : components) 
				c.addComponent(component);
			res.addCategory(c);
		}		
		// organize text categories
		for (String categorypath : lookForDir(TextCategory)) {
			String categoryname = categorypath; 
			ArrayList<CategoryComponent> components = readXML(TextCategory + "/" + 
					categorypath + "/attributes.xml");
//			ArrayList<StudentEntry> studententries = readStudentEntries(TextCategory + "/" +
//					categorypath + "/" + categorypath + ".csv", studentlist, components);
			
			Category c = new TextCategory(categoryname, studentlist);
			for (CategoryComponent component : components) 
				c.addComponent(component);
			res.addCategory(c);
		}

		return res;
	}
	
	//  ------- load all existing courses ------- //
	protected static ArrayList<Course> readCourseList() {
		ArrayList<Course> res = new ArrayList<Course>();
		if(checkDirExist(root + "database")) {
			for (String coursename : lookForDir(root + "database")) {
				String[] terms = coursename.split("_");
				
				// code & name & year
				if (terms.length == 3) 
					res.add(load(terms[1], terms[0], Integer.parseInt(terms[2])));
				else 
					System.out.println("Invalid directory name: " + coursename);
			}
		}
		return res;
	}
	
	//  ------- get course directory ------ //
	private static String getCourseDir(String name, String code, int year) {
		return code + '_' + name + '_' + Integer.toString(year);
	}
	
	private static ArrayList<Student> readStudent(String filename) {
		ArrayList<Student> res = new ArrayList<Student>();
		ArrayList<String> rowData = readCSV(filename);
		
		for (int i = 1; i < rowData.size(); i++) {
			Student object = new Student();
			object.readFromRowData(rowData.get(i));
			res.add(object);
		}
		return res;
	}
	
	private static ArrayList<StudentEntry> readStudentEntries(String filename, 
			ArrayList<Student> students, ArrayList<CategoryComponent> components) {
		ArrayList<StudentEntry> res = new ArrayList<StudentEntry>();
		ArrayList<String> rowData = readCSV(filename);
		
		for (int i = 1; i < rowData.size(); i++) {
			// find name
			String idname = rowData.get(i).split(",")[0];
			Student s = null;
			StudentEntry object;
			for (int j = 0; j < students.size(); j++) {
				if (students.get(j).toString().equals(idname))
					s = students.get(j);
			}
			if (s != null) {
				object = new StudentEntry(s, components);
				object.readFromRowData(rowData.get(i));
				res.add(object);
			} else {
				System.out.println("[DataUtil readStudentEntries] can't find such a student in studentlist: "
						+ idname);
			}
		}
		return res;
	}
	
	private static double readOverallWeight(String filename) {
		String weight = readText(filename);
		double res = 0.0;
		try {
			res = Double.parseDouble(weight);
		}catch (Exception e) {
			System.out.println("[DataUtil readOverallWeight] parse fail! weight = "
					+ weight);
		}
		return res;
	}
	
	private static boolean readStatus(String filename) {
		String status = readText(filename);
		if (status.equals("Active"))
			return true;
		else if (status.equals("Inactive"))
			return false;
		
		System.out.println("[DataUtil readStatus] Invalid content, status is: " + status);
		return false;
	}
	
	// create and write files for each category
	private static void handleCategory(String path, Category category) {
		createDir(path);
		
		// csv, main content for this category
		String csvFile = path + "/" + category.getName() + ".csv";
		createFile(csvFile);
		writeToCSV(csvFile, category.getStudentEntries());
		
		// attributes
		String attributesFile = path + "/" + "attributes.xml";
		createFile(attributesFile);
		writeXML(attributesFile, category.getComponents());
		
		// only gradeablecategory would have weights thing
		if (category instanceof GradeableCategory) {
			
			// text, overall weight for such a category
			String overweight = path + "/" + "overweight";
			createFile(overweight);
			writeText(overweight, 
					Double.toString(((GradeableCategory) category).getWeight()));
		}
	}
	
	/**
	 *  file helper functions	
	 */
	private static ArrayList<String> lookForDir(String dir) {
		dir = pathMapping(dir);
		// return all directory under dir
		ArrayList<String> res = new ArrayList<String>();
		File currentDir = new File(dir);
		for (File file : currentDir.listFiles()) {
			if (file.isDirectory())
				res.add(file.getName());
		}
		return res;
	}
	
	private static ArrayList<String> lookForFiles(String dir, String reg) {
		dir = pathMapping(dir);
		// return all file that match the reg under dir
		ArrayList<String> res = new ArrayList<String>();
		File currentDir = new File(dir);
		String reg_expression = "(.*)" + reg + "(.*)";
		for (File file : currentDir.listFiles()) {
			if (file.isFile() && file.getName().matches(reg_expression))
				res.add(file.getName());
		}
		return res;
	}
	
	private static boolean checkDirExist(String dir) {
		dir = pathMapping(dir);
		File file = new File(dir);
		if (file.exists() && file.isDirectory()) 
			return true;
		return false;
	}
	
	private static boolean checkFileExist(String filepath) {
		filepath = pathMapping(filepath);
		File file = new File(filepath);
		if (file.exists() && file.isFile()) 
			return true;
		return false;
	}
	
	private static boolean createDir(String dir) {
		dir = pathMapping(dir);
		boolean res = true;
		File file = new File(dir);
		try {
			res = file.mkdir();
		} catch(Exception e) {
			System.out.println("[DataUtil createDir] create directory failed!");
			res = false;
		}
		return res;
	}
	
	private static boolean dropDir(String dir) {
		dir = pathMapping(dir);
		boolean res = true;
		File file = new File(dir);
		try {
			recursiveDrop(file);
		} catch(Exception e) {
			System.out.println("[DataUtil createDir] delete directory failed!");
			res = false;
		}
		return res;
	}
	
	private static void recursiveDrop(File file) {
		File[] files = file.listFiles();
		if (files != null) {
			for (File f : files) {
				recursiveDrop(f);
			}
		}
		file.delete();
	}

 	private static boolean createFile(String filepath) {
		filepath = pathMapping(filepath);
		boolean res = true;
		File file = new File(filepath);
		try {
			res = file.createNewFile();
		} catch(Exception e) {
			System.out.println("[DataUtil createFile] create file " + filepath + " failed!");
		}
		return res;
	}
	
	/**
	 *  I/O helper functions 
	 * @param <T>
	 */
	private static <T extends Writeout> void writeToCSV(String filename, ArrayList<T> records)  {
		filename = pathMapping(filename);
		if (records.size() > 0) {
			ArrayList<String> tags = records.get(0).getColumnName();
			
			// a record = key + [tag1(value), tag2(value), ..., tagN(value)]
			if (tags.size() != records.get(0).writeAsRecord().size() + 1)
				return;
			
			try(PrintWriter writer = new PrintWriter(new File(filename))) {
				StringBuilder sbuilder = new StringBuilder();
				
				// write tags
				String tagList = "";
				for (int i = 0; i < tags.size(); i++) {
					tagList += tags.get(i);
					if (i != tags.size() - 1)
						tagList += ",";
				}
				
				// write records
				String data = "";
				for (int i = 0; i < records.size(); i++) {
					data += records.get(i).getKey() + ",";
					ArrayList<String> entries = records.get(i).writeAsRecord();
					for (int j = 0; j < entries.size(); j++) {
						data += entries.get(j);
						if (j != entries.size() - 1)
							data += ",";
					}
					if (i != records.size() - 1)
						data += "\n";
				}
				
				sbuilder.append(tagList + "\n");
				sbuilder.append(data); 
				writer.write(sbuilder.toString());
				writer.close();
			} catch(FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("[DataUtil writeToCSV] len of records is 0, target file: " + filename);
		}
	}
	
	private static ArrayList<String> readCSV(String filename) {
		filename = pathMapping(filename);
		if (checkFileExist(filename)) {
			ArrayList<String> res = new ArrayList<String>();
			try (Scanner sc = new Scanner(new File(filename))) {
				while (sc.hasNextLine()) 
					res.add(sc.nextLine());
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}			
			return res;
		}
		System.out.println("[DataUtil readText] " + filename + " doesn't exist!");
		return new ArrayList<String>();
	}
	
	
	private static String readText(String filename) {
		filename = pathMapping(filename);
		if (checkFileExist(filename)) {
			String res = "";
			try (Scanner sc = new Scanner(new File(filename))) {
				while (sc.hasNextLine())
					res += sc.nextLine();
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}			
			return res;
		}
		System.out.println("[DataUtil readText] " + filename + " doesn't exist!");
		return "";
	}
	
	private static void writeText(String filename, String content) {
		filename = pathMapping(filename);
		try(PrintWriter writer = new PrintWriter(new File(filename))) {
			StringBuilder sbuilder = new StringBuilder();
			sbuilder.append(content);
			writer.write(sbuilder.toString());
			writer.close();
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void writeXML(String filename, 
			ArrayList<CategoryComponent> components) {
		filename = pathMapping(filename);
		Document dom;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			dom = dBuilder.newDocument();
			
			// root element
			Element root = dom.createElement("Components");
			dom.appendChild(root);
			
			for (CategoryComponent component : components) {
				Map<String, String> attributes = component.getAllAttributes();
				root.appendChild(getChildNode(dom, attributes));
			}
			
			// write to file
			try {
	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            Transformer transformer = transformerFactory.newTransformer();
	            //for pretty print
	            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	            DOMSource source = new DOMSource(dom);
	            StreamResult file = new StreamResult(new File(filename));
	            transformer.transform(source, file);
			}catch (Exception ee) { System.out.println(ee.getMessage());}
		} catch(Exception e) { System.out.println(e.getMessage());}
	}
	
	private static Node getChildNode(Document doc, Map<String, String> attributes) {
		if (attributes.containsKey("name") && attributes.containsKey("type") &&
				attributes.containsKey("isEditable")) {
				Element ele = doc.createElement(attributes.get("name"));
				
				// has duplicate for "name", but seems doesn't matter...
				for (Map.Entry<String, String> kv : attributes.entrySet()) {
					ele.appendChild(getInfoNode(doc, kv.getKey(), kv.getValue()));
				}
				
				return ele;
		} 
		return null;
	}
	
	private static Node getInfoNode(Document doc, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
	
	private static ArrayList<CategoryComponent> readXML(String filename) {
		filename = pathMapping(filename);
		Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        ArrayList<CategoryComponent> res = new ArrayList<CategoryComponent>();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse(filename);
            
            Node root = dom.getFirstChild();
            NodeList components = root.getChildNodes();
            
            for (int i = 0; i < components.getLength(); i++) {
            	if (components.item(i).getNodeType() == Node.ELEMENT_NODE) {
	            	
	            	Map<String, String> attributes = readXMLComponent(components.item(i));
	            	CategoryComponent c = null;
	            	if (attributes.get("type").equals("gradeable")) 
	            		c = new GradeableComponent(attributes);
	            	else if (attributes.get("type").equals("text"))
	            		c = new TextComponent(attributes);
	            	else
	            		System.out.println("[DataUtil readXML] mis matched type");
	            	
	            	res.add(c);
            	}
            }
        } catch(Exception e) { System.out.println("[DataUtil readXML] read err");}
        return res;
	}
	
	private static Map<String, String> readXMLComponent(Node node) {
		Map<String, String> res = new TreeMap<String, String>();
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node current = list.item(i);
			if (current.hasChildNodes()) {
				res.put(current.getNodeName(), current.getFirstChild().getTextContent());
//				System.out.println(current.getNodeName() + " | " + current.getFirstChild().getTextContent());
			}
		}
		
		return res;
	}
	
	// generate path according to diff sys of linux vs win vs macos
	private static String pathMapping(String path) {
		// only windows need to map
		if (File.separatorChar == '\\') 
			return path.replace('/', File.pathSeparatorChar);
		
		return path;
	}
}
