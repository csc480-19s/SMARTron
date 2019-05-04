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

}
