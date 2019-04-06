import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONBuilder {

    private String examID = "";
    private String examName = "";

    public void setExamCode(String examID){
        this.examID = examID;
    }

    public void setExamName(String examName){
        this.examName = examName;
    }

    public void buildAnswerKeyJSON(Student key){

        JSONArray arr = new JSONArray();

        for (int i = 0; i < key.getAnswers().size(); i++)  {

            JSONObject entry = new JSONObject();
            entry.put("questionID", i);
            JSONArray ansarr = new JSONArray();

            for(int j = 0; j < key.getAnswers().get(i).length(); j++) {
                ansarr.add(key.getAnswers().get(i).charAt(j));
            }

            entry.put("answerKeys", ansarr);
            arr.add(entry);
        }

        JSONObject ret = new JSONObject();
        ret.put("answersList", arr);
        ret.put("examId", this.examID);
        ret.put("examName", this.examName);

        try(FileWriter f = new FileWriter("AnswerKey.json")){
            f.write(ret.toString());
            f.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildByStudentJSON(List<Student> studentExams) {

        JSONObject ret = new JSONObject();
        JSONArray arr = new JSONArray();
        for (Student studentExam : studentExams) {
            arr.add(studentExam.genJsonData());
        }

        ret.put("students", arr);

        try(FileWriter f = new FileWriter("Bystudent.json")){
            f.write(ret.toString());
            f.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void buildByQuestion(List<Question> questions) {
        JSONArray arr = new JSONArray();

        for (int i = 0; i < questions.size(); i++){
            JSONObject ent = new JSONObject();
            ent.put("questionNumber", i++);
            ent.put("data", questions.get(i).returnList());
            arr.add(ent);
        }

        JSONObject ret = new JSONObject();
        ret.put("questionsList", arr);
        ret.put("examId", this.examID);
        ret.put("examName", this.examName);

        try(FileWriter f = new FileWriter("Byquestion.json")){
            f.write(ret.toString());
            f.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildStatsJSON(Stats stats){

            JSONObject ret = new JSONObject();

            ret.put("examName", this.examName);
            ret.put("examID", this.examID);
            ret.put("mean", stats.getMean());
            ret.put("median", stats.getMedian());
            ret.put("max", stats.getMax());
            ret.put("min", stats.getMin());
            ret.put("range", stats.getRange());
            ret.put("variance", stats.getVariance());
            ret.put("kr20", stats.getKr20());
            ret.put("kr21", stats.getKr21());
            ret.put("cronbach", stats.getCronbach());

        try(FileWriter f = new FileWriter("Stats.json")){
            f.write(ret.toString());
            f.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildMainpageJSON() {

        try (FileWriter f = new FileWriter("Mainpage.json")) {
            f.write("");
            f.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
