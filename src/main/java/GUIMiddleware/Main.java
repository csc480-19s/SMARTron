package GUIMiddleware;

import Database.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    //declare within main string array the arguments

    public static void main(String[] args) throws Exception {

        InstructorDao instDao = new InstructorDao();
        AnswerKeyDao ansKDao = new AnswerKeyDao();
        CourseDao courDao = new CourseDao();
        ExamDao examDao = new ExamDao();
        GenericDao gen = new GenericDao();
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
        System.out.println(answers);
        answers = answers.substring(1, answers.length()-1);



        String answerSheet = examID +",...,...,...,...,...," +answers;
        System.out.println(answerSheet);

        ls = gen.selectFirst("SELECT first_name, last_name, gender,semester,birth_date,student_id,course_crn,answers FROM exam WHERE instructor_id= " + "'" + instructorID + "'" +" and exam_id= " +"'" + examID + "'" + ";");

        MiddlewareInterface mi = new MiddlewareInterface();
        mi.addStudentExam(answerSheet);
        for (int i = 1; i < ls.size(); i++) {
            mi.addStudentExam(ls.get(i));

        }


        mi.getGrades();
        //        try(FileWriter f = new FileWriter("test.json")){
    }
}