import java.util.ArrayList;
import java.util.Collections;

public class Evaluation
{
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
