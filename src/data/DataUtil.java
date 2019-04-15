package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import model.Course;
import model.GradeableCategory;
import model.GradeableComponent;
import model.Student;
import model.StudentEntry;
import model.TextCategory;
import model.TextComponent;
import model.Category;
import model.Component;

public class DataUtil {
	private static final String root = "";
	private static final String ctypeText = "Text";
	private static final String ctypeGradeable = "Gradeable";
	private static final char fileSeparator = File.separatorChar;
	
	
	//  ------- save Course -------   // 
	public static void save(Course course) {
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
	public static Course load(String name, String code, int year) {
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
		ArrayList<Student> studentlist = readStudent(coursePath + "/" + "Student.csv");
		// load Summary.csv
		
		
		// load categories
		
		// organize gradeable categories
		String GradeableCategory = coursePath + "/" + "Category" + "/" + "GradeableCategory";
		String TextCategory = coursePath + "/" + "Category" + "/" + "TextCategory";
		for (String categorypath : lookForDir(TextCategory)) {
			ArrayList<Component> components = readComponents(TextCategory + "/" + 
					categorypath + "/" + categorypath + ".csv",
					TextCategory + "/" + categorypath + "/" + "componentType");
			ArrayList<StudentEntry> studententries = readStudentEntries(TextCategory + "/" +
					categorypath + "/" + categorypath + ".csv", studentlist, components);
			
			
			res.addNonGradeable(name);
		}
		
		// organize text categories
		for (String categorypath : lookForDir(GradeableCategory)) {
			String categoryname = categorypath;
			double weight = readOverallWeight(GradeableCategory + "/" +
					categorypath + "/" + "overweight");
			ArrayList<Component> components = readComponents(GradeableCategory + "/" +
					categorypath + "/" + categorypath + ".csv",
					GradeableCategory + "/" + categorypath + "/" + "componentType");
			ArrayList<StudentEntry> studententries = readStudentEntries(GradeableCategory + "/" +
					categorypath + "/" + categorypath + ".csv", studentlist, components);
			
			res.addGradeable(weight, categoryname);
		}
		
		// load Student.csv, have to load it after dealing with categories thing
		for (Student student : studentlist)
			res.addStudent(student.getSid(), student.getFirstName(), 
				student.getMiddleName(), student.getLastName(), student.isStatus());

		
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
	
	private static ArrayList<Component> readComponents(String filename, String typefile) {
		ArrayList<Component> res = new ArrayList<Component>();
		
		// read components from first row
		String[] attributes = readCSV(filename).get(0).split(",");
		String[] componentsType = readText(typefile).split(",");
		
		if (attributes.length == componentsType.length + 1) {
			for (int i = 1; i < attributes.length; i++) {
				String name = attributes[i];
				Component c = null;
				// all return editable by default
				if (componentsType[i-1].equals(ctypeText)) {
					c = new TextComponent(name, true);
				} else if (componentsType[i-1].equals(ctypeGradeable)) {
					c = new GradeableComponent(name, true);
				} else {
					System.out.println("[DataUtil readComponents] miss match type, type = "
							+ componentsType[i-1]);
					continue;
				}
				res.add(c);
			}
		}
		return res;
	}
	
	private static ArrayList<StudentEntry> readStudentEntries(String filename, 
			ArrayList<Student> students, ArrayList<Component> components) {
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
		
		// text, componentType for each component
		createFile(path + "/" + "componentType");
		writeText(path + "/" + "componentType", 
				getAllComponentType(category));
		
		// only gradeablecategory would have weights thing
		if (category instanceof GradeableCategory) {
			
			// text, overall weight for such a category
			String overweight = path + "/" + "overweight";
			createFile(overweight);
			writeText(overweight, 
					Double.toString(((GradeableCategory) category).getWeight()));
			
//			// text, weight for each component
//			String componentweight = path + "/" + "componentweight";
//			createFile(componentweight);
//			writeText(componentweight,)
		}
	}
	
	private static String getAllComponentType(Category category) {
		String res = "";
		for(int i = 0; i < category.getComponents().size(); i++) {
			Component c = category.getComponents().get(i);
			if (c instanceof TextComponent) 
				res += ctypeText;
			else if (c instanceof GradeableComponent) 
				res += ctypeGradeable;
			
			if (i != category.getComponents().size() - 1)
				res += ",";
		}
		return res;
	}
	
	/**
	 *  file helper functions	
	 */
	private static ArrayList<String> lookForDir(String dir) {
		// return all directory under dir
		ArrayList<String> res = new ArrayList<String>();
		File currentDir = new File(dir);
		for (File file : currentDir.listFiles()) {
			if (file.isDirectory())
				res.add(file.getAbsolutePath().replace(File.separatorChar, '/'));
		}
		return res;
	}
	
	private static ArrayList<String> lookForFiles(String dir, String reg) {
		// return all file that match the reg under dir
		ArrayList<String> res = new ArrayList<String>();
		File currentDir = new File(dir);
		String reg_expression = "(.*)" + reg + "(.*)";
		for (File file : currentDir.listFiles()) {
			if (file.isFile() && file.getName().matches(reg_expression))
				res.add(file.getAbsolutePath().replace(File.separatorChar, '/'));
		}
		return res;
	}
	
	private static boolean checkDirExist(String dir) {
		File file = new File(dir);
		if (file.exists() && file.isDirectory()) 
			return true;
		return false;
	}
	
	private static boolean checkFileExist(String filepath) {
		File file = new File(filepath);
		if (file.exists() && file.isFile()) 
			return true;
		return false;
	}
	
	private static boolean createDir(String dir) {
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
		boolean res = true;
		File file = new File(dir);
		try {
			res = file.delete();
		} catch(Exception e) {
			System.out.println("[DataUtil createDir] delete directory failed!");
			res = false;
		}
		return res;
	}

	private static boolean createFile(String filepath) {
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
		try(PrintWriter writer = new PrintWriter(new File(filename))) {
			StringBuilder sbuilder = new StringBuilder();
			sbuilder.append(content);
			writer.write(sbuilder.toString());
			writer.close();
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// generate path according to diff sys of linux vs win vs macos
	private String pathMapping(String path) {
		// only windows need to map
		if (File.separatorChar == '\\') 
			return path.replace('/', File.pathSeparatorChar);
		
		return path;
	}
}
