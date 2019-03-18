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
        students = stu; //The first element in the array becomes the key, the rest are  students
        points = new int[key.getAnswers().length - 43];
        allPoints = 0;
        for(int i = 0; i < points.length; i++){
            points[i] = 1;
            allPoints+=1;
        } //This is for the point system later, an array is used because we need point values for each question
    }

    public void gradeTests(){
        for (int i = 0; i < students.size(); i++){
            int numRight = 0;
            for(int j = 43; j < key.getAnswers().length; j++){
                if (key.getAnswers()[j].equals(students.get(i).getAnswers()[j])){
                    numRight = numRight + points[j - 43];
                } //this counts the ammount of questions that are right and increments numRight every time by the ammounnt of points the question is worth.
            }
            float numRight2 = (float) numRight;
            float grade = numRight2 / allPoints * 100;
            System.out.println(grade); //A grade is found by deviding allPoints by numRight points and mutplying by 100.
        }

    }


}
