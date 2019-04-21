package GUIMiddleware;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.math.BigDecimal;
import java.util.Scanner;

public class Statistics {

    public static void main(String[] args) {

        /***************************************************************************************************************
         *Everything here in the main method is just stuff for generating random data to test the statistics over.      *
         *It waits for you to press enter to do stats calculations to verify that it's still calculating them instantly.*
         ***************************************************************************************************************/

        int examCount = 1000;
        int questionCount = 200;

        Scanner kb = new Scanner(System.in);

        List<List<String>> exams = new ArrayList<>();
        List<Short> scores;
        List<String> answerKey = examGenerator(questionCount);
        List<Byte> weight = weightGenerator(questionCount);

        int max = 0;
        for (byte c : weight) {
            max += ((int) c);
        }
        System.out.println("Exam high score is " + max);

        for (int i = 0; i < examCount; i++) {
            exams.add(examGenerator(questionCount));
        }

        System.out.println();
        scores = examGrader(exams, answerKey, weight);
        System.out.println("Ready to calculate statistics...");
        kb.nextLine();
        System.out.println("Lowest score: " + lowestScore(scores) + " Highest Score: " + highestScore(scores) + " Average: " + meanShort(scores) + " Median: " + median(scores) + " Range: " + rangeOfScores(scores));
        System.out.println("Cronbach's Alpha: " + cronbachsAlpha(exams, answerKey, weight, examCount));
    }

    static List<String> examGenerator(int questionCount) {
        String answerChoices = "ABCDE";
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
        Random r = new Random();
        List<String> exam = new ArrayList<>();
        String sex = "MF";

        for (int i = 0; i < 20; i++) {
            exam.add("" + alphabet.charAt(r.nextInt(alphabet.length())));
        }

        for (int i = 0; i < 10; i++) {
            exam.add("" + r.nextInt(10));
        }

        exam.add("" + r.nextInt(2));
        if ("1".equals("" + exam.get(30))) {
            exam.add("" + r.nextInt(3));
        } else {
            exam.add("" + r.nextInt(10));
        }
        exam.add("" + r.nextInt(4));
        if ("3".equals(exam.get(32) + "")) {
            exam.add("" + r.nextInt(2));
        } else {
            exam.add("" + r.nextInt(10));
        }
        for (int i = 0; i < 2; i++) {
            exam.add("" + r.nextInt(10));
        }

        for (int i = 0; i < 6; i++) {
            exam.add("" + r.nextInt(10));
        }

        exam.add("" + sex.charAt(r.nextInt(sex.length())));

        exam.add("" + r.nextInt(2));
        if ("1".equals("" + exam.get(43))) {
            exam.add("" + r.nextInt(7));
        } else {
            exam.add("" + r.nextInt(10));
        }

        for (int i = 0; i < questionCount; i++) {
            exam.add("" + answerChoices.charAt(r.nextInt(answerChoices.length())));
        }
        return exam;
    }  /** Generates a List of Strings that are exam data in the following form:
     *
     * Index 0-19: name, ex: "SIGZLD BDGNUFX FKGMN"
     * 20-29: ID #, "2367636071"
     * 30-35: Birthday, "020733"
     * 36-41: Special code, "442918"
     * 42: Sex, "M"
     * 43-44: Education level, "15"
     *
     * All characters after 44 are answer choices, currently stored as ABCDE, and the number
     * generated depends on the int entered for the parameter questionCount */

    static List<Short> examGrader(List<List<String>> exams, List<String> answerKey, List<Byte> weight) {
        List<Short> examScores = new ArrayList<>();
        for (int i = 0; i < exams.size(); i++) {
            short examScore = 0;
            for (int j = 45; j < answerKey.size(); j++) {
                if (exams.get(i).get(j).equals(answerKey.get(j))) {
                    examScore += weight.get(j - 45);
                }
            }
            examScores.add(examScore);
        }
        return examScores;
    }    /** Grades the list of exams using an answer key that is generated
     * exactly the same as any other randomly generated quiz.
     * The weight is a list of Bytes that affect the corresponding question
     * to be worth that many points */

    static List<Byte> weightGenerator(int questionCount) {
        Random r = new Random();
        List<Byte> weight = new ArrayList<>();
        for (int i = 0; i < questionCount; i++) {
            weight.add((byte)(r.nextInt(5) + 1));
        }
        return weight;
    } /** Generates a List of Bytes, size depends on the questionCount parameter. */

    static BigDecimal meanShort(List<Short> scores) {
        int total = 0;
        for (short score : scores) {
            total += score;
        }
        return BigDecimal.valueOf(total).divide(BigDecimal.valueOf(scores.size()));
    } /** Takes in a List of Shorts and calculates the mean, outputs a BigDecimal. */

    static BigDecimal meanDecimal(List<BigDecimal> scores) {
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal score : scores) {
            total = total.add(score);
        }
        return total.divide(BigDecimal.valueOf(scores.size()));
    } /** Takes in a List of BigDecimals and calculates the mean, outputs a BigDecimal */

    static BigDecimal meanByte(List<Byte> scores) {
        int total = 0;
        for (Byte score : scores) {
            total += score;
        }
        return BigDecimal.valueOf(total).divide(BigDecimal.valueOf(scores.size()),4, RoundingMode.HALF_UP);
    } /** Takes in a List of Bytes and calculates the mean, outputs a BigDecimal */

    static BigDecimal overallVariance(List<Short> scores) {
        BigDecimal mean = meanShort(scores);
        List<BigDecimal> squaredDifference = new ArrayList<>();
        for(Short score : scores) {
            squaredDifference.add(mean.subtract(BigDecimal.valueOf(score)).pow(2));
        }
        return meanDecimal(squaredDifference);
    } /** Takes in a List of Shorts and calculates the Variance of them, outputs a BigDecimal */

    static List<List<Byte>> gradesByQuestion(List<List<String>> exams, List<String> answerKey, List<Byte> weight) {
        List<List<Byte>> examGradesByQuestion = new ArrayList<>();
        for (int i = 0; i < exams.size(); i++) {
            List<Byte> examScore = new ArrayList<>();
            for (int j = 45; j < answerKey.size(); j++) {
                if (exams.get(i).get(j).equals(answerKey.get(j))) {
                    examScore.add(weight.get(j-45));
                } else {
                    examScore.add((byte)0);
                }
            }
            examGradesByQuestion.add(examScore);
        }
        return examGradesByQuestion;
    } /** Takes in a List of Lists of Strings (exams)
     * and a List of Strings (answer key)
     * and a List of Bytes (weight) and outputs
     * a corresponding List of Lists of Bytes for the
     * grades by question for every exam.
     * This is critical for Cronbach's Alpha to function correctly. */

    static List<BigDecimal> meanByQuestion(List<List<Byte>> gradesByQuestion) {
        List<BigDecimal> meanByQuestion = new ArrayList<>();
        for (int j = 0; j < gradesByQuestion.get(0).size(); j++) {
            List<Byte> questionScores = new ArrayList<>();
            for (int i = 0; i < gradesByQuestion.size(); i++) {
                questionScores.add(gradesByQuestion.get(i).get(j));
            }
            meanByQuestion.add(meanByte(questionScores));
        }
        return meanByQuestion;
    } /** Takes in a List of Lists of Bytes and calculates the mean grade for each question across multiple exams,
     * outputs a List of BigDecimals corresponding to the mean for that question.
     * This is a part of Chronbach's Alpha. */

    static List<List<BigDecimal>> differenceFromMean(List<List<Byte>> gradesByQuestion, List<BigDecimal> meanByQuestion) {
        List<List<BigDecimal>> differenceFromMean = new ArrayList<>();
        for (int i = 0; i < gradesByQuestion.size(); i++) {
            List<BigDecimal> examMeanDifferences = new ArrayList<>();
            for (int j = 0; j < gradesByQuestion.get(0).size(); j++) {
                examMeanDifferences.add(meanByQuestion.get(j).subtract(BigDecimal.valueOf(gradesByQuestion.get(i).get(j))));
            }
            differenceFromMean.add(examMeanDifferences);
        }
        return differenceFromMean;
    }  /** Takes in a List of Lists of Bytes (grades by question)
     * and a List of BigDecimals (mean by question) and
     * calculates the difference from the mean for each question on each exam across all exams.
     * outputs a List of Lists of BigDecimals corresponding to this. */

    static List<List<BigDecimal>> squaredData(List<List<BigDecimal>> input) {
        List<List<BigDecimal>> output = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            List<BigDecimal> tempList = new ArrayList<>();
            for (int j = 0; j < input.get(0).size(); j++) {
                tempList.add(input.get(i).get(j).pow(2));
            }
            output.add(tempList);
        }
        return output;
    } /** Takes in a List of Lists of BigDecimals and squares each BigDecimal. Outputs a List of Lists of BigDecimals corresponding to this. */

    static List<BigDecimal> additionOfData(List<List<BigDecimal>> input) {
        List<BigDecimal> output = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int j = 0; j < input.get(0).size(); j++) {
                sum = sum.add(input.get(i).get(j));
            }
            output.add(sum);
        }
        return output;
    }  /** Takes in a List of Lists of BigDecimals and adds all the children lists in a parent list together.
     * outputs the corresponding list */

    static List<BigDecimal> divisionOfData(List<BigDecimal> input, int divisor) {
        List<BigDecimal> output = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            output.add(input.get(i).divide(BigDecimal.valueOf(divisor)));
        }
        return output;
    }   /** Takes in a List of BigDecimals and divides each BigDecimal by the divisor.
     * outputs the corresponding list */

    static BigDecimal summationOfList(List<BigDecimal> input) {
        BigDecimal sum = BigDecimal.ZERO;
        for(int i = 0; i < input.size(); i++) {
            sum = sum.add(input.get(i));
        }
        return sum;
    } /** Takes in a List of BigDecimals and adds each BigDecimal together. Outputs the corresponding BigDecimal */

    static BigDecimal cronbachsAlpha(List<List<String>> exams, List<String> answerKey, List<Byte> weight, int examCount) {
        List<List<Byte>> gradesByQuestion = gradesByQuestion(exams, answerKey, weight);
        List<BigDecimal> meanByQuestion = meanByQuestion(gradesByQuestion);
        List<List<BigDecimal>> differencesFromMean = differenceFromMean(gradesByQuestion, meanByQuestion);
        List<List<BigDecimal>> squaredDifferencesFromMean = squaredData(differencesFromMean);
        List<BigDecimal> additionOfSDFTM = additionOfData(squaredDifferencesFromMean);
        List<BigDecimal> varianceIndividual = divisionOfData(additionOfSDFTM, examCount);
        BigDecimal sigmaVI = summationOfList(varianceIndividual);
        BigDecimal vTest = overallVariance(examGrader(exams, answerKey, weight));
        BigDecimal numberOfQuestions = BigDecimal.valueOf(exams.get(0).size());

        BigDecimal rightHand = BigDecimal.ONE.subtract(sigmaVI.divide(vTest, 128, RoundingMode.HALF_UP));
        BigDecimal leftHand = numberOfQuestions.divide(numberOfQuestions.subtract(BigDecimal.ONE), 128, RoundingMode.HALF_UP);

        return rightHand.multiply(leftHand).abs().round(MathContext.DECIMAL32);
    }  /** This method uses many other methods in this class to, in this crder:
     * Calculate the Grade on each question of each exam, using the exam data, the answer key, and a weight.
     * Calculate the mean grade on each question
     * Calculate each exam's question's difference from the mean
     * Square the Differences From The Mean
     * Add the SDFTM across questions together
     * Divide the added SDFTM by the total number of exams, which should be provided by the examCount parameter (Individual Variance)
     * Add the Individual Variance together (Sigma VI)
     * Calculate the Variance of the overall test scores (VTest)
     * Calculates the number of questions on the exam (n) and then performs n/(n-1) (Left hand side of Cronbach's Alpha equation)
     * Calculates 1-(SigmaVI/VTest) (Right hand side of Cronbach's Alpha equation
     * Multiplies the right hand and left hand sides together
     * Outputs the corresponding value */

    static int lowestScore(List<Short> scores) {
        int lowestScore = 200;
        for (int i = 0; i < scores.size(); i++) {
            if ((int)scores.get(i) < lowestScore) {
                lowestScore = scores.get(i);
            }
        }
        return lowestScore;
    } /** Takes in a List of Shorts, outputs the lowest value */

    static int highestScore(List<Short> scores) {
        int highestScore = 0;
        for (int i = 0; i < scores.size(); i++) {
            if ((int)scores.get(i) > highestScore) {
                highestScore = scores.get(i);
            }
        }
        return highestScore;
    } /** Takes in a List of Shorts, outputs the highest value */

    static int median(List<Short> scores) {
        return scores.get(scores.size()/2);
    } /** Takes in a List of Shorts, outputs the Median value */

    static int rangeOfScores(List<Short> scores) {
        return highestScore(scores) - lowestScore(scores);
    } /** Takes in a List of Shorts, outputs the range of scores */

}
