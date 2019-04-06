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

    public void initializeStudentFromStudentData(List<String> studentData) {
        this.studentData = studentData;

        findName();
        findSex();
        findGrade();
        findBirthday();
        findId();
        findCode();
    }

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

    public void initializeAnswers(List<String> answers) {
        this.answers = answers;

    }

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

    public JSONObject genJsonData() {

        JSONObject obj = new JSONObject();

        obj.put("name", this.name);
        obj.put("grade", this.letterGrade);
        //obj.put("points", this.numPoints);
        obj.put("percent", this.examGrade);

        return obj;

    }

    public String alphaConverter(String entry) {
        int c = Integer.parseInt(entry);
        if (c >= 0 && c <= 26) {
            c = c + 64;
            char ret = (char) c;
            return String.valueOf(ret);
        }

        return "";
    }

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
}
