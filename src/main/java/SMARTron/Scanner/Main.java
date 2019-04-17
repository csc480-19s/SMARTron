package SMARTron.Scanner;

import SMARTron.Database.AnswerKeyDao;
import SMARTron.Database.CourseDao;
import SMARTron.Database.ExamDao;
import SMARTron.Database.InstructorDao;
import SMARTron.GUIMiddleware.ExamManager;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import SMARTron.GUIMiddleware.Student;
import java.util.Random;

/**
 * The Main class of the Scanner side of engine
 * This part should run periodically and scan for new emails if it does
 * it should process them and insert them into the database before finishing
 * @author 
 *      (`-.                .-') _             ('-.       .-') _  .-') _          _ .-') _                .-') _  ('-. .-. 
 *    _(OO  )_             ( OO ) )          _(  OO)     ( OO ) )(  OO) )        ( (  OO) )              ( OO ) )( OO )  / 
 *,--(_/   ,. \ ,-.-') ,--./ ,--,'  .-----. (,------.,--./ ,--,' /     '._        \     .'_   ,-.-') ,--./ ,--,' ,--. ,--. 
 *\   \   /(__/ |  |OO)|   \ |  |\ '  .--./  |  .---'|   \ |  |\ |'--...__)       ,`'--..._)  |  |OO)|   \ |  |\ |  | |  | 
 * \   \ /   /  |  |  \|    \|  | )|  |('-.  |  |    |    \|  | )'--.  .--'       |  |  \  '  |  |  \|    \|  | )|   .|  | 
 *  \   '   /,  |  |(_/|  .     |//_) |OO  )(|  '--. |  .     |/    |  |          |  |   ' |  |  |(_/|  .     |/ |       | 
 *   \     /__),|  |_.'|  |\    | ||  |`-'|  |  .--' |  |\    |     |  |          |  |   / : ,|  |_.'|  |\    |  |  .-.  | 
 *    \   /   (_|  |   |  | \   |(_'  '--'\  |  `---.|  | \   |     |  |          |  '--'  /(_|  |   |  | \   |  |  | |  | 
 *     `-'      `--'   `--'  `--'   `-----'  `------'`--'  `--'     `--'          `-------'   `--'   `--'  `--'  `--' `--' 
 **/
public class Main {

    public static void main(String[] args) throws IOException {
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
        List<List<List<List<String>>>> tests = u.runScanner();
        for (int x = 0; x < tests.size(); x++) {
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
                e.addStudentExam(u.multi(arr.get(i).get(5)), s);
            }
            e.getGrades();
            List<Student> exams = e.getStudents();
            Student ansKey = e.getKey();
            for (int i = 0; i < exams.size(); i++) {
                System.out.println(exams.get(i).getName() + " : " + exams.get(i).getExamGrade() + " : " + exams.get(i).getId());
            }
            try {
                instDao.addInstructor("123456789", "Bastian", "Tenbergen");
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
            boolean realCode = false;
            try {
                if (ansKey.getName().replace(".", "").length() == 5) {
                    realCode = true;
                    ansKDao.addAnswerKey(ansKey.getName().replace(".", ""), "123456789", Arrays.toString(ansKey.getAnswers().toArray()));
                } else {
                    ansKDao.addAnswerKey("XCVBN", "123456789", Arrays.toString(ansKey.getAnswers().toArray()));
                }
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
            try {
                courDao.addCourse("54322", "Bastian's FunHouse", "810", "Fall18", "123456789");
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
            int count = 0;
            String code = "XCVBN";
            if (realCode) {
                code = ansKey.getName().replace(".", "");
            }
            for (int i = 0; i < exams.size(); i++) {
                boolean notInserted = true;
                boolean validName = false;
                while (notInserted) {
                    try {
                        if (exams.get(i).getId() != null && !exams.get(i).getId().equals("") && !exams.get(i).getId().equals("..........")) {
                            if (validName) {
                                String o = "" + new Random().nextInt(10);
                                examDao.addExam(exams.get(i).getName(), exams.get(i).getName(),
                                        exams.get(i).getId().replace(".", o), "Fall18", exams.get(i).getBirthday(), "54322", "123456789", code,
                                        Arrays.toString(exams.get(i).getAnswers().toArray()));
                            } else {
                                validName = true;
                                examDao.addExam(exams.get(i).getName(), exams.get(i).getName(),
                                        exams.get(i).getId(), "Fall18", exams.get(i).getBirthday(), "54322", "123456789", code,
                                        Arrays.toString(exams.get(i).getAnswers().toArray()));
                            }
                        } else {
                            examDao.addExam(exams.get(i).getName(), exams.get(i).getName(),
                                    "NONE" + count, "Fall18", exams.get(i).getBirthday(), "54322", "123456789", code,
                                    Arrays.toString(exams.get(i).getAnswers().toArray()));
                            count++;
                        }
                        notInserted = false;
                    } catch (Exception ex) {
                        if (!validName) {
                            count++;
                        }
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
