/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isensestressanalyzer.utils;

/**
 *
 * @author Matteo
 */
public class ViewKeyboard 
{
    private final Point position;
    private final float width;
    private final float height;
    
    public ViewKeyboard(float x, float y, float width, float height)
    {
        this.position = new Point(x, y); this.width = width; 
        this.height = height;
    }
    
    public Point getPosition()
    {
        return this.position;
    }
    
    public float getWidth()
    {
        return this.width;
    }
    
    public float getHeight()
    {
        return this.height;
    }
}
