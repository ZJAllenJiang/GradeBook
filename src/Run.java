import data.DataUtil;
import model.Category;
import model.Course;
import model.StudentEntry;

public class Run {
	public static void main(String Args[]) {
//		DataUtil.save(new Course("test"));
//		DataUtil.load();
		
		
		Course c = new Course("test");
		Category hw = c.getAllCategories().get(0);
		
		for (StudentEntry se : hw.getStudentEntries()) {
			System.out.print("Name: ");
			System.out.println(se.getStudent().getFirstName()
					+ " " + se.getStudent().getMiddleName()
					+ " " + se.getStudent().getLastName());
			
			for (int i = 0; i < se.getAllData().size(); i++) {
				System.out.println(se.getAllData().get(i).toString());
			}
		}
	}
}
