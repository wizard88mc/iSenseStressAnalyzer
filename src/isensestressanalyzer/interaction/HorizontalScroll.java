package isensestressanalyzer.interaction;

import isensestressanalyzer.utils.Point;
import java.util.ArrayList;

/**
 * This class represents an Horizontal Scroll during the Search exercise
 * @author Matteo Cimsn
 */
public class HorizontalScroll extends Scroll
{
    public HorizontalScroll(long startTimestamp, long endTimestamp, 
            ArrayList<Interaction> mListInteraction, ArrayList<ScrollMovement> movements)
    {
        super(startTimestamp, endTimestamp, mListInteraction, movements);
    }
    
    /**
     * Calculates the linearity of the Horizontal scroll as the ratio of the 
     * difference between the first and the last point of the interaction
     * @param height the height of the screen
     * @return the linearity of the scroll
     */
    @Override
    public Double calculateLinearity(int height)
    {
        if (mListInteraction.isEmpty())
        {
            return null;
        }
        Point firstPoint = mListInteraction.get(0).getPoint(),
                endPoint = mListInteraction.get(mListInteraction.size() - 1).getPoint();
        
        return (double) Math.abs(firstPoint.getY() - endPoint.getY()) / (float) height;
    }
    
    /**
     * Calculates the linearity of the scroll as sum of the linearity of 
     * each interaction element that belongs to the scroll 
     * @param height the height of the screen
     * @return the linearity of the scroll as linearity of each interaction 
     * element
     */
    @Override
    public Double calculateLinearityAsSumOfEveryPoint(int height)
    {
        double sum = 0; 
        for (int i = 1; i < mListInteraction.size(); i++)
        {
            Point before = mListInteraction.get(i - 1).getPoint(),
                    current = mListInteraction.get(i).getPoint();
            
            sum += Math.abs(current.getY() - before.getY());
        }
        
        return sum / (float) height;
    }
}
