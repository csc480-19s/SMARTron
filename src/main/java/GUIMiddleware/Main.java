package GUIMiddleware;

import SMARTron.Database.AnswerKeyDao;
import SMARTron.Database.CourseDao;
import SMARTron.Database.ExamDao;
import SMARTron.Database.GenericDao;
import SMARTron.Database.InstructorDao;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
