package qualityevaluator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Metrics
{
    // HashMap to insert results.txt values
    //                        K: TopicNum, V: ArrayList<fileIDs ranked>
    public static LinkedHashMap<Integer, ArrayList<String>> results;

    public static void loadResultsFile(String filePath) throws IOException
    {
        if (results != null) return;

        results = new LinkedHashMap<>();

        List<String> allLines = Files.readAllLines(Paths.get(filePath));
        for (String line : allLines)
        {
            String[] splitLine = line.split("\\s+");
            int topicNum = Integer.parseInt(splitLine[0]);
            String fileID = splitLine[2];
            int rank = Integer.parseInt(splitLine[3]);
            float score = Float.parseFloat(splitLine[4]);

            var value = results.containsKey(topicNum) ? results.get(topicNum) : new ArrayList<String>();

            value.add(fileID);
            results.put(topicNum, value);
        }
    }

    public static float calculateBprefByTopicNumber(int topicNum, ArrayList<String> retrievedDocs)
    {
        var allFilesWeHaveInfo = IRQualityEvaluator.qrels.get(topicNum);

        // Non Relevant docs
        var N = (int) allFilesWeHaveInfo.values().stream().filter(v -> v.equals(0)).count();

        // Relevant docs
        var R = allFilesWeHaveInfo.size() - N;

        int nonRelevantCounter = 0;
        int relevantCounter = 0;
        int notJudgedCounter = 0;

        float sum = 0; // of bpref Type
        for (String fileID : retrievedDocs)
        {
            System.out.println(topicNum + ", " + fileID);
            if (!allFilesWeHaveInfo.containsKey(fileID))
            {
                notJudgedCounter++; // Not exists in qrels.txt
            }
            else
            {
                int relevance = allFilesWeHaveInfo.get(fileID);

                if (relevance == 0) // Exists with 0
                {
                    nonRelevantCounter++;
                }
                else // Exists with 1 or 2
                {
                    // We must calculate type only for this case
                    sum += 1.0f - (float)nonRelevantCounter / (float)Math.min(R, N);
                    relevantCounter++;
                }
            }
        }
        //System.out.println("Relevant : " + relevantCounter + ", NonRelevant : " + nonRelevantCounter + ", NotJudged : " + notJudgedCounter);

        // bpref
        return sum / R;
    }

    public static LinkedHashMap<Integer, Float> calculateBprefs(String filePath) throws IOException
    {
        // Read results.txt file
        //
        loadResultsFile(filePath);

        var bprefs = new LinkedHashMap<Integer, Float>();
        for (int topicNum : results.keySet())
        {
            // Get all docs by rank for every topic
            var retrievedDocs = results.get(topicNum);

            float bpref = calculateBprefByTopicNumber(topicNum, retrievedDocs);

            bprefs.put(topicNum, bpref);
        }

        return bprefs;
    }

/*    public static LinkedHashMap<Integer, Float> calculateAveps(String filePath) throws IOException
    {
        // Read results.txt file
        loadResultsFile(filePath);

        var aveps = new LinkedHashMap<Integer, Float>();
    }*/
}
