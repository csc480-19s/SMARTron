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

        List<String> ls = new ArrayList<>();



        //ls = gen.selectFirst("SELECT * FROM exam");
        MiddlewareInterface mi = new MiddlewareInterface();
        for (int i = 1; i < ls.size(); i++) {
            mi.addStudentExam(ls.get(i));
        }
        
        mi.getGrades();
        
        


        //        try(FileWriter f = new FileWriter("test.json")){
    }
}