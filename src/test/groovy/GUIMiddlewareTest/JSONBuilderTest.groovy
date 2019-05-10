package GUIMiddlewareTest

import GUIMiddleware.Question
import GUIMiddleware.Stats
import GUIMiddleware.Student
import org.json.simple.parser.JSONParser
import spock.lang.Specification
import GUIMiddleware.*
import org.json.simple.*

class JSONBuilderTest extends Specification {
    JSONBuilder builder = new JSONBuilder()
    Student stu = new Student()

    def "successful resetAllJSON"() {
        when:
        builder.resetAllJSON()
        FileReader fr = null
        try{
            fr = new FileReader("AnswerKey.txt")
        }
        catch (FileNotFoundException fe){
            System.out.println("File not found")
        }
        char[] chars = new char[18]
        fr.read(chars)
        fr.close()
        String s1 = new String(chars)
        try{
            fr = new FileReader("Bystudent.txt")
        }
        catch (FileNotFoundException fe){
            System.out.println("File not found")
        }
        chars = new char[18]
        fr.read(chars)
        fr.close()
        String s2 = new String(chars)
        try{
            fr = new FileReader("Byquestion.txt")
        }
        catch (FileNotFoundException fe){
            System.out.println("File not found")
        }
        chars = new char[18]
        fr.read(chars)
        fr.close()
        String s3 = new String(chars)
        try{
            fr = new FileReader("Stats.txt")
        }
        catch (FileNotFoundException fe){
            System.out.println("File not found")
        }
        chars = new char[18]
        fr.read(chars)
        fr.close()
        String s4 = new String(chars)

        then:
        s1.equals("Nothing to display")
        s2.equals("Nothing to display")
        s3.equals("Nothing to display")
        s4.equals("Nothing to display")
    }

    def "successful buildMainpageJSON"() {
        when:
        builder.buildMainpageJSON()
        FileReader fr = null
        try{
            fr = new FileReader("Mainpage.txt")
        }
        catch (FileNotFoundException fe){
            System.out.println("File not found")
        }
        char[] chars = new char[1]
        fr.read(chars)
        fr.close()
        String s1 = chars.toString()

        then:
        s1.equals("")
    }

    def "successful buildAnswerKeyJSON"() {
        when:
        stu.initializeStudentFromDatabaseString("Dilbert,M,87,01/23/19,TTYSD,CFGHW,4,3,3,3,4,1,2,0,0,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1")
        builder.setExamCode("Code")
        builder.setExamName("name")
        builder.buildAnswerKeyJSON(stu)
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream("AnswerKey.txt")))
        String id = json.get("examId")
        String name = json.get("examName")
        JSONArray ans = json.get("answersList")
        int s = ans.size()

        then:
        id.equals("Code")
        name.equals("name")
        s == 25
    }

    def "fail buildAnswerKeyJSON"() {
        when:
        builder.setExamCode("Code")
        builder.setExamName("name")
        builder.buildAnswerKeyJSON("String meant to break it")
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream("AnswerKey.txt")))
        String id = json.get("examId")
        String name = json.get("examName")
        JSONArray ans = json.get("answersList")
        int s = ans.size()

        then:
        thrown Exception
    }

    def "successful buildByStudentJSON"() {
        when:
        List<Student> list = new ArrayList()
        stu.initializeStudentFromDatabaseString("Dilbert,M,87,01/23/19,TTYSD,CFGHW,4,3,3,3,4,1,2,0,0,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1")
        list.add(stu)
        Student stud = new Student()
        stud.initializeStudentFromDatabaseString("Kevin,M,87,01/23/19,TTYSD,CFGHW,4,3,3,3,4,1,2,0,0,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1")
        list.add(stud)
        Student stude = new Student()
        stude.initializeStudentFromDatabaseString("Steve,M,87,01/23/19,TTYSD,CFGHW,4,3,3,3,4,1,2,0,0,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1")
        list.add(stude)
        builder.setExamCode("Code")
        builder.setExamName("name")
        builder.buildByStudentJSON(list)

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream("Bystudent.txt")))
        JSONArray ans = json.get("students")
        int s = ans.size()

        then:
        s == 3
    }

    def "fail buildByStudentJSON"() {
        when:
        builder.setExamCode("Code")
        builder.setExamName("name")
        builder.buildByStudentJSON(3)

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream("Bystudent.txt")))
        JSONArray ans = json.get("students")
        int s = ans.size()

        then:
        thrown Exception
    }

    def "successful buildByQuestion"() {
        when:
        builder.setExamCode("Code")
        builder.setExamName("name")
        Question q1 = new Question()
        q1.increment('0');q1.increment('0');q1.increment('0');q1.increment('0');q1.increment('3')
        Question q2 = new Question()
        q2.increment('1');q2.increment('3');q2.increment('0');q2.increment('4');q2.increment('3')
        Question q3 = new Question()
        q3.increment('2');q3.increment('2');q3.increment('0');q3.increment('4');q3.increment('3')
        List<Question> list = new ArrayList()
        list.add(q1);list.add(q2);list.add(q3);
        builder.buildByQuestion(list, 3)

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream("Byquestion.txt")))
        String id = json.get("examId")
        String name = json.get("examName")
        JSONArray ans = json.get("questionsList")
        int s = ans.size()

        then:
        id.equals("Code")
        name.equals("name")
        s == 3
    }

    def "fail buildByQuestion because int is larger than array size"() {
        when:
        builder.setExamCode("Code")
        builder.setExamName("name")
        Question q1 = new Question()
        q1.increment('0');q1.increment('0');q1.increment('0');q1.increment('0');q1.increment('3')
        Question q2 = new Question()
        q2.increment('1');q2.increment('3');q2.increment('0');q2.increment('4');q2.increment('3')
        Question q3 = new Question()
        q3.increment('2');q3.increment('2');q3.increment('0');q3.increment('4');q3.increment('3')
        List<Question> list = new ArrayList()
        list.add(q1);list.add(q2);list.add(q3);
        builder.buildByQuestion(list, 4)

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream("Byquestion.txt")))
        String id = json.get("examId")
        String name = json.get("examName")
        JSONArray ans = json.get("questionsList")
        int s = ans.size()

        then:
        thrown Exception
    }

    def "successful buildByQuestion while reading less than the full array"() {
        when:
        builder.setExamCode("Code")
        builder.setExamName("name")
        Question q1 = new Question()
        q1.increment('0');q1.increment('0');q1.increment('0');q1.increment('0');q1.increment('3')
        Question q2 = new Question()
        q2.increment('1');q2.increment('3');q2.increment('0');q2.increment('4');q2.increment('3')
        Question q3 = new Question()
        q3.increment('2');q3.increment('2');q3.increment('0');q3.increment('4');q3.increment('3')
        List<Question> list = new ArrayList()
        list.add(q1);list.add(q2);list.add(q3);
        builder.buildByQuestion(list, 2)

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream("Byquestion.txt")))
        String id = json.get("examId")
        String name = json.get("examName")
        JSONArray ans = json.get("questionsList")
        int s = ans.size()

        then:
        id.equals("Code")
        name.equals("name")
        s == 2
    }

    def "successful buildByQuestion while reading 0 questions in array"() {
        when:
        builder.setExamCode("Code")
        builder.setExamName("name")
        Question q1 = new Question()
        q1.increment('0');q1.increment('0');q1.increment('0');q1.increment('0');q1.increment('3')
        Question q2 = new Question()
        q2.increment('1');q2.increment('3');q2.increment('0');q2.increment('4');q2.increment('3')
        Question q3 = new Question()
        q3.increment('2');q3.increment('2');q3.increment('0');q3.increment('4');q3.increment('3')
        List<Question> list = new ArrayList()
        list.add(q1);list.add(q2);list.add(q3);
        builder.buildByQuestion(list, 0)

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream("Byquestion.txt")))
        String id = json.get("examId")
        String name = json.get("examName")
        JSONArray ans = json.get("questionsList")
        int s = ans.size()

        then:
        id.equals("Code")
        name.equals("name")
        s == 0
    }

    def "fail buildByQuestion from first param not a list of questions"() {
        when:
        builder.setExamCode("Code")
        builder.setExamName("name")
        Question q1 = new Question()
        q1.increment('0');q1.increment('0');q1.increment('0');q1.increment('0');q1.increment('3')
        builder.buildByQuestion(q1, 3)

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream("Byquestion.txt")))
        String id = json.get("examId")
        String name = json.get("examName")
        JSONArray ans = json.get("questionsList")
        int s = ans.size()

        then:
        thrown Exception
    }

    def "fail buildByQuestion from second parameter not an int"() {
        when:
        builder.setExamCode("Code")
        builder.setExamName("name")
        Question q1 = new Question()
        q1.increment('0');q1.increment('0');q1.increment('0');q1.increment('0');q1.increment('3')
        Question q2 = new Question()
        q2.increment('1');q2.increment('3');q2.increment('0');q2.increment('4');q2.increment('3')
        Question q3 = new Question()
        q3.increment('2');q3.increment('2');q3.increment('0');q3.increment('4');q3.increment('3')
        List<Question> list = new ArrayList()
        list.add(q1);list.add(q2);list.add(q3);
        builder.buildByQuestion(list, "3")

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream("Byquestion.txt")))
        String id = json.get("examId")
        String name = json.get("examName")
        JSONArray ans = json.get("questionsList")
        int s = ans.size()

        then:
        thrown Exception
    }

    def "successful buildStatsJSON"() {
        when:
        List<Student> list = new ArrayList()
        stu.initializeStudentFromDatabaseString("Dilbert,M,87,01/23/19,TTYSD,CFGHW,4,3,3,3,4,1,2,0,0,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1")
        list.add(stu)
        Student stud = new Student()
        stud.initializeStudentFromDatabaseString("Kevin,M,87,01/23/19,TTYSD,CFGHW,4,3,3,3,4,1,2,0,0,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1")
        list.add(stud)
        Student stude = new Student()
        stude.initializeStudentFromDatabaseString("Steve,M,87,01/23/19,TTYSD,CFGHW,4,3,3,3,4,1,2,0,0,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1")
        list.add(stude)
        builder.setExamCode("Code")
        builder.setExamName("name")
        Stats stats = Stub(Stats.class)
        stats.getMean() >> "77"
        stats.getMedian() >> "79"
        stats.getMax() >> "97"
        stats.getMin() >> "54"
        stats.getRange() >> "43"
        stats.getVariance() >> "7"
        stats.getKr20() >> "7"
        stats.getKr21() >> "7"
        stats.getCronbach() >> "7"
        List<List<String>> frequency = new ArrayList<>();
        frequency.add(new ArrayList<String>());frequency.add(new ArrayList<String>());frequency.add(new ArrayList<String>());frequency.add(new ArrayList<String>())
        stats.getFrequency() >> frequency
        List<String> percentiles = new ArrayList<>();
        percentiles.add("67");percentiles.add("77");percentiles.add("87")
        stats.getPercentiles() >> percentiles
        stats.getDeviation() >> "7"
        builder.buildStatsJSON(stats,list)

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream("Stats.txt")))
        JSONArray ans = json.get("byLetterGrade")
        int s = ans.size()
        String id = json.get("examID")
        String name = json.get("examName")
        String min = json.get("min")
        String max = json.get("max")
        String mean = json.get("mean")
        String range = json.get("range")
        String median = json.get("median")
        String variance = json.get("variance")
        String stdev = json.get("deviation")
        String kr20 = json.get("kr20")
        String kr21 = json.get("kr21")
        String cron = json.get("cronbach")
        List<List<String>> freq = json.get("questionFrequency")
        List<String> percent = json.get("percentile")

        then:
        s == 6
        id.equals("Code")
        name.equals("name")
        min.equals("54")
        max.equals("97")
        mean.equals("77")
        range.equals("43")
        median.equals("79")
        variance.equals("7")
        stdev.equals("7")
        kr20.equals("7")
        kr21.equals("7")
        cron.equals("7")
        freq.size() == 4
        percent.size() == 3

    }

    def "fail buildStatsJSON from first param as a string"() {
        when:
        List<Student> list = new ArrayList()
        stu.initializeStudentFromDatabaseString("Dilbert,M,87,01/23/19,TTYSD,CFGHW,4,3,3,3,4,1,2,0,0,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1")
        list.add(stu)
        Student stud = new Student()
        stud.initializeStudentFromDatabaseString("Kevin,M,87,01/23/19,TTYSD,CFGHW,4,3,3,3,4,1,2,0,0,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1")
        list.add(stud)
        Student stude = new Student()
        stude.initializeStudentFromDatabaseString("Steve,M,87,01/23/19,TTYSD,CFGHW,4,3,3,3,4,1,2,0,0,2,0,1,2,1,3,2,0,3,3,2,0,1,2,3,1")
        list.add(stude)
        builder.setExamCode("Code")
        builder.setExamName("name")
        builder.buildStatsJSON("Stats 1,2,3,5,32,1,4,4",list)

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream("Stats.txt")))
        JSONArray ans = json.get("byLetterGrade")
        int s = ans.size()
        String id = json.get("examID")
        String name = json.get("examName")
        String min = json.get("min")
        String max = json.get("max")
        String mean = json.get("mean")
        String range = json.get("range")
        String median = json.get("median")
        String variance = json.get("variance")
        String stdev = json.get("deviation")
        String kr20 = json.get("kr20")
        String kr21 = json.get("kr21")
        String cron = json.get("cronbach")
        List<List<String>> freq = json.get("questionFrequency")
        List<String> percent = json.get("percentile")

        then:
        thrown Exception
    }

    def "fail buildStatsJSON from second param"() {
        when:
        String list = "CSV,List,Instead"
        builder.setExamCode("Code")
        builder.setExamName("name")
        Stats stats = Stub(Stats.class)
        stats.getMean() >> "77"
        stats.getMedian() >> "79"
        stats.getMax() >> "97"
        stats.getMin() >> "54"
        stats.getRange() >> "43"
        stats.getVariance() >> "7"
        stats.getKr20() >> "7"
        stats.getKr21() >> "7"
        stats.getCronbach() >> "7"
        List<List<String>> frequency = new ArrayList<>();
        frequency.add(new ArrayList<String>());frequency.add(new ArrayList<String>());frequency.add(new ArrayList<String>());frequency.add(new ArrayList<String>())
        stats.getFrequency() >> frequency
        List<String> percentiles = new ArrayList<>();
        percentiles.add("67");percentiles.add("77");percentiles.add("87")
        stats.getPercentiles() >> percentiles
        stats.getDeviation() >> "7"
        builder.buildStatsJSON(stats,list)

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream("Stats.txt")))
        JSONArray ans = json.get("byLetterGrade")
        int s = ans.size()
        String id = json.get("examID")
        String name = json.get("examName")
        String min = json.get("min")
        String max = json.get("max")
        String mean = json.get("mean")
        String range = json.get("range")
        String median = json.get("median")
        String variance = json.get("variance")
        String stdev = json.get("deviation")
        String kr20 = json.get("kr20")
        String kr21 = json.get("kr21")
        String cron = json.get("cronbach")
        List<List<String>> freq = json.get("questionFrequency")
        List<String> percent = json.get("percentile")

        then:
        thrown Exception
    }
}
