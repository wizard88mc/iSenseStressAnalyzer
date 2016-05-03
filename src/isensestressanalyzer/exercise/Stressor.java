package isensestressanalyzer.exercise;

import isensestressanalyzer.ISenseStressAnalyzer;
import static isensestressanalyzer.exercise.Exercise.replaceDelimiter;
import isensestressanalyzer.interaction.Interaction;
import isensestressanalyzer.interaction.NumberPickerInteraction;
import isensestressanalyzer.utils.ScreenObject;
import java.util.ArrayList;

/**
 * class Stressor
 * 
 * Represents a Stressor exercise
 * 
 * @author Matteo Ciman
 * @version 0.1
 * @since 2014-10-16
 */
public class Stressor extends Exercise {
    
    ArrayList<ScreenObject> numberPickers;
    ArrayList<NumberPickerInteraction> mNumberPickerInteractionsList;
    
    public Stressor(int order, boolean stress, int repetition, 
            int subRepetition, String[] additionalV) {
        
        super(order, ExerciseType.STRESSOR, stress, repetition, subRepetition,
                additionalV);
    }
    
    public ArrayList<ScreenObject> getNumberPickers() {
        return this.numberPickers;
    }
    
    /**
     * Return the starting value of the stressor
     * @return starting value
     */
    public String getStartingValue() {
        return additionalValues.get(0);
    }
    
    /**
     * Returns the decrease value
     * @return the decrease value
     */
    public String getDecreaseValue() {
        return additionalValues.get(1);
    }
    
    /**
     * Returns the answer submitted by the user
     * @return the submitted answer
     */
    public String getAnswerSubmitted() {
        return additionalValues.get(2);
    }
    
    public ArrayList<NumberPickerInteraction> getNumberPickerInteractions() {
        return this.mNumberPickerInteractionsList;
    }

    /**
     * Completes data acquisition retrieve the screen objects and the interaction
     * with these objects
     * @param protocol the protocol to look for
     */
    @Override
    public void completeDataAcquisition(Protocol protocol) {
        
        super.completeDataAcquisition(protocol);
        
        ArrayList<String> additionalInformationFromLayoutFile = 
            ISenseStressAnalyzer.mLayoutReader.
                retrieveAllLayoutInformationPerExercisePerRepetition(protocol, 
                    order, getBasicString(), stress, repetition);
        
        screenObjects = new ArrayList<>();
        numberPickers = new ArrayList<>();
        
        for (String data: additionalInformationFromLayoutFile) {
            
            data = replaceDelimiter(data);
            String elements[] = data.split(",");
            // Add null at the end of the string if it terminates with a comma
            if (elements.length != 8) {
                
               data += "null";
               elements = data.split(",");
            }
            /**
             * elements[0]: ID
             * elements[1]: complete class
             * elements[2]: x position
             * elements[3]: y position
             * elements[4]: width
             * elements[5]: height
             * elements[6]: visibility
             * elements[7]: text / null
             */
            /**
             * The elements is a keyboard object
             */
            if (elements.length == 8 && elements[1].contains("NumberPicker")) {
                
                numberPickers.add(new ScreenObject(Double.valueOf(elements[0]), 
                    elements[1], Float.valueOf(elements[2]), 
                    Float.valueOf(elements[3]), Integer.valueOf(elements[4]), 
                    Integer.valueOf(elements[5]), Integer.valueOf(elements[6]), 
                    elements[7]));
            }
            /**
             * Generic object
             */
            else if (elements.length == 8) {
                
                screenObjects.add(new ScreenObject(Double.valueOf(elements[0]), 
                    elements[1], Float.valueOf(elements[2]), Float.valueOf(elements[3]), 
                    Integer.valueOf(elements[4]), Integer.valueOf(elements[5]), 
                    Integer.valueOf(elements[6]), elements[7]));
            }
        }
        
        retrieveNumberPickerInteractions(protocol);
    }
    
    /**
     * Retrieves all the interactions with the NumberPicker objects
     * @param protocol the protocol to consider
     */
    private void retrieveNumberPickerInteractions(Protocol protocol) {
        
        ArrayList<String> touchInteractionsFromTouchFile = 
                ISenseStressAnalyzer.mTouchReader
                    .retrieveTouchEventPerExercisePerRepetition(protocol, 
                        order, STRESSOR_STRING, stress, repetition);
        
        mNumberPickerInteractionsList = new ArrayList<>();
        
        for (int i = 0; i < touchInteractionsFromTouchFile.size(); i++) {
            
            if (touchInteractionsFromTouchFile.get(i).split(",")[0].equals("0")) {
                
                mNumberPickerInteractionsList.add(
                    createNumberPickerInteraction(touchInteractionsFromTouchFile, 
                        i)); 
            }
        }
    }
    
    /**
     * Creates a NumberPickerInteraction
     * @param stringInteractions the list of string that represent an interaction
     * @param startIndex the index to start to create the interaction
     * @return a new NumberPickerInteraction
     */
    private NumberPickerInteraction createNumberPickerInteraction(
            ArrayList<String> stringInteractions, int startIndex) {
        
        int endIndex = -1;
        ArrayList<Interaction> listInteractions = new ArrayList<>();
        
        listInteractions.add(new Interaction(stringInteractions.get(startIndex)));
        
        for (int i = startIndex + 1; i < stringInteractions.size() && endIndex == -1; 
            i++) {
            
            listInteractions.add(new Interaction(stringInteractions.get(i)));
            if (listInteractions.get(listInteractions.size() - 1).isTouchUp()) {
                endIndex = i;
            }
        }
        
        /**
         * TODO: check if the interaction is inside the NumberPicker
         */
        
        return new NumberPickerInteraction(listInteractions);
    }
}
