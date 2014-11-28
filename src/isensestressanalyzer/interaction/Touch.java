package isensestressanalyzer.interaction;

import isensestressanalyzer.dataanalysis.BasicDataStatistic;
import isensestressanalyzer.utils.Point;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 */
public class Touch 
{
    protected ArrayList<Interaction> listInteractions = null;

    public Touch(ArrayList<Interaction> listInteractions)
    {
        this.listInteractions = listInteractions;
    }
    
    /**
     * Returns the list of the Interaction objects that constitute the touch 
     * operation
     * @return a list of Interaction objects
     */
    public ArrayList<Interaction> getInteractions()
    {
        return this.listInteractions;
    }
    
    /**
     * Returns the duration of the touch operation
     * @return the duration of the touch
     */
    public Double getTouchDuration()
    {
        return (double) (listInteractions.get(listInteractions.size() - 1).getTimestamp() 
                - listInteractions.get(0).getTimestamp()) / 1000;
    }
    
    /**
     * Returns if the Touch operation is valid, i.e. all the interaction objects 
     * of the Touch are valid
     * @return boolean if the Touch is valid or not
     */
    public boolean isValid()
    {
        boolean everythingValid = true;
        for (Interaction mInteraction: listInteractions)
        {
            everythingValid = everythingValid & mInteraction.isValid();
        }
        return everythingValid;
    }
    
    /**
     * Returns basic data analysis of the pressure of the current digit
     * @return a BasicDataObject with basic analysis of the pressure of the 
     * current digit
     */
    public BasicDataStatistic getPressureBasicData()
    {
        ArrayList<Double> values = new ArrayList<>();
        
        for (Interaction interaction: listInteractions)
        {
            if (interaction.mInteractionType == Interaction.InteractionType.TOUCH_DOWN || 
                    interaction.mInteractionType == Interaction.InteractionType.TOUCH_MOVING || 
                    interaction.mInteractionType == Interaction.InteractionType.TOUCH_UP)
            {
                values.add(interaction.pressure);
            }
        }
        return new BasicDataStatistic(values, false);
    }
    
    /**
     * Returns basic data analysis of the size of the current digit
     * @return a BasicDataObject with basic analysis of the size of the current
     * digit
     */
    public BasicDataStatistic getSizeBasicData()
    {
        ArrayList<Double> values = new ArrayList<>();
        
        for (Interaction interaction: listInteractions)
        {
            if (interaction.mInteractionType == Interaction.InteractionType.TOUCH_DOWN || 
                    interaction.mInteractionType == Interaction.InteractionType.TOUCH_MOVING || 
                    interaction.mInteractionType == Interaction.InteractionType.TOUCH_UP)
            {
                values.add(interaction.size);
            }
        }
        return new BasicDataStatistic(values, false);
    }
    
    /**
     * Returns and evaluation of the movement made during the digit. This is the 
     * sum of the distance between all the Interactions that constitute a 
     * digit
     * @return the length of the movement of the digit 
     */
    public Double getTouchMovement()
    {
        double total = 0;
        
        //for (int i = 0; i < listInteractions.size() - 1; i++)
        //{
            //Point first = listInteractions.get(i).getPoint(),
              //      second = listInteractions.get(i+1).getPoint();
        Point first = listInteractions.get(0).getPoint(), 
                second = listInteractions.get(listInteractions.size() - 1).getPoint();
            
            total += (Math.sqrt(
                    Math.pow(first.getX() - second.getX(), 2) + 
                            Math.pow(first.getY() - second.getY(), 2)) / listInteractions.size());
        //}
        
        return (double) total; // / (double) listInteractions.size();
    }
}
