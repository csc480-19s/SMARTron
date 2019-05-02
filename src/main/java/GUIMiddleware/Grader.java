package GUIMiddleware;

import java.util.ArrayList;
import java.util.List;

public class Grader {

    private List<Question> questions = new ArrayList<>();
    private int numQuestions = 0;
    private int answerKeyErrors = 0;

    //questions is a list of question objects used to find stats per question
    //each index in the array corresponds to a question object

    public List<Float> getGrades(List<Student> studentExams, Student key){
        List<Float> grades = new ArrayList<>();
        this.initializeStatsByQuestion(key.getAnswers());
        int numPoints;
        numQuestions = this.findKeyLength(key);

        //"grades" is the return list, it has all  the recorded grades
        //numPoints is a integer used to find the of point scored on a given test
        //key is the test key, studentExams is a list of student exams

        for (Student studentExam : studentExams) {
            numPoints = 0;

            for (int j = 0; j < numQuestions; j++) {
                if (studentExam.getAnswers().size() > j) {
                    if (studentExam.getAnswers().get(j) != null && key.getAnswers() != null && key.getAnswers().get(j).contains(studentExam.getAnswers().get(j))) {
                        //Compares the jth  position in the answer array from ith studentExam to the key
                        //It cannot equal "-1", that means the  answer is blank
                        numPoints++;
                        //Increments numPoints if it passes the conditions of the if statement
                    }else if (studentExam.getAnswers().get(j).equals("error")){
                        System.out.println("Error at Student: " + studentExam.getName() + " at answer: " + studentExam.getAnswers().get(j) +  " question number: " + j);
                    } else if (key.getAnswers().get(j).equals("error")) {
                    	answerKeyErrors++;
                    }
                    if (studentExam.getAnswers().get(j) != null)
                        questions.get(j).increment(studentExam.getAnswers().get(j));
                    //For the question number j on the test, the jth position in the question array will be adjusted
                    //If the student has put "A" (we read "A" as 0) the "a" integer is incremented in that instance

                }
            }
            grades.add((float) numPoints / (numQuestions - answerKeyErrors) * 100);
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

    private int findKeyLength(Student key){
        for(int  i = 0; i < key.getAnswers().size(); i++){
            String temp = key.getAnswers().get(i);
            if(temp.equals("-1")){
                return i;
            }
        }
        return key.getAnswers().size();
    }

    public int getExamLength(){
        return this.numQuestions;
    }

}


//returns the questions list