/*

 * To change this license header, choose License Headers in Project Properties.

 * To change this template file, choose Tools | Templates

 * and open the template in the editor.

 */

package picorient;



import java.awt.Graphics2D;

import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;

import javax.imageio.ImageIO;



/**

 *

 * @author zreis

 */

public class Main {



    /**

     * @param args the command line arguments

     * @throws java.io.IOException

     */

    public static void main(String args[]) throws IOException {

        int width = 3300;    //width of the image

        int height = 2550;   //height of the image

        int x = 1;

        int y = 1;

        BufferedImage image = null;

        String temp = "";

        try {

            File directory;        // File object referring to the directory

           //directory hardcoded in to speed process

            directory = new File("c:\\csc480");

            //Read directory to get all jpegs

            //For loop for all scantron jpg files      

            for (final File fileEntry : directory.listFiles()) {

                //Get the new image

                File testImage = fileEntry;  //image file path

                BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

                newImage = ImageIO.read(testImage);



                //Make sure the image is vertical

                if (newImage.getHeight() > newImage.getWidth()) {

                    newImage = rotateClockwise90(newImage);

                }

                //Check if the standard or narrow slip

                int offset = 5 * newImage.getWidth() / 88;

                int threshold = newImage.getWidth() / 88;

                if (newImage.getWidth() / 11 - newImage.getHeight() / 8 < 10) {

                    offset = 0;

                    threshold = 3 * newImage.getWidth() / 88 - 8;

                }



                //Calculate the color saturation use to determine if rotation will be necessary 

                int count = 0;

                for (int i = 140; i < newImage.getWidth() - 140; i++) {

                    for (int j = 0; j < 40; j++) {

                        if ((newImage.getRGB(i, threshold - j) & 0xff00 / 256) < 120) {

                            count = count + 1;

                        }

                    }

                }

                // Image requires 180 degree rotation

                if (count > 10000) {//newImage.getWidth() / 2.0) {

                    newImage = rotateClockwise90(newImage);

                    newImage = rotateClockwise90(newImage);

                }

                // overwrite file after proper orientation was completed

                ImageIO.write(newImage, "JPG", testImage);

                int count1 = 0;

                for (int i = 7 * newImage.getWidth() / 11; i < 10 * newImage.getWidth() / 11; i++) {

                    if (newImage.getRGB(i, 3 * newImage.getWidth() / 22) > -100) {

                        count1 = count1 + 1;

                    }

                }

                // strip extension off file name

                String file = testImage.toString();

                if (file != null && file.length() > 0) {

                    while (file.contains(".")) {

                        file = file.substring(0, file.lastIndexOf('.'));

                    }

                }

                // Verify if the scan is a front or back and rename file accordingly

                if (count1 > 10) {



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



}