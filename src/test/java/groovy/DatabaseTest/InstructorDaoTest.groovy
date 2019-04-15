package groovy.DatabaseTest

import spock.lang.Specification

//QMR section 3.6
class   InstructorDaoTest extends Specification {//TODO: possibly look into deriving more tests
    def "AddInstructor"() {//3.6.2
        given: "an instructors information"
        InstructorDao instDao = new InstructorDao()
        def instIDSize200 = "STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID2135243STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID21352"
        def instIDSize201 = "1STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID2135243STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID21352"
        def instFName = "Test"
        def fNLong = "1STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524"
        def instLName = "Instructor"
        def lNLong = "1STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524"

        when: "the instructors information are all nonempty strings of size =< 200; Test#1"
        //assumes there is not already an instructor with this name in the system
        instDao.addInstructor(instIDSize200, instFName, instLName)
        def list = instDao.selectInstructor(instFName, instLName)
        instDao.deleteInstructor(instIDSize200)//cleanup
        then: "list contains that id"//TC#1
        list == [instIDSize200]
        notThrown(Exception)

        when: "the instructor is already in the database"
        instDao.addInstructor(instIDSize200, instFName, instLName)
        instDao.addInstructor(instIDSize200, instFName, instLName)
        then:  "exception thrown"
        thrown(Exception)
        instDao.deleteInstructor(instIDSize200)

        when: "the instructor information is empty strings; Test#2"
        instDao.addInstructor("","","")
        then: "an exception is thrown, cannot be added to table"//TC#2
        thrown(Exception)

        when: "the instructor information are all null; Test#3"
        instDao.addInstructor(null, null, null)
        then: "an exception is thrown"//TC#3
        thrown(Exception)

        when: "the information are not strings"
        instDao.addInstructor(5,17,54)
        then: "an exception is thrown"//TC#4
        thrown(Exception)

        when: "the instructor ID is larger than 200"
        instDao.addInstructor(instIDSize201, instFName, instLName)
        then: "an exception is thrown"//TC#5, only id
        thrown(Exception)

        when: "All values are larger than size 200"
        instDao.addInstructor(instIDSize201, fNLong, lNLong)
        then: "an exception is thrown"//full TC#5
        thrown(Exception)
    }

    def "DeleteInstructor"() {//3.6.3
        given: "an instructors information"
        InstructorDao instDao = new InstructorDao()
        def instID = "TESTID213524987j6htr4u34u"
        def longInstID = "1STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID2135243STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID21352"
        def instFName = "Test"
        def instLName = "Instructor"

        when: "the instructor was not in the database already"
        def beforeList = instDao.selectInstructor(instFName, instLName)
        instDao.deleteInstructor(instID)
        def afterList = instDao.selectInstructor(instFName, instLName)
        then: "the instructor should not be in the database"
        notThrown(Exception)
        beforeList.size() == 0
        afterList.size() == 0

        when: "the instructor was in the database"//this is problematic... (1) first run inst not deleted<bug!> (2) run again and exception thrown since inst still in database
        instDao.addInstructor(instID, instFName, instLName)
        beforeList = instDao.selectInstructor(instFName, instLName)
        instDao.deleteInstructor(instID)
        afterList = instDao.selectInstructor(instFName, instLName)
        then: "instructor should be removed"
        notThrown(Exception)
        beforeList.size() == 1
        afterList.size() == 0

        when: "instructor ID is too long"
        instDao.deleteInstructor(longInstID)
        then: "exception thrown"
        thrown(Exception)

        when: "the ID is an empty string"
        instDao.deleteInstructor("")
        then: "Exception thrown"
        thrown(Exception)

        when: "the ID is null"
        instDao.deleteInstructor(null)
        then: "an exception is thrown"
        thrown(Exception)

        when: "the ID is not a string"
        instDao.deleteInstructor(5)
        then: "an exception is thrown"
        thrown(Exception)
    }

    def "SelectInstructor"(){
        //TC#1 tested already above
        given:
        InstructorDao instDao = new InstructorDao()

        when: "empty string"
        def list = instDao.selectInstructor("", "")
        then:"nothing happens, nothing returned"
        list.size() == 0
        noExceptionThrown()

        when: "null"
        instDao.selectInstructor(null, null)
        then: "exception thrown"
        thrown(Exception)

        when: "not string"
        instDao.selectInstructor(5, 6)
        then: "exception thrown"
        thrown(Exception)

    }
}