package isensestressanalyzer.analyzer;

import isensestressanalyzer.dataanalysis.BasicDataStatistic;

/**
 *
 * @author Matteo Ciman
 */
public class SearchAnalysisResume 
{
    private BasicDataStatistic pressureDataClick, sizeDataClick, 
            movementDataClick, averagePressureScrollVertical, averagePressureScrollHorizontal, 
            averageSizeScrollVertical, averageSizeScrollHorizontal, 
            scrollDeltaDataVertical, scrollDeltaDataHorizontal, 
            scrollTimeLengthDataVertical, scrollTimeLengthDataHorizontal,
            scrollInteractionLengthDataVertical, scrollInteractionLengthDataHorizontal, 
            speedScrollDeltaDataVertical, speedScrollDeltaDataHorizontal, 
            speedScrollInteractionDataVertical, speedScrollInteractionDataHorizontal, 
            meanDistanceFromCenterDataVertical, meanDistanceFromCenterDataHorizontal, 
            meanDistanceFromTopLeftDataVertical, meanDistanceFromTopLeftDataHorizontal, 
            linearityDataVertical, linearityDataHorizontal, 
            linearityAsSumEveryPointDataVertical, linearityAsSumEveryPointDataHorizontal;
    
    public void pressureDataClick(BasicDataStatistic data)
    {
        this.pressureDataClick = data;
    }
    
    public BasicDataStatistic getPressureDataClick() {
        return this.pressureDataClick;
    }
    
    public void sizeDataClick(BasicDataStatistic data)
    {
        this.sizeDataClick = data;
    }
    
    public BasicDataStatistic getSizeDataClick() {
        return this.sizeDataClick;
    }
    
    public void movementDataClick(BasicDataStatistic data)
    {
        this.movementDataClick = data;
    }
    
    public BasicDataStatistic getMovementDataClick() {
        return this.movementDataClick;
    }
    
    public void averagePressureScrollDataVertical(BasicDataStatistic data) {
    	this.averagePressureScrollVertical = data;
    }
    
    public BasicDataStatistic getAveragePressureScrollDataVertical() {
    	return this.averagePressureScrollVertical;
    }
    
    public void averagePressureScrollDataHorizontal(BasicDataStatistic data) {
    	this.averagePressureScrollHorizontal = data;
    }
    
    public BasicDataStatistic getAveragePressureScrollDataHorizontal() {
    	return this.averagePressureScrollHorizontal;
    }
    
    public void averageSizeScrollDataVertical(BasicDataStatistic data) {
    	this.averageSizeScrollVertical = data;
    }
    
    public BasicDataStatistic getAverageSizeScrollDataVertical() {
    	return this.averageSizeScrollVertical;
    }
    
    public void averageSizeScrollDataHorizontal(BasicDataStatistic data) {
    	this.averageSizeScrollHorizontal = data;
    }
    
    public BasicDataStatistic getAverageSizeScrollDataHorizontal() {
    	return this.averageSizeScrollHorizontal;
    }
    
    public void scrollDeltaDataVertical(BasicDataStatistic data)
    {
        this.scrollDeltaDataVertical = data;
    }
    
    public BasicDataStatistic getScrollDeltaDataVertical() {
        return this.scrollDeltaDataVertical;
    }
    
    public void scrollDeltaDataHorizontal(BasicDataStatistic data)
    {
        this.scrollDeltaDataHorizontal = data;
    }
    
    public BasicDataStatistic getScrollDeltaDataHorizontal() {
        return this.scrollDeltaDataHorizontal;
    }
    
    public void scrollTimeLengthDataVertical(BasicDataStatistic data)
    {
        this.scrollTimeLengthDataVertical = data;
    }
    
    public BasicDataStatistic getScrollTimeLengthDataVertical() {
        return this.scrollTimeLengthDataVertical;
    }
    
    public void scrollTimeLengthDataHorizontal(BasicDataStatistic data)
    {
        this.scrollTimeLengthDataHorizontal = data;
    }
    
    public BasicDataStatistic getScrollTimeLengthDataHorizontal() {
        return this.scrollTimeLengthDataHorizontal;
    }
    
    public void scrollInteractionLengthDataVertical(BasicDataStatistic data)
    {
        this.scrollInteractionLengthDataVertical = data;
    }
    
    public BasicDataStatistic getScrollInteractionLengthDataVertical() {
        return this.scrollInteractionLengthDataVertical;
    }
    
    public void scrollInteractionLengthDataHorizontal(BasicDataStatistic data)
    {
        this.scrollInteractionLengthDataHorizontal = data;
    }
    
    public BasicDataStatistic getScrollInteractionLengthDataHorizontal() {
        return this.scrollInteractionLengthDataHorizontal;
    }
    
    public void speedScrollDeltaDataVertical(BasicDataStatistic data)
    {
        this.speedScrollDeltaDataVertical = data;
    }
    
    public BasicDataStatistic getSpeedScrollDeltaDataVertical() {
        return this.speedScrollDeltaDataVertical;
    }
    
    public void speedScrollDeltaDataHorizontal(BasicDataStatistic data)
    {
        this.speedScrollDeltaDataHorizontal = data;
    }
    
    public BasicDataStatistic getSpeedScrollDeltaDataHorizontal() {
        return this.speedScrollDeltaDataHorizontal;
    }
    
    public void speedScrollInteractionDataVertical(BasicDataStatistic data)
    {
        this.speedScrollInteractionDataVertical = data;
    }
    
    public BasicDataStatistic getSpeedScrollInteractionDataVertical() {
        return this.speedScrollInteractionDataVertical;
    }
    
    public void speedScrollInteractionDataHorizontal(BasicDataStatistic data)
    {
        this.speedScrollInteractionDataHorizontal = data;
    }
    
    public BasicDataStatistic getSpeedScrollInteractionDataHorizontal() {
        return this.speedScrollInteractionDataHorizontal;
    }
    
    public void meanDistanceFromCenterDataVertical(BasicDataStatistic data)
    {
        this.meanDistanceFromCenterDataVertical = data;
    }
    
    public BasicDataStatistic getMeanDistanceFromCenterDataVertical() {
        return this.meanDistanceFromCenterDataVertical;
    }
    
    public void meanDistanceFromCenterDataHorizontal(BasicDataStatistic data)
    {
        this.meanDistanceFromCenterDataHorizontal = data;
    }
    
    public BasicDataStatistic getMeanDistanceFromCenterDataHorizontal() {
        return this.meanDistanceFromCenterDataHorizontal;
    }
    
    public void meanDistanceFromTopLeftDataVertical(BasicDataStatistic data)
    {
        this.meanDistanceFromTopLeftDataVertical = data;
    }
    
    public BasicDataStatistic getMeanDistanceFromTopLeftDataVertical() {
        return this.meanDistanceFromTopLeftDataVertical;
    }
    
    public void meandDistanceFromTopLeftDataHorizontal(BasicDataStatistic data)
    {
        this.meanDistanceFromTopLeftDataHorizontal = data;
    }
    
    public BasicDataStatistic getMeanDistanceFromTopLeftDataHorizontal() {
        return this.meanDistanceFromTopLeftDataHorizontal;
    }
    
    public void linearityDataVertical(BasicDataStatistic data)
    {
        this.linearityDataVertical = data;
    }
    
    public BasicDataStatistic getLinearityDataVertical() {
        return this.linearityDataVertical;
    }
    
    public void linearityDataHorizontal(BasicDataStatistic data)
    {
        this.linearityDataHorizontal = data;
    }
    
    public BasicDataStatistic getLinearityDataHorizontal() {
        return this.linearityDataHorizontal;
    }
    
    public void linearityAsSumEveryPointDataVertical(BasicDataStatistic data)
    {
        this.linearityAsSumEveryPointDataVertical = data;
    }
    
    public BasicDataStatistic getLinearityAsSumEveryPointDataVertical() {
        return this.linearityAsSumEveryPointDataVertical;
    }
    
    public void linearityAsSumEveryPointDataHorizontal(BasicDataStatistic data)
    {
        this.linearityAsSumEveryPointDataHorizontal = data;
    }
    
    public BasicDataStatistic getLinearityAsSumEveryPointDataHorizontal() {
        return this.linearityAsSumEveryPointDataHorizontal;
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
