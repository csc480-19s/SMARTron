package groovy.GUIMiddlewareTest

import GUIMiddleware.Student
import spock.lang.Specification
import java.lang.reflect.Method

class StudentTest extends Specification{

    Student student = new Student()

    def "This test the correct initializeAnswers method"(){

        when:
        List<String> answers = ['a', 'b', 'c', 'd']
        student.initializeAnswers(answers)

        then:
        notThrown(Exception)
        student.answers == ['a', 'b', 'c', 'd']
        student.answers != ['a', 'b', 'c', 'e']

    }

    //I am guessing this doesnt fail due to the fact
    //java may be casting that list to string
    def "Tests addition of incorrect type"(){

        when:
        List<Integer> answers = [1,2,3]
        student.initializeAnswers(answers)

        then:
        thrown(Exception)

    }

    def "Test the findName method"(){

        when:
        def testString = ['12', '20', '12','26', '2', '1', '2', '4', '5', '0', '22', '4', '12', '24', '14', '15', '16', '9', '0', '0']
        student.studentData = testString
        Class aClass = student.getClass()
        Method method = aClass.getDeclaredMethod("findName")
        method.setAccessible(true)
        method.invoke(student)

        then:
        student.name == "Jondn Tryniski"

    }



}
