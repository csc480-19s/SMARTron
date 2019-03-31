package SMARTron

import SMARTron.Database.ExamDao
import SMARTron.Database.GenericDao
import spock.lang.Specification

class ExamDaoTest extends Specification{

    ExamDao examDao = new ExamDao()
    GenericDao genericDao = new GenericDao()

    def "add a course into db"() {
        when:
        int before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "MATT", "MATT", "EXAM1")
        //examDao.addExam("MATT", "MATT", "MATT", "MATT",
                //"MATT", "MATT", "MATT", "MATT")
        int after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        println(after)

        then:
        before == 0
        after == 1
        //clean up the db
        examDao.deleteExam("EXAM1")
    }

    def "delete a course from db"() {
        when:
        //populate the db to try deleting
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "MATT", "MATT", "EXAM1")

        int before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.deleteExam("EXAM1")
        int after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        println(after)

        then:
        before == 1
        after == 0
    }

}
