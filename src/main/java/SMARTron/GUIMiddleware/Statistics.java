package SMARTron.GUIMiddleware;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.math.BigDecimal;
import java.util.Scanner;

public class Statistics {

    public static void main(String[] args) {
        
          /*********************************************************************************************************
          *Everything here in the main method is just stuff for generating random data to test the statistics over.*
          *********************************************************************************************************/

        int examCount = 1000;
        int questionCount = 200;

        List<List<String>> exams = new ArrayList<>();
        List<Integer> scores;
        List<String> answerKey = examGenerator(questionCount);
        List<Integer> weight = weightGenerator(questionCount);

        int max = 0;
        for (Integer c : weight) {
            max += (c);
        }
        System.out.println("Exam max score is " + max);

        for (int i = 0; i < examCount; i++) {
            exams.add(examGenerator(questionCount));
        }

        scores = examGrader(exams, answerKey, weight);
        List<List<Integer>> questionFrequency = questionFrequency(exams);
        List<BigDecimal> percentiles = percentiles(scores);

        System.out.println("Lowest score: " + lowestScore(scores) + " Highest Score: " + highestScore(scores) + " Average: " + meanInteger(scores).round(MathContext.DECIMAL32) + " Median: " + median(scores) + " Range: " + rangeOfScores(scores));
        System.out.println("Cronbach's Alpha: " + cronbachsAlpha(exams, answerKey, weight).round(MathContext.DECIMAL32));
        System.out.println("KR-21: " + kuderRichardson21(exams, answerKey, weight).round(MathContext.DECIMAL32));
        System.out.println("KR-20: " + kuderRichardson20(exams, answerKey, weight).round(MathContext.DECIMAL32));
        System.out.println("Standard Deviation: " + standardDeviation(scores));

        int testQuestion = 22; //remember index 0-199 means questions 1-200
        int testChoice = 2; //remember index 0-4 means choices a-e
        System.out.println("Question " + testQuestion + " answer " + testChoice + " frequency: " + questionFrequency.get(testQuestion).get(testChoice));

        System.out.println("Exam 1 percentile: " + percentiles.get(0).round(MathContext.DECIMAL32));
        System.out.println("Exam 1 score: " + scores.get(0));

/*
        System.out.println();
        for(String a : answerKey) {
            System.out.print(a);
        }
        System.out.println();
        for(List<String> a : exams) {
            for(String b : a) {
                System.out.print(b);
            }
            System.out.println();
        }
*/
    }

    static List<String> examGenerator(int questionCount) {
        String answerChoices = "01234";
        Random r = new Random();
        List<String> exam = new ArrayList<>();

        /*
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
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

        //This is all stuff for generating the user data, such as the person name, DOB, etc.
        //It is only here for testing runtime stuff

        */
        for (int i = 0; i < questionCount; i++) {
            exam.add("" + answerChoices.charAt(r.nextInt(answerChoices.length())));
        }
        return exam;
    }
    /* Generates a List of Strings that are exam data in the following form:                             \
     *                                                                                                    \
     * Index 0-19: name, ex: "SIGZLD BDGNUFX FKGMN"                                                        \
     * 20-29: ID #, "2367636071"                                                                            \
     * 30-35: Birthday, "020733"                                                                             \
     * 36-41: Special code, "442918"                                                                          Deprecated
     * 42: Sex, "M"                                                                                          /
     * 43-44: Education level, "15"                                                                         /
     *                                                                                                     /
     * All characters after 44 are answer choices, currently stored                                       /
     * as 01234, and the number generated depends on the int entered for the parameter questionCount     /
     *
     * Generates a List of Strings that are exam data in the form of 01234, corresponding to A, B, C, D, and E.
     * The number generated depends on the int entered for the parameter questionCount
     * */

    static List<Integer> examGrader(List<List<String>> exams, List<String> answerKey, List<Integer> weight) {
        List<Integer> examScores = new ArrayList<>();
        for (int i = 0; i < exams.size(); i++) {
            int examScore = 0;
            for (int j = 0; j < answerKey.size(); j++) {
                if (exams.get(i).get(j).equals(answerKey.get(j))) {
                    examScore += weight.get(j);
                }
            }
            examScores.add(examScore);
        }
        return examScores;
    }
    /* Grades the list of exams using an answer key that is generated
     * exactly the same as any other randomly generated quiz.
     * The weight is a list of Bytes that affect the corresponding question
     * to be worth that many points */

    static List<Integer> weightGenerator(int questionCount) {
        Random r = new Random();
        List<Integer> weight = new ArrayList<>();
        for (int i = 0; i < questionCount; i++) {
            //weight.add((r.nextInt(2) + 2));
            weight.add(1);
        }
        return weight;
    }
    /* Generates a List of Bytes, size depends on the questionCount parameter. */

    static BigDecimal meanDecimal(List<BigDecimal> scores) {
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal score : scores) {
            total = total.add(score);
        }
        return total.divide(BigDecimal.valueOf(scores.size()), 128, RoundingMode.HALF_UP);
    }
    /* Takes in a List of BigDecimals and calculates the mean, outputs a BigDecimal */

    static BigDecimal meanInteger(List<Integer> scores) {
        int total = 0;
        for (Integer score : scores) {
            total += score;
        }
        return BigDecimal.valueOf(total).divide(BigDecimal.valueOf(scores.size()),128, RoundingMode.HALF_UP);
    }
    /* Takes in a List of Integers and calculates the mean, outputs a BigDecimal */

    static BigDecimal overallVariance(List<Integer> scores) {
        BigDecimal mean = meanInteger(scores);
        List<BigDecimal> squaredDifference = new ArrayList<>();
        for(Integer score : scores) {
            squaredDifference.add(mean.subtract(BigDecimal.valueOf(score)).pow(2));
        }
        return meanDecimal(squaredDifference);
    }
    /* Takes in a List of Integers and calculates the Variance of them, outputs a BigDecimal */

    static List<List<Integer>> gradesByQuestion(List<List<String>> exams, List<String> answerKey, List<Integer> weight) {
        List<List<Integer>> examGradesByQuestion = new ArrayList<>();
        for (int i = 0; i < exams.size(); i++) {
            List<Integer> examScore = new ArrayList<>();
            for (int j = 0; j < answerKey.size(); j++) {
                if (exams.get(i).get(j).equals(answerKey.get(j))) {
                    examScore.add(weight.get(j));
                } else {
                    examScore.add(0);
                }
            }
            examGradesByQuestion.add(examScore);
        }
        return examGradesByQuestion;
    }
    /* Takes in a List of Lists of Strings (exams)
     * and a List of Strings (answer key)
     * and a List of Bytes (weight) and outputs
     * a corresponding List of Lists of Bytes for the
     * grades by question for every exam.
     * This is critical for Cronbach's Alpha to function correctly. */

    static List<BigDecimal> meanByQuestion(List<List<Integer>> gradesByQuestion) {
        List<BigDecimal> meanByQuestion = new ArrayList<>();
        for (int j = 0; j < gradesByQuestion.get(0).size(); j++) {
            List<Integer> questionScores = new ArrayList<>();
            for (int i = 0; i < gradesByQuestion.size(); i++) {
                questionScores.add(gradesByQuestion.get(i).get(j));
            }
            meanByQuestion.add(meanInteger(questionScores));
        }
        return meanByQuestion;
    }
    /* Takes in a List of Lists of Bytes and calculates the mean grade for each question across multiple exams,
     * outputs a List of BigDecimals corresponding to the mean for that question.
     * This is another critical part of Cronbach's Alpha. */

    static List<List<BigDecimal>> differenceFromMean(List<List<Integer>> gradesByQuestion, List<BigDecimal> meanByQuestion) {
        List<List<BigDecimal>> differenceFromMean = new ArrayList<>();
        for (int i = 0; i < gradesByQuestion.size(); i++) {
            List<BigDecimal> examMeanDifferences = new ArrayList<>();
            for (int j = 0; j < gradesByQuestion.get(0).size(); j++) {
                examMeanDifferences.add(meanByQuestion.get(j).subtract(BigDecimal.valueOf(gradesByQuestion.get(i).get(j))));
            }
            differenceFromMean.add(examMeanDifferences);
        }
        return differenceFromMean;
    }
    /* Takes in a List of Lists of Bytes (grades by question)
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
    }
    /* Takes in a List of Lists of BigDecimals and squares each BigDecimal. Outputs a List of Lists of BigDecimals
     * corresponding to this. */

    static List<BigDecimal> additionOfData(List<List<BigDecimal>> input) {
        List<BigDecimal> output = new ArrayList<>();
        for (int i = 0; i < input.get(0).size(); i++) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int j = 0; j < input.size(); j++) {
                sum = sum.add(input.get(j).get(i));
            }
            output.add(sum);
        }
        return output;
    }
    /* Takes in a List of Lists of BigDecimals and adds all the children lists in a parent list together.
     * outputs the corresponding list */

    static List<BigDecimal> divisionOfData(List<BigDecimal> input, int divisor) {
        List<BigDecimal> output = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            output.add(input.get(i).divide(BigDecimal.valueOf(divisor), 128, RoundingMode.HALF_UP));
        }
        return output;
    }
    /* Takes in a List of BigDecimals and divides each BigDecimal by the divisor.
     * outputs the corresponding list */

    static BigDecimal summationOfList(List<BigDecimal> input) {
        BigDecimal sum = BigDecimal.ZERO;
        for(int i = 0; i < input.size(); i++) {
            sum = sum.add(input.get(i));
        }
        return sum;
    }
    /* Takes in a List of BigDecimals and adds each BigDecimal together. Outputs the corresponding BigDecimal */

    static BigDecimal cronbachsAlpha(List<List<String>> exams, List<String> answerKey, List<Integer> weight) {
        List<List<Integer>> gradesByQuestion = gradesByQuestion(exams, answerKey, weight);
        List<BigDecimal> meanByQuestion = meanByQuestion(gradesByQuestion);
        List<List<BigDecimal>> differencesFromMean = differenceFromMean(gradesByQuestion, meanByQuestion);
        List<List<BigDecimal>> squaredDifferencesFromMean = squaredData(differencesFromMean);
        List<BigDecimal> additionOfSDFTM = additionOfData(squaredDifferencesFromMean);
        List<BigDecimal> varianceIndividual = divisionOfData(additionOfSDFTM, exams.size());

        BigDecimal sigmaVI = summationOfList(varianceIndividual);
        BigDecimal vTest = overallVariance(examGrader(exams, answerKey, weight));
        BigDecimal numberOfQuestions = BigDecimal.valueOf(exams.get(0).size());

        try {
            BigDecimal rightHand = BigDecimal.ONE.subtract((sigmaVI.divide(vTest, 128, RoundingMode.HALF_UP)));
            BigDecimal leftHand = numberOfQuestions.divide((numberOfQuestions.subtract(BigDecimal.ONE)), 128, RoundingMode.HALF_UP);
            return rightHand.multiply(leftHand);

        } catch (ArithmeticException e) {
            return BigDecimal.ONE;
        }
    }
    /* This method uses many other methods in this class to, in this order:
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
     * Outputs the corresponding value
     *
     * Should be between 0 and 1 but negative values are possible when your exam is complete nonsense like my random exam data generator.*/

    static int lowestScore(List<Integer> scores) {
        int lowestScore = 200;
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i) < lowestScore) {
                lowestScore = scores.get(i);
            }
        }
        return lowestScore;
    }
    /* Takes in a List of Integer, outputs the lowest value */

    static int highestScore(List<Integer> scores) {
        int highestScore = 0;
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i) > highestScore) {
                highestScore = scores.get(i);
            }
        }
        return highestScore;
    }
    /* Takes in a List of Integers, outputs the highest value */

    static int median(List<Integer> scores) {
        return scores.get(scores.size()/2);
    }
    /* Takes in a List of Integers, outputs the Median value */

    static int rangeOfScores(List<Integer> scores) {
        return highestScore(scores) - lowestScore(scores);
    }
    /* Takes in a List of Integers, outputs the range of scores */

    static List<BigDecimal> proportionPassingByQuestion(List<List<String>> exams, List<String> answerKey, List<Integer> weight) {
        List<List<Integer>> gradesByQuestion = gradesByQuestion(exams, answerKey, weight);
        List<BigDecimal> proportionPassingByQuestion = new ArrayList<>();

        for (int i = 0; i < gradesByQuestion.get(0).size(); i++) {
            BigDecimal currentProportion = BigDecimal.ZERO;
            for (int j = 0; j < gradesByQuestion.size(); j++) {
                if (gradesByQuestion.get(j).get(i) != 0) {
                    currentProportion = (currentProportion.add(BigDecimal.ONE));
                }
            }
            proportionPassingByQuestion.add(currentProportion.divide(BigDecimal.valueOf(exams.size()), 128, RoundingMode.HALF_UP));
        }
        return proportionPassingByQuestion;
    }
    /* Tales in the List of Lists of Strings for the exams and the List of Strings for the answer key and the
     * List of Bytes for the weight and outputs a List of BigDecimals corresponding to the proportion of exams where
     * that question was answered correctly */

    static List<BigDecimal> proportionFailingByQuestion(List<List<String>> exams, List<String> answerKey, List<Integer> weight) {
        List<List<Integer>> gradesByQuestion = gradesByQuestion(exams, answerKey, weight);
        List<BigDecimal> proportionFailingByQuestion = new ArrayList<>();
        for (int i = 0; i < gradesByQuestion.get(0).size(); i++) {
            BigDecimal currentProportion = BigDecimal.ZERO;
            for (int j = 0; j < gradesByQuestion.size(); j++) {
                if (gradesByQuestion.get(j).get(i) == 0)  {
                    currentProportion = (currentProportion.add(BigDecimal.ONE));
                }
            }
            proportionFailingByQuestion.add((currentProportion.divide((BigDecimal.valueOf(exams.size())), 128, RoundingMode.HALF_UP)));
        }
        return proportionFailingByQuestion;
    }
    /* Tales in the List of Lists of Strings for the exams and the List of Strings for the answer key and the
     * List of Bytes for the weight and outputs a List of BigDecimals corresponding to the proportion of exams where
     * that question was answered incorrectly */

    static BigDecimal kuderRichardson21 (List<List<String>> exams, List<String> answerKey, List<Integer> weight) {
        BigDecimal numberOfQuestions = BigDecimal.valueOf(exams.get(0).size());
        BigDecimal overallVariance = overallVariance(examGrader(exams, answerKey, weight));
        BigDecimal meanScore = meanInteger(examGrader(exams, answerKey, weight));
        BigDecimal meanWeight = meanInteger(weight);
        
        try {
            BigDecimal leftHand = numberOfQuestions.divide(numberOfQuestions.subtract(BigDecimal.ONE), 128, RoundingMode.HALF_UP);
            BigDecimal rightHand = BigDecimal.ONE.subtract((meanScore.multiply((numberOfQuestions.subtract((meanScore)))).divide((numberOfQuestions.multiply((overallVariance))), 128, RoundingMode.HALF_UP)));
            return ((leftHand.multiply(rightHand, MathContext.UNLIMITED)).divide(meanWeight, 128, RoundingMode.HALF_UP));

        } catch (ArithmeticException e) {
            return BigDecimal.ONE;
        }
    }
    /* This method does, in this order:
     * Calculates the number of questions (n)
     * Calculates the overall variance of test scores (var)
     * Calculates the mean of the test scores (M)
     * Calculates n/(n-1), called leftHand
     * Calculates 1-(M*(n-M)/(n*Var)), called rightHand
     * returns leftHand * rightHand
     *
     * Note that this statistic is not completely accurate unless the exam is entirely 1 point questions
     * Should be a value between 0 and 1 but can be negative when fed a very terrible exam */

    static BigDecimal kuderRichardson20 (List<List<String>> exams, List<String> answerKey, List<Integer> weight) {
        BigDecimal numberOfQuestions = BigDecimal.valueOf(exams.get(0).size());
        BigDecimal overallVariance = overallVariance(examGrader(exams, answerKey, weight));
        List<BigDecimal> proportionPassingList = proportionPassingByQuestion(exams, answerKey, weight);
        List<BigDecimal> proportionFailingList = proportionFailingByQuestion(exams, answerKey, weight);
        BigDecimal meanWeight = meanInteger(weight);

        List<BigDecimal> passingXfailing = new ArrayList<>();
        for (int i = 0; i < proportionFailingList.size(); i++) {
            passingXfailing.add((proportionPassingList.get(i).multiply(proportionFailingList.get(i))));
        }
        BigDecimal sigmaPxQ = BigDecimal.ZERO;
        for (BigDecimal a : passingXfailing) {
            sigmaPxQ = ((sigmaPxQ.add(a)));
        }

        try {
            BigDecimal leftHand = (numberOfQuestions).divide((numberOfQuestions.subtract(BigDecimal.ONE)), 128, RoundingMode.HALF_UP);
            BigDecimal rightHand = (BigDecimal.ONE.subtract((sigmaPxQ.divide((overallVariance), 128, RoundingMode.HALF_UP))));
            return ((leftHand.multiply(rightHand)).divide(meanWeight, 128, RoundingMode.HALF_UP));

        } catch (ArithmeticException e) {
            return BigDecimal.ONE;
        }
    }
    /* This method does, in this order:
     * Calculates the number of questions (n)
     * Calculates the variance of test scores (var)
     * Calculates a list of the proportion of people that passed each question (p)
     * Calculates a list of the proportion of people that failed each question (q)
     * Calculates a list of the elements of p and q multiplied together (p*q)
     * Adds each element of p*q together (Σp*q)
     * Calculates n/(n-1), called leftHand
     * Calculates 1-(Σp*q)/Var, called rightHand
     * Returns leftHand * rightHand
     *
     * Note that this statistic is not completely accurate unless the exam is entirely 1 point questions
     * Should be between 0 and 1 but can be negative when you are a really bad test maker and your students are also really bad ツ */

    static List<List<Integer>> questionFrequency(List<List<String>> exams) {
        List<List<Integer>> questionFrequency = new ArrayList<>();

        for (int i = 0; i < exams.get(0).size(); i++) {
            List<Integer> templist = new ArrayList<>();

            int a = 0;
            int b = 0;
            int c = 0;
            int d = 0;
            int e = 0;

            for (int j = 0; j < exams.size(); j++) {

                if (exams.get(j).get(i).equals("0")) {
                    a++;
                } else if (exams.get(j).get(i).equals("1")) {
                    b++;
                } else if (exams.get(j).get(i).equals("2")) {
                    c++;
                } else if (exams.get(j).get(i).equals("3")) {
                    d++;
                } else if (exams.get(j).get(i).equals("4")) {
                    e++;
                }
            }

            templist.add(a);
            templist.add(b);
            templist.add(c);
            templist.add(d);
            templist.add(e);

            questionFrequency.add(templist);
        }
        return questionFrequency;
    }
    /* This method takes in the List<List<Strings>> that are the exams in the form of exams<question<answer>> and the answers are in 01234 format,
     * and returns a List<List<Integer>> which is the question<choice<frequency>> of the exam.
     * For example, questionFrequency(exams).get(5).get(3) will give you an integer corresponding to question 6 choice D frequency.
     * Remember that a 200 question exam is indexed 0-199 */

    static BigDecimal squareRoot(BigDecimal input) {
        BigDecimal first = new BigDecimal("0");
        BigDecimal second = new BigDecimal(Math.sqrt(input.doubleValue()));
        while (!first.equals(second)) {
            first = second;
            second = input.divide(first, 8, RoundingMode.HALF_UP);
            second = second.add(first);
            second = second.divide(BigDecimal.valueOf(2), 8, RoundingMode.HALF_UP);

        }
        return second;
    }
    /* This method takes in a BigDecimal as input and returns the square root of the BigDecimal */

    static BigDecimal standardDeviation(List<Integer> scores) {
        return squareRoot(overallVariance(scores));
    }

    static List<BigDecimal> percentiles(List<Integer> scores) {

        List<BigDecimal> percentiles = new ArrayList<>();

        for (int i = 0; i < scores.size(); i++) {
            BigDecimal numberLessThan = BigDecimal.ZERO;

            for (int j = 0; j < scores.size(); j++) {
                if (scores.get(j) < scores.get(i)) {
                    numberLessThan = numberLessThan.add(BigDecimal.ONE);
                }
            }

            percentiles.add((numberLessThan.divide(BigDecimal.valueOf(scores.size()), 128, RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(100)));
        }

        return percentiles;
    }
    /* This method takes in a List<Integer> corresponding to the scores and returns a list of BigDecimals corresponding
     * to that exam's percentile.
     * For example, percentiles(scores).get(5) will give you the 6th exam's percentile.
     * Remember index 0-1999 for a 2000 count exam.
     */
}
