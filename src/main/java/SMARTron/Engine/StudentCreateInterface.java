package SMARTron.Engine;

import java.util.ArrayList;

public class StudentCreateInterface {
    ArrayList<Student> rawScores;
    Grader g;
    //This is one of the forst major classes used for grading StudentCreateInterface > Grader > []Student
    public StudentCreateInterface(){//String[][] tt
        rawScores = new ArrayList<>();

        // The python script puts the data in an out of order format, its easyer to handle the data once it is collected as a multidimenitnal array
        /* Indexing test data for multi dimentinal arrays
        String[][] stu = {
                {"1", "11", "21", "31", "41"},
                {"2", "12", "22", "32", "42"},
                {"3", "13", "23", "33", "43"},
                {"4", "14", "24", "34", "44"},
                {"5", "15", "25", "35", "45"},
                {"6", "16", "26", "36", "46"},
                {"7", "17", "27", "37", "47"},
                {"8", "18", "28", "38", "48"},
                {"9", "19", "29", "39", "49"},
                {"10", "20", "30", "40", "50"},
                {"51", "61", "71", "81", "91"},
                {"52", "62", "72", "82", "92"},
                {"53", "63", "73", "83", "93"},
                {"54", "64", "74", "84", "94"},
                {"55", "65", "75", "85", "95"},
                {"56", "66", "76", "86", "96"},
                {"57", "67", "77", "87", "97"},
                {"58", "68", "78", "88", "98"},
                {"59", "69", "79", "89", "99"},
                {"60", "70", "80", "90", "100"}
        };
        */
        /*
//more test data
        String[][] stu = {
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"}
        };
        //                Name start                                                                                                   Name End |sex |Grade|B-day Start    B-dayEnd|  id Start                                 id End   Code Start               Code end
        //                 V                                                                                                                 V   V     V    V                    V    V                                             V     V                             V
        String[] stu43 = {"13", "1", "20", "20", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "1", "15", "3", "2", "1", "9", "8", "8", "0", "4", "8", "4", "6", "8", "5", "9", "-1", "-1", "-1", "-1", "-1", "-1", "-1"};
        //
        //
        String[][] key = {
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"},
                {"1", "2", "1", "4", "0"}
        };
        */

        //String[] key43 = {"11", "5", "25", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"};
        //String[] stu43 = {"13", "1", "20", "20", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "1", "15", "3", "2", "1", "9", "8", "8", "0", "4", "8", "4", "6", "8", "5", "9", "-1", "-1", "-1", "-1", "-1", "-1", "-1"};
        //more test data
//
        //addStudent(tt, key43);
        //addStudent(tt, stu43); //<info passed into grader
        //Grader g = new Grader(rawScores); // <-- constructor for grader gets the ball rolling
        //g.gradeTests();
    }
    //This method is the second part of the add student method
//It constructs the data from the two string arrays put inside it and makes one big string array
    public void gimmieTheLoot(String[] s, String[] first43){ //Yeah its a stupid method name, too much astroworld I guess..
        //Student ss = new Student(s); //clone?
        String[] temp = new String[43 + s.length];
        for (int i = 0; i < temp.length; i++){
            if(i < 43){//43 is used for a constant for all the datapoints on a scantron that are not questions
                temp[i] = first43[i];
            }else{
                temp[i] = s[i - 43]; //adjust the index for the question
            }
        }
        Student ss = new Student(temp);
        rawScores.add(ss);
    }

    public void addStudent(String[][] s, String[] first43){
        ArrayList<String> temp = new ArrayList<>();
        int pos = 0;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 10; j++){
                temp.add(pos++, s[j][i]);
                temp.add(temp.size(), s[j+10][i]); //all this method does is convert the multidimentinal array (seen above) and make it into a single array to be used later
            }
        }

        String[] temp2 = new String[temp.size()];
        temp2 = temp.toArray(temp2);
        gimmieTheLoot(temp2, first43); //This method has a second part
    }

    public void runGrades() {
        Grader temp = new Grader(rawScores);
        temp.gradeTests();
        g = temp;
    }
}


/*
Parts of the scantron:
Section, number of entries, array range, scope
1. Name - Last, First, Middle: 20|0-19|0-26
2. Sex: 1|19-20|0-2
3. Grade: 1|20-21|0-17*
4. Birth date: 5|21-26|MO 0-12*..DAY1 0-4*.. DAY2-YR 0-10*
5. Id: 10|26-36|0-10*
6. Code: 6|36-42|0-10*
7. Questions: 200|42-242|0-5
*1 = 0, the 0 is used as nul
 */