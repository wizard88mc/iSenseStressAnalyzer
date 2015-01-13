package isensestressanalyzer.analyzer;

import isensestressanalyzer.ISenseStressAnalyzer;
import isensestressanalyzer.dataanalysis.StressNoStressData;
import isensestressanalyzer.exercise.Survey;
import isensestressanalyzer.tester.Tester;

import java.util.ArrayList;

import org.apache.commons.math3.stat.inference.TTest;

/**
 *
 * @author Matteo Ciman
 */
public class SurveyAnalyzer {
    
    private static class FastResumeSurvey {
        public ArrayList<Double> valenceAnswers = new ArrayList<>(),
            energyAnswers = new ArrayList<>(),
            stressAnswers = new ArrayList<>();
        
        /**
         * Adds an answer of a tester to the list of submitted answers
         * @param valence the reported valence
         * @param energy the reported energy
         * @param stress the reported stress
         */
        public void addValues(double valence, double energy, double stress) {
            valenceAnswers.add(valence); energyAnswers.add(energy);
            stressAnswers.add(stress);
        }
        
        /**
         * Calculates the mean value of the valence
         * @return valence mean value
         */
        public double getValenceMean() {
            double mean = 0;
            for (Double value: valenceAnswers) {
                mean += value;
            }
            return mean / valenceAnswers.size();
        }
        
        /**
         * Calculates the mean value of the energy
         * @return energy mean value
         */
        public double getEnergyMean() {
            double mean = 0;
            for (Double value: energyAnswers) {
                mean += value;
            }
            return mean / energyAnswers.size();
        }
        
        /**
         * Calculates the mean value of the stress
         * @return stress mean value
         */
        public double getStressMean() {
            double mean = 0;
            for (Double value: stressAnswers) {
                mean += value;
            }
            return mean / stressAnswers.size();
        }
        
        /**
         * Calculates the Standard Deviation of a set of values 
         * @param valence if we want the std of the valence values
         * @param energy if we want the std of the energy values
         * @param stress if we wan the std of the stress values
         * @return the std of the list of values
         */
        public double getStd(boolean valence, boolean energy, boolean stress) {
        	
        	double std = 0;
        	ArrayList<Double> toUse = null; double mean = 0;
        	if (valence) {
        		toUse = valenceAnswers; mean = getValenceMean();
        	}
        	else if (energy) {
        		toUse = energyAnswers; mean = getEnergyMean();
        	}
        	else if (stress) {
        		toUse = stressAnswers; mean = getStressMean();
        	}
        	
        	for (Double value: toUse) {
        		std += Math.pow(value - mean, 2);
        	}
        	
        	return Math.sqrt(std / toUse.size());
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
            
            System.out.println("Average valence: " + toAdd.getValenceMean() + " +- " + toAdd.getStd(true, false, false));
            System.out.println("Average energy: " + toAdd.getEnergyMean() + " +- " + toAdd.getStd(false, true, false));
            System.out.println("Average stress: " + toAdd.getStressMean() + " +- " + toAdd.getStd(false, false, true));
            
            resumes.add(toAdd);
        }
        
        performTestBetweenTwoSurveys(resumes.get(0), resumes.get(1), "Begin", "After relax");
        performTestBetweenTwoSurveys(resumes.get(2), resumes.get(3), "Before stressor", "After stressor");
        performTestBetweenTwoSurveys(resumes.get(2), resumes.get(4), "Before stressor", "After stress task");
        performTestBetweenTwoSurveys(resumes.get(3), resumes.get(4), "After Stressor", "After stress task");	
    }
    
    private static void performTestBetweenTwoSurveys(FastResumeSurvey first, FastResumeSurvey second, 
            String stepFirst, String stepSecond) {
        
        double[] firstArray = new double[first.stressAnswers.size()], 
                secondArray = new double[second.stressAnswers.size()];
        
        StressNoStressData.copyMeanValueIntoDoubleArray(firstArray, first.stressAnswers);
        StressNoStressData.copyMeanValueIntoDoubleArray(secondArray, second.stressAnswers);
        
        System.out.println("TTest between " + stepFirst + " and " +  
                stepSecond + ": " + new TTest().pairedTTest(firstArray, secondArray) / 2 +
                " t = " + new TTest().pairedT(firstArray, secondArray));
    }
    
    
}
