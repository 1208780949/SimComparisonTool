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
        DecimalFormat df = new DecimalFormat("#.#####");

        int redIndex = getClosestIndex(red, r);
        int greenIndex = getClosestIndex(green, g);
        int blueIndex = getClosestIndex(blue, b);

        if (redIndex - greenIndex < 10) {
            return (int) Math.round(position.get(greenIndex) * 255);
        } else if (redIndex - blueIndex < 10) {
            return (int) Math.round(position.get(blueIndex) * 255);
        } else {
            return (int) Math.round(position.get(greenIndex) * 255);
        }


    }

    /**
     * Get the index of the closet value in an arraylist
     * @return closet index
     */
    private int getClosestIndex(ArrayList<Double> arr, double n) {
        double distance = Math.abs(arr.get(0) - n);
        int idx = 0;
        for(int i = 1; i < arr.size(); i++){
            double iDistance = Math.abs(arr.get(i) - n);
            if(iDistance < distance){
                idx = i;
                distance = iDistance;
            }
        }
        return idx;
    }

}
