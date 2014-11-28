package isensestressanalyzer.interaction;

import isensestressanalyzer.utils.Point;
import java.util.ArrayList;

/**
 * This class represents an Vertical Scroll on the screen during the Search
 * exercise
 * 
 * @author Matteo Ciman
 */
public class VerticalScroll extends Scroll 
{
    public VerticalScroll(long startTimestamp, long endTimestamp, 
            ArrayList<Interaction> mListInteraction, ArrayList<ScrollMovement> movements)
    {
        super(startTimestamp, endTimestamp, mListInteraction, movements);
    }
    
    /**
     * Calculates the linearity of the Vertical scroll as the ratio of the difference
     * between the X coordinate of the first and last point of the 
     * scroll over the entire width of the screen
     * @param width the width of the screen
     * @return ratio distance X start and end point over width of the screen
     */
    @Override
    public Double calculateLinearity(int width)
    {
        if (mListInteraction.isEmpty())
        {
            return null;
        }
        Point firstPoint = mListInteraction.get(0).getPoint(),
                endPoint = mListInteraction.get(mListInteraction.size() - 1).getPoint();
        
        return (double) Math.abs(firstPoint.getX() - endPoint.getX()) / (float) width;
    }
    
    /**
     * Calculates the linearity of the scroll considering all the points of the
     * interaction and not only the first and the last one
     * @param width the width of the screen
     * @return the linearity of the scroll as sum of the linearity of all the
     * interaction points
     */
    @Override
    public Double calculateLinearityAsSumOfEveryPoint(int width)
    {
        double sum = 0; 
        for (int i = 1; i < mListInteraction.size(); i++)
        {
            Point before = mListInteraction.get(i - 1).getPoint(),
                    current = mListInteraction.get(i).getPoint();
            
            sum += Math.abs(current.getX() - before.getX());
        }
        
        return sum / (float) width;
    }
}
