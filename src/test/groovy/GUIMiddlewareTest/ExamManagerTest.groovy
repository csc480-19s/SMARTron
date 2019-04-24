package GUIMiddlewareTest

import GUIMiddleware.Grader
import GUIMiddleware.LetterConverter
import GUIMiddleware.Stats
import GUIMiddleware.Student
import GUIMiddleware.StudentCreator
import spock.lang.Specification
import GUIMiddleware.ExamManager

import java.lang.reflect.Method

class ExamManagerTest extends Specification {

    ExamManager em = new ExamManager()
    //dependencies
    List<Student> studentExams
    List<Integer> studentGradesForStats
    List<Float> studentNumberGrades
    List<String> studentLetterGrades
    Grader grader
    Stats stats
    LetterConverter letterConverter
    StudentCreator studentCreator
    Student key

    //addStudentExam is a wrapper for the makeStudent() in StudentCreator
    def "add student exam"() {
        when:
        String[][] first = [["need","test","strings"],["in a 2d","array","for mocked studentcreator"]]
        String[] second = ['10', '15', '14', '4', '14', '0', '20', '18', '25', '14', '9', '19', '11', '9', '0', '13', '0', '0', '0', '0', '13', '1', '1', '1', '7', '9', '2', '8', '0', '3', '0', '4', '3', '9', '7', '4', '1', '2', '3', '4', '5', '6', '0']
        int before = em.getStudentExams().size()
        em.addStudentExam(first, second)
        int after = em.getStudentExams().size()

        then:
        before == 0
        after == 1

    }

    def "fail add student exam, bad first parameter"() {
        when:
        int first = 42
        String[] second = ['10', '15', '14', '4', '14', '0', '20', '18', '25', '14', '9', '19', '11', '9', '0', '13', '0', '0', '0', '0', '13', '1', '1', '1', '7', '9', '2', '8', '0', '3', '0', '4', '3', '9', '7', '4', '1', '2', '3', '4', '5', '6', '0']
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
        String[][] first = [["need","test","strings"],["in a 2d","array","for mocked studentcreator"]]
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
        String[][] first = [['4','3','3','3','4'],['1','2','0','0','2'],['0','1','2','1','3'],['2','0','3','3','2'],['0','1','2','3','1']]
        String[] second = ['10', '15', '14', '4', '14', '0', '20', '18', '25', '14', '9', '19', '11', '9', '0', '13', '0', '0', '0', '0', '13', '1', '1', '1', '7', '9', '2', '8', '0', '3', '0', '4', '3', '9', '7', '4', '1', '2', '3', '4', '5', '6', '0']
        em.addStudentExam(first, second)
        String[][] firs = [['3','3','0','1','2'],['0','1','0','0','2'],['0','1','2','1','3'],['2','0','3','3','2'],['2','2','2','3','2']]
        String[] secon = ['11', '12', '16', '2', '10', '3', '10', '15', '23', '12', '10', '13', '11', '9', '0', '13', '0', '0', '0', '0', '13', '1', '1', '1', '7', '9', '2', '8', '0', '3', '0', '4', '3', '9', '7', '4', '1', '2', '3', '4', '5', '6', '0']
        em.addStudentExam(first, second)
        when:
        int before = em.getStudentNumberGrades().size()
        int befor = em.getKey().answers.size()
        Class aClass = em.getClass()
        Method method = aClass.getDeclaredMethod("getExamGrades")
        method.setAccessible(true)
        method.invoke(em)
        int after = em.getStudentNumberGrades().size()
        int afte = em.getKey().answers.size()

        then:
        before == 0
        befor == 0
        after == 2
        afte == 25

    }

}
