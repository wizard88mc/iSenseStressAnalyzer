package isensestressanalyzer.filereader;

import isensestressanalyzer.exercise.Exercise;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * class FilesInputReader
 * 
 * This class is responsible to read the file that holds all the information
 * relative to the input files. Each Row of the input file is a couple
 * (IMEI, timestamp) that identifies and input
 * 
 * @author  Matteo Ciman
 * @version 0.1
 * @since   2014-10-16
 */
public class FilesInputReader extends FileReader {
    
    private static final String INPUT_DATA = "input_data_serious";
    private static final String INPUT_DATA_DEBUG = "input_data_small";
        
    public FilesInputReader() {
        super(INPUT_DATA);
    }
    
    /**
     * Reads the file to retrieve all the couples of IMEI, timestamp that 
     * represent the input files
     * 
     * @return a list of couples IMEI, timestamp
     */
    public ArrayList<String> getAllInputFiles() {
        
        ArrayList<String> toReturn = new ArrayList<>();
        
        try {
            openFile();
            String line;
            while ((line = readLine()) != null) {
                toReturn.add(Exercise.replaceDelimiter(line));
            }
        }
        catch(FileNotFoundException exc) {
            System.out.println(exc.toString());
            exc.printStackTrace();
        }
        return toReturn;
    }
}
