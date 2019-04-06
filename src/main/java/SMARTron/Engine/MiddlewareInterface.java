
import java.util.ArrayList;
import java.util.List;

public class MiddlewareInterface {


    private List<Student> studentExams = new ArrayList<>();
    private List<Integer> studentGradesForStats = new ArrayList<>();
    private List<Float> studentNumberGrades = new ArrayList<>();
    private List<String> studentLetterGrades  = new ArrayList<>();
    private Grader grader = new Grader();
    private Stats stats = new Stats();
    private LetterConverter letterConverter = new LetterConverter();
    private JSONBuilder jsonBuilder = new JSONBuilder();
    private StudentCreater studentCreater = new StudentCreater();
    private Student key = new Student();
    private String examName = "";
    private String examID = "";

    public void addStudentExam(String StudentData){
        this.studentExams.add(studentCreater.makeSecondPassStudent(StudentData));
    }

    public void getGrades(){
        this.getExamGrades();
        this.getExamLetterGrades();
        this.examGradeIntegerConverter();
        this.getExamID();
        this.assignStudentGrades();
        this.getStats();
        this.makeJSON();
    }

    private void getExamGrades(){
        this.key = studentExams.get(0);
        this.studentNumberGrades = grader.getGrades(studentExams, key);
    }

    private void getStats(){
        this.stats.setScores(studentGradesForStats, key.getAnswers(), studentExams);
        this.stats.getStats();
    }

    private void getExamLetterGrades(){
        this.studentLetterGrades = letterConverter.genLetterGrade(studentNumberGrades);
    }

    private void examGradeIntegerConverter(){
        for (Float i: studentNumberGrades) {
            studentGradesForStats.add(Math.round(i));
        }
    }

    private void getExamID(){
        this.examID = this.key.getName();
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    private void assignStudentGrades(){
        for (int i = 0; i < studentExams.size(); i++){
            studentExams.get(i).setExamGrade(studentNumberGrades.get(i));
            studentExams.get(i).setLetterGrade(studentLetterGrades.get(i));
        }
    }

    private void makeJSON(){
        jsonBuilder.setExamCode(examID);
        jsonBuilder.setExamName(examName);

        jsonBuilder.buildMainpageJSON();
        jsonBuilder.buildAnswerKeyJSON(this.key);
        jsonBuilder.buildByStudentJSON(this.studentExams);
        jsonBuilder.buildByQuestion(this.grader.getStatsByQuestion());
        //jsonBuilder.buildStatsJSON(stats);

    }
}