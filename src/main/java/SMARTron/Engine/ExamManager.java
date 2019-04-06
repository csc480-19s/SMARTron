
import java.util.ArrayList;
import java.util.List;

public class ExamManager {

    private List<Student> studentExams = new ArrayList<>();
    private List<Integer> studentGradesForStats = new ArrayList<>();
    private List<Float> studentNumberGrades = new ArrayList<>();
    private List<String> studentLetterGrades  = new ArrayList<>();
    private Grader grader = new Grader();
    private Stats stats = new Stats();
    private LetterConverter letterConverter = new LetterConverter();
    private StudentCreater studentCreater = new StudentCreater();
    private Student key = new Student();
    private String examName = "";
    private String examID = "";

    public void addStudentExam(String[][] answers, String[] StudentData){
        this.studentExams.add(studentCreater.makeStudent(answers, StudentData));
    }

    public void getGrades(){
        this.getExamGrades();
        this.getExamLetterGrades();
        this.examGradeIntegerConverter();
        this.getExamID();
        this.assignStudentGrades();
        this.getStats();
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
        this.examName = examName; //mainly for the middlewhere
    }

    private void assignStudentGrades(){
        for (int i = 0; i < studentExams.size(); i++){
            studentExams.get(i).setExamGrade(studentNumberGrades.get(i));
            studentExams.get(i).setLetterGrade(studentLetterGrades.get(i));
        }
    }
}
