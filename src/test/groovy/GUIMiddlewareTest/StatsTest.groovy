package GUIMiddlewareTest

import GUIMiddleware.Stats
import spock.lang.Specification

/*
*
* Author: Sean McGrath
*
 */

class StatsTest extends Specification {

    def "meanDecimal - A list of BigDecimals is given to get the mean of"() {
        when: ""
        List<BigDecimal> scores = [4, 34, 5, 3] as BigDecimal[]
        BigDecimal result = Stats.meanDecimal(scores)
        then: "The result is the mean TC#1"
        result == 11.5
    }

    def "meanDecimal - A list of strings is entered TC#2"() {
        when: ""
        List<String> stringList = ["", "whoops", "string"] as String[]
        Stats.meanDecimal(stringList)
        then: "An exception is thrown"
        thrown(Exception)
    }

    def "meanDecimal - Something that isn't a list is given TC#3"() {
        when: ""
        int score = 6
        Stats.meanDecimal(score)
        then: "An exception is thrown"
        thrown(Exception)
    }


    def "MeanInteger - A list of Integers is given to get the mean of"() {
        when: ""
        List<Integer> scores = [4, 34, 5, 3] as Integer[]
        BigDecimal result = Stats.meanInteger(scores)
        then: "the result is the mean"
        result == 11.5
    }

    def "MeanInteger - A list of strings is entered TC#2"() {
        when: ""
        List<String> stringList = ["", "whoops", "string"] as String[]
        Stats.meanInteger(stringList)
        then: "An exception is thrown"
        thrown(Exception)
    }

    def "MeanInteger - Something that isn't a list is given TC#3"() {
        when: ""
        int score = 6
        Stats.meanInteger(score)
        then: "An exception is thrown"
        thrown(Exception)
    }

    def "OverallVariance - variance is calculated over a list of integers"() {
        when: ""
        List<Integer> scores = [4, 34, 5, 3] as Integer[]
        BigDecimal result = Stats.overallVariance(scores)
        then: "the result is the correct variance"
        result == 169.25
    }

    def "OverallVariance - variance is calculated over a single number"() {
        when: ""
        List<Integer> scores = [4] as Integer[]
        BigDecimal result = Stats.overallVariance(scores)
        then: "the result is the original integer"
        result == 0
    }

    def "OverallVariance - a list of strings is given"() {
        when: ""
        Stats.overallVariance(["", "whoops", "yikes"])
        then: "an exception is thrown"
        thrown(Exception)
    }

    def "OverallVariance - a non-list is given"() {
        when: ""
        Stats.overallVariance(6)
        then: "an exception is thrown"
        thrown(Exception)
    }

    def "OverallVariance - variance is calculated over a sample size of 0"() {
        when: ""
        List<Integer> scores = [] as Integer[]
        Stats.overallVariance(scores)
        then: "an exception is thrown since the method would be dividing by 0"
        thrown(Exception)
    }

    def "GradesByQuestion - Method is done normally"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        List<List<Integer>> result = Stats.gradesByQuestion(exams, answerKey, weights)
        List<List<Integer>> expectedResult = [[1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 1, 1, 0, 1, 1, 1, 1], [0, 1, 1, 0, 1, 1, 1, 1, 1], [1, 0, 1, 1, 1, 1, 0, 0, 1]]
        then: "the correct result is returned"
        result == expectedResult
    }

    def "GradesByQuestion - alternate success test, TC#10"() {
        when: ""
        List<List<String>> exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        List<List<Integer>> result = Stats.gradesByQuestion(exams, answerKey, weights)
        List<List<Integer>> expectedResult = [[0, 0, 0, 0, 0, 0, 0, 0, 1], [0, 0, 0, 0, 0, 0, 0, 0, 1], [0, 0, 0, 0, 0, 0, 0, 0, 1], [0, 0, 0, 0, 0, 1, 0, 0, 1]]
        then: "the correct result is returned"
        result == expectedResult
    }

    def "GradesByQuestion - alternate success test, TC#11"() {
        when: ""
        List<List<String>> exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["0", "0", "0", "0", "0", "0", "0", "0", "0"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        List<List<Integer>> result = Stats.gradesByQuestion(exams, answerKey, weights)
        List<List<Integer>> expectedResult = [[0, 0, 0, 0, 0, 0, 0, 0, 1], [1, 0, 0, 0, 1, 0, 0, 1, 1], [0, 0, 0, 0, 0, 0, 0, 0, 1], [0, 0, 0, 0, 0, 1, 0, 0, 1]]
        then: "the correct result is returned"
        result == expectedResult
    }

    def "GradesByQuestion - alternate success test, TC#12"() {
        when: ""
        List<List<String>> exams = [["2", "2", "2", "3", "4", "2", "1", "1", "4"], ["0", "1", "3", "2", "4", "0", "0", "2", "4"], ["0", "0", "3", "3", "4", "2", "3", "3", "1"], ["0", "1", "2", "3", "0", "2", "1", "0", "4"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        List<List<Integer>> result = Stats.gradesByQuestion(exams, answerKey, weights)
        List<List<Integer>> expectedResult = [[0, 0, 1, 1, 1, 1, 1, 0, 1], [1, 1, 0, 0, 1, 0, 0, 0, 1], [1, 0, 1, 1, 1, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1]]
        then: "the correct result is returned"
        result == expectedResult
    }

    def "GradesByQuestion - alternate success test, TC#13"() {
        when: ""
        List<List<String>> exams = [["0", "1", "3", "4", "5", "2", "error", "1", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "-1", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "3", "-1", "error", "error"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        List<List<Integer>> result = Stats.gradesByQuestion(exams, answerKey, weights)
        List<List<Integer>> expectedResult = [[1, 1, 1, 1, 1, 1, 0, 1, 1], [1, 0, 1, 1, 0, 1, 1, 1, 1], [0, 1, 1, 0, 0, 1, 1, 1, 1], [1, 0, 1, 1, 1, 1, 0, 0, 0]]
        then: "the correct result is returned"
        result == expectedResult
    }

    def "GradesByQuestion - The exams are of unequal length"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1", "0", "0", "0", "0"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.gradesByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "GradesByQuestion - some exam strings are not 0-4"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "8"], ["0", "0", "z", "3", "2", "2", "z", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "9", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.gradesByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "GradesByQuestion - some exam strings are longer than 1 char"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "32"], ["0", "01", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "11021", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "33"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.gradesByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "GradesByQuestion - exams are a list of ints"() {
        when: ""
        List<List<String>> exams = [6, 8] as Integer[]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.gradesByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "GradesByQuestion - exams and answer key are null"() {
        when: ""
        List<List<String>> exams = null
        List<String> answerKey = null
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.gradesByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "GradesByQuestion - answer key is not a list of strings"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = [4, 2, 7] as Integer[]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.gradesByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "GradesByQuestion - answer key has a string that is longer than 5 chars"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["012341"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.gradesByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "GradesByQuestion - answer key has one or more strings that are not between 0 and 4"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "v", "b", "2", "aeg", "l", "b", "a", "n"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.gradesByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "SquaredData - A list of BigDecimals is given"() {
        when: ""
        List<List<BigDecimal>> scores = new ArrayList<>()
        BigDecimal tempA = 4
        BigDecimal tempB = 34
        BigDecimal tempC = 5
        BigDecimal tempD = 3
        List<BigDecimal> list1 = [tempA, tempB, tempC, tempD]
        BigDecimal tempS = 17
        BigDecimal tempX = 99
        BigDecimal tempY = 191
        BigDecimal tempZ = 42
        List<BigDecimal> list2 = [tempS, tempX, tempY, tempZ]
        BigDecimal temp1 = 45
        BigDecimal temp2 = 19
        BigDecimal temp3 = 81
        BigDecimal temp4 = 111
        List<BigDecimal> list3 = [temp1, temp2, temp3, temp4]
        scores.add(list1)
        scores.add(list2)
        scores.add(list3)

        List<List<BigDecimal>> result = Stats.squaredData(scores)
        then: "the result is the list of squared numbers"
        result == [[tempA * tempA, tempB * tempB, tempC * tempC, tempD * tempD], [tempS * tempS, tempX * tempX, tempY * tempY, tempZ * tempZ], [temp1 * temp1, temp2 * temp2, temp3 * temp3, temp4 * temp4]]
    }

    def "SquaredData - A list of strings is entered"() {
        when: ""
        List<String> stringList = ["", "whoops", "string"] as String[]
        Stats.squaredData(stringList)
        then: "An exception is thrown"
        thrown(Exception)
    }

    def "SquaredData - Something that isn't a list is given"() {
        when: ""
        int score = 6
        Stats.squaredData(score)
        then: "An exception is thrown"
        thrown(Exception)
    }

    def "SquaredData - the lists in the List are not of equal length"() {
        when: ""
        List<List<BigDecimal>> scores = new ArrayList<>()
        BigDecimal tempA = 4
        BigDecimal tempB = 34
        BigDecimal tempC = 5
        BigDecimal tempD = 3
        BigDecimal tempS = 17
        BigDecimal tempY = 191
        BigDecimal tempZ = 42
        BigDecimal temp1 = 45
        BigDecimal temp4 = 111
        List<BigDecimal> list1 = [tempA, tempB, tempC, tempD]
        List<BigDecimal> list2 = [tempS, tempY, tempZ]
        List<BigDecimal> list3 = [temp1, temp4]
        scores.add(list1)
        scores.add(list2)
        scores.add(list3)
        Stats.squaredData(scores)
        then: "An Exception is thrown"
        thrown(Exception)
    }

    def "CronbachsAlpha - Cronbachs Alpha is done normally"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        BigDecimal result = Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0
    }

    def "CronbachsAlpha - alternate success test, TC#10"() {
        when: ""
        List<List<String>> exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        BigDecimal result = Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0
    }

    def "CronbachsAlpha - alternate success test, TC#11"() {
        when: ""
        List<List<String>> exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["0", "0", "0", "0", "0", "0", "0", "0", "0"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        BigDecimal result = Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0.5625
    }

    def "CronbachsAlpha - alternate success test, TC#12"() {
        when: ""
        List<List<String>> exams = [["2", "2", "2", "3", "4", "2", "1", "1", "4"], ["0", "1", "3", "2", "4", "0", "0", "2", "4"], ["0", "0", "3", "3", "4", "2", "3", "3", "1"], ["0", "1", "2", "3", "0", "2", "1", "0", "4"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        BigDecimal result = Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0.6428571412500000//originally not exact enough so updated result
    }

    def "CronbachsAlpha - The exams are of unequal length"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1", "0", "0", "0", "0"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "CronbachsAlpha - some exam strings are not 0-4"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "8"], ["0", "0", "z", "3", "2", "2", "z", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "9", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "CronbachsAlpha - some exam strings are longer than 1 char"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "32"], ["0", "01", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "11021", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "33"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "CronbachsAlpha - exams are a list of ints"() {
        when: ""
        List<List<String>> exams = [6, 8] as Integer[]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "CronbachsAlpha - exams and answer key are null"() {
        when: ""
        List<List<String>> exams = null
        List<String> answerKey = null
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "CronbachsAlpha - answer key is not a list of strings"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = [4, 2, 7] as Integer[]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "CronbachsAlpha - answer key has a string that is longer than 5 chars"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["012341"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "CronbachsAlpha - answer key has one or more strings that are not between 0 and 1"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["a", "v", "b", "f", "aeg", "l", "b", "a", "n"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "lowestScore - is calculated over a list of integers"() {
        when:
        List<Integer> scores = [56, 24, 65, 89] as Integer[]
        int result = Stats.lowestScore(scores)
        then: "the result is the correct variance"
        result == 24
    }

    def "LowestScore - calculated over a list of integers where the answer is expected to be a decimal"() {
        when: ""
        List<Integer> scores = [56, 24.5, 65, 89] as Integer[]
        int result = Stats.lowestScore(scores)
        then: "the result is the correct variance"
        result == 24.5
    }

    def "LowestScore - a list of strings is given"() {
        when: ""
        Stats.lowestScore(["", "whoops", "yikes"])
        then: "an exception is thrown"
        thrown(Exception)
    }

    def "LowestScore - a non-list is given"() {
        when: ""
        Stats.lowestScore(6)
        then: "an exception is thrown"
        thrown(Exception)
    }


    def "highestScore - is calculated over a list of integers"() {
        when: ""
        List<Integer> scores = [56, 24, 65, 89] as Integer[]
        BigDecimal result = Stats.highestScore(scores)
        then: "the result is the correct variance"
        result == 89
    }

    def "highestScore - is calculated over a list of integers where the answer is expected to be a decimal"() {
        when: ""
        List<Integer> scores = [56, 24, 65, 89.5] as Integer[]
        BigDecimal result = Stats.highestScore(scores)
        then: "the result is the correct variance"
        result == 89.5
    }

    def "HighestScore - a list of strings is given"() {
        when: ""
        Stats.highestScore(["", "whoops", "yikes"])
        then: "an exception is thrown"
        thrown(Exception)
    }

    def "HighestScore - a non-list is given"() {
        when: ""
        Stats.highestScore(6)
        then: "an exception is thrown"
        thrown(Exception)
    }

    def "Median - highestScore is calculated over one number"() {
        when: ""
        int result = Stats.median([5])
        then: "the result is that number"
        result == 5
    }

    def "Median - highestScore is calculated over an even list of integers"() {
        when: ""
        List<Integer> scores = [56, 24, 65, 89] as Integer[]
        int result = Stats.median(scores)
        then: "the result is the correct variance"
        result == 60.5
    }//fails due to return type being int and because the method not calculating real median, more in QMR
    def "Median - highestScore is calculated over an odd list of integers"() {
        when: ""
        List<Integer> scores = [56, 24, 65, 89, 59] as Integer[]
        int result = Stats.median(scores)
        then: "the result is the correct variance"
        result == 59
    }

    def "Median - a list of strings is given"() {
        when: ""
        Stats.median(["", "whoops", "yikes"])
        then: "an exception is thrown"
        thrown(Exception)
    }

    def "Median - a non-list is given"() {
        when: ""
        Stats.median(6)
        then: "an exception is thrown"
        thrown(Exception)
    }

    def "rangeOfScores - is calculated over a list of integers"() {
        when: ""
        List<Integer> scores = [56, 24, 65, 89] as Integer[]
        BigDecimal result = Stats.rangeOfScores(scores)
        then: "the result is the correct range"
        result == 65
    }

    def "RangeOfScores - is calculated over a list of integers where the answer is expected to be a decimal"() {
        when: ""
        List<Integer> scores = [56, 24, 65, 89.5] as Integer[]
        BigDecimal result = Stats.rangeOfScores(scores)
        then: "the result is the correct variance"
        result == 65.5
    }

    def "RangeOfScores - is calculated over an odd list of integers where the answer is expected to be a decimal"() {
        when: ""
        List<Integer> scores = [56, 24, 65, 89.5, 77] as Integer[]
        BigDecimal result = Stats.rangeOfScores(scores)
        then: "the result is the correct variance"
        result == 65.5
    }

    def "RangeOfScores - a list of strings is given"() {
        when: ""
        Stats.rangeOfScores(["", "whoops", "yikes"])
        then: "an exception is thrown"
        thrown(Exception)
    }

    def "RangeOfScores - a non-list is given"() {
        when: ""
        Stats.rangeOfScores(6)
        then: "an exception is thrown"
        thrown(Exception)
    }

    def "ProportionPassingByQuestion - the method is done normally"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        List<BigDecimal> result = Stats.proportionPassingByQuestion(exams, answerKey, weights)
        List<BigDecimal> expectedResult = [0.75000000, 0.50000000, 1.00000000, 0.75000000, 0.75000000, 1.00000000, 0.75000000, 0.75000000, 0.00000000]
        then: "the correct result is returned"
        result == expectedResult
    }

    def "ProportionPassingByQuestion - alternate success test, TC#10"() {
        when: ""
        List<List<String>> exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        List<BigDecimal> result = Stats.proportionPassingByQuestion(exams, answerKey, weights)
        List<BigDecimal> expectedResult = [0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.25000000, 0.00000000, 0.00000000, 0.00000000]
        then: "the correct result is returned"
        result == expectedResult
    }

    def "ProportionPassingByQuestion - alternate success test, TC#11"() {
        when: ""
        List<List<String>> exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["0", "0", "0", "0", "0", "0", "0", "0", "0"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        List<BigDecimal> result = Stats.proportionPassingByQuestion(exams, answerKey, weights)
        List<BigDecimal> expectedResult = [0.25000000, 0.00000000, 0.00000000, 0.00000000, 0.25000000, 0.25000000, 0.00000000, 0.25000000, 0.00000000]
        then: "the correct result is returned"
        result == expectedResult
    }

    def "ProportionPassingByQuestion - alternate success test, TC#12"() {
        when: ""
        List<List<String>> exams = [["2", "2", "2", "3", "4", "2", "1", "1", "4"], ["0", "1", "3", "2", "4", "0", "0", "2", "4"], ["0", "0", "3", "3", "4", "2", "3", "3", "1"], ["0", "1", "2", "3", "0", "2", "1", "0", "4"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        List<BigDecimal> result = Stats.proportionPassingByQuestion(exams, answerKey, weights)
        List<BigDecimal> expectedResult = [0.75000000, 0.50000000, 0.75, 0.75, 1.00000000, 0.50000000, 0.50000000, 0.25000000, 0.00000000]
        then: "the correct result is returned"
        result == expectedResult
    }

    def "ProportionPassingByQuestion - The exams are of unequal length"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1", "0", "0", "0", "0"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionPassingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "ProportionPassingByQuestion - some exam strings are not 0-4"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "8"], ["0", "0", "z", "3", "2", "2", "z", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "9", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionPassingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "ProportionPassingByQuestion - some exam strings are longer than 1 char"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "32"], ["0", "01", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "11021", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "33"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionPassingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "ProportionPassingByQuestion - exams are a list of ints"() {
        when: ""
        List<List<String>> exams = [6, 8] as Integer[]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionPassingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "ProportionPassingByQuestion - exams and answer key are null"() {
        when: ""
        List<List<String>> exams = null
        List<String> answerKey = null
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionPassingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "ProportionPassingByQuestion - answer key is not a list of strings"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = [4, 2, 7] as Integer[]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionPassingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "ProportionPassingByQuestion - answer key has a string that is longer than 5 chars"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["012341"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionPassingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "ProportionPassingByQuestion - answer key has one or more strings that are not between 0 and 4"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["a", "v", "b", "f", "aeg", "l", "b", "a", "n"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionPassingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "ProportionFailingByQuestion - the method is done normally"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        List<BigDecimal> result = Stats.proportionFailingByQuestion(exams, answerKey, weights)
        List<BigDecimal> expectedResult = [0.25000000, 0.50000000, 0.00000000, 0.25000000, 0.25000000, 0.00000000, 0.25000000, 0.25000000, 1.00000000]
        then: "the correct result is returned"
        result == expectedResult
    }

    def "ProportionFailingByQuestion - alternate success test, TC#10"() {
        when: ""
        List<List<String>> exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        List<BigDecimal> result = Stats.proportionFailingByQuestion(exams, answerKey, weights)
        List<BigDecimal> expectedResult = [1.00000000, 1.00000000, 1.00000000, 1.00000000, 1.00000000, 0.75000000, 1.00000000, 1.00000000, 1.00000000]
        then: "the correct result is returned"
        result == expectedResult
    }

    def "ProportionFailingByQuestion - alternate success test, TC#11"() {
        when: ""
        List<List<String>> exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["0", "0", "0", "0", "0", "0", "0", "0", "0"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        List<BigDecimal> result = Stats.proportionFailingByQuestion(exams, answerKey, weights)
        List<BigDecimal> expectedResult = [0.75000000, 1.00000000, 1.00000000, 1.00000000, 0.75000000, 0.75000000, 1.00000000, 0.75000000, 1.00000000]
        then: "the correct result is returned"
        result == expectedResult
    }

    def "ProportionFailingByQuestion - alternate success test, TC#12"() {
        when: ""
        List<List<String>> exams = [["2", "2", "2", "3", "4", "2", "1", "1", "4"], ["0", "1", "3", "2", "4", "0", "0", "2", "4"], ["0", "0", "3", "3", "4", "2", "3", "3", "1"], ["0", "1", "2", "3", "0", "2", "1", "0", "4"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        List<BigDecimal> result = Stats.proportionFailingByQuestion(exams, answerKey, weights)
        List<BigDecimal> expectedResult = [0.25000000, 0.50000000, 0.25000000, 0.25000000, 0.00000000, 0.50000000, 0.50000000, 0.75000000, 1.00000000]
        then: "the correct result is returned"
        result == expectedResult
    }

    def "ProportionFailingByQuestion - The exams are of unequal length"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1", "0", "0", "0", "0"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionFailingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "ProportionFailingByQuestion - some exam strings are not a-e"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "8"], ["0", "0", "z", "3", "2", "2", "z", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "9", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionFailingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "ProportionFailingByQuestion - some exam strings are longer than 1 char"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "32"], ["0", "01", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "11021", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "33"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionFailingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "ProportionFailingByQuestion - exams are a list of ints"() {
        when: ""
        List<List<String>> exams = [6, 8] as Integer[]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionFailingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "ProportionFailingByQuestion - exams and answer key are null"() {
        when: ""
        List<List<String>> exams = null
        List<String> answerKey = null
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionFailingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "ProportionFailingByQuestion - answer key is not a list of strings"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = [4, 2, 7] as Integer[]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionFailingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "ProportionFailingByQuestion - answer key has a string that is longer than 5 chars"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["012341"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionFailingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "ProportionFailingByQuestion - answer key has one or more strings that are not between 0 and 4"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["a", "v", "b", "f", "aeg", "l", "b", "a", "n"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionFailingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "KR21 - is done normally"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        BigDecimal result = Stats.kuderRichardson21(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0
    }

    def "KR21 - alternate success test, TC#10"() {
        when: ""
        List<List<String>> exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        BigDecimal result = Stats.kuderRichardson21(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0
    }

    def "KR21 -alternate success test, TC#11"() {
        when: ""
        List<List<String>> exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["0", "0", "0", "0", "0", "0", "0", "0", "0"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        BigDecimal result = Stats.kuderRichardson21(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0
    }

    def "KR21 - alternate success test, TC#12"() {
        when: ""
        List<List<String>> exams = [["2", "2", "2", "3", "4", "2", "1", "1", "4"], ["0", "1", "3", "2", "4", "0", "0", "2", "4"], ["0", "0", "3", "3", "4", "2", "3", "3", "1"], ["0", "1", "2", "3", "0", "2", "1", "0", "4"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        BigDecimal result = Stats.kuderRichardson20(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0.482142857
    }

    def "KR21 - The exams are of unequal length"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1", "0", "0", "0", "0"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson21(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "KR21 - some exam strings are not a-e"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "8"], ["0", "0", "z", "3", "2", "2", "z", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "9", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson21(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "KR21 - some exam strings are longer than 1 char"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "32"], ["0", "01", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "11021", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "33"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson21(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "KR21 - exams are a list of ints"() {
        when: ""
        List<List<String>> exams = [6, 8] as Integer[]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson21(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "KR21 - exams and answer key are null"() {
        when: ""
        List<List<String>> exams = null
        List<String> answerKey = null
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson21(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "KR21 - answer key is not a list of strings"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = [4, 2, 7] as Integer[]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson21(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "KR21 - answer key has a string that is longer than 5 chars"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson21(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "KR21 - answer key has one or more strings that are not between 0 and 4"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["a", "v", "b", "f", "aeg", "l", "b", "a", "n"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson21(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "KR20 - The method is used correctly"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        BigDecimal result = Stats.kuderRichardson20(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0
    }

    def "KR20 - alternate success test, TC#10"() {
        when: ""
        List<List<String>> exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        BigDecimal result = Stats.kuderRichardson20(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0
    }

    def "KR20 - alternate success test, TC#11"() {
        when: ""
        List<List<String>> exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["0", "0", "0", "0", "0", "0", "0", "0", "0"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        BigDecimal result = Stats.kuderRichardson20(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0.5625
    }

    def "KR20 - alternate success test, TC#12"() {
        when: ""
        List<List<String>> exams = [["2", "2", "2", "3", "4", "2", "1", "1", "4"], ["0", "1", "3", "2", "4", "0", "0", "2", "4"], ["0", "0", "3", "3", "4", "2", "3", "3", "1"], ["0", "1", "2", "3", "0", "2", "1", "0", "4"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        BigDecimal result = Stats.kuderRichardson20(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0.64285714
    }

    def "KR20 - The exams are of unequal length"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1", "0", "0", "0", "0"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson20(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "KR20 - some exam strings are not 0-4"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "8"], ["0", "0", "z", "3", "2", "2", "z", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "9", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson20(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "KR20 - some exam strings are longer than 1 char"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "32"], ["0", "01", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "11021", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "33"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson20(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "KR20 - exams are a list of ints"() {
        when: ""
        List<List<String>> exams = [6, 8] as Integer[]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson20(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "KR20 - exams and answer key are null"() {
        when: ""
        List<List<String>> exams = null
        List<String> answerKey = null
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson20(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "KR20 - answer key is not a list of strings"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = [4, 2, 7] as Integer[]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson20(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "KR20 - answer key has a string that is longer than 5 chars"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["012341"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson20(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "KR20 - answer key has one or more strings that are not between 0 and 4"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["a", "v", "b", "f", "aeg", "l", "b", "a", "n"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson20(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "QuestionFrequency - The method is used correctly"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<List<String>> result = Stats.questionFrequency(exams)
        List<List<String>> expectedResult = [["3", "1", "0", "0", "0"], ["2", "2", "0", "0", "0"], ["0", "0", "4", "0", "0"], ["0", "1", "0", "3", "0"], ["0", "0", "1", "0", "3"], ["0", "0", "4", "0", "0"], ["0", "3", "0", "1", "0"], ["3", "0", "1", "0", "0"], ["0", "1", "1", "2", "0"]]
        then: "the correct result is returned"
        result == expectedResult
    }

    def "QuestionFrequency - alternate success test, TC#10"() {
        when: ""
        List<List<String>> exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        List<List<String>> result = Stats.questionFrequency(exams)
        List<List<String>> expectedResult = [["0", "0", "4", "0", "0"], ["4", "0", "0", "0", "0"], ["1", "3", "0", "0", "0"], ["0", "4", "0", "0", "0"], ["0", "4", "0", "0", "0"], ["3", "0", "1", "0", "0"], ["0", "0", "0", "4", "0"], ["0", "0", "1", "3", "0"], ["0", "0", "0", "4", "0"]]
        then: "the correct result is returned"
        result == expectedResult
    }

    def "alternate success test, TC#11"() {
        when: ""
        List<List<String>> exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["0", "0", "0", "0", "0", "0", "0", "0", "0"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        List<List<String>> result = Stats.questionFrequency(exams)
        List<List<String>> expectedResult = [["1", "0", "3", "0", "0"], ["4", "0", "0", "0", "0"], ["2", "2", "0", "0", "0"], ["1", "3", "0", "0", "0"], ["1", "3", "0", "0", "0"], ["3", "0", "1", "0", "0"], ["1", "0", "0", "3", "0"], ["1", "0", "1", "2", "0"], ["1", "0", "0", "3", "0"]]
        then: "the correct result is returned"
        result == expectedResult
    }

    def "QuestionFrequency - alternate success test, TC#12"() {
        when: ""
        List<List<String>> exams = [["2", "2", "2", "3", "4", "2", "1", "1", "4"], ["0", "1", "3", "2", "4", "0", "0", "2", "4"], ["0", "0", "3", "3", "4", "2", "3", "3", "1"], ["0", "1", "2", "3", "0", "2", "1", "0", "4"]]
        List<List<String>> result = Stats.questionFrequency(exams)
        List<List<String>> expectedResult = [["3", "0", "1", "0", "0"], ["1", "2", "1", "0", "0"], ["0", "0", "2", "2", "0"], ["0", "0", "1", "3", "0"], ["1", "0", "0", "0", "3"], ["1", "0", "3", "0", "0"], ["1", "2", "0", "1", "0"], ["1", "1", "1", "1", "0"], ["0", "1", "0", "0", "3"]]
        then: "the correct result is returned"
        result == expectedResult
    }

    def "QuestionFrequency - The exams are of unequal length"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1", "0", "0", "0", "0"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2"]]
        Stats.questionFrequency(exams)
        then: "exception thrown"
        thrown(Exception)
    }

    def "QuestionFrequency - some exam strings are not 0-4"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "8"], ["0", "0", "z", "3", "2", "2", "z", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "9", "4", "2", "3", "2", "3"]]
        Stats.questionFrequency(exams)
        then: "exception thrown"
        thrown(Exception)
    }

    def "QuestionFrequency - some exam strings are longer than 1 char"() {
        when: ""
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "32"], ["0", "01", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "11021", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "33"]]
        Stats.questionFrequency(exams)
        then: "exception thrown"
        thrown(Exception)
    }

    def "QuestionFrequency - exams are a list of ints"() {
        when: ""
        List<List<String>> exams = [6, 8] as Integer[]
        Stats.questionFrequency(exams)
        then: "exception thrown"
        thrown(Exception)
    }

    def "QuestionFrequency - exams null"() {
        when: ""
        List<List<String>> exams = null
        Stats.questionFrequency(exams)
        then: "exception thrown"
        thrown(Exception)
    }

    def "squareRoot"() {
        when: "simple test for simple method"
        BigDecimal input = 9.00000
        BigDecimal result = Stats.squareRoot(input)
        then: "result is the square root"
        result == 3.0000
    }
}
