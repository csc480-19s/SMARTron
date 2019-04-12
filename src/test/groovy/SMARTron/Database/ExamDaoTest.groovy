package SMARTron.Database

import spock.lang.Specification

class ExamDaoTest extends Specification{

    ExamDao examDao = new ExamDao()
    GenericDao genericDao = new GenericDao()

    def "add a course into db"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "EXAM1", "answers")
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
                "02/17/1997", "54322", "123456789", "EXAM1", "answers")

        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.deleteExam("EXAM1")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        before.size() == 1
        after.size() == 0
    }

    def "try to add with non string first name"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam(77, "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "EXAM1", "answers")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        thrown Exception
    }

    def "first name too long for db"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("DilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbert"+
                "DilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbert" +
                "DilbertDilbertDilbertDilbertDilbertDilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "EXAM1", "answers")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        thrown Exception
    }

    def "last name as non string"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", GenericDao(), "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "EXAM1", "answers")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        thrown Exception
    }

    def "last name too long to fit into db"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "JohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnson" +
                "JohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnson" +
                "JohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "EXAM1", "answers")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        thrown Exception
    }

    def "student id as non string"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "Johnson", 804991926, "Spring2019",
                "02/17/1997", "54322", "123456789", "EXAM1", "answers")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        thrown Exception
    }

    def "student id too long"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "Johnson", "80499192600", "Spring2019",
                "02/17/1997", "54322", "123456789", "EXAM1", "answers")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        thrown Exception
    }

    def "semester as non string"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "Johnson", "804991926", 2019,
                "02/17/1997", "54322", "123456789", "EXAM1", "answers")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        thrown Exception

    }

    def "semester too long"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019q34",
                "02/17/1997", "54322", "123456789", "EXAM1", "answers")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        thrown Exception
    }

    def "birthdate as non string"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                Date("2/2/17"), "54322", "123456789", "EXAM1", "answers")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        thrown Exception
    }

    def "birthdate as too long of a string"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997/21stcentury", "54322", "123456789", "EXAM1", "answers")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        thrown Exception
    }

    def "crn as non string"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", 54322, "123456789", "EXAM1", "answers")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        thrown Exception
    }

    def "crn as too long"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "543224", "123456789", "EXAM1", "answers")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        thrown Exception
    }

    def "instructor id as non string"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", 123456789, "EXAM1", "answers")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        thrown Exception
    }

    def "instructor id as too long of a string"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789123456789123456789123456789123456789" +
                "123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789" +
                "123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789" +
                "123456789", "EXAM1", "answers")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        thrown Exception
    }

    def "exam id as non string"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", 1, "answers")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        thrown Exception
    }

    def "exam id as too long of a string"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "EXAM10", "answers")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        thrown Exception
    }

    def "answers as non string"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "EXAM1", int[25])
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        thrown Exception
    }

    def "answers as too long of a string"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        char[] chars = new char[5001];
        Arrays.fill(chars, '2,');
        String answers = new String(chars);
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "EXAM1", answers)
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        thrown Exception
    }

    def "try adding duplicate exams into db"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "EXAM1", "answers")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "EXAM1", "answers")

        then:
        thrown Exception
        examDao.deleteExam("EXAM1")
    }

    def "delete a exam that's not in the db"() {
        when:
        List<String> before = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")
        examDao.deleteExam("EXAM1")
        List<String> after = genericDao.select("SELECT * FROM scantron.exam WHERE exam_id='EXAM1';")

        then:
        notThrown Exception
        before.size() == 0
        after.size() == 0
    }

}