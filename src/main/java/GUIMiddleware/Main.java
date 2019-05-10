package GUIMiddleware;

import Database.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    //declare within main string array the arguments

    public static void main(String[] args) throws Exception {
        AnswerKeyDao ansKDao = new AnswerKeyDao();
        ExamDao examDao = new ExamDao();
        List<String> ls = new ArrayList<>();
        List<String> akey = new ArrayList<>();

        String instructorID = args[0];
        String examID = args[1];

        JSONBuilder jsonbuilder = new JSONBuilder();
        jsonbuilder.resetAllJSON();

        akey = ansKDao.selectUpdatedAnswerKey(examID,instructorID);
        String answers = "";


        for (int i = 0; i < akey.size(); i++) {
            answers = answers + akey.get(i);
        }
        
        answers = answers.substring(1, answers.length()-1);
        System.out.println(answers);
        String answerSheet = examID +",...,...,...,...,...," +answers;

        ls = examDao.selectStudents(instructorID, examID);
        
        //System.out.println(ls.get(0));

        MiddlewareInterface mi = new MiddlewareInterface();
        mi.addStudentExam(answerSheet);
        for (int i = 1; i < ls.size(); i++) {
            mi.addStudentExam(ls.get(i));

        }


        mi.getGrades();
        //        try(FileWriter f = new FileWriter("test.json")){
    }
}