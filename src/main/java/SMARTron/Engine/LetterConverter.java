import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LetterConverter {

    private float aboveForA = 93;
    private float aboveForB = 83;
    private float aboveForC = 73;
    private float aboveForD = 63;

    public LetterConverter(){

    }

    public List<String> genLetterGrade(List<Float> examGrade) {

        List<String> returnLetterGrades = new ArrayList<>();

        for (Float i : examGrade) {

            if (i >= aboveForA && i <= 100.0) {
                returnLetterGrades.add("A");
                //} else if (examGrade >= 90) {
                //return "A-";
                //} else if (examGrade >= 87) {
                //return "B+";
            } else if (aboveForB >= i) {
                returnLetterGrades.add("B");
                //} else if (examGrade >= 80) {
                //return "B-";
                //} else if (examGrade >= 77) {
                //return "C+";
            } else if (aboveForC >= i) {
                returnLetterGrades.add("C");
                //} else if (examGrade >= 70) {
                //return "C-";
                //} else if (examGrade >= 67) {
                //return "D+";
            } else if (aboveForD >= i) {
                returnLetterGrades.add("D");
                //} else if (examGrade >= 60) {
                //return "D-";
            } else {
                returnLetterGrades.add("F");;
            }
        }

        return returnLetterGrades;
    }

    public void letterDistribution(String distribution){
        Scanner distroScanner = new Scanner(distribution);
        distroScanner.useDelimiter(",");

        this.aboveForA = Integer.parseInt(distroScanner.next());
        this.aboveForB = Integer.parseInt(distroScanner.next());
        this.aboveForC = Integer.parseInt(distroScanner.next());
        this.aboveForD = Integer.parseInt(distroScanner.next());
    }

}
