package SMARTron.Engine

import spock.lang.Specification

class LetterConverterTest extends Specification{

    LetterConverter lc = new LetterConverter();

    def "generate a letter grade"() {
        when:
        List<Float> list = Arrays.asList(76.9, 86.8, 65.1, 57.0, 94.1)
        List<String> returnedList = lc.genLetterGrade(list)

        then:
        returnedList.size() == 5
        returnedList.get(0) == "C"
        returnedList.get(1) == "B"
        returnedList.get(2) == "D"
        returnedList.get(3) == "F"
        returnedList.get(4) == "A"
    }

    def "fail with string input array"() {
        when:
        List<String> list = Arrays.asList("76.9", "86.8", "65.1", "57.0", "94.1")
        List<String> returnedList = lc.genLetterGrade(list)

        then:
        thrown Exception
    }

    //BVA test for genLetterGrade
    def "in point for each grade boundary"() {
        when:
        List<Float> list = Arrays.asList(62.9, 72.9, 82.9, 92.9)
        List<String> returnedList = lc.genLetterGrade(list)

        then:
        returnedList.size() == 4
        returnedList.get(0) == "F"
        returnedList.get(1) == "D"
        returnedList.get(2) == "C"
        returnedList.get(3) == "B"
    }

    //BVA test for genLetterGrade
    def "on point for each grade boundary"() {
        when:
        List<Float> list = Arrays.asList(63.0, 73.0, 83.0, 93.0)
        List<String> returnedList = lc.genLetterGrade(list)

        then:
        returnedList.size() == 4
        returnedList.get(0) == "D"
        returnedList.get(1) == "C"
        returnedList.get(2) == "B"
        returnedList.get(3) == "A"
    }

    //BVA test for genLetterGrade
    def "off point for each grade boundary"() {
        when:
        List<Float> list = Arrays.asList(63.1, 73.1, 83.1, 93.1)
        List<String> returnedList = lc.genLetterGrade(list)

        then:
        returnedList.size() == 4
        returnedList.get(0) == "D"
        returnedList.get(1) == "C"
        returnedList.get(2) == "B"
        returnedList.get(3) == "A"
    }

    //testing setLetterBreakpoints
    def "set letter breakpoints"() {
        when:
        String breakpoints = "95,85,75,65";
        lc.setLetterBreakpoints(breakpoints)

        then:
        lc.getAboveForA() == 95.0
        lc.getAboveForB() == 85.0
        lc.getAboveForC() == 75.0
        lc.getAboveForD() == 65.0
    }
}
