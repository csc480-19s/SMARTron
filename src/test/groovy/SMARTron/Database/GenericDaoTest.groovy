package Database

import spock.lang.Specification

class GenericDaoTest extends Specification {//is this even going to be used or did I just waste my time?...
    GenericDao genDao = new GenericDao()

    def "Insert"() {//3.5.2
        when: "string is a valid SQL string; Test#1"
        //assumes there is not already an instructor with this name in the system
        def beforeList = genDao.select("SELECT * FROM scantron.exam WHERE exam_id='SZVYT';")//either i cant write SQL statements for shit, or this broken
        genDao.insert("INSERT INTO scantron.exam ("+
                " first_name, " +
                "last_name," +
                " student_id," +
                " semester," +
                " gender," +
                " birth_date," +
                " course_crn," +
                " instructor_id," +
                " exam_id," +
                " grade_scale " +
                ") VALUES (" +
                "'Kevin', 'Johnson', '804028361', 'Spring2019', 'm', '03/09/97', '32474', 'id7654', 'SZVYT', '123'" +
                ");")
        def afterList = genDao.select("SELECT * FROM scantron.exam where exam_id=='SZVYT';")
        then: "insert is successful"//TC#1
        noExceptionThrown()
        beforeList.size() == 0
        afterList.size() == 1
        genDao.delete("DELETE FROM scantron.exam WHERE exam_id='SZVYT'")

        when: "String is not a valid SQL string"
        genDao.insert("Invalid String")
        then: "exception thrown"
        thrown(Exception)

        when: "imput is not a string"
        genDao.insert(420)
        then: "exception thrown"
        thrown(Exception)
    }

    def "Delete"() {//3.5.4
        when: "is valid SQL statement"
        genDao.insert("INSERT INTO scantron.instructor (instructor_id) VALUES ('64535')")
        def beforeList = genDao.select("SELECT instructor_id FROM scantron.instructor WHERE instructor_id='64535'")//this scans from NOT instructor... broke?
        genDao.delete("DELETE FROM scantron.instructor WHERE instructor_id='64535'")
        def afterList = genDao.select("SELECT instructor_id FROM scantron.instructor WHERE instructor_id='64535'")
        then: "entry deleted"
        noExceptionThrown()
        beforeList.size() == 1
        afterList.size() == 0

        when: "not SQL statement"
        genDao.delete("zzzzzzzzzzzzzzz")
        then: "exception thrown"
        thrown(Exception)

        when: "null"
        genDao.delete(null)
        then: "exception thrown"
        thrown(Exception)

        when: "not string"
        genDao.delete(4)
        then: "exception thrown"
        thrown(Exception)
    }

    def "Select"() {//3.5.5
        //tc#1 tested in insert

        when: "not SQL statement"
        genDao.select("sql statements suck ohhhhh goooood,,, stop not working")
        then: "exception thrown"
        thrown(Exception)

        when: "null"
        genDao.select(null)
        then: "exception thrown"
        thrown(Exception)

        when: "not string"
        genDao.select(5)
        then: "exception thrown"
        thrown(Exception)
    }
}