package SMARTron

import spock.lang.*
import SMARTron.Engine.*

class ToolsTest extends Specification {

    Tools t = new Tools()

    def "Test an integer between 0 and 26"() {

        when:
        def a = "1"

        then:
        t.alphaConverter(a) == "A"

    }

    //Engine must fix this test as we know it is failing
    def "Boundary test at inbounds 0"() {
        when:
        def x = "0"

        then:
        t.alphaConverter(x) == "a"
    }

    def "Boundary test at inbounds 26"() {
        when:
        def x = "26"

        then:
        t.alphaConverter(x) == "Z"
    }

    //not throwing an exception still returning a character
    def "Boundary test at outbounds -1"() {
        when:
        def x = "-1"
        t.alphaConverter(x)

        then:
        thrown(Exception)
    }

    //not throwing an exception still returning a character
    def "Boundary test at outbonds 27"() {
        when:
        def x = "27"
        t.alphaConverter(x)

        then:
        thrown(Exception)
    }

    def "Test with data within bounds"() {
        when:
        def x = "13"

        then:
        t.alphaConverter(x) == "M"
    }

    def "Test a noninteger input since it takes a string"() {
        when:
        def x = "x"
        t.alphaConverter(x)

        then:
        thrown(Exception)
    }

    def "Test with an integer as input"() {
        when:
        def x = 1
        t.alphaConverter(x)

        then:
        thrown(Exception)
    }

    def "Test with an incorrect Object"() {
        when:
        Student x = new Student()
        def a = x
        t.alphaConverter(x)

        then:
        thrown(Exception)
    }

}
