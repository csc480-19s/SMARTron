package GUIMiddleware;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student {

    private String name = "nil";

    private String sex = "nil";

    private String birthday = "nil";

    private String grade = "nil";

    private float examGrade = 0;

    private String id = "nil";

    private String code = "nil";

    private List<String> answers = new ArrayList<>();

    private List<String> studentData = new ArrayList<>();

    private String letterGrade = "nil";

    /**
     *name, sex, birthday, grade, id, code - all of these variables are the points
     *of information used on every scantron sheet
     *
     *examGrade calculated exam grade is assigned after grader passes through
     *answers array of all answers used for grading
     *
     *studentData array of all raw "first43" data-points from scantron
     *
     *letterGrade calculated letter grade
     */

    public void initializeStudentFromStudentData(List<String> studentData) {
        this.studentData = studentData;

        findName();
        findSex();
        findGrade();
        findBirthday();
        findId();
        findCode();
    }

    //This method is used by the StudentCreator, it takes a list of strings and runs methods responsible
    //for finding the main points of information

    public void initializeStudentFromDatabaseString(String databaseString){
        this.answers.clear();
        Scanner stringScanner = new Scanner(databaseString);
        stringScanner.useDelimiter(",");

        this.name = stringScanner.next();
        this.sex = stringScanner.next();
        this.grade = stringScanner.next();
        this.birthday = stringScanner.next();
        this.id = stringScanner.next();
        this.code = stringScanner.next();

        while(stringScanner.hasNext()){
            answers.add(stringScanner.next());
        }
    }

    //unlike the method above this method accepts one string,
    //it uses a delimiter to extract the information

    public void initializeAnswers(List<String> answers) {
        this.answers = answers;

    }

    //initializes answer array

    private void findName() {
        String temp = "";
        for (int i = 0; i < 20; i++) {
            if (studentData.get(i) != null && !studentData.get(i).equals("error") && !studentData.get(i).equals("-1")) {
                temp = temp + this.alphaConverter(studentData.get(i));
            } else {
                temp = temp + ".";
            }
        }

        this.name = temp;
    }

    private void findSex() {
        if (studentData.get(20) != null && !studentData.get(20).equals("-1") && !studentData.get(20).equals("error")) {
            this.sex = studentData.get(20);
        } else {
            this.sex = ".";
        }
    }

    private void findGrade() {
        if (studentData.get(21) != null && !studentData.get(21).equals("-1") && !studentData.get(21).equals("error")) {
            this.grade = studentData.get(21);
        } else {
            this.grade = ".";
        }
    }

    private void findBirthday() {
        String temp = "";

        for (int i = 22; i < 27; i++) {
            if (studentData.get(i) != null && !studentData.get(i).equals("error") && !studentData.get(i).equals("-1")) {
                temp = temp + this.alphaConverter(studentData.get(i));
            } else {
                temp = temp + ".";
            }
        }
        this.birthday = temp;
    }

    private void findId() {
        String temp = "";

        for (int i = 26; i < 37; i++) {
            if (studentData.get(i) != null && !studentData.get(i).equals("error") && !studentData.get(i).equals("-1")) {
                temp = temp + studentData.get(i);
            } else {
                temp = temp + ".";
            }
        }
        this.id = temp;
    }

    private void findCode() {
        String temp = "";

        for (int i = 36; i < 43; i++) {
            if (studentData.get(i) != null && !studentData.get(i).equals("error") && !studentData.get(i).equals("-1")) {
                temp = temp + studentData.get(i);
            } else {
                temp = temp + ".";
            }
        }
        this.code = temp;
    }

    //all of these methods extract, and construct specifically indexed information from the string array

    public JSONObject genJsonData() {

        JSONObject obj = new JSONObject();

        obj.put("name", this.name);
        obj.put("grade", this.letterGrade);
        //obj.put("points", this.numPoints);
        obj.put("percent", this.examGrade);

        return obj;

    }

    //Constructs JSON for this individual student

    public String alphaConverter(String entry) {
        int c = Integer.parseInt(entry);
        if (c >= 0 && c <= 26) {
            c = c + 64;
            char ret = (char) c;
            return String.valueOf(ret);
        }

        return "";
    }

    //Used to convert ints to alpha characters [i.e. 1 -> "A"]

    public void setExamGrade(float grade) {
        this.examGrade = grade;
    }

    public  void setLetterGrade(String letterGrade){
        this.letterGrade  = letterGrade;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGrade() {
        return grade;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getLetterGrade() {
        return letterGrade;
    }
    
    public float getExamGrade() {
        return examGrade;
    }
}
