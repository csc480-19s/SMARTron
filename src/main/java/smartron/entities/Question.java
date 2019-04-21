package smartron.entities;

import java.util.ArrayList;
import java.util.HashMap;

public class Question {

    private String examName;
    private String examID;
    private int number;
    private String correct;
    private ArrayList<QuestionResponse> data;

    public Question(String examName, String examID, int number, String correct, ArrayList<QuestionResponse> data){

        this.examName = examName;
        this.examID = examID;
        this.number = number;
        this.correct = correct;
        this.data = data;
    }

    public String getExamName(){
        return  examName;
    }

    public void setExamName(String examID){
        this.examName = examName;
    }

    public String getExamID(){
        return  examID;
    }

    public void setExamID(String examID){
        this.examID = examID;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int id){
        this.number = number;
    }

    public String getCorrect(){
        return  correct;
    }

    public void setCorrect(String correct){
        this.correct = correct;
    }

    public ArrayList getData(){ return data; }

    public void setData(ArrayList data){ this.data = data; }

}
