package isensestressanalyzer.filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Matteo Ciman
 */
public class FileReader  {
    
    public static final String FOLDER_WITH_DATA = "data";
    protected File file = null;
    protected BufferedReader reader = null;
    
    public FileReader(String filename) {
        
        file = new File(FOLDER_WITH_DATA + File.separator + filename + ".csv");
    }
    
    public FileReader(String IMEI, String data, String postFix) {
        
        file = new File(FOLDER_WITH_DATA + File.separator 
                + IMEI + File.separator
                + IMEI + "_" + data + "_" + postFix + ".csv");
    }
    
    /**
     * Opens the file to read
     * @throws FileNotFoundException 
     */
    public void openFile() throws FileNotFoundException {
        reader = new BufferedReader(new java.io.FileReader(file));
    }
    
    /**
     * Reads a line from the file
     * @return the current line if any, otherwise null
     */
    public String readLine() {
        
        try {
            return reader.readLine();
        }
        catch(IOException exc) {
            System.out.println(exc.toString());
            exc.printStackTrace();
            return null;
        }
    }
    
    /**
     * Closes the current BufferedReader
     */
    public void closeFile() {
        
        try {
            reader.close();
        }
        catch(IOException exc) {
            System.out.println(exc.toString());
            exc.printStackTrace();
        }
    }
    
    /**
     * Returns the string with the exercise to look for inside a log file
     * 
     * @param exerciseNumber The number of the exercise
     * @param exercise the String of the exercise
     * @param stress if stress or not
     * @return the title string to look for
     */
    protected String buildStringForExerciseTitle(int exerciseNumber, 
            String exercise, boolean stress) {
        return "Exercise " + exerciseNumber + ": " + exercise + "(" + stress + ")";
    }
    
    /**
     * Returns the string of the repetition to search inside the file
     * @param repetition the number of the repetition
     * @return the String of the repetition
     */
    protected String buildStringForRepetition(int repetition) {
        return "REPETITION " + repetition;
    }
    
    /**
     * Checks if a particular string is inside the List of lines of the file, 
     * starting from a particular index
     * @param string the string to search
     * @param lines a list of lines of a file
     * @param startIndex the starting point to search
     * @return the index for the searched string, -1 otherwise
     */
    protected int searchForStringInList(String string, List<String> lines, 
            int startIndex) {
        
        for (int i = startIndex; i < lines.size(); i++) {
            if (lines.get(i).equals(string)) {
                return i;
            }
        }
        return -1;
    }
}
