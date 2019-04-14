package SMARTron.GUIMiddleware;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LetterConverter {

    private float aboveForA = 93;
    private float aboveForB = 83;
    private float aboveForC = 73;
    private float aboveForD = 63;

     //*These four variables are used for assigning letter grades
     //*This is the default scheme for how they are assigned.

    public List<String> genLetterGrade(List<Float> examGrade) {

        List<String> returnLetterGrades = new ArrayList<>();

        //list of all the letter grades, this will be returned

        for (Float i : examGrade) {

            if (i >= aboveForA && i <= 100.0) {
                returnLetterGrades.add("A");
            } else if (aboveForB >= i) {
                returnLetterGrades.add("B");
            } else if (aboveForC >= i) {
                returnLetterGrades.add("C");
            } else if (aboveForD >= i) {
                returnLetterGrades.add("D");
            } else {
                returnLetterGrades.add("F");;
            }

            //finds the letter assigned with the grade, and adds it to returnLetterGrades
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

    //This method accepts a String "distribution", and parces through it.
    //It will then assign the class variables to the Integer.parseInt() of that section of the parced String
}
