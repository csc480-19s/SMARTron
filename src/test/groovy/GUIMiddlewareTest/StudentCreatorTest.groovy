package GUIMiddlewareTest

import GUIMiddleware.Student
import spock.lang.Specification
import GUIMiddleware.StudentCreator

class StudentCreatorTest extends Specification {

    StudentCreator sc = new StudentCreator()

    def "Test the makeStudent method"() {
        String[][]answers = [['3', '1', '2', '5']]
        String[] studentInfo = ['10', '15', '14', '4', '14', '0', '20', '18', '25', '14', '9', '19', '11', '9', '0', '13', '0', '0', '0', '0', '13', '1', '1', '1', '7', '9', '2', '8', '0', '3', '0', '4', '3', '9', '7', '4', '1', '2', '3', '4', '5', '6', '1']

        when:
        def student = sc.makeStudent(answers, studentInfo)

        then:
        notThrown(Exception)
        student.getName() == "JONDN TRYNISKI M    "
        student.getSex() == "M"
        student.getGrade() == "A"
        student.getBirthday() == "11792"
        student.getId() == '803043974'
        student.getCode() == "ABCDEF"
        student.getAnswers() == ['3', '1', '2', '5']

    }

}
