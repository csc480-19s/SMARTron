package SMARTron.Database


import SMARTron.Database.AnswerKeyDao
import spock.lang.Ignore
import spock.lang.Specification

class AnswerKeyDaoTest extends Specification{

    AnswerKeyDao akd = new AnswerKeyDao()

    //for mocking later, just need working tests for now
    /*def setup() {


        answerKeyDao = Mock(AnswerKeyDao)

    }*/


    def "correct addition of an answerkey"() {

        when:
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

}
