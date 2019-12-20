import org.junit.Test;
import java.util.HashSet;
import static org.junit.Assert.*;

/**
 * This is my code! Its goal is to test the search engine
 * CS 312 - Assignment 9
 * @author Jacob Greenberg
 * @version 1.1 12-7-19
 */

public class Tester
{
    public Tester()
    {
    }

    @Test
    public void StopList()
    {
        StopList stopList = new StopList("testing/stoplist");
        assertEquals("the, with, and, i ", stopList.toString());
    }

    @Test
    public void Document()
    {
        Document document = new Document("testing/doc1");
        assertEquals("doc1", document.toString());
    }

    @Test
    public void isAStopWord()
    {
        StopList stopList = new StopList("testing/stoplist");
        assertTrue(stopList.containsWord("THE"));
    }

    @Test
    public void isNotAStopWord()
    {
        StopList stopList = new StopList("testing/stoplist");
        assertFalse(stopList.containsWord("dog"));
    }

    @Test
    public void documentDisplay()
    {
        Document document = new Document("testing/doc1");
        assertEquals("doc1:\ngo happy the; tom tom happy ", document.display());
    }

    @Test
    public void singleWordQuery()
    {
        InvertedIndex invertedIndex = new InvertedIndex("testing/stoplist");
        Document document = new Document("testing/doc1");
        invertedIndex.addDocument(document);
        HashSet<Document> matched = invertedIndex.singleWordQuery("TOM");
        assertEquals("[doc1]", matched.toString());
    }

    @Test
    public void dump()
    {
        InvertedIndex invertedIndex = new InvertedIndex("testing/stoplist");
        Document document1 = new Document("testing/doc1");
        Document document2 = new Document("testing/doc2");
        invertedIndex.addDocument(document1);
        invertedIndex.addDocument(document2);
        assertEquals("Inverted Index Directory:\n\ttom: [doc1]\n\thappy: [doc1, doc2]\n\tfish: [doc2]" +
                "\n\tgo: [doc1]\n", invertedIndex.dump());
    }

    @Test
    public void multiWordQuery()
    {
        InvertedIndex invertedIndex = new InvertedIndex("testing/stoplist");
        Document document1 = new Document("testing/doc1");
        Document document2 = new Document("testing/doc2");
        Document document3 = new Document("testing/doc3");
        invertedIndex.addDocument(document1);
        invertedIndex.addDocument(document2);
        invertedIndex.addDocument(document3);
        HashSet<Document> matched = invertedIndex.multiWordQuery("happy fish");

        assertEquals("[doc2, doc3]", matched.toString());
    }

        @Test
        public void singleWordQueryMultiDocument()
        {
            InvertedIndex invertedIndex = new InvertedIndex("testing/stoplist");
            Document document1 = new Document("testing/doc1");
            Document document2 = new Document("testing/doc2");
            invertedIndex.addDocument(document1);
            invertedIndex.addDocument(document2);
            HashSet<Document> matched = invertedIndex.singleWordQuery("HAPPY");
            assertEquals("[doc2, doc1]", matched.toString());
    }
}
