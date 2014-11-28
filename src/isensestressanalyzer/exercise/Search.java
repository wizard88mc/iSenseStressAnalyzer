package isensestressanalyzer.exercise;

import isensestressanalyzer.ISenseStressAnalyzer;
import isensestressanalyzer.dataanalysis.BasicDataStatistic;
import isensestressanalyzer.interaction.HorizontalScroll;
import isensestressanalyzer.interaction.Interaction;
import isensestressanalyzer.interaction.Scroll;
import isensestressanalyzer.interaction.ScrollMovement;
import isensestressanalyzer.interaction.Touch;
import isensestressanalyzer.interaction.VerticalScroll;
import java.util.ArrayList;
import java.util.Collections;

/**
 * class Search
 * 
 * Represents a Search exercise
 * 
 * @author  Matteo Ciman
 * @version 0.1
 * @since   2014-10-15
 */
public class Search extends Exercise 
{
    private ArrayList<Touch> clicksOnIcons = null;
    private ArrayList<Scroll> mListScroll = null; 
    private ArrayList<Long> listTimestampEvents = new ArrayList<>();
    
    public Search(int order, boolean stress, int repetition, 
            int subRepetition, String[] additionalV) 
    {
        super(order, ExerciseType.SEARCH, stress, repetition, subRepetition, 
                additionalV);
    }
    
    /**
     * Returns the total number of icons on the screen
     * @return the total icons
     */
    public String getTotalIcons()
    {
        return additionalValues.get(0);
    }
    
    /**
     * Returns the total number of correct (to click) icons on the screen
     * @return the correct icons
     */
    public String getTotalCorrectIcons()
    {
        return additionalValues.get(1);
    }
    
    /**
     * Returns the number of correct icons found
     * @return the correct icons found
     */
    public String getCorrectIconsFound()
    {
        return additionalValues.get(2);
    }
    
    /**
     * Returns the missing (correct but not found) icons
     * @return the missing icons
     */
    public String getMissingIcons()
    {
        return additionalValues.get(3);
    }
    
    /**
     * Returns the number of wrong icons clicked
     * @return the wrong icons clicked
     */
    public String getWrongIconsClicked()
    {
        return additionalValues.get(4);
    }

    @Override
    public void completeDataAcquisition(Protocol protocol) 
    {
        super.completeDataAcquisition(protocol);
        
        ArrayList<String> touchInteractionsFromFile = ISenseStressAnalyzer.mTouchReader
                .retrieveTouchEventPerExercisePerRepetition(protocol, 
                                order, SEARCH_STRING, stress, repetition);
        
        clicksOnIcons = new ArrayList<>();
        for (int i = 0; i < touchInteractionsFromFile.size(); i++)
        {
            if (touchInteractionsFromFile.get(i).contains("CLICK"))
            {
                
                Touch touch = createTouch(touchInteractionsFromFile, i);
                try
                {
                clicksOnIcons.add(touch);
                listTimestampEvents.add(touch.getInteractions().get(0).getTimestamp());
                }
                catch(Exception exc)
                {
                    exc.printStackTrace();
                }
            }
        }
        
        ArrayList<String> layoutInfo = ISenseStressAnalyzer.mLayoutReader
                .retrieveAllLayoutInformationPerExercisePerRepetition(protocol, 
                        order, SEARCH_STRING, stress, repetition);
        
        mListScroll = new ArrayList<>();
        
        for (int i = 0; i < layoutInfo.size(); i++)
        {
            if (layoutInfo.get(i).contains("V_SCROLL_START"))
            {
                // COMPLETE WITH A VERTICAL SCROLL
                Scroll scroll = createScrollEvent(layoutInfo, 
                        touchInteractionsFromFile, i, true);
                if (scroll != null)
                {
                    mListScroll.add(scroll);
                    listTimestampEvents.add(scroll.getStartTimestamp());
                }
            }
            else if (layoutInfo.get(i).contains("H_SCROLL_START"))
            {
                // COMPLETE WITH AN HORIZONTAL SCROLL
                Scroll scroll = createScrollEvent(layoutInfo, 
                        touchInteractionsFromFile, i, false);
                if (scroll != null)
                {
                    mListScroll.add(scroll);
                    listTimestampEvents.add(scroll.getStartTimestamp());
                }
            }
        }
        
        /**
         * Sorting timestamps to order all the events that take place during
         * search exercise to be able to move the position of the objects on the 
         * screen when the user scrolls
         */
        Collections.sort(listTimestampEvents);
    }
    
    /**
     * Creates a new Touch interaction when the user clicks on the screen
     * @param touchInteractions list of String events on touch file
     * @param index the index of the (CLICK, ICON) entry
     * @return a new Touch object
     */
    private Touch createTouch(ArrayList<String> touchInteractions, int index)
    {
        int startPoint = index;
        int endPoint = index;
        for (int i = index; i >= 0 && (startPoint == index || endPoint == index) ; i--)
        {
            if (endPoint != index && touchInteractions.get(i).startsWith("0"))
            {
                startPoint = i;
            }
            if (touchInteractions.get(i).startsWith("1"))
            {
                endPoint = i;
            }
        }
        
        try {
            ArrayList<Interaction> listInteractionClick = new ArrayList<>();
            for (int i = startPoint; i <= endPoint; i++)
            {
                Interaction interaction = new Interaction(touchInteractions.get(i));
                if (interaction.getTimestamp() != -1)
                {
                    listInteractionClick.add(interaction);
                }
            }

            if (listInteractionClick.isEmpty())
            {
                return null;
            }
            else
            {
                return new Touch(listInteractionClick);
            }
        }
        catch(Exception exc)
        {
            exc.printStackTrace(); return null;
        }
    }
    
    /**
     * Creates a new scroll object using the layout and touch interaction readings
     * 
     * @param layoutInfo the layout info from the file
     * @param touchInteractions the touch interactions from the file
     * @param startIndex the start index of the scroll we are evaluating
     * @param vertical if it is vertical or horizontal scroll
     * @return a new Scroll object (Horizontal or Vertical)
     */
    private Scroll createScrollEvent(ArrayList<String> layoutInfo, 
            ArrayList<String> touchInteractions, int startIndex, boolean vertical)
    {
        //TODO first search to complete the scroll from the layout file, after
        // retrieve info from the touch interactions to create the touch interaction list
        String endToSearch = vertical ? "V_SCROLL_END" : "H_SCROLL_END";
        int endIndex = startIndex;
        for (int i = startIndex; i < layoutInfo.size() && endIndex == startIndex; i++)
        {
            if (layoutInfo.get(i).contains(endToSearch))
            {
                endIndex = i;
            }
        }
        
        /**
         * start index = first timestamp of the scroll
         * end index = last timestamp of the scroll
         * [startIndex ... endIndex] ScrollMovement elements
         */
        ArrayList<Interaction> listInteractions = new ArrayList<>();
        ArrayList<ScrollMovement> scrollMovements = new ArrayList<>();
        String startTimestamp = "", endTimestamp = "";
        for (int i = startIndex; i <= endIndex; i++)
        {
            if (layoutInfo.get(i).contains("SCROLL_START"))
            {
                // Extract start timestamp
                startTimestamp = layoutInfo.get(i).split(",")[1];
            }
            else if (layoutInfo.get(i).contains("SCROLL_END"))
            {
                // Extract end timestamp
                endTimestamp = layoutInfo.get(i).split(",")[1];
            }
            else if (layoutInfo.get(i).contains("_SCROLL,"))
            {
                // Extract scroll movement
                scrollMovements.add(new ScrollMovement(layoutInfo.get(i)));
            }
        }
        
        /**
         * Search for the list of interactions
         */
        int startIndexTouch = -1; int endIndexTouch = -1;
        for (int i = 0; i < touchInteractions.size() && (startIndexTouch == -1 || 
                endIndexTouch == -1); i++)
        {
            if (startIndexTouch == -1 && 
                    touchInteractions.get(i).contains(startTimestamp))
            {
                startIndexTouch = i;
            }
            if (touchInteractions.get(i).contains(endTimestamp))
            {
                endIndexTouch = i;
            }
        }
        
        if (startIndexTouch != -1 && endIndexTouch != -1
                && startTimestamp != "" && endTimestamp != "")
        {
            for (int i = startIndexTouch; i <= endIndexTouch; i++)
            {
                listInteractions.add(new Interaction(touchInteractions.get(i)));
            }

            try 
            {
            if (vertical)
            {
                return new VerticalScroll(Long.valueOf(startTimestamp), 
                        Long.valueOf(endTimestamp), listInteractions, 
                        scrollMovements);
            }
            else
            {
                return new HorizontalScroll(Long.valueOf(startTimestamp), 
                        Long.valueOf(endTimestamp), listInteractions, 
                        scrollMovements);
            }
            }catch(Exception exc)
            {
                exc.printStackTrace();
                return null;
            }
        }
        else 
        {
            return null;
        }
    }
    
    /**
     * The touches on the icons
     * @return 
     */
    public ArrayList<Touch> getClickOnIcons()
    {
        return this.clicksOnIcons;
    }
    
    /**
     * 
     * @return the scroll events on the screen
     */
    public ArrayList<Scroll> getScrollEvents()
    {
        return this.mListScroll;
    }
    
    public BasicDataStatistic getAveragePressureBasicData()
    {
        ArrayList<Double> pressure = new ArrayList<>();
        for (Touch touch: clicksOnIcons)
        {
            pressure.add(touch.getPressureBasicData().getAverage());
        }
        return new BasicDataStatistic(pressure, false);
    }
    
    public BasicDataStatistic getAverageSizeBasicData()
    {
        ArrayList<Double> size = new ArrayList<>();
        for (Touch touch: clicksOnIcons)
        {
            size.add(touch.getSizeBasicData().getAverage());
        }
        return new BasicDataStatistic(size, false);
    }
    
    public BasicDataStatistic getAverageMovementClicksBasicData()
    {
        ArrayList<Double> movement = new ArrayList<>();
        for (Touch touch: clicksOnIcons)
        {
            movement.add(touch.getTouchMovement());
        }
        return new BasicDataStatistic(movement, false);
    }
}
