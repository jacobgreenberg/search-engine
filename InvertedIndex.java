import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * This is my code! Its goal is to create an inverted index and perform various functions therein
 * CS 312 - Assignment 9
 * @author Jacob Greenberg
 * @version 1.1 12-7-19
 */
public class InvertedIndex
{
    protected HashMap<String, HashSet<Document>> directory;
    protected HashSet<Document> documents;
    protected StopList stopList;

    /**
     * purpose: instantiate documents, directory, and stop list with a given filename
     * @param stopFileName the filename of the stop list
     */
    InvertedIndex(String stopFileName)
    {
        stopList = new StopList(stopFileName); // O(n)
        directory = new HashMap<>();
        documents = new HashSet<>();
    }

    /**
     * purpose: map words (keys) to their containing documents (values)
     */
    private void index()
    {
        for (Document document : documents)
            for (String word : document.words)
                if (!stopList.containsWord(word))
                {
                    if (!directory.containsKey(word))
                        directory.put(word, new HashSet<>());

                    directory.get(word).add(document);
                }
    }

    /**
     * purpose: add documents to set of documents
     * @param document the document being added to documents
     */
    void addDocument(Document document)
    {
        documents.add(document);
        index(); // O(n^2)
    }

    /**
     * purpose: perform a search on inverted index with a single word
     * @param word the word being searched for in documents
     * @return the document(s) containing the query if found, empty set otherwise
     */
    HashSet<Document> singleWordQuery(String word)
    {
        word = word.toLowerCase();
        HashSet<Document> matchedDocuments = new HashSet<>();

        if (!directory.isEmpty() && !stopList.containsWord(word) && directory.containsKey(word))
            matchedDocuments.addAll(directory.get(word));

        return matchedDocuments;
    }

    /**
     * purpose: perform a search on inverted index for multiple words
     * @param words the words being searched for in documents
     * @return the document(s) containing the query if found, empty set otherwise
     */
    HashSet<Document> multiWordQuery(String words)
    {
        words = words.toLowerCase();
        HashSet<String> queryWords = new HashSet<>();
        HashSet<Document> matchedDocuments = new HashSet<>();
        StringTokenizer stringTokenizer = new StringTokenizer(words);

        while (stringTokenizer.hasMoreTokens())
            queryWords.add(stringTokenizer.nextToken());

        queryWords.removeIf(word -> stopList.containsWord(word));

        for (String word : queryWords)
            if (!directory.isEmpty() && directory.containsKey(word))
                for (Document document : directory.get(word))
                    if (document.words.containsAll(queryWords))
                        matchedDocuments.add(document);

        return matchedDocuments;
    }

    /**
     * purpose: dump current contents of inverted index
     * @return String representation of inverted index contents
     */
    String dump()
    {
        StringBuilder sb = new StringBuilder();
        for (String key : directory.keySet())
            sb.append("\t").append(key).append(": ").append(directory.get(key)).append("\n");

        return "Inverted Index Directory:\n" + sb.toString();
    }
}
