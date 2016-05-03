package isensestressanalyzer.interaction;

import isensestressanalyzer.utils.KeyboardKeyObject;
import java.util.ArrayList;

/**
 * class Digit
 * 
 * This class represents a digit during the Writing task, with the clicked key
 * and the list of interactions made during this digit
 * 
 * @author  Matteo Ciman
 * @version 0.1
 * @since   2014-10-20
 */
public class Digit extends Touch {
    
    private final KeyboardKeyObject key;
    
    public Digit(KeyboardKeyObject key, ArrayList<Interaction> listInteractions) {
        super(listInteractions); this.key = key;
    }
    
    /**
     * Returns the character of the digit
     * @return the character of the digit
     */
    public String getDigitText() {
        return this.key.getText();
    }
    
    public long getDigitStart() {
        return this.listInteractions.get(0).timestamp;
    }
    
    public long getDigitEnd() {
        return this.listInteractions.get(listInteractions.size() - 1).timestamp;
    }
    
    public KeyboardKeyObject getKeyClicked() {
        return key;
    }
}
