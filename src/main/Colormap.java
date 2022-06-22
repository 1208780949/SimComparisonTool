package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Colormap {

    private ArrayList<Double> position;
    private ArrayList<Double> red;
    private ArrayList<Double> green;
    private ArrayList<Double> blue;

    public Colormap() {

        this.position = new ArrayList<>();
        this.red = new ArrayList<>();
        this.green = new ArrayList<>();
        this.blue = new ArrayList<>();
        importColormap();

    }

    /**
     * Imports the color map
     */
    private void importColormap() {

        try (BufferedReader br = new BufferedReader(new FileReader(System.getenv("LOCALAPPDATA") + File.separator + SimComparisonTool.SCT_FOLDER_NAME + File.separator + "TurboMap.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                position.add(Double.parseDouble(values[0]));
                red.add(Double.parseDouble(values[1]));
                green.add(Double.parseDouble(values[2]));
                blue.add(Double.parseDouble(values[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Determine the position on turbomap. This is a way to convert
     * rgb pixels to custom greyscale accurately (but slowly)
     * @param r red
     * @param g green
     * @param b blue
     * @return custom grayscale pixel value
     */
    public int determinePos(double r, double g, double b) {

        ArrayList<Integer> redClose = getAllIndexCloseTo(red, r);
        ArrayList<Integer> greenClose = getAllIndexCloseTo(green, g);
        ArrayList<Integer> blueClose = getAllIndexCloseTo(blue, b);

        for (int red : redClose) {
            for (int green : greenClose) {
                if (red == green) {
                    for (int blue : blueClose) {
                        if (red == blue) {
                            return red;
                        }
                    }
                }
            }
        }

        return 0;

    }

    /**
     * Get all the indices in a double arraylist that are
     * close to value n
     * @param arr double arraylist
     * @param n number
     * @return all indices close to n
     */
    private ArrayList<Integer> getAllIndexCloseTo(ArrayList<Double> arr, double n) {

        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            double element = arr.get(i);
            if (approximatelyEqual(element, n)) {
                indices.add(i);
            }
        }
        return indices;
    }

    /**
     * If absolute value of two numbers is less than 0.03,
     * they are approximately equal.
     * 0.03 is a semi randomly chosen number.
     * @param x number 1
     * @param y number 2
     * @return true if they are approxmiately equal
     */
    private boolean approximatelyEqual(double x, double y) {
        return Math.abs(x - y) < 0.03;
    }

}
