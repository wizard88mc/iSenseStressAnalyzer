package isensestressanalyzer.filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class DigitsHeatmapReader
 * 
 * Used to read the settings file to know which 
 * 
 * @author Matteo Ciman
 */
public class DigitsHeatmapReader {
    
    private static final String SETTINGS_FOLDER = "settings";
    private static final String DIGITS_HEATMAP = "digits_heatmap.csv";
    
    public static ArrayList<String> getDigitsForHeathmap() {
        
        ArrayList<String> digits = new ArrayList<>();
        
        try {
                File file = new File(isensestressanalyzer.filereader.
                    FileReader.FOLDER_WITH_DATA + File.separator + 
                SETTINGS_FOLDER + File.separator + DIGITS_HEATMAP);
            
            BufferedReader reader  = new BufferedReader(new FileReader(file));
            
            String line;
            while ((line = reader.readLine()) != null) {
                digits.add(line);
            }
        }
        catch(FileNotFoundException exc) {
            System.out.println("FileNotFound exception digits_heatmap.csv");
            exc.printStackTrace();
        }
        catch(IOException exc) {
            System.out.println("IOException in reading digits_heathmap.csv");
            exc.printStackTrace();
        }
        
        
        return digits;
    }
}
