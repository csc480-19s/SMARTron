package Scanner;

import Database.AnswerKeyDao;
import Database.CourseDao;
import Database.ExamDao;
import Database.InstructorDao;
import GUIMiddleware.ExamManager;
import GUIMiddleware.Student;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Utilities u = new Utilities();
        OrientTool ot = new OrientTool();
        InstructorDao instDao = new InstructorDao();
        AnswerKeyDao ansKDao = new AnswerKeyDao();
        CourseDao courDao = new CourseDao();
        ExamDao examDao = new ExamDao();

//        u.RetrieveEmails();
//        u.changeDirectory();
//        u.pdf2jpeg();
//        ot.orient();
        //String[][] array = u.runScanner();
        List<List<List<String>>> arr = u.runScanner();
//        for (int j = 0; j < array.length; j++) {
//            for (int i = 0; i < array[j].length; i++) {
//                System.out.println(array[j][i]);
//            }
//            System.out.println("--------");
//        }
//        StudentCreateInterface scf = new StudentCreateInterface();
//        for (int i = 0; i < arr.size(); i++) {
//            String[] s = new String[43];
//            for (int j = 0; j < 5; j++) {
//                for (int k = 0; k < arr.get(i).get(j).size(); k++) {
//                    s[k] = arr.get(i).get(j).get(k);
//                }
//            }
//            scf.addStudent(u.multi(arr.get(i).get(5)), s);
//        }
//        for (int i = 0; i < array.length; i++) {
//            String[] s = new String[43];
//            System.arraycopy(array[i], 0, s, 0, 43);
//            scf.addStudent(u.multi(array[i]), s);
//        }
        //scf.runGrades();
//        for (int i = 0; i < scf.g.students.size(); i++) {
//            System.out.println(scf.g.students.get(i).getName() + ": " + scf.g.students.get(i).getExamGrade());
//            System.out.println(Arrays.toString(Arrays.copyOfRange(scf.g.students.get(i).getAnswers(), 43, scf.g.students.get(i).getAnswers().length)));
//        }

        ExamManager ex = new ExamManager();
        for (int i = 0; i < arr.size(); i++) {
            String[] s = new String[43];
            int c = 0;
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < arr.get(i).get(j).size(); k++) {
                    if (c < 43) {
                        s[c] = arr.get(i).get(j).get(k);
                        c++;
                    }
                }
            }
            ex.addStudentExam(u.multi(arr.get(i).get(5)), s);
        }
        ex.getGrades();
        List<Student> exams = ex.getStudents();
        for (int i = 0; i < exams.size(); i++) {
            System.out.println(exams.get(i).getName() + " : " + exams.get(i).getExamGrade());
        }
//        try {
//            instDao.addInstructor("123456789", "Bastian", "Tenbergen");
//        } catch (Exception ex) {
//            //ex.printStackTrace();
//        }
//        try {
//            ansKDao.addAnswerKey("69696", "123456789", Arrays.toString(Arrays.copyOfRange(scf.g.key.getAnswers(), 43, scf.g.key.getAnswers().length)));
//        } catch (Exception ex) {
//            //ex.printStackTrace();
//        }
//        try {
//            courDao.addCourse("54322", "Bastian's FunHouse", "810", "Fall18", "123456789");
//        } catch (Exception ex) {
//            //ex.printStackTrace();
//        }
//        int count = 0;
//        for (int i = 0; i < scf.rawScores.size(); i++) {
//            try {
//                if (!scf.g.students.get(i).getID().replace(" ", "").equals("")) {
//                    examDao.addExam(scf.g.students.get(i).getName(), scf.g.students.get(i).getName(),
//                            scf.g.students.get(i).getID().replace("error", "-").replace(" ", ""), "Fall18", scf.g.students.get(i).getBirthday().replace("error", "-").replace(" ", ""), "54322", "123456789", "69696",
//                            Arrays.toString(Arrays.copyOfRange(scf.g.students.get(i).getAnswers(), 43, scf.g.students.get(i).getAnswers().length)));
//                } else {
//                    examDao.addExam(scf.g.students.get(i).getName(), scf.g.students.get(i).getName(),
//                            "UNKNOWN" + count, "Fall18", scf.g.students.get(i).getBirthday().replace("error", "-").replace(" ", ""), "54322", "123456789", "69696",
//                            Arrays.toString(Arrays.copyOfRange(scf.g.students.get(i).getAnswers(), 43, scf.g.students.get(i).getAnswers().length)));
//                    count++;
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
    }
}
