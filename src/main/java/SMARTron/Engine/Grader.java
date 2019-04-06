import java.util.ArrayList;
import java.util.List;

public class Grader {

    private List<Question> questions = new ArrayList<>();

    public List<Float> getGrades(List<Student> studentExams, Student key){
        List<Float> grades = new ArrayList<>();
        this.initializeStatsByQuestion(key.getAnswers());
        //studentExams.remove(0);
        int numPoints;

        for (Student studentExam : studentExams) {
            numPoints = 0;
            for (int j = 0; j < studentExam.getAnswers().size(); j++) {
                if (key.getAnswers().get(j).contains(studentExam.getAnswers().get(j)) && !key.getAnswers().get(j).contains("-1")) {
                    numPoints++;
                }
                questions.get(j).increment(studentExam.getAnswers().get(j));

            }
            grades.add((float) numPoints / key.getAnswers().size() * 100);
        }
        return grades;
    }

    private void initializeStatsByQuestion(List<String> key){
        for(int i = 0; i < key.size(); i++){
            questions.add(new Question());
        }
    }

    public List<Question> getStatsByQuestion(){
        return this.questions;
    }
}