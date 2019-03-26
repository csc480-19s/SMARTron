package Main;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

//packages
import java.awt.Graphics2D;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Utilities {

    public void RetrieveEmails() throws IOException {
        //Runs Databases Python Script which retrieves the emails
        //
        String pdfFilename;
        String line;
        String command = "python images/imapConnect.py";    //String which stroes the command
        try {
            Process p = Runtime.getRuntime().exec(command);         //executes the python script 
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));   //creates a BufferedReader object which stores the entire output of the python script
            while ((line = input.readLine()) != null) {                                             //runs through each line of the buffered reader storing the individual values
                System.out.println(line);                           //prints out the line String, this print line will be deleted when we finish(currenntly used for testing)
            }
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));   //If there are any errors being printed out, this line will catch them and output them
            while ((line = error.readLine()) != null) {
                System.out.println(line);
            }
            error.close();
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //Changes Directory based on whether the item is a pdf extension.
    public void changeDirectory() throws IOException {

        File file2 = new File("images/");       //Finds the directory at which all the pdfs are stored
        String[] arraytemp = file2.list();      //Creates an array of names of every item in the directory
        for (int a = 0; a < arraytemp.length; a++) {        //parses through every file looking for ".pdf"
            System.out.println(arraytemp[a].substring(arraytemp[a].length() - 3, arraytemp[a].length()));
            int length = arraytemp[a].length();
            if (arraytemp[a].substring(length - 3, length).equals("pdf")) {
                System.out.println(arraytemp[a]);
                Files.move(Paths.get("images/" + arraytemp[a]), Paths.get("images/pdfTest/" + arraytemp[a]), StandardCopyOption.REPLACE_EXISTING);  //moves all pdfs into another directory
            }
        }
    }

    public void pdf2jpeg() throws IOException { //png, jpg, pdf, tiff, pnp
        
        File file = new File("images/pdfTest/");            //Finds the directory at which the pdfs are stored
        int i = file.list().length;                         //gets a count of how many files in the directory, not currently used.
        String[] array = file.list();                       //Puts the name of each file into a String array
        for (int j = 0; j < array.length; j++) {            //iterates thru that String array created previously
            String fileName = array[j];                     //not currently used
            PDDocument document = PDDocument.load(new File("images/pdfTest/" + array[j]));  //Uses the PDFBOX library to create a pdf document from the directory & pdf name
            PDFRenderer pdfRenderer = new PDFRenderer(document);                            //Uses PDFBox library to render the pdf.
            for (int page = 0; page < document.getNumberOfPages(); ++page) {                //iterates through each pdf, creating jpgs from each page
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);   //creates a buffered image from the specific page and of color type RGB
                ImageIOUtil.writeImage(bim, ("images/jpgs/" + array[j]) + "-" + (page + 1) + ".jpg", 300); //Writes a jpg image to the directory specified
            }
            document.close();                               //closes the document
        }
    }

    public void orient() {
        int width = 963;    //width of the image
        int height = 640;   //height of the image
        int x = 1;
        int y = 1;
        BufferedImage image = null;
        // File folder = new File("C:\\csc480");
        String temp = "";
        try {
            //Prompt for directory
            String directoryName;  // Directory name entered by the user.
            File directory;        // File object referring to the directory.

            directory = new File("images/jpgs/");
            //Read directory to get all jpegs
            //For loop for all jpeg
            for (final File fileEntry : directory.listFiles()) {
                //Get the new image
                File testImage = fileEntry;  //image file path
                System.out.println(testImage.getAbsoluteFile());
                BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                newImage = ImageIO.read(testImage);
                //      System.out.println(newImage.getWidth() + " " + newImage.getHeight());
                //Make sure the image is vertical
                if (newImage.getHeight() > newImage.getWidth()) {
                    newImage = rotateClockwise90(newImage);
                    System.out.println("Rotate 90 degrees");
                }
                //Check if the standard or narrow slip
                int offset = 5 * newImage.getWidth() / 88;
                int threshold = newImage.getWidth() / 88;
                if (newImage.getWidth() / 11 - newImage.getHeight() / 8 < 10) {
                    offset = 0;
                    threshold = 3 * newImage.getWidth() / 88;
                }
                //    System.out.println(threshold);
                //check get the correct
                int count = 0;
                int previous = -1;
                for (int i = 140; i < newImage.getWidth() - 140; i++) {
                    if (newImage.getRGB(i, threshold) < -1000) {
                        if (previous != -1) {
                            count = count + 1;
                            previous = -1;
                        }
                    } else if (newImage.getRGB(i, threshold - 1) < -1000) {
                        if (previous != -1) {
                            count = count + 1;
                            previous = -1;
                        }
                    } else if (newImage.getRGB(i, threshold - 2) < -1000) {
                        if (previous != -1) {
                            count = count + 1;
                            previous = -1;
                        }
                    } else {
                        previous = 1;
                    }
                }
                if (count > 50) {//newImage.getWidth() / 2.0) {
                    newImage = rotateClockwise90(newImage);
                    newImage = rotateClockwise90(newImage);
                    System.out.println("Rotate 180 degrees");
                }

                ImageIO.write(newImage, "JPG", testImage);

                int count1 = 0;
                for (int i = 7 * newImage.getWidth() / 11; i < 10 * newImage.getWidth() / 11; i++) {

                    //System.out.println(newImage.getRGB(i, 3*newImage.getWidth()/22));
                    if (newImage.getRGB(i, 3 * newImage.getWidth() / 22) > -100) {
                        count1 = count1 + 1;
                    }
                }
               // System.out.println("Count: " + count);
                // System.out.println("Count1: " + count1);

                // strip extension off file name
                String file = testImage.toString();
                if (file != null && file.length() > 0) {
                    while (file.contains(".")) {
                        file = file.substring(0, file.lastIndexOf('.'));
                    }
                }
                // Check for front or back and rename file

                if (count1 > 0) {

                    File frontName = new File(file + "_front_" + x + ".jpg");
                    testImage.renameTo(frontName);
                    x++;

                } else {
                    File backName = new File(file + "_back_" + y + ".jpg");
                    testImage.renameTo(backName);
                    y++;
                }

            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

    }

    public static BufferedImage rotateClockwise90(BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();

        BufferedImage dest = new BufferedImage(height, width, src.getType());

        Graphics2D graphics2D = dest.createGraphics();
        graphics2D.translate((height - width) / 2, (height - width) / 2);
        graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
        graphics2D.drawRenderedImage(src, null);

        return dest;
    }

    public String[][] runGrader() {            //Runs Python Script which analyzes a single Scantron. 
        File dir = new File("images/jpgs");     //Finds the directory at which all jpgs are stored
        String[] filesInDir = dir.list();       //creates a string array of all names of each file in the directory
        String array[][] = new String[filesInDir.length][143];  //creates a multi dimensional array which will store all values of output by the grader
        int i = 0;
        for (int j = 0; j < filesInDir.length; j++) {   //iterates through the previously established array

            String line;
            String command = "python images/edgey.py -f images/jpgs/" + filesInDir[j]; // runs python analyzer script on each file in the jpg directory
            try {
                Process p = Runtime.getRuntime().exec(command);                         //if there is an error, it will print the specific error, used for testing
                BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));

                //for(
                while ((line = error.readLine()) != null) {
                    System.out.println(line);                                           
                }
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));   //everything the python script sends to the output stream gets stored
                while ((line = input.readLine()) != null && i < 143) {                                  //each line which is outputed gets stored in the line string
                    array[j][i] = line;                                                                 //each line gets put into the multidimensional array, depending on the scantron
                    i++;
                }
                error.close();
                input.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            i = 0;
        }

        return array;               //syntax for the array -array[student/scantron][information from that students scantron]
    }

    public String[] getAnswers(int[][] a) {  //Just a test method which takes a multi array and sorts it into a single dimension array, no longer in use
        int k = 0;
        for (int l = 0; l < a.length; l++) {
            for (int f = 0; f < a[l].length; f++) {
                k++;
            }
        }

        String studentAnswers[] = new String[k];
        k = 0;
        for (int l = 0; l < a.length; l++) {
            for (int f = 0; f < a[l].length; f++) {
                studentAnswers[k] = Integer.toString(a[l][f]);

            }
        }
        return studentAnswers;
    }

    public String[][] multi(String[] a) {  //iterates through the Single String array and creates a multidimensional array of values, which recreates the scantron answer sheet
        
        //This array passes in a String array which is created by the runGrader method, and is just used to sort the answer choices on the scantron
        
        String[][] array = new String[20][5];     //Creates an array which will store each set of values, as explained in class, syntax[20 rows][5 columns] based on the ammount of rows and columns on a scantron
        int f, j;
        int count = 0;
        for (int i = 0; i < a.length; i++) {       //iterates through the array passed in, finding the length.
            if (a[i] == "null") {       
                i = a.length;
            } else {
                count++;
            }
        }
        String[] temp = new String[count];
        for (int i = 0; i < count; i++) {           //creates an array based on the length of the passed in String array
            temp[i] = a[i];                         //stores the values of passed in array into new temp array

        }

        for (int i = 0; i < temp.length; i++) { // parses through the temp array
            String line = temp[i];              //each element of the temp array is stored to be parsed
            if (line.contains("-") && line.length() > 2) {      //checks to see if the line has a "-" mark, and is greater than 2 characters, this helps to find the answers in the output
               //^^This is also a check to make sure we dont accept a negative one value as an answer
                
                
                //As data is sent from the python script, the first 43 lines of data that are printed from the python analyzer are just integers, after that
                // The script prints out the answers the student wrote down on the scantron, these answers have the syntax "row-column answer" for example, "0-3 2" means that on row 0,
                //column three, the student put the answer "C". This means that on question 31, the student put down the answer C.
                
                if (line.indexOf("-") != -1) {      //checks to make sure that "-" exists in the string
                    //stores values in the multidimensional array based on the rows and columns syntax we are using
                    f = Integer.parseInt(line.substring(0, line.indexOf("-"))); //grabs the row number from the string
                    j = Integer.parseInt(line.substring(line.indexOf("-") + 1, line.indexOf("-") + 2)); //grabs the column number
                    array[f][j] = (line.substring(line.indexOf(" ") + 1, line.length()));   //stores the answer at the row-column spot in the multidimensional array
                }
            }
        }
        return array;       //outputs the array
    }
}
