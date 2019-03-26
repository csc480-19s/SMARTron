package Main;

import java.util.ArrayList;

public class Grader {


    Student key;
    ArrayList<Student> students;
    int[] points;
    int allPoints;

    public Grader(ArrayList<Student> stu){
        key = stu.get(0);
        stu.remove(0);
        students = stu;
        points = new int[key.getAnswers().length - 43];
        allPoints = 0;
        for(int i = 0; i < points.length; i++){
            points[i] = 1;
            allPoints+=1;
        }
    }

    public void gradeTests(){
        for (int i = 0; i < students.size(); i++){
            int numRight = 0;
            for(int j = 43; j < key.getAnswers().length; j++){
                if (key.getAnswers()[j].equals(students.get(i).getAnswers()[j])){
                    numRight = numRight + points[j - 43];
                }
            }
            float numRight2 = (float) numRight;
            float grade = numRight2 / allPoints * 100;
            System.out.println(grade);
        }

    }


}
