package isensestressanalyzer.outputwriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Matteo Ciman
 */
public class DigitsKeyboardFeaturesOutputWriter extends OutputWriter {
    
    private File outputFile;
    private BufferedWriter outputWriter;
    
    public DigitsKeyboardFeaturesOutputWriter(String featureName) {
        
        try {
            outputFile = new File(OUTPUT_FOLDER + 
                FOLDER_WRITE_DATA_DISTANCES + File.separator + 
                featureName + ".csv");
            
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
            }
            
            outputWriter = new BufferedWriter(
                new FileWriter(outputFile));
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
            outputWriter.write(IMEI);
        }
        catch(IOException exc) {
            System.out.println("IOException in writing IMEI");
            exc.printStackTrace();
        }
    }
    
    /**
     * Adds a new value to the output file
     * @param value the value to add
     */
    public void addValue(String value) {
        
        try {
            outputWriter.write("," + value);
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
            outputWriter.newLine(); outputWriter.flush();
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
            outputWriter.flush(); outputWriter.close();
        }
        catch(IOException exc) {
            System.out.println("IOException in closing files");
            exc.printStackTrace();
        }
    }
}
