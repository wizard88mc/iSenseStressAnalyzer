package isensestressanalyzer.analyzer;

import isensestressanalyzer.dataanalysis.BasicDataStatistic;
import isensestressanalyzer.dataanalysis.RotationDataWrapper;
import isensestressanalyzer.dataanalysis.StressNoStressData;
import isensestressanalyzer.dataanalysis.StressNoStressRotationData;
import java.util.ArrayList;

/**
 * Holds all the data coming from the analysis of the write task
 * @author Matteo Ciman
 * @version 0.1
 * @since 2014-11-13
 */
public class WriteAnalysisResume 
{
    private BasicDataStatistic pressureData;
    private BasicDataStatistic sizeData;
    private BasicDataStatistic ratioPressureSizeData;
    private BasicDataStatistic movementData;
    private BasicDataStatistic durationData;
    private BasicDataStatistic precisionData;
    private BasicDataStatistic ratioBackOverDigits;
    private BasicDataStatistic ratioWrongAllWords;
    private BasicDataStatistic digitsFrequency;
    private StressNoStressRotationData rotationData;
    
   public void pressureData(BasicDataStatistic data)
   {
       pressureData = data;
   }
   
   public void sizeData(BasicDataStatistic data)
   {
       sizeData = data;
   }
   
   public void ratioPressureSizeData(BasicDataStatistic data) {
	   ratioPressureSizeData = data;
   }
   
   public void movementData(BasicDataStatistic data)
   {
       movementData = data;
   }
   
   public void durationData(BasicDataStatistic data)
   {
       durationData = data;
   }
   
   public void precisionData(BasicDataStatistic data)
   {
       precisionData = data;
   }
   
   public void ratioBackOverDigitsData(BasicDataStatistic data)
   {
       ratioBackOverDigits = data;
   }
   
   public void ratioWrongAllWords(BasicDataStatistic data)
   {
       ratioWrongAllWords = data;
   }
   
   public void digitsFrequency(BasicDataStatistic data)
   {
       digitsFrequency = data;
   }
   
   public void rotationData(ArrayList<RotationDataWrapper> noStress, 
           ArrayList<RotationDataWrapper> stress)
   {
       rotationData = new StressNoStressRotationData(noStress, stress);
   }
   
   public BasicDataStatistic getPressureData()
   {
       return pressureData;
   }
   
   public BasicDataStatistic getSizeData()
   {
       return this.sizeData;
   }
   
   public BasicDataStatistic getMovementData()
   {
       return this.movementData;
   }
   
   public BasicDataStatistic getRatioPressureSizeData() {
	   return this.ratioPressureSizeData;
   }
   
   public BasicDataStatistic getDurationData()
   {
       return this.durationData;
   }
   
   public BasicDataStatistic getPrecisionData()
   {
       return this.precisionData;
   }
   
   public BasicDataStatistic getRatioBackOverDigits()
   {
       return this.ratioBackOverDigits;
   }
   
   public BasicDataStatistic getRatioWrongAllWords()
   {
       return this.ratioWrongAllWords;
   }
   
   public BasicDataStatistic getDigitsFrequencyData()
   {
       return this.digitsFrequency;
   }
   
   public StressNoStressRotationData getRotationData()
   {
       return rotationData;
   }
}
