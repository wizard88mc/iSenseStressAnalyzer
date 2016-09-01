package isensestressanalyzer.outputwriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Matteo Ciman
 */
public class StressorDistanceWriter extends OutputWriter {
    
    private File fileDistanceFromCenter, fileAverageLength, fileRatioOverUnder, 
        fileRectangleArea, fileLinearity;
    private BufferedWriter writerDistanceFromCenter, writerAverageLength, 
        writerRatioOverUnder, writerAverageAreaRectangle, writerAverageLinearity;
    
    public StressorDistanceWriter(int groupingIndex) {
        
        try {
            
            String basePath = OUTPUT_FOLDER + 
                FOLDER_STRESSOR_FEATURES + File.separator + 
                FOLDER_NAMES_GROUPING[groupingIndex] + File.separator;
            
            fileDistanceFromCenter = new File(basePath + 
                "average_distance_from_center.csv");
            
            fileAverageLength = new File(basePath + "average_length.csv");
            
            fileRatioOverUnder = new File(basePath + 
                "average_ratio_space_over_under.csv");
            
            fileRectangleArea = new File(basePath + "average_area_rectangle.csv");
            
            fileLinearity = new File(basePath +  "average_linearity.csv");
            
            if (!fileDistanceFromCenter.exists()) {
                fileDistanceFromCenter.getParentFile().mkdirs();
            }
            if (!fileAverageLength.exists()) {
                fileAverageLength.getParentFile().mkdirs();
            }
            if (!fileRatioOverUnder.exists()) {
                fileRatioOverUnder.getParentFile().mkdirs();
            }
            if (!fileRectangleArea.exists()) {
                fileRectangleArea.getParentFile().mkdirs();
            }
            if (!fileLinearity.exists()) {
                fileLinearity.getParentFile().mkdirs();
            }
            
            writerDistanceFromCenter = new BufferedWriter(new 
                FileWriter(fileDistanceFromCenter));
            writerAverageLength = new BufferedWriter(new 
                FileWriter(fileAverageLength));
            writerRatioOverUnder = new BufferedWriter(new 
                FileWriter(fileRatioOverUnder));
            writerAverageAreaRectangle = new BufferedWriter(new 
                FileWriter(fileRectangleArea));
            writerAverageLinearity = new BufferedWriter(new 
                FileWriter(fileLinearity));
        }
        catch(IOException exc) {
            System.out.println("IOException in creating BufferedWriter");
            exc.printStackTrace();
        }
    }
    
    public void writeIMEI(String imei) {
        
        try {
            writerDistanceFromCenter.write(imei);
            writerDistanceFromCenter.flush();
            
            writerAverageLength.write(imei);
            writerAverageLength.flush();
            
            writerRatioOverUnder.write(imei);
            writerRatioOverUnder.flush();
            
            writerAverageAreaRectangle.write(imei);
            writerAverageAreaRectangle.flush();
            
            writerAverageLinearity.write(imei);
            writerAverageLinearity.flush();
        }
        catch(IOException exc) {
            System.out.println("IOException in writing IMEI");
            exc.printStackTrace();
        }
    }
    
    /**
     * Writes on the output file the distance from the center
     * @param distanceFromCenter 
     */
    public void writeDistanceCenter(String distanceFromCenter) {
        try {
            writerDistanceFromCenter.write("," + distanceFromCenter);
            writerDistanceFromCenter.flush();
        }
        catch(IOException exc) {
            System.out.println("IOException in writing output");
            exc.printStackTrace();
        }
    }
    
    /**
     * Writes on the output file the average length
     * @param length 
     */
    public void writeAverageLength(String length) {
        try {
            writerAverageLength.write("," + length);
            writerAverageLength.flush();
        }
        catch(IOException exc) {
            exc.printStackTrace();
        }
    }
    
    /**
     * Writes on the output file the ratio of the points over and under the center
     * @param ratio 
     */
    public void writeRatioOverBottom(String ratio) {
        try {
            writerRatioOverUnder.write("," + ratio);
            writerRatioOverUnder.flush();
        }
        catch(IOException exc) {
            exc.printStackTrace();
        }
    }
    
    /**
     * Writes on the output file the area of the rectangle
     * @param area 
     */
    public void writeAreaRectangle(String area) {
        try {
            writerAverageAreaRectangle.write("," + area);
            writerAverageAreaRectangle.flush();
        }
        catch(IOException exc) {
            exc.printStackTrace();
        }
    }
    
    /**
     * Writes on the output file the linearity
     * @param linearity 
     */
    public void writeLinearity(String linearity) {
        try {
            writerAverageLinearity.write("," + linearity);
            writerAverageLinearity.flush();
        }
        catch(IOException exc) {
            exc.printStackTrace();
        }
    }
    
    public void endParticipant() {
        try {
            writerDistanceFromCenter.newLine(); writerDistanceFromCenter.flush();
            writerAverageLength.newLine(); writerAverageLength.flush();
            writerRatioOverUnder.newLine(); writerRatioOverUnder.flush();
            writerAverageAreaRectangle.newLine(); writerAverageAreaRectangle.flush();
            writerAverageLinearity.newLine(); writerAverageLinearity.flush();
        }
        catch(IOException exc) {
            exc.printStackTrace();
        }
    }
    
    public void close() {
        try {
            writerDistanceFromCenter.flush(); writerDistanceFromCenter.close();
            writerAverageLength.flush(); writerAverageLength.close();
            writerRatioOverUnder.flush(); writerRatioOverUnder.close();
            writerAverageAreaRectangle.flush(); writerAverageAreaRectangle.close();
            writerAverageLinearity.flush(); writerAverageLinearity.close();
        }
        catch(IOException exc) {
            exc.printStackTrace();
        }
    }
}
