package isensestressanalyzer;

import isensestressanalyzer.analyzer.SearchAnalyzer;
import isensestressanalyzer.analyzer.WriteAnalysisResume;
import isensestressanalyzer.analyzer.WriteAnalyzer;
import isensestressanalyzer.exercise.Exercise;
import isensestressanalyzer.exercise.Protocol;
import isensestressanalyzer.exercise.Write;
import isensestressanalyzer.filereader.FilesInputReader;
import isensestressanalyzer.filereader.LayoutReader;
import isensestressanalyzer.filereader.RotationSensorReader;
import isensestressanalyzer.filereader.SettingsReader;
import isensestressanalyzer.filereader.TouchReader;
import java.util.ArrayList;
import tester.Test;
import tester.Tester;

/**
 *
 * @author  Matteo Ciman
 * @version 0.1
 * @since   2014-10-15
 */
public class ISenseStressAnalyzer 
{
    public static FilesInputReader mFilesInputReader = null;
    public static SettingsReader mSettingsReader = null;
    public static TouchReader mTouchReader = null;
    public static LayoutReader mLayoutReader = null;
    public static RotationSensorReader mRotationSensorReader = null;
    /**
     * List of the possible protocols followed
     */
    /*public static final Protocol protocols[] = new Protocol[]{
        new Protocol("SURVEY,false", "RELAX,false", "SURVEY,false", "SEARCH,false",
            "WRITE,false","SURVEY,false","STRESSOR,true","SURVEY,false","SEARCH,true",
            "WRITE,true","SURVEY,false"), 
        new Protocol("SURVEY,false", "RELAX,false","SURVEY,false","SEARCH,false",
            "WRITE,false","SURVEY,false","STRESSOR,false","SEARCH,true","WRITE,true",
            "SURVEY,false"), 
        new Protocol("SURVEY,false", "RELAX,false","SURVEY,false","SEARCH,false",
            "WRITE,false","SURVEY,false","STRESSOR,true","SURVEY,false","SEARCH,false",
            "WRITE,false","SURVEY,false"), 
        new Protocol("SURVEY,false", "RELAX,false", "SURVEY,false", "SEARCH,false", 
            "WRITE,false","SURVEY,false", "WAIT_SECOND_STEP,false","STRESSOR,true",
            "SURVEY,false","SEARCH,true","WRITE,true","SURVEY,false")
    };*/
    
    public static final Protocol protocols[] = new Protocol[] {
        new Protocol("SURVEY,false", "RELAX,false","SURVEY,false","SEARCH,false",
            "WRITE,false","SURVEY,false","STRESSOR,true","SURVEY,false","SEARCH,true",
            "WRITE,true","SURVEY,false")
    };
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        FilesInputReader reader = new FilesInputReader();
        ArrayList<String> listFiles = reader.getAllInputFiles();
        ArrayList<Tester> listTester = new ArrayList<>();
        
        /**
         * Each input file represents an IMEI followed by a list of files names
         * that represent a test for a particular tester
         */
        for (String input: listFiles)
        {
            String[] elements = input.split(",");
            
            /**
             * elements[0]: IMEI of the user
             * elements[1..n-1]: all the exercises performed by the tester
             */
            for (int i = 1; i < elements.length; i++)
            {
                Tester tester = new Tester(elements[0]);
                
                mSettingsReader = new SettingsReader(elements[0], 
                        elements[i]);
                
                tester.setPhoneSettings(mSettingsReader.getPhoneSettings());

                /**
                 * Check if the Settings file contains the protocol. If not, this 
                 * file cannot be used to extract data
                 */
                for (Protocol protocol: protocols)
                {
                    boolean isProtocolPresent = mSettingsReader.checkForSpecificProtocol(protocol);

                    /**
                     * If the protocol is present means that I can extract the data 
                     * from the other files, otherwise I have to discard it 
                     */
                    if (isProtocolPresent)
                    {  
                        ArrayList<Exercise> listExercises = 
                                mSettingsReader.getExercisesResults(protocol);
                        
                        clearWrongExercises(listExercises);

                        for (Exercise exercise: listExercises)
                        {
                            mTouchReader = new TouchReader(elements[0], elements[i]);
                            mLayoutReader = new LayoutReader(elements[0], elements[i]);
                            mRotationSensorReader = new RotationSensorReader(elements[0], elements[i]);
                            // Data retrieving and analysis for each exercise (work both
                            // intra or inter user)
                            exercise.completeDataAcquisition(protocol);
                        }
                        
                        Tester.touchExerciseClassification(listExercises);
                        tester.addTest(new Test(protocol, listExercises));
                    }
                }
                listTester.add(tester);
                System.out.println("***** TESTER: " + tester.getName() + " ******");
                
            }
        }
        
        ArrayList<WriteAnalysisResume> resumesForTesters = new ArrayList<>();
        for (Tester tester: listTester)
        {
            resumesForTesters.add(WriteAnalyzer.performingGlobalAnalysis(tester));
        }
        
    }
    
    private static void clearWrongExercises(ArrayList<Exercise> exercises)
    {
        ArrayList<Exercise> exercisesToEliminate = new ArrayList<>();
        
        for (Exercise exercise: exercises)
        {
            if (exercise instanceof Write)
            {
                Write write = (Write) exercise;
                if (write.getAdditionalValues().size() < 6 || 
                        write.getWrittenText().length() < write.getTextToWrite().length() / 3)
                {
                    exercisesToEliminate.add(exercise);
                }
            }
        }
        
        for (Exercise exercise: exercisesToEliminate)
        {
            exercises.remove(exercise);
        }
        
        exercisesToEliminate.clear();
    }
}

