import data.DataUtil;
import model.Category;
import model.Course;
import model.Student;
import model.StudentEntry;

public class Run {
	public static void main(String Args[]) {
		DataUtil.save(new Course());
		Course c = DataUtil.load("Java", "591", 2019);
//		
//		// show all category
//		for (Category category : c.getAllCategories()) {
//			System.out.println(category.getName());
//		}
//		
//		// show all students
//		for (Student student : c.getAllStudents()) {
//			System.out.println(student.toString());
//		}
		
		Category hw = c.getAllCategories().get(0);
		for (StudentEntry se : hw.getStudentEntries()) {
			System.out.print("Name: ");
			System.out.println(se.getStudent().getFirstName()
					+ " " + se.getStudent().getMiddleName()
					+ " " + se.getStudent().getLastName());
			
			for (int i = 0; i < se.getAllData().size(); i++) {
				System.out.println(se.getAllData().get(i).getData().toString());
			}
		}
	}
}
