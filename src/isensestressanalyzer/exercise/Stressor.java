package isensestressanalyzer.exercise;

/**
 * class Stressor
 * 
 * Represents a Stressor exercise
 * 
 * @author Matteo Ciman
 * @version 0.1
 * @since 2014-10-16
 */
public class Stressor extends Exercise
{
    public Stressor(int order, boolean stress, int repetition, 
            int subRepetition, String[] additionalV) 
    {
        super(order, ExerciseType.STRESSOR, stress, repetition, subRepetition,
                additionalV);
    }
    
    /**
     * Return the starting value of the stressor
     * @return starting value
     */
    public String getStartingValue()
    {
        return additionalValues.get(0);
    }
    
    /**
     * Returns the decrease value
     * @return the decrease value
     */
    public String getDecreaseValue()
    {
        return additionalValues.get(1);
    }
    
    /**
     * Returns the answer submitted by the user
     * @return the submitted answer
     */
    public String getAnswerSubmitted()
    {
        return additionalValues.get(2);
    }    

    @Override
    public void completeDataAcquisition(Protocol protocol) {
    }
}
