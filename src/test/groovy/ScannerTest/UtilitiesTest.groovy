package ScannerTest

import spock.lang.Specification
import Scanner.Utilities

class UtilitiesTest extends Specification {

    Utilities u = new Utilities()

    def "test of multi"() {
        when:
        def testString = ["0-0, 0", "0-1, 4", "0-2, 2", "1-0, 1", "1-1, 2", "1-2, 3"]
        def retArr = u.multi(testString)

        then:
        retArr == [["0", "4", "2"],["1", "2", "3"]]

    }

    def "test of uneven rows, left longer than right"() {
        when:
        def testString = ["0-0, 1", "0-1, 3", "0-2, 4", "1-0, 3", "1-1, 3"]
        def retArr = u.multi(testString)

        then:
        retArr == [["1", "3", "4"], ["3", "3", "error"]]
    }

    def "test of uneven rows, right longer than left"() {
        when:
        def testString = ["0-0, 1", "0-1, 4", "0-2, 3", "1-0, 2", "1-1, 3"]
        def retArr = u.multi(testString)

        then:
        retArr == [["1", "4", "3"], ["2", "3", "error"]]
    }

    def "test of addition of null"() {
        when:
        def testNull = [null]
        u.multi(testNull)

        then:
        thrown(NullPointerException)
    }

    def "test random null in an otherwsie normal string"() {
        when:
        def testString = ["0-0, 0", "0-1, 4", "0-2, ", null, "1-0, 1", "1-1, 2", "1-2, 3"]
        u.multi(testString)

        then:
        thrown(Exception)
    }

    def "test of one more row than a scantron actually has"() {
        when:
        def testString = ["0-0, 1", "1-0, 1", "2-0, 1", "3-0 1", "4-0, 1", "5-0, 1", "6-0, 1", "7-0 1", "8-0 1", "9-0 1",
                          "10-0, 1", "11-0, 1", "12-0, 1", "13-0 1", "14-0, 1", "15-0, 1", "16-0, 1", "17-0 1", "18-0 1", "19-0 1"]
        def retArr = u.multi(testString)

        then:
        retArr == [["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"],
                   ["1"], ["1"], ["1"], ["1"], ["1"], ["1"]]
    }

    def "test of exact rows that a scantron actually has"() {
        when:
        def testString = ["0-0, 1", "1-0, 1", "2-0, 1", "3-0 1", "4-0, 1", "5-0, 1", "6-0, 1", "7-0 1", "8-0 1", "9-0 1",
                          "10-0, 1", "11-0, 1", "12-0, 1", "13-0 1", "14-0, 1", "15-0, 1", "16-0, 1", "17-0 1", "18-0 1"]
        def retArr = u.multi(testString)

        then:
        retArr == [["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"],
                   ["1"], ["1"], ["1"], ["1"], ["1"]]
    }

    def "test of one less row than a scantron actually has"() {
        when:
        def testString = ["0-0, 1", "1-0, 1", "2-0, 1", "3-0 1", "4-0, 1", "5-0, 1", "6-0, 1", "7-0 1", "8-0 1", "9-0 1",
                          "10-0, 1", "11-0, 1", "12-0, 1", "13-0 1", "14-0, 1", "15-0, 1", "16-0, 1", "17-0 1"]
        def retArr = u.multi(testString)

        then:
        retArr == [["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"], ["1"],
                   ["1"], ["1"], ["1"], ["1"]]
    }


    def "test more one more column than a scantron actually has"() {
        when:
        def testString = ["0-0 1", "0-1 1", "0-2 1", "0-3 1", "0-4 1", "0-5 1"]
        def retArr = u.multi(testString)

        then:
        retArr == [["1", "1", "1", "1", "1", "1"]]
    }

    def "test exact columns that a scantron actually has"() {
        when:
        def testString = ["0-0 1", "0-1 1", "0-2 1", "0-3 1", "0-4 1"]
        def retArr = u.multi(testString)

        then:
        retArr == [["1", "1", "1", "1", "1"]]
    }

    def "test one less column than a scantron actually has"() {
        when:
        def testString = ["0-0 1", "0-1 1", "0-2 1", "0-3 1"]
        def retArr = u.multi(testString)

        then:
        retArr == [["1", "1", "1", "1"]]
    }

    def "test the addition of a the string null"() {
        when:
        def testString = ["0-0, 0", "0-1, null", "0-2, 2", "1-0, 1", "1-1, 2", "1-2, 3"]
        def retArr = u.multi(testString)

        then:
        retArr == [["0", "error", "2"], ["1", "2", "3"]]
    }

    def "test the addition of just int's"() {
        when:
        def failedInput = [1,2,3]
        u.multi(failedInput)

        then:
        thrown(Exception)
    }

    def "test addition on non string list"() {
        when:
        def failedInput = [1-1, 2-1]
        u.multi(failedInput)

        then:
        thrown(Exception)
    }


    def "test the addition of non formatted string"() {
        when:
        def failedInput = ["0 0 1", "0 1 2"]
        u.multi(failedInput)

        then:
        thrown(Exception)
    }

}
