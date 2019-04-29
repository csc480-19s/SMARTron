package GUIMiddleware;

import java.util.ArrayList;
import java.util.List;


public class ExamManager {

//This class is a high level controller for making student objects, grade them, and process them


    private List<Student> studentExams = new ArrayList<>();
    private List<Integer> studentGradesForStats = new ArrayList<>();
    private List<Float> studentNumberGrades = new ArrayList<>();
    private List<String> studentLetterGrades  = new ArrayList<>();
    private Grader grader = new Grader();
    private Stats stats = new Stats();
    private LetterConverter letterConverter = new LetterConverter();
    private StudentCreator studentCreator = new StudentCreator();
    private Student key = new Student();
    //The bottom two variables are unused and can be removed I am guessing.SS
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
     *studentCreator Makes student classes.
     *
     *key This is the test key, It is a student class.
     *
     *examName Exam Name.
     *
     *examID Exam ID.
     */


    public void addStudentExam(String[][] answers, String[] studentData){
        this.studentExams.add(studentCreator.makeStudent(answers, studentData));
    }

    /**
     *addStudentExam() uses the StudentCreator to add a student to the studentExams
     *answers is a multidimensional array that holds the answer data in the order it was given.
     *studentData carries the first 43 points of information on the scantron
    **/


    public void getGrades(){
        this.getExamGrades();
        this.getExamLetterGrades();
        this.examGradeIntegerConverter();
        this.getExamID();
        this.assignStudentGrades();
        //this.getStats();
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

    private void assignStudentGrades(){
        for (int i = 0; i < studentExams.size(); i++){
            studentExams.get(i).setExamGrade(studentNumberGrades.get(i));
            studentExams.get(i).setLetterGrade(studentLetterGrades.get(i));
        }
    }

    //Assigns the grades for all the students in studentExams from "studentNumberGrades" and "studentLetterGrades"
    
    public List<Student> getStudents() {
        return studentExams;
    }
    
    public Student getKey() {
        return key;
    }

    public List<Student> getStudentExams() {
        return studentExams;
    }

    public List<Float> getStudentNumberGrades() {
        return studentNumberGrades;
    }

    public List<String> getStudentLetterGrades() {
        return studentLetterGrades;
    }

    public List<Integer> getStudentGradesForStats() {
        return studentGradesForStats;
    }

    public Stats getstats(){
        return stats;
    }

    public String getExamId(){
        return examID;
    }
}
