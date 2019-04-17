package GUIMiddleware;

import Database.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        InstructorDao instDao = new InstructorDao();
        AnswerKeyDao ansKDao = new AnswerKeyDao();
        CourseDao courDao = new CourseDao();
        ExamDao examDao = new ExamDao();
        GenericDao gen = new GenericDao();
//        ExamServlet es = new ExamServlet();
        List<String> ls = new ArrayList<>();


//
//        File studentpage = new File("\\GUIMiddleware\\Bystudent.txt");
//        Scanner studentPageScanner = new Scanner(studentpage);
//
//        System.out.println(studentPageScanner.nextLine());



        String instructorID = "123456789";
        String examID = "69696";
        ls = gen.selectFirst("SELECT first_name, last_name, gender,semester,birth_date,student_id,course_crn,answers FROM exam WHERE instructor_id='123456789' and exam_id='69696';");



        MiddlewareInterface mi = new MiddlewareInterface();
        for (int i = 1; i < ls.size(); i++) {

            mi.addStudentExam(ls.get(i));

        }

        mi.getGrades();




        //        try(FileWriter f = new FileWriter("test.json")){
    }
}
