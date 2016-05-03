package isensestressanalyzer.outputwriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Matteo Ciman
 */
public class DigitsDistancesOutputWriter extends OutputWriter {
    
    private File fileDistanceEachPoint, fileDistanceFromCenter;
    private BufferedWriter writerDistancesEachPoint, writerDistancesFromCenter;
    
    public DigitsDistancesOutputWriter() {
        
        try {
            fileDistanceEachPoint = new File(OUTPUT_FOLDER + 
                FOLDER_WRITE_DATA_DISTANCES + File.separator + 
                "average_distance_between_each_point.csv");
            fileDistanceFromCenter = new File(OUTPUT_FOLDER + 
                FOLDER_WRITE_DATA_DISTANCES + File.separator + 
                "average_distance_from_center.csv");
            
            if (!fileDistanceEachPoint.exists()) {
                fileDistanceEachPoint.getParentFile().mkdirs();
            }
            if (!fileDistanceFromCenter.exists()) {
                fileDistanceFromCenter.getParentFile().mkdirs();
            }
            
            writerDistancesEachPoint = new BufferedWriter(
                new FileWriter(fileDistanceEachPoint));
            writerDistancesFromCenter = new BufferedWriter(
                new FileWriter(fileDistanceFromCenter));
        }
        catch(IOException exc) {
            System.out.println("Exception in creating files for write "
                + "distances output");
            exc.printStackTrace();
        }
    }
    
    /**
     * Starts a new line writing the IMEI of the tester
     * @param IMEI the IMEI of the participant
     */
    public void writeIMEI(String IMEI) {
        
        try {
            writerDistancesEachPoint.write(IMEI);
            writerDistancesFromCenter.write(IMEI);
        }
        catch(IOException exc) {
            System.out.println("IOException in writing IMEI");
            exc.printStackTrace();
        }
    }
    
    /**
     * Adds a new value to the output file
     * @param eachPoint distance for each point distance
     * @param center distance for from center distance
     */
    public void addValueDistance(String eachPoint, String center) {
        
        try {
            writerDistancesEachPoint.write("," + eachPoint);
            writerDistancesFromCenter.write("," + center);
        }
        catch(IOException exc) {
            System.out.println("IOException in writing distance value");
            exc.printStackTrace();
        }
    }
    
    /**
     * Completes the line of the participant
     */
    public void participantCompleted() {
        
        try {
            writerDistancesEachPoint.newLine(); writerDistancesEachPoint.flush();
            writerDistancesFromCenter.newLine(); writerDistancesFromCenter.flush();
        }
        catch(IOException exc) {
            System.out.println("IOException in completing participant");
            exc.printStackTrace();
        }
    }
    
    /**
     * Closes the output file
     */
    public void close() {
        try {
            writerDistancesEachPoint.flush(); writerDistancesEachPoint.close();
            writerDistancesFromCenter.flush(); writerDistancesFromCenter.close();
        }
        catch(IOException exc) {
            System.out.println("IOException in closing files");
            exc.printStackTrace();
        }
    }
}
