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
    private StressNoStressData pressureData;
    private StressNoStressData sizeData;
    private StressNoStressData movementData;
    private StressNoStressData durationData;
    private StressNoStressData precisionData;
    private StressNoStressData ratioBackOVerDigits;
    private StressNoStressData ratioWrongAllWords;
    private StressNoStressData digitsFrequency;
    private StressNoStressRotationData rotationData;
    
   public void pressureData(BasicDataStatistic noStress, 
           BasicDataStatistic stress)
   {
       pressureData = new StressNoStressData("Pressure", noStress, stress);
   }
   
   public void sizeData(BasicDataStatistic noStress, 
           BasicDataStatistic stress)
   {
       sizeData = new StressNoStressData("Size", noStress, stress);
   }
   
   public void movementData(BasicDataStatistic noStress, 
           BasicDataStatistic stress)
   {
       movementData = new StressNoStressData("Movement", noStress, stress);
   }
   
   public void durationData(BasicDataStatistic noStress, 
           BasicDataStatistic stress)
   {
       durationData = new StressNoStressData("Duration", noStress, stress);
   }
   
   public void precisionData(BasicDataStatistic noStress, 
           BasicDataStatistic stress)
   {
       precisionData = new StressNoStressData("Precision", noStress, stress);
   }
   
   public void ratioBackOverDigitsData(BasicDataStatistic noStress, 
           BasicDataStatistic stress)
   {
       ratioBackOVerDigits = new StressNoStressData("Ratio Back Digits over all digits", 
               noStress, stress);
   }
   
   public void ratioWrongAllWords(BasicDataStatistic noStress, 
           BasicDataStatistic stress)
   {
       ratioWrongAllWords = new StressNoStressData("Ratio wrong words over all words", noStress, stress);
   }
   
   public void digitsFrequency(BasicDataStatistic noStress, 
           BasicDataStatistic stress)
   {
       digitsFrequency = new StressNoStressData("Digits frequency", noStress, 
               stress);
   }
   
   public void rotationData(ArrayList<RotationDataWrapper> noStress, 
           ArrayList<RotationDataWrapper> stress)
   {
       rotationData = new StressNoStressRotationData(noStress, stress);
   }
   
   public StressNoStressData getPressureData()
   {
       return pressureData;
   }
   
   public StressNoStressData getSizeData()
   {
       return this.sizeData;
   }
   
   public StressNoStressData getMovementData()
   {
       return this.movementData;
   }
   
   public StressNoStressData getDurationData()
   {
       return this.durationData;
   }
   
   public StressNoStressData getPrecisionData()
   {
       return this.precisionData;
   }
   
   public StressNoStressData getRatioBackOverDigits()
   {
       return this.ratioBackOVerDigits;
   }
   
   public StressNoStressData getRatioWrongAllWords()
   {
       return this.ratioWrongAllWords;
   }
   
   public StressNoStressData getDigitsFrequencyData()
   {
       return this.digitsFrequency;
   }
   
   public StressNoStressRotationData getRotationData()
   {
       return rotationData;
   }
   
   /**
    * Prints the results of the t-test for each feature we are considering
    */
   /*public void printAnalysis()
   {
       pressureData.makeAndPrintTTest();
       sizeData.makeAndPrintTTest();
       movementData.makeAndPrintTTest();
       durationData.makeAndPrintTTest();
       precisionData.makeAndPrintTTest();
       ratioBackOVerDigits.makeAndPrintTTest();
       ratioWrongAllWords.makeAndPrintTTest();
       digitsFrequency.makeAndPrintTTest();
       rotationData.makeAndPrintTest();
   }*/
}
