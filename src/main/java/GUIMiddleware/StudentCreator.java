package GUIMiddleware;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentCreator {

    public Student makeStudent(String[][] answers, String[] StudentData) {
        List<String> answerTemp = new ArrayList<>();

        int pos = 0;

        for(int i = 0; i < answers[0].length; i++) {
            for(int j = 0; j < 10; j++) {
                if (j < answers.length) {
                    answerTemp.add(pos++, answers[j][i]);
                }
                if (j+10 < answers.length) {
                    answerTemp.add(answerTemp.size(), answers[j+10][i]);
                }
            }
        }


        List<String> StudentDataTemp = new ArrayList<>(Arrays.asList(StudentData));

        Student returnStudent = new Student();
        returnStudent.initializeStudentFromStudentData(StudentDataTemp);
        returnStudent.initializeAnswers(answerTemp);
        return returnStudent;
    }

    public Student makeStudent(String[][] answersFront, String[][] answersBack, String[] StudentData) {
        List<String> answerTempFront = new ArrayList<>();

        int pos = 0;

        for(int i = 0; i < answersFront[0].length; i++) {
            for(int j = 0; j < 10; j++) {
                if (j < answersFront.length) {
                    answerTempFront.add(pos++, answersFront[j][i]);
                }
                if (j+10 < answersFront.length) {
                    answerTempFront.add(answerTempFront.size(), answersFront[j+10][i]);
                }
            }
        }

        pos = 0;
        List<String> answerTempBack = new ArrayList<>();


        for(int i = 0; i < answersBack[0].length; i++) {
            for(int j = 0; j < 10; j++) {
                if (j < answersBack.length) {
                    answerTempBack.add(pos++, answersBack[j][i]);
                }
                if (j+10 < answersBack.length) {
                    answerTempBack.add(answerTempBack.size(), answersBack[j+10][i]);
                }
            }
        }

        List<String> answerTemp = new ArrayList<>();

        for (int i = 0; i < answerTempFront.size(); i++){
            answerTemp.add(answerTempFront.get(i));
        }
        for (int i = 0; i < answerTempBack.size(); i++){
            answerTemp.add(answerTempBack.get(i));
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
