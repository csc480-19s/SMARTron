package smartron.entities;

import java.util.HashMap;

public class Question {
    private String examID;
    private int id;
    private String correct;
    private HashMap<String, Integer> responseFrequencies;
    private HashMap<String, Integer> responsePercentages;

    public Question(String examID, int id, String correct, HashMap<String,
            Integer> responseFrequencies, HashMap<String,Integer> responsePercentages){
        this.examID = examID;
        this.id = id;
        this.correct = correct;
        this.responseFrequencies = responseFrequencies;
        this.responsePercentages = responsePercentages;
    }

    public String getExamID(){
        return  examID;
    }

    public void setExamID(String examID){
        this.examID = examID;
    }

    public int getID(){
        return  id;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getCorrect(){
        return  correct;
    }

    public void setCorrect(String correct){
        this.correct = correct;
    }

    public HashMap getResponseFrequencies(){
        return responseFrequencies;
    }

    public void setResponseFrequencies(HashMap<String, Integer> responseFrequencies){
        this.responseFrequencies = responseFrequencies;
    }

    public HashMap getResponseFPercentages(){
        return responsePercentages;
    }

    public void setResponsePercentages(HashMap<String, Integer> responsePercentages){
        this.responsePercentages = responsePercentages;
    }
}
