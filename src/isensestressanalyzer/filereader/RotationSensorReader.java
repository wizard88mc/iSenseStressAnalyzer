package isensestressanalyzer.filereader;

import isensestressanalyzer.exercise.Protocol;
import isensestressanalyzer.rotationsensor.RotationSensorData;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * class RotationSensorReader
 * 
 * This class is responsible to read the file with the RotationSensor data and
 * retrieve all the data from this sensor
 * 
 * @author  Matteo Ciman
 * @version 0.1
 * @since   2014-10-16
 */
public class RotationSensorReader extends FileReader
{

    public RotationSensorReader(String IMEI, String data) {
        super(IMEI, data, "log_rotation_sensor_data");
    }
    
    /**
     * Retrieves all the rotation sensor data associated with a particular
     * exercise and a particular repetition
     * 
     * @param protocol the current protocol
     * @param exerciseNumber the number of the exercise
     * @param exercise the String value of the exercise ("STRESSOR", "SURVEY" etc)
     * @param repetition the number of the repetition
     * @param stress if stress or not
     * @return a list of RotationSensorData
     */
    public ArrayList<RotationSensorData> getRotationSensorDataForExercise(
            Protocol protocol, int exerciseNumber, 
            String exercise, int repetition, boolean stress)
    {
        ArrayList<RotationSensorData> data = new ArrayList<>();
        
        try
        {
            openFile();
            String titleExerciseLookingFor = "Exercise " + exerciseNumber + ": " + exercise + "(" + stress + ")";
            String line;
            while (!readLine().contains(protocol.toString())) {}
            
            while (!readLine().contains(titleExerciseLookingFor)) {}
            
            String repetitionLooginkFor = "REPETITION " + repetition;
            while (!readLine().contains(repetitionLooginkFor)) {}
            
            // the * indicates the end of the current repetition
            while (!(line = readLine()).contains("*"))
            {
                // the line contains the values of the rotation sensor
                String[] values = line.replace("(", "").replace(")", "")
                        .split(",");
                
                data.add(new RotationSensorData(Long.valueOf(values[0]), 
                        Float.valueOf(values[1]), Float.valueOf(values[2]), 
                        Float.valueOf(values[3])));
            }
         
            closeFile();
        }
        catch(FileNotFoundException exc)
        {
            System.out.println(exc.toString());
            exc.printStackTrace();
        }
        return data;
    }
}
