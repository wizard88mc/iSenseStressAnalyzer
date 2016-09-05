package isensestressanalyzer;

import isensestressanalyzer.analyzer.SearchAnalyzer;
import isensestressanalyzer.analyzer.SurveyAnalyzer;
import isensestressanalyzer.analyzer.WriteAnalyzer;
import isensestressanalyzer.dataanalysis.StressorTouchesPositionFeatures;
import isensestressanalyzer.dataanalysis.WriteKeyboardFeatures;
import isensestressanalyzer.exercise.Exercise;
import isensestressanalyzer.exercise.Protocol;
import isensestressanalyzer.exercise.Write;
import isensestressanalyzer.filereader.FilesInputReader;
import isensestressanalyzer.filereader.LayoutReader;
import isensestressanalyzer.filereader.RotationSensorReader;
import isensestressanalyzer.filereader.SettingsReader;
import isensestressanalyzer.filereader.TouchReader;
import isensestressanalyzer.heatmap.StressorTouchesHeatmapCreator;
import isensestressanalyzer.heatmap.WriteTouchesHeatmapCreator;
import isensestressanalyzer.tester.Test;
import isensestressanalyzer.tester.Tester;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author  Matteo Ciman
 * @version 0.1
 * @since   2014-10-15
 */
public class ISenseStressAnalyzer {
    
    private static final String STATISTICAL_ANALYSIS = "1";
    private static final String HEATMAP = "2";
    
    public static FilesInputReader mFilesInputReader = null;
    public static SettingsReader mSettingsReader = null;
    public static TouchReader mTouchReader = null;
    public static LayoutReader mLayoutReader = null;
    public static RotationSensorReader mRotationSensorReader = null;
    public static ArrayList<File> listCreatedFiles = new ArrayList<>();
    
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
    
    public static final Protocol PROTOCOLS[] = new Protocol[] {
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
        for (String input: listFiles) {
            
            String[] elements = input.split(",");
            
            /**
             * elements[0]: IMEI of the user
             * elements[1..n-1]: all the exercises performed by the tester
             */
            for (int i = 1; i < elements.length; i++) {
                
                Tester tester = new Tester(elements[0]);
                
                mSettingsReader = new SettingsReader(elements[0], 
                        elements[i]);
                
                tester.setPhoneSettings(mSettingsReader.getPhoneSettings());
                tester.setUserDetails(mSettingsReader.getStringUser());

                for (Protocol protocol: PROTOCOLS) {
                    
                    /**
                     * Check if the Settings file contains the protocol. If not, this 
                     * file cannot be used to extract data
                     */
                    boolean isProtocolPresent = mSettingsReader.
                            checkForSpecificProtocol(protocol);

                    /**
                     * If the protocol is present means that I can extract the data 
                     * from the other files, otherwise I have to discard it 
                     */
                    if (isProtocolPresent) {
                        
                        ArrayList<Exercise> listExercises = 
                            mSettingsReader.getExercisesResults(protocol);
                        
                        cleanWrongExercises(listExercises);

                        for (Exercise exercise: listExercises) {
                            
                            mTouchReader = new TouchReader(elements[0], 
                                elements[i]);
                            mLayoutReader = new LayoutReader(elements[0], 
                                elements[i]);
                            mRotationSensorReader = 
                                new RotationSensorReader(elements[0], 
                                    elements[i]);
                            /**
                             * Data retrieving and analysis for each exercise
                             * (work both intra or inter user)
                             */
                            exercise.completeDataAcquisition(protocol);
                        }
                        
                        Tester.touchExerciseClassification(listExercises);
                        tester.addTest(new Test(protocol, listExercises));
                    }
                }
                listTester.add(tester);
                //System.out.println("***** TESTER: " + tester.getName() + " ******");
            }
        }
        
        if (args[0].contains(STATISTICAL_ANALYSIS)) {
            
            for (Tester tester: listTester) {

                tester.getWriteAnalyzer().performAnalysis(tester);
                tester.getSearchAnalyzer().performAnalysis(tester);

                WriteAnalyzer.performLocalAnalysis(tester);
                SearchAnalyzer.performLocalAnalysis(tester);

                //tester.createARFFFileForWriteTask();
                tester.createARFFFileForSearchTask();
                    //WriteAnalyzer.createHeatMapForDigit(tester, " ");
            }

            SurveyAnalyzer.performAnalysis(listTester);
            WriteAnalyzer.performGlobalAnalysis(listTester);
            SearchAnalyzer.performGlobalAnalysis(listTester);

            WriteAnalyzer.printPercentageSingleFeature();
            SearchAnalyzer.printPercentageSingleFeature();

            //Tester.createGlobalARFF(listTester);

            //ClassifiersTester tester = new ClassifiersTester(listCreatedFiles);
            //tester.performEvaluation();
        }
        if (args[0].contains(HEATMAP)) {
            
            System.out.println("*** Working on Distance Features for Stressor ***");
            new StressorTouchesPositionFeatures().workWithNumberPickers(listTester);
            
            System.out.println("*** Creating Heatmap for Stressor ***");
            int counter = 1;
            for (Tester tester: listTester) {
                
                System.out.println("Creating heatpmap for Stressor - Tester " + 
                    counter + "/" + listTester.size());
                
                StressorTouchesHeatmapCreator mapCreator = 
                    new StressorTouchesHeatmapCreator(tester);
                
                mapCreator.createHeatmapForStressors();
                
                counter++;
            }
            
            System.out.println("*** Working on Key Distances Features ***");
            new WriteKeyboardFeatures().workOnKeyDistances(listTester);
            
            System.out.println("*** Creating Heatmap for Digits ***");
            counter = 1;
            for (Tester tester: listTester) {

                System.out.println("Creating heatmap digit write - Tester " + 
                    counter + "/" + listTester.size());
                
                WriteTouchesHeatmapCreator mapCreator = 
                    new WriteTouchesHeatmapCreator(tester);
                mapCreator.createHeatMapForAllDigits();
            }
            
        }
    }
    
    /**
     * Eliminates all the exercises that do not respect minimum requirements
     * @param exercises the list of exercises to test
     */
    private static void cleanWrongExercises(ArrayList<Exercise> exercises) {
        
        ArrayList<Exercise> exercisesToEliminate = new ArrayList<>();
        
        for (Exercise exercise: exercises) {
            
            if (exercise instanceof Write) {
                
                Write write = (Write) exercise;
                if (write.getAdditionalValues().size() < 6 || 
                    write.getWrittenText().length() < write.getTextToWrite().length() / 3) {
                    
                    exercisesToEliminate.add(exercise);
                }
            }
        }
        
        for (Exercise exercise: exercisesToEliminate) {
            
            exercises.remove(exercise);
        }
        
        exercisesToEliminate.clear();
    }
}

