package DatabaseTest

import Database.CourseDao
import spock.lang.Specification


class CourseDaoTest extends Specification {

    CourseDao courseDao = new CourseDao()

    def "add,select, and delete a course in db"() {
        when:
        List<String> before = courseDao.selectCourse("SDFGH","800","Spring2019","MATT")
        courseDao.addCourse("SDFGH", "Software Design", "800", "Spring2019", "MATT")
        List<String> after = courseDao.selectCourse("SDFGH","800","Spring2019","MATT")

        then:
        before.size() == 0
        after.size() == 1
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

    def "test selectCrn method"() {
        when:
        courseDao.addCourse('SDFGH', 'Software Design', '800', 'Spring2019', 'MATT')
        def result = courseDao.selectCrn('MATT')
        courseDao.deleteCourse('SDFGH', '800', 'Spring2019', 'MATT')

        then:
        result == 'SDFGH'
    }

    //test trying duplicates and test deleting when not in db
    def "delete a course from db when not in it"() {
        when:
        List<String> before = courseDao.selectCourse("SDFGH","800","Spring2019","MATT")
        courseDao.deleteCourse("SDFGH", "800", "Spring2019", "MATT")
        List<String> after = courseDao.selectCourse("SDFGH","800","Spring2019","MATT")

        then:
        notThrown Exception
        before.size() == 0
        after.size() == 0
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

    def "add a duplicate course into db"() {
        when:
        List<String> before = courseDao.selectCourse("SDFGH","800","Spring2019","MATT")
        courseDao.addCourse("SDFGH", "Software Design", "800", "Spring2019", "MATT")
        List<String> after = courseDao.selectCourse("SDFGH","800","Spring2019","MATT")
        courseDao.addCourse("SDFGH", "Software Design", "800", "Spring2019", "MATT")
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")

        then:
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
        thrown Exception
    }

    def "fail add from crn as non-string"(){
        when:
        courseDao.addCourse(12345, "Software Design", "800", "Spring2019", "MATT")

        then:
        thrown Exception
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

    def "fail add from crn too long"() {
        when:
        courseDao.addCourse("SDFGHTTYW", "Software Design", "800", "Spring2019", "MATT")

        then:
        thrown Exception
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

    def "add with crn empty"() {
        when:
        List<String> before = courseDao.selectCourse("","800","Spring2019","MATT")
        courseDao.addCourse("", "Software Design", "800", "Spring2019", "MATT")
        List<String> after = courseDao.selectCourse("","800","Spring2019","MATT")

        then:
        before.size() == 0
        after.size() == 1
        //clean up the db
        courseDao.deleteCourse("", "800", "Spring2019","MATT")
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

    def "fail add from course name as non-string"(){
        when:
        courseDao.addCourse("SDFGH", 42, "800", "Spring2019", "MATT")
        List<String> result = courseDao.selectCourse("SDFGH","800","Spring2019","MATT")

        then:
        result == null
        thrown Exception
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

    def "fail add from course name too long"(){
        when:
        courseDao.addCourse("SDFGH", "\"Documentation is like sex. When it's good, "
                + "it's very good. When it's bad, it's better than nothing.\"" + "\"Documentation is like sex. When it's good, "
                + "it's very good. When it's bad, it's better than nothing.\""
                + "\"Documentation is like sex. When it's good, "
                + "it's very good. When it's bad, it's better than nothing.\""
                + "I saw this quote online and thought y'all would get a laugh. I hope this string is long enough now."
                , "800", "Spring2019", "MATT")
        List<String> result = courseDao.selectCourse("SDFGH","800","Spring2019","MATT")

        then:
        result == null
        thrown Exception
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

    def "add with empty course name"(){
        when:
        List<String> before = courseDao.selectCourse("SDFGH","800","Spring2019","MATT")
        courseDao.addCourse("SDFGH", "", "800", "Spring2019", "MATT")
        List<String> after = courseDao.selectCourse("SDFGH","800","Spring2019","MATT")

        then:
        before.size() == 0
        after.size() == 1
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

    def "section not string"(){
        when:
        courseDao.addCourse("SDFGH", "Software Design", 800, "Spring2019", "MATT")

        then:
        thrown Exception
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

    def "section too long"(){
        when:
        courseDao.addCourse("SDFGH", "Software Design", "80000000000", "Spring2019", "MATT")

        then:
        thrown Exception
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

    def "section is empty string"() {
        when:
        List<String> before = courseDao.selectCourse("SDFGH","","Spring2019","MATT")
        courseDao.addCourse("SDFGH", "Software Design", "", "Spring2019", "MATT")
        List<String> after = courseDao.selectCourse("SDFGH","","Spring2019","MATT")

        then:
        before.size() == 0
        after.size() == 1
        //clean up the db
        courseDao.deleteCourse("SDFGH", "", "Spring2019","MATT")
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

    def "semester is too long"() {
        when:
        courseDao.addCourse("SDFGH", "Software Design", "800", "Spring2019testing", "MATT")

        then:
        thrown Exception
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

    def "semester is not a string"() {
        when:
        courseDao.addCourse("SDFGH", "Software Design", "800", 2019, "MATT")

        then:
        thrown Exception
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

    def "semester is empty string"() {
        when:
        List<String> before = courseDao.selectCourse("SDFGH","800","","MATT")
        courseDao.addCourse("SDFGH", "Software Design", "800", "", "MATT")
        List<String> after = courseDao.selectCourse("SDFGH","800","","MATT")

        then:
        before.size() == 0
        after.size() == 1
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "","MATT")
    }

    def "instructor id not already in db"() {
        when:
        courseDao.addCourse("SDFGH", "Software Design", "800", "Spring2019", "DILBERT")

        then:
        thrown Exception
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

    def "instructor is empty string"() {
        when:
        List<String> before = courseDao.selectCourse("SDFGH","800","Spring2019","")
        courseDao.addCourse("SDFGH", "Software Design", "800", "Spring2019", "")
        List<String> after = courseDao.selectCourse("SDFGH","800","Spring2019","")

        then:
        before.size() == 0
        after.size() == 1
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","")
    }

    def "instructor is too long"() {
        when:
        courseDao.addCourse("SDFGH", "Software Design", "800", "Spring2019", "GOTTA MAKE A REALLY LONG STRING"
                +" TO TEST AN UNBELIABLY LONG INSTRUCTOR NAME WHICH WOULD NEVER COME CLOSE TO BEING LONG ENOUGH"
                + " FOR THIS TOO FAIL AT 200 CHARACTERS LONG. NEEDS ANOTHER 50ISH CHARACTERS..................."
                +"..............................................................................................")

        then:
        thrown Exception
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

    def "fail select a course from db from crn not a string"() {
        when:
        List<String> before = courseDao.selectCourse(6, "800", "Spring2019", "MATT")
        courseDao.addCourse("6", "Software Design", "800", "Spring2019", "MATT")
        List<String> after = courseDao.selectCourse(6, "800", "Spring2019", "MATT")
        then:
        thrown Exception
        //clean up the db
        courseDao.deleteCourse("6", "800", "Spring2019","MATT")
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

    def "fail select a course from db from section not a string"() {
        when:
        List<String> before = courseDao.selectCourse("SDFGH", 800, "Spring2019", "MATT")
        courseDao.addCourse("SDFGH", "Software Design", "800", "Spring2019", "MATT")
        List<String> after = courseDao.selectCourse("SDFGH", 800, "Spring2019", "MATT")

        then:
        thrown Exception
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

    def "fail select a course from db from semester not a string"() {
        when:
        List<String> before = courseDao.selectCourse("SDFGH", "800", 2019, "MATT")
        courseDao.addCourse("SDFGH", "Software Design", "800", "2019", "MATT")
        List<String> after = courseDao.selectCourse("SDFGH", "800", 2019, "MATT")
        then:
        thrown Exception
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

    def "fail select a course from db from instructor id not a string"() {
        when:
        List<String> before = courseDao.selectCourse("SDFGH", "800", "Spring2019", 1212121)
        courseDao.addCourse("SDFGH", "Software Design", "800", "Spring2019", "1212121")
        List<String> after = courseDao.selectCourse("SDFGH", "800", "Spring2019", 1212121)

        then:
        thrown Exception
        //clean up the db
        courseDao.deleteCourse("SDFGH", "800", "Spring2019","MATT")
    }

}