package isensestressanalyzer.interaction;

import isensestressanalyzer.utils.ScreenObject;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 */
public class NumberPickerInteraction {
    
    ArrayList<Interaction> mListInteractions;
    
    public NumberPickerInteraction(ArrayList<Interaction> listInteractions) {
        this.mListInteractions = listInteractions;
    }
    
    public ArrayList<Interaction> getListInteractions() {
        return this.mListInteractions;
    }
    
    /**
     * Checks if the first interaction is inside the NumberPicker
     * @param numberPicker the NumberPicker to check
     * @return true if the interaction starts inside the NumberPicker, false
     * otherwise
     */
    public boolean isInside(ScreenObject numberPicker) {
        
        Interaction firstInteraction = mListInteractions.get(0);
        
        return firstInteraction.isValid() && 
            firstInteraction.getPoint().getX() >= 
                numberPicker.getPosition().getX() && 
            firstInteraction.getPoint().getY() >= 
                numberPicker.getPosition().getY() && 
            firstInteraction.getPoint().getX() <= 
                numberPicker.getPosition().getX() + numberPicker.getWidth() && 
            firstInteraction.getPoint().getY() <= 
                numberPicker.getPosition().getY() + numberPicker.getHeight();
    }
}
