package isensestressanalyzer.utils;

import isensestressanalyzer.interaction.Interaction;
import isensestressanalyzer.interaction.Touch;

/**
 *
 * @author Matteo Ciman
 */
public class KeyboardKeyObject extends ScreenObject {
    
    private final ViewKeyboard keyboard;

    public KeyboardKeyObject(ViewKeyboard keyboard, Double id, String objectClass, 
            float x, float y, int width, int height, int visibility, String text) {
        
        super(id, objectClass, x, y, width, height, visibility, text);
        this.keyboard = keyboard;
    }
    
    /**
     * Provides a rate of the touch interaction with the key object of the 
     * keyboard
     * @param touch the touch interaction
     * @return 
     */
    @Override
    public float rateTouch(Touch touch) {
        /**
         * Give a rate only if the last interaction is inside the KeyboardKey
         * Maybe not necessary control, but just to be sure
         */
        if (isInside(touch.getInteractions()
                .get(touch.getInteractions().size() - 1))) {
            
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
    public boolean isInside(Interaction interaction) {
        
        Point realPosition = this.getPosition();
        
        return (interaction.getPoint().getX() >= realPosition.getX()&& 
            interaction.getPoint().getX() <= realPosition.getX() + width && 
            interaction.getPoint().getY() >= realPosition.getY()&& 
            interaction.getPoint().getY() <= realPosition.getY()+ height);
    }
    
    @Override
    public Point getPosition() {
        return new Point(this.position.getX() + keyboard.getPosition().getX(), 
            this.position.getY() + keyboard.getPosition().getY());
    }
    
    /**
     * Returns the keyboard that "holds" the key
     * @return the ViewKeyboard object
     */
    public ViewKeyboard getKeyboard() {
        return this.keyboard;
    }
    
    /**
     * Returns the position of the center of the object
     * @return a Point object that represents the center of the Key
     */
    @Override
    public Point getCenterPosition() {
        
        return new Point(Math.round(getPosition().getX() + width / 2), 
            Math.round(getPosition().getY() + height / 2));
    }
}
