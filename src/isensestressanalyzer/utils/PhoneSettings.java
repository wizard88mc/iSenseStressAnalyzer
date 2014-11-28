package isensestressanalyzer.utils;

/**
 * class PhoneSettings
 * 
 * This class stores all the information about the phone that are saved in 
 * the Settings file. This information are the date of the test, the manufacturer
 * and the model of the smartphone, the screen width and height and the language
 * of the system
 * 
 * @author Matteo Ciman
 * @version 0.1
 * @since 2014-10-16
 */
public class PhoneSettings 
{
    private final String date;
    private final String manufacturerAndModel;
    private final long screenWidth;
    private final long screenHeight;
    private final String language;
    
    public PhoneSettings(String date, String manufacturerAndModel, long screenWidth, 
            long screenHeight, String language)
    {
        this.date = date; this.manufacturerAndModel = manufacturerAndModel; 
        this.screenWidth = screenWidth; this.screenHeight = screenHeight;
        this.language = language;
    }
    
    /**
     * Returns the date of the exercise
     * @return the date of the exercise
     */
    public String getDate()
    {
        return this.date;
    }
    
    /**
     * Returns the manufacturer and the model of the smartphone
     * @return the manufacturer and the model
     */
    public String getManufacturerAndModel()
    {
        return this.manufacturerAndModel;
    }
    
    /**
     * Returns the screen width of the smartphone
     * @return the screen width
     */
    public long getScreenWidth()
    {
        return this.screenWidth;
    }
    
    /**
     * Returns the screen height of the smartphone
     * @return the screen height
     */
    public long getScreenHeight()
    {
        return this.screenHeight;
    }
    
    /**
     * Returns the user language used on the smartphone
     * @return the language
     */
    public String getLanguage()
    {
        return this.language;
    }
}
