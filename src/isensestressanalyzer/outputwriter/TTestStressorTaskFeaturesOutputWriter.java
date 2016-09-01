package isensestressanalyzer.outputwriter;

import static isensestressanalyzer.outputwriter.OutputWriter.FOLDER_STRESSOR_FEATURES;
import static isensestressanalyzer.outputwriter.OutputWriter.OUTPUT_FOLDER;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Writes the TTest for the 
 * @author Matteo Ciman
 */
public class TTestStressorTaskFeaturesOutputWriter extends OutputWriter {
    
    private BufferedWriter outputWriter;
    
    public TTestStressorTaskFeaturesOutputWriter(int groupingIndex) {
        
        try {
            String basePath = OUTPUT_FOLDER + 
                FOLDER_STRESSOR_FEATURES + File.separator + 
                FOLDER_NAMES_GROUPING[groupingIndex] + File.separator;
            
            File file = new File(basePath + "TTestFeatures.csv");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            
            outputWriter = new BufferedWriter(new FileWriter(file));
        }
        catch(IOException exc) {
            System.out.println("Exception in creating TTestFeatures file: " + 
                exc.toString());
        }
    }
    
    /**
     * Starts a new line in the output file with the name of the feature
     * @param name the name of the feature
     */
    public void writeFeatureName(String name) {
        try {
            outputWriter.write(name);
        }
        catch(IOException exc) {
            System.out.println("Error in writing feature name: " + exc.toString());
        }
    }
    
    public void writeTTest(double beforeMiddle, double beforeEnd, 
        double middleEnd) {
        
        try {
            outputWriter.write("," + beforeMiddle + "," + beforeEnd + 
                    "," + middleEnd);
        }
        catch(IOException exc) {
            System.out.println("Error in writing TTest: " + exc.toString());
        }
    }
    
    public void endFeatureTests() {
        
        try {
            outputWriter.newLine();
            outputWriter.flush();
        }
        catch(IOException exc) {
            System.out.println("IOException in ending features line: " + 
                    exc.toString());
        }
        
    }
    
    public void close() {
        
        try {
            outputWriter.flush();
            outputWriter.close();
        }
        catch(IOException exc) {
            System.out.println("IOException in closing file: " + exc.toString());
        }
    }
}
