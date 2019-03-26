package SMARTron

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation

import javax.sql.DataSource
import groovy.sql.Sql
import spock.lang.*

import java.sql.SQLException

class AnswerKeyDaoTest extends Specification{

    AnswerKeyDao akd = new AnswerKeyDao()
    GenericDao gd = new GenericDao()



    def "correct addition of an answerkey"() {

        when:
        akd.addAnswerKey("Midterm", "MATT")

        then:
        def result = gd.select("SELECT * FROM scantron.answerkey WHERE exam_id = 'Midterm' AND instructor_id = 'MATT';")
        result == 1
        //Cleanup of the insert
        akd.deleteAnswerKey("Midterm")
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

        //delete after next run the first delet
        when:
        akd.addAnswerKey("123456789", "MATT")

        then:
        def result = gd.select("SELECT * FROM scantron.answerkey WHERE exam_id = '123456789' AND instructor_id = 'MATT';")
        result == 0


    }

    def "too long of intructor_id"() {

        when:
        akd.addAnswerKey("Midterm", "Bastian Tenbergen is the best professor at SUNY Oswego.")

        then:
        def result = gd.select("SELECT * FROM scantron.answerkey WHERE exam_id = 'Midterm' AND instructor_id = 'Bastian Tenbergen is the best professor at SUNY Oswego.';")
        result == 0

    }

    def "multiple exams with same id"() {

        when:
        akd.addAnswerKey("Midterm", "MATT")
        akd.addAnswerKey("Midterm", "MATT")

        then:
        def result = gd.select("SELECT * FROM scantron.answerkey WHERE exam_id = 'Midterm' AND instructor_id = 'MATT';")
        result == 1
        akd.deleteAnswerKey("Midterm")
    }

}
