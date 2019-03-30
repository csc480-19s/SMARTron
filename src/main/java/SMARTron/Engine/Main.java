package SMARTron.Engine;

import SMARTron.Database.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException, SQLException, Exception {
        Utilities u = new Utilities();
        OrientTool ot = new OrientTool();
        InstructorDao instDao = new InstructorDao();
        AnswerKeyDao ansKDao = new AnswerKeyDao();
        CourseDao courDao = new CourseDao();
        ExamDao examDao = new ExamDao();

        u.RetrieveEmails();
        u.changeDirectory();
        u.pdf2jpeg();
        ot.orient();
        String[][] array = u.runGrader();
//        for (int j = 0; j < array.length; j++) {
//            for (int i = 0; i < array[j].length; i++) {
//                System.out.println(array[j][i]);
//            }
//            System.out.println("--------");
//        }
        StudentCreateInterface scf = new StudentCreateInterface();
        for (int i = 0; i < array.length; i++) {
            String[] s = new String[43];
            for (int j = 0; j < 43; j++) {
                s[j] = array[i][j];
            }
            scf.addStudent(u.multi(array[i]), s);
        }
        scf.runGrades();
        for (int i = 0; i < scf.g.students.size(); i++) {
            System.out.println(scf.g.students.get(i).getName() + ": " + scf.g.students.get(i).getExamGrade());
            System.out.println(Arrays.toString(Arrays.copyOfRange(scf.g.students.get(i).getAnswers(), 43, scf.g.students.get(i).getAnswers().length)));
        }
        instDao.addInstructor("123456789", "Bastian", "Tenbergen");
        ansKDao.addAnswerKey("asdfg", "123456789", Arrays.toString(Arrays.copyOfRange(scf.g.key.getAnswers(), 43, scf.g.key.getAnswers().length)));
        courDao.addCourse("54322", "Bastian's FunHouse", "800", "Fall18", "123456789");
        for (int i = 0; i < scf.rawScores.size(); i++) {
            examDao.addExam(scf.g.students.get(i).getName(), scf.g.students.get(i).getName(), scf.g.students.get(i).getID(), "Fall18", scf.g.students.get(i).getBirthday(), "csc212", "123456789", scf.g.key.getName(), Arrays.toString(Arrays.copyOfRange(scf.g.students.get(i).getAnswers(), 43, scf.g.students.get(i).getAnswers().length)));
        }
    }
}