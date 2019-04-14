
import java.util.ArrayList;
import java.util.List;

public class MiddlewareInterface {

    //This class is a high level controller for making student objects, grade them, and process them

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

    /**
     *
     *studentExams list of student objects built from database.
     *
     *studentGradesForStats Integer array of scores from the Grader class, used in Stats class.
     *They are in the same indexed order as the "studentExams".
     *
     *studentNumberGrades Float array of scores from the Grader class, they are put into "studentExams"
     *They are in the same indexed order as the "studentExams".
     *
     *studentLetterGrades String array of letter scores from grader, they are put into "studentExams"
     * They are in the same indexed order as the "studentExams".
     *
     *grader Grades the "studentExams".
     *
     *stats Finds stats from the grades.
     *
     *letterConverter Returns a list of letter grades based off of "studentNumberGrades".
     *
     *studentCreater Makes student classes.
     *
     *key This is the test key, It is a student class.
     *
     *examName Exam Name.
     *
     *examID Exam ID.
     */

    public void addStudentExam(String StudentData){
        this.studentExams.add(studentCreater.makeSecondPassStudent(StudentData));
    }

    /**
     *addStudentExam() uses the StudentCreator to add a student to the studentExams
     *studentData carries all the information needed to make a student object
     **/

    public void getGrades(){
        this.getExamGrades();
        this.getExamLetterGrades();
        this.examGradeIntegerConverter();
        this.getExamID();
        this.assignStudentGrades();
        this.getStats();
        this.makeJSON();
    }

    //Rns most of the important student processing methods in one go

    private void getExamGrades(){
        this.key = studentExams.get(0);
        this.studentNumberGrades = grader.getGrades(studentExams, key);
    }

    //Calls the grader getGrades() method

    private void getStats(){
        this.stats.setScores(studentGradesForStats, key.getAnswers(), studentExams);
        this.stats.getStats();
    }

    //Calls the stats getStats() method

    private void getExamLetterGrades(){
        this.studentLetterGrades = letterConverter.genLetterGrade(studentNumberGrades);
    }

    //Calls the letterConverter genLetterGrade() method

    private void examGradeIntegerConverter(){
        for (Float i: studentNumberGrades) {
            studentGradesForStats.add(Math.round(i));
        }
    }

    //Adds values to the stats class

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

    //Assigns the grades for all the students in studentExams from "studentNumberGrades" and "studentLetterGrades"

    private void makeJSON(){
        jsonBuilder.setExamCode(examID);
        jsonBuilder.setExamName(examName);

        jsonBuilder.buildMainpageJSON();
        jsonBuilder.buildAnswerKeyJSON(this.key);
        jsonBuilder.buildByStudentJSON(this.studentExams);
        jsonBuilder.buildByQuestion(this.grader.getStatsByQuestion());
        //jsonBuilder.buildStatsJSON(stats);

    }
    //This makes the json for the gui
}