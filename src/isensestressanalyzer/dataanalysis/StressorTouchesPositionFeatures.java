package isensestressanalyzer.dataanalysis;

import isensestressanalyzer.ISenseStressAnalyzer;
import isensestressanalyzer.exercise.Stressor;
import isensestressanalyzer.interaction.Interaction;
import isensestressanalyzer.interaction.NumberPickerInteraction;
import isensestressanalyzer.outputwriter.StressorDistanceWriter;
import isensestressanalyzer.tester.Tester;
import isensestressanalyzer.utils.MathUtils;
import isensestressanalyzer.utils.Point;
import isensestressanalyzer.utils.ScreenObject;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 */
public class StressorTouchesPositionFeatures {
    
    private static StressorDistanceWriter outputWriter;
    
    private static ArrayList<Double> averageDistanceFromCenter = new ArrayList<>(), 
        averageLength = new ArrayList<>(), 
        averageRatioOverBottom = new ArrayList<>(), 
        averageAreaRectangle = new ArrayList<>(), 
        averageLinearity = new ArrayList<>();
    
    public static void workWithNumberPickers(ArrayList<Tester> listTesters) {
        
        outputWriter = new StressorDistanceWriter();
        
        int counter = 1;
        for (Tester tester: listTesters) {
            
            System.out.println("Working with NumberPickers - Tester " + 
                counter + "/" + listTesters.size());
            
            outputWriter.writeIMEI(tester.getName());
            
            ArrayList<Stressor> listStressors = tester.
                getStressorExercisesForProtocol(ISenseStressAnalyzer.PROTOCOLS[0]);
            
            ArrayList<ScreenObject> numberPickers = listStressors.get(0)
                .getNumberPickers();
            
            workWithFirstGrouping(listStressors, numberPickers);
            workWithSecondGrouping(listStressors, numberPickers);
            workWithThirdGrouping(listStressors, numberPickers);
            workWithFourthGrouping(listStressors, numberPickers);
            
            outputWriter.endParticipant();
            
            counter++;
        }
        
        outputWriter.close();
    }
    
    private static void workWithFirstGrouping(ArrayList<Stressor> listStressors, 
            ArrayList<ScreenObject> numberPickers) {
        
        ArrayList<ArrayList<ScreenObject>> firstGrouping = new ArrayList<>();
        firstGrouping.add(new ArrayList<ScreenObject>());
        firstGrouping.add(new ArrayList<ScreenObject>());
        firstGrouping.add(new ArrayList<ScreenObject>());
        firstGrouping.add(new ArrayList<ScreenObject>());
        
        firstGrouping.get(0).add(numberPickers.get(0));
        firstGrouping.get(1).add(numberPickers.get(1));
        firstGrouping.get(2).add(numberPickers.get(2));
        firstGrouping.get(3).add(numberPickers.get(3));
        
        firstMiddleLastStressor(firstGrouping, listStressors);
        aroundFirstMiddleLastStressor(firstGrouping, listStressors);
    }
    
    private static void workWithSecondGrouping(ArrayList<Stressor> listStressors, 
            ArrayList<ScreenObject> numberPickers) {
        
        ArrayList<ArrayList<ScreenObject>> secondGrouping = new ArrayList<>();
        secondGrouping.add(new ArrayList<ScreenObject>());
        secondGrouping.add(new ArrayList<ScreenObject>());
        
        secondGrouping.get(0).add(numberPickers.get(0));
        secondGrouping.get(0).add(numberPickers.get(1));
        
        secondGrouping.get(1).add(numberPickers.get(2));
        secondGrouping.get(1).add(numberPickers.get(3));
        
        firstMiddleLastStressor(secondGrouping, listStressors);
        aroundFirstMiddleLastStressor(secondGrouping, listStressors);
    }
    
    private static void workWithThirdGrouping(ArrayList<Stressor> listStressors, 
            ArrayList<ScreenObject> numberPickers) {
        
        ArrayList<ArrayList<ScreenObject>> thirdGrouping = new ArrayList<>();
        thirdGrouping.add(new ArrayList<ScreenObject>());
        
        thirdGrouping.get(0).addAll(numberPickers);
        
        firstMiddleLastStressor(thirdGrouping, listStressors);
        aroundFirstMiddleLastStressor(thirdGrouping, listStressors);
    }
    
    private static void workWithFourthGrouping(ArrayList<Stressor> listStressors, 
            ArrayList<ScreenObject> numberPickers) {
        
        ArrayList<ArrayList<ScreenObject>> fourthGrouping = new ArrayList<>();
        fourthGrouping.add(new ArrayList<ScreenObject>());
        fourthGrouping.add(new ArrayList<ScreenObject>());
        fourthGrouping.add(new ArrayList<ScreenObject>());
        
        fourthGrouping.get(0).add(numberPickers.get(0));
        
        fourthGrouping.get(1).add(numberPickers.get(1));
        fourthGrouping.get(1).add(numberPickers.get(2));
        
        fourthGrouping.get(2).add(numberPickers.get(3));
        
        firstMiddleLastStressor(fourthGrouping, listStressors);
        aroundFirstMiddleLastStressor(fourthGrouping, listStressors);
    }
    
    /**
     * 
     * @param listNumberPickers how I want to group NumberPickers
     * @param listStressors 
     */
    private static void firstMiddleLastStressor(ArrayList<ArrayList<ScreenObject>> listNumberPickers, 
            ArrayList<Stressor> listStressors) {
        
        ArrayList<ArrayList<Stressor>> exerciseToAnalyze = new ArrayList<>();
        exerciseToAnalyze.add(new ArrayList<Stressor>()); //BEGIN
        exerciseToAnalyze.add(new ArrayList<Stressor>()); //MIDDLE
        exerciseToAnalyze.add(new ArrayList<Stressor>()); //END
        
        exerciseToAnalyze.get(0).add(listStressors.get(0));
        exerciseToAnalyze.get(1).add(listStressors.get(listStressors.size() / 2));
        exerciseToAnalyze.get(2).add(listStressors.get(listStressors.size() - 1));
        
        workWithStressors(exerciseToAnalyze, listNumberPickers);
    }
    
    private static void aroundFirstMiddleLastStressor(ArrayList<ArrayList<ScreenObject>> 
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
        
        workWithStressors(exerciseToAnalyze, listNumberPickers);
    }
    
    private static void workWithStressors(ArrayList<ArrayList<Stressor>> exercisesToAnalyze, 
        ArrayList<ArrayList<ScreenObject>> listNumberPickers) {
            
        for (ArrayList<Stressor> exercises: exercisesToAnalyze) {
            /**
             * Exercises are the ones to analyze and to write the final result
             */
            
            for (ArrayList<ScreenObject> numberPickers: listNumberPickers) {
                /**
                 * numberPickers are the NumberPickers to combine together
                 */
                averageDistanceFromCenter = new ArrayList<>();
                averageLength = new ArrayList<>();
                averageRatioOverBottom = new ArrayList<>();
                averageAreaRectangle = new ArrayList<>();
                averageLinearity = new ArrayList<>();
                
                workWithStressorsAndNumberPickers(exercises, numberPickers);
            
                outputWriter.writeDistanceCenter(MathUtils.DECIMAL_FORMAT.format(
                    MathUtils.calculateStatisticInformation(averageDistanceFromCenter)[0]));

                outputWriter.writeAverageLength(MathUtils.DECIMAL_FORMAT.format(
                    MathUtils.calculateStatisticInformation(averageLength)[0]));

                outputWriter.writeRatioOverBottom(MathUtils.DECIMAL_FORMAT.format(
                    MathUtils.calculateStatisticInformation(averageRatioOverBottom)[0]));

                outputWriter.writeAreaRectangle(MathUtils.DECIMAL_FORMAT.format(
                    MathUtils.calculateStatisticInformation(averageAreaRectangle)[0]));

                outputWriter.writeLinearity(MathUtils.DECIMAL_FORMAT.format(
                    MathUtils.calculateStatisticInformation(averageLinearity)[0]));
            }
        }
    }
    
    private static void workWithStressorsAndNumberPickers(
            ArrayList<Stressor> stressors, ArrayList<ScreenObject> numberPickers) {
        
        for (Stressor stressor: stressors) {
            for (ScreenObject numberPicker: numberPickers) {
                workWithStressorAndScreenObject(stressor, numberPicker);
            }
        }
    }
    
    /**
     * Calculates the different features for 
     * @param stressor the Stressor exercise
     * @param object the considered NumberPicker
     * @return the average distances from the center 
     */
    private static void workWithStressorAndScreenObject(Stressor stressor,
        ScreenObject object) {

        ArrayList<Double> distances = new ArrayList<>();
        ArrayList<Double> lengths = new ArrayList<>(), 
                ratios = new ArrayList<>(), rectangles = new ArrayList<>(), 
                linearity = new ArrayList<>();
        
        for (NumberPickerInteraction interaction: stressor.getNumberPickerInteractions()) {
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
            }
        }
        
        averageDistanceFromCenter.add(MathUtils.
            calculateStatisticInformation(distances)[0]);
        averageLength.add(MathUtils.calculateStatisticInformation(lengths)[0]);
        averageRatioOverBottom.add(MathUtils.calculateStatisticInformation(ratios)[0]);
        averageAreaRectangle.add(MathUtils.calculateStatisticInformation(rectangles)[0]);
        averageLinearity.add(MathUtils.calculateStatisticInformation(linearity)[0]);
    }
    
    /**
     * Calculates the average distance of one interaction from a screen object
     * @param object the ScreenObject
     * @param interactions the list of interactions
     * @return the average distance from the center
     */
    private static double calculateAverageDistanceFromCenter(ScreenObject object, 
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
    
    private static double calculateLength(ScreenObject object, 
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
    
    private static double calculateRatioOverUnder(ScreenObject object, 
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
    
    private static double calculateAreaRectangle(ScreenObject object, 
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
    
    private static double calculateLinearity(ScreenObject object, 
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
    
}
