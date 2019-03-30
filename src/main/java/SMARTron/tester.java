package SMARTron;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class tester {

	public static void main(String[] args) throws Exception {
		
		// Dao objects
		InstructorDao instDao = new InstructorDao();
		AnswerKeyDao ansKDao = new AnswerKeyDao();
		CourseDao courDao = new CourseDao();
		ExamDao examDao = new ExamDao();
		
		//Strings to test with
		String instId = "42";
		String instFN = "Bastian";
		String instLN = "Tenbergen";
		String examId = "12345";
		String courseCrn = "12345";
		String courseName = "Happy Time Course";
		String secNum = "800";
		String sem = "Summer";
		String stdntFN = "T.M.";
		String stdntLN = "Tuna";
		String stdntId = "ITSAID";
		String brthDt = "NOW";
		String answers = "a,b,c,d,g,h";
		String updatedAnswers = "a,b,c,d,e,h";
		
		// Things must be done in this order due to foreign key constraints
		// Failing to do this will cause the scripts to fail, and if there is
		// ever an issue we should find a way to roll back the changes made
		System.out.println("Trying to insert into the tables");
		try {
		instDao.addInstructor(instId, instFN, instLN);
		ansKDao.addAnswerKey(examId, instId, answers);
		courDao.addCourse(courseCrn, courseName, secNum, sem, instId);
		examDao.addExam(stdntFN, stdntLN, stdntId, sem, brthDt, courseCrn, instId, examId, answers);
		examDao.addExam("Me", "Name", "1", sem, brthDt, courseCrn, instId, examId, "s,ds,32,yj");
		ansKDao.addUpdatedAnswerKey(updatedAnswers, examId);
		} catch (Exception e) {
			throw new Exception("The insert statements failed. Please check the data and try again");
		}
		
		List<String> list = new ArrayList<String>();
		list = (ArrayList<String>) ansKDao.selectAnswerKey(examId, instId);
		printList(list);
		list = instDao.selectInstructor(instFN, instLN);
		printList(list);
		list = examDao.selectExamId(examId, instId, stdntId, courseCrn, sem);
		printList(list);
		list = examDao.selectExamId(examId, instId, "1", courseCrn, sem);
		printList(list);
		list = courDao.selectCourse(courseCrn, secNum, sem, instId);
		printList(list);
		System.out.println("Inserted into the tables.");
		
		System.out.println("Trying to delete from the tables");
		
		examDao.deleteExam(examId);
		courDao.deleteCourse(courseCrn, secNum, sem, instId);
		ansKDao.deleteAnswerKey(examId);
		instDao.deleteInstructor(instId);
		
		System.out.println("Deleted tables");
	}
	
	public static void printList(List<String> list) throws SQLException {
		for (String ls : list)
		{
			System.out.println(ls);
			
		}
	}
}
