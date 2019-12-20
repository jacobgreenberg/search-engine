import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.nio.file.Paths;

/**
 * This is my code! Its goal is to create a document and perform various functions therein
 * CS 312 - Assignment 9
 * @author Jacob Greenberg
 * @version 1.1 12-7-19
 */
public class Document
{
    protected String name;
    protected HashSet<String> words;
    protected StringBuilder fullText;

    /**
     * purpose: read in document and build a set of its words
     * @param filename the filename of the document
     */
    Document(String filename)
    {
        words = new HashSet<>();
        fullText = new StringBuilder();
        name = Paths.get(filename).getFileName().toString();

        try
        {
            Scanner scanner = new Scanner(new FileReader(filename));
            while (scanner.hasNext())
            {
                String next = scanner.next();
                words.add(next.replaceAll("[^a-zA-Z0-9-']", "").toLowerCase());
                fullText.append(next).append(" ");
            }
            scanner.close();

        } catch (Exception error)
        {
            System.err.println("Document could not be read:\n" + error);
        }
    }

    /**
     * purpose: create string representation of document name and its original contents
     * @return the full text of the document
     */
    String display()
    {
        return name + ":\n" + fullText.toString();
    }

    /**
     * purpose: return string of document's name
     * @return name of document for inverted index dump
     */
    @Override
    public String toString()
    {
        return name;
    }
}
