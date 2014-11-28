package isensestressanalyzer.utils;

import isensestressanalyzer.interaction.Interaction;
import isensestressanalyzer.interaction.Touch;

/**
 * class ScreenObject
 * 
 * This class represents a View on the screen, with starting point position,
 * width and height
 * 
 * @author Matteo Ciman
 */
public class ScreenObject 
{    
    protected final double ID;
    protected final String objectClass;
    protected final Point position;
    protected final int width;
    protected final int height;
    protected final int visibility;
    protected final String text;
    
    public ScreenObject(Double id, String objectClass, float x, float y, int width, 
            int height, int visibility, String text)
    {
        int indexInterestingClass = objectClass.lastIndexOf(".");
        this.objectClass = objectClass.substring(indexInterestingClass+1).replace("$", ".");
        this.ID = id; this.position = new Point(x, y); 
        this.width = width; this.height = height; this.visibility = visibility;
        this.text = text.replace(";", ",");
    }
    
    /**
     * Returns the position of the view on the screen
     * @return the View position
     */
    public Point getPosition()
    {
        return this.position;
    }
    
    /**
     * Returns the width of the object
     * @return View width
     */
    public int getWidth()
    {
        return this.width;
    }
    
    /**
     * Returns the height of the object
     * @return view height 
     */
    public int getHeight()
    {
        return this.height;
    }
    
    /**
     * Rates a touch operation on the object. The basic implementation 
     * evaluates the first and the last interaction of the touch
     * @param touch the touch to evaluate
     * @return 0 if the touch is not always inside the object, otherwise the 
     * evaluation of the first interaction + the evaluation of the last interaction 
     */
    public float rateTouch(Touch touch)
    {
        if (isTouchAlwaysInside(touch))
        {
            return rateInteraction(touch.getInteractions().get(0)) + 
                    rateInteraction(touch.getInteractions().get(touch.getInteractions().size() - 1));
        }
        return 0;
    }
    
    /**
     * Checks if the touch operation is always inside the object or not (useful 
     * for buttons or ImageView)
     * @param touch the touch operation to evaluate
     * @return is the touch operation is always inside or not
     */
    protected boolean isTouchAlwaysInside(Touch touch)
    {
        boolean isInside = true;
        for (Interaction interaction: touch.getInteractions())
        {
            isInside = isInside & isInside(interaction);
        }
        return isInside;
    }
    
    /**
     * Checks if the interaction is inside the object
     * @param interaction the interaction to check
     * @return if the interaction is inside the object or not
     */
    protected boolean isInside(Interaction interaction)
    {
        return (interaction.getPoint().getX() >= position.getX() && 
                interaction.getPoint().getX() <= position.getX() + width && 
                interaction.getPoint().getY() >= position.getY() && 
                interaction.getPoint().getY() <= position.getY() + height);
    }
    
    /**
     * Provides an evaluation of the interaction on the object. The interaction
     * is rated 1 if it is in the center of the object, o.5 if inside but not 
     * in the center, 0 otherwise (this could happend only in case of digit where
     * the digits starts outside the object and ends inside)
     * @param interaction the interaction to evaluate
     * @return 1 if in the center of the object, 0.5 inside, 0 outside
     */
    protected float rateInteraction(Interaction interaction)
    {
        if (interaction.getPoint().getX() >= getRealStartingPointX() + width / 4 && 
                interaction.getPoint().getX() <= getRealStartingPointX() + (width * 3 / 4) && 
                interaction.getPoint().getY() >= getRealStartingPointY() + height / 4 && 
                interaction.getPoint().getY() <= getRealStartingPointY() + height * 3 / 4)
        {
            return 1.0F;
        }
        else 
        {
            return 0;
        }
    }
    
    protected float getRealStartingPointX()
    {
        return this.position.getX();
    }
    
    protected float getRealStartingPointY()
    {
        return this.position.getY();
    }
    
    public String getText()
    {
        return this.text;
    }
}
