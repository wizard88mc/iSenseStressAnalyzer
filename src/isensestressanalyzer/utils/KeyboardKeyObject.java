package isensestressanalyzer.utils;

import isensestressanalyzer.interaction.Interaction;
import isensestressanalyzer.interaction.Touch;

/**
 *
 * @author Matteo Ciman
 */
public class KeyboardKeyObject extends ScreenObject 
{

    public static ViewKeyboard mViewKeyboard = null;
    
    public KeyboardKeyObject(Double id, String objectClass, float x, float y, 
            int width, int height, int visibility, String text) 
    {
        super(id, objectClass, x, y, width, height, visibility, text);
    }
    
    @Override
    public float rateTouch(Touch touch)
    {
        /**
         * Give a rate only if the last interaction is inside the KeyboardKey
         * Maybe not necessary control, but just to be sure
         */
        if (isInside(touch.getInteractions().get(touch.getInteractions().size() - 1)))
        {
            float result = 0;
            /*for (Interaction interaction: touch.getInteractions())
            {
                result += rateInteraction(interaction);
            }*/
            return rateInteraction(touch.getInteractions().get(touch.getInteractions().size() - 1));
            //return result;
        }
        else return 0F;
    }
    
    @Override
    protected boolean isInside(Interaction interaction)
    {
        return (interaction.getPoint().getX() >= getRealStartingPointX() && 
                interaction.getPoint().getX() <= getRealStartingPointX() + width && 
                interaction.getPoint().getY() >= getRealStartingPointY() && 
                interaction.getPoint().getY() <= getRealStartingPointY() + height);
    }
    
    @Override
    protected float getRealStartingPointX()
    {
        return this.position.getX() + mViewKeyboard.getPosition().getX();
    }
    
    @Override
    protected float getRealStartingPointY()
    {
        return this.position.getY() + mViewKeyboard.getPosition().getY();
    }
}
