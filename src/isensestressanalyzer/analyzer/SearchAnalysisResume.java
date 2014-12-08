package isensestressanalyzer.analyzer;

import isensestressanalyzer.dataanalysis.BasicDataStatistic;
import isensestressanalyzer.dataanalysis.StressNoStressData;

/**
 *
 * @author Matteo Ciman
 */
public class SearchAnalysisResume 
{
    private StressNoStressData pressureDataClick, sizeDataClick, 
            movementDataClick, scrollDeltaDataVertical, scrollDeltaDataHorizontal, 
            scrollTimeLengthDataVertical, scrollTimeLengthDataHorizontal,
            scrollInteractionLengthDataVertical, scrollInteractionLengthDataHorizontal, 
            speedScrollDeltaDataVertical, speedScrollDeltaDataHorizontal, 
            speedScrollInteractionDataVertical, speedScrollInteractionDataHorizontal, 
            meanDistanceFromCenterDataVertical, meanDistanceFromCenterDataHorizontal, 
            meanDistanceFromTopLeftDataVertical, meanDistanceFromTopLeftDataHorizontal, 
            linearityDataVertical, linearityDataHorizontal, 
            linearityAsSumEveryPointDataVertical, linearityAsSumEveryPointDataHorizontal;
    
    public void pressureDataClick(BasicDataStatistic noStress, 
            BasicDataStatistic stress)
    {
        this.pressureDataClick = new StressNoStressData("Touch pressure", noStress, stress);
    }
    
    public void sizeDataClick(BasicDataStatistic noStress, 
            BasicDataStatistic stress)
    {
        this.sizeDataClick = new StressNoStressData("Touch size", noStress, stress);
    }
    
    public void movementDataClick(BasicDataStatistic noStress, 
            BasicDataStatistic stress)
    {
        this.movementDataClick = new StressNoStressData("Touch movement", noStress, stress);
    }
    
    public void scrollDeltaDataVertical(BasicDataStatistic noStress, 
            BasicDataStatistic stress)
    {
        this.scrollDeltaDataVertical = new StressNoStressData("Scroll delta vertical", 
                noStress, stress);
    }
    
    public void scrollDeltaDataHorizontal(BasicDataStatistic noStress, 
            BasicDataStatistic stress)
    {
        this.scrollDeltaDataHorizontal = new StressNoStressData("Scroll delta horizontal", 
                noStress, stress);
    }
    
    public void scrollTimeLengthDataVertical(BasicDataStatistic noStress, 
            BasicDataStatistic stress)
    {
        this.scrollTimeLengthDataVertical = new StressNoStressData("Scroll time length vertical", 
                noStress, stress);
    }
    
    public void scrollTimeLengthDataHorizontal(BasicDataStatistic noStress, 
            BasicDataStatistic stress)
    {
        this.scrollTimeLengthDataHorizontal = new StressNoStressData("Scroll time length horizontal", 
                noStress, stress);
    }
    
    public void scrollInteractionLengthDataVertical(BasicDataStatistic noStress, 
            BasicDataStatistic stress)
    {
        this.scrollInteractionLengthDataVertical = new StressNoStressData("Scroll interaction length vertical",
                noStress, stress);
    }
    
    public void scrollInteractionLengthDataHorizontal(BasicDataStatistic noStress,
            BasicDataStatistic stress)
    {
        this.scrollInteractionLengthDataHorizontal = new StressNoStressData("Scroll interaction length horizontal", 
                noStress, stress);
    }
    
    public void speedScrollDeltaDataVertical(BasicDataStatistic noStress, 
            BasicDataStatistic stress)
    {
        this.speedScrollDeltaDataVertical = new StressNoStressData("Speed scroll delta vertical", 
                noStress, stress);
    }
    
    public void speedScrollDeltaDataHorizontal(BasicDataStatistic noStress, 
            BasicDataStatistic stress)
    {
        this.speedScrollDeltaDataHorizontal = new StressNoStressData("Speed scroll delta horizontal", 
                noStress, stress);
    }
    
    public void speedScrollInteractionDataVertical(BasicDataStatistic noStress,
            BasicDataStatistic stress)
    {
        this.speedScrollInteractionDataVertical = new StressNoStressData("Speed scroll interaction vertical", 
                noStress, stress);
    }
    
    public void speedScrollInteractionDataHorizontal(BasicDataStatistic noStress, 
            BasicDataStatistic stress)
    {
        this.speedScrollInteractionDataHorizontal = new StressNoStressData("Speed scroll interaction horizontal", 
                noStress, stress);
    }
    
    public void meanDistanceFromCenterDataVertical(BasicDataStatistic noStress, 
            BasicDataStatistic stress)
    {
        this.meanDistanceFromCenterDataVertical = new StressNoStressData("Mean distance from center vertical", 
                noStress, stress);
    }
    
    public void meanDistanceFromCenterDataHorizontal(BasicDataStatistic noStress, 
            BasicDataStatistic stress)
    {
        this.meanDistanceFromCenterDataHorizontal = new StressNoStressData("Mean distance from center horizontal",
                noStress, stress);
    }
    
    public void meanDistanceFromTopLeftDataVertical(BasicDataStatistic noStress,
            BasicDataStatistic stress)
    {
        this.meanDistanceFromTopLeftDataVertical = new StressNoStressData("Mean distance from top left vertical", 
                noStress, stress);
    }
    
    public void meandDistanceFromTopLeftDataHorizontal(BasicDataStatistic noStress,
            BasicDataStatistic stress)
    {
        this.meanDistanceFromTopLeftDataHorizontal = new StressNoStressData("Mean distance from top left horizontal", 
                noStress, stress);
    }
    
    public void linearityDataVertical(BasicDataStatistic noStress, 
            BasicDataStatistic stress)
    {
        this.linearityDataVertical = new StressNoStressData("Linearity vertical", 
                noStress, stress);
    }
    
    public void linearityDataHorizontal(BasicDataStatistic noStress, 
            BasicDataStatistic stress)
    {
        this.linearityDataHorizontal = new StressNoStressData("Linearity horizontal", 
                noStress, stress);
    }
    
    public void linearityAsSumEveryPointDataVertical(BasicDataStatistic noStress, 
            BasicDataStatistic stress)
    {
        this.linearityAsSumEveryPointDataVertical = new StressNoStressData("Linearity as sum every point vertical", 
                noStress, stress);
    }
    
    public void linearityAsSumEveryPointDataHorizontal(BasicDataStatistic noStress,
            BasicDataStatistic stress)
    {
        this.linearityAsSumEveryPointDataHorizontal = new StressNoStressData("Linearity as sum every point horizontal", 
                noStress, stress);
    }
    
    /*public void printAnalysis()
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
    }*/
}
