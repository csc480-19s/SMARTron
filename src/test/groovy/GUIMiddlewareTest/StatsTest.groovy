package GUIMiddlewareTest

import GUIMiddleware.Stats
import spock.lang.Specification

class StatsTest extends Specification {

    def "MeanDecimal"() {
        when: "A list of BigDecimals is given to get the mean of"
        List<BigDecimal> scores = [4,34,5,3] as BigDecimal[]
        BigDecimal result = Stats.meanDecimal(scores)
        then: "The result is the mean TC#1"
        result == 11.5

        when: "A list of strings is entered TC#2"
        List<String> stringList = ["", "whoops", "string"] as String[]
        Stats.meanDecimal(stringList)
        then: "An exception is thrown"
        thrown(Exception)

        when: "Something that isn't a list is given TC#3"
        int score = 6
        Stats.meanDecimal(score)
        then: "An exception is thrown"
        thrown(Exception)
    }

    def "MeanInteger"() {
        when: "A list of Integers is given to get the mean of"
        List<Integer> scores = [4,34,5,3] as Integer[]
        BigDecimal result = Stats.meanInteger(scores)
        then: "the result is the mean"
        result == 11.5

        when: "A list of strings is entered TC#2"
        List<String> stringList = ["", "whoops", "string"] as String[]
        Stats.meanInteger(stringList)
        then: "An exception is thrown"
        thrown(Exception)

        when: "Something that isn't a list is given TC#3"
        int score = 6
        Stats.meanInteger(score)
        then: "An exception is thrown"
        thrown(Exception)
    }

    def "OverallVariance"() {
        when: "variance is calculated over a list of integers"
        List<Integer> scores = [4,34,5,3] as Integer[]
        BigDecimal result = Stats.overallVariance(scores)
        then: "the result is the correct variance"
        result == 169.25

        when: "variance is calculated over a single number"
        scores = [4] as Integer[]
        result = Stats.overallVariance(scores)
        then: "the result is the original integer"
        result == 0

        when: "a list of strings is given"
        Stats.overallVariance(["","whoops","yikes"])
        then: "an exception is thrown"
        thrown(Exception)

        when: "a non-list is given"
        Stats.overallVariance(6)
        then: "an exception is thrown"
        thrown(Exception)

        when: "variance is calculated over a sample size of 0"
        scores = [] as Integer[]
        Stats.overallVariance(scores)
        then: "an exception is thrown since the method would be dividing by 0"
        thrown(Exception)
    }

    def "GradesByQuestion"() {
        when: "Method is done normally"
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        List<List<Integer>> result = Stats.gradesByQuestion(exams, answerKey, weights)
        List<List<Integer>> expectedResult = [[1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 0, 1, 1, 0, 1, 1, 1, 1], [0, 1, 1, 0, 1, 1, 1, 1, 1], [1, 0, 1, 1, 1, 1, 0, 0, 1]]
        then: "the correct result is returned"
        result == expectedResult

        when: "alternate success test, TC#10"
        exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.gradesByQuestion(exams, answerKey, weights)
        expectedResult = [[0, 0, 0, 0, 0, 0, 0, 0, 1], [0, 0, 0, 0, 0, 0, 0, 0, 1], [0, 0, 0, 0, 0, 0, 0, 0, 1], [0, 0, 0, 0, 0, 1, 0, 0, 1]]
        then: "the correct result is returned"
        result == expectedResult

        when: "alternate success test, TC#11"
        exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["0", "0", "0", "0", "0", "0", "0", "0", "0"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.gradesByQuestion(exams, answerKey, weights)
        expectedResult = [[0, 0, 0, 0, 0, 0, 0, 0, 1], [1, 0, 0, 0, 1, 0, 0, 1, 1], [0, 0, 0, 0, 0, 0, 0, 0, 1], [0, 0, 0, 0, 0, 1, 0, 0, 1]]
        then: "the correct result is returned"
        result == expectedResult

        when: "alternate success test, TC#12"
        exams = [["2", "2", "2", "3", "4", "2", "1", "1", "4"], ["0", "1", "3", "2", "4", "0", "0", "2", "4"], ["0", "0", "3", "3", "4", "2", "3", "3", "1"], ["0", "1", "2", "3", "0", "2", "1", "0", "4"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.gradesByQuestion(exams, answerKey, weights)
        expectedResult = [[0, 0, 1, 1, 1, 1, 1, 0, 1], [1, 1, 0, 0, 1, 0, 0, 0, 1], [1, 0, 1, 1, 1, 0, 0, 0, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1]]
        then: "the correct result is returned"
        result == expectedResult

        when: "alternate success test, TC#13"
        exams = [["0", "1", "3", "4", "5", "2", "error", "1", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "-1", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "3", "-1", "error", "error"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.gradesByQuestion(exams, answerKey, weights)
        expectedResult = [[1, 1, 1, 1, 1, 1, 0, 1, 1], [1, 0, 1, 1, 0, 1, 1, 1, 1], [0, 1, 1, 0, 0, 1, 1, 1, 1], [1, 0, 1, 1, 1, 1, 0, 0, 0]]
        then: "the correct result is returned"
        result == expectedResult

        when: "The exams are of unequal length"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1", "0", "0", "0", "0"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.gradesByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "some exam strings are not 0-4"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "8"], ["0", "0", "z", "3", "2", "2", "z", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "9", "4", "2", "3", "2", "3"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.gradesByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "some exam strings are longer than 1 char"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "32"], ["0", "01", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "11021", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "33"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.gradesByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "exams are a list of ints"
        exams = [6, 8] as Integer[]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.gradesByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "exams and answer key are null"
        exams = null
        answerKey = null
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.gradesByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "answer key is not a list of strings"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        answerKey = [4, 2, 7] as Integer[]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.gradesByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "answer key has a string that is longer than 5 chars"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        answerKey = ["012341"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.gradesByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "answer key has one or more strings that are not between 0 and 4"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        answerKey = ["0", "v", "b", "2", "aeg", "l", "b", "a", "n"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.gradesByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "SquaredData"() {
        when: "A list of BigDecimals is given"
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
        result == [[tempA*tempA, tempB*tempB, tempC*tempC, tempD*tempD], [tempS*tempS, tempX*tempX, tempY*tempY, tempZ*tempZ], [temp1*temp1, temp2*temp2, temp3*temp3, temp4*temp4]]

        when: "A list of strings is entered"
        List<String> stringList = ["", "whoops", "string"] as String[]
        Stats.squaredData(stringList)
        then: "An exception is thrown"
        thrown(Exception)

        when: "Something that isn't a list is given"
        int score = 6
        Stats.squaredData(score)
        then: "An exception is thrown"
        thrown(Exception)

        when: "the lists in the List are not of equal length"
        scores = new ArrayList<>()
        scores.add(list1)
        list2 = [tempS, tempY, tempZ]
        list3 = [temp1, temp4]
        scores.add(list2)
        scores.add(list3)
        Stats.squaredData(scores)
        then: "An Exception is thrown"
        thrown(Exception)
    }

    def "CronbachsAlpha"() {
        when: "Cronbachs Alpha is done normally"
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        BigDecimal result = Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0

        when: "alternate success test, TC#10"
        exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0

        when: "alternate success test, TC#11"
        exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["0", "0", "0", "0", "0", "0", "0", "0", "0"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0.5625

        when: "alternate success test, TC#12"
        exams = [["2", "2", "2", "3", "4", "2", "1", "1", "4"], ["0", "1", "3", "2", "4", "0", "0", "2", "4"], ["0", "0", "3", "3", "4", "2", "3", "3", "1"], ["0", "1", "2", "3", "0", "2", "1", "0", "4"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0.642857

        when: "The exams are of unequal length"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1", "0", "0", "0", "0"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "some exam strings are not 0-4"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "8"], ["0", "0", "z", "3", "2", "2", "z", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "9", "4", "2", "3", "2", "3"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "some exam strings are longer than 1 char"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "32"], ["0", "01", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "11021", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "33"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "exams are a list of ints"
        exams = [6, 8] as Integer[]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "exams and answer key are null"
        exams = null
        answerKey = null
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "answer key is not a list of strings"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        answerKey = [4, 2, 7] as Integer[]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "answer key has a string that is longer than 5 chars"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        answerKey = ["012341"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "answer key has one or more strings that are not between 0 and 1"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        answerKey = ["a", "v", "b", "f", "aeg", "l", "b", "a", "n"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.cronbachsAlpha(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "LowestScore"() {
        when: "lowestScore is calculated over a list of integers"
        List<Integer> scores = [56, 24, 65, 89] as Integer[]
        int result = Stats.lowestScore(scores)
        then: "the result is the correct variance"
        result == 24

        when: "highestScore is calculated over a list of integers where the answer is expected to be a decimal"
        scores = [56, 24.5, 65, 89] as Integer[]
        result = Stats.lowestScore(scores)
        then: "the result is the correct variance"
        result == 24.5

        when: "a list of strings is given"
        Stats.lowestScore(["","whoops","yikes"])
        then: "an exception is thrown"
        thrown(Exception)

        when: "a non-list is given"
        Stats.lowestScore(6)
        then: "an exception is thrown"
        thrown(Exception)
    }

    def "HighestScore"() {
        when: "highestScore is calculated over a list of integers"
        List<Integer> scores = [56, 24, 65, 89] as Integer[]
        BigDecimal result = Stats.highestScore(scores)
        then: "the result is the correct variance"
        result == 89

        when: "highestScore is calculated over a list of integers where the answer is expected to be a decimal"
        scores = [56, 24, 65, 89.5] as Integer[]
        result = Stats.highestScore(scores)
        then: "the result is the correct variance"
        result == 89.5

        when: "a list of strings is given"
        Stats.highestScore(["","whoops","yikes"])
        then: "an exception is thrown"
        thrown(Exception)

        when: "a non-list is given"
        Stats.highestScore(6)
        then: "an exception is thrown"
        thrown(Exception)
    }

    def "Median"() {
        when: "highestScore is calculated over one number"
        int result = Stats.median([5])
        then: "the result is that number"
        result == 5

        when: "highestScore is calculated over an even list of integers"
        List<Integer> scores = [56, 24, 65, 89] as Integer[]
        result = Stats.median(scores)
        then: "the result is the correct variance"
        result == 60.5

        when: "highestScore is calculated over an odd list of integers"
        scores = [56, 24, 65, 89, 59] as Integer[]
        result = Stats.median(scores)
        then: "the result is the correct variance"
        result == 59

        when: "a list of strings is given"
        Stats.median(["","whoops","yikes"])
        then: "an exception is thrown"
        thrown(Exception)

        when: "a non-list is given"
        Stats.median(6)
        then: "an exception is thrown"
        thrown(Exception)
    }

    def "RangeOfScores"() {
        when: "rangeOfScores is calculated over a list of integers"
        List<Integer> scores = [56, 24, 65, 89] as Integer[]
        BigDecimal result = Stats.rangeOfScores(scores)
        then: "the result is the correct range"
        result == 65

        when: "highestScore is calculated over a list of integers where the answer is expected to be a decimal"
        scores = [56, 24, 65, 89.5] as Integer[]
        result = Stats.rangeOfScores(scores)
        then: "the result is the correct variance"
        result == 65.5

        when: "highestScore is calculated over an odd list of integers where the answer is expected to be a decimal"
        scores = [56, 24, 65, 89.5, 77] as Integer[]
        result = Stats.rangeOfScores(scores)
        then: "the result is the correct variance"
        result == 65.5

        when: "a list of strings is given"
        Stats.rangeOfScores(["","whoops","yikes"])
        then: "an exception is thrown"
        thrown(Exception)

        when: "a non-list is given"
        Stats.rangeOfScores(6)
        then: "an exception is thrown"
        thrown(Exception)
    }

    def "ProportionPassingByQuestion"() {
        when: "the method is done normally"
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        List<BigDecimal> result = Stats.proportionPassingByQuestion(exams, answerKey, weights)
        List<BigDecimal> expectedResult = [0.75, 0.5, 1, 0.75, 0.75, 1, 0.75, 0.75, 0]
        then: "the correct result is returned"
        result == expectedResult

        when: "alternate success test, TC#10"
        exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.proportionPassingByQuestion(exams, answerKey, weights)
        expectedResult = [0, 0, 0, 0, 0, 0.25, 0, 0, 0]
        then: "the correct result is returned"
        result == expectedResult

        when: "alternate success test, TC#11"
        exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["0", "0", "0", "0", "0", "0", "0", "0", "0"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.proportionPassingByQuestion(exams, answerKey, weights)
        expectedResult = [0.25, 0, 0, 0, 0.25, 0.25, 0, 0.25, 0]
        then: "the correct result is returned"
        result == expectedResult

        when: "alternate success test, TC#12"
        exams = [["2", "2", "2", "3", "4", "2", "1", "1", "4"], ["0", "1", "3", "2", "4", "0", "0", "2", "4"], ["0", "0", "3", "3", "4", "2", "3", "3", "1"], ["0", "1", "2", "3", "0", "2", "1", "0", "4"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.proportionPassingByQuestion(exams, answerKey, weights)
        expectedResult = [0.75, 0.5, 0.75, 0.75, 1, 0.5, 0.5, 0.25, 0]
        then: "the correct result is returned"
        result == expectedResult

        when: "The exams are of unequal length"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1", "0", "0", "0", "0"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionPassingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "some exam strings are not 0-4"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "8"], ["0", "0", "z", "3", "2", "2", "z", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "9", "4", "2", "3", "2", "3"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionPassingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "some exam strings are longer than 1 char"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "32"], ["0", "01", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "11021", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "33"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionPassingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "exams are a list of ints"
        exams = [6, 8] as Integer[]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionPassingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "exams and answer key are null"
        exams = null
        answerKey = null
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionPassingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "answer key is not a list of strings"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        answerKey = [4, 2, 7] as Integer[]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionPassingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "answer key has a string that is longer than 5 chars"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        answerKey = ["012341"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionPassingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "answer key has one or more strings that are not between 0 and 4"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        answerKey = ["a", "v", "b", "f", "aeg", "l", "b", "a", "n"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionPassingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "ProportionFailingByQuestion"() {
        when: "the method is done normally"
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        List<BigDecimal> result = Stats.proportionFailingByQuestion(exams, answerKey, weights)
        List<BigDecimal> expectedResult = [0.25, 0.5, 0, 0.25, 0.25, 0, 0.25, 0.25, 1]
        then: "the correct result is returned"
        result == expectedResult

        when: "alternate success test, TC#10"
        exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.proportionFailingByQuestion(exams, answerKey, weights)
        expectedResult = [1, 1, 1, 1, 1, 0.75, 1, 1, 1]
        then: "the correct result is returned"
        result == expectedResult

        when: "alternate success test, TC#11"
        exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["0", "0", "0", "0", "0", "0", "0", "0", "0"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.proportionFailingByQuestion(exams, answerKey, weights)
        expectedResult = [0.75, 1, 1, 1, 0.75, 0.75, 1, 0.75, 1]
        then: "the correct result is returned"
        result == expectedResult

        when: "alternate success test, TC#12"
        exams = [["2", "2", "2", "3", "4", "2", "1", "1", "4"], ["0", "1", "3", "2", "4", "0", "0", "2", "4"], ["0", "0", "3", "3", "4", "2", "3", "3", "1"], ["0", "1", "2", "3", "0", "2", "1", "0", "4"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.proportionFailingByQuestion(exams, answerKey, weights)
        expectedResult = [0.25, 0.5, 0.25, 0.25, 0, 0.5, 0.5, 0.75, 1]
        then: "the correct result is returned"
        result == expectedResult

        when: "The exams are of unequal length"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1", "0", "0", "0", "0"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionFailingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "some exam strings are not a-e"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "8"], ["0", "0", "z", "3", "2", "2", "z", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "9", "4", "2", "3", "2", "3"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionFailingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "some exam strings are longer than 1 char"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "32"], ["0", "01", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "11021", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "33"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionFailingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "exams are a list of ints"
        exams = [6, 8] as Integer[]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionFailingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "exams and answer key are null"
        exams = null
        answerKey = null
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionFailingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "answer key is not a list of strings"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        answerKey = [4, 2, 7] as Integer[]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionFailingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "answer key has a string that is longer than 5 chars"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        answerKey = ["012341"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionFailingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "answer key has one or more strings that are not between 0 and 4"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        answerKey = ["a", "v", "b", "f", "aeg", "l", "b", "a", "n"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.proportionFailingByQuestion(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "KuderRichardson21"() {

        when: "kr21 is done normally"
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        BigDecimal result = Stats.kuderRichardson21(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0

        when: "alternate success test, TC#10"
        exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.kuderRichardson21(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0

        when: "alternate success test, TC#11"
        exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["0", "0", "0", "0", "0", "0", "0", "0", "0"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.kuderRichardson21(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0

        when: "alternate success test, TC#12"
        exams = [["2", "2", "2", "3", "4", "2", "1", "1", "4"], ["0", "1", "3", "2", "4", "0", "0", "2", "4"], ["0", "0", "3", "3", "4", "2", "3", "3", "1"], ["0", "1", "2", "3", "0", "2", "1", "0", "4"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.kuderRichardson20(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0.482142857

        when: "The exams are of unequal length"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1", "0", "0", "0", "0"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson21(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "some exam strings are not a-e"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "8"], ["0", "0", "z", "3", "2", "2", "z", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "9", "4", "2", "3", "2", "3"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson21(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "some exam strings are longer than 1 char"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "32"], ["0", "01", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "11021", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "33"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson21(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "exams are a list of ints"
        exams = [6, 8] as Integer[]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson21(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "exams and answer key are null"
        exams = null
        answerKey = null
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson21(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "answer key is not a list of strings"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        answerKey = [4, 2, 7] as Integer[]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson21(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "answer key has a string that is longer than 5 chars"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson21(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "answer key has one or more strings that are not between 0 and 4"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        answerKey = ["a", "v", "b", "f", "aeg", "l", "b", "a", "n"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson21(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "KuderRichardson20"() {

        when: "The method is used correctly"
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<String> answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        List<Integer> weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        BigDecimal result = Stats.kuderRichardson20(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0

        when: "alternate success test, TC#10"
        exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.kuderRichardson20(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0

        when: "alternate success test, TC#11"
        exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["0", "0", "0", "0", "0", "0", "0", "0", "0"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.kuderRichardson20(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0.5625

        when: "alternate success test, TC#12"
        exams = [["2", "2", "2", "3", "4", "2", "1", "1", "4"], ["0", "1", "3", "2", "4", "0", "0", "2", "4"], ["0", "0", "3", "3", "4", "2", "3", "3", "1"], ["0", "1", "2", "3", "0", "2", "1", "0", "4"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        result = Stats.kuderRichardson20(exams, answerKey, weights)
        then: "the correct result is returned"
        result == 0.642857

        when: "The exams are of unequal length"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1", "0", "0", "0", "0"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson20(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "some exam strings are not 0-4"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "8"], ["0", "0", "z", "3", "2", "2", "z", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "9", "4", "2", "3", "2", "3"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson20(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "some exam strings are longer than 1 char"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "32"], ["0", "01", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "11021", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "33"]]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson20(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "exams are a list of ints"
        exams = [6, 8] as Integer[]
        answerKey = ["0", "1", "2", "3", "04", "2", "1", "0", "01234"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson20(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "exams and answer key are null"
        exams = null
        answerKey = null
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson20(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "answer key is not a list of strings"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        answerKey = [4, 2, 7] as Integer[]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson20(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "answer key has a string that is longer than 5 chars"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        answerKey = ["012341"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson20(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)

        when: "answer key has one or more strings that are not between 0 and 4"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        answerKey = ["a", "v", "b", "f", "aeg", "l", "b", "a", "n"]
        weights = [1, 1, 1, 1, 1, 1, 1, 1, 1]
        Stats.kuderRichardson20(exams, answerKey, weights)
        then: "exception thrown"
        thrown(Exception)
    }

    def "QuestionFrequency"() {
        when: "The method is used correctly"
        List<List<String>> exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "3"]]
        List<List<String>> result = Stats.questionFrequency(exams)
        List<List<String>> expectedResult = [["3", "1", "0", "0", "0"], ["2", "2", "0", "0", "0"], ["0", "0", "4", "0", "0"], ["0", "1", "0", "3", "0"], ["0", "0", "1", "0", "3"], ["0", "0", "4", "0", "0"], ["0", "3", "0", "1", "0"], ["3", "0", "1", "0", "0"], ["0", "1", "1", "2", "0"]]
        then: "the correct result is returned"
        result == expectedResult

        when: "alternate success test, TC#10"
        exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        result = Stats.questionFrequency(exams)
        expectedResult = [["0", "0", "4", "0", "0"], ["4", "0", "0", "0", "0"], ["1", "3", "0", "0", "0"], ["0", "4", "0", "0", "0"], ["0", "4", "0", "0", "0"], ["3", "0", "1", "0", "0"], ["0", "0", "0", "4", "0"], ["0", "0", "1", "3", "0"], ["0", "0", "0", "4", "0"]]
        then: "the correct result is returned"
        result == expectedResult

        when: "alternate success test, TC#11"
        exams = [["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["0", "0", "0", "0", "0", "0", "0", "0", "0"], ["2", "0", "1", "1", "1", "0", "3", "3", "3"], ["2", "0", "0", "1", "1", "2", "3", "2", "3"]]
        result = Stats.questionFrequency(exams)
        expectedResult = [["1", "0", "3", "0", "0"], ["4", "0", "0", "0", "0"], ["2", "2", "0", "0", "0"], ["1", "3", "0", "0", "0"], ["1", "3", "0", "0", "0"], ["3", "0", "1", "0", "0"], ["1", "0", "0", "3", "0"], ["1", "0", "1", "2", "0"], ["1", "0", "0", "3", "0"]]
        then: "the correct result is returned"
        result == expectedResult

        when: "alternate success test, TC#12"
        exams = [["2", "2", "2", "3", "4", "2", "1", "1", "4"], ["0", "1", "3", "2", "4", "0", "0", "2", "4"], ["0", "0", "3", "3", "4", "2", "3", "3", "1"], ["0", "1", "2", "3", "0", "2", "1", "0", "4"]]
        result = Stats.questionFrequency(exams)
        expectedResult = [["3", "0", "1", "0", "0"], ["1", "2", "1", "0", "0"], ["0", "0", "2", "2", "0"], ["0", "0", "1", "3", "0"], ["1", "0", "0", "0", "3"], ["1", "0", "3", "0", "0"], ["1", "2", "0", "1", "0"], ["1", "1", "1", "1", "0"], ["0", "1", "0", "0", "3"]]
        then: "the correct result is returned"
        result == expectedResult

        when: "The exams are of unequal length"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "3"], ["0", "0", "2", "3", "2", "2", "1", "0", "1", "0", "0", "0", "0"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2"]]
        Stats.questionFrequency(exams)
        then: "exception thrown"
        thrown(Exception)

        when: "some exam strings are not 0-4"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "8"], ["0", "0", "z", "3", "2", "2", "z", "0", "1"], ["1", "1", "2", "1", "4", "2", "1", "0", "2"], ["0", "0", "2", "9", "4", "2", "3", "2", "3"]]
        Stats.questionFrequency(exams)
        then: "exception thrown"
        thrown(Exception)

        when: "some exam strings are longer than 1 char"
        exams = [["0", "1", "2", "3", "4", "2", "1", "0", "32"], ["0", "01", "2", "3", "2", "2", "1", "0", "1"], ["1", "1", "2", "1", "4", "2", "11021", "0", "2"], ["0", "0", "2", "3", "4", "2", "3", "2", "33"]]
        Stats.questionFrequency(exams)
        then: "exception thrown"
        thrown(Exception)

        when: "exams are a list of ints"
        exams = [6, 8] as Integer[]
        Stats.questionFrequency(exams)
        then: "exception thrown"
        thrown(Exception)

        when: "exams null"
        exams = null
        Stats.questionFrequency(exams)
        then: "exception thrown"
        thrown(Exception)
    }

    def "squareRoot"() {
        when: ""
        BigDecimal input = 9.00000
        BigDecimal result = Stats.squareRoot(input)
        then: "result is the square root"
        result == 3.0000
    }
}
