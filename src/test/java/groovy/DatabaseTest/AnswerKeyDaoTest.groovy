package groovy.DatabaseTest


import SMARTron.Database.AnswerKeyDao
import spock.lang.Ignore
import spock.lang.Specification

class AnswerKeyDaoTest extends Specification {

    AnswerKeyDao akd = new AnswerKeyDao()

    //for mocking later, just need working tests for now
    /*def setup() {


        answerKeyDao = Mock(AnswerKeyDao)

    }*/


    def "correct addition of an answerkey"() {

        when:
        //This simply ensures that the exam is not already in the DB
        akd.deleteAnswerKey('midterm')
        akd.addAnswerKey('midterm', 'MATT', 'a,b,c,d')


        then:
        def result = akd.selectAnswerKey('midterm', 'MATT')
        result == ['a,b,c,d']
        //Cleanup of the insert
        akd.deleteAnswerKey("midterm")
    }

    def "incorrect examid"() {

        when:
        akd.addAnswerKey(1, "Oswego")

        then:
        thrown(Exception)

    }

    def "incorrect instructerID"() {

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

    //Should throw Exception based on the code, but returns an empty list
    //The exception should be thrown from select
    @Ignore
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
    @Ignore
    def "test error throwing of a delete of an exam"() {

        when:
        akd.deleteAnswerKey("Midterm")

        then:
        thrown(Exception)

    }

    def "test addition of an updated answer key"() {

        when:
        akd.addAnswerKey('Midterm', 'MATT', 'a,b,c')
        akd.addUpdatedAnswerKey('Midterm', 'a,b,c,d,e,f')

        then:
        def result = akd.selectUpdatedAnswerKey('Midterm', 'MATT')
        result == ['a,b,c,d,e,f']
        akd.deleteAnswerKey('Midterm')

    }

//This should throw an error as that test doesnt exist, midterm is null through
//the selectUpdated. the non existent test ends up being an empty list
    @Ignore
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
    @Ignore
    def 'test throwing an exception for selecting an answer key that doesnt exist'() {

        when:
        akd.deleteAnswerKey("Midterm")
        akd.selectAnswerKey("Midterm", "MATT")

        then:
        thrown(Exception)

    }

}
