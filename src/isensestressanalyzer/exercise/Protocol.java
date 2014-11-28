package isensestressanalyzer.exercise;

/**
 * This class represents a particular protocol. Used to search in a file
 * for that particular protocol
 * @author Matteo Ciman
 * @since 2014-11-11
 * @version 0.1
 */
public class Protocol 
{
    private final String[] steps;
    
    public Protocol(String... steps)
    {
        this.steps = steps;
    }
    
    /**
     * Returns the protocol in a search in file string
     * @return the string of the protocol to search in the files
     */
    @Override
    public String toString()
    {
        String toReturn = "** PROTOCOL: ";
        for (String step: steps)
        {
            toReturn += step + " - ";
        }
        return toReturn;
    }
    
    /**
     * Returns the steps of the Protocol
     * @return an Array of String[] with the steps of the protocol
     */
    public String[] getSteps()
    {
        return this.steps;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Protocol)
        {
            return this.toString().equals(((Protocol) obj).toString());
        }
        else return false;
    }
}
