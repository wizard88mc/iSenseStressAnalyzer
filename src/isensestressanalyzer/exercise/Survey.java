package isensestressanalyzer.exercise;

import java.util.ArrayList;

/**
 * class Survey
 * 
 * Represents a Survey exercise with the answers provided by the user
 * 
 * @author  Matteo Ciman
 * @version 0.1 
 * @since   2014-10-15
 */
public class Survey extends Exercise 
{
    public Survey(int order, boolean stress, int repetition,
            int subRepetition, String[] additionalV) {
        
        super(order, ExerciseType.SURVEY, stress, repetition, subRepetition,
                additionalV);
    }
    
    /**
     * Returns the valence value of the survey
     * @return the valence value
     */
    public String getValence() {
        return (String) additionalValues.get(0);
    }
    
    /**
     * Returns the energy value of the survey
     * @return the energy value
     */
    public String getEnergy() {
        return (String) additionalValues.get(1);
    }
    
    /**
     * Returns the stress value of the survey
     * @return the stress value
     */
    public String getStress() {
        return (String) additionalValues.get(2);
    }
    
    /**
     * Returns the valence value as integer
     * @return valence as integer
     */
    public int getIntValence() {
        return  Integer.valueOf(additionalValues.get(0));
    }
    
    /**
     * Returns the energy value as integer
     * @return energy as integer
     */
    public int getIntEnergy() {
        return Integer.valueOf(additionalValues.get(1));
    }
    
    /**
     * Returns the stress value as integer
     * @return stress as integer
     */
    public int getIntStress() {
        return Integer.valueOf(additionalValues.get(2));
    }
    
    @Override
    public void completeDataAcquisition(Protocol protocol) {
        
    }
}
