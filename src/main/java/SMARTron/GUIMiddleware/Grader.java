package SMARTron.GUIMiddleware;

import java.util.ArrayList;
import java.util.List;

public class Grader {

    private List<Question> questions = new ArrayList<>();

    //questions is a list of question objects used to find stats per question
    //each index in the array corresponds to a question object

    public List<Float> getGrades(List<Student> studentExams, Student key){
        List<Float> grades = new ArrayList<>();
        this.initializeStatsByQuestion(key.getAnswers());
        int numPoints;

        //"grades" is the return list, it has all  the recorded grades
        //numPoints is a integer used to find the of point scored on a given test
        //key is the test key, studentExams is a list of student exams

        for (Student studentExam : studentExams) {
            numPoints = 0;
            for (int j = 0; j < studentExam.getAnswers().size(); j++) {
                if (studentExam.getAnswers().get(j) != null && key.getAnswers().get(j).contains(studentExam.getAnswers().get(j)) && !key.getAnswers().get(j).contains("-1")) {
                    //Compares the jth  position in the answer array from ith studentExam to the key
                    //It cannot equal "-1", that means the  answer is blank
                    numPoints++;
                    //Increments numPoints if it passes the conditions of the if statement
                }
                if (studentExam.getAnswers().get(j) != null)
                questions.get(j).increment(studentExam.getAnswers().get(j));
                //For the question number j on the test, the jth position in the question array will be adjusted
                //If the student has put "A" (we read "A" as 0) the "a" integer is incremented in that instance

            }
            grades.add((float) numPoints / key.getAnswers().size() * 100);
            //This will calculate the grade for the exam at i
        }
        return grades;
    }

    private void initializeStatsByQuestion(List<String> key){
        for(int i = 0; i < key.size(); i++){
            questions.add(new Question());
        }
    }

    //This makes the questions array the proper size based off the number of questions from the key

    public List<Question> getStatsByQuestion(){
        return this.questions;
    }
}

//returns the questions list