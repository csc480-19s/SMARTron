package GUIMiddlewareTest

import org.json.simple.JSONArray
import org.json.simple.JSONObject
import spock.lang.Specification
import GUIMiddleware.Question

class QuestionTest extends Specification {

    //Values for the different answers
    Question question = new Question()
    def a = "0"
    def b = "1"
    def c = "2"
    def d = "3"
    def e = "4"



    def "test incrementing a"(){
        when:
        question.increment(a)
        question.increment(a)
        question.increment(a)

        then:
        def returnList = question.returnList()
        def val = returnList.get(0)
        val == [name:"A", value:3]
    }

    def "test incrementing b"(){
        when:
        question.increment(b)
        question.increment(b)

        then:
        def returnList = question.returnList()
        def val = returnList.get(1)
        val == [name:"B", value:2]
    }

    def "test incrementing c"(){
        when:
        question.increment(c)

        then:
        def returnList = question.returnList()
        def val = returnList.get(2)
        val == [name:"C", value:1]
    }

    def "test incrementing d"(){
        when:
        for(int i = 0; i < 5; i++)
            question.increment(d)

        then:
        def returnList = question.returnList()
        def val = returnList.get(3)
        val == [name:"D", value:5]
    }

    def "test incrementing e"(){
        when:
        for(int i = 0; i < 2000; i++)
            question.increment(e)

        then:
        def returnList = question.returnList()
        def val = returnList.get(4)
        val == [name:"E", value:2000]
    }

    def "test adding out of bounds lower"(){
        when:
        def buffer = new ByteArrayOutputStream()
        System.out = new PrintStream(buffer)
        question.increment("-1")

        then:
        buffer.toString() == "SMARTron question object add Error\n"

    }

    def "test adding out of bounds upper"(){
        when:
        def buffer = new ByteArrayOutputStream()
        System.out = new PrintStream(buffer)
        question.increment("5")

        then:
        buffer.toString() == "SMARTron question object add Error\n"

    }

    def "test the return of the entire JSON object"(){
        when:
        question.increment(a)
        question.increment(b)
        question.increment(c)
        question.increment(d)
        question.increment(e)

        then:
        def returnList = question.returnList()
        returnList == [[name:"A", value:1],[name:"B", value:1], [name:"C", value:1], [name:"D", value:1], [name:"E", value:1]]

        and:
        returnList.class == JSONArray

    }

}
