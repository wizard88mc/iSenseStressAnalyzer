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
    
   public void pressureData(ArrayList<BasicDataStatistic> noStress, 
           ArrayList<BasicDataStatistic> stress)
   {
       pressureData = new StressNoStressData("Pressure", noStress, stress);
   }
   
   public void sizeData(ArrayList<BasicDataStatistic> noStress, 
           ArrayList<BasicDataStatistic> stress)
   {
       sizeData = new StressNoStressData("Size", noStress, stress);
   }
   
   public void movementData(ArrayList<BasicDataStatistic> noStress, 
           ArrayList<BasicDataStatistic> stress)
   {
       movementData = new StressNoStressData("Movement", noStress, stress);
   }
   
   public void durationData(ArrayList<BasicDataStatistic> noStress, 
           ArrayList<BasicDataStatistic> stress)
   {
       durationData = new StressNoStressData("Duration", noStress, stress);
   }
   
   public void precisionData(ArrayList<BasicDataStatistic> noStress, 
           ArrayList<BasicDataStatistic> stress)
   {
       precisionData = new StressNoStressData("Precision", noStress, stress);
   }
   
   public void ratioBackOverDigitsData(ArrayList<BasicDataStatistic> noStress, 
           ArrayList<BasicDataStatistic> stress)
   {
       ratioBackOVerDigits = new StressNoStressData("Ratio Back Digits over all digits", 
               noStress, stress);
   }
   
   public void ratioWrongAllWords(ArrayList<BasicDataStatistic> noStress, 
           ArrayList<BasicDataStatistic> stress)
   {
       ratioWrongAllWords = new StressNoStressData("Ratio wrong words over all words", noStress, stress);
   }
   
   public void digitsFrequency(ArrayList<BasicDataStatistic> noStress, 
           ArrayList<BasicDataStatistic> stress)
   {
       digitsFrequency = new StressNoStressData("Digits frequency", noStress, stress);
   }
   
   public void rotationData(ArrayList<RotationDataWrapper> noStress, 
           ArrayList<RotationDataWrapper> stress)
   {
       rotationData = new StressNoStressRotationData(noStress, stress);
   }
   
   /**
    * Prints the results of the t-test for each feature we are considering
    */
   public void printAnalysis()
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
   }
}
