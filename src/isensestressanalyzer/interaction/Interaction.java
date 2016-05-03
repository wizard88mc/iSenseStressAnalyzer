package isensestressanalyzer.interaction;

import isensestressanalyzer.exercise.Exercise;
import isensestressanalyzer.utils.Point;

/**
 * class Interaction
 * 
 * This class represents a single interaction with a screen, where an interaction
 * is a sequence of  TouchData beginning with InteractionType = 0 and ending
 * with InteractionType = 1
 * 
 * @author  Matteo Ciman
 * @version 0.1
 * @since   2014-10-17
 */
public class Interaction {
    
    public enum InteractionType {TOUCH_DOWN, TOUCH_UP, TOUCH_MOVING};
    public static final String TOUCH_DOWN_STRING = "0";
    public static final String TOUCH_UP_STRING = "1";
    public static final String TOUCH_MOVING_STRING = "2";
    
    protected InteractionType mInteractionType;
    protected long timestamp;
    protected Point mPoint;
    protected Double size;
    protected Double pressure;
    protected long firstEventTimestamp;
    
    public Interaction(String stringInteraction) {
        
        stringInteraction = Exercise.replaceDelimiter(stringInteraction);
        String[] elements = stringInteraction.split(",");
        /**
         * elements[0]: interaction (0,1,2)
         * elements[1]: timestamp
         * elements[2]: x position
         * elements[3]: y position
         * elements[4]: size
         * elements[5]: pressure
         * elements[6]: firstEventTimestamp
         */
        if (elements.length == 7) {
            this.buildInteraction(elements[0], Long.valueOf(elements[1]), 
                Math.round(Float.valueOf(elements[2])), 
                Math.round(Float.valueOf(elements[3])), Float.valueOf(elements[4]), 
                Float.valueOf(elements[5]), Long.valueOf(elements[6]));
        }
        else {
            this.timestamp = -1;
        }
    }
    
    protected final void buildInteraction(String interaction, long timestamp, 
            int x, int y, double size, double pressure, 
            long firstEventTimestamp) {
        
        this.timestamp = timestamp; this.mPoint = new Point(x, y);
        this.size = size; this.pressure = pressure; 
        this.firstEventTimestamp = firstEventTimestamp;
        
        switch(interaction) {
            case TOUCH_DOWN_STRING: {
                mInteractionType = InteractionType.TOUCH_DOWN;
                break;
            }
            case TOUCH_UP_STRING: {
                mInteractionType = InteractionType.TOUCH_UP;
                break;
            }
            case TOUCH_MOVING_STRING: {
                mInteractionType = InteractionType.TOUCH_MOVING;
                break;
            }
            default: {
                mInteractionType = null;
            }
        }
    }
    
    /**
     * Returns the position of the interaction
     * @return the interaction Point
     */
    public Point getPoint() {
        return this.mPoint;
    }
    
    public boolean isValid() {
        return mInteractionType != null;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public boolean isTouchDown() {
        return isValid() && mInteractionType == InteractionType.TOUCH_DOWN;
    }
    
    public boolean isTouchMove() {
        return isValid() && mInteractionType == InteractionType.TOUCH_MOVING;
    }
    
    public boolean isTouchUp() {
        return isValid() && mInteractionType == InteractionType.TOUCH_UP;
    }
}
