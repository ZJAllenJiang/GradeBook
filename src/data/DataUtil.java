package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.Course;
import model.StudentEntry;
import model.Category;

public class DataUtil {
	private static final String root = "";
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
		String categoryPath = coursePath + "/" + "Category";
		if (!checkDirExist(categoryPath)) 
			createDir(categoryPath);

		// create directory for each category
		for (Category category : course.getAllCategories()) {
			String currentPath = categoryPath + "/" + category.getName() + ".csv";
			createFile(currentPath);
			if (checkFileExist(currentPath))
				writeToCSV(currentPath, 
						category.getStudentEntries());
		}
	}
	
	public static void load() {
		String path = root + "database";
		if (!checkDirExist(path)) {
			System.out.println("[DataUtil load] directory doesn't exist!");
			return;
		}
		
	}
	
	/**
	 *  file helper functions	
	 */
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
	private static <T extends Writable> void writeToCSV(String filename, ArrayList<T> records)  {
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
					System.out.print(tags.get(i) + " ");
					tagList += tags.get(i);
					if (i != tags.size() - 1)
						tagList += ",";
				}
				System.out.println();
				
				// write records
				String data = "";
				for (int i = 0; i < records.size(); i++) {
					data += records.get(i).getKey() + ",";
					System.out.print(records.get(i).getKey() + " ");
					ArrayList<String> entries = records.get(i).writeAsRecord();
					for (int j = 0; j < entries.size(); j++) {
						System.out.print(entries.get(j) + " ");
						data += entries.get(j);
						if (j != entries.size() - 1)
							data += ",";
					}
					System.out.println();
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
	
//	private static ArrayList<RowData> organizeRowData(ArrayList<StudentEntry> studententries) {
//		ArrayList<RowData> res = new ArrayList<RowData>();
//		
//		return res;
//	}
	
	
	// generate path according to diff sys of linux vs win vs macos
	private String pathMapping(String path) {
		// only windows need to map
		if (File.separatorChar == '\\') 
			return path.replace('/', File.pathSeparatorChar);
		
		return path;
	}
}
