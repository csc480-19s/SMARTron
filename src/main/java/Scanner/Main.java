package Scanner;

import Database.*;
import GUIMiddleware.ExamManager;
import GUIMiddleware.Student;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Main class of the Scanner side of engine This part should run
 * periodically and scan for new emails if it does it should process them and
 * insert them into the database before finishing
 * 
 * @author Vincent Dinh
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Utilities u = new Utilities();
        OrientTool ot = new OrientTool();
        InstructorDao instDao = new InstructorDao();
        AnswerKeyDao ansKDao = new AnswerKeyDao();
        CourseDao courDao = new CourseDao();
        ExamDao examDao = new ExamDao();
        //GenericDao gDao = new GenericDao();

        while (true) {
            try {
                u.RetrieveEmails();
                u.changeDirectory();
                u.pdf2jpeg();
                ot.orient();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            List<List<List<List<String>>>> tests = null;
            try {
                tests = u.runScanner();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (tests != null) {
                for (int x = 0; x < tests.size(); x++) {
                    try {
                        List<List<List<String>>> arr = tests.get(x);
                        ExamManager e = new ExamManager();
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
                            if (arr.get(i).size() == 6) {
                                e.addStudentExam(u.multi(arr.get(i).get(5)), s);
                            } else if (arr.get(i).size() == 7) {
                                e.addStudentExam(u.multi(arr.get(i).get(5)), u.multi(arr.get(i).get(6)), s);
                            }
                        }
                        e.getGrades();
                        List<Student> exams = e.getStudents();
                        Student ansKey = e.getKey();
//                        for (int i = 0; i < exams.size(); i++) {
//                            System.out.println(exams.get(i).getName() + " " + exams.get(i).getAnswers().size() + " " + exams.get(i).getExamGrade());
//                        }
                        String code = ansKey.getName().replace(".", "");
                        String instructID = "";
                        try {
                            instructID = ansKDao.getInstructorId(code);
                            //instructID = gDao.select("select instructor_id from answerkey where exam_id = \"" + code + "\"").get(0);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        if (instructID != null && !instructID.equals("")) {
                            String email = instructID + "@oswego.edu";
                            u.sendEmailReceived(email);
                            boolean realCode = false;
                            try {
                                if (code.length() == 5) {
                                    realCode = true;
                                    //ansKDao.addUpdatedAnswerKey(code, Arrays.toString(ansKey.getAnswers().toArray()));
                                    ansKDao.updateOriginalAnswerKey(code, Arrays.toString(ansKey.getAnswers().toArray()));
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            boolean in = false;
                            String crn = "";
                            try {
                                crn = courDao.selectCrn(instructID).get(0);
                                //crn = gDao.select("select course_crn from course where instructor_id = \"" + instructID + "\"").get(0);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            if (crn.equals("")) {
                                while (!in) {
                                    for (int c = 0; c < 5; c++) {
                                        crn = crn + ((char) (new Random().nextInt(26) + 65));
                                    }
                                    try {
                                        courDao.addCourse(crn, "Bastian's FunHouse", "810", "Fall18", instructID);
                                        in = true;
                                    } catch (Exception ex) {
                                        crn = "";
                                        ex.printStackTrace();
                                    }
                                }
                            }
                            if (!realCode) {
                                code = "!real";
                            }
                            List<String> sID = new ArrayList<String>();
                            List<Float> grades = new ArrayList<Float>();
                            for (int i = 0; i < exams.size(); i++) {
                                sID.add(exams.get(i).getId());
                                grades.add(exams.get(i).getExamGrade());
                                try {
                                    examDao.addExam(exams.get(i).getName(), exams.get(i).getName(),
                                            exams.get(i).getId(), "*", exams.get(i).getBirthday(), crn, instructID, code,
                                            Arrays.toString(exams.get(i).getAnswers().toArray()));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                            String csv = u.gradeCSV(code, sID, grades);
                            u.sendEmailProcessed(email, csv);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            u.deleteAllFiles();
            Thread.sleep(300000);
        }
    }
}
