package isensestressanalyzer.analyzer;

import isensestressanalyzer.ISenseStressAnalyzer;
import isensestressanalyzer.dataanalysis.StressNoStressData;
import isensestressanalyzer.exercise.Survey;
import java.util.ArrayList;
import org.apache.commons.math3.stat.inference.TTest;
import tester.Tester;

/**
 *
 * @author Matteo Ciman
 */
public class SurveyAnalyzer {
    
    private static class FastResumeSurvey {
        public ArrayList<Double> valenceAnswers = new ArrayList<>(),
            energyAnswers = new ArrayList<>(),
            stressAnswers = new ArrayList<>();
        
        public void addValues(double valence, double energy, double stress) {
            valenceAnswers.add(valence); energyAnswers.add(energy);
            stressAnswers.add(stress);
        }
        
        public double getValenceMean() {
            double mean = 0;
            for (Double value: valenceAnswers) {
                mean += value;
            }
            return mean / valenceAnswers.size();
        }
        
        public double getEnergyMean() {
            double mean = 0;
            for (Double value: energyAnswers) {
                mean += value;
            }
            return mean / energyAnswers.size();
        }
        
        public double getStressMean() {
            double mean = 0;
            for (Double value: stressAnswers) {
                mean += value;
            }
            return mean / stressAnswers.size();
        }
    }
    
    public static void performAnalysis(ArrayList<Tester> listTester) {
        
        ArrayList<ArrayList<Survey>> mListSurveys = new ArrayList<>();
        
        for (Tester tester: listTester) {
            
            ArrayList<Survey> answers = tester.getSurveyListForProtocol(ISenseStressAnalyzer.protocols[0]);
            
            for (int i = 0; i < answers.size(); i++) {
                if (mListSurveys.size() < i + 1) {
                    mListSurveys.add(new ArrayList<Survey>());
                }
                mListSurveys.get(i).add(answers.get(i));
            }
        }
        
        ArrayList<FastResumeSurvey> resumes = new ArrayList<>();
        
        for (int i = 0; i < mListSurveys.size(); i++) {
            
            System.out.println("Survey " + i);
            ArrayList<Survey> surveys = mListSurveys.get(i);
            FastResumeSurvey toAdd = new FastResumeSurvey();
            
            for (Survey survey: surveys) {
                
                toAdd.addValues(survey.getIntValence(), survey.getIntEnergy(), 
                        survey.getIntStress());
            }
            
            System.out.println("Average valence: " + toAdd.getValenceMean());
            System.out.println("Average energy: " + toAdd.getEnergyMean());
            System.out.println("Average stress: " + toAdd.getStressMean());
            
            resumes.add(toAdd);
        }
        
        performTestBetweenTwoSurveys(resumes.get(0), resumes.get(1), "Begin", "After relax");
        performTestBetweenTwoSurveys(resumes.get(2), resumes.get(3), "Before stressor", "After stressor");
        performTestBetweenTwoSurveys(resumes.get(2), resumes.get(4), "Before stressor", "After stress task");
    }
    
    private static void performTestBetweenTwoSurveys(FastResumeSurvey first, FastResumeSurvey second, 
            String stepFirst, String stepSecond) {
        
        double[] firstArray = new double[first.stressAnswers.size()], 
                secondArray = new double[second.stressAnswers.size()];
        
        StressNoStressData.copyMeanValueIntoDoubleArray(firstArray, first.stressAnswers);
        StressNoStressData.copyMeanValueIntoDoubleArray(secondArray, second.stressAnswers);
        
        System.out.println("TTest between " + stepFirst + " and " +  
                stepSecond + ": " + new TTest().pairedTTest(firstArray, secondArray));
    }
    
    
}
