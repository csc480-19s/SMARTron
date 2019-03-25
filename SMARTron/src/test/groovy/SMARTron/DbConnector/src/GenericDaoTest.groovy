package SMARTron.DbConnector.src

import spock.lang.Specification

import java.sql.SQLException

class GenericDaoTest extends Specification {

    def "Insert"() {
        given: "an SQL string"
        GenericDao genDao = new GenericDao()
        def SQL = "TEST213524"
        when: "the SQL string is a valid string; Test#1"
        genDao.insert(SQL)
        then: "there are no exceptions; Test#1"//1.1, 2.1, 3.1
        noExceptionThrown()
        when: "the SQL string is an empty string; Test#2"//1.a, 2.a, 3.a
        genDao.insert("")
        then: "an exception is thrown"//should an exception be thrown for empty strings???
        SQLException e = thrown()
        when: "the SQL string is not a string; Test#3"//1.b, 2.b, 3.b
        genDao.insert(null)
        then: "an exception is thrown"
        SQLException e2 = thrown()
    }

    def "Delete"() {
        given: "an SQL string"
        GenericDao genDao = new GenericDao()
        def SQL = "TEST213524"
        when: "the SQL string is a valid string; Test#1"
        genDao.insert(SQL)
        genDao.delete(SQL)
        then: "there are no exceptions; Test#1"//1.1, 2.1, 3.1
        noExceptionThrown()
        when: "the SQL string is an empty string; Test#2"//1.a, 2.a, 3.a
        genDao.insert("")
        genDao.delete("")
        then: "an exception is thrown"//should an exception be thrown for empty strings???
        SQLException e = thrown()
        when: "the SQL string is not a string; Test#3"//1.b, 2.b, 3.b
        genDao.insert(null)
        genDao.delete(null)
        then: "an exception is thrown"
        SQLException e2 = thrown()
    }

    def "Select"() {
        given: "a SQL string"
        GenericDao genDao = new GenericDao()
        when: "an instructor is added"
        def buffer = new ByteArrayOutputStream()
        System.out = new PrintStream(buffer)
        genDao.insert(ID)
        and: "the instructor is selected"
        genDao.select(ID)
        then:
        buffer.toString() == out

        where:
        ID |  out
        'TESTID123' |  ID
        '' | ID
    }
}
