/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isensestressanalyzer.interaction;

/**
 *
 * @author Matteo
 */
public class ScrollMovement 
{
    private int left;
    private int top;
    private int oldLeft;
    private int oldTop;
    
    public ScrollMovement(String logString) {
        
        String[] elements = logString.split(",");
        /**
         * elements[0]: V_SCROLL or H_SCROLL
         * elements[1]: left
         * elements[2]: top
         * elements[3]: old left
         * elements[4]: old top
         */
        this.createInstance(Integer.valueOf(elements[1]), Integer.valueOf(elements[2]), 
                Integer.valueOf(elements[3]), Integer.valueOf(elements[4]));
    }
    
    private void createInstance(int left, int top, int oldLeft, int oldTop) {
        
        this.left = left; this.top = top; this.oldLeft = oldLeft; 
        this.oldTop = oldTop;
    }
    
    public int getLeft() {
        return this.left;
    }
    
    public int getTop() {
        return this.top;
    }
    
    public int getOldLeft() {
        return this.oldLeft;
    }
    
    public int getOldTop() {
        return this.oldTop;
    }
}
