package isensestressanalyzer.filereader;

import isensestressanalyzer.exercise.Exercise;
import isensestressanalyzer.exercise.Protocol;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * class TouchReader
 * 
 * This class is responsible to retrieve the touch data associated with a particular
 * exercise
 * 
 * @author Matteo Ciman
 * @version 0.1
 * @since 2014-10-16
 */
public class TouchReader extends FileReader {

    public TouchReader(String IMEI, String data) {
        super(IMEI, data, "log_touch_data");
    }
    
    /**
     * Returns a list of strings that recorder the touch event for a particular exercise
     * and repetition
     * @param protocol the protocol we are currently considering
     * @param exerciseNumber the number of the exercise
     * @param exercise the String of the exercise ("SURVEY", "STRESSOR" etc)
     * @param stress if stress or not
     * @param repetition the repetition of the exercise
     * @return a List of String containing the recorded events
     */
    public ArrayList<String> retrieveTouchEventPerExercisePerRepetition(
            Protocol protocol, int exerciseNumber, 
            String exercise, boolean stress, int repetition) {
        
        ArrayList<String> eventsRecorded = new ArrayList<>();
        
        try {
            
            openFile();
            
            String titleExerciseLookingFor = buildStringForExerciseTitle(exerciseNumber, 
                    exercise, stress); 
            String line;
            
            // Moving to the right protocol
            while (!readLine().contains(protocol.toString())) {}
            
            // Moving to the right exercise
            while (!readLine().contains(titleExerciseLookingFor)) {}
            
            String repetitionLooginkFor = buildStringForRepetition(repetition);
            while (!readLine().contains(repetitionLooginkFor)) {}
            
            while (!(line = readLine()).contains("*"))
            {
               eventsRecorded.add(Exercise.replaceDelimiter(line));
            }
            
            closeFile();
        }
        catch(FileNotFoundException exc) {
            System.out.println(exc.toString());
            exc.printStackTrace();
        }
        return eventsRecorded;
    }
}
