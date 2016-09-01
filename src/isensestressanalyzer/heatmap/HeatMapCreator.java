package isensestressanalyzer.heatmap;

import isensestressanalyzer.interaction.Digit;
import isensestressanalyzer.interaction.Interaction;
import isensestressanalyzer.tester.Tester;
import isensestressanalyzer.utils.Point;
import isensestressanalyzer.utils.ScreenObject;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 */
public class HeatMapCreator {
    
    protected final Tester tester;
    protected final Integer[] digitSize = {1, 5, 10, 20};
    
    public HeatMapCreator(Tester tester) {
        this.tester = tester;
    }
    
    /**
     * For each digit, it completes the heatmap adding each digit over it
     * @param heatmap the heatmap we have to complete
     * @param targetObject the ScreenObject we are studying
     * @param listDigits the list of digits over the object
     * @param digitSize the size of the digit
     */
    protected void completeHeatmapValues(ArrayList<ArrayList<Double>> heatmap, 
    		ScreenObject targetObject, ArrayList<Digit> listDigits, 
                Integer digitSize) {
    	
    	for (Digit digit: listDigits) {
    		
            ArrayList<Interaction> listInteractions = digit.getInteractions();

            for (Interaction interaction: listInteractions) {

                int xPosition = Math.round(interaction.getPoint().getX() 
                    - targetObject.getPosition().getX());

                int yPosition = Math.round(interaction.getPoint().getY() 
                    - targetObject.getPosition().getY());

                if (yPosition >=0 && xPosition >= 0 && 
                    xPosition < heatmap.size() && 
                    yPosition < heatmap.get(0).size()) {
                    
                    updateHeatmapScore(xPosition, yPosition, digitSize, heatmap);
                }
            }
    	}
    }

    protected void updateHeatmapScore(int xPosition, int yPosition, int touchSize, 
            ArrayList<ArrayList<Double>> heatmap) {
            
        Double centerValue = heatmap.get(xPosition).get(yPosition);
        if (centerValue + touchSize < heatmap.get(xPosition).size()) {
            heatmap.get(xPosition).set(yPosition, centerValue + touchSize);
        }
        
        for (int i = 1; i < touchSize; i++) {
            
            /**
             * i also represents the increment for 
             */
            Point topLeft = new Point(xPosition - (touchSize - i), 
                    yPosition - (touchSize - i)), 
                topRight = new Point(xPosition + (touchSize - i), 
                    yPosition - (touchSize - i)), 
                bottomLeft = new Point(xPosition - (touchSize - i), 
                    yPosition + (touchSize - i)), 
                bottomRight = new Point(xPosition + (touchSize - i), 
                    yPosition + (touchSize - i));
            
            fromLeftToRight(topLeft, topRight, i, heatmap);
            fromLeftToRight(bottomLeft, bottomRight, i, heatmap);
            fromTopToBottom(topLeft, bottomLeft, i, heatmap);
            fromTopToBottom(topRight, bottomRight, i, heatmap);
        }
    }
    
    private void fromLeftToRight(Point left, Point right, 
            int increase, ArrayList<ArrayList<Double>> heatmap) {
        
        for (int index = left.getX(); index < right.getX(); index++) {
                
            if (index >= 0 && index < heatmap.size() && 
                left.getY() > 0 && left.getY() < heatmap.get(index).size()) {
                    
                Double currentValue = heatmap.get(index).get(left.getY());
                heatmap.get(index).set(left.getY(), currentValue + increase);
            }
        }
    }
    
    private void fromTopToBottom(Point top, Point bottom, 
            int increase, ArrayList<ArrayList<Double>> heatmap) {
        
        for (int index = top.getY(); index < bottom.getY(); index++) {
            
            if (top.getX() >= 0 && top.getX() < heatmap.size() && 
                top.getY() > 0 && 
                top.getY() < heatmap.get(top.getX()).size() && 
                index >= 0 && index < heatmap.get(top.getX()).size()) {
                
                Double currentValue = heatmap.get(top.getX()).get(index);
                heatmap.get(top.getX()).set(index, currentValue + increase);
            }
        }
    }
    
    /**
     * Copies an ArrayList of double in an array
     * @param list the starting arraylist
     * @param array the target destination array
     */
    protected void copyDoubleArrayListToDoubleArray(ArrayList<ArrayList<Double>> list, 
    		double[][] array) {
    	
        for (int i = 0; i < array.length; i++) {
            
            array[i] = new double[list.size()];
        }
        
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                array[j][i] = list.get(i).get(j);
            }
        }
    }
}
