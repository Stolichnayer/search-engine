import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

public class Evaluation
{
    public static int calculateFrequency(String word, String fileID)
    {
        var words = Program.uniqueWords;
        var wordFiles = words.get(word);
        var tags = wordFiles.get(fileID);

        int sum = 0;

        // if fileID key exists in value HashMap of words HashMap
        if (tags != null)
        {
            // Sum every count of word in tags
            for (int count : tags.values())
            {
                sum += count;
            }
        }

        return sum;
    }

    public static HashMap<String, Integer> calculateMaxFreqs()
    {
        var files = Program.files;
        var words = Program.uniqueWords;

        var maxFreqs = new HashMap<String, Integer>();

        // For every file
        String aa = "";
        for (String fileID : files.keySet())
        {
            int maxFreq = 0;

            // For every word
            for (String word : words.keySet())
            {
                int freq = calculateFrequency(word, fileID);

                // Check for max freq
                if (freq > maxFreq)
                {
                    maxFreq = freq;
                    aa = word;
                }

            }

            maxFreqs.put(fileID, maxFreq);
            // System.out.println(fileID + ", " + maxFreq + " " + aa);
        }

        return maxFreqs;
    }

    public static HashMap<String, Integer> getTFs(String word, TreeMap<String, HashMap<String, HashMap<String, Integer>>> words)
    {
        // For every word: HashMap<fileID, frequency>
        HashMap<String, Integer> freqs = new HashMap<>();



        return freqs;
    }

    public static float getIDF(String word)
    {
        float df = Program.uniqueWords.get(word).size();
        float docNum = Program.files.size();
        //System.out.println(docNum);
        //System.out.println("df = " + df + ", N / df = " + docNum / df + ", log2(N/df) = " + (float) (Math.log(docNum / df) / Math.log(2)));
        return (float) (Math.log(docNum / df) / Math.log(2));
    }

    public static float getVectorLength(String fileID)
    {
        ArrayList<Float> freqs = new ArrayList<>();

        // For every word in the TreeMap uniqueWords
        for (String word : Program.uniqueWords.keySet())
        {
            // Search if HashMap of fileID exists in values of uniqueWords and get its HashMap value
            var tags = Program.uniqueWords.get(word).get(fileID);

            // In case we find the file, term exists in this file
            if (tags != null)
            {
                // for every tag in the HashMap<tag, count>
                float sum = 0;
                for (String tag : tags.keySet())
                {
                    // Add to sum the tag count
                    sum += tags.get(tag);
                }

                freqs.add(sum * getIDF(word));
            }
        }

        // Get the max term frequency
        // TODO: maybe wrong!!!!!!!!!!!!!!!!!!!!!!!!!!!
        float max_freq = Collections.max(freqs);

        float sum = 0;

        // For every frequency in the ArrayList
        for (float freq : freqs)
        {
            // add (freq * idf / max_freq) squared
            sum += Math.pow(freq / max_freq, 2);
        }

        // Return the length of the vector (square root of the sum of squared (freq / max_freq))
        return (float) Math.sqrt(sum);
    }

}
