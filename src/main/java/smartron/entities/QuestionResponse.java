package smartron.entities;

public class QuestionResponse {

    private String response;
    private int frequency;

    public QuestionResponse(String response, int frequency){
        this.response = response;
        this.frequency = frequency;
    }

    public String getResponse(){
        return  response;
    }

    public void setResponse(String response){
        this.response = response;
    }

    public int getFrequency(){
        return frequency;
    }

    public void setFrequency(int id){
        this.frequency = frequency;
    }
}
