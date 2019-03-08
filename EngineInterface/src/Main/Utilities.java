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
import javax.imageio.ImageIO;

public class Utilities {
  

    public void RetrieveEmails() throws IOException {

        String pdfFilename;
        String line; 
        String command = "python images/imapConnect.py";
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

    
    public void orient(){
         int width = 3300;    //width of the image
        int height = 2550;   //height of the image
        BufferedImage image = null;
        // File folder = new File("C:\\csc480");
        String temp = "";
        try {
            //Prompt for directory
            String directoryName;  // Directory name entered by the user.
            File directory;        // File object referring to the directory.
            directoryName = "images\\jpgs";
            directory = new File(directoryName);
            //Read directory to get all jpegs
            //For loop for all jpeg
            for (final File fileEntry : directory.listFiles()) {
                //Get the new image
                File testImage = fileEntry;  //image file path
                System.out.println(testImage.getAbsoluteFile());

                BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                newImage = ImageIO.read(testImage);

                //  System.out.println(newImage.getWidth()  + " " + newImage.getHeight());
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
                    //3/8ths of an inch-change for size   1/4 would be 1/44
                    threshold = 3 * newImage.getWidth() / 88;
                }

              //  System.out.println(threshold);
                //check get the correct
                int count = 0;
                int previous = -1;
                for (int i = 140; i < newImage.getWidth() - 140; i++) {
                    //Top changes to -1 at row 63 in ideal image
                    //Bottom changes to -1 at approximately row 140
                    if (newImage.getRGB(i, threshold) == -1) {
                        if (previous != -1) {
                            count = count + 1;
                            previous = -1;
                        }
                    } else {
                        previous = 1;
                    }
                }

                if (count > 75) {
                    newImage = rotateClockwise90(newImage);
                    newImage = rotateClockwise90(newImage);
                    System.out.println("Rotate 180 degrees");
                    //break;
                }

              //  System.out.println("Count: "+count);
                ImageIO.write(newImage, "JPG", testImage);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
        public static BufferedImage rotateClockwise90(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage pic = new BufferedImage(height, width, img.getType());

        Graphics2D graphics2D = pic.createGraphics();
        graphics2D.translate((height - width) / 2, (height - width) / 2);
        graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
        graphics2D.drawRenderedImage(img, null);

        return pic;
    }
    
    
  
    
    
    public int[][] runGrader() {            //Runs Python Script which analyzes a single Scantron. Returns MultiDimensional Array of Integers which are currently just the answers. No other Data.
        String pdfFilename;
        int array[][] = new int[20][5];
        int i, j;
        String line;
        String command = "python images/edgey.py -f images/scan.jpg"; //
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            //for(
            while ((line = error.readLine()) != null) {
                System.out.println(line);
            }
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {

                if (line.indexOf("-") != -1) {
                    i = Integer.parseInt(line.substring(0, line.indexOf("-")));
                    j = Integer.parseInt(line.substring(line.indexOf("-") + 1, line.indexOf("-") + 2));
                    array[i][j] = Integer.parseInt(line.substring(line.indexOf(" ") + 1, line.length()));
                }

            }
            error.close();
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
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

}
