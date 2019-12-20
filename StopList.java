import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;

/**
 * This is my code! Its goal is to create a stop list and perform various functions therein
 * CS 312 - Assignment 9
 * @author Jacob Greenberg
 * @version 1.1 12-7-19
 */
public class StopList
{
    protected HashSet<String> stopList;

    /**
     * purpose: read in stop list and build a set of its stop words
     * @param filename the filename of the stop list
     */
    StopList(String filename)
    {
        stopList = new HashSet<>();
        try
        {
            Scanner scanner = new Scanner(new FileReader(filename));
            while (scanner.hasNext())
                stopList.add(scanner.next().toLowerCase());

            scanner.close();

        } catch (Exception error)
        {
            System.err.println("Stop list file could not be read:\n" + error);
        }
    }

    /**
     * purpose: determine if stop list contains a given word
     * @param word the word being checked against the stop list
     * @return true or false if the word is a stop word
     */
    boolean containsWord(String word)
    {
        return stopList.contains(word.toLowerCase());
    }

    // for testing only
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (String word : stopList)
            sb.append(word).append(", ");
        sb.deleteCharAt(sb.length() - 2);

        return sb.toString();
    }
}
