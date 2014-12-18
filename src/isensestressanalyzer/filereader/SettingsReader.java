package isensestressanalyzer.filereader;

import isensestressanalyzer.exercise.Exercise;
import isensestressanalyzer.exercise.Protocol;
import isensestressanalyzer.exercise.Search;
import isensestressanalyzer.exercise.Stressor;
import isensestressanalyzer.exercise.Survey;
import isensestressanalyzer.exercise.Write;
import isensestressanalyzer.utils.PhoneSettings;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * class SettingsReader
 * 
 * Is responsible to read the Settings file, that holds the information 
 * of the phone and the results of all the exercises
 * 
 * @author Matteo
 */
public class SettingsReader extends FileReader
{
    private String stringForGameParticipation = null;
    
    public SettingsReader(String IMEI, String data) {
        super(IMEI, data, "log_settings_data");
    }
    
    /**
     * Builds a PhoneSettings object retrieving the smartphone specs
     * @return a PhoneSettings object containing the smartphone specs
     */
    public PhoneSettings getPhoneSettings()
    {
        String line, date = null, manufacturer = null, language = null;
        long screenWidth = 0, screenHeight = 0;
        
        try 
        {
            openFile();
        
            line = readLine();
            
            while (language == null) 
            {
                if (!line.contains("/")) {
                    /**
                     * Relevant information is only in the second part of the 
                     * string after the :
                     */
                    String interesting = (line.split(":")[1]).replace("*", "").trim();
                    if (date == null) {
                        date = interesting.substring(0, interesting.length() - 2);
                    }
                    else if (manufacturer == null) {
                        
                        manufacturer = interesting;
                    }
                    else if (screenHeight == 0 && screenWidth == 0) {
                        
                        String[] widthAndHeight = interesting.split("x");
                        screenWidth = Long.valueOf(widthAndHeight[0]);
                        screenHeight = Long.valueOf(widthAndHeight[1]);
                    }
                    else if (language == null)
                    {
                        language = interesting;
                    }
                    else if (stringForGameParticipation == null && line.contains("User")) {
                        stringForGameParticipation = line;
                    }
                }
                line = readLine();
            }
        }
        catch(FileNotFoundException exc)
        {
            System.out.println(exc.toString());
            exc.printStackTrace();
        }
        return new PhoneSettings(date, manufacturer, screenWidth, screenHeight, 
                language);
    }
    
    /**
     * Retrieves all the exercises results saved in the Settings file 
     * @param protocol the protocol we are currently analyzing
     * @return a list of Exercise object with the results and the settings saved
     */
    public ArrayList<Exercise> getExercisesResults(Protocol protocol)
    {
        ArrayList<Exercise> exercises = new ArrayList<>();
        
        try
        {
            openFile();
            
            String line = null; boolean completed = false;
            while ((line = readLine()) != null && 
                    !line.contains(protocol.toString())) {}
            
            while ((line = readLine()) != null && !completed)
            {
                // Checking if the line contains exercise info
                if (line.contains("(")) 
                {
                    String[] elements = line.replace("(", "").replace(")", "").split(",");
                    /**
                     * elements[0]: exercise order
                     * elements[1]: exercise type
                     * elements[2]: stress
                     * elements[3]: repetition
                     * elements[4]: subrepetition
                     * elements[5..end]: additional exercise specific values
                     */
                    String[] additionalValues = Arrays.copyOfRange(elements, 5, elements.length);
                    
                    switch (elements[1]) 
                    {
                        case Exercise.SURVEY_STRING: 
                        {
                            exercises.add(new Survey(Integer.valueOf(elements[0]), 
                                    Boolean.valueOf(elements[2]),
                                    Integer.valueOf(elements[3]), 
                                    Integer.valueOf(elements[4]), 
                                    additionalValues));
                            break;
                        }
                        case Exercise.SEARCH_STRING:
                        {
                            exercises.add(new Search(Integer.valueOf(elements[0]), 
                                    Boolean.valueOf(elements[2]),
                                    Integer.valueOf(elements[3]), 
                                    Integer.valueOf(elements[4]), 
                                    additionalValues));
                            break;
                        }
                        case Exercise.WRITE_STRING:
                        {
                            exercises.add(new Write(Integer.valueOf(elements[0]), 
                                    Boolean.valueOf(elements[2]),
                                    Integer.valueOf(elements[3]),
                                    Integer.valueOf(elements[4]),
                                    additionalValues));
                            break;
                        }
                        case Exercise.STRESSOR_STRING: 
                        {
                            exercises.add(new Stressor(Integer.valueOf(elements[0]), 
                                    Boolean.valueOf(elements[2]),
                                    Integer.valueOf(elements[3]), 
                                    Integer.valueOf(elements[4]),
                                    additionalValues));
                            break;
                        }
                        default:
                        {
                            break;
                        }
                    }
                }
                else if (line.contains("PROTOCOL"))
                {
                    completed = true;
                }
            }
        }
        catch(FileNotFoundException exc)
        {
            System.out.println(exc.toString());
            exc.printStackTrace();
        }
        
        closeFile();
        
        return exercises;
    }
    
    /**
     * Checks if a specific protocol is inside the Settings file and if it
     * is complete
     * @param protocol the protocol we want to check if inside the file
     * @return if the protocol is inside the settings file or not
     */
    public boolean checkForSpecificProtocol(Protocol protocol)
    {
        /**
         * First step is to check is if the prolog of the protocol is inside 
         * the file
         */
        try
        {
            List<String> lines = Files.readAllLines(Paths.get(file.toURI()), 
            		Charset.forName("UTF-8"));
            String completeFile = "";
            for (String line: lines)
            {
                completeFile += line;
            }
            
            int indexStart = completeFile.indexOf(protocol.toString());
            if (indexStart != -1)
            {
                int startSteps = completeFile.indexOf("(", indexStart);
                int indexEndProtocol = completeFile.indexOf("PROTOCOL", indexStart + 10);
                if (indexEndProtocol == -1)
                {
                    indexEndProtocol = Integer.MAX_VALUE;
                }
                
                for (String step: protocol.getSteps())
                {
                    if (!step.contains("RELAX") && !step.contains("WAIT_SECOND_STEP"))
                    {
                        int indexStep = completeFile.indexOf(step, startSteps);
                        if (indexStep != -1 && indexStep < indexEndProtocol)
                        {
                            startSteps = completeFile.indexOf("(", indexStep + 1);
                        }
                        else
                        {
                           return false;
                        }
                   }
                }
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(IOException exc)
        {
            System.out.println("Exception in SettingsReader:checkForSpecificProtocol");
            exc.printStackTrace();
            return false;
        }
    }
    
    public String getStringUser() {
        return stringForGameParticipation;
    }
}
