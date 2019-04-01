package SMARTron.Engine;

public class Student { // VV all the attributes of a scantron.

    public String getName() {
        return name;
    }

    private String name;

    private String sex;

    private String birthday;

    private String grade;

    private float examGrade;

    private String id;

    private String code;

    private String[] answers;

    private Tools t;

// when a student object is made, 

    //Fancy for loop assigns all of these values to the individual variables in the student object from the correct indexes.
    public Student(String[] s){
        t = new Tools();
        answers = s;
        String temp = "";
        for(int i = 0; i < 20; i++){
            if (answers[i] != null && !answers[i].equals("error")) {
                temp = temp + t.alphaConverter(answers[i]);
            } else {
                temp = temp + "-";
            }
        }
        name = temp;
        temp = "";
        if(answers[20] != null && !answers[20].equals("-1")) {
            sex = answers[20];
        }
        if(answers[21] != null && !answers[21].equals("-1")) {
            grade = answers[21];
        }
        for(int i = 22; i < 27; i++){
            if(answers[i] != null && !answers[i].equals("-1")) {
                temp = temp + answers[i];
            }
        }
        birthday = temp;
        temp = "";
        for(int i = 26; i < 37; i++){
            if(answers[i] != null && !answers[i].equals("-1")) {
                temp = temp + answers[i];
            }
        }
        id = temp;
        temp = "";
        for(int i = 36; i < 43; i++){
            if(answers[i] != null && !answers[i].equals("-1")) {
                temp = temp + answers[i];
            }
        }
        code = temp;
    }


    //public void printStudent(){
    //System.out.println("Name: " + this.name + " | " + "Id: " + this.id);
    //}

    public String[] getAnswers(){
        return this.answers;
    }

    public String getSex(){
        return this.sex;
    }

    public String getBirthday(){
        return this.birthday;
    }

    public String getGrade(){
        return this.grade;
    }

    public float getExamGrade(){
        return this.examGrade;
    }

    public void setExamGrade(float g){
        this.examGrade = g;
    }

    public String getID(){
        return this.id;
    }
    public String getCode(){
        return this.code;
    }
}