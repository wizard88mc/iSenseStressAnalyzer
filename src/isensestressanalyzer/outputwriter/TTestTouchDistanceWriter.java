package isensestressanalyzer.outputwriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Matteo
 */
public class TTestTouchDistanceWriter extends OutputWriter {
    
    private static final String OUTPUT_FILE_NAME = 
        "ttest_write_touch_distance.csv";
    
    private File outputFile;
    private BufferedWriter outputWriter;
    
    public TTestTouchDistanceWriter() {
        
        try {
            outputFile = new File(OUTPUT_FOLDER + FOLDER_WRITE_DATA_DISTANCES + 
                File.separator + OUTPUT_FILE_NAME);
            
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
            }
            
            outputWriter = new BufferedWriter(new FileWriter(outputFile));
        }
        catch(IOException exc) {
            System.out.println("IOException in creating BufferedWriter");
            exc.printStackTrace();
        }
    }
    
    /**
     * Writes the result of the tTest
     * @param digit the considered digit
     * @param tTestEachPoint the result of the ttest for each point distance
     * @param tTestCenter the result of the ttest for center distance
     */
    public void writeTTestResult(String digit, String tTestEachPoint, 
            String tTestCenter) {
        
        try {
            outputWriter.write(digit + "," + tTestEachPoint + "," + tTestCenter);
            outputWriter.newLine(); outputWriter.flush();
        }
        catch(IOException exc) {
            System.out.println("IOException in writing TTest result");
            exc.printStackTrace();
        }   
    }
    
    /**
     * Closes the output file
     */
    public void close() {
        try {
            outputWriter.flush(); outputWriter.close();
        }
        catch(IOException exc) {
            System.out.println("IOException in closing TTest output file");
            exc.printStackTrace();
        }
    }
}
