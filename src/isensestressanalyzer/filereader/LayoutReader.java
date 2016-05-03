package isensestressanalyzer.filereader;

import isensestressanalyzer.exercise.Protocol;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * class LayoutReader
 * 
 * This class is responsible to read the layout file 
 * 
 * @author  Matteo Ciman
 * @version 0.1
 * @since   2014-10-17
 */
public class LayoutReader extends FileReader
{
    public LayoutReader(String IMEI, String data) {
        super(IMEI, data, "log_layout_data");
    }
    
    /**
     * Retrieves all the layout information for a particular exercise and repetition
     * @param protocol the protocol we are currently considering
     * @param exerciseNumber the order of the exercise
     * @param exercise the String of the exercise
     * @param stress if stress or not
     * @param repetition the number of the repetition
     * @return a List of String with the events saved by the Layout logger
     */
    public ArrayList<String> retrieveAllLayoutInformationPerExercisePerRepetition(
        Protocol protocol, int exerciseNumber, String exercise, boolean stress, 
            int repetition) {
        
        ArrayList<String> layoutInfo = new ArrayList<>();
        
        try {
            
            openFile();
            
            String titleExerciseLookingFor = buildStringForExerciseTitle(exerciseNumber, 
                    exercise, stress);
            
            while (!readLine().contains(protocol.toString())) {}
            
            while (!readLine().contains(titleExerciseLookingFor)) {}
            
            String repetitionLooginkFor = buildStringForRepetition(repetition);
            while (!readLine().contains(repetitionLooginkFor)) {}
            
            String line;
            while ((line = readLine()) != null && !line.contains("*")) {
                layoutInfo.add(line);
            }
            
            closeFile();
        }
        catch(FileNotFoundException exc) {
            System.out.println(exc.toString());
            exc.printStackTrace();
        }
        return layoutInfo;
    }
    
}
