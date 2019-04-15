package smartron.entities;

public class Exam {
    String examName;
    String examCode;
    String timeStamp;

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public String getTimestamp() {
        return timeStamp;
    }

    public void setTimestamp(String timestamp) {
        this.timeStamp = timestamp;
    }
    public Exam(String code, String name, String time){
        examName=name;
        examCode = code;
        timeStamp = time;
    }
}
