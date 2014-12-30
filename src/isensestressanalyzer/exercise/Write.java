package isensestressanalyzer.exercise;

import java.util.ArrayList;
import isensestressanalyzer.ISenseStressAnalyzer;
import isensestressanalyzer.dataanalysis.BasicDataStatistic;
import isensestressanalyzer.interaction.Digit;
import isensestressanalyzer.interaction.Interaction;
import isensestressanalyzer.utils.KeyboardKeyObject;
import isensestressanalyzer.utils.ScreenObject;

/**
 * class Writing
 * 
 * Represents a Writing exercise
 * 
 * @author  Matteo Ciman
 * @version 0.1
 * @since   2014-10-16
 */
public class Write extends Exercise 
{
    private ArrayList<Digit> digits = null;
    
    public Write(int order, boolean stress, int repetition, 
            int subRepetition, String[] additionalV) 
    {
        super(order, ExerciseType.WRITE, stress, repetition, subRepetition,
                additionalV);
    }
    
    /**
     * Returns the total words to write
     * @return number of words to write
     */
    public String getTotalWords()
    {
        return additionalValues.get(0);
    }
    
    /**
     * Returns the number of the correct words written by the user
     * @return number of correct words
     */
    public String getTotalCorrectWords()
    {
        return additionalValues.get(1);
    }
    
    /**
     * Returns the number of wrong words written by the user
     * @return number of wrong words
     */
    public String getTotalErrors()
    {
        return additionalValues.get(2);
    }
    
    /**
     * Returns the number of times the user clicked on the back button to cancel
     * a character
     * @return the number of times the back buttons was pressed
     */
    public String getNumberBackButton()
    {
        return additionalValues.get(3);
    }
    
    /**
     * Returns the ratio of the back digits over the total number of written 
     * digits
     * @return back_digit / total_digits
     */
    public Double getRatioBackButtonsOverDigits()
    {
        return Double.valueOf(additionalValues.get(3)) / digits.size();
    }
    
    /**
     * Returns the text to write
     * @return the text to write
     */
    public String getTextToWrite()
    {
        return additionalValues.get(4);
    }
    
    /**
     * Returns the text written by the user
     * @return the written text
     */
    public String getWrittenText()
    {
        return additionalValues.get(5);
    }
    
    /**
     * Completes data acquisition retrieving data from the layout logger to 
     * create view object, and retrieves data from the touch logger to get 
     * information about the digits
     * @param protocol the protocol we are currently considering
     */
    public void completeDataAcquisition(Protocol protocol)
    {
        super.completeDataAcquisition(protocol);
        
        ArrayList<String> touchInteractionsFromTouchFile = 
                ISenseStressAnalyzer.mTouchReader
                        .retrieveTouchEventPerExercisePerRepetition(protocol, 
                                order, WRITE_STRING, stress, repetition);
        
        digits = new ArrayList<>();
        
        for (int i = 0; i < touchInteractionsFromTouchFile.size(); i++)
        {
            if (touchInteractionsFromTouchFile.get(i).contains("DIGIT"))
            {
                digits.add(createDigit(touchInteractionsFromTouchFile, i));
            }
        }
    }
    
    /**
     * Creates a digit object with the information about the digit, analyzing 
     * data saved in touch logger
     * 
     * @param touchInteractions the lines from the touch logger
     * @param index the index to from from backward to build digit info
     * @return a new Digit object with the information about the digit
     */
    public Digit createDigit(ArrayList<String> touchInteractions, int index)
    {
        int startingPoint = index;
        boolean found = false;
        
        for (int i = index - 1; i >= 0 && !found; i--)
        {
            if (touchInteractions.get(i).startsWith("0,"))
            {
                found = true; startingPoint = i;
            }
        }
        
        ArrayList<Interaction> interactions = new ArrayList<>();
        
        for (int i = startingPoint; i < index; i++)
        {
            interactions.add(new Interaction(touchInteractions.get(i)));
        }
        
        return new Digit((touchInteractions.get(index).split(","))[1], interactions);
    }
    
    /**
     * Returns all the digits of the exercise
     * @return the digits of the exercise
     */
    public ArrayList<Digit> getDigits()
    {
        return this.digits;
    }
    
    /**
     * Calculates the data statistic for pressure information
     * @return BasicDataStatistic built with pressure information
     */
    public BasicDataStatistic getPressionDigitsBasicData()
    {
        ArrayList<Double> pressures = new ArrayList<>();
        for (Digit digit: digits)
        {
            pressures.add(digit.getPressureBasicData().getAverage());
        }
        return new BasicDataStatistic(pressures, false);
    }
    
    /**
     * Calculates the data statistics for size information
     * @return BasicDataStatistic built with size information
     */
    public BasicDataStatistic getSizeDigitsBasicData()
    {
        ArrayList<Double> sizes = new ArrayList<>();
        for (Digit digit: digits)
        {
            sizes.add(digit.getSizeBasicData().getAverage());
        }
        return new BasicDataStatistic(sizes, false);
    }
    
    public BasicDataStatistic getRatioPressureSizeData() {
    	
    	ArrayList<Double> ratios = new ArrayList<>();
    	for (Digit digit: digits) {
    		ratios.add(digit.getPressureBasicData().getAverage() / 
    				digit.getSizeBasicData().getAverage());
    	}
    	
    	return new BasicDataStatistic(ratios, true);
    }
    
    /**
     * Calculates the data statistic for the movement information
     * @return BasicDataStatistic built with movement information
     */
    public BasicDataStatistic getMovementDigitsBasicData()
    {
        ArrayList<Double> movements = new ArrayList<>();
        for (Digit digit: digits)
        {
            movements.add(digit.getTouchMovement());
        }
        return new BasicDataStatistic(movements, false);
    }
    
    /**
     * Calculates the data statistic for the duration information
     * @return BasicDataStatistic built with duration information
     */
    public BasicDataStatistic getDurationDigitsBasicData()
    {
        ArrayList<Double> durations = new ArrayList<>();
        for (Digit digit: digits)
        {
            durations.add(digit.getTouchDuration());
        }
        return new BasicDataStatistic(durations, false);
    }
    
    /**
     * Calculates the data statistic for the touch precision information
     * @return BasicDataStatistic built with precision information
     */
    public BasicDataStatistic getTouchPrecisionDigitsBasicData()
    {
        ArrayList<Double> precisions = new ArrayList<>();
        for (Digit digit: digits)
        {
            KeyboardKeyObject digitObjectClicked = findKeyboardKeyForDigit(digit);
            if (digitObjectClicked != null)
            {
                precisions.add((double) digitObjectClicked.rateTouch(digit));
                
            }
        }
        return new BasicDataStatistic(precisions, false);
    }
    
    /**
     * Provides an evaluation of the touches operation during the writing. 
     * It sums the points of each digit on the screen to get an overall evaluation
     * @return an evaluation of the digit of the exercise (sum of the points of 
     * each interaction / total number of interactions)
     */
    public Double rateTouchPrecisionExercise()
    {
        double rate = 0;
        double maxRate = 0;
        for (Digit digit: digits)
        {
            KeyboardKeyObject digitObjectClicked = findKeyboardKeyForDigit(digit);
            if (digitObjectClicked != null)
            {
                rate += digitObjectClicked.rateTouch(digit);
                maxRate += 1;
            }
        }
        return rate / maxRate;
    }
    
    private KeyboardKeyObject findKeyboardKeyForDigit(Digit digit)
    {
        for (ScreenObject screenObject: screenObjects)
        {
            if (screenObject instanceof KeyboardKeyObject
                    && screenObject.getText().equals(digit.getDigitText()))
            {
                return (KeyboardKeyObject) screenObject;
            }
        }
        return null;
    }
    
    /**
     * calculates the frequency of digits
     * @return writing time / number of digits
     */
    public Double calculateDigitsFrequency()
    {
        long deltaTime = digits.get(digits.size() - 1).getDigitEnd() - 
                digits.get(0).getDigitStart();
        
        return (double) deltaTime / (double) digits.size();
    }
    
    /**
     * Calculates the list of attributes for the write task
     * @return for each digits the list of attributes
     */
    public ArrayList<ArrayList<Double>> ARFFAttributesForWrite() {
    	
    	ArrayList<ArrayList<Double>> attributesForAllDigits = new ArrayList<>();
    	
    	for (int i = 0; i < digits.size(); i++) {
    		
    		ArrayList<Double> attributes = new ArrayList<>();
    		attributes.add(digits.get(i).getPressureBasicData().getAverage());
    		attributes.add(digits.get(i).getSizeBasicData().getAverage());
    		attributes.add(digits.get(i).getTouchMovement());
    		attributes.add(digits.get(i).getTouchDuration());
    		
    		if (i > 0) {
    			attributes.add((double) (digits.get(i).getDigitEnd() - 
    					digits.get(i - 1).getDigitEnd()));
    		}
    		else {
    			attributes.add(null);
    		}
    		
    		attributesForAllDigits.add(attributes);
    	}
    	
    	return attributesForAllDigits;
    }
    
    /**
     * Returns the name of all the attributes calculated for the write task
     * @return
     */
    public static ArrayList<String> getARFFListAttributesNames() {
    	
    	ArrayList<String> attributesNames = new ArrayList<>();
    	attributesNames.add("PRESSURE");
    	attributesNames.add("SIZE");
    	attributesNames.add("RATIO_PRESSURE_SIZE");
    	attributesNames.add("MOVEMENT");
    	attributesNames.add("DURATION");
    	attributesNames.add("TIME_DISTANCE");
    	return attributesNames;
    }
}
