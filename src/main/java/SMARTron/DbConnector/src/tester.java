import java.sql.SQLException;

public class tester {

	public static void main(String[] args) throws SQLException {
		
		// Dao objects
		InstructorDao instDao = new InstructorDao();
		AnswerKeyDao ansKDao = new AnswerKeyDao();
		CourseDao courDao = new CourseDao();
		ExamDao examDao = new ExamDao();
		
		//Strings to test with
		String instId = "1";
		String instFN = "Bastian";
		String instLN = "Tenbergen";
		String examId = "42424242";
		String courseCrn = "12345";
		String courseName = "Happy Time Course";
		String secNum = "800";
		String sem = "Summer";
		String stdntFN = "T.M.";
		String stdntLN = "Tuna";
		String stdntId = "ITSAID";
		String brthDt = "NOW";
		
		// Things must be done in this order due to foreign key constraints
		// Failing to do this will cause the scripts to fail, and if there is
		// ever an issue we should find a way to roll back the changes made
		System.out.println("Trying to insert into the tables");
		
		instDao.addInstructor(instId, instFN, instLN);
		ansKDao.addAnswerKey(examId, instId);
		courDao.addCourse(courseCrn, courseName, secNum, sem, instId);
		examDao.addExam(stdntFN, stdntLN, stdntId, sem, brthDt, courseCrn, instId, examId);
		
		System.out.println("Inserted into the tables.");
		
		System.out.println("Trying to delete from the tables");
		
		examDao.deleteExam(examId);
		courDao.deleteCourse(courseCrn, secNum, sem);
		ansKDao.deleteAnswerKey(examId);
		instDao.deleteInstructor(instId);
		
		System.out.println("Deleted tables");
	}
}
