package GUIMiddlewareTest

import GUIMiddleware.*
import spock.lang.Specification


class GraderTest extends Specification {

    def g = new Grader()
    def sc = new StudentCreator()

    def "test normal run of grader"() {
        when:
        def testAnswers = [['3', '1', '2'], ['3', '2', '4']]
        def testStudent = ['10', '15', '14', '4', '14', '0', '20', '18', '25', '14', '9', '19',
                           '11', '9', '0', '13', '0', '0', '0', '0', '13', '1', '1', '1', '7',
                           '9', '2', '8', '0', '3', '0', '4', '3', '9', '7', '4', '1', '2', '3', '4', '5', '6', '1']
        def test = sc.makeStudent(testAnswers as String[][], testStudent as String[])
        def testList = [test]
        def keyData = new String[46]
        for (def i = 0; i < 46; i++) {
            keyData[i] = "1"
        }
        def key = sc.makeStudent([['3', '1', '2'], ['3', '2', '4']] as String[][], keyData as String[])
        def gradedList = g.getGrades(testList, key)

        then:
        gradedList.get(0) == 100
    }

    def "test run of grader with a null value in student"() {
        when:
        def testAnswers = [['3', '1', '2'], ['3', '2', null]]
        def testStudent = ['10', '15', '14', '4', '14', '0', '20', '18', '25', '14', '9', '19',
                           '11', '9', '0', '13', '0', '0', '0', '0', '13', '1', '1', '1', '7',
                           '9', '2', '8', '0', '3', '0', '4', '3', '9', '7', '4', '1', '2', '3', '4', '5', '6', '1']
        def test = sc.makeStudent(testAnswers as String[][], testStudent as String[])
        def testList = [test]
        def keyData = new String[46]
        for (def i = 0; i < 46; i++) {
            keyData[i] = "1"
        }
        def key = sc.makeStudent([['3', '1', '2'], ['3', '2', '4']] as String[][], keyData as String[])
        g.getGrades(testList, key)

        then:
        thrown(Exception)

    }

    def "test run of grader with a null in key"() {
        when:
        def testAnswers = [['3', '1', '2'], ['3', '2', '4']]
        def testStudent = ['10', '15', '14', '4', '14', '0', '20', '18', '25', '14', '9', '19',
                           '11', '9', '0', '13', '0', '0', '0', '0', '13', '1', '1', '1', '7',
                           '9', '2', '8', '0', '3', '0', '4', '3', '9', '7', '4', '1', '2', '3', '4', '5', '6', '1']
        def test = sc.makeStudent(testAnswers as String[][], testStudent as String[])
        def testList = [test]
        def keyData = new String[46]
        for (def i = 0; i < 46; i++) {
            keyData[i] = "1"
        }
        def key = sc.makeStudent([['3', '1', '2'], ['3', '2', null]] as String[][], keyData as String[])
        g.getGrades(testList, key)

        then:
        thrown(Exception)
    }

    def "test run of grader with a null in key and student"() {
        when:
        def testAnswers = [['3', '1', '2'], ['3', '2', null]]
        def testStudent = ['10', '15', '14', '4', '14', '0', '20', '18', '25', '14', '9', '19',
                           '11', '9', '0', '13', '0', '0', '0', '0', '13', '1', '1', '1', '7',
                           '9', '2', '8', '0', '3', '0', '4', '3', '9', '7', '4', '1', '2', '3', '4', '5', '6', '1']
        def test = sc.makeStudent(testAnswers as String[][], testStudent as String[])
        def testList = [test]
        def keyData = new String[46]
        for (def i = 0; i < 46; i++) {
            keyData[i] = "1"
        }
        def key = sc.makeStudent([['3', '1', '2'], ['3', '2', null]] as String[][], keyData as String[])
        g.getGrades(testList, key)

        then:
        thrown(Exception)
    }

    def "test a run of grader with a non 100 or 0 score"() {
        when:
        def testAnswers = [['3', '1', '2', '2', '4', '0'], ['3', '2', '4', '2', '1', '3']]
        def testStudent = ['10', '15', '14', '4', '14', '0', '20', '18', '25', '14', '9', '19',
                           '11', '9', '0', '13', '0', '0', '0', '0', '13', '1', '1', '1', '7',
                           '9', '2', '8', '0', '3', '0', '4', '3', '9', '7', '4', '1', '2', '3', '4', '5', '6', '1']
        def test = sc.makeStudent(testAnswers as String[][], testStudent as String[])
        def testList = [test]
        def keyData = new String[46]
        for (def i = 0; i < 46; i++) {
            keyData[i] = "1"
        }
        def key = sc.makeStudent([['1', '1', '3', '2', '4', '1'], ['3', '2', '2', '2', '1', '3']] as String[][], keyData as String[])
        def gradedList = g.getGrades(testList, key)

        then:
        gradedList.get(0) == 66.66667 as float
    }

    def "test a run of grader with a 100, a 0, and a calculated grade"() {
        when:
        def testAnswers = [['3', '1', '2', '2', '4', '0'], ['3', '2', '4', '2', '1', '3']]
        def testStudent = ['10', '15', '14', '4', '14', '0', '20', '18', '25', '14', '9', '19',
                           '11', '9', '0', '13', '0', '0', '0', '0', '13', '1', '1', '1', '7',
                           '9', '2', '8', '0', '3', '0', '4', '3', '9', '7', '4', '1', '2', '3', '4', '5', '6', '1']
        def test = sc.makeStudent(testAnswers as String[][], testStudent as String[])
        def testAnswers100 = [['1', '1', '3', '2', '4', '1'], ['3', '2', '2', '2', '1', '3']]
        def testAnswers0 = [['0', '0', '0', '0', '0', '0', ], ['0', '0', '0', '0', '0', '0', ]]
        def test100 = sc.makeStudent(testAnswers100 as String[][], testStudent as String[])
        def test0 = sc.makeStudent(testAnswers0 as String[][], testStudent as String[])
        def testList = [test, test100, test0]
        def keyData = new String[46]
        for (def i = 0; i < 46; i++) {
            keyData[i] = "1"
        }
        def key = sc.makeStudent([['1', '1', '3', '2', '4', '1'], ['3', '2', '2', '2', '1', '3']] as String[][], keyData as String[])
        def gradedList = g.getGrades(testList, key)

        then:
        gradedList.get(0) == 66.66667 as float
        gradedList.get(1) == 100
        gradedList.get(2) == 0
    }

    def "test of a student choosing multiple answers"() {
        when:
        def testAnswers = [['3', '1 1', '2'], ['3', '2', '4']]
        def testStudent = ['10', '15', '14', '4', '14', '0', '20', '18', '25', '14', '9', '19',
                           '11', '9', '0', '13', '0', '0', '0', '0', '13', '1', '1', '1', '7',
                           '9', '2', '8', '0', '3', '0', '4', '3', '9', '7', '4', '1', '2', '3', '4', '5', '6', '1']
        def test = sc.makeStudent(testAnswers as String[][], testStudent as String[])
        def testList = [test]
        def keyData = new String[46]
        for (def i = 0; i < 46; i++) {
            keyData[i] = "1"
        }
        def key = sc.makeStudent([['3', '1', '2'], ['3', '2', '4']] as String[][], keyData as String[])
        def gradedList = g.getGrades(testList, key)

        then:
        gradedList.get(0) == 83.33333 as float
    }
}