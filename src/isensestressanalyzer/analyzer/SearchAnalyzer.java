package isensestressanalyzer.analyzer;

import isensestressanalyzer.dataanalysis.BasicDataStatistic;
import isensestressanalyzer.exercise.Search;
import java.util.ArrayList;
import tester.Tester;

/**
 *
 * @author Matteo Ciman
 */
public class SearchAnalyzer extends Analyzer
{   
    public void performAnalysis(Tester tester)
    {   
        ArrayList<Search> exercises = tester.getSearchExercisesForProtocol(isensestressanalyzer.ISenseStressAnalyzer.protocols[0]);

        if (!exercises.isEmpty())
        {
            BasicDataStatistic[] 
                    pressureData = new BasicDataStatistic[]{null, null},
                    sizeData = new BasicDataStatistic[]{null, null},
                    movementData = new BasicDataStatistic[]{null, null},
                    scrollDeltaDataVertical = new BasicDataStatistic[]{null, null},
                    scrollTimeLengthDataVertical = new BasicDataStatistic[]{null, null},
                    scrollInteractionLengthDataVertical = new BasicDataStatistic[]{null, null},
                    speedScrollDeltaDataVertical = new BasicDataStatistic[]{null, null},
                    speedScrollInteractionDataVertical = new BasicDataStatistic[]{null, null},
                    meanDistanceFromCenterDataVertical = new BasicDataStatistic[]{null, null},
                    meanDistanceFromTopLeftDataVertical = new BasicDataStatistic[]{null, null},
                    linearityDataVertical = new BasicDataStatistic[]{null, null},
                    linearityAsSumEveryPointDataVertical = new BasicDataStatistic[]{null, null},
                    scrollDeltaDataHorizontal = new BasicDataStatistic[]{null, null},
                    scrollTimeLengthDataHorizontal = new BasicDataStatistic[]{null, null},
                    scrollInteractionLengthDataHorizontal = new BasicDataStatistic[]{null, null},
                    speedScrollDeltaDataHorizontal = new BasicDataStatistic[]{null, null},
                    speedScrollInteractionDataHorizontal = new BasicDataStatistic[]{null, null},
                    meanDistanceFromCenterDataHorizontal = new BasicDataStatistic[]{null, null},
                    meanDistanceFromTopLeftDataHorizontal = new BasicDataStatistic[]{null, null},
                    linearityDataHorizontal = new BasicDataStatistic[]{null, null},
                    linearityAsSumEveryPointDataHorizontal = new BasicDataStatistic[]{null, null};

            for (Search exercise: exercises)
            {
                if (!exercise.stress())
                {
                    pressureData[0] = new BasicDataStatistic(exercise.getAveragePressureBasicData().getAverage());
                    sizeData[0] = new BasicDataStatistic(exercise.getAverageSizeBasicData().getAverage());
                    movementData[0] = new BasicDataStatistic(exercise.getAverageMovementClicksBasicData().getAverage());
                    scrollDeltaDataVertical[0] = exercise.getScrollDeltaDataVertical();
                    scrollDeltaDataHorizontal[0] = exercise.getScrollDeltaDataHorizontal();
                    scrollTimeLengthDataVertical[0] = exercise.getScrollTimeLengthData(false);
                    scrollTimeLengthDataHorizontal[0] = exercise.getScrollTimeLengthData(true);
                    scrollInteractionLengthDataVertical[0] = exercise.getScrollInteractionLengthData(false);
                    scrollInteractionLengthDataHorizontal[0] = exercise.getScrollInteractionLengthData(true);
                    speedScrollDeltaDataVertical[0] = exercise.getSpeedScrollDeltaData(false);
                    speedScrollDeltaDataHorizontal[0] = exercise.getSpeedScrollDeltaData(true);
                    speedScrollInteractionDataVertical[0] = exercise.getSpeedScrollInteractionData(false);
                    speedScrollInteractionDataHorizontal[0] = exercise.getSpeedScrollInteractionData(true);
                    meanDistanceFromCenterDataVertical[0] = exercise.getMeanDistanceFromCenterData(false, tester.getPhoneSettings());
                    meanDistanceFromCenterDataHorizontal[0] = exercise.getMeanDistanceFromCenterData(true, tester.getPhoneSettings());
                    meanDistanceFromTopLeftDataVertical[0] = exercise.getMeanDistanceFromTopLeftData(false);
                    meanDistanceFromTopLeftDataHorizontal[0] = exercise.getMeanDistanceFromTopLeftData(true);
                    linearityDataVertical[0] = exercise.getLinearityData(false, tester.getPhoneSettings());
                    linearityDataHorizontal[0] = exercise.getLinearityData(true, tester.getPhoneSettings());
                    linearityAsSumEveryPointDataVertical[0] = exercise.getLinearityAsSumEveryPointData(false, tester.getPhoneSettings());
                    linearityAsSumEveryPointDataHorizontal[0] = exercise.getLinearityAsSumEveryPointData(true, tester.getPhoneSettings());
                }
                else
                {
                    pressureData[1] = new BasicDataStatistic(exercise.getAveragePressureBasicData().getAverage());
                    sizeData[1] = new BasicDataStatistic(exercise.getAverageSizeBasicData().getAverage());
                    movementData[1] = new BasicDataStatistic(exercise.getAverageMovementClicksBasicData().getAverage());
                    scrollDeltaDataVertical[1] = exercise.getScrollDeltaDataVertical();
                    scrollDeltaDataHorizontal[1] = exercise.getScrollDeltaDataHorizontal();
                    scrollTimeLengthDataVertical[1] = exercise.getScrollTimeLengthData(false);
                    scrollTimeLengthDataHorizontal[1] = exercise.getScrollTimeLengthData(true);
                    scrollInteractionLengthDataVertical[1] = exercise.getScrollInteractionLengthData(false);
                    scrollInteractionLengthDataHorizontal[1] = exercise.getScrollInteractionLengthData(true);
                    speedScrollDeltaDataVertical[1] = exercise.getSpeedScrollDeltaData(false);
                    speedScrollDeltaDataHorizontal[1] = exercise.getSpeedScrollDeltaData(true);
                    speedScrollInteractionDataVertical[1] = exercise.getSpeedScrollInteractionData(false);
                    speedScrollInteractionDataHorizontal[1] = exercise.getSpeedScrollInteractionData(true);
                    meanDistanceFromCenterDataVertical[1] = exercise.getMeanDistanceFromCenterData(false, tester.getPhoneSettings());
                    meanDistanceFromCenterDataHorizontal[1] = exercise.getMeanDistanceFromCenterData(true, tester.getPhoneSettings());
                    meanDistanceFromTopLeftDataVertical[1] = exercise.getMeanDistanceFromTopLeftData(false);
                    meanDistanceFromTopLeftDataHorizontal[1] = exercise.getMeanDistanceFromTopLeftData(true);
                    linearityDataVertical[1] = exercise.getLinearityData(false, tester.getPhoneSettings());
                    linearityDataHorizontal[1] = exercise.getLinearityData(true, tester.getPhoneSettings());
                    linearityAsSumEveryPointDataVertical[1] = exercise.getLinearityAsSumEveryPointData(false, tester.getPhoneSettings());
                    linearityAsSumEveryPointDataHorizontal[1] = exercise.getLinearityAsSumEveryPointData(true, tester.getPhoneSettings());
                }
            }

            if (pressureData[0] != null)
            {
                SearchAnalysisResume resume = new SearchAnalysisResume();
                resume.pressureDataClick(pressureData[0], pressureData[1]); 
                resume.sizeDataClick(sizeData[0], sizeData[1]);
                resume.movementDataClick(movementData[0], movementData[1]);
                resume.scrollDeltaDataVertical(scrollDeltaDataVertical[0], scrollDeltaDataVertical[1]);
                resume.scrollDeltaDataHorizontal(scrollDeltaDataHorizontal[0], scrollDeltaDataHorizontal[1]);
                resume.scrollTimeLengthDataVertical(scrollTimeLengthDataVertical[0], scrollTimeLengthDataVertical[1]);
                resume.scrollTimeLengthDataHorizontal(scrollTimeLengthDataHorizontal[0], scrollTimeLengthDataHorizontal[1]);
                resume.scrollInteractionLengthDataVertical(scrollInteractionLengthDataVertical[0], scrollInteractionLengthDataVertical[1]);
                resume.scrollInteractionLengthDataHorizontal(scrollInteractionLengthDataHorizontal[0], scrollInteractionLengthDataHorizontal[1]);
                resume.speedScrollDeltaDataVertical(speedScrollDeltaDataVertical[0], speedScrollDeltaDataVertical[1]);
                resume.speedScrollDeltaDataHorizontal(speedScrollDeltaDataHorizontal[0], speedScrollDeltaDataHorizontal[1]);
                resume.speedScrollInteractionDataVertical(speedScrollInteractionDataVertical[0], speedScrollInteractionDataVertical[1]);
                resume.speedScrollInteractionDataHorizontal(speedScrollInteractionDataHorizontal[0], speedScrollInteractionDataHorizontal[1]);
                resume.meanDistanceFromCenterDataVertical(meanDistanceFromCenterDataVertical[0], meanDistanceFromCenterDataVertical[1]);
                resume.meanDistanceFromCenterDataHorizontal(meanDistanceFromCenterDataHorizontal[0], meanDistanceFromCenterDataHorizontal[1]);
                resume.meanDistanceFromTopLeftDataVertical(meanDistanceFromTopLeftDataVertical[0], meanDistanceFromTopLeftDataVertical[1]);
                resume.meandDistanceFromTopLeftDataHorizontal(meanDistanceFromTopLeftDataHorizontal[0], meanDistanceFromTopLeftDataHorizontal[1]);
                resume.linearityDataVertical(linearityDataVertical[0], linearityDataVertical[1]);
                resume.linearityDataHorizontal(linearityDataHorizontal[0], linearityDataHorizontal[1]);
                resume.linearityAsSumEveryPointDataVertical(linearityAsSumEveryPointDataVertical[0], linearityAsSumEveryPointDataVertical[1]);
                resume.linearityAsSumEveryPointDataHorizontal(linearityAsSumEveryPointDataHorizontal[0], linearityAsSumEveryPointDataHorizontal[1]);

                tester.addNewSearchAnalysisResume(resume);
                System.out.println("** RESUME SEARCH **");
            }
        }
    }
}
