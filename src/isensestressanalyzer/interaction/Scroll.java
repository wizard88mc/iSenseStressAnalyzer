package isensestressanalyzer.interaction;

import isensestressanalyzer.utils.Point;
import java.util.ArrayList;

/**
 * This class represents a generic scroll
 * @author Matteo Ciman
 */
public class Scroll 
{
    protected final long startTimestamp;
    protected final long endTimestamp;
    protected final ArrayList<Interaction> mListInteraction;
    protected final ArrayList<ScrollMovement> mListScrollMovement;
    
    public Scroll(long start, long end, ArrayList<Interaction> mListInteraction, 
            ArrayList<ScrollMovement> mListScrollMovement)
    {
        this.startTimestamp = start; this.endTimestamp = end;
        this.mListInteraction = mListInteraction; 
        this.mListScrollMovement = mListScrollMovement;
    }
    
    public long getStartTimestamp()
    {
        return this.startTimestamp;
    }
    
    public long getEndTimestamp()
    {
        return this.endTimestamp;
    }
    
    /**
     * Calculates the average pressure during the scroll action
     * @return the average pressure
     */
    public double calculateAveragePressure() {
    	double mean = 0; int counter = 0;
    	
    	for (Interaction interaction: mListInteraction) {
    		if (interaction.pressure != 0.0) {
    			mean += interaction.pressure;
    			counter++;
    		}
    	}
    	return mean / counter;
    }
    
    /**
     * Calculates the average size during the scroll action
     * @return the average size
     */
    public double calculateAverageSize() {
    	double mean = 0; int counter = 0;
    	
    	for (Interaction interaction: mListInteraction) {
    		if (interaction.size != 0.0) {
    			mean += interaction.size;
    			counter++;
    		}
    	}
    	
    	return mean / counter;
    }
    
    /**
     * Calculates the delta of the scroll as the difference of the starting
     * top scroll to the end top scroll (or from the starting left to the end
     * left in case of horizontal scroll)
     * @return difference between the starting top and the end top (or the 
     * starting left and the end left in case of horizontal scroll)
     */
    public double calculateScrollDelta()
    {
        ScrollMovement first = mListScrollMovement.get(0),
                last = mListScrollMovement.get(mListScrollMovement.size() - 1);
        
        return (Math.abs(first.getOldTop() - last.getTop()) + 
                Math.abs(first.getOldLeft() - last.getLeft()));
    }
    
    /**
     * Calculates the duration of the scroll as end timestamp - start timestamp
     * @return time length of the scroll
     */
    public double calculateScrollTimeLength()
    {
        return endTimestamp - startTimestamp;
    }
    
    /**
     * Length of the scroll interaction as sum of the distance between all the 
     * interactions with the screen that compose a scroll
     * @return the length of the scroll
     */
    public double calculateScrollInteractionLength()
    {
        double length = 0; 
        for (int i = 0; i < mListInteraction.size() - 1; i++)
        {
            Point first = mListInteraction.get(i).getPoint(), 
                    second = mListInteraction.get(i + 1).getPoint();
            
            length += Math.sqrt(Math.pow(second.getX() - first.getX(), 2) + 
                    Math.pow(second.getY() - first.getY(), 2));
        }
        return length;
    }
    
    /**
     * Calculates the speed of the scroll movement as ratio of the scroll delta
     * over time length
     * @return the speed of the scroll movement
     */
    public double calculateSpeedScrollDelta()
    {
        return calculateScrollDelta() / calculateScrollTimeLength();
    }
    
    /**
     * Calculates the speed of the scroll interaction as ratio of the interaction
     * space length over time length
     * @return the speed of the scroll interaction
     */
    public double calculateSpeedScrollInteraction()
    {
        return calculateScrollInteractionLength() / calculateScrollTimeLength();
    }
    
    /**
     * Calculates the ratio between the delta of the scroll and the length of 
     * the scroll interaction
     * @return ratio of scroll delta over interaction space length
     */
    public double calculateRatioDeltaScrollOverTouchDistance()
    {
        return calculateScrollDelta() / calculateScrollInteractionLength();
    }
    
    /**
     * Calculates the mean distance of a scroll interaction with respect to a 
     * particular point of the screen (could be the center of the screen or the
     * top left corner)
     * @param x x coordinate of the point
     * @param y y coordinate of the target point 
     * @return mean distance between the interaction and a target point
     */
    public Double meanDistanceFromPoint(int x, int y)
    {
        int counter = 0; double mean = 0;
        
        /*for (Interaction interaction: mListInteraction)
        {
            Point point = interaction.getPoint();
            mean += Math.sqrt(Math.pow(point.getX() - x, 2) + 
                    Math.pow(point.getY() - y, 2));
            counter++;
        }
        mean /= counter;*/
        
        if (mListInteraction.size() != 0) {
	        Point point = mListInteraction.get(0).getPoint(),
	        		lastPoint = mListInteraction.get(mListInteraction.size() - 1).getPoint();
	        mean = Math.sqrt(Math.pow(point.getX() - x, 2) + 
	        		Math.pow(point.getY() - y, 2)) + 
	        		Math.sqrt(Math.pow(lastPoint.getX() - x, 2) + 
	        				Math.pow(lastPoint.getY() - y, 2));
	        
	        return mean / 2;
        }
        else {
        	return 0.0;
        }
    }
    
    public Double calculateLinearity(int screenSize)
    {
        return 0.0;
    }
    
    //TODO
    public Double calculateLinearityAsSumOfEveryPoint(int screenSize) {
    	return 0.0;
    }
}
