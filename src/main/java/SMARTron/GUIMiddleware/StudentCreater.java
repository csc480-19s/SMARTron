package SMARTron.GUIMiddleware;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentCreater {

    public Student makeStudent(String[][] answers, String[] StudentData) {
        List<String> answerTemp = new ArrayList<>();

        int pos = 0;
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 10; j++) {
                answerTemp.add(pos++, answers[j][i]);
                answerTemp.add(answerTemp.size(), answers[j+10][i]);
            }
        }

        List<String> StudentDataTemp = new ArrayList<>(Arrays.asList(StudentData));

        Student returnStudent = new Student();
        returnStudent.initializeStudentFromStudentData(StudentDataTemp);
        returnStudent.initializeAnswers(answerTemp);
        return returnStudent;
    }

    //Takes a primitive multidimensional array and standard string array and converts them into a Student object
    //(for scanner)

    public Student makeSecondPassStudent(String studentData) {

        Student returnStudent = new Student();
        returnStudent.initializeStudentFromDatabaseString(studentData);

        return returnStudent;
    }

    //Takes a primitive string array and makes a Student object (from database)
}
