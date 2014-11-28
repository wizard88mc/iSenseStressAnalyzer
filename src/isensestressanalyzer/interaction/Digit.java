package isensestressanalyzer.interaction;

import isensestressanalyzer.dataanalysis.BasicDataStatistic;
import isensestressanalyzer.utils.Point;
import java.util.ArrayList;

/**
 * class Digit
 * 
 * This class represents a digit during the Writing task, with the clicked digit
 * and the list of interactions made during this digit
 * 
 * @author  Matteo Ciman
 * @version 0.1
 * @since   2014-10-20
 */
public class Digit extends Touch
{   
    protected String digit;
    
    public Digit(String digit, ArrayList<Interaction> listInteractions) {
        super(listInteractions); this.digit = digit;
    }
    
    /**
     * Returns the character of the digit
     * @return the character of the digit
     */
    public String getDigitText()
    {
        return this.digit;
    }
    
    public long getDigitStart()
    {
        return this.listInteractions.get(0).timestamp;
    }
    
    public long getDigitEnd()
    {
        return this.listInteractions.get(listInteractions.size() - 1).timestamp;
    }
}
