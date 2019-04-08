

public class TestDataFeeder implements OLDTESTDATA {

    ExamManager  examManager = new ExamManager();

    public void feed(){
        examManager.addStudentExam(key, key43);
        examManager.addStudentExam(stu, stu43);
        examManager.addStudentExam(stu2, stu243);
        examManager.addStudentExam(stu3, stu343);

        examManager.getGrades();
    }

    //not needed anymore, delete
}
