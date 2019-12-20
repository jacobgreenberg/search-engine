import java.util.HashSet;
import java.util.Scanner;

/**
 * This is my code! Its goal is to provide a CLI to the user
 * CS 312 - Assignment 9
 * @author Jacob Greenberg
 * @version 1.1 12-7-19
 */
public class CLI
{
    /**
     * @param args standard in from command line
     */
    public static void main(String[] args)
    {
        if (args.length < 2)
            System.err.println("Usage: java CLI [-d] stoplist documents\nctrl-d to exit");

        else
        {
            boolean display = args[0].equalsIgnoreCase("-d");
            int currentIndex = display ? 1 : 0;

            InvertedIndex invertedIndex = new InvertedIndex(args[currentIndex++]);

            long indexStart = System.currentTimeMillis();

            for (; currentIndex < args.length; currentIndex++)
                invertedIndex.addDocument(new Document(args[currentIndex]));

            long indexStop = System.currentTimeMillis();
            long indexElapsed = indexStop - indexStart;

            System.out.println("@@index took " + indexElapsed + "ms\n");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a query: ");
            while (scanner.hasNext())
            {
                String line = scanner.nextLine();
                if (line.equalsIgnoreCase("@@debug"))
                    System.out.println(invertedIndex.dump());
                else
                {
                    String[] query = line.trim().split(" ");
                    HashSet<Document> docs;
                    long queryStart = System.currentTimeMillis();

                    if (query.length == 1)
                        docs = invertedIndex.singleWordQuery(line);
                    else
                        docs = invertedIndex.multiWordQuery(line);

                    System.out.println("--- found in " + (docs == null ? 0 : docs.size())
                            + " documents");

                    if (docs != null)
                    {
                        if (display)
                            for (Document document : docs)
                                System.out.println("\n" + document.display());
                        else
                            for (Document document : docs)
                                System.out.println(document.toString());
                    }

                    long queryStop = System.currentTimeMillis();
                    long queryElapsed = queryStop - queryStart;
                    System.out.println("\n@@query took " + queryElapsed + "ms\n");
                }
                System.out.print("Enter a query: ");
            }
            scanner.close();
        }
    }
}
