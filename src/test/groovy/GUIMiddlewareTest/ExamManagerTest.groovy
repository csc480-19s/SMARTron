package GUIMiddlewareTest

import GUIMiddleware.Grader
import GUIMiddleware.LetterConverter
import GUIMiddleware.Stats
import GUIMiddleware.Student
import GUIMiddleware.StudentCreator
import spock.lang.Specification
import GUIMiddleware.ExamManager

class ExamManagerTest extends Specification {

    ExamManager em = new ExamManager()
    //dependencies
    private List<Student> studentExams
    private List<Integer> studentGradesForStats
    private List<Float> studentNumberGrades
    private List<String> studentLetterGrades
    private Grader grader
    private Stats stats
    private LetterConverter letterConverter
    private StudentCreator studentCreator
    private Student key

    public void setup(){
        grader = Mock(Grader.class)
        stats = Mock(Stats.class)
        letterConverter = Mock(LetterConverter.class)
        studentCreator = Mock(StudentCreator.class)
        key = Mock(Student.class)
        studentExams = new ArrayList<>();
        studentGradesForStats = new ArrayList<>();
        studentNumberGrades = new ArrayList<>();
        studentLetterGrades  = new ArrayList<>();
        //exammanager stuff here

    }

    //addStudentExam is a wrapper for the makeStudent() in StudentCreator
    def "add student exam"() {
        when:
        String[][] first= {"need" "test" "strings"}{"in a 2d" "array" "for mocked studentcreator"}
        String[] second = {"yet more" "strings for" "mocked obj"}
        int before = em.getStudentExams().size()
        em.addStudentExam(first, second)
        int after = em.getStudentExams().size()

        then:
        before == 0
        after == 1

    }

    def "fail add student exam, bad first parameter"() {
        when:
        int first= 42
        String[] second = {"yet more" "strings for" "mocked obj"}
        int before = em.getStudentExams().size()
        em.addStudentExam(first, second)
        int after = em.getStudentExams().size()

        then:
        thrown Exception
        before == 0
        after == 0

    }

    def "fail add student exam, bad second parameter"() {
        when:
        String[][] first= {"need" "test" "strings"}{"in a 2d" "array" "for mocked studentcreator"}
        boolean second = true
        int before = em.getStudentExams().size()
        em.addStudentExam(first, second)
        int after = em.getStudentExams().size()

        then:
        thrown Exception
        before == 0
        after == 0

    }

    def "get exam grades"() {
        when:


        then:

    }

}
