package DatabaseTest

import Database.InstructorDao
import spock.lang.Specification

/*
*
* Author: Sean McGrath
*
 */
//QMR section 3.6
class InstructorDaoTest extends Specification {


    def "AddInstructor - the instructors information are all nonempty strings of size =< 200; Test#1"() {
        //assumes there is not already an instructor with this name in the system
        when: ""
        InstructorDao instDao = new InstructorDao()
        def instIDSize200 = "STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID2135243STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID21352"
        def instFName = "Test"
        def instLName = "Instructor"
        instDao.addInstructor(instIDSize200, instFName, instLName)
        def list = instDao.selectInstructor(instFName, instLName)
        instDao.deleteInstructor(instIDSize200)//cleanup
        then: "list contains that id"//TC#1
        list == [instIDSize200]
        notThrown(Exception)
    }

    def "AddInstructor - the instructor is already in the database"() {
        when: ""
        InstructorDao instDao = new InstructorDao()
        def instIDSize200 = "STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID2135243STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID21352"
        def instFName = "Test"
        def instLName = "Instructor"
        instDao.addInstructor(instIDSize200, instFName, instLName)
        instDao.addInstructor(instIDSize200, instFName, instLName)
        then: "exception thrown"
        thrown(Exception)
        instDao.deleteInstructor(instIDSize200)
    }

    def "AddInstructor - the instructor information is empty strings; Test#2"() {
        when: ""
        InstructorDao instDao = new InstructorDao()
        instDao.addInstructor("", "", "")
        then: "an exception is thrown, cannot be added to table"//TC#2
        thrown(Exception)
    }

    def "AddInstructor - the instructor information are all null; Test#3"() {
        when: ""
        InstructorDao instDao = new InstructorDao()
        instDao.addInstructor(null, null, null)
        then: "an exception is thrown"//TC#3
        thrown(Exception)
    }

    def "AddInstructor - the information are not strings"() {
        when: ""
        InstructorDao instDao = new InstructorDao()
        instDao.addInstructor(5, 17, 54)
        then: "an exception is thrown"//TC#4
        thrown(Exception)
    }

    def "AddInstructor - the instructor ID is larger than 200"() {
        when: ""
        InstructorDao instDao = new InstructorDao()
        instDao.addInstructor(instIDSize201, instFName, instLName)
        then: "an exception is thrown"//TC#5, only id
        thrown(Exception)
    }

    def "AddInstructor - All values are larger than size 200"() {
        when: ""
        InstructorDao instDao = new InstructorDao()
        instDao.addInstructor(instIDSize201, fNLong, lNLong)
        then: "an exception is thrown"//full TC#5
        thrown(Exception)
    }


    def "DeleteInstructor - the instructor was not in the database already"() {
        when: ""
        InstructorDao instDao = new InstructorDao()
        def instID = "TESTID213524987j6htr4u34u"
        def instFName = "Test"
        def instLName = "Instructor"
        def beforeList = instDao.selectInstructor(instFName, instLName)
        instDao.deleteInstructor(instID)
        def afterList = instDao.selectInstructor(instFName, instLName)
        then: "the instructor should not be in the database"
        notThrown(Exception)
        beforeList.size() == 0
        afterList.size() == 0
    }

    def "DeleteInstructor - the instructor was in the database"() {
        when: ""
        InstructorDao instDao = new InstructorDao()
        def instID = "TESTID213524987j6htr4u34u"
        def instFName = "Test"
        def instLName = "Instructor"
        instDao.addInstructor(instID, instFName, instLName)
        def beforeList = instDao.selectInstructor(instFName, instLName)
        instDao.deleteInstructor(instID)
        def afterList = instDao.selectInstructor(instFName, instLName)
        then: "instructor should be removed"
        notThrown(Exception)
        beforeList.size() == 1
        afterList.size() == 0
    }

    def "DeleteInstructor - instructor ID is too long"() {
        when: ""
        InstructorDao instDao = new InstructorDao()
        def longInstID = "1STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID2135243STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID213524STID21352"
        instDao.deleteInstructor(longInstID)
        then: "exception thrown"
        thrown(Exception)
    }

    def "DeleteInstructor - the ID is an empty string"() {
        when: ""
        InstructorDao instDao = new InstructorDao()
        instDao.deleteInstructor("")
        then: "Exception thrown"
        thrown(Exception)
    }

    def "DeleteInstructor - the ID is null"() {
        when: ""
        InstructorDao instDao = new InstructorDao()
        instDao.deleteInstructor(null)
        then: "an exception is thrown"
        thrown(Exception)
    }

    def "DeleteInstructor - the ID is not a string"() {
        when: ""
        InstructorDao instDao = new InstructorDao()
        instDao.deleteInstructor(5)
        then: "an exception is thrown"
        thrown(Exception)
    }


    def "SelectInstructor - empty string"() {
        when: ""
        InstructorDao instDao = new InstructorDao()
        def list = instDao.selectInstructor("", "")
        then: "nothing happens, nothing returned"
        list.size() == 0
        noExceptionThrown()
    }

    def "SelectInstructor - null"() {
        when: ""
        InstructorDao instDao = new InstructorDao()
        instDao.selectInstructor(null, null)
        then: "exception thrown"
        thrown(Exception)
    }

    def "SelectInstructor - not string"() {
        when: ""
        InstructorDao instDao = new InstructorDao()
        instDao.selectInstructor(5, 6)
        then: "exception thrown"
        thrown(Exception)
    }

    def "test the getInstIdfromEmail method"() {//almsot useless but can keep anyway :P
        when:
        InstructorDao id = new InstructorDao()
        id.addInstructor('jtrynisk', 'Jondn', 'Tryniski')
        def result = id.getInstIdFromEmail('jtrynisk@oswego.edu')

        then:
        result == ['jtrynisk']

    }
}