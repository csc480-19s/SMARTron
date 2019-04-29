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

}