package DatabaseTest

import Database.ExamDao
import spock.lang.Specification

class ExamDaoTest extends Specification{

    ExamDao examDao = new ExamDao()

    def "add and select exam db then delete"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.deleteExam("69696")
        List<String> after2 = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        before.size() == 0
        after.size() == 1
        after2.size() == 0
    }

    def "try to add with non string first name"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam(77, "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        thrown Exception
    }

    def "first name too long for db"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("DilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbert"+
                "DilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbertDilbert" +
                "DilbertDilbertDilbertDilbertDilbertDilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "EXAM1", "answers")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        thrown Exception
    }

    def "last name as non string"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("Dilbert", AnswerKeyDao(), "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        thrown Exception
    }

    def "last name too long to fit into db"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("Dilbert", "JohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnson" +
                "JohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnson" +
                "JohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnsonJohnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        thrown Exception
    }

    def "student id as non string"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("Dilbert", "Johnson", 804991926, "Spring2019",
                "02/17/1997", "54322", "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        thrown Exception
    }

    def "student id too long"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("Dilbert", "Johnson", "80499192600", "Spring2019",
                "02/17/1997", "54322", "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        thrown Exception
    }

    def "semester as non string"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("Dilbert", "Johnson", "804991926", 2019,
                "02/17/1997", "54322", "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        thrown Exception
    }

    def "semester too long"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019q34",
                "02/17/1997", "54322", "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        thrown Exception
    }

    def "birthdate as non string"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                Date("2/2/17"), "54322", "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        thrown Exception
    }

    def "birthdate as too long of a string"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997/21stcentury", "54322", "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        thrown Exception
    }

    def "crn as non string"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", 54322, "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        thrown Exception
    }

    def "crn as too long"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "543224", "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        thrown Exception
    }

    def "instructor id as non string"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", 123456789, "69696", "answers")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        thrown Exception
    }

    def "instructor id as too long of a string"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789123456789123456789123456789123456789" +
                "123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789" +
                "123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789" +
                "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        thrown Exception
    }

    def "exam id as non string"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", 1, "answers")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        thrown Exception
    }

    def "exam id as too long of a string"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "EXAM10", "answers")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        thrown Exception
    }

    def "answers as non string"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "69696", int[25])
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        thrown Exception
    }

    def "answers as too long of a string"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        char[] chars = new char[5001];
        Arrays.fill(chars, '2,');
        String answers = new String(chars);
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "69696", answers)
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        thrown Exception
    }

    def "try adding duplicate exams into db"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "69696", "answers")
        List<String> after2 = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        after2.size() == 2
        examDao.deleteExam("69696")
    }

    def "delete an exam that's not in the db"() {
        when:
        List<String> before = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")
        examDao.deleteExam("69696")
        List<String> after = examDao.selectExamId("69696","123456789","804991926","54322","Spring2019")

        then:
        notThrown Exception
        before.size() == 0
        after.size() == 0
    }

    def "fail select an exam from db from examid not as string"() {
        when:
        List<String> before = examDao.selectExamId(1, "123456789", "804991926", "54322", "Spring2019")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696", "123456789", "804991926", "54322", "Spring2019")

        then:
        thrown Exception
        //clean up the db
        examDao.deleteExam("69696")
    }

    def "fail select an exam from db from instrId not as string"() {
        when:
        List<String> before = examDao.selectExamId("69696", 123456789, "804991926", "54322", "Spring2019")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696", 123456789, "804991926", "54322", "Spring2019")

        then:
        thrown Exception
        //clean up the db
        examDao.deleteExam("69696")
    }

    def "fail select an exam from db from stdId not as string"() {
        when:
        List<String> before = examDao.selectExamId("69696", "123456789", 804991926, "54322", "Spring2019")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696", "123456789", 804991926, "54322", "Spring2019")

        then:
        thrown Exception
        //clean up the db
        examDao.deleteExam("69696")
    }

    def "fail select an exam from db from crn as not a string"() {
        when:
        List<String> before = examDao.selectExamId("69696", "123456789", "804991926", 54322, "Spring2019")
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696", "123456789", "804991926", 54322, "Spring2019")

        then:
        thrown Exception
        //clean up the db
        examDao.deleteExam("69696")
    }

    def "fail select an exam from db from semester as not a string"() {
        when:
        List<String> before = examDao.selectExamId("69696", "123456789", "804991926", "54322", 2019)
        examDao.addExam("Dilbert", "Johnson", "804991926", "2019",
                "02/17/1997", "54322", "123456789", "69696", "answers")
        List<String> after = examDao.selectExamId("69696", "123456789", "804991926", "54322", 2019)

        then:
        thrown Exception
        //clean up the db
        examDao.deleteExam("69696")
    }

    def "test the selectStudents method"() {
        when:
        examDao.addExam("Dilbert", "Johnson", "804991926", "Spring2019",
                "02/17/1997", "54322", "123456789", "69696", "answers")
        def result = examDao.selectStudents('123456789', '69696')
        examDao.deleteExam('69696')

        then:
        result == ['Dilbert Johnson']
    }

}
