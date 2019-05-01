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
    }

    def "MeanByQuestion"() {
    }

    def "DifferenceFromMean"() {
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


    def "AdditionOfData"() {
    }

    def "DivisionOfData"() {
    }

    def "SummationOfList"() {
    }

    def "CronbachsAlpha"() {
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
    }

    def "ProportionFailingByQuestion"() {
    }

    def "KuderRichardson21"() {
    }

    def "KuderRichardson20"() {
    }

    def "QuestionFrequency"() {
    }

    def "Percentiles"() {
    }

    def "SquareRoot"() {
    }

    def "Quartiles"() {
    }
}
