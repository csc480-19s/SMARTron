package SMARTron

import SMARTron.Database.ExamDao
import SMARTron.Database.GenericDao
import spock.lang.Specification

class ExamDaoTest extends Specification{

    ExamDao examDao = new ExamDao()
    GenericDao genericDao = new GenericDao()

    def "add a course into db"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "MATT", "MATT", "EXAM1")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        before.size() == 0
        after.size() == 1
        //clean up the db
        examDao.deleteExam("EXAM1")
    }

    def "delete a course from db"() {
        when:
        //populate the db to try deleting
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "MATT", "MATT", "EXAM1")

        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.deleteExam("EXAM1")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        before.size() == 1
        after.size() == 0
    }

}
