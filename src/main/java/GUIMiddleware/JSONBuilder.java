package GUIMiddleware;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class JSONBuilder {

    private String examID = "";
    private String examName = "";

    //examID is the exam's ID
    //examName is the exam's Name

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

        try(FileWriter f = new FileWriter("AnswerKey.txt")){
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

        try(FileWriter f = new FileWriter("Bystudent.txt")){

            f.write(ret.toString());
            f.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getBystudent() throws FileNotFoundException {
        File studentpage = new File("Bystudent.txt");
        Scanner studentPageScanner = new Scanner(studentpage);
        String studentPageEndpointString = studentPageScanner.nextLine();

        return studentPageEndpointString;
    }

    public void buildByQuestion(List<Question> questions, int length) {
        JSONArray arr = new JSONArray();
        JSONObject ent = new JSONObject();
        for (int i = 0; i < length; i++){
            ent.clear();
            ent.put("questionNumber", i + 1);
            ent.put("data", questions.get(i).returnList());
            arr.add(ent.clone());
        }

        JSONObject ret = new JSONObject();
        ret.put("questionsList", arr);
        ret.put("examId", this.examID);
        ret.put("examName", this.examName);

        try(FileWriter f = new FileWriter("Byquestion.txt")){
            f.write(ret.toString());
            f.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getByquestion() throws FileNotFoundException {
        File studentpage = new File("Byquestion.txt");
        Scanner studentPageScanner = new Scanner(studentpage);
        String studentPageEndpointString = studentPageScanner.nextLine();

        return studentPageEndpointString;
    }

    public void buildStatsJSON(Stats stats, List<Student> students){

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
        ret.put("questionFrequency", stats.getFrequency());
        ret.put("percentile", stats.getPercentiles());
        ret.put("deviation", stats.getDeviation());


        int a, b, c, d, e, f;
        a = b = c = d = e = f = 0;

        for(int i = 0; i < students.size(); i++){
            String temp = students.get(i).getLetterGrade();
            if (temp.equals("A")){
                a++;
            }else if (temp.equals("B")){
                b++;
            }else if (temp.equals("C")){
                c++;
            }else if (temp.equals("D")){
                d++;
            }else if (temp.equals("E")){
                e++;
            }else{
                f++;
            }
        }

        JSONObject ent = new JSONObject();
        JSONArray arr = new JSONArray();
        ent.put("Value", "A");
        ent.put("Scores", a);
        arr.add(ent.clone());
        ent.clear();

        ent.put("Value", "B");
        ent.put("Scores", b);
        arr.add(ent.clone());
        ent.clear();

        ent.put("Value", "C");
        ent.put("Scores", c);
        arr.add(ent.clone());
        ent.clear();

        ent.put("Value", "D");
        ent.put("Scores", d);
        arr.add(ent.clone());
        ent.clear();

        ent.put("Value", "E");
        ent.put("Scores", e);
        arr.add(ent.clone());
        ent.clear();

        ent.put("Value", "F");
        ent.put("Scores", f);
        arr.add(ent.clone());
        ent.clear();

        ret.put("byLetterGrade", arr);

        try(FileWriter fw = new FileWriter("Stats.txt")){
            fw.write(ret.toString());
            fw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public String getBystats() throws FileNotFoundException {
        File studentpage = new File("Stats.txt");
        Scanner studentPageScanner = new Scanner(studentpage);
        String studentPageEndpointString = studentPageScanner.nextLine();

        return studentPageEndpointString;
    }

    public void buildMainpageJSON() {

        try (FileWriter f = new FileWriter("Mainpage.txt")) {
            f.write("");
            f.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetAllJSON() {
        try(FileWriter f = new FileWriter("Bystudent.txt")){
            f.write("Nothing to display");
            f.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter f = new FileWriter("AnswerKey.txt")){
            f.write("Nothing to display");
            f.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter f = new FileWriter("Byquestion.txt")){
            f.write("Nothing to display");
            f.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter f = new FileWriter("Stats.txt")){
            f.write("Nothing to display");
            f.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //All of the prior methods build JSON from the Grader, Stats, Question array, Student array, and information from database
    //It is in the format that gui needs.
}