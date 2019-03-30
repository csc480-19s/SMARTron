package SMARTron.Engine;

import SMARTron.AnswerKeyDao;
import SMARTron.CourseDao;
import SMARTron.ExamDao;
import SMARTron.InstructorDao;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {
        Utilities u = new Utilities();
        InstructorDao instDao = new InstructorDao();
        AnswerKeyDao ansKDao = new AnswerKeyDao();
        CourseDao courDao = new CourseDao();
        ExamDao examDao = new ExamDao();

//        for(int i = 0; i < scf.rawScores.size(); i++){
//        examDao.deleteExam(scf.rawScores.get(i).getName());
//	courDao.deleteCourse(scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName());
//	ansKDao.deleteAnswerKey(scf.rawScores.get(i).getName());
//	instDao.deleteInstructor(scf.rawScores.get(i).getName());
//        instDao.addInstructor(scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName());
//        ansKDao.addAnswerKey(scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName());
//        courDao.addCourse(scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName());
//        examDao.addExam(scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName());
//               u.RetrieveEmails();
//               u.changeDirectory();
//               u.pdf2jpeg();
//               u.orient();
        String array[][] = u.runGrader();
        for (int j = 0; j < array.length; j++) {
            for (int i = 0; i < array[j].length; i++) {
                System.out.println(array[j][i]);
            }
            System.out.println("--------");
        }
        String[] a1 = new String[43];
        String[] a2 = new String[43];
        for (int i = 0; i < 43; i++) { //
            a1[i] = array[0][i];
            a2[i] = array[1][i];
        }

        PseudoStudent s1 = new PseudoStudent(u.multi(array[0]), a1);
        PseudoStudent s2 = new PseudoStudent(u.multi(array[1]), a2);
        //All sfc instances commented out as they are still under development
        //StudentCreateInterface sfc = new StudentCreateInterface();

        //sfc.addStudent(s1.multi, s1.array);
        //sfc.addStudent(s2.multi, s2.array);

        //for (int i = 0; i < sfc.rawScores.size(); i++) {

        //examDao.deleteExam(scf.rawScores.get(i).getName());
	//courDao.deleteCourse(scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName());
//	ansKDao.deleteAnswerKey(scf.rawScores.get(i).getName());
//	instDao.deleteInstructor(scf.rawScores.get(i).getName());
//            examDao.deleteExam(sfc.rawScores.get(i).getName());
//            courDao.deleteCourse(sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName());
//            ansKDao.deleteAnswerKey(sfc.rawScores.get(i).getName());
//            instDao.deleteInstructor(sfc.rawScores.get(i).getName());
//            instDao.addInstructor(sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName());
//            ansKDao.addAnswerKey(sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName());
//            courDao.addCourse(sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName());
//            examDao.addExam(sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName());

        }

//String array[][] = u.runGrader();
//        for (int j = 0; j < array[0].length; j++) {
//            System.out.println(array[0][j]);
//        }
//        String arrayMulti[][] = u.multi(array[0]);
//        for (int j = 0; j < arrayMulti.length; j++) {
//            for (int i = 0; i < arrayMulti[j].length; i++) {
//                System.out.println(arrayMulti[j][i]);
//            }
//        }
//        int[][] array = u.runGrader();        //runGrader works but only for a specific image at the moment
//        String[][] arr2 = new String[20][5];
//        for(int i = 0; i < 20; i++){
//            for(int j = 0; j < 5; j++){
//                arr2[i][j] = Integer.toString(array[i][j]);
//            }
//        }
//        StudentCreateInterface sci = new StudentCreateInterface(arr2);
//        int i = 0;
//
//        for (int i = 0; i < array.length; i++) {
//            for (int j = 0; j < array[i].length; j++) {
//                System.out.println(array[i][j]);
//            }
//        }
        //Still Being Integrated, Does not work Properly.
//        String[] s = u.getAnswers(array);
//        Student student = new Student(s); 
//        String[] key43 = {"11", "5", "25", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"};
//        Student key = new Student(key43);
//        ArrayList<Student> students = new ArrayList<>();
//        students.add(key);
//        students.add(student);
//        Grader grader = new Grader(students);
//        grader.gradeTests();
//    }
}