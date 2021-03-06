package isensestressanalyzer.exercise;

import isensestressanalyzer.ISenseStressAnalyzer;
import isensestressanalyzer.rotationsensor.RotationSensorData;
import isensestressanalyzer.utils.ScreenObject;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Exercise class 
 * 
 * This class represents the base class for each exercise. It holds the common
 * data for each exercise, i.e. the type, the order number, the repetition and 
 * the sub repetition
 * 
 * @author   Matteo Ciman
 * @version 0.1
 * @since   2014-10-15
 */
public abstract class Exercise {
    
    protected enum ExerciseType {SURVEY, SEARCH, WRITE, STRESSOR, RELAX};
    public static final String SURVEY_STRING = "SURVEY";
    public static final String SEARCH_STRING = "SEARCH";
    public static final String WRITE_STRING = "WRITE";
    public static final String STRESSOR_STRING = "STRESSOR";
    public static final String RELAX_STRING = "RELAX";
    
    protected final int order;
    protected final ExerciseType mExerciseType;
    protected boolean stress;
    protected final int repetition; 
    protected final int subRepetition;
    protected ArrayList<String> additionalValues = null;
    protected ArrayList<ScreenObject> screenObjects = null;
    protected ArrayList<RotationSensorData> listRotationSensorData;
    /**
     * Instantiates a new Exercise object with the given order value, 
     * exercise type, repetition and subrepetition
     * @param order the order of the exercise in the whole set of exercises
     * @param exerciseType the type of exercise as string
     * @param stress if stress exercise or not
     * @param repetition number of the repetition
     * @param subRepetition number of the current subrepetition (used only with
     * the stressor)
     * @param additionalV the additional results specific of each exercise
     */
    public Exercise(int order, ExerciseType exerciseType, boolean stress,
            int repetition, int subRepetition, String[] additionalV) {
        
        this.order = order; this.repetition = repetition; this.stress = stress;
        this.mExerciseType = exerciseType; this.subRepetition = subRepetition;
        
        additionalValues = new ArrayList<>();
        additionalValues.addAll(Arrays.asList(additionalV));
    }
    
    /**
     * Returns the String corresponding to the current type of the exercise
     * 
     * @return the String of the exercise type 
     */
    public String getBasicString() {
        
        switch (mExerciseType) {
            case SURVEY: {
                return SURVEY_STRING;
            }
            case SEARCH: {
                return SEARCH_STRING;
            }
            case WRITE: {
                return WRITE_STRING;
            }
            case STRESSOR: {
                return STRESSOR_STRING;
            }
            default: {
                return RELAX_STRING;
            }
        }
    }
    
    public void completeDataAcquisition(Protocol protocol) {
        
        this.listRotationSensorData = 
            isensestressanalyzer.ISenseStressAnalyzer.mRotationSensorReader
                .getRotationSensorDataForExercise(protocol, order, 
                getBasicString(), repetition, stress);
    }
    
    /**
     * Returns the collected data from the rotation sensor
     * @return the data from the rotation sensor data
     */
    public ArrayList<RotationSensorData> getRotationSensorData() {
        return this.listRotationSensorData;
    }
    
    /**
     * Eliminates all the delimiter for the beginning and the end of the current 
     * line
     * 
     * @param string the string to clean up
     * @return the cleaned up string
     */
    public static String replaceDelimiter(String string) {
        
        String[] elementsToEliminate = {"[", "]", "(", ")"};
        for (String element: elementsToEliminate) {
            
            string = string.replace(element, "");
        }
        return string;
    }
    
    /**
     * Returns if the exercise is under stress condition or not
     * @return if stress or not
     */
    public boolean stress() {
        return this.stress;
    }
    
    public ArrayList<String> getAdditionalValues() {
        return this.additionalValues;
    }
    
    public void setStress(boolean stress) {
        this.stress = stress;
    }
    
    public ArrayList<RotationSensorData> getListRotationSensorData() {
        
        return this.listRotationSensorData;
    }
    
    /**
     * Retrieves an object on the screen
     * @param targetClass the class of the object
     * @param text the text of the object
     * @return The ScreenObject if found, null otherwise
     */
    public ScreenObject findScreenObject(String targetClass, String text) {
    	
    	ScreenObject object = null;
    	
    	for (int i = 0; i < screenObjects.size() && object == null; i++) {
            
            if (screenObjects.get(i).getObjectClass().contains(targetClass) && 
                screenObjects.get(i).getText().equals(text)) {
    		
                object = screenObjects.get(i);
            }
    	}
    	
    	return object;
    }
}
