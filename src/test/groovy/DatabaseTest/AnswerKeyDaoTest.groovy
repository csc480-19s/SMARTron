package DatabaseTest

import Database.AnswerKeyDao
import spock.lang.Specification

class AnswerKeyDaoTest extends Specification {

    AnswerKeyDao akd = new AnswerKeyDao()

    def "correct addition of an answerkey"() {

        when:
        akd.addAnswerKey('midterm', 'MATT', 'a,b,c,d', "4")


        then:
        def result = akd.selectAnswerKey('midterm', 'MATT')
        result == ['a,b,c,d']
        //Cleanup of the insert
        akd.deleteAnswerKey("midterm")
    }

    def "test getInstructorID method"() {
        when: akd.addAnswerKey('midterm', 'jtrynisk', 'a,b,c,d', '4')
        def result = akd.getInstructorId('midterm')
        akd.deleteAnswerKey('midterm')

        then:
        result == 'jtrynisk'

    }

    def "test getting the length of the answerkey"() {
        when:
        akd.addAnswerKey('midterm', 'jtrynisk', 'a,b,c,d,e', '5')
        def result = akd.getAnswerKeyLength('midterm')

        then:
        result == 5
        result != 4 || 6
        akd.deleteAnswerKey('midterm')
    }

    def "test the addUpdateAnswerKey method"() {
        when:
        akd.addAnswerKey('midterm', 'jtrynisk', 'a,b,c,d', '4')
        akd.addUpdatedAnswerKey('midterm', 'e,e,e,e')

        then:
        def result = akd.selectUpdatedAnswerKey('midterm', 'jtrynisk')
        result == ['e,e,e,e']
        akd.deleteAnswerKey('midterm')
    }

    def "test the updateAnswerKey method"() {
        when:
        akd.addAnswerKey('midterm', 'jtrynisk', 'a,b,c,d', '4')
        akd.updateOriginalAnswerKey('midterm', 'e,e,e,e')
        def result = akd.selectAnswerKey('midterm', 'jtrynisk')

        then:
        result == ['e,e,e,e']
        akd.deleteAnswerKey('midterm')
    }

    def "test adding more answers than original through addUpdatedAnswerKey"() {
        when:
        akd.addAnswerKey('midterm', 'jtrynisk', 'a,b,c,d', '4')
        akd.addUpdatedAnswerKey('midterm', 'a,b,c,d,e')
        def result = akd.selectUpdatedAnswerKey('midterm', 'jtrynisk')
        akd.deleteAnswerKey('midterm')

        then:
        result == ['a,b,c,d']
    }

    def "test adding less answers than original through addUpdatedAnswerKey"() {
        when:
        akd.addAnswerKey('midterm', 'jtrynisk', 'a,b,c,d', '4')
        akd.addUpdatedAnswerKey('midterm', 'a,b,c')
        def result = akd.selectUpdatedAnswerKey('midterm', 'jtrynisk')
        akd.deleteAnswerKey('midterm')

        then:
        result == ['a,b,c,d']

    }

    def "test adding more answers than origin through updateAnswerKey"() {
        when:
        akd.addAnswerKey('midterm', 'jtrynisk', 'a,b,c,d', '4')
        akd.updateOriginalAnswerKey('midterm', 'a,b,c,d,e')
        def result = akd.selectAnswerKey('midterm', 'jtrynisk')
        akd.deleteAnswerKey('midterm')

        then:
        result == ['a,b,c,d']

    }

    def "test adding less answers than original through updateAnswerKey"() {
        when:
        akd.addAnswerKey('midterm', 'jtrynisk', 'a,b,c,d', '4')
        akd.updateOriginalAnswerKey('midterm', 'a,b,c')
        def result = akd.selectAnswerKey('midterm', 'jtrynisk')
        akd.deleteAnswerKey('midterm')

        then:
        result == ['a,b,c,d']

    }

    def "incorrect examid"() {

        when:
        akd.addAnswerKey(1, "Oswego")

        then:
        thrown(Exception)

    }

    def "incorrect instructorID"() {

        when:
        akd.addAnswerKey("Midterm", Oswego)

        then:
        thrown(Exception)
    }

    def "too long of exam_id"() {

        when:
        akd.addAnswerKey("123456789", "MATT", 'a,b,c')

        then:
        thrown(Exception)


    }

    def "too long of instructor"() {

        when:
        akd.addAnswerKey("Midterm", "Bastian Tenbergen is the best professor at SUNY Oswego.", 'a,b,c')

        then:
        thrown(Exception)

    }

    def "multiple exams with same id"() {

        when:
        akd.addAnswerKey("Midterm", "MATT", 'a,b,c')
        akd.addAnswerKey("Midterm", "MATT", 'a,b,c')

        then:
        thrown(Exception)
        akd.deleteAnswerKey("Midterm")
    }


    def "test deletion of an exam"() {

        when:
        akd.addAnswerKey("Midterm", "MATT", 'abc')

        and:
        akd.deleteAnswerKey("Midterm")
        akd.selectAnswerKey('Midterm', 'MATT')

        then:
        thrown(Exception)

    }

//Another failure of throwing an exception...le sad
    def "test error throwing of a delete of an exam"() {

        when:
        akd.deleteAnswerKey("Midterm")

        then:
        thrown(Exception)

    }

    def "test addition of an updated answer key"() {

        when:
        akd.addAnswerKey('Midterm', 'MATT', 'a,b,c', "3")
        akd.addUpdatedAnswerKey('Midterm', 'a,b,c,d,e,f')

        then:
        def result = akd.selectUpdatedAnswerKey('Midterm', 'MATT')
        result == ['a,b,c,d,e,f']
        akd.deleteAnswerKey('Midterm')

    }

//This should throw an error as that test doesnt exist, midterm is null through
//the selectUpdated. the non existent test ends up being an empty list
    def "test thrown exception of an updated answer key"() {

        when:
        akd.addAnswerKey("Midterm", "MATT", 'a,b,c')
        akd.addUpdatedAnswerKey("Halfterm", 'a,b,c,d')

        then:
        thrown(Exception)
        def wrongResult = akd.selectUpdatedAnswerKey('Halfterm', 'MATT')
        wrongResult == ['a,b,c,d']
        akd.deleteAnswerKey("Midterm")

    }

//What I am learning is ain't nothing throwing exceptions that should... :(
    def 'test throwing an exception for selecting an answer key that doesnt exist'() {

        when:
        akd.deleteAnswerKey("Midterm")
        akd.selectAnswerKey("Midterm", "MATT")

        then:
        thrown(Exception)

    }

}
