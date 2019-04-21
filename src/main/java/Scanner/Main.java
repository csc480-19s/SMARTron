package Scanner;

import Database.*;
import GUIMiddleware.*;
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

    public static void main(String[] args) throws IOException, InterruptedException {
        //String testAddress = "noleary@oswego.edu";

        Utilities u = new Utilities();
        OrientTool ot = new OrientTool();
        //InstructorDao instDao = new InstructorDao();
        AnswerKeyDao ansKDao = new AnswerKeyDao();
        CourseDao courDao = new CourseDao();
        ExamDao examDao = new ExamDao();
        GenericDao gDao = new GenericDao();

        while (true) {
            u.RetrieveEmails();
            u.changeDirectory();
            u.pdf2jpeg();
            ot.orient();
            List<List<List<List<String>>>> tests = u.runScanner();
            for (int x = 0; x < tests.size(); x++) {
                List<List<List<String>>> arr = tests.get(x);
//            u.sendEmailReceived(testAddress);
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
                    e.addStudentExam(u.multi(arr.get(i).get(5)), s);
                }
                e.getGrades();
                List<Student> exams = e.getStudents();
                Student ansKey = e.getKey();
//            for (int i = 0; i < exams.size(); i++) {
//                System.out.println(exams.get(i).getName() + " : " + exams.get(i).getExamGrade() + " : " + exams.get(i).getId());
//            }
                String code = ansKey.getName().replace(".", "");
                String instructID = "";
                try {
                    instructID = gDao.select("select instructor_id from answerkey where exam_id = \"" + code + "\"").get(0);
                } catch (Exception ex) {
                    //ex.printStackTrace();
                }
//            try {
//                instDao.addInstructor(instructID, "Bastian", "Tenbergen");
//            } catch (Exception ex) {
//                //ex.printStackTrace();
//            }
                boolean realCode = false;
                try {
                    if (code.length() == 5) {
                        realCode = true;
                        ansKDao.addAnswerKey(code, instructID, Arrays.toString(ansKey.getAnswers().toArray()));
                    } else {
                        ansKDao.addAnswerKey("XCVBS", instructID, Arrays.toString(ansKey.getAnswers().toArray()));
                    }
                } catch (Exception ex) {
                    //ex.printStackTrace();
                }
                boolean in = false;
                String crn = "";
                try {
                    crn = gDao.select("select course_crn from course where instructor_id = \"" + instructID + "\"").get(0);
                } catch (Exception ex) {
                    //ex.printStackTrace();
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
                            //ex.printStackTrace();
                        }
                    }
                }
                int count = 0;
                if (!realCode) {
                    code = "!real";
                }
                //List<String> sID = new ArrayList<String>();
                //List<Float> grades = new ArrayList<Float>();
                for (int i = 0; i < exams.size(); i++) {
                    //sID.add(exams.get(i).getId());
                    //grades.add(exams.get(i).getExamGrade());
                    boolean notInserted = true;
                    boolean validName = false;
                    int vCount = 0;
                    while (notInserted && vCount < 9) {
                        try {
                            if (exams.get(i).getId() != null && !exams.get(i).getId().equals("") && !exams.get(i).getId().equals("..........")) {
                                if (validName) {
                                    vCount++;
                                    String o = "" + new Random().nextInt(10);
                                    examDao.addExam(exams.get(i).getName(), exams.get(i).getName(),
                                            o + exams.get(i).getId().substring(vCount), "Fall18", exams.get(i).getBirthday(), crn, instructID, code,
                                            Arrays.toString(exams.get(i).getAnswers().toArray()));
                                    vCount = 0;
                                } else {
                                    validName = true;
                                    examDao.addExam(exams.get(i).getName(), exams.get(i).getName(),
                                            exams.get(i).getId(), "Fall18", exams.get(i).getBirthday(), crn, instructID, code,
                                            Arrays.toString(exams.get(i).getAnswers().toArray()));
                                }
                            } else {
                                examDao.addExam(exams.get(i).getName(), exams.get(i).getName(),
                                        "NONE" + count, "Fall18", exams.get(i).getBirthday(), crn, instructID, code,
                                        Arrays.toString(exams.get(i).getAnswers().toArray()));
                                count++;
                            }
                            notInserted = false;
                        } catch (Exception ex) {
                            if (!validName) {
                                count++;
                            }
                            //ex.printStackTrace();
                        }
                    }
                }
                //String csv = u.gradeCSV(code, sID, grades);
                //u.sendEmailProcessed(testAddress, csv);
            }
            u.deleteAllFiles();
            Thread.sleep(300000);
        }
    }
}
