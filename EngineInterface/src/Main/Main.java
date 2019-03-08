package Main;


import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        Utilities u = new Utilities();
        u.RetrieveEmails();
        u.changeDirectory();
        u.pdf2jpeg();
        u.orient();
        int[][] array = u.runGrader();        //runGrader works but only for a specific image at the moment
        String[][] arr2 = new String[20][5];
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 5; j++){
                arr2[i][j] = Integer.toString(array[i][j]);
            }
        }
        StudentCreateInterface sci = new StudentCreateInterface(arr2);
        int i = 0;
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
