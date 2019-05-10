package groovy.GUIMiddlewareTest

import GUIMiddleware.Student
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import spock.lang.Specification
import java.lang.reflect.Method

class StudentTest extends Specification{

    Student student = new Student()
    def testString = ['10', '15', '14', '4', '14', '0', '20', '18', '25', '14', '9', '19', '11', '9', '0', '13', '0', '0', '0', '0', '13', '1', '1', '1', '7', '9', '2', '8', '0', '3', '0', '4', '3', '9', '7', '4', '1', '2', '3', '4', '5', '6', '1']

    def "This test the correct initializeAnswers method"(){

        when:
        student.studentData = testString
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

    //Fails but because of the bounds issue from the alphaconverter
    //function
    def "Test the findName method"(){

        when:
        student.studentData = testString
        Class aClass = student.getClass()
        Method method = aClass.getDeclaredMethod("findName")
        method.setAccessible(true)
        method.invoke(student)

        then:
        student.name == "JONDN TRYNISKI M    "

    }

    def "Test the findName with an invalid entry"() {

        when:
        def string = ['1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', 'error', '1', '1', '1', '1', '1',]
        student.studentData = string
        Class aClass = student.getClass()
        Method method = aClass.getDeclaredMethod("findName")
        method.setAccessible(true)
        method.invoke(student)

        then:
        student.name == "AAAAAAAAAAAAAAA.AAAA"
    }

    def "Boundary test of the findName method outbound"() {

        when:
        student.studentData = testString
        Class aClass = student.getClass()
        Method method = aClass.getDeclaredMethod("findName")
        method.setAccessible(true)
        method.invoke(student)

        then:
        student.getName() == "JONDN TRYNISKI M    "

    }

    //Feel like this should convert to the letter instead of keeping it a number?!?!?!?!
    def "Test the findSex method"() {

        when:
        student.studentData = testString
        Class aClass = student.getClass()
        Method method = aClass.getDeclaredMethod("findSex")
        method.setAccessible(true)
        method.invoke(student)

        then:
        student.getSex() == "M"

    }

    def "test invalid findSex entry"() {

        when:
        testString.add(20, "error")
        testString.remove(21)
        student.studentData = testString
        Class aClass = student.getClass()
        Method method = aClass.getDeclaredMethod("findSex")
        method.setAccessible(true)
        method.invoke(student)

        then:
        student.getSex()  == "."
    }

    //Feel like this should convert to the letter instead of keeping it a number?!?!?!?!
    def "Test the findGrade method"() {

        when:
        student.studentData = testString
        Class aClass = student.getClass()
        Method method = aClass.getDeclaredMethod("findGrade")
        method.setAccessible(true)
        method.invoke(student)

        then:
        student.getGrade() == "A"

    }

    def "Test the incorrect additino of a grade"() {

        when:
        testString.add(21, "error")
        testString.remove(22)
        student.studentData = testString
        Class aClass = student.getClass()
        Method method = aClass.getDeclaredMethod("findGrade")
        method.setAccessible(true)
        method.invoke(student)

        then:
        student.getGrade() == "."
    }

    //This is right month is one digiit.VVVV

    //This method for some reason runs alpha converter on the birthday?
    //0 is invalid in this it shouldn't be.
    //Also only takes 5 characters, should be 6
    def "Test the findBirthday method"() {

        when:
        student.studentData = testString
        Class aClass = student.getClass()
        Method method = aClass.getDeclaredMethod("findBirthday")
        method.setAccessible(true)
        method.invoke(student)

        then:
        student.getBirthday() == "11792"

    }

    def "Test incorrect input for birthday"() {

        when:
        testString.add(24, "error")
        testString.remove(25)
        student.studentData = testString
        Class aClass = student.getClass()
        Method method = aClass.getDeclaredMethod("findBirthday")
        method.setAccessible(true)
        method.invoke(student)

        then:
        student.getBirthday() == "01.792"

    }

    //Fails as it takes the last number of the birthday as the student ID
    def "Test the findId method"() {

        when:
        student.studentData = testString
        Class aClass = student.getClass()
        Method method = aClass.getDeclaredMethod("findId")
        method.setAccessible(true)
        method.invoke(student)

        then:
        student.getId() == '803043974'
    }

    def "Test an incorrect input for findID"() {

        when:
        testString.add(28, "error")
        testString.remove(29)
        student.studentData = testString
        Class aClass = student.getClass()
        Method method = aClass.getDeclaredMethod("findId")
        method.setAccessible(true)
        method.invoke(student)

        then:
        student.getId() == "8.3043974"
    }


    //The code is made up of numbers so I would expect it to get letters,
    //it however gets numbers.
    def "Test the findCode method"() {

        when:
        student.studentData = testString
        Class aClass = student.getClass()
        Method method = aClass.getDeclaredMethod("findCode")
        method.setAccessible(true)
        method.invoke(student)

        then:
        student.getCode() == 'BCDEFA'
    }

    def "Test incorrect input of findCode"() {

        when:
        testString.add(38, "error")
        testString.remove(39)
        student.studentData = testString
        Class aClass = student.getClass()
        Method method = aClass.getDeclaredMethod("findCode")
        method.setAccessible(true)
        method.invoke(student)

        then:
        student.getCode() == "B.DEFA"
    }

    def "Test the inbounds of alpha converter"() {

        when:
        def lowerBound = student.alphaConverter("1")
        def upperBound = student.alphaConverter("26")

        then:
        lowerBound == "A"
        upperBound == "Z"
    }

    //First out boundary is -1 should be 0
    def "Test the outbounds of alpha convert"() {

        when:
        def lowerBound = student.alphaConverter("0")
        def upperBound = student.alphaConverter("27")

        then:
        lowerBound == ""
        upperBound == ""
    }

    def "Test within boundary of alpha converter"() {

        when:
        def result = student.alphaConverter("13")

        then:
        result == "M"
    }

    //returns all nulls
    def "Test the initialization from a List"() {

        when:
        student.initializeStudentFromStudentData(testString)

        then:
        student.getBirthday() == "011792"
        student.getCode() == "ABCDEF"
        student.getSex() == "M"
        student.getGrade() == "A"
        student.getId() == "803043974"
        student.getName() == "JONDN TRYNISKI M    "

    }

    //Never gets the letter grade?!
    //Percentage is calculated where?
    def "Test the json generator"() {

        when:
        student.initializeStudentFromStudentData(testString)
        def result = student.genJsonData()

        then:
        result.getClass() == JSONObject
        result == "[grade:A, name:JONDN TRYNISKI M    , percent:0.0]"
    }

}
