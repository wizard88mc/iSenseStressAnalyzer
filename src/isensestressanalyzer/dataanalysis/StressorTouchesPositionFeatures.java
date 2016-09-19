package isensestressanalyzer.dataanalysis;

import isensestressanalyzer.ISenseStressAnalyzer;
import isensestressanalyzer.exercise.Stressor;
import isensestressanalyzer.interaction.Interaction;
import isensestressanalyzer.interaction.NumberPickerInteraction;
import isensestressanalyzer.outputwriter.StressorDistanceWriter;
import isensestressanalyzer.outputwriter.TTestStressorTaskFeaturesOutputWriter;
import isensestressanalyzer.tester.Tester;
import isensestressanalyzer.utils.MathUtils;
import isensestressanalyzer.utils.Point;
import isensestressanalyzer.utils.ScreenObject;
import java.util.ArrayList;
import org.apache.commons.math3.stat.inference.TTest;

/**
 *
 * @author Matteo Ciman
 */
public class StressorTouchesPositionFeatures {
    
    private class BeginMiddleEndValues {
        public ArrayList<Double> begin = new ArrayList<>(), 
                middle = new ArrayList<>(), 
                end = new ArrayList<>();
    }
    
    private class FeaturesValuesForTester {
        public BeginMiddleEndValues averageDistanceFromCenter = new BeginMiddleEndValues(),
            averageLength = new BeginMiddleEndValues(), 
            averageRatioOverBottom = new BeginMiddleEndValues(),
            averageAreaRectangle = new BeginMiddleEndValues(),
            averageLinearity = new BeginMiddleEndValues(), 
            averageSize = new BeginMiddleEndValues(), 
            averagePressure = new BeginMiddleEndValues(), 
            averageTimeDuration = new BeginMiddleEndValues();
    }
    
    private ArrayList<Double> averageDistanceFromCenter = new ArrayList<>(), 
        averageLength = new ArrayList<>(), 
        averageRatioOverBottom = new ArrayList<>(), 
        averageAreaRectangle = new ArrayList<>(), 
        averageLinearity = new ArrayList<>(), 
        averageSize = new ArrayList<>(), 
        averagePressure = new ArrayList<>(), 
        averageTimeDuration = new ArrayList<>();
    
    private final ArrayList<FeaturesValuesForTester> 
            testersFeaturesValuesFirstSetStressorTasks = new ArrayList<>(),
            testersFeaturesValuesSecondSetStressorTasks = new ArrayList<>(), 
            testersFeaturesValuesThirdSetStressorTasks = new ArrayList<>();
    
    private StressorDistanceWriter outputWriter;
            
    public void workWithNumberPickers(ArrayList<Tester> listTesters) {
            
        /**
         * Different grouping methods correspond to different way of grouping
         * together the four NumberPickers
         */
        workWithFirstGrouping(listTesters);

        workWithSecondGrouping(listTesters);

        workWithThirdGrouping(listTesters);

        workWithFourthGrouping(listTesters);
    }
    
    /**
     * First grouping: each NumberPicker is alone and makes a group
     * @param listTesters the list of testers
     */
    private void workWithFirstGrouping(ArrayList<Tester> listTesters) {
        
        System.out.println("Working with first Stressor Grouping");
        
        outputWriter = new StressorDistanceWriter(0);
        
        testersFeaturesValuesFirstSetStressorTasks.clear();
        testersFeaturesValuesSecondSetStressorTasks.clear();
        testersFeaturesValuesThirdSetStressorTasks.clear();
        
        int counter = 0;
        for (Tester tester: listTesters) {
            
            System.out.println("Tester " + (counter + 1) +  "/" + 
                listTesters.size());
            
            outputWriter.writeIMEI(tester.getName());
            
            ArrayList<Stressor> listStressors = tester.
                getStressorExercisesForProtocol(ISenseStressAnalyzer.PROTOCOLS[0]);
            
            ArrayList<ScreenObject> numberPickers = listStressors.get(0)
                .getNumberPickers();
        
            ArrayList<ArrayList<ScreenObject>> firstGrouping = new ArrayList<>();
            firstGrouping.add(new ArrayList<ScreenObject>());
            firstGrouping.add(new ArrayList<ScreenObject>());
            firstGrouping.add(new ArrayList<ScreenObject>());
            firstGrouping.add(new ArrayList<ScreenObject>());

            firstGrouping.get(0).add(numberPickers.get(0));
            firstGrouping.get(1).add(numberPickers.get(1));
            firstGrouping.get(2).add(numberPickers.get(2));
            firstGrouping.get(3).add(numberPickers.get(3));

            testersFeaturesValuesFirstSetStressorTasks.
                add(firstMiddleLastStressor(firstGrouping, listStressors));
            testersFeaturesValuesSecondSetStressorTasks.
                add(aroundFirstMiddleLastStressor(firstGrouping, listStressors));
            testersFeaturesValuesThirdSetStressorTasks.
                add(threeGroupOfStressorTasks(firstGrouping, listStressors));
            
            counter++;
            
            outputWriter.endParticipant();
        }
        
        outputWriter.close();
        
        workWithFeaturesToPrepareForTTest(0);
    }
    
    /**
     * Second grouping: First and second + Third and Fourth
     * @param listTesters: list of testers
     */
    private void workWithSecondGrouping(ArrayList<Tester> listTesters) {
        
        System.out.println("Working with second Stressor Task Grouping");
        
        outputWriter = new StressorDistanceWriter(1);
        
        testersFeaturesValuesFirstSetStressorTasks.clear();
        testersFeaturesValuesSecondSetStressorTasks.clear();
        testersFeaturesValuesThirdSetStressorTasks.clear();
        
        int counter = 0;
        for (Tester tester: listTesters) {
            
            System.out.println("Tester " + (counter + 1) + "/" + 
                listTesters.size());
        
            outputWriter.writeIMEI(tester.getName());
            
            ArrayList<Stressor> listStressors = tester.
                getStressorExercisesForProtocol(ISenseStressAnalyzer.PROTOCOLS[0]);
        
            ArrayList<ScreenObject> numberPickers = listStressors.get(0).
                getNumberPickers();
            
            ArrayList<ArrayList<ScreenObject>> secondGrouping = new ArrayList<>();
            secondGrouping.add(new ArrayList<ScreenObject>());
            secondGrouping.add(new ArrayList<ScreenObject>());

            secondGrouping.get(0).add(numberPickers.get(0));
            secondGrouping.get(0).add(numberPickers.get(1));

            secondGrouping.get(1).add(numberPickers.get(2));
            secondGrouping.get(1).add(numberPickers.get(3));
        
            testersFeaturesValuesFirstSetStressorTasks.
                add(firstMiddleLastStressor(secondGrouping, listStressors));
            testersFeaturesValuesSecondSetStressorTasks.
                add(aroundFirstMiddleLastStressor(secondGrouping, listStressors));
            testersFeaturesValuesThirdSetStressorTasks.
                add(threeGroupOfStressorTasks(secondGrouping, listStressors));
            
            counter++;
            
            outputWriter.endParticipant();
        }
        
        outputWriter.close();
        
        workWithFeaturesToPrepareForTTest(1);
    }
    
    /**
     * Third Grouping: First and Second and Third and Fourth
     * @param listTesters the list of Stressor Task
     */
    private void workWithThirdGrouping(ArrayList<Tester> listTesters) {
        
        System.out.println("Working with second Stressor Grouping");
        
        outputWriter = new StressorDistanceWriter(2);
        
        testersFeaturesValuesFirstSetStressorTasks.clear();
        testersFeaturesValuesSecondSetStressorTasks.clear();
        testersFeaturesValuesThirdSetStressorTasks.clear();
        
        int counter = 0;
        for (Tester tester: listTesters) {
            
            System.out.println("Tester " + (counter + 1) + "/" + 
                listTesters.size());
            
            outputWriter.writeIMEI(tester.getName());
            
            ArrayList<Stressor> listStressors = tester.
                getStressorExercisesForProtocol(ISenseStressAnalyzer.PROTOCOLS[0]);
            
            ArrayList<ScreenObject> numberPickers = listStressors.get(0).
                getNumberPickers();
            
            ArrayList<ArrayList<ScreenObject>> thirdGrouping = new ArrayList<>();
            thirdGrouping.add(new ArrayList<ScreenObject>());

            thirdGrouping.get(0).addAll(numberPickers);
        
            testersFeaturesValuesFirstSetStressorTasks.
                add(firstMiddleLastStressor(thirdGrouping, listStressors));
            testersFeaturesValuesSecondSetStressorTasks.
                add(aroundFirstMiddleLastStressor(thirdGrouping, listStressors));
            testersFeaturesValuesThirdSetStressorTasks.
                add(threeGroupOfStressorTasks(thirdGrouping, listStressors));
            
            counter++;
            
            outputWriter.endParticipant();
        }
        
        outputWriter.close();
        
        workWithFeaturesToPrepareForTTest(2);
    }
    
    /**
     * Fourth Grouping: First + Second and Third + Fourth
     * @param listStressors
     * @param numberPickers 
     */
    private void workWithFourthGrouping(ArrayList<Tester> listTesters) {
        
        System.out.println("Working with fourth Stressor Grouping");
        
        outputWriter = new StressorDistanceWriter(3);
        
        testersFeaturesValuesFirstSetStressorTasks.clear();
        testersFeaturesValuesSecondSetStressorTasks.clear();
        testersFeaturesValuesThirdSetStressorTasks.clear();
        
        int counter = 0;
        for (Tester tester: listTesters) {
            
            System.out.println("Tester " + (counter + 1) + "/" + 
                listTesters.size());
        
            outputWriter.writeIMEI(tester.getName());
            
            ArrayList<Stressor> listStressors = tester.
                getStressorExercisesForProtocol(ISenseStressAnalyzer.PROTOCOLS[0]);
            
            ArrayList<ScreenObject> numberPickers = listStressors.get(0).
                getNumberPickers();
        
            ArrayList<ArrayList<ScreenObject>> fourthGrouping = new ArrayList<>();
            fourthGrouping.add(new ArrayList<ScreenObject>());
            fourthGrouping.add(new ArrayList<ScreenObject>());
            fourthGrouping.add(new ArrayList<ScreenObject>());

            fourthGrouping.get(0).add(numberPickers.get(0));

            fourthGrouping.get(1).add(numberPickers.get(1));
            fourthGrouping.get(1).add(numberPickers.get(2));

            fourthGrouping.get(2).add(numberPickers.get(3));
            
            testersFeaturesValuesFirstSetStressorTasks.
                add(firstMiddleLastStressor(fourthGrouping, listStressors));
            testersFeaturesValuesSecondSetStressorTasks.
                add(aroundFirstMiddleLastStressor(fourthGrouping, listStressors));
            testersFeaturesValuesThirdSetStressorTasks.
                add(threeGroupOfStressorTasks(fourthGrouping, listStressors));
        
            counter++;
            outputWriter.endParticipant();
        }
        
        outputWriter.close();
        
        workWithFeaturesToPrepareForTTest(3);
    }
    
    /**
     * Analyzes the first, middle and last Stressor Task
     * @param listNumberPickers how NumberPickers are grouped
     * @param listStressors the list of the Stressor Tasks
     */
    private FeaturesValuesForTester firstMiddleLastStressor(ArrayList<ArrayList<ScreenObject>> listNumberPickers, 
            ArrayList<Stressor> listStressors) {
        
        ArrayList<ArrayList<Stressor>> exerciseToAnalyze = new ArrayList<>();
        exerciseToAnalyze.add(new ArrayList<Stressor>()); //BEGIN
        exerciseToAnalyze.add(new ArrayList<Stressor>()); //MIDDLE
        exerciseToAnalyze.add(new ArrayList<Stressor>()); //END
        
        exerciseToAnalyze.get(0).add(listStressors.get(0));
        exerciseToAnalyze.get(1).add(listStressors.get(listStressors.size() / 2));
        exerciseToAnalyze.get(2).add(listStressors.get(listStressors.size() - 1));
        
        return workWithStressors(exerciseToAnalyze, listNumberPickers);
    }
    
    /**
     * Analyzes the first + 2, middle +- 1, last -2
     * @param listNumberPickers how NumberPicker widgets are grouped
     * @param listStressors the list of Stressor Task
     */
    private FeaturesValuesForTester aroundFirstMiddleLastStressor(ArrayList<ArrayList<ScreenObject>> 
        listNumberPickers, ArrayList<Stressor> listStressors) {
        
        ArrayList<ArrayList<Stressor>> exerciseToAnalyze = new ArrayList<>();
        exerciseToAnalyze.add(new ArrayList<Stressor>()); //BEGIN
        exerciseToAnalyze.add(new ArrayList<Stressor>()); //MIDDLE
        exerciseToAnalyze.add(new ArrayList<Stressor>()); //END
        
        exerciseToAnalyze.get(0).add(listStressors.get(0));
        exerciseToAnalyze.get(0).add(listStressors.get(1));
        exerciseToAnalyze.get(0).add(listStressors.get(2));
        
        exerciseToAnalyze.get(1).add(listStressors.get(listStressors.size() / 2 - 1));
        exerciseToAnalyze.get(1).add(listStressors.get(listStressors.size() / 2));
        exerciseToAnalyze.get(1).add(listStressors.get(listStressors.size() / 2 + 1));
        
        exerciseToAnalyze.get(2).add(listStressors.get(listStressors.size() - 3));
        exerciseToAnalyze.get(2).add(listStressors.get(listStressors.size() - 2));
        exerciseToAnalyze.get(2).add(listStressors.get(listStressors.size() - 1));
        
        return workWithStressors(exerciseToAnalyze, listNumberPickers);
    }
    
    private FeaturesValuesForTester threeGroupOfStressorTasks(ArrayList<ArrayList<ScreenObject>>
        listNumberPickers, ArrayList<Stressor> listStressors) {
        
        ArrayList<ArrayList<Stressor>> exerciseToAnalyze = new ArrayList<>();
        exerciseToAnalyze.add(new ArrayList<Stressor>());
        exerciseToAnalyze.add(new ArrayList<Stressor>());
        exerciseToAnalyze.add(new ArrayList<Stressor>());
        
        int elements = listStressors.size() / 3;
        int firstEnd = 0 + elements + 1, secondStart = firstEnd, 
            secondEnd = secondStart + elements + 1,thirdStart = secondEnd;
        
        exerciseToAnalyze.get(0).addAll(listStressors.subList(0, firstEnd));
        
        exerciseToAnalyze.get(1).addAll(listStressors.subList(secondStart, secondEnd));
        
        exerciseToAnalyze.get(2).addAll(listStressors.subList(thirdStart, listStressors.size()));
        
        return workWithStressors(exerciseToAnalyze, listNumberPickers);
    }
    
    /**
     * Takes the Stressor Task and the grouping method and calculates the features
     * @param exercisesToAnalyze
     * @param listNumberPickers 
     */
    private FeaturesValuesForTester workWithStressors(ArrayList<ArrayList<Stressor>> exercisesToAnalyze, 
        ArrayList<ArrayList<ScreenObject>> listNumberPickers) {
        
        FeaturesValuesForTester finalValues = new FeaturesValuesForTester();
            
        for (int i = 0; i < exercisesToAnalyze.size(); i++) {
            /**
             * Exercises are the ones to analyze and to write the final result
             */
            ArrayList<Double> valueAverageDistanceFromCenter = new ArrayList<>(),
                valueAverageLength = new ArrayList<>(), 
                valueAverageRatioOverBottom = new ArrayList<>(),
                valueAverageAreaRectangle = new ArrayList<>(),
                valueAverageLinearity = new ArrayList<>(), 
                valueAverageSize = new ArrayList<>(), 
                valueAveragePressure = new ArrayList<>(), 
                valueAverageTimeDuration = new ArrayList<>();
            
            for (ArrayList<ScreenObject> numberPickers: listNumberPickers) {
                /**
                 * numberPickers are the NumberPickers to combine together
                 */
                /**
                 * These values are used to store together all the values 
                 * of the numberPickers
                 */
                averageDistanceFromCenter = new ArrayList<>();
                averageLength = new ArrayList<>();
                averageRatioOverBottom = new ArrayList<>();
                averageAreaRectangle = new ArrayList<>();
                averageLinearity = new ArrayList<>();
                averageSize = new ArrayList<>();
                averagePressure = new ArrayList<>();
                averageTimeDuration = new ArrayList<>();
                
                workWithStressorsAndNumberPickers(exercisesToAnalyze.get(i), 
                    numberPickers);
            
                Double averageValue = MathUtils.
                    calculateStatisticInformation(averageDistanceFromCenter)[0];
                outputWriter.writeDistanceCenter(MathUtils.DECIMAL_FORMAT.
                    format(averageValue));
                valueAverageDistanceFromCenter.add(averageValue);

                averageValue = MathUtils.
                    calculateStatisticInformation(averageLength)[0];
                outputWriter.writeAverageLength(MathUtils.DECIMAL_FORMAT.
                    format(averageValue));
                valueAverageLength.add(averageValue);

                averageValue = MathUtils.
                    calculateStatisticInformation(averageRatioOverBottom)[0];
                outputWriter.writeRatioOverBottom(MathUtils.DECIMAL_FORMAT.
                    format(averageValue));
                valueAverageRatioOverBottom.add(averageValue);

                averageValue = MathUtils.
                    calculateStatisticInformation(averageAreaRectangle)[0];
                outputWriter.writeAreaRectangle(MathUtils.DECIMAL_FORMAT.
                    format(averageValue));           
                valueAverageAreaRectangle.add(averageValue);

                averageValue = MathUtils.
                    calculateStatisticInformation(averageLinearity)[0];
                outputWriter.writeLinearity(MathUtils.DECIMAL_FORMAT.
                    format(averageValue));
                valueAverageLinearity.add(averageValue);
                
                averageValue = MathUtils.
                    calculateStatisticInformation(averageSize)[0];
                outputWriter.writeSize(MathUtils.DECIMAL_FORMAT.format(averageValue));
                valueAverageSize.add(averageValue);
                
                averageValue = MathUtils.
                    calculateStatisticInformation(averagePressure)[0];
                outputWriter.writePressure(MathUtils.DECIMAL_FORMAT.format(averageValue));
                valueAveragePressure.add(averageValue);
                
                averageValue = MathUtils.
                    calculateStatisticInformation(averageTimeDuration)[0];
                outputWriter.writeTimeDuration(MathUtils.DECIMAL_FORMAT.format(averageValue));
                valueAverageTimeDuration.add(averageValue);
            }
                
            switch (i) {
                case 0: {
                    finalValues.averageDistanceFromCenter.begin = valueAverageDistanceFromCenter;
                    finalValues.averageLength.begin = valueAverageLength;
                    finalValues.averageRatioOverBottom.begin = valueAverageRatioOverBottom;
                    finalValues.averageAreaRectangle.begin = valueAverageAreaRectangle;
                    finalValues.averageLinearity.begin = valueAverageLinearity;
                    finalValues.averageSize.begin = valueAverageSize;
                    finalValues.averagePressure.begin = valueAveragePressure;
                    finalValues.averageTimeDuration.begin = valueAverageTimeDuration;
                    break;
                }
                case 1: {
                    finalValues.averageDistanceFromCenter.middle = valueAverageDistanceFromCenter;
                    finalValues.averageLength.middle = valueAverageLength;
                    finalValues.averageRatioOverBottom.middle = valueAverageRatioOverBottom;
                    finalValues.averageAreaRectangle.middle = valueAverageAreaRectangle;
                    finalValues.averageLinearity.middle = valueAverageLinearity;
                    finalValues.averageSize.middle = valueAverageSize;
                    finalValues.averagePressure.middle = valueAveragePressure;
                    finalValues.averageTimeDuration.middle = valueAverageTimeDuration;
                    break;
                }
                case 2: {
                    finalValues.averageDistanceFromCenter.end = valueAverageDistanceFromCenter;
                    finalValues.averageLength.end = valueAverageLength;
                    finalValues.averageRatioOverBottom.end = valueAverageRatioOverBottom;
                    finalValues.averageAreaRectangle.end = valueAverageAreaRectangle;
                    finalValues.averageLinearity.end = valueAverageLinearity;
                    finalValues.averageSize.end = valueAverageSize;
                    finalValues.averagePressure.end = valueAveragePressure;
                    finalValues.averageTimeDuration.end = valueAverageTimeDuration;
                    break;
                }
            }
        }
        
        return finalValues;
    }
    
    private void workWithStressorsAndNumberPickers(
            ArrayList<Stressor> stressors, ArrayList<ScreenObject> numberPickers) {
        
        for (Stressor stressor: stressors) {
            for (ScreenObject numberPicker: numberPickers) {
                workWithStressorAndScreenObject(stressor, numberPicker);
            }
        }
    }
    
    /**
     * Calculates the different features for the stressor task
     * @param stressor the Stressor exercise
     * @param object the considered NumberPicker
     * @return the average distances from the center 
     */
    private void workWithStressorAndScreenObject(Stressor stressor,
        ScreenObject object) {

        ArrayList<Double> distances = new ArrayList<>(), 
            lengths = new ArrayList<>(), ratios = new ArrayList<>(), 
            rectangles = new ArrayList<>(), linearity = new ArrayList<>(), 
            size = new ArrayList<>(), pressure = new ArrayList<>(), 
            time = new ArrayList<>();
        
        for (NumberPickerInteraction interaction: 
                stressor.getNumberPickerInteractions()) {
            
            if (interaction.isInside(object)) {
                distances.add(calculateAverageDistanceFromCenter(object, 
                    interaction.getListInteractions()));
                lengths.add(calculateLength(object, 
                    interaction.getListInteractions()));
                ratios.add(calculateRatioOverUnder(object, 
                    interaction.getListInteractions()));
                rectangles.add(calculateAreaRectangle(object, 
                    interaction.getListInteractions()));
                linearity.add(calculateLinearity(object, 
                    interaction.getListInteractions()));
                size.add(calculateSize(object, 
                    interaction.getListInteractions()));
                pressure.add(calculatePressure(object, 
                    interaction.getListInteractions()));
                time.add(calculateTimeDuration(object, 
                    interaction.getListInteractions()));
            }
        }
        
        averageDistanceFromCenter.add(MathUtils.
            calculateStatisticInformation(distances)[0]);
        averageLength.add(MathUtils.calculateStatisticInformation(lengths)[0]);
        averageRatioOverBottom.add(MathUtils.calculateStatisticInformation(ratios)[0]);
        averageAreaRectangle.add(MathUtils.calculateStatisticInformation(rectangles)[0]);
        averageLinearity.add(MathUtils.calculateStatisticInformation(linearity)[0]);
        averageSize.add(MathUtils.calculateStatisticInformation(size)[0]);
        averagePressure.add(MathUtils.calculateStatisticInformation(pressure)[0]);
        averageTimeDuration.add(MathUtils.calculateStatisticInformation(time)[0]);
    }
    
    /**
     * Calculates the average distance of one interaction from a screen object
     * @param object the ScreenObject
     * @param interactions the list of interactions
     * @return the average distance from the center
     */
    private double calculateAverageDistanceFromCenter(ScreenObject object, 
            ArrayList<Interaction> interactions) {
        
        Point center = object.getCenterPosition();
        ArrayList<Double> distances = new ArrayList<>();
        
        for (Interaction interaction: interactions) {
            if (object.isInside(interaction)) {
                distances.add(calculateDistance(center, interaction.getPoint()));
            }
        }
        
        return MathUtils.calculateStatisticInformation(distances)[0];
    }
    
    private double calculateLength(ScreenObject object, 
            ArrayList<Interaction> interactions) {
        
        double length = 0.0;
        
        for (int i = 1; i < interactions.size(); i++) {
            if (object.isInside(interactions.get(i)) && 
                object.isInside(interactions.get(i-1))) {
                
                length += calculateDistance(interactions.get(i).getPoint(), 
                    interactions.get(i-1).getPoint());
            }
        }
        
        return length;
    }
    
    private double calculateRatioOverUnder(ScreenObject object, 
            ArrayList<Interaction> interactions) {
        
        ArrayList<Interaction> over = new ArrayList<>(), 
            under = new ArrayList<>();
        
        for (Interaction interaction: interactions) {
            if (object.isInside(interaction)) {
                if (interaction.getPoint().getY() < object.getCenterPosition().getY()) {
                    over.add(interaction);
                }
                else if (interaction.getPoint().getY() >= object.getCenterPosition().getY()) {
                    under.add(interaction);
                }
            }
        }
        
        Double distanceOver = 0.0, distanceUnder = 0.0;
        
        for (int i = 1; i < over.size(); i++) {
            distanceOver += calculateDistance(over.get(i).getPoint(), 
                over.get(i - 1).getPoint());
        }
        for (int i = 1; i < under.size(); i++) {
            distanceUnder += calculateDistance(under.get(i).getPoint(), 
                under.get(i-1).getPoint());
        }
        
        return distanceOver / distanceUnder;
    }
    
    private double calculateAreaRectangle(ScreenObject object, 
            ArrayList<Interaction> interactions) {
        
        Point top = interactions.get(0).getPoint(), 
            bottom = interactions.get(0).getPoint();
        
        for (Interaction interaction: interactions) {
            if (interaction.getPoint().getY() < top.getY()) {
                top = interaction.getPoint();
            }
            if (interaction.getPoint().getY() > bottom.getY()) {
                bottom = interaction.getPoint();
            }
        }
        
        int base = Math.abs(top.getX() - bottom.getX()), 
            height = Math.abs(top.getY() - bottom.getY());
        
        return base * height;
    }
    
    private double calculateLinearity(ScreenObject object, 
            ArrayList<Interaction> interactions) {
        
        ArrayList<Interaction> toUse = new ArrayList<>();
        
        for (Interaction intearction: interactions) {
            if (object.isInside(intearction)) {
                toUse.add(intearction);
            }
        }
        
        double linearity = 0.0;
        
        for (int i = 1; i < toUse.size(); i++) {
            linearity += Math.abs(toUse.get(i).getPoint().getX() - 
                toUse.get(i-1).getPoint().getX());
        }
        
        return linearity / (double) object.getWidth();
    }
    
    /**
     * Calculates euclidian distance between two points
     * @param x the first point
     * @param y the second point
     * @return the euclidian distance
     */
    private static double calculateDistance(Point x, Point y) {
        
        return Math.sqrt(Math.pow(x.getX() - y.getX(), 2) + 
            Math.pow(x.getY() - y.getY(), 2));
    }
    
    public double calculateSize(ScreenObject object, 
            ArrayList<Interaction> interactions) {
        
        int elements = 0; double size = 0;
        for (Interaction interaction: interactions) {
            if (object.isInside(interaction)) {
                size += interaction.getSize();
                elements++;
            }
        }
        
        return size / (double) elements;
    }
    
    public double calculatePressure(ScreenObject object, 
            ArrayList<Interaction> interactions) {
        
        int elements = 0; double pressure = 0;
        for (Interaction interaction: interactions) {
            if (object.isInside(interaction)) {
                pressure += interaction.getPressure();
                elements++;
            }
        }
        
        return pressure / (double)elements;
    }
    
    public double calculateTimeDuration(ScreenObject object, 
            ArrayList<Interaction> interactions) {
        
        Interaction first = interactions.get(0), 
            last = interactions.get(interactions.size() - 1);
        int index = 1;
        
        while (!object.isInside(first)) {
            first = interactions.get(index);
            index++;
        }
        
        index = interactions.size() - 2;
        while (!object.isInside(last)) {
            last = interactions.get(index);
            index--;
        }
        
        return last.getTimestamp() - first.getTimestamp();
    }
    
    private void workWithFeaturesToPrepareForTTest(int groupingIndex) {
        
        TTestStressorTaskFeaturesOutputWriter ttestWriter = 
            new TTestStressorTaskFeaturesOutputWriter(groupingIndex);
        
        /**
         * Iterating on each feature 
         */
        /**
         * First feature: average distance from center
         */
        ArrayList<BeginMiddleEndValues> valuesFirst = new ArrayList<>(), 
                valuesSecond = new ArrayList<>(), valuesThird = new ArrayList<>();
        for (int i = 0; i < testersFeaturesValuesFirstSetStressorTasks.size(); i++) { 
            
            valuesFirst.add(testersFeaturesValuesFirstSetStressorTasks.get(i).averageDistanceFromCenter);
            valuesSecond.add(testersFeaturesValuesSecondSetStressorTasks.get(i).averageDistanceFromCenter);
            valuesThird.add(testersFeaturesValuesThirdSetStressorTasks.get(i).averageDistanceFromCenter);
        }
        ttestWriter.writeFeatureName("Average distance from center");
        workWithFeatureForTTest(valuesFirst, ttestWriter);
        workWithFeatureForTTest(valuesSecond, ttestWriter);
        workWithFeatureForTTest(valuesThird, ttestWriter);
        ttestWriter.endFeatureTests();
        
        /**
         * Second feature: average length
         */
        valuesFirst.clear(); valuesSecond.clear(); valuesThird.clear();
        for (int i = 0; i < testersFeaturesValuesFirstSetStressorTasks.size(); i++) {
            
            valuesFirst.add(testersFeaturesValuesFirstSetStressorTasks.get(i).averageLength);
            valuesSecond.add(testersFeaturesValuesSecondSetStressorTasks.get(i).averageLength);
            valuesThird.add(testersFeaturesValuesThirdSetStressorTasks.get(i).averageLength);
        }
        ttestWriter.writeFeatureName("Average length");
        workWithFeatureForTTest(valuesFirst, ttestWriter);
        workWithFeatureForTTest(valuesSecond, ttestWriter);
        workWithFeatureForTTest(valuesThird, ttestWriter);
        ttestWriter.endFeatureTests();
        
        /**
         * Third feature: average ratio over bottom
         */
        valuesFirst.clear(); valuesSecond.clear(); valuesThird.clear();
        for (int i = 0; i < testersFeaturesValuesFirstSetStressorTasks.size(); i++) {
            
            valuesFirst.add(testersFeaturesValuesFirstSetStressorTasks.get(i).averageRatioOverBottom);
            valuesSecond.add(testersFeaturesValuesSecondSetStressorTasks.get(i).averageRatioOverBottom);
            valuesThird.add(testersFeaturesValuesThirdSetStressorTasks.get(i).averageRatioOverBottom);
        }
        ttestWriter.writeFeatureName("Average ratio over bottom");
        workWithFeatureForTTest(valuesFirst, ttestWriter);
        workWithFeatureForTTest(valuesSecond, ttestWriter);
        workWithFeatureForTTest(valuesThird, ttestWriter);
        ttestWriter.endFeatureTests();
        
        /**
         * Fourth feature: average area rectangle
         */
        valuesFirst.clear(); valuesSecond.clear(); valuesThird.clear();
        for (int i = 0; i < testersFeaturesValuesFirstSetStressorTasks.size(); i++) {
            
            valuesFirst.add(testersFeaturesValuesFirstSetStressorTasks.get(i).averageAreaRectangle);
            valuesSecond.add(testersFeaturesValuesSecondSetStressorTasks.get(i).averageAreaRectangle);
            valuesThird.add(testersFeaturesValuesThirdSetStressorTasks.get(i).averageAreaRectangle);
        }
        ttestWriter.writeFeatureName("Average area rectangle");
        workWithFeatureForTTest(valuesFirst, ttestWriter);
        workWithFeatureForTTest(valuesSecond, ttestWriter);
        workWithFeatureForTTest(valuesThird, ttestWriter);
        ttestWriter.endFeatureTests();
        
        /**
         * Fifth feature: average linearity
         */
        valuesFirst.clear(); valuesSecond.clear(); valuesThird.clear();
        for (int i = 0; i < testersFeaturesValuesFirstSetStressorTasks.size(); i++) {
            
            valuesFirst.add(testersFeaturesValuesFirstSetStressorTasks.get(i).averageLinearity);
            valuesSecond.add(testersFeaturesValuesSecondSetStressorTasks.get(i).averageLinearity);
            valuesThird.add(testersFeaturesValuesFirstSetStressorTasks.get(i).averageLinearity);
        }
        ttestWriter.writeFeatureName("Average linearity");
        workWithFeatureForTTest(valuesFirst, ttestWriter);
        workWithFeatureForTTest(valuesSecond, ttestWriter);
        workWithFeatureForTTest(valuesThird, ttestWriter);
        ttestWriter.endFeatureTests();
        
        /**
         * Sixth feature: average size
         */
        valuesFirst.clear(); valuesSecond.clear(); valuesThird.clear();
        for (int i = 0; i < testersFeaturesValuesFirstSetStressorTasks.size(); i++) {
            
            valuesFirst.add(testersFeaturesValuesFirstSetStressorTasks.get(i).averageSize);
            valuesSecond.add(testersFeaturesValuesSecondSetStressorTasks.get(i).averageSize);
            valuesThird.add(testersFeaturesValuesThirdSetStressorTasks.get(i).averageSize);
        }
        ttestWriter.writeFeatureName("Average Size");
        workWithFeatureForTTest(valuesFirst, ttestWriter);
        workWithFeatureForTTest(valuesSecond, ttestWriter);
        workWithFeatureForTTest(valuesThird, ttestWriter);
        ttestWriter.endFeatureTests();
        
        /**
         * Seventh feature: average pressure
         */
        valuesFirst.clear(); valuesSecond.clear(); valuesThird.clear();
        for (int i = 0; i < testersFeaturesValuesFirstSetStressorTasks.size(); i++) {
            
            valuesFirst.add(testersFeaturesValuesFirstSetStressorTasks.get(i).averagePressure);
            valuesSecond.add(testersFeaturesValuesSecondSetStressorTasks.get(i).averagePressure);
            valuesThird.add(testersFeaturesValuesThirdSetStressorTasks.get(i).averagePressure);
        }
        ttestWriter.writeFeatureName("Average Pressure");
        workWithFeatureForTTest(valuesFirst, ttestWriter);
        workWithFeatureForTTest(valuesSecond, ttestWriter);
        workWithFeatureForTTest(valuesThird, ttestWriter);
        ttestWriter.endFeatureTests();
        
        /**
         * Eight feature: average time duration
         */
        valuesFirst.clear(); valuesSecond.clear(); valuesThird.clear();
        for (int i = 0; i < testersFeaturesValuesFirstSetStressorTasks.size(); i++) {
            
            valuesFirst.add(testersFeaturesValuesFirstSetStressorTasks.get(i).averageTimeDuration);
            valuesSecond.add(testersFeaturesValuesSecondSetStressorTasks.get(i).averageTimeDuration);
            valuesThird.add(testersFeaturesValuesThirdSetStressorTasks.get(i).averageTimeDuration);
        }
        ttestWriter.writeFeatureName("Average time duration");
        workWithFeatureForTTest(valuesFirst, ttestWriter);
        workWithFeatureForTTest(valuesSecond, ttestWriter);
        workWithFeatureForTTest(valuesThird, ttestWriter);
        ttestWriter.endFeatureTests();
        
        ttestWriter.close();
    }
    /**
     * Performs TTest with features values of begin, middle end Stressor Tasks
     * @param beginValues the list of values at the beginning
     * @param middleValues the list of values in the middle
     * @param endValues the list of values at the end
     * @param writer the output writer
     */
    private void workWithFeatureForTTest(ArrayList<BeginMiddleEndValues> values, 
            TTestStressorTaskFeaturesOutputWriter writer) {
        
        ArrayList<Double> beginValues = new ArrayList<>(), middleValues = new ArrayList<>(), 
            endValues = new ArrayList<>();
        
        /**
         * Iterating over all the NumberPickers
         */
        for (int i = 0; i < values.get(0).begin.size(); i++) {
            
            /**
             * Retrieving all the values BEGIN, MIDDLE and END 
             */
            for (BeginMiddleEndValues threeValues: values) {
                beginValues.add(threeValues.begin.get(i)); middleValues.add(threeValues.middle.get(i));
                endValues.add(threeValues.end.get(i));
            }

            double[] arrayBeginValues = MathUtils.convertToArrayDouble(beginValues), 
                arrayMiddleValues = MathUtils.convertToArrayDouble(middleValues),
                arrayEndValues = MathUtils.convertToArrayDouble(endValues);

            if (arrayBeginValues.length >= 2 && arrayMiddleValues.length >= 2 
                && arrayEndValues.length >= 2) {  

                Double beginMiddle = new TTest().pairedTTest(arrayBeginValues, arrayMiddleValues),
                    beginEnd = new TTest().pairedTTest(arrayBeginValues, arrayEndValues),
                    middleEnd = new TTest().pairedTTest(arrayMiddleValues, arrayEndValues);

                writer.writeTTest(beginMiddle, beginEnd, middleEnd);
            }
            else {
                writer.writeTTest(-1, -1, -1);
            }
        }
    }
}
