package isensestressanalyzer.outputwriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Matteo
 */
public class TTestTouchDigitsAnalysis extends OutputWriter {
    
    private static final String OUTPUT_FILE_NAME_BASE = 
        "ttest_";
    
    private File outputFile;
    private BufferedWriter outputWriter;
    
    public TTestTouchDigitsAnalysis(String outputFileName) {
        
        try {
            outputFile = new File(OUTPUT_FOLDER + FOLDER_WRITE_DATA_DISTANCES + 
                File.separator + OUTPUT_FILE_NAME_BASE + outputFileName + ".csv");
            
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
     * @param result the result of the TTest
     * @param occurrencesNoStress the number of occurrences of the digit in no stress
     * @param occurrencesStress the number of occurrences of the digit in stress
     */
    public void writeTTestResult(String digit, String result, 
            String occurrencesNoStress, String occurrencesStress) {
        
        try {
            outputWriter.write(digit + "," + result + "," + 
                occurrencesNoStress + "," + occurrencesStress);
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
