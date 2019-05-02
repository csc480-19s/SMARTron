package Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * The supplement methods used by the Scanner side of Engine
 * @author Vincent
 */
public class Utilities {
    
    /**
     * Retrieves attachments on unread emails using a python script "imapConnect.py"
     * @throws IOException 
     */
    public void RetrieveEmails() throws IOException {
        String line;
        String command = "python3 src/main/python/imapConnect.py";
        Process p = Runtime.getRuntime().exec(command);
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((line = input.readLine()) != null) {
            System.out.println(line);
        }
        input.close();
        BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        while ((line = error.readLine()) != null) {
            System.out.println(line);
        }
        error.close();
    }

    //Changes Directory based on whether the item is a pdf extension.
    public void changeDirectory() throws IOException {
        File file2 = new File("images/");
        String[] arraytemp = file2.list();
        Arrays.sort(arraytemp);
        for (int a = 0; a < arraytemp.length; a++) {
            System.out.println(arraytemp[a].substring(arraytemp[a].length() - 3, arraytemp[a].length()));
            int length = arraytemp[a].length();
            if (arraytemp[a].substring(length - 3, length).equals("pdf")) {
                System.out.println(arraytemp[a]);
                Files.move(Paths.get("images/" + arraytemp[a]), Paths.get("images/pdfTest/" + arraytemp[a]), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    /**
     * Parses the images embedded into the pdfs
     * @throws IOException 
     */
    public void pdf2jpeg() throws IOException { //png, jpg, pdf, tiff, pnp
        File file = new File("images/pdfTest/");
        String[] array = file.list();
        Arrays.sort(array);
        for (int j = 0; j < array.length; j++) {
            PDDocument document = PDDocument.load(new File("images/pdfTest/" + array[j]));
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                ImageIOUtil.writeImage(bim, ("images/jpgs/" + array[j]) + "-" + (page + 1) + ".jpg", 300);
            }
            document.close();
        }
    }

    /**
     * Runs Python Script which analyzes Scantron sheets. Returns
     * MultiDimensional List of exams of students of lists integer Strings of
     * the selected bubbles. The most outer List is of different tests The next
     * inner list is of each individual student exam the next list is of lists
     * of each section of the scantron
     *
     * @return the list of lists of lists of String lists
     * @throws java.lang.Exception
     */
    public List<List<List<List<String>>>> runScanner() throws Exception {
        File dir = new File("images/jpgs");
        String[] filesInDir = dir.list();
        Arrays.sort(filesInDir);
        List<List<List<List<String>>>> arr = new ArrayList<List<List<List<String>>>>();
        List<String> examNames = new ArrayList<String>();
        List<List<String>> examNumbers = new ArrayList<List<String>>();
        int count = 0;
        for (int j = 0; j < filesInDir.length; j++) {
            Scanner s = new Scanner(filesInDir[j]);
            s.useDelimiter("-");
            String name = s.next();
            String number = s.next();
            if (!examNames.contains(name)) {
                examNames.add(name);
                examNumbers.add(new ArrayList<String>());
                arr.add(new ArrayList<List<List<String>>>());
            }
            int index = examNames.indexOf(name);
            if (!examNumbers.get(index).contains(number)) {
                arr.get(index).add(new ArrayList<List<String>>());
                examNumbers.get(index).add(number);
                count = examNumbers.get(index).indexOf(number);
                for (int z = 0; z < 6; z++) {
                    arr.get(index).get(count).add(new ArrayList<String>());
                }
            } else {
                count = examNumbers.get(index).indexOf(number);
            }
            String line;
            String command = "python3 src/main/python/edgey.py -f images/jpgs/" + filesInDir[j];
            if (filesInDir[j].contains("front")) {
                try {
                    Process p = Runtime.getRuntime().exec(command);
                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    int num = 0;
                    while ((line = input.readLine()) != null) {
                        if (line.startsWith("n")) {
                            arr.get(index).get(count).get(0).add(line.replace("n ", ""));
                        } else if (line.startsWith("g")) {
                            arr.get(index).get(count).get(1).add(line.replace("g ", ""));
                        } else if (line.startsWith("e")) {
                            arr.get(index).get(count).get(2).add(line.replace("e ", ""));
                        } else if (line.startsWith("m")) {
                            arr.get(index).get(count).get(3).add(line.replace("m ", ""));
                        } else if (line.startsWith("d")) {
                            arr.get(index).get(count).get(3).add(line.replace("d ", ""));
                        } else if (line.startsWith("i")) {
                            if (num > 2 || arr.get(index).get(count).get(3).isEmpty()) {
                                arr.get(index).get(count).get(4).add(line.replace("i ", ""));
                            } else {
                                num++;
                                arr.get(index).get(count).get(3).add(line.replace("i ", ""));
                            }
                        } else {
                            arr.get(index).get(count).get(5).add(line);
                        }
                    }
                    input.close();
                    BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                    while ((line = error.readLine()) != null) {
                        System.out.println(line);
                    }
                    error.close();
                } catch (Exception ex) {
                    throw ex;
                }
            } else if (filesInDir[j].contains("back")) {
                try {
                    Process p = Runtime.getRuntime().exec(command);
                    arr.get(index).get(count).add(new ArrayList<String>());
                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    while ((line = input.readLine()) != null) {
                        if (!line.startsWith("n") && !line.startsWith("g") && !line.startsWith("e") && !line.startsWith("m") && !line.startsWith("d") && !line.startsWith("i")) {
                            arr.get(index).get(count).get(6).add(line);
                        }
                    }
                    input.close();
                    BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                    while ((line = error.readLine()) != null) {
                        System.out.println(line);
                    }
                    error.close();
                } catch (Exception ex) {
                    throw ex;
                }
            }
        }
        return arr;
    }

    /**
     * Converts the raw answers output of runScanner() into a 2D array based on
     * the layout of the Scantron sheet
     *
     * @param a The raw answers list
     * @return Ordered 2D array
     */
    public String[][] multi(List<String> a) {
        int maxR = 0, maxC = 0;
        String[] temp = new String[a.size()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = a.get(i);
            int temp1 = Integer.parseInt(temp[i].substring(0, temp[i].indexOf("-")));
            int temp2 = Integer.parseInt(temp[i].substring(temp[i].indexOf("-") + 1, temp[i].indexOf("-") + 2));
            if (temp1 > maxR) {
                maxR = temp1;
            }
            if (temp2 > maxC) {
                maxC = temp2;
            }
        }
        String[][] array = new String[maxR + 1][maxC + 1];

        int f = 0, j = 0;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].contains("-") && temp[i].length() > 2) {
                f = Integer.parseInt(temp[i].substring(0, temp[i].indexOf("-")));
                j = Integer.parseInt(temp[i].substring(temp[i].indexOf("-") + 1, temp[i].indexOf("-") + 2));
                array[f][j] = (temp[i].substring(temp[i].indexOf(" ") + 1, temp[i].length()));
            }
        }

        for (int i = 0; i < array.length; i++) {
            for (int k = 0; k < array[i].length; k++) {
                if (array[i][k] == null) {
                    array[i][k] = "error";
                }
            }
        }
        return array;
    }

    /**
     * Creates a CSV from lists of the student ids and their associated grades
     * in the folder where the send email script will be
     *
     * @param name 5 character code of exam
     * @param ids student ids
     * @param grades student grades
     * @return the file path to the csv
     * @throws IOException
     */
    public String gradeCSV(String name, List<String> ids, List<Float> grades) throws IOException {
        File file = new File("src/main/python/csv/" + name + ".csv");
        PrintWriter pwriter = new PrintWriter(file);
        pwriter.write("Student ID's,Grades\n");
        for (int i = 0; i < ids.size(); i++) {
            pwriter.write(ids.get(i) + "," + grades.get(i) + "\n");
        }
        pwriter.close();
        return "src/main/python/csv/" + name + ".csv";
    }

    /**
     * Executes sendEmailHandler to send csv file
     *
     * @param address receiving email address
     * @param csvPath path to csv
     * @throws IOException
     */
    public void sendEmailProcessed(String address, String csvPath) throws IOException {
        String command = "python3 src/main/python/sendEmailHandler.py -a " + address + " -c " + csvPath;
        Process p = Runtime.getRuntime().exec(command);
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = input.readLine()) != null) {
            System.out.println(line);
        }
        input.close();
        BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        while ((line = error.readLine()) != null) {
            System.out.println(line);
        }
        error.close();
    }

    /**
     * Executes sendEmailHandler to notify users that scans were received and
     * are being processed
     *
     * @param address receiving email address
     * @throws IOException
     */
    public void sendEmailReceived(String address) throws IOException {
        String command = "python3 src/main/python/sendEmailHandler.py -a " + address;
        Process p = Runtime.getRuntime().exec(command);
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = input.readLine()) != null) {
            System.out.println(line);
        }
        input.close();
        BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        while ((line = error.readLine()) != null) {
            System.out.println(line);
        }
        error.close();
    }

    /**
     * Deletes all jpg, pdf, and csv files from project directory
     */
    public void deleteAllFiles() {
        String jpgs = "images/jpgs/";
        String pdfs = "images/pdfTest/";
        String csvs = "src/main/python/csv/";
        String[] files = new File(jpgs).list();
        for (int i = 0; i < files.length; i++) {
            File f = new File(jpgs + files[i]);
            f.delete();
        }
        files = new File(pdfs).list();
        for (int i = 0; i < files.length; i++) {
            File f = new File(pdfs + files[i]);
            f.delete();
        }
        files = new File(csvs).list();
        for (int i = 0; i < files.length; i++) {
            File f = new File(csvs + files[i]);
            f.delete();
        }
    }
}
