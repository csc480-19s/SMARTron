package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {
        Utilities u = new Utilities(); //Creates a Utility Object
        InstructorDao instDao = new InstructorDao(); //Creates an InstructorDao Object
        AnswerKeyDao ansKDao = new AnswerKeyDao(); //Creates an AnswerKeyDao Object
        CourseDao courDao = new CourseDao();       //Creates a CourseDao Object
        ExamDao examDao = new ExamDao();            //Creates an Exam Dao Object

//        for(int i = 0; i < scf.rawScores.size(); i++){
//        examDao.deleteExam(scf.rawScores.get(i).getName());
//	courDao.deleteCourse(scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName());
//	ansKDao.deleteAnswerKey(scf.rawScores.get(i).getName());
//	instDao.deleteInstructor(scf.rawScores.get(i).getName());
//        instDao.addInstructor(scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName());
//        ansKDao.addAnswerKey(scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName());
//        courDao.addCourse(scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName());
//        examDao.addExam(scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName());
//               u.RetrieveEmails();
//               u.changeDirectory();
//               u.pdf2jpeg();
//               u.orient();
        String array[][] = u.runGrader();       //Runes the "runGrader" Method, inserting the multidimensional Array Output into a String Array used in the Main Method
        for (int j = 0; j < array.length; j++) {        //Iterates thru the array, printing out all objects THIS IS USED FOR TESTING PURPOSES
            for (int i = 0; i < array[j].length; i++) {
                System.out.println(array[j][i]);
            }
            System.out.println("--------");
        }
        String[] a1 = new String[43];           //Creates an array of just the first 43 data points of each scantron, This is the students name, ID, and date
        String[] a2 = new String[43];           //This creates another array of the second scantron sheet
        for (int i = 0; i < 43; i++) {          //Iterates thru the multidimensional array storing values in the previously created arrays in line 40 and 39.
            a1[i] = array[0][i];
            a2[i] = array[1][i];
        }

        PseudoStudent s1 = new PseudoStudent(u.multi(array[0]), a1);        //Creates Pseudo Student objects which takes the array of answers, and the array of information.
        PseudoStudent s2 = new PseudoStudent(u.multi(array[1]), a2);

        StudentCreateInterface sfc = new StudentCreateInterface();          //creates a student interface object which can store and take information from each student

        sfc.addStudent(s1.multi, s1.array);                                 //adds student one and two into the array
        sfc.addStudent(s2.multi, s2.array);

        for (int i = 0; i < sfc.rawScores.size(); i++) {                    

        //examDao.deleteExam(scf.rawScores.get(i).getName());
	//courDao.deleteCourse(scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName(), scf.rawScores.get(i).getName());
//	ansKDao.deleteAnswerKey(scf.rawScores.get(i).getName());
//	instDao.deleteInstructor(scf.rawScores.get(i).getName());
            
            //The below lines push the student scores into the database
            
            
            examDao.deleteExam(sfc.rawScores.get(i).getName());
            courDao.deleteCourse(sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName());
            ansKDao.deleteAnswerKey(sfc.rawScores.get(i).getName());
            instDao.deleteInstructor(sfc.rawScores.get(i).getName());
            instDao.addInstructor(sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName());
            ansKDao.addAnswerKey(sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName());
            courDao.addCourse(sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName());
            examDao.addExam(sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName(), sfc.rawScores.get(i).getName());

            
            //
        }
        
        //ALL OF THIS CODE IS USED FOR TESTING, YOU CAN COMPLETELY IGNORE IT!

//String array[][] = u.runGrader();
//        for (int j = 0; j < array[0].length; j++) {
//            System.out.println(array[0][j]);
//        }
//        String arrayMulti[][] = u.multi(array[0]);
//        for (int j = 0; j < arrayMulti.length; j++) {
//            for (int i = 0; i < arrayMulti[j].length; i++) {
//                System.out.println(arrayMulti[j][i]);
//            }
//        }
//        int[][] array = u.runGrader();        //runGrader works but only for a specific image at the moment
//        String[][] arr2 = new String[20][5];
//        for(int i = 0; i < 20; i++){
//            for(int j = 0; j < 5; j++){
//                arr2[i][j] = Integer.toString(array[i][j]);
//            }
//        }
//        StudentCreateInterface sci = new StudentCreateInterface(arr2);
//        int i = 0;
//
//        for (int i = 0; i < array.length; i++) {
//            for (int j = 0; j < array[i].length; j++) {
//                System.out.println(array[i][j]);
//            }
//        }
        //Still Being Integrated, Does not work Properly.
//        String[] s = u.getAnswers(array);
//        Student student = new Student(s); 
//        String[] key43 = {"11", "5", "25", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"};
//        Student key = new Student(key43);
//        ArrayList<Student> students = new ArrayList<>();
//        students.add(key);
//        students.add(student);
//        Grader grader = new Grader(students);
//        grader.gradeTests();
    }
}