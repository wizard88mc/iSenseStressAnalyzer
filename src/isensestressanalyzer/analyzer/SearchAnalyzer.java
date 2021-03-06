package isensestressanalyzer.analyzer;

import isensestressanalyzer.dataanalysis.BasicDataStatistic;
import isensestressanalyzer.dataanalysis.StressNoStressData;
import isensestressanalyzer.exercise.Search;
import isensestressanalyzer.tester.Tester;

import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 */
public class SearchAnalyzer extends Analyzer {
    
    private static final String[] FEATURES_NAMES = new String[]{"Pressure data", 
        "Click size", "Click movement", "Average pressure vertical", 
        "Average pressure horizontal", "Average size vertical", "Average size horizontal", 
        "Scroll delta vertical", "Scroll delta horizontal", 
        "Scroll time length vertical", "Scroll time length horizontal", 
        "Scroll interaction length vertical", "Scroll interaction length horizontal", 
        "Speed scroll delta vertical", "Speed scroll delta horizontal", 
        "Speed scroll interaction vertical", "Speed scroll interaction horizontal", 
        "Mean distance from center vertical", "Mean distance from center horizontal", 
        "Mean distance from top left vertical", "Mean distance from top left horizontal", 
        "Linearity vertical", "Linearity horizontal", "Linearity sum every point vertical", 
        "Linearity sum every point horizontal"};
    
    private static final int[] FEATURES_PASSES_SINGLE_TEST = new int[]{0, 0, 0, 0, 0, 
    	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static int totalTesters = 0;
    private final ArrayList<SearchAnalysisResume> noStressResumes = new ArrayList<>();
    private final ArrayList<SearchAnalysisResume> stressResumes = new ArrayList<>(); 
    
    public void performAnalysis(Tester tester) {
        
        ArrayList<Search> exercises = tester.
            getSearchExercisesForProtocol(isensestressanalyzer.
                ISenseStressAnalyzer.PROTOCOLS[0]);

        if (!exercises.isEmpty()) {
            
            for (Search exercise: exercises) {
                
                SearchAnalysisResume resume = new SearchAnalysisResume();
                resume.pressureDataClick(new BasicDataStatistic(exercise.getAveragePressureBasicData().getAverage())); 
                resume.sizeDataClick(new BasicDataStatistic(exercise.getAverageSizeBasicData().getAverage()));
                resume.movementDataClick(new BasicDataStatistic(exercise.getAverageMovementClicksBasicData().getAverage()));
                resume.averagePressureScrollDataVertical(exercise.getAveragePressureScrollData(true));
                resume.averagePressureScrollDataHorizontal(exercise.getAveragePressureScrollData(false));
                resume.averageSizeScrollDataVertical(exercise.getAverageSizeScrollData(true));
                resume.averageSizeScrollDataHorizontal(exercise.getAverageSizeScrollData(false));
                resume.scrollDeltaDataVertical(exercise.getScrollDeltaDataVertical());
                resume.scrollDeltaDataHorizontal(exercise.getScrollDeltaDataHorizontal());
                resume.scrollTimeLengthDataVertical(exercise.getScrollTimeLengthData(false));
                resume.scrollTimeLengthDataHorizontal(exercise.getScrollTimeLengthData(true));
                resume.scrollInteractionLengthDataVertical(exercise.getScrollInteractionLengthData(false));
                resume.scrollInteractionLengthDataHorizontal(exercise.getScrollInteractionLengthData(true));
                resume.speedScrollDeltaDataVertical(exercise.getSpeedScrollDeltaData(false));
                resume.speedScrollDeltaDataHorizontal(exercise.getSpeedScrollDeltaData(true));
                resume.speedScrollInteractionDataVertical(exercise.getSpeedScrollInteractionData(false));
                resume.speedScrollInteractionDataHorizontal(exercise.getSpeedScrollInteractionData(true));
                resume.meanDistanceFromCenterDataVertical(exercise.getMeanDistanceFromCenterData(false, tester.getPhoneSettings()));
                resume.meanDistanceFromCenterDataHorizontal(exercise.getMeanDistanceFromCenterData(true, tester.getPhoneSettings()));
                resume.meanDistanceFromTopLeftDataVertical(exercise.getMeanDistanceFromTopLeftData(false));
                resume.meandDistanceFromTopLeftDataHorizontal(exercise.getMeanDistanceFromTopLeftData(true));
                resume.linearityDataVertical(exercise.getLinearityData(false, tester.getPhoneSettings()));
                resume.linearityDataHorizontal(exercise.getLinearityData(true, tester.getPhoneSettings()));
                resume.linearityAsSumEveryPointDataVertical(exercise.getLinearityAsSumEveryPointData(false, tester.getPhoneSettings()));
                resume.linearityAsSumEveryPointDataHorizontal(exercise.getLinearityAsSumEveryPointData(true, tester.getPhoneSettings()));
                
                if (!exercise.stress()) {
                    noStressResumes.add(resume);
                }
                else {
                    stressResumes.add(resume);
                }
            }
        }
    }
    
    private Double getMeanPressureData(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getPressureDataClick().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllPressureData(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getPressureDataClick().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanSizeData(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getSizeDataClick().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllSizeData(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getSizeDataClick().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanMovementData(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getMovementDataClick().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllMovementData(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getMovementDataClick().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanAveragePressureScrollDataVertical(boolean stress) {
        
    	double mean = 0;
    	ArrayList<SearchAnalysisResume> toUse = noStressResumes;
    	if (stress) {
    		toUse = stressResumes;
    	}
    	for (SearchAnalysisResume resume: toUse) {
    		mean += resume.getAveragePressureScrollDataVertical().getAverage();
    	}
    	return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllAveragePressureScrollDataVertical(boolean stress) {
        
    	ArrayList<SearchAnalysisResume> toUse = noStressResumes;
    	if (stress) {
    		toUse = stressResumes;
    	}
    	ArrayList<Double> valuesToReturn = new ArrayList<>();
    	for (SearchAnalysisResume resume: toUse) {
    		valuesToReturn.add(resume.getAveragePressureScrollDataVertical()
                        .getAverage());
    	}
    	return valuesToReturn;
    }
    
    private Double getMeanAveragePressureScrollDataHorizontal(boolean stress) {
    	double mean = 0;
    	ArrayList<SearchAnalysisResume> toUse = noStressResumes;
    	if (stress) {
    		toUse = stressResumes;
    	}
    	for (SearchAnalysisResume resume: toUse) {
    		mean += resume.getAveragePressureScrollDataHorizontal().getAverage();
    	}
    	return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllAveragePressureScrollDataHorizontal(boolean stress) {
        
    	ArrayList<SearchAnalysisResume> toUse = noStressResumes;
    	if (stress) {
    		toUse = stressResumes;
    	}
    	ArrayList<Double> valuesToReturn = new ArrayList<>();
    	for (SearchAnalysisResume resume: toUse) {
    		valuesToReturn.add(resume.getAveragePressureScrollDataHorizontal()
                        .getAverage());
    	}
    	return valuesToReturn;
    }
    
    private Double getMeanAverageSizeScrollDataVertical(boolean stress) {
        
    	double mean = 0;
    	ArrayList<SearchAnalysisResume> toUse = noStressResumes;
    	if (stress) {
    		toUse = stressResumes;
    	}
    	for (SearchAnalysisResume resume: toUse) {
    		mean += resume.getAverageSizeScrollDataVertical().getAverage();
    	}
    	return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllAverageSizeScrollDataVertical(boolean stress) {
        
    	ArrayList<SearchAnalysisResume> toUse = noStressResumes;
    	if (stress) {
    		toUse = stressResumes;
    	}
    	ArrayList<Double> valuesToReturn = new ArrayList<>();
    	for (SearchAnalysisResume resume: toUse) {
    		valuesToReturn.add(resume.getAverageSizeScrollDataVertical().getAverage());
    	}
    	return valuesToReturn;
    }
    
    private Double getMeanAverageSizeScrollDataHorizontal(boolean stress) {
        
    	double mean = 0;
    	ArrayList<SearchAnalysisResume> toUse = noStressResumes;
    	if (stress) {
    		toUse = stressResumes;
    	}
    	for (SearchAnalysisResume resume: toUse) {
    		mean += resume.getAverageSizeScrollDataHorizontal().getAverage();
    	}
    	return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllAverageSizeScrollDataHorizontal(boolean stress) {
        
    	ArrayList<SearchAnalysisResume> toUse = noStressResumes;
    	if (stress) {
    		toUse = stressResumes;
    	}
    	ArrayList<Double> valuesToReturn = new ArrayList<>();
    	for (SearchAnalysisResume resume: toUse) {
    		valuesToReturn.add(resume.getAverageSizeScrollDataHorizontal().getAverage());
    	}
    	return valuesToReturn;
    }
    
    private Double getMeanScrollDeltaDataVertical(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getScrollDeltaDataVertical().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllScrollDeltaDataVertical(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getScrollDeltaDataVertical().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanScrollDeltaDataHorizontal(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getScrollDeltaDataHorizontal().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllScrollDeltaDataHorizontal(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getScrollDeltaDataHorizontal().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanScrollTimeLengthDataVertical(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getScrollTimeLengthDataVertical().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllScrollTimeLengthDataVertical(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getScrollTimeLengthDataVertical().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanScrollTimeLengthDataHorizontal(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getScrollTimeLengthDataHorizontal().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllScrollTimeLengthDataHorizontal(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getScrollTimeLengthDataHorizontal().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanScrollInteractionLengthDataVertical(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getScrollInteractionLengthDataVertical().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllScrollInteractionLengthDataVertical(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getScrollInteractionLengthDataVertical().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanScrollInteractionLengthDataHorizontal(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getScrollInteractionLengthDataHorizontal().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllScrollInteractionLengthDataHorizontal(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getScrollInteractionLengthDataHorizontal().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanSpeedScrollDeltaDataVertical(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getSpeedScrollDeltaDataVertical().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllSpeedScrollDeltaDataVertical(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getSpeedScrollDeltaDataVertical().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanSpeedScrollDeltaDataHorizontal(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getSpeedScrollDeltaDataHorizontal().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllSpeedScrollDeltaDataHorizontal(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getSpeedScrollDeltaDataHorizontal().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanSpeedScrollInteractionDataVertical(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getSpeedScrollInteractionDataVertical().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllSpeedScrollInteractionDataVertical(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getSpeedScrollInteractionDataVertical().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanSpeedScrollInteractionDataHorizontal(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getSpeedScrollInteractionDataHorizontal().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllSpeedScrollInteractionDataHorizontal(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getSpeedScrollInteractionDataHorizontal().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanDistanceFromCenterDataVertical(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getMeanDistanceFromCenterDataVertical().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllMeanDistanceFromCenterDataVertical(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getMeanDistanceFromCenterDataVertical().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanDistanceFromCenterDataHorizontal(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getMeanDistanceFromCenterDataHorizontal().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllMeanDistanceFromCenterDataHorizontal(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getMeanDistanceFromCenterDataHorizontal().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanDistanceFromTopLeftDataVertical(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getMeanDistanceFromTopLeftDataVertical().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllMeanDistanceFromTopLeftDataVertical(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getMeanDistanceFromTopLeftDataVertical().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanDistanceFromTopLeftDataHorizontal(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getMeanDistanceFromTopLeftDataHorizontal().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllMeanDistanceFromTopLeftDataHorizontal(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getMeanDistanceFromTopLeftDataHorizontal().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanLinearityDataVertical(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getLinearityDataVertical().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllLinearityDataVertical(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getLinearityDataVertical().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanLinearityDataHorizontal(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getLinearityDataHorizontal().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllLinearityDataHorizontal(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getLinearityDataHorizontal().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanLinearityAsSumEveryPointDataVertical(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getLinearityAsSumEveryPointDataVertical().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllLinearityAsSumEveryPointDataVertical(boolean stress) {
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getLinearityAsSumEveryPointDataVertical()
                    .getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanLinearityAsSumEveryPointDataHorizontal(boolean stress) {
        
        double mean = 0;
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (SearchAnalysisResume resume: toUse) {
            mean += resume.getLinearityAsSumEveryPointDataHorizontal().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllLinearityAsSumEveryPointDataHorizontal(boolean stress) {
        
        ArrayList<SearchAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (SearchAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getLinearityAsSumEveryPointDataHorizontal().getAverage());
        }
        return valuesToReturn;
    }
    
    public static void performGlobalAnalysis(ArrayList<Tester> listTester) {
        
        ArrayList<ArrayList<Double>> pressureData = new ArrayList<>(), 
                sizeData = new ArrayList<>(), 
                movementData = new ArrayList<>(), 
                averagePressureScrollDataVertical = new ArrayList<>(), 
                averagePressureScrollDataHorizontal = new ArrayList<>(), 
                averageSizeScrollDataVertical = new ArrayList<>(), 
                averageSizeScrollDataHorizontal = new ArrayList<>(),
                scrollDeltaDataVertical = new ArrayList<>(), 
                scrollDeltaDataHorizontal = new ArrayList<>(), 
                scrollTimeLengthDataVertical = new ArrayList<>(), 
                scrollTimeLengthDataHorizontal = new ArrayList<>(), 
                scrollInteractionLengthDataVertical = new ArrayList<>(), 
                scrollInteractionLengthDataHorizontal = new ArrayList<>(), 
                speedScrollDeltaDataVertical = new ArrayList<>(), 
                speedScrollDeltaDataHorizontal = new ArrayList<>(), 
                speedScrollInteractionDataVertical = new ArrayList<>(), 
                speedScrollInteractionDataHorizontal = new ArrayList<>(), 
                meanDistanceFromCenterDataVertical = new ArrayList<>(), 
                meanDistanceFromCenterDataHorizontal = new ArrayList<>(), 
                meanDistanceFromTopLeftDataVertical = new ArrayList<>(), 
                meanDistanceFromTopLeftDataHorizontal = new ArrayList<>(), 
                linearityDataVertical = new ArrayList<>(), 
                linearityDataHorizontal = new ArrayList<>(), 
                linearityAsSumEveryPointDataVertical = new ArrayList<>(), 
                linearityAsSumEveryPointDataHorizontal = new ArrayList<>();
                
        for (Tester tester: listTester) {
            
            if (pressureData.isEmpty()) {
            
                pressureData.add(new ArrayList<Double>()); 
                pressureData.add(new ArrayList<Double>());
                
                sizeData.add(new ArrayList<Double>()); 
                sizeData.add(new ArrayList<Double>());
                
                movementData.add(new ArrayList<Double>()); 
                movementData.add(new ArrayList<Double>());
                
                averagePressureScrollDataVertical.add(new ArrayList<Double>()); 
                averagePressureScrollDataVertical.add(new ArrayList<Double>());
                
                averagePressureScrollDataHorizontal.add(new ArrayList<Double>()); 
                averagePressureScrollDataHorizontal.add(new ArrayList<Double>());
                
                averageSizeScrollDataVertical.add(new ArrayList<Double>()); 
                averageSizeScrollDataVertical.add(new ArrayList<Double>());
                
                averageSizeScrollDataHorizontal.add(new ArrayList<Double>()); 
                averageSizeScrollDataHorizontal.add(new ArrayList<Double>());
                
                scrollDeltaDataVertical.add(new ArrayList<Double>()); 
                scrollDeltaDataVertical.add(new ArrayList<Double>());
                
                scrollDeltaDataHorizontal.add(new ArrayList<Double>()); 
                scrollDeltaDataHorizontal.add(new ArrayList<Double>());
                
                scrollTimeLengthDataVertical.add(new ArrayList<Double>()); 
                scrollTimeLengthDataVertical.add(new ArrayList<Double>());
                
                scrollTimeLengthDataHorizontal.add(new ArrayList<Double>()); 
                scrollTimeLengthDataHorizontal.add(new ArrayList<Double>());
                
                scrollInteractionLengthDataVertical.add(new ArrayList<Double>()); 
                scrollInteractionLengthDataVertical.add(new ArrayList<Double>());
                
                scrollInteractionLengthDataHorizontal.add(new ArrayList<Double>()); 
                scrollInteractionLengthDataHorizontal.add(new ArrayList<Double>());
                
                speedScrollDeltaDataVertical.add(new ArrayList<Double>()); 
                speedScrollDeltaDataVertical.add(new ArrayList<Double>());
                
                speedScrollDeltaDataHorizontal.add(new ArrayList<Double>()); 
                speedScrollDeltaDataHorizontal.add(new ArrayList<Double>());
                
                speedScrollInteractionDataVertical.add(new ArrayList<Double>()); 
                speedScrollInteractionDataVertical.add(new ArrayList<Double>());
                
                speedScrollInteractionDataHorizontal.add(new ArrayList<Double>()); 
                speedScrollInteractionDataHorizontal.add(new ArrayList<Double>());
                
                meanDistanceFromCenterDataVertical.add(new ArrayList<Double>()); 
                meanDistanceFromCenterDataVertical.add(new ArrayList<Double>());
                
                meanDistanceFromCenterDataHorizontal.add(new ArrayList<Double>()); 
                meanDistanceFromCenterDataHorizontal.add(new ArrayList<Double>());
                
                meanDistanceFromTopLeftDataVertical.add(new ArrayList<Double>()); 
                meanDistanceFromTopLeftDataVertical.add(new ArrayList<Double>());
                
                meanDistanceFromTopLeftDataHorizontal.add(new ArrayList<Double>()); 
                meanDistanceFromTopLeftDataHorizontal.add(new ArrayList<Double>());
                
                linearityDataVertical.add(new ArrayList<Double>()); 
                linearityDataVertical.add(new ArrayList<Double>());
                
                linearityDataHorizontal.add(new ArrayList<Double>()); 
                linearityDataHorizontal.add(new ArrayList<Double>());
                
                linearityAsSumEveryPointDataVertical.add(new ArrayList<Double>()); 
                linearityAsSumEveryPointDataVertical.add(new ArrayList<Double>());
                
                linearityAsSumEveryPointDataHorizontal.add(new ArrayList<Double>()); 
                linearityAsSumEveryPointDataHorizontal.add(new ArrayList<Double>());
            }
            
            SearchAnalyzer mSearchAnalyzer = tester.getSearchAnalyzer();
            
            pressureData.get(0).add(mSearchAnalyzer.getMeanPressureData(false));
            pressureData.get(1).add(mSearchAnalyzer.getMeanPressureData(true));
            
            sizeData.get(0).add(mSearchAnalyzer.getMeanSizeData(false)); 
            sizeData.get(1).add(mSearchAnalyzer.getMeanSizeData(true));
            
            movementData.get(0).add(mSearchAnalyzer.getMeanMovementData(false)); 
            movementData.get(1).add(mSearchAnalyzer.getMeanMovementData(true));
            
            averagePressureScrollDataVertical.get(0).add(mSearchAnalyzer.getMeanAveragePressureScrollDataVertical(false)); 
            averagePressureScrollDataVertical.get(1).add(mSearchAnalyzer.getMeanAveragePressureScrollDataVertical(true));
            
            averagePressureScrollDataHorizontal.get(0).add(mSearchAnalyzer.getMeanAveragePressureScrollDataHorizontal(false));
            averagePressureScrollDataHorizontal.get(1).add(mSearchAnalyzer.getMeanAveragePressureScrollDataHorizontal(true));
            
            averageSizeScrollDataVertical.get(0).add(mSearchAnalyzer.getMeanAverageSizeScrollDataVertical(false));
            averageSizeScrollDataVertical.get(1).add(mSearchAnalyzer.getMeanAverageSizeScrollDataVertical(true));
            
            averageSizeScrollDataHorizontal.get(0).add(mSearchAnalyzer.getMeanAverageSizeScrollDataHorizontal(false));
            averageSizeScrollDataHorizontal.get(1).add(mSearchAnalyzer.getMeanAverageSizeScrollDataHorizontal(true));
            
            scrollDeltaDataVertical.get(0).add(mSearchAnalyzer.getMeanScrollDeltaDataVertical(false)); 
            scrollDeltaDataVertical.get(1).add(mSearchAnalyzer.getMeanScrollDeltaDataVertical(true));
            
            scrollDeltaDataHorizontal.get(0).add(mSearchAnalyzer.getMeanScrollDeltaDataHorizontal(false)); 
            scrollDeltaDataHorizontal.get(1).add(mSearchAnalyzer.getMeanScrollDeltaDataHorizontal(true));
            
            scrollTimeLengthDataVertical.get(0).add(mSearchAnalyzer.getMeanScrollTimeLengthDataVertical(false)); 
            scrollTimeLengthDataVertical.get(1).add(mSearchAnalyzer.getMeanScrollTimeLengthDataVertical(true));
            
            scrollTimeLengthDataHorizontal.get(0).add(mSearchAnalyzer.getMeanScrollTimeLengthDataHorizontal(false)); 
            scrollTimeLengthDataHorizontal.get(1).add(mSearchAnalyzer.getMeanScrollTimeLengthDataHorizontal(true));
            
            scrollInteractionLengthDataVertical.get(0).add(mSearchAnalyzer.getMeanScrollInteractionLengthDataVertical(false)); 
            scrollInteractionLengthDataVertical.get(1).add(mSearchAnalyzer.getMeanScrollInteractionLengthDataVertical(true));
            
            scrollInteractionLengthDataHorizontal.get(0).add(mSearchAnalyzer.getMeanScrollInteractionLengthDataHorizontal(false)); 
            scrollInteractionLengthDataHorizontal.get(1).add(mSearchAnalyzer.getMeanScrollInteractionLengthDataHorizontal(true));
            
            speedScrollDeltaDataVertical.get(0).add(mSearchAnalyzer.getMeanSpeedScrollDeltaDataVertical(false)); 
            speedScrollDeltaDataVertical.get(1).add(mSearchAnalyzer.getMeanSpeedScrollDeltaDataVertical(true));
            
            speedScrollDeltaDataHorizontal.get(0).add(mSearchAnalyzer.getMeanSpeedScrollDeltaDataHorizontal(false)); 
            speedScrollDeltaDataHorizontal.get(1).add(mSearchAnalyzer.getMeanSpeedScrollDeltaDataHorizontal(true));
            
            speedScrollInteractionDataVertical.get(0).add(mSearchAnalyzer.getMeanSpeedScrollInteractionDataVertical(false)); 
            speedScrollInteractionDataVertical.get(1).add(mSearchAnalyzer.getMeanSpeedScrollInteractionDataVertical(true));
            
            speedScrollInteractionDataHorizontal.get(0).add(mSearchAnalyzer.getMeanSpeedScrollInteractionDataHorizontal(false)); 
            speedScrollInteractionDataHorizontal.get(1).add(mSearchAnalyzer.getMeanSpeedScrollInteractionDataHorizontal(true));
            
            meanDistanceFromCenterDataVertical.get(0).add(mSearchAnalyzer.getMeanDistanceFromCenterDataVertical(false)); 
            meanDistanceFromCenterDataVertical.get(1).add(mSearchAnalyzer.getMeanDistanceFromCenterDataVertical(false));
            
            meanDistanceFromCenterDataHorizontal.get(0).add(mSearchAnalyzer.getMeanDistanceFromCenterDataHorizontal(false));
            meanDistanceFromCenterDataHorizontal.get(1).add(mSearchAnalyzer.getMeanDistanceFromCenterDataHorizontal(true));
            
            meanDistanceFromTopLeftDataVertical.get(0).add(mSearchAnalyzer.getMeanDistanceFromTopLeftDataVertical(false)); 
            meanDistanceFromTopLeftDataVertical.get(1).add(mSearchAnalyzer.getMeanDistanceFromTopLeftDataVertical(true));
            
            meanDistanceFromTopLeftDataHorizontal.get(0).add(mSearchAnalyzer.getMeanDistanceFromTopLeftDataHorizontal(false)); 
            meanDistanceFromTopLeftDataHorizontal.get(1).add(mSearchAnalyzer.getMeanDistanceFromTopLeftDataHorizontal(true));
            
            linearityDataVertical.get(0).add(mSearchAnalyzer.getMeanLinearityDataVertical(false)); 
            linearityDataVertical.get(1).add(mSearchAnalyzer.getMeanLinearityDataVertical(true));
            
            linearityDataHorizontal.get(0).add(mSearchAnalyzer.getMeanLinearityDataHorizontal(false)); 
            linearityDataHorizontal.get(1).add(mSearchAnalyzer.getMeanLinearityDataHorizontal(true));
            
            linearityAsSumEveryPointDataVertical.get(0).add(mSearchAnalyzer.getMeanLinearityAsSumEveryPointDataVertical(false)); 
            linearityAsSumEveryPointDataVertical.get(1).add(mSearchAnalyzer.getMeanLinearityAsSumEveryPointDataVertical(true));
            
            linearityAsSumEveryPointDataHorizontal.get(0).add(mSearchAnalyzer.getMeanLinearityAsSumEveryPointDataHorizontal(false)); 
            linearityAsSumEveryPointDataHorizontal.get(1).add(mSearchAnalyzer.getMeanLinearityAsSumEveryPointDataHorizontal(true));
        }
        
        printReport(false, new StressNoStressData(FEATURES_NAMES[0], pressureData.get(0), pressureData.get(1)), 
            new StressNoStressData(FEATURES_NAMES[1], sizeData.get(0), sizeData.get(1)), 
            new StressNoStressData(FEATURES_NAMES[2], movementData.get(0), movementData.get(1)),
            new StressNoStressData(FEATURES_NAMES[3], scrollDeltaDataVertical.get(0), scrollDeltaDataVertical.get(1)),
            new StressNoStressData(FEATURES_NAMES[4], scrollDeltaDataHorizontal.get(0), scrollDeltaDataHorizontal.get(1)),
            new StressNoStressData(FEATURES_NAMES[5], scrollTimeLengthDataVertical.get(0), scrollTimeLengthDataVertical.get(1)),
            new StressNoStressData(FEATURES_NAMES[6], scrollTimeLengthDataHorizontal.get(0), scrollTimeLengthDataHorizontal.get(1)),
            new StressNoStressData(FEATURES_NAMES[7], scrollInteractionLengthDataVertical.get(0), scrollInteractionLengthDataVertical.get(1)),
            new StressNoStressData(FEATURES_NAMES[8], scrollInteractionLengthDataHorizontal.get(0), scrollInteractionLengthDataHorizontal.get(1)),
            new StressNoStressData(FEATURES_NAMES[9], speedScrollDeltaDataVertical.get(0), speedScrollDeltaDataVertical.get(1)),
            new StressNoStressData(FEATURES_NAMES[10], speedScrollDeltaDataHorizontal.get(0), speedScrollDeltaDataHorizontal.get(1)),
            new StressNoStressData(FEATURES_NAMES[11], speedScrollInteractionDataVertical.get(0), speedScrollInteractionDataVertical.get(1)),
            new StressNoStressData(FEATURES_NAMES[12], speedScrollInteractionDataHorizontal.get(0), speedScrollInteractionDataHorizontal.get(1)),
            new StressNoStressData(FEATURES_NAMES[13], meanDistanceFromCenterDataVertical.get(0), meanDistanceFromCenterDataVertical.get(1)),
            new StressNoStressData(FEATURES_NAMES[14], meanDistanceFromCenterDataHorizontal.get(0), meanDistanceFromCenterDataHorizontal.get(1)),
            new StressNoStressData(FEATURES_NAMES[15], meanDistanceFromTopLeftDataVertical.get(0), meanDistanceFromTopLeftDataVertical.get(1)),
            new StressNoStressData(FEATURES_NAMES[16], meanDistanceFromTopLeftDataHorizontal.get(0), meanDistanceFromTopLeftDataHorizontal.get(1)),
            new StressNoStressData(FEATURES_NAMES[17], linearityDataVertical.get(0), linearityDataVertical.get(1)),
            new StressNoStressData(FEATURES_NAMES[18], linearityDataHorizontal.get(0), linearityDataHorizontal.get(1)),
            new StressNoStressData(FEATURES_NAMES[19], linearityAsSumEveryPointDataVertical.get(0), linearityAsSumEveryPointDataVertical.get(1)),
            new StressNoStressData(FEATURES_NAMES[20], linearityAsSumEveryPointDataHorizontal.get(0), linearityAsSumEveryPointDataHorizontal.get(1)));
    }
    
    public static void performLocalAnalysis(Tester tester) {
        
        System.out.println("*********** Tester: " + tester.getName() + " ***********");
        
        SearchAnalyzer searchAnalyzer = tester.getSearchAnalyzer();
        
        boolean[] results = printReport(true, new StressNoStressData(FEATURES_NAMES[0], searchAnalyzer.getAllPressureData(false), searchAnalyzer.getAllPressureData(true)), 
                new StressNoStressData(FEATURES_NAMES[1], searchAnalyzer.getAllSizeData(false), searchAnalyzer.getAllSizeData(true)), 
                new StressNoStressData(FEATURES_NAMES[2], searchAnalyzer.getAllMovementData(false), searchAnalyzer.getAllMovementData(true)), 
                new StressNoStressData(FEATURES_NAMES[3], searchAnalyzer.getAllAveragePressureScrollDataVertical(false), searchAnalyzer.getAllAveragePressureScrollDataVertical(true)),
                new StressNoStressData(FEATURES_NAMES[4], searchAnalyzer.getAllAveragePressureScrollDataHorizontal(false), searchAnalyzer.getAllAveragePressureScrollDataHorizontal(true)),
                new StressNoStressData(FEATURES_NAMES[5], searchAnalyzer.getAllAverageSizeScrollDataVertical(false), searchAnalyzer.getAllAverageSizeScrollDataVertical(true)),
                new StressNoStressData(FEATURES_NAMES[6], searchAnalyzer.getAllAverageSizeScrollDataHorizontal(false), searchAnalyzer.getAllAverageSizeScrollDataHorizontal(true)),
                new StressNoStressData(FEATURES_NAMES[7], searchAnalyzer.getAllScrollDeltaDataVertical(false), searchAnalyzer.getAllScrollDeltaDataVertical(true)), 
                new StressNoStressData(FEATURES_NAMES[8], searchAnalyzer.getAllScrollDeltaDataHorizontal(false), searchAnalyzer.getAllScrollDeltaDataHorizontal(true)), 
                new StressNoStressData(FEATURES_NAMES[9], searchAnalyzer.getAllScrollTimeLengthDataVertical(false), searchAnalyzer.getAllScrollTimeLengthDataVertical(true)), 
                new StressNoStressData(FEATURES_NAMES[10], searchAnalyzer.getAllScrollTimeLengthDataHorizontal(false), searchAnalyzer.getAllScrollTimeLengthDataHorizontal(true)), 
                new StressNoStressData(FEATURES_NAMES[11], searchAnalyzer.getAllScrollInteractionLengthDataVertical(false), searchAnalyzer.getAllScrollInteractionLengthDataVertical(true)), 
                new StressNoStressData(FEATURES_NAMES[12], searchAnalyzer.getAllScrollInteractionLengthDataHorizontal(false), searchAnalyzer.getAllScrollInteractionLengthDataHorizontal(true)), 
                new StressNoStressData(FEATURES_NAMES[13], searchAnalyzer.getAllSpeedScrollDeltaDataVertical(false), searchAnalyzer.getAllSpeedScrollDeltaDataVertical(true)), 
                new StressNoStressData(FEATURES_NAMES[14], searchAnalyzer.getAllSpeedScrollDeltaDataHorizontal(false), searchAnalyzer.getAllSpeedScrollDeltaDataHorizontal(true)), 
                new StressNoStressData(FEATURES_NAMES[15], searchAnalyzer.getAllSpeedScrollInteractionDataVertical(false), searchAnalyzer.getAllSpeedScrollInteractionDataVertical(true)), 
                new StressNoStressData(FEATURES_NAMES[16], searchAnalyzer.getAllSpeedScrollInteractionDataHorizontal(false), searchAnalyzer.getAllSpeedScrollInteractionDataHorizontal(true)), 
                new StressNoStressData(FEATURES_NAMES[17], searchAnalyzer.getAllMeanDistanceFromCenterDataVertical(false), searchAnalyzer.getAllMeanDistanceFromCenterDataVertical(true)), 
                new StressNoStressData(FEATURES_NAMES[18], searchAnalyzer.getAllMeanDistanceFromCenterDataHorizontal(false), searchAnalyzer.getAllMeanDistanceFromCenterDataHorizontal(true)), 
                new StressNoStressData(FEATURES_NAMES[19], searchAnalyzer.getAllMeanDistanceFromTopLeftDataVertical(false), searchAnalyzer.getAllMeanDistanceFromTopLeftDataVertical(true)), 
                new StressNoStressData(FEATURES_NAMES[20], searchAnalyzer.getAllMeanDistanceFromTopLeftDataHorizontal(false), searchAnalyzer.getAllMeanDistanceFromTopLeftDataHorizontal(true)), 
                new StressNoStressData(FEATURES_NAMES[21], searchAnalyzer.getAllLinearityDataVertical(false), searchAnalyzer.getAllLinearityDataVertical(true)), 
                new StressNoStressData(FEATURES_NAMES[22], searchAnalyzer.getAllLinearityDataHorizontal(false), searchAnalyzer.getAllLinearityDataHorizontal(true)), 
                new StressNoStressData(FEATURES_NAMES[23], searchAnalyzer.getAllLinearityAsSumEveryPointDataVertical(false), searchAnalyzer.getAllLinearityAsSumEveryPointDataVertical(true)), 
                new StressNoStressData(FEATURES_NAMES[24], searchAnalyzer.getAllLinearityAsSumEveryPointDataHorizontal(false), searchAnalyzer.getAllLinearityAsSumEveryPointDataHorizontal(true)));
        
        for (int i = 0; i < results.length; i++) {
            if (results[i]) {
                FEATURES_PASSES_SINGLE_TEST[i]++;
            }
        }
        totalTesters++;
    }
    
    public static void printPercentageSingleFeature() {
    	
    	for (int i = 0; i < FEATURES_NAMES.length; i++) {
            System.out.println(FEATURES_NAMES[i] + ": " + 
                FEATURES_PASSES_SINGLE_TEST[i] + "/" + totalTesters + "(" + 
                FEATURES_PASSES_SINGLE_TEST[i] * 100 / totalTesters + ")");
    	}
    }
}
