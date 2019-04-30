package GUIMiddleware;

import Database.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    //declare within main string array the arguments

    public static void main(String[] args) throws Exception {
        ExamDao examDao = new ExamDao();
        List<String> ls = new ArrayList<>();

        String instructorID = args[0];
        String examID = args[1];

        ls = examDao.selectStudents(instructorID, examID);
        
        //System.out.println(ls.get(0));
        MiddlewareInterface mi = new MiddlewareInterface();

        for (int i = 1; i < ls.size(); i++) {
            mi.addStudentExam(ls.get(i));
        }

        mi.getGrades();
        //        try(FileWriter f = new FileWriter("test.json")){
    }
}