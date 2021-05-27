package qualityevaluator;

import com.sun.source.tree.Tree;
import evaluator.Search;
import gr.uoc.csd.hy463.Topic;
import gr.uoc.csd.hy463.TopicsReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class IRQualityEvaluator
{
    // HashMap to store qrels.txt information
    //                    K: TopicNum  V: { K: fileID, V: relevance number (0, 1, 2) }
    public static HashMap<Integer, LinkedHashMap<String, Integer>> qrels = new HashMap<>();

    public static void insertIntoHashMap(int topicNum, String fileID, int relevance)
    {
        LinkedHashMap<String, Integer> value;

        if (!qrels.containsKey(topicNum))
        {
            value = new LinkedHashMap<>();
        }
        else
        {
            value = qrels.get(topicNum);
        }

        value.put(fileID, relevance);
        qrels.put(topicNum, value);
    }

    public static void loadQrelsFile() throws IOException
    {
        List<String> allLines = Files.readAllLines(Paths.get("qrels.txt"));

        for (String line : allLines)
        {
            // Split lines by space or spaces (inc tabs)
            String[] splitLine = line.split("\\s+");
            int topicNum = Integer.parseInt(splitLine[0]);
            String fileID = splitLine[2];
            int relevance = Integer.parseInt(splitLine[3]);

            insertIntoHashMap(topicNum, fileID, relevance);
        }
    }

    public static void readTopicsAndSearch() throws Exception
    {
        Search.Initialize();

        FileOutputStream fos = new FileOutputStream("results.txt", false);

        ArrayList<Topic> topics = TopicsReader.readTopics("topics.xml");
        for (Topic topic : topics)
        {
            int topicNum = topic.getNumber();
            String topicType = String.valueOf(topic.getType());
            String topicSummary = topic.getSummary();
            String topicDescription = topic.getDescription();

            var results = Search.search(topicSummary);

            if (results != null)
            {
                int rank = 1;
                for (String doc : results.keySet())
                {
                    String row = topicNum + " 0 " + doc.substring(0, doc.length() - 5) + " "  + rank++ + " " + String.format("%,5f", results.get(doc)) + " SUMMARY_STD_NO_WEIGHTING\n";
                    System.out.println(row);
                    fos.write(row.getBytes(StandardCharsets.UTF_8));
                }
            }
        }
    }

    public static void writeEvalResultsFile() throws IOException
    {
        loadQrelsFile();
        var bprefs = Metrics.calculateBprefs("results.txt");
        //var avep = Metrics.calculateAveps("results.txt");

        FileOutputStream fos = new FileOutputStream("eval_results.txt", false);

        for (int topicNum : bprefs.keySet())
        {
            String row = topicNum + "\t" + bprefs.get(topicNum) + "\n";
            fos.write(row.getBytes(StandardCharsets.UTF_8));
        }

        fos.close();
    }

    public static void main(String[] args) throws Exception
    {
        readTopicsAndSearch();
        writeEvalResultsFile();

        System.out.println("Finished Quality evaluation.");
    }
}
