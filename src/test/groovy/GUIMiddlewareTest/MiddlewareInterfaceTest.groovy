package GUIMiddlewareTest

import GUIMiddleware.MiddlewareInterface
import GUIMiddleware.Stats
import GUIMiddleware.Student
import spock.lang.Specification

import java.lang.reflect.Method

class MiddlewareInterfaceTest extends Specification {

    MiddlewareInterface mi = new MiddlewareInterface()

    def "add student exam"() {
        when:
        String second = "Dilbert,M,87,01/23/19,TTYSD,CFGHW,4,3,3,3,4,1,2,0,0,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        int before = mi.getStudentExams().size()
        mi.addStudentExam(second)
        int after = mi.getStudentExams().size()

        then:
        before == 0
        after == 1

    }


    def "fail add student exam from incorrectly formatted string"() {
        when:
        String second = "test string to break it"
        int before = mi.getStudentExams().size()
        mi.addStudentExam(second)
        int after = mi.getStudentExams().size()

        then:
        thrown Exception
        before == 0
        after == 0

    }

    def "successful getExamID"() {
        when:
        String second = "Dilbert,M,87,01/23/19,TTYSD,CFGHW,4,3,3,3,4,1,2,0,0,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(second)
        Class aClass = mi.getClass()
        Method method = aClass.getDeclaredMethod("getExamID")
        method.setAccessible(true)
        method.invoke(mi)
        String id = mi.getExamId()

        then:
        id.compareTo("") != 0

    }

    def "fail getExamID from no id"() {
        when:
        String second = "Dilbert,M,87,01/23/19"
        mi.addStudentExam(second)
        Class aClass = mi.getClass()
        Method method = aClass.getDeclaredMethod("getExamID")
        method.setAccessible(true)
        method.invoke(mi)
        String id = mi.getExamId()

        then:
        thrown Exception//scanner will try to read nothing
    }

    def "fail getExamID from invalid string"() {
        when:
        String second = "test"
        mi.addStudentExam(second)
        Class aClass = mi.getClass()
        Method method = aClass.getDeclaredMethod("getExamID")
        method.setAccessible(true)
        method.invoke(mi)
        String id = mi.getExamId()

        then:
        thrown Exception
    }

    def "get exam grades"() {
        String second = "Dilbert,M,87,01/23/19,TTYSD,CFGHW,4,3,3,3,4,1,2,0,0,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(second)
        String secon = "Frank,M,67,11/13/15,FGHSD,TFGRW,1,2,1,1,2,2,1,1,1,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(secon)
        when:
        int before = mi.getStudentNumberGrades().size()
        int befor = mi.getKey().answers.size()
        Class aClass = mi.getClass()
        Method method = aClass.getDeclaredMethod("getExamGrades")
        method.setAccessible(true)
        method.invoke(mi)
        int after = mi.getStudentNumberGrades().size()
        int afte = mi.getKey().answers.size()

        then:
        before == 0
        befor == 0
        after == 1
        afte == 100
    }

    def "fail getExamGrades from invalid strings"() {
        String second = "Dilbert is #1"
        mi.addStudentExam(second)
        String secon = "Frank,M,67,11/13/15,FGHSD,TFGRW,1,2,1,1,2,2,1,1,1,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(secon)
        when:
        int before = mi.getStudentNumberGrades().size()
        int befor = mi.getKey().answers.size()
        Class aClass = mi.getClass()
        Method method = aClass.getDeclaredMethod("getExamGrades")
        method.setAccessible(true)
        method.invoke(mi)
        int after = mi.getStudentNumberGrades().size()
        int afte = mi.getKey().answers.size()

        then:
        thrown Exception
    }

    def "successful getExamLetterGrades"() {
        String second = "Dilbert,M,87,01/23/19,TTYSD,CFGHW,4,3,3,3,4,1,2,0,0,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(second)
        String secon = "Frank,M,67,11/13/15,FGHSD,TFGRW,1,2,1,1,2,2,1,1,1,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(secon)
        when:
        Class aClass = mi.getClass()
        Method method = aClass.getDeclaredMethod("getExamGrades")
        method.setAccessible(true)
        method.invoke(mi)
        method = aClass.getDeclaredMethod("getExamLetterGrades")
        method.setAccessible(true)
        method.invoke(mi)
        List<String> grades = mi.getStudentLetterGrades()

        then:
        grades.size() == 1
        //should have only 1 grade. Returning 2 so the exam key must be getting counted as well
    }

    def "fail getExamLetterGrades from invalid strings"() {
        String second = "Dilbert is #1"
        mi.addStudentExam(second)
        String secon = "Frank,M,67,11/13/15,FGHSD,TFGRW,1,2,1,1,2,2,1,1,1,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(secon)
        when:
        Class aClass = mi.getClass()
        Method method = aClass.getDeclaredMethod("getExamGrades")
        method.setAccessible(true)
        method.invoke(mi)
        method = aClass.getDeclaredMethod("getExamLetterGrades")
        method.setAccessible(true)
        method.invoke(mi)
        List<String> grades = mi.getStudentLetterGrades()

        then:
        thrown Exception
    }

    def "successful examGradeIntegerConverter"() {
        String second = "Dilbert,M,87,01/23/19,TTYSD,CFGHW,4,3,3,3,4,1,2,0,0,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(second)
        String secon = "Frank,M,67,11/13/15,FGHSD,TFGRW,1,2,1,1,2,2,1,1,1,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(secon)
        when:
        Class aClass = mi.getClass()
        Method method = aClass.getDeclaredMethod("getExamGrades")
        method.setAccessible(true)
        method.invoke(mi)
        method = aClass.getDeclaredMethod("examGradeIntegerConverter")
        method.setAccessible(true)
        method.invoke(mi)
        List<Integer> grades = mi.getStudentGradesForStats()

        then:
        grades.size() == 1
        //should have only 1 grade. Returning 2 so the exam key must be getting counted as well
    }

    def "fail examGradeIntegerConverter from invalid strings"() {
        String second = "Dilbert is #1"
        mi.addStudentExam(second)
        String secon = "Frank,M,67,11/13/15,FGHSD,TFGRW,1,2,1,1,2,2,1,1,1,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(secon)
        when:
        Class aClass = mi.getClass()
        Method method = aClass.getDeclaredMethod("getExamGrades")
        method.setAccessible(true)
        method.invoke(mi)
        method = aClass.getDeclaredMethod("examGradeIntegerConverter")
        method.setAccessible(true)
        method.invoke(mi)
        List<Integer> grades = em.getStudentGradesForStats()

        then:
        thrown Exception
    }

    def "successful assignStudentGrades"() {
        String second = "Dilbert,M,87,01/23/19,TTYSD,CFGHW,4,3,3,3,4,1,2,0,0,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(second)
        String secon = "Frank,M,67,11/13/15,FGHSD,TFGRW,1,2,1,1,2,2,1,1,1,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(secon)
        when:
        Class aClass = mi.getClass()
        Method method = aClass.getDeclaredMethod("getExamGrades")
        method.setAccessible(true)
        method.invoke(mi)
        method = aClass.getDeclaredMethod("getExamLetterGrades")
        method.setAccessible(true)
        method.invoke(mi)
        method = aClass.getDeclaredMethod("assignStudentGrades")
        method.setAccessible(true)
        method.invoke(mi)
        List<Student> students = mi.getStudentExams()

        then:
        students.size() == 1
        students.get(0).getLetterGrade() == 'D'
        students.get(0).getExamGrade() == 64.0
        //the key is being graded against itself and is the first in the list, failing this test
    }

    def "fail assignStudentGrades from invalid strings"() {
        String second = "Dilbert is #1"
        mi.addStudentExam(second)
        String secon = "Frank,M,67,11/13/15,FGHSD,TFGRW,1,2,1,1,2,2,1,1,1,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(secon)
        when:
        Class aClass = mi.getClass()
        Method method = aClass.getDeclaredMethod("getExamGrades")
        method.setAccessible(true)
        method.invoke(mi)
        method = aClass.getDeclaredMethod("getExamLetterGrades")
        method.setAccessible(true)
        method.invoke(mi)
        method = aClass.getDeclaredMethod("assignStudentGrades")
        method.setAccessible(true)
        method.invoke(mi)
        List<Student> students = mi.getStudentExams()

        then:
        thrown Exception
    }

    def "successful getStats"() {
        String second = "Dilbert,M,87,01/23/19,TTYSD,CFGHW,4,3,3,3,4,1,2,0,0,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(second)
        String secon = "Frank,M,67,11/13/15,FGHSD,TFGRW,1,2,1,1,2,2,1,1,1,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(secon)
        when:
        Class aClass = mi.getClass()
        Method method = aClass.getDeclaredMethod("getExamGrades")
        method.setAccessible(true)
        method.invoke(mi)
        method = aClass.getDeclaredMethod("examGradeIntegerConverter")
        method.setAccessible(true)
        method.invoke(mi)
        method = aClass.getDeclaredMethod("getStats")
        method.setAccessible(true)
        method.invoke(mi)
        Stats stats = mi.getstats()

        then:
        //checking to see if the value in stats changed.
        0 != stats.getMax().compareTo("")
    }

    def "fail getStats from invalid strings"() {
        String second = "Dilbert is #1"
        mi.addStudentExam(second)
        String secon = "Frank,M,67,11/13/15,FGHSD,TFGRW,1,2,1,1,2,2,1,1,1,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(secon)
        when:
        Class aClass = mi.getClass()
        Method method = aClass.getDeclaredMethod("getExamGrades")
        method.setAccessible(true)
        method.invoke(mi)
        method = aClass.getDeclaredMethod("examGradeIntegerConverter")
        method.setAccessible(true)
        method.invoke(mi)
        method = aClass.getDeclaredMethod("getStats")
        method.setAccessible(true)
        method.invoke(mi)
        Stats stats = mi.getstats()

        then:
        thrown Exception
    }

    def "successful getGrades"() {
        String second = "Dilbert,M,87,01/23/19,TTYSD,CFGHW,4,3,3,3,4,1,2,0,0,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(second)
        String secon = "Frank,M,67,11/13/15,FGHSD,TFGRW,1,2,1,1,2,2,1,1,1,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(secon)
        when:
        int before = mi.getStudentNumberGrades().size()
        int befor = mi.getKey().answers.size()
        Class aClass = mi.getClass()
        Method method = aClass.getDeclaredMethod("getGrades")
        method.setAccessible(true)
        method.invoke(mi)
        int after = mi.getStudentNumberGrades().size()
        int afte = mi.getKey().answers.size()
        List<String> grades = mi.getStudentLetterGrades()
        List<Integer> grade = mi.getStudentGradesForStats()
        String id = mi.getExamId()
        List<Student> students = mi.getStudentExams()
        Stats stats = mi.getstats()

        then:
        before == 0
        befor == 0
        after == 1
        afte == 100
        grades.size() == 1
        grade.size() == 1
        id.compareTo("") != 0
        students.size() == 1
        students.get(0).getLetterGrade() == 'D'
        students.get(0).getExamGrade() == 64.0
        0 != stats.getMax().compareTo("")
    }

    def "fail getGrades from invalid strings"() {
        String second = "Dilbert is #1"
        mi.addStudentExam(second)
        String secon = "Frank,M,67,11/13/15,FGHSD,TFGRW,1,2,1,1,2,2,1,1,1,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1,-1,-1," +
                "-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1" +
                ",-1,-1,-1"
        mi.addStudentExam(secon)
        when:
        int before = mi.getStudentNumberGrades().size()
        int befor = mi.getKey().answers.size()
        Class aClass = mi.getClass()
        Method method = aClass.getDeclaredMethod("getGrades")
        method.setAccessible(true)
        method.invoke(mi)
        int after = mi.getStudentNumberGrades().size()
        int afte = mi.getKey().answers.size()
        List<String> grades = mi.getStudentLetterGrades()
        List<Integer> grade = mi.getStudentGradesForStats()
        String id = mi.getExamId()
        List<Student> students = mi.getStudentExams()
        Stats stats = mi.getstats()

        then:
        thrown Exception
    }

}