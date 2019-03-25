package SMARTron.DbConnector.src

import spock.lang.Specification

import java.sql.SQLException

class   InstructorDaoTest extends Specification {
    def "AddInstructor"() {
        given: "an instructors information"
            InstructorDao instDao = new InstructorDao()
            def instID = "TESTID213524"
            def instFName = "Bastian"
            def instLName = "Tenbergen"
        when: "the instructors information are all valid strings; Test#1"
            instDao.addInstructor(instID, instFName, instLName)
        then: "there are no exceptions; Test#1"//1.1, 2.1, 3.1
            noExceptionThrown()
        when: "the instructor information is empty strings; Test#2"//1.a, 2.a, 3.a
            instDao.addInstructor("","","")
        then: "an exception is thrown"//should an exception be thrown for empty strings???
            SQLException e = thrown()
        when: "the instructor information are all null; Test#3"//1.b, 2.b, 3.b
            instDao.addInstructor(null, null, null)
        then: "an exception is thrown"
            SQLException e2 = thrown()
    }

    def "DeleteInstructor"() {
        given: "an instructors ID"
            InstructorDao instDao = new InstructorDao()
            def instID = "TESTID213524"
        when: "the ID is a valid string"
            instDao.deleteInstructor(instID)
        then: "the instructor should not be in the database"
            noExceptionThrown()//find a way to check if instructor was removed, possibly using selectInstructor
        when: "the ID is an empty string"
            instDao.deleteInstructor("")
        then: "an exception is thrown"
            SQLException e = thrown()
        when: "the ID is null"
            instDao.deleteInstructor(null)
        then: "an exception is thrown"
            SQLException e2 = thrown()
    }

    //test for commandline output since there is no return type (D:<)
    //theoretically works
    def "SelectInstructor"() {
        given: "an instructor ID"
            InstructorDao instDao = new InstructorDao()
        when: "an instructor is added"
            def buffer = new ByteArrayOutputStream()
            System.out = new PrintStream(buffer)
            instDao.addInstructor(ID, FName, LName)
        and: "the instructor is selected"
        instDao.selectInstructor(ID)
        then:
        buffer.toString() == out

        where:
        ID | FName | LName | out
        'TESTID123' | 'fname' | 'lname' | ID + ' ' + FName + ' ' + LName
        '' | '' | '' | ID + ' ' + FName + ' ' + LName//possibly remove, if '' is not supposed to be added; or change

    }
}
