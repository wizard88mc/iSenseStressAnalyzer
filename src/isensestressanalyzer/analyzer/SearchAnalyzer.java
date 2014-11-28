package isensestressanalyzer.analyzer;

import isensestressanalyzer.ISenseStressAnalyzer;
import isensestressanalyzer.dataanalysis.BasicDataStatistic;
import isensestressanalyzer.dataanalysis.StressNoStressData;
import isensestressanalyzer.exercise.Protocol;
import isensestressanalyzer.exercise.Search;
import isensestressanalyzer.interaction.HorizontalScroll;
import isensestressanalyzer.interaction.Scroll;
import isensestressanalyzer.interaction.Touch;
import isensestressanalyzer.interaction.VerticalScroll;
import isensestressanalyzer.utils.PhoneSettings;
import java.util.ArrayList;
import tester.Tester;

/**
 *
 * @author Matteo Ciman
 */
public class SearchAnalyzer extends Analyzer
{
    private final ArrayList<Tester> mListTester;
    
    public SearchAnalyzer(ArrayList<Tester> mListTester)
    {
        this.mListTester = mListTester;
    }
    
    public SearchAnalyzer(Tester singleTester)
    {
        this.mListTester = new ArrayList<>(); mListTester.add(singleTester);
    }
    
    public void performAnalysis()
    {
        for (Protocol protocol: ISenseStressAnalyzer.protocols)
        {
            
            StressNoStressData pressureData = new StressNoStressData("Touch pressure"),
                    sizeData = new StressNoStressData("Touch size"),
                    movementData = new StressNoStressData("Touch movement"),
                    scrollDeltaDataVertical = new StressNoStressData("Scroll delta"),
                    scrollTimeLengthDataVertical = new StressNoStressData("Scroll time length"),
                    scrollInteractionLengthDataVertical = new StressNoStressData("Scroll interaction length"),
                    speedScrollDeltaDataVertical = new StressNoStressData("Speed scroll delta"),
                    speedScrollInteractionDataVertical = new StressNoStressData("Speed scroll interaction"),
                    meanDistanceFromCenterDataVertical = new StressNoStressData("Mean distance from center"),
                    meanDistanceFromTopLeftDataVertical = new StressNoStressData("Mean distance from top left"),
                    linearityDataVertical = new StressNoStressData("Linearity"),
                    linearityAsSumEveryPointDataVertical = new StressNoStressData("Linearity as sum every point"),
                    scrollDeltaDataHorizontal = new StressNoStressData("Scroll delta horizontal"),
                    scrollTimeLengthDataHorizontal = new StressNoStressData("Scroll time length horizontal"),
                    scrollInteractionLengthDataHorizontal = new StressNoStressData("Scroll interaction length horizontal"),
                    speedScrollDeltaDataHorizontal = new StressNoStressData("Speed scroll delta horizontal"),
                    speedScrollInteractionDataHorizontal = new StressNoStressData("Speed scroll interaction horizontal"),
                    meanDistanceFromCenterDataHorizontal = new StressNoStressData("Mean distance from center horizontal"),
                    meanDistanceFromTopLeftHorizontal = new StressNoStressData("Mean distance from top left horizontal"),
                    linearityDataHorizontal = new StressNoStressData("Linearity horizontal"),
                    linearityAsSumEveryPointDataHorizontal = new StressNoStressData("Linearity as sum every point horizontal");
            
            
            for (Tester tester: mListTester)
            {
                ArrayList<Search> exercises = tester.getSearchExercisesForProtocol(protocol);

                if (!exercises.isEmpty() && mListTester.size() > 1)
                {
                    ArrayList<Touch> iconsClickedNoStress = new ArrayList<>(), 
                            iconsClickedStress = new ArrayList<>();

                    ArrayList<Scroll> scrollNoStress = new ArrayList<>(), 
                            scrollStress = new ArrayList<>();

                    for (Search search: exercises)
                    {
                        if (!search.stress())
                        {
                            iconsClickedNoStress.addAll(search.getClickOnIcons());
                            scrollNoStress.addAll(search.getScrollEvents());
                        }
                        else
                        {
                            iconsClickedStress.addAll(search.getClickOnIcons());
                            scrollStress.addAll(search.getScrollEvents());
                        }
                    }
                    managePressureData(iconsClickedNoStress, iconsClickedStress, 
                            pressureData);
                    manageSizeData(iconsClickedNoStress, iconsClickedStress, 
                            sizeData);
                    manageMovementData(iconsClickedNoStress, iconsClickedStress, 
                            movementData);
                    manageScrollDeltaData(scrollNoStress, scrollStress, 
                            scrollDeltaDataVertical, scrollDeltaDataHorizontal);
                    manageScrollTimeLengthData(scrollNoStress, scrollStress, 
                            scrollTimeLengthDataVertical, scrollTimeLengthDataHorizontal);
                    manageScrollInteractionLengthData(scrollNoStress, scrollStress, 
                            scrollInteractionLengthDataVertical, scrollInteractionLengthDataHorizontal);
                    manageSpeedScrollDeltaData(scrollNoStress, scrollStress, 
                            speedScrollDeltaDataVertical, speedScrollDeltaDataHorizontal);
                    manageSpeedScrollInteractionData(scrollNoStress, scrollStress, 
                            speedScrollInteractionDataVertical, speedScrollInteractionDataHorizontal);
                    manageMeanDistanceFromCenter(scrollNoStress, scrollStress, tester, 
                            meanDistanceFromCenterDataVertical, meanDistanceFromCenterDataHorizontal);
                    manageMeanDistanceFromTopLeft(scrollNoStress, scrollStress, 
                            meanDistanceFromTopLeftDataVertical, meanDistanceFromTopLeftHorizontal);
                    manageLinearityData(scrollNoStress, scrollStress, tester, 
                            linearityDataVertical, linearityDataHorizontal);
                    manageLinearityAsSumEveryPoint(scrollNoStress, scrollStress, tester, 
                            linearityAsSumEveryPointDataVertical, linearityAsSumEveryPointDataHorizontal);
                }
                else if (!exercises.isEmpty() && mListTester.size() == 1)
                {
                    for (Search exercise: exercises)
                    {
                        if (!exercise.stress())
                        {
                            pressureData.addNoStressData(new BasicDataStatistic(exercise.getAveragePressureBasicData().getAverage()));
                            sizeData.addNoStressData(new BasicDataStatistic(exercise.getAverageSizeBasicData().getAverage()));
                            movementData.addNoStressData(new BasicDataStatistic(exercise.getAverageMovementClicksBasicData().getAverage()));
                            manageScrollDeltaData(exercise.getScrollEvents(), null, scrollDeltaDataVertical, scrollDeltaDataHorizontal);
                            manageScrollTimeLengthData(exercise.getScrollEvents(), null, scrollTimeLengthDataVertical, scrollTimeLengthDataHorizontal);
                            manageScrollInteractionLengthData(exercise.getScrollEvents(), null, scrollInteractionLengthDataVertical, scrollInteractionLengthDataHorizontal);
                            manageSpeedScrollDeltaData(exercise.getScrollEvents(), null, speedScrollDeltaDataVertical, speedScrollDeltaDataHorizontal);
                            manageSpeedScrollInteractionData(exercise.getScrollEvents(), null, speedScrollInteractionDataVertical, speedScrollInteractionDataHorizontal);
                            manageMeanDistanceFromCenter(exercise.getScrollEvents(), null, tester, meanDistanceFromCenterDataVertical, meanDistanceFromCenterDataHorizontal);
                            manageMeanDistanceFromTopLeft(exercise.getScrollEvents(), null, meanDistanceFromTopLeftDataVertical, meanDistanceFromTopLeftHorizontal);
                            manageLinearityData(exercise.getScrollEvents(), null, tester, linearityDataVertical, linearityDataHorizontal);
                            manageLinearityAsSumEveryPoint(exercise.getScrollEvents(), null, tester, linearityAsSumEveryPointDataVertical, linearityAsSumEveryPointDataHorizontal);
                        }
                        else
                        {
                            pressureData.addStressData(new BasicDataStatistic(exercise.getAveragePressureBasicData().getAverage()));
                            sizeData.addStressData(new BasicDataStatistic(exercise.getAverageSizeBasicData().getAverage()));
                            movementData.addStressData(new BasicDataStatistic(exercise.getAverageMovementClicksBasicData().getAverage()));
                            manageScrollDeltaData(null, exercise.getScrollEvents(), scrollDeltaDataVertical, scrollDeltaDataHorizontal);
                            manageScrollTimeLengthData(null, exercise.getScrollEvents(), scrollTimeLengthDataVertical, scrollTimeLengthDataHorizontal);
                            manageScrollInteractionLengthData(null, exercise.getScrollEvents(), scrollInteractionLengthDataVertical, scrollInteractionLengthDataHorizontal);
                            manageSpeedScrollDeltaData(null, exercise.getScrollEvents(), speedScrollDeltaDataVertical, speedScrollDeltaDataHorizontal);
                            manageSpeedScrollInteractionData(null, exercise.getScrollEvents(), speedScrollInteractionDataVertical, speedScrollInteractionDataHorizontal);
                            manageMeanDistanceFromCenter(null, exercise.getScrollEvents(), tester, meanDistanceFromCenterDataVertical, meanDistanceFromCenterDataHorizontal);
                            manageMeanDistanceFromTopLeft(null, exercise.getScrollEvents(), meanDistanceFromTopLeftDataVertical, meanDistanceFromTopLeftHorizontal);
                            manageLinearityData(null, exercise.getScrollEvents(), tester, linearityDataVertical, linearityDataHorizontal);
                            manageLinearityAsSumEveryPoint(null, exercise.getScrollEvents(), tester, linearityAsSumEveryPointDataVertical, linearityAsSumEveryPointDataHorizontal);
                        }
                    }
                }
            }
            
            if (!pressureData.isEmpty())
            {
                SearchAnalysisResume resume = new SearchAnalysisResume();
                resume.pressureDataClick(pressureData); resume.sizeDataClick(sizeData);
                resume.movementDataClick(movementData); 
                resume.scrollDeltaData(scrollDeltaDataVertical, scrollDeltaDataHorizontal);
                resume.scrollTimeLengthData(scrollTimeLengthDataVertical, scrollTimeLengthDataHorizontal);
                resume.scrollInteractionLengthData(scrollInteractionLengthDataVertical, scrollInteractionLengthDataHorizontal);
                resume.speedScrollDeltaData(speedScrollDeltaDataVertical, speedScrollDeltaDataHorizontal);
                resume.speedScrollInteractionData(speedScrollInteractionDataVertical, speedScrollInteractionDataHorizontal);
                resume.meanDistanceFromCenterData(meanDistanceFromCenterDataVertical, meanDistanceFromCenterDataHorizontal);
                resume.meandDistanceFromTopLeftData(meanDistanceFromTopLeftDataVertical, meanDistanceFromTopLeftHorizontal);
                resume.linearityData(linearityDataVertical, linearityDataHorizontal);
                resume.linearityAsSumEveryPointData(linearityAsSumEveryPointDataVertical, linearityAsSumEveryPointDataHorizontal);

                System.out.println("** RESUME SEARCH **");
                //System.out.println(protocol.toString());
                resume.printAnalysis();
            }
        }
    }
    
    public void performGlobalAnalysis()
    {
        
        StressNoStressData pressureData = new StressNoStressData("Touch pressure"),
                    sizeData = new StressNoStressData("Touch size"),
                    movementData = new StressNoStressData("Touch movement"),
                    scrollDeltaDataVertical = new StressNoStressData("Scroll delta"),
                    scrollTimeLengthDataVertical = new StressNoStressData("Scroll time length"),
                    scrollInteractionLengthDataVertical = new StressNoStressData("Scroll interaction length"),
                    speedScrollDeltaDataVertical = new StressNoStressData("Speed scroll delta"),
                    speedScrollInteractionDataVertical = new StressNoStressData("Speed scroll interaction"),
                    meanDistanceFromCenterDataVertical = new StressNoStressData("Mean distance from center"),
                    meanDistanceFromTopLeftDataVertical = new StressNoStressData("Mean distance from top left"),
                    linearityDataVertical = new StressNoStressData("Linearity"),
                    linearityAsSumEveryPointDataVertical = new StressNoStressData("Linearity as sum every point"),
                    scrollDeltaDataHorizontal = new StressNoStressData("Scroll delta horizontal"),
                    scrollTimeLengthDataHorizontal = new StressNoStressData("Scroll time length horizontal"),
                    scrollInteractionLengthDataHorizontal = new StressNoStressData("Scroll interaction length horizontal"),
                    speedScrollDeltaDataHorizontal = new StressNoStressData("Speed scroll delta horizontal"),
                    speedScrollInteractionDataHorizontal = new StressNoStressData("Speed scroll interaction horizontal"),
                    meanDistanceFromCenterDataHorizontal = new StressNoStressData("Mean distance from center horizontal"),
                    meanDistanceFromTopLeftHorizontal = new StressNoStressData("Mean distance from top left horizontal"),
                    linearityDataHorizontal = new StressNoStressData("Linearity horizontal"),
                    linearityAsSumEveryPointDataHorizontal = new StressNoStressData("Linearity as sum every point horizontal");
        
        for (Protocol protocol: ISenseStressAnalyzer.protocols)
        {   
            for (Tester tester: mListTester)
            {
                ArrayList<Search> exercises = tester.getSearchExercisesForProtocol(protocol);

                if (!exercises.isEmpty())
                {
                    ArrayList<Touch> iconsClickedNoStress = new ArrayList<>(), 
                            iconsClickedStress = new ArrayList<>();

                    ArrayList<Scroll> scrollNoStress = new ArrayList<>(), 
                            scrollStress = new ArrayList<>();

                    for (Search search: exercises)
                    {
                        if (!search.stress())
                        {
                            iconsClickedNoStress.addAll(search.getClickOnIcons());
                            scrollNoStress.addAll(search.getScrollEvents());
                        }
                        else
                        {
                            iconsClickedStress.addAll(search.getClickOnIcons());
                            scrollStress.addAll(search.getScrollEvents());
                        }
                    }
                    managePressureData(iconsClickedNoStress, iconsClickedStress, 
                            pressureData);
                    manageSizeData(iconsClickedNoStress, iconsClickedStress, 
                            sizeData);
                    manageMovementData(iconsClickedNoStress, iconsClickedStress, 
                            movementData);
                    manageScrollDeltaData(scrollNoStress, scrollStress, 
                            scrollDeltaDataVertical, scrollDeltaDataHorizontal);
                    manageScrollTimeLengthData(scrollNoStress, scrollStress, 
                            scrollTimeLengthDataVertical, scrollTimeLengthDataHorizontal);
                    manageScrollInteractionLengthData(scrollNoStress, scrollStress, 
                            scrollInteractionLengthDataVertical, scrollInteractionLengthDataHorizontal);
                    manageSpeedScrollDeltaData(scrollNoStress, scrollStress, 
                            speedScrollDeltaDataVertical, speedScrollDeltaDataHorizontal);
                    manageSpeedScrollInteractionData(scrollNoStress, scrollStress, 
                            speedScrollInteractionDataVertical, speedScrollInteractionDataHorizontal);
                    manageMeanDistanceFromCenter(scrollNoStress, scrollStress, tester, 
                            meanDistanceFromCenterDataVertical, meanDistanceFromCenterDataHorizontal);
                    manageMeanDistanceFromTopLeft(scrollNoStress, scrollStress, 
                            meanDistanceFromTopLeftDataVertical, meanDistanceFromTopLeftHorizontal);
                    manageLinearityData(scrollNoStress, scrollStress, tester, 
                            linearityDataVertical, linearityDataHorizontal);
                    manageLinearityAsSumEveryPoint(scrollNoStress, scrollStress, tester, 
                            linearityAsSumEveryPointDataVertical, linearityAsSumEveryPointDataHorizontal);
                }
            }
        }
        
        if (!pressureData.isEmpty())
        {
            SearchAnalysisResume resume = new SearchAnalysisResume();
            resume.pressureDataClick(pressureData); resume.sizeDataClick(sizeData);
            resume.movementDataClick(movementData); 
            resume.scrollDeltaData(scrollDeltaDataVertical, scrollDeltaDataHorizontal);
            resume.scrollTimeLengthData(scrollTimeLengthDataVertical, scrollTimeLengthDataHorizontal);
            resume.scrollInteractionLengthData(scrollInteractionLengthDataVertical, scrollInteractionLengthDataHorizontal);
            resume.speedScrollDeltaData(speedScrollDeltaDataVertical, speedScrollDeltaDataHorizontal);
            resume.speedScrollInteractionData(speedScrollInteractionDataVertical, speedScrollInteractionDataHorizontal);
            resume.meanDistanceFromCenterData(meanDistanceFromCenterDataVertical, meanDistanceFromCenterDataHorizontal);
            resume.meandDistanceFromTopLeftData(meanDistanceFromTopLeftDataVertical, meanDistanceFromTopLeftHorizontal);
            resume.linearityData(linearityDataVertical, linearityDataHorizontal);
            resume.linearityAsSumEveryPointData(linearityAsSumEveryPointDataVertical, linearityAsSumEveryPointDataHorizontal);

            System.out.println("** RESUME SEARCH **");
            //System.out.println(protocol.toString());
            resume.printAnalysis();
        }
    }
    
    /**
     * Calculates the BasicDataStatistic for Touch pressure data
     * @param touchNoStress
     * @param touchStress
     * @param dataNoStress
     * @param dataStress 
     */
    private void managePressureData(ArrayList<Touch> touchNoStress, 
            ArrayList<Touch> touchStress, 
            StressNoStressData pressureData)
    {
        ArrayList<Double> pressureAverageNoStress = new ArrayList<>(), 
                pressureAverageStress = new ArrayList<>();
        
        for (Touch touch: touchNoStress)
        {
            pressureAverageNoStress.add(touch.getPressureBasicData().getAverage());
        }
        for (Touch touch: touchStress)
        {
            pressureAverageStress.add(touch.getPressureBasicData().getAverage());
        }
        
        if (!pressureAverageNoStress.isEmpty() && 
                !pressureAverageStress.isEmpty())
        {
            pressureData.addData(new BasicDataStatistic(pressureAverageNoStress, false), 
                    new BasicDataStatistic(pressureAverageStress, false));
        }
    }
    
    /**
     * Calculates the BasicDataStatistic for the Touch size data
     * @param touchNoStress
     * @param touchStress
     * @param dataNoStress
     * @param dataStress 
     */
    private void manageSizeData(ArrayList<Touch> touchNoStress, 
            ArrayList<Touch> touchStress, 
            StressNoStressData sizeData)
    {
        ArrayList<Double> sizeAverageNoStress = new ArrayList<>(), 
                sizeAverageStress = new ArrayList<>();
        
        for (Touch touch: touchNoStress)
        {
            sizeAverageNoStress.add(touch.getSizeBasicData().getAverage());
        }
        for (Touch touch: touchStress)
        {
            sizeAverageStress.add(touch.getSizeBasicData().getAverage());
        }
        
        if (!sizeAverageNoStress.isEmpty() && !sizeAverageStress.isEmpty())
        {
            sizeData.addData(new BasicDataStatistic(sizeAverageNoStress, false), 
                    new BasicDataStatistic(sizeAverageStress, false));
        }
    }
    
    /**
     * Calculates the BasicDataStatistic for the Movement touch data
     * @param touchNoStress
     * @param touchStress
     * @param dataNoStress
     * @param dataStress 
     */
    private void manageMovementData(ArrayList<Touch> touchNoStress, 
            ArrayList<Touch> touchStress, 
            StressNoStressData movementData)
    {
        ArrayList<Double> movementAverageNoStress = new ArrayList<>(), 
                movementAverageStress = new ArrayList<>();
        
        for (Touch touch: touchNoStress)
        {
            movementAverageNoStress.add(touch.getTouchMovement());
        }
        for (Touch touch: touchStress)
        {
            movementAverageStress.add(touch.getTouchMovement());
        }
        
        if (!movementAverageNoStress.isEmpty() && 
                !movementAverageStress.isEmpty())
        {
            movementData.addData(new BasicDataStatistic(movementAverageNoStress, false), 
                    new BasicDataStatistic(movementAverageStress, false));
        }
    }
    
    private void manageScrollDeltaData(ArrayList<Scroll> scrollNoStress, 
            ArrayList<Scroll> scrollStress, StressNoStressData verticalData, 
            StressNoStressData horizontalData)
    {
        ArrayList<Double> scrollDeltaNoStressVertical = new ArrayList<>(), 
                scrollDeltaStressVertical = new ArrayList<>(), 
                scrollDeltaNoStressHorizontal = new ArrayList<>(), 
                scrollDeltaStressHorizontal = new ArrayList<>();
        
        if (scrollNoStress != null)
        {
            for (Scroll scroll: scrollNoStress)
            {
                if (scroll instanceof VerticalScroll)
                {
                    scrollDeltaNoStressVertical.add(scroll.calculateScrollDelta());
                }
                else if (scroll instanceof HorizontalScroll)
                {
                    scrollDeltaNoStressHorizontal.add(scroll.calculateScrollDelta());
                }
            }
        }
        if (scrollStress != null)
        {
            for (Scroll scroll: scrollStress)
            {
                if (scroll instanceof VerticalScroll)
                {
                    scrollDeltaStressVertical.add(scroll.calculateScrollDelta());
                }
                else if (scroll instanceof HorizontalScroll)
                {
                    scrollDeltaStressHorizontal.add(scroll.calculateScrollDelta());
                }
            }
        }
        
        if (!scrollDeltaNoStressVertical.isEmpty() && 
                !scrollDeltaStressVertical.isEmpty() && 
                (scrollNoStress != null && scrollStress != null) || 
                ((scrollNoStress != null && scrollStress == null) || 
                (scrollNoStress == null && scrollStress != null)))
        {
            if (scrollNoStress != null)
            {
                verticalData.addNoStressData(new BasicDataStatistic(scrollDeltaNoStressVertical, false));
            }
            if (scrollStress != null)
            {
                verticalData.addStressData(new BasicDataStatistic(scrollDeltaStressVertical, false));
            }
        }
        if (!scrollDeltaNoStressHorizontal.isEmpty() && 
                !scrollDeltaStressHorizontal.isEmpty() && 
                (scrollNoStress != null && scrollStress != null) || 
                ((scrollNoStress != null && scrollStress == null) || 
                (scrollNoStress == null && scrollStress != null)))
        {
            if (scrollNoStress != null)
            {
                horizontalData.addNoStressData(new BasicDataStatistic(scrollDeltaNoStressHorizontal, false));
            }
            if (scrollStress != null)
            {
                horizontalData.addStressData(new BasicDataStatistic(scrollDeltaStressHorizontal, false));
            }
                   
        }
        
    }
    
    private void manageScrollTimeLengthData(ArrayList<Scroll> scrollNoStress, 
            ArrayList<Scroll> scrollStress, StressNoStressData verticalData, 
            StressNoStressData horizontalData)
    {
        ArrayList<Double> scrollTimeLengthNoStressVertical = new ArrayList<>(), 
                scrollTimeLengthStressVertical = new ArrayList<>(), 
                scrollTimeLengthNoStressHorizontal = new ArrayList<>(), 
                scrollTimeLengthStressHorizontal = new ArrayList<>();
        
        if (scrollNoStress != null)
        {
            for (Scroll scroll: scrollNoStress)
            {
                if (scroll instanceof VerticalScroll)
                {
                    scrollTimeLengthNoStressVertical.add(scroll.calculateScrollTimeLength());
                }
                else if (scroll instanceof HorizontalScroll)
                {
                    scrollTimeLengthNoStressHorizontal.add(scroll.calculateScrollTimeLength());
                }
            }
        }
        if (scrollStress != null)
        {
            for (Scroll scroll: scrollStress)
            {
                if (scroll instanceof VerticalScroll)
                {
                    scrollTimeLengthStressVertical.add(scroll.calculateScrollTimeLength());
                }
                else if (scroll instanceof HorizontalScroll)
                {
                    scrollTimeLengthStressHorizontal.add(scroll.calculateScrollTimeLength());
                }
            }
        }
        if (!scrollTimeLengthNoStressVertical.isEmpty() && 
                !scrollTimeLengthStressVertical.isEmpty() && 
                (scrollNoStress != null && scrollStress != null) || 
                ((scrollNoStress != null && scrollStress == null) || 
                (scrollNoStress == null && scrollStress != null)))
        {
            if (scrollNoStress != null)
            {
                verticalData.addNoStressData(new BasicDataStatistic(scrollTimeLengthNoStressVertical, false));
            }
            if (scrollStress != null)
            {
                verticalData.addStressData(new BasicDataStatistic(scrollTimeLengthStressVertical, false));
            }
        }
        if (!scrollTimeLengthNoStressHorizontal.isEmpty() && 
                !scrollTimeLengthStressHorizontal.isEmpty() && 
                (scrollNoStress != null && scrollStress != null) || 
                ((scrollNoStress != null && scrollStress == null) || 
                (scrollNoStress == null && scrollStress != null)))
        {
            if (scrollNoStress != null)
            {
                horizontalData.addNoStressData(new BasicDataStatistic(scrollTimeLengthNoStressHorizontal, false));
            }
            if (scrollStress != null)
            {
                horizontalData.addStressData(new BasicDataStatistic(scrollTimeLengthStressHorizontal, false));
            }
        }
    }
    
    private void manageScrollInteractionLengthData(ArrayList<Scroll> scrollNoStress, 
            ArrayList<Scroll> scrollStress, StressNoStressData verticalData, 
            StressNoStressData horizontalData)
    {
        ArrayList<Double> scrollInteractionLengthNoStressVertical = new ArrayList<>(), 
                scrollInteractionLengthStressVertical = new ArrayList<>(), 
                scrollInteractionLengthNoStressHorizontal = new ArrayList<>(), 
                scrollInteractionLengthStressHorizontal = new ArrayList<>();
        
        if (scrollNoStress != null)
        {
        for (Scroll scroll: scrollNoStress)
            {
                if (scroll instanceof VerticalScroll)
                {
                    scrollInteractionLengthNoStressVertical.add(scroll.calculateScrollInteractionLength());
                }
                else if (scroll instanceof HorizontalScroll)
                {
                    scrollInteractionLengthNoStressHorizontal.add(scroll.calculateScrollInteractionLength());
                }
            }
        }
        if (scrollStress != null)
        {
            for (Scroll scroll: scrollStress)
            {
                if (scroll instanceof VerticalScroll)
                {
                    scrollInteractionLengthStressVertical.add(scroll.calculateScrollInteractionLength());
                }
                else if (scroll instanceof HorizontalScroll)
                {
                    scrollInteractionLengthStressHorizontal.add(scroll.calculateScrollInteractionLength());
                }
            }
        }
        if (!scrollInteractionLengthNoStressVertical.isEmpty() && 
                !scrollInteractionLengthStressVertical.isEmpty() && 
                (scrollNoStress != null && scrollStress != null) || 
                ((scrollNoStress != null && scrollStress == null) || 
                (scrollNoStress == null && scrollStress != null)))
        {
            if (scrollNoStress != null)
            {
                verticalData.addNoStressData(new BasicDataStatistic(scrollInteractionLengthNoStressVertical, false));
            }
            if (scrollStress != null)
            {
                verticalData.addStressData(new BasicDataStatistic(scrollInteractionLengthStressVertical, false));
            }
            
        }
        if (!scrollInteractionLengthNoStressHorizontal.isEmpty() && 
                !scrollInteractionLengthStressHorizontal.isEmpty() && 
                (scrollNoStress != null && scrollStress != null) || 
                ((scrollNoStress != null && scrollStress == null) || 
                (scrollNoStress == null && scrollStress != null)))
        {
            if (scrollNoStress != null)
            {
                horizontalData.addNoStressData(new BasicDataStatistic(scrollInteractionLengthNoStressHorizontal, false));
            }
            if (scrollStress != null)
            {
                horizontalData.addStressData(new BasicDataStatistic(scrollInteractionLengthStressHorizontal, false));
            }
        }
    }
    
    private void manageSpeedScrollDeltaData(ArrayList<Scroll> scrollNoStress, 
            ArrayList<Scroll> scrollStress, StressNoStressData verticalData, 
            StressNoStressData horizontalData)
    {
        ArrayList<Double> speedScrollDeltaNoStressVertical = new ArrayList<>(), 
                speedScrollDeltaStressVertical = new ArrayList<>(), 
                speedScrollDeltaNoStressHorizontal = new ArrayList<>(), 
                speedScrollDeltaStressHorizontal = new ArrayList<>();
        if (scrollNoStress != null)
        {
        for (Scroll scroll: scrollNoStress)
            {
                if (scroll instanceof VerticalScroll)
                {
                    speedScrollDeltaNoStressVertical.add(scroll.calculateSpeedScrollDelta());
                }
                else if (scroll instanceof HorizontalScroll)
                {
                    speedScrollDeltaNoStressHorizontal.add(scroll.calculateSpeedScrollDelta());
                }
            }
        }
        if (scrollStress != null)
        {
            for (Scroll scroll: scrollStress)
            {
                if (scroll instanceof VerticalScroll)
                {
                    speedScrollDeltaStressVertical.add(scroll.calculateSpeedScrollDelta());
                }
                else if (scroll instanceof HorizontalScroll)
                {
                    speedScrollDeltaStressHorizontal.add(scroll.calculateSpeedScrollDelta());
                }
            }
        }
        if (!speedScrollDeltaNoStressVertical.isEmpty() && 
                !speedScrollDeltaStressVertical.isEmpty() && 
                (scrollNoStress != null && scrollStress != null) || 
                ((scrollNoStress != null && scrollStress == null) || 
                (scrollNoStress == null && scrollStress != null)))
        {
            if (scrollNoStress != null)
            {
                verticalData.addNoStressData(new BasicDataStatistic(speedScrollDeltaNoStressVertical, false));
            }
            if (scrollStress != null)
            {
                verticalData.addStressData(new BasicDataStatistic(speedScrollDeltaStressVertical, false));
            }
        }
        if (!speedScrollDeltaNoStressHorizontal.isEmpty() && 
                !speedScrollDeltaStressHorizontal.isEmpty() && 
                (scrollNoStress != null && scrollStress != null) || 
                ((scrollNoStress != null && scrollStress == null) || 
                (scrollNoStress == null && scrollStress != null)))
        {
            if (scrollNoStress != null)
            {
                horizontalData.addNoStressData(new BasicDataStatistic(speedScrollDeltaNoStressHorizontal, false));
            }
            if (scrollStress != null)
            {
                horizontalData.addStressData(new BasicDataStatistic(speedScrollDeltaStressHorizontal, false));
            }
        }
    }
    
    private void manageSpeedScrollInteractionData(ArrayList<Scroll> scrollNoStress, 
            ArrayList<Scroll> scrollStress, StressNoStressData verticalData, 
            StressNoStressData horizontalData)
    {
        ArrayList<Double> speedScrollInteractionNoStressVertical = new ArrayList<>(), 
                speedScrollInteractionStressVertical = new ArrayList<>(), 
                speedScrollInteractionNoStressHorizontal = new ArrayList<>(), 
                speedScrollInteractionStressHorizontal = new ArrayList<>();
        
        if (scrollNoStress != null)
        {
            for (Scroll scroll: scrollNoStress)
            {
                if (scroll instanceof VerticalScroll)
                {
                    speedScrollInteractionNoStressVertical.add(scroll.calculateSpeedScrollInteraction());
                }
                else if (scroll instanceof HorizontalScroll)
                {
                    speedScrollInteractionNoStressHorizontal.add(scroll.calculateSpeedScrollInteraction());
                }
            }
        }
        if (scrollStress != null)
        {
            for (Scroll scroll: scrollStress)
            {
                if (scroll instanceof VerticalScroll)
                {
                    speedScrollInteractionStressVertical.add(scroll.calculateSpeedScrollInteraction());
                }
                else if (scroll instanceof HorizontalScroll)
                {
                    speedScrollInteractionStressHorizontal.add(scroll.calculateSpeedScrollInteraction());
                }
            }
        }
        if (!speedScrollInteractionNoStressVertical.isEmpty() && 
                !speedScrollInteractionStressVertical.isEmpty() && 
                (scrollNoStress != null && scrollStress != null) || 
                ((scrollNoStress != null && scrollStress == null) || 
                (scrollNoStress == null && scrollStress != null)))
        {
            if (scrollNoStress != null)
            {
                verticalData.addNoStressData(new BasicDataStatistic(speedScrollInteractionNoStressVertical, false));
            }
            if (scrollStress != null)
            {
                verticalData.addStressData(new BasicDataStatistic(speedScrollInteractionStressVertical, false));
            }
        }
        if (!speedScrollInteractionNoStressHorizontal.isEmpty() && 
                !speedScrollInteractionStressHorizontal.isEmpty() && 
                (scrollNoStress != null && scrollStress != null) || 
                ((scrollNoStress != null && scrollStress == null) || 
                (scrollNoStress == null && scrollStress != null)))
        {
            if (scrollNoStress != null)
            {
                horizontalData.addNoStressData(new BasicDataStatistic(speedScrollInteractionNoStressHorizontal, false));
            }
            if (scrollStress != null)
            {
                horizontalData.addStressData(new BasicDataStatistic(speedScrollInteractionStressHorizontal, false));
            }
        }
    }
    
    private void manageMeanDistanceFromCenter(ArrayList<Scroll> scrollNoStress, 
            ArrayList<Scroll> scrollStress, Tester tester, StressNoStressData verticalData, 
            StressNoStressData horizontalData)
    {
        ArrayList<Double> distanceFromCenterNoStressVertical = new ArrayList<>(),
                distanceFromCenterStressVertical = new ArrayList<>(), 
                distanceFromCenterNoStressHorizontal = new ArrayList<>(), 
                distanceFromCenterStressHorizontal = new ArrayList<>();
        
        PhoneSettings settings = tester.getPhoneSettings();

        if (scrollNoStress != null)
        {
            for (Scroll scroll: scrollNoStress)
            {
                if (scroll instanceof VerticalScroll)
                {
                    distanceFromCenterNoStressVertical.add(
                        scroll.meanDistanceFromPoint((int) settings.getScreenWidth() / 2, 
                                (int) settings.getScreenHeight() / 2));
                }
                else if (scroll instanceof HorizontalScroll)
                {
                    distanceFromCenterNoStressHorizontal.add(
                        scroll.meanDistanceFromPoint((int) settings.getScreenWidth() / 2, 
                                (int) settings.getScreenHeight() / 2));
                }
            }
        }
        if (scrollStress != null)
        {
            for (Scroll scroll: scrollStress)
            {
                if (scroll instanceof VerticalScroll)
                {
                    distanceFromCenterStressVertical.add(
                        scroll.meanDistanceFromPoint((int) settings.getScreenWidth() / 2, 
                                (int) settings.getScreenHeight() / 2));
                }
                else if (scroll instanceof HorizontalScroll)
                {
                    distanceFromCenterStressHorizontal.add(
                        scroll.meanDistanceFromPoint((int) settings.getScreenWidth() / 2, 
                                (int) settings.getScreenHeight() / 2));
                }
            }
        }
        if (!distanceFromCenterNoStressVertical.isEmpty() && 
                !distanceFromCenterStressVertical.isEmpty() && 
                (scrollNoStress != null && scrollStress != null) || 
                ((scrollNoStress != null && scrollStress == null) || 
                (scrollNoStress == null && scrollStress != null)))
        {
            if (scrollNoStress != null)
            {
                verticalData.addNoStressData(new BasicDataStatistic(distanceFromCenterNoStressVertical, false));
            }
            if (scrollStress != null)
            {
                verticalData.addStressData(new BasicDataStatistic(distanceFromCenterStressVertical, false));
            }
        }
        if (!distanceFromCenterNoStressHorizontal.isEmpty() && 
                !distanceFromCenterStressHorizontal.isEmpty() && 
                (scrollNoStress != null && scrollStress != null) || 
                ((scrollNoStress != null && scrollStress == null) || 
                (scrollNoStress == null && scrollStress != null)))
        {
            if (scrollNoStress != null)
            {
                horizontalData.addNoStressData(new BasicDataStatistic(distanceFromCenterNoStressHorizontal, false));
            }
            if (scrollStress != null)
            {
                horizontalData.addStressData(new BasicDataStatistic(distanceFromCenterStressHorizontal, false));
            }
        }
    }
    
    private void manageMeanDistanceFromTopLeft(ArrayList<Scroll> scrollNoStress, 
            ArrayList<Scroll> scrollStress, StressNoStressData verticalData, 
            StressNoStressData horizontalData)
    {
        ArrayList<Double> distanceFromTopLeftNoStressVertical = new ArrayList<>(), 
                distanceFromTopLeftStressVertical = new ArrayList<>(), 
                distanceFromTopLeftNoStressHorizontal = new ArrayList<>(),
                distanceFromTopLeftStressHorizontal = new ArrayList<>();
        if (scrollNoStress != null)
        {
            for (Scroll scroll: scrollNoStress)
            {
                if (scroll instanceof VerticalScroll)
                {
                    distanceFromTopLeftNoStressVertical.add(scroll.meanDistanceFromPoint(0, 0));
                }
                else if (scroll instanceof HorizontalScroll)
                {
                    distanceFromTopLeftNoStressHorizontal.add(scroll.meanDistanceFromPoint(0, 0));
                }
            }
        }
        if (scrollStress != null)
        {
            for (Scroll scroll: scrollStress)
            {
                if (scroll instanceof VerticalScroll)
                {
                    distanceFromTopLeftStressVertical.add(scroll.meanDistanceFromPoint(0, 0));
                }
                else if (scroll instanceof HorizontalScroll)
                {
                    distanceFromTopLeftStressHorizontal.add(scroll.meanDistanceFromPoint(0, 0));
                }
            }
        }
        if (!distanceFromTopLeftNoStressVertical.isEmpty() && 
                !distanceFromTopLeftStressVertical.isEmpty() && 
                (scrollNoStress != null && scrollStress != null) || 
                ((scrollNoStress != null && scrollStress == null) || 
                (scrollNoStress == null && scrollStress != null)))
        {
            if (scrollNoStress != null)
            {
                verticalData.addNoStressData(new BasicDataStatistic(distanceFromTopLeftNoStressVertical, false));
            }
            if (scrollStress != null)
            {
                verticalData.addStressData(new BasicDataStatistic(distanceFromTopLeftStressVertical, false));
            }
        }
        if (!distanceFromTopLeftNoStressHorizontal.isEmpty() && 
                !distanceFromTopLeftStressHorizontal.isEmpty()  && 
                (scrollNoStress != null && scrollStress != null) || 
                ((scrollNoStress != null && scrollStress == null) || 
                (scrollNoStress == null && scrollStress != null)))
        {
            if (scrollNoStress != null)
            {
                horizontalData.addNoStressData(new BasicDataStatistic(distanceFromTopLeftNoStressHorizontal, false));
            }
            if (scrollStress != null)
            {
                horizontalData.addStressData(new BasicDataStatistic(distanceFromTopLeftStressHorizontal, false));
            }
        }
    }
    
    private void manageLinearityData(ArrayList<Scroll> scrollNoStress, 
            ArrayList<Scroll> scrollStress, Tester tester, 
            StressNoStressData verticalData, StressNoStressData horizontalData)
    {
        ArrayList<Double> linearityNoStressVertical = new ArrayList<>(), 
                linearityStressVertical = new ArrayList<>(), 
                linearityNoStressHorizontal = new ArrayList<>(), 
                linearityStressHorizontal = new ArrayList<>();
        if (scrollNoStress != null)
        {
            for (Scroll scroll: scrollNoStress)
            {
                if (scroll instanceof VerticalScroll)
                {
                    linearityNoStressVertical.add(
                        scroll.calculateLinearity((int) 
                            tester.getPhoneSettings().getScreenWidth()));
                }
                else if (scroll instanceof HorizontalScroll)
                {
                    linearityNoStressHorizontal.add(
                        scroll.calculateLinearity((int) 
                            tester.getPhoneSettings().getScreenHeight()));
                }
            }
        }
        if (scrollStress != null)
        {
            for (Scroll scroll: scrollStress)
            {
                if (scroll instanceof VerticalScroll)
                {
                    linearityStressVertical.add(
                        scroll.calculateLinearity((int) 
                            tester.getPhoneSettings().getScreenWidth()));
                }
                else if (scroll instanceof HorizontalScroll)
                {
                    linearityStressHorizontal.add(
                        scroll.calculateLinearity((int) 
                            tester.getPhoneSettings().getScreenHeight()));
                }
            }
        }
        if (!linearityNoStressVertical.isEmpty() && 
                !linearityStressVertical.isEmpty() && 
                (scrollNoStress != null && scrollStress != null) || 
                ((scrollNoStress != null && scrollStress == null) || 
                (scrollNoStress == null && scrollStress != null)))
        {
            if (scrollNoStress != null)
            {
                verticalData.addNoStressData(new BasicDataStatistic(linearityNoStressVertical, false));
            }
            if (scrollStress != null)
            {
                verticalData.addStressData(new BasicDataStatistic(linearityStressVertical, false));
            }
        }
        if (!linearityNoStressHorizontal.isEmpty() && 
            !linearityStressHorizontal.isEmpty() && 
            (scrollNoStress != null && scrollStress != null) || 
            ((scrollNoStress != null && scrollStress == null) || 
            (scrollNoStress == null && scrollStress != null)))
        {
            if (scrollNoStress != null)
            {
                horizontalData.addNoStressData(new BasicDataStatistic(linearityNoStressHorizontal, false));
            }
            if (scrollStress != null)
            {
                horizontalData.addStressData(new BasicDataStatistic(linearityStressHorizontal, false));
            }
        }
    }
    
    private void manageLinearityAsSumEveryPoint(ArrayList<Scroll> scrollNoStress, 
            ArrayList<Scroll> scrollStress, Tester tester, 
            StressNoStressData verticalData, StressNoStressData horizontalData)
    {
        ArrayList<Double> linearityAsSumEveryPointNoStressVertical = new ArrayList<>(), 
                linearityAsSumEveryPointStressVertical = new ArrayList<>(), 
                linearityAsSumEveryPointNoStressHorizontal = new ArrayList<>(), 
                linearityAsSumEveryPointStressHorizontal = new ArrayList<>();
        
        if (scrollNoStress != null)
        {
        for (Scroll scroll: scrollNoStress)
        {
            if (scroll instanceof VerticalScroll)
            {
                linearityAsSumEveryPointNoStressVertical.add(scroll.calculateLinearityAsSumOfEveryPoint(
                        (int) tester.getPhoneSettings().getScreenWidth()));
            }
            else if (scroll instanceof HorizontalScroll)
            {
                linearityAsSumEveryPointNoStressHorizontal.add(scroll.calculateLinearityAsSumOfEveryPoint(
                        (int) tester.getPhoneSettings().getScreenHeight()));
            }
        }
        }
        if (scrollStress != null)
        {
            for (Scroll scroll: scrollStress)
            {
                if (scroll instanceof VerticalScroll)
                {
                    linearityAsSumEveryPointStressVertical.add(scroll.calculateLinearityAsSumOfEveryPoint(
                            (int) tester.getPhoneSettings().getScreenWidth()));
                }
                else
                {
                    linearityAsSumEveryPointStressHorizontal.add(scroll.calculateLinearityAsSumOfEveryPoint(
                            (int) tester.getPhoneSettings().getScreenHeight()));
                }
            }
        }
        if ((!linearityAsSumEveryPointNoStressVertical.isEmpty() && 
                !linearityAsSumEveryPointStressVertical.isEmpty() && 
                scrollNoStress != null && scrollStress != null) || 
                ((scrollNoStress != null && scrollStress == null) || 
                (scrollNoStress == null && scrollStress != null)))
        {
            if (scrollNoStress != null)
            {
                verticalData.addNoStressData(new BasicDataStatistic(linearityAsSumEveryPointNoStressVertical, false));
            }
            if (scrollStress != null)
            {
                verticalData.addStressData(new BasicDataStatistic(linearityAsSumEveryPointStressVertical, false));
            }
        }
        if ((!linearityAsSumEveryPointNoStressHorizontal.isEmpty() && 
                !linearityAsSumEveryPointStressHorizontal.isEmpty() && 
                scrollNoStress != null && scrollStress != null) ||  
                ((scrollNoStress != null && scrollStress == null) || 
                (scrollNoStress == null && scrollStress != null)))
        {
            if (scrollNoStress != null)
            {
                horizontalData.addNoStressData(new BasicDataStatistic(linearityAsSumEveryPointNoStressHorizontal, false));
            }
            if (scrollStress != null)
            {
                horizontalData.addStressData(new BasicDataStatistic(linearityAsSumEveryPointStressHorizontal, false));
            }
        }
    }
}
