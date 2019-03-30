package SMARTron;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

//packages

public class Utilities {

    public void RetrieveEmails() throws IOException {

        String pdfFilename;
        String line;
        String command = "python src/main/python/imapConnect.py";
        //^^Alter this script based on server directories
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
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

        File file2 = new File("images/");
        String[] arraytemp = file2.list();
        for (int a = 0; a < arraytemp.length; a++) {
            System.out.println(arraytemp[a].substring(arraytemp[a].length() - 3, arraytemp[a].length()));
            int length = arraytemp[a].length();
            if (arraytemp[a].substring(length - 3, length).equals("pdf")) {
                System.out.println(arraytemp[a]);
                Files.move(Paths.get("images/" + arraytemp[a]), Paths.get("images/pdfTest/" + arraytemp[a]), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    public void pdf2jpeg() throws IOException { //png, jpg, pdf, tiff, pnp

        File file = new File("images/pdfTest/");
        int i = file.list().length;
        String[] array = file.list();
        for (int j = 0; j < array.length; j++) {
            String fileName = array[j];
            PDDocument document = PDDocument.load(new File("images/pdfTest/" + array[j]));
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                ImageIOUtil.writeImage(bim, ("images/jpgs/" + array[j]) + "-" + (page + 1) + ".jpg", 300);
            }
            document.close();
        }
    }

    //Legacy version of orienting images no need to test
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

    //leagacy version no need to test
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

    public String[][] runGrader() {            //Runs Python Script which analyzes a single Scantron. Returns MultiDimensional Array of Integers which are currently just the answers. No other Data.
        File dir = new File("images/jpgs");
        String[] filesInDir = dir.list();
        String array[][] = new String[filesInDir.length][143];
        int i = 0;
        for (int j = 0; j < filesInDir.length; j++) {
            String line;
            String command = "python src/main/python/edgey.py -f images/jpgs/" + filesInDir[j]; //
            try {
                Process p = Runtime.getRuntime().exec(command);
                                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((line = input.readLine()) != null && i < 143) {
                    array[j][i] = line;
                    i++;
                }
                BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                while ((line = error.readLine()) != null) {
                    System.out.println(line);
                }
                error.close();
                input.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            i = 0;
        }

        return array;
    }

    public String[] getAnswers(int[][] a) {  //Takes MultiDimensional Array initiated by runGrader and puts it into a single String of Answers.
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

    public String[][] multi(String[] a) {
        String[][] array = new String[20][5];
        int f, j;
        int count = 0;
        for (int i = 43; i < a.length; i++) {
            if (a[i] == null) {
                i = a.length;
            } else {
                count++;
            }
        }
        String[] temp = new String[count];
        for (int i = 0; i < count; i++) {
            temp[i] = a[i];

        }

        for (int i = 0; i < temp.length; i++) {
            String line = temp[i];
            if (line.contains("-") && line.length() > 2) {
                if (line.indexOf("-") != -1) {
                    f = Integer.parseInt(line.substring(0, line.indexOf("-")));
                    j = Integer.parseInt(line.substring(line.indexOf("-") + 1, line.indexOf("-") + 2));
                    array[f][j] = (line.substring(line.indexOf(" ") + 1, line.length()));
                }
            }
        }
        return array;
    }
}
