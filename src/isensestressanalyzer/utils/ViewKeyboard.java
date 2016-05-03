package isensestressanalyzer.utils;

import java.util.ArrayList;

/**
 * This class represent a virtual keyboard on the screen
 * @author Matteo Ciman
 */
public class ViewKeyboard  {
    
    private final Point position;
    private final float width;
    private final float height;
    
    private final ArrayList<KeyboardKeyObject> listKeys = new ArrayList<>();
    
    public ViewKeyboard(float x, float y, float width, float height) {
        this.position = new Point(Math.round(x), Math.round(y)); 
        this.width = width; 
        this.height = height;
    }
    
    /**
     * Adds a key element to the keyboard
     * @param key the key element to add
     */
    public void addKey(KeyboardKeyObject key) {
        listKeys.add(key);
    } 
    
    public Point getPosition() {
        return this.position;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    /**
     * Returns the KeyboardKeyObject associated with that particular text
     * @param text the text of the KeyboardKeyObject
     * @return the KeyboardKeyObject with the correct text, null otherwise  
     */
    public KeyboardKeyObject getKeyObject(String text) {
        
        KeyboardKeyObject object = null;
        
        for (int i = 0; i < listKeys.size() && object == null; i++) {
            if (listKeys.get(i).getText().equals(text)) {
                object = listKeys.get(i);
            }
        }
        return object;
    }
}
