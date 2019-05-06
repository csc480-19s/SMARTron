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

    def "test more columns than a scantron actually has"() {
        when:
        def testString = ["0-0, 1", "1-0, 3", "2-0, 4", "3-0 4", "4-0, 2", "5-0, 2", "6-0, 3"]
        def retArr = u.multi(testString)

        then:
        retArr == [["1"], ["3"], ["4"], ["4"], ["2"], ["2"], ["3"]]
    }

    //ok this is actually way longer but I already put in the work
    //for this stupidly long test so it is staying.
    //Also this finds that the max rows is 10 should be 20.
    def "test more rows than a scantron actually has"() {
        when:
        def testString = ["0-0 1", "0-1 1", "0-2 1", "0-3 1", "0-4 1", "0-5 1", "0-6 1", "0-7 1", "0-8 1", "0-9 1", "0-10 1",
                          "0-11 1", "0-12 1", "0-13 1", "0-14 1", "0-15 1", "0-16 1", "0-17 1", "0-18 1", "0-19 1", "0-20 1",
                          "0-21 1", "0-22 1", "0-23 1", "0-24 1", "0-25 1", "0-26 1", "0-27 1", "0-28 1", "0-29 1", "0-30 1",
                          "0-31 1", "0-32 1", "0-33 1", "0-34 1", "0-35 1", "0-36 1", "0-37 1", "0-38 1", "0-39 1", "0-40 1",
                          "0-41 1", "0-42 1", "0-43 1", "0-44 1", "0-45 1", "0-46 1", "0-47 1", "0-48 1", "0-49 1", "0-50 1",
                          "0-51 1"]
        def retArr = u.multi(testString)

        then:
        retArr == [["1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                    "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                    "1", "1", "1", "1", "1", "1", "1", "1", "1",]]
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
