package isensestressanalyzer.analyzer;

import isensestressanalyzer.dataanalysis.StressNoStressData;

/**
 *
 * @author Matteo Ciman
 */
public class SearchAnalysisResume 
{
    private StressNoStressData pressureDataClick;
    private StressNoStressData sizeDataClick;
    private StressNoStressData movementDataClick;
    
    private StressNoStressData scrollDeltaDataVertical,
        scrollDeltaDataHorizontal, scrollTimeLengthDataVertical,
        scrollTimeLengthDataHorizontal,scrollInteractionLengthDataVertical,
        scrollInteractionLengthDataHorizontal, speedScrollDeltaDataVertical,
        speedScrollDeltaDataHorizontal, speedScrollInteractionDataVertical,
        speedScrollInteractionDataHorizontal, meanDistanceFromCenterDataVertical,
        meanDistanceFromCenterDataHorizontal, meanDistanceFromTopLeftDataVertical,
        meanDistanceFromTopLeftDataHorizontal, linearityDataVertical, 
        linearityDataHorizontal, linearityAsSumEveryPointDataVertical, 
        linearityAsSumEveryPointDataHorizontal;
    
    public void pressureDataClick(StressNoStressData data)
    {
        this.pressureDataClick = data;
    }
    
    public void sizeDataClick(StressNoStressData data)
    {
        this.sizeDataClick = data;
    }
    
    public void movementDataClick(StressNoStressData data)
    {
        this.movementDataClick = data;
    }
    
    public void scrollDeltaData(StressNoStressData vertical, 
            StressNoStressData horizontal)
    {
        this.scrollDeltaDataVertical = vertical; 
        this.scrollDeltaDataHorizontal = horizontal;
    }
    
    public void scrollTimeLengthData(StressNoStressData vertical, 
            StressNoStressData horizontal)
    {
        this.scrollTimeLengthDataVertical = vertical; 
        this.scrollTimeLengthDataHorizontal = horizontal;
    }
    
    public void scrollInteractionLengthData(StressNoStressData vertical, 
            StressNoStressData horizontal)
    {
        this.scrollInteractionLengthDataVertical = vertical;
        this.scrollInteractionLengthDataHorizontal = horizontal;
    }
    
    public void speedScrollDeltaData(StressNoStressData vertical, 
            StressNoStressData horizontal)
    {
        this.speedScrollDeltaDataVertical = vertical;
        this.speedScrollDeltaDataHorizontal = horizontal;
    }
    
    public void speedScrollInteractionData(StressNoStressData vertical, 
            StressNoStressData horizontal)
    {
        this.speedScrollInteractionDataVertical = vertical;
        this.speedScrollInteractionDataHorizontal = horizontal;
    }
    
    public void meanDistanceFromCenterData(StressNoStressData vertical, 
            StressNoStressData horizontal)
    {
        this.meanDistanceFromCenterDataVertical = vertical;
        this.meanDistanceFromCenterDataHorizontal = horizontal;
    }
    
    public void meandDistanceFromTopLeftData(StressNoStressData vertical, 
            StressNoStressData horizontal)
    {
        this.meanDistanceFromTopLeftDataVertical = vertical;
        this.meanDistanceFromTopLeftDataHorizontal = horizontal;
    }
    
    public void linearityData(StressNoStressData vertical, 
            StressNoStressData horizontal)
    {
        this.linearityDataVertical = vertical;
        this.linearityDataHorizontal = horizontal;
    }
    
    public void linearityAsSumEveryPointData(StressNoStressData vertical, 
            StressNoStressData horizontal)
    {
        this.linearityAsSumEveryPointDataVertical = vertical; 
        this.linearityAsSumEveryPointDataHorizontal = horizontal;
    }
    
    public void printAnalysis()
    {
        if (!pressureDataClick.isEmpty())
            pressureDataClick.makeAndPrintTTest();
        if (!sizeDataClick.isEmpty())
            sizeDataClick.makeAndPrintTTest();
        if (!movementDataClick.isEmpty())
            movementDataClick.makeAndPrintTTest();
        if (!scrollDeltaDataVertical.isEmpty())
            scrollDeltaDataVertical.makeAndPrintTTest();
        if (!scrollDeltaDataHorizontal.isEmpty())
            scrollDeltaDataHorizontal.makeAndPrintTTest();
        if (!scrollTimeLengthDataVertical.isEmpty())
            scrollTimeLengthDataVertical.makeAndPrintTTest();
        if (!scrollTimeLengthDataHorizontal.isEmpty())
            scrollTimeLengthDataHorizontal.makeAndPrintTTest();
        if (!scrollInteractionLengthDataVertical.isEmpty())
            scrollInteractionLengthDataVertical.makeAndPrintTTest();
        if (!scrollInteractionLengthDataHorizontal.isEmpty())
            scrollInteractionLengthDataHorizontal.makeAndPrintTTest();
        if (!speedScrollDeltaDataVertical.isEmpty())
            speedScrollDeltaDataVertical.makeAndPrintTTest();
        if (!speedScrollDeltaDataHorizontal.isEmpty())
            speedScrollDeltaDataHorizontal.makeAndPrintTTest();
        if (!speedScrollInteractionDataVertical.isEmpty())
            speedScrollInteractionDataVertical.makeAndPrintTTest();
        if (!speedScrollInteractionDataHorizontal.isEmpty())
            speedScrollInteractionDataHorizontal.makeAndPrintTTest();
        if (!meanDistanceFromCenterDataVertical.isEmpty())
            meanDistanceFromCenterDataVertical.makeAndPrintTTest();
        if (!meanDistanceFromCenterDataHorizontal.isEmpty())
            meanDistanceFromCenterDataHorizontal.makeAndPrintTTest();
        if (!meanDistanceFromTopLeftDataVertical.isEmpty())
            meanDistanceFromTopLeftDataVertical.makeAndPrintTTest();
        if (!meanDistanceFromTopLeftDataHorizontal.isEmpty())
            meanDistanceFromTopLeftDataHorizontal.makeAndPrintTTest();
        if (!linearityDataVertical.isEmpty())
            linearityDataVertical.makeAndPrintTTest();
        if (!linearityDataHorizontal.isEmpty())
            linearityDataHorizontal.makeAndPrintTTest();
        if (!linearityAsSumEveryPointDataVertical.isEmpty())
            linearityAsSumEveryPointDataVertical.makeAndPrintTTest();
        if (!linearityAsSumEveryPointDataHorizontal.isEmpty())
            linearityAsSumEveryPointDataHorizontal.makeAndPrintTTest();
    }
}
