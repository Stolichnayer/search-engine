package evaluator;

import gui.MainForm;
import mitos.stemmer.Stemmer;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.currentTimeMillis;

public class Search
{
    public static LinkedHashMap<String, Long[]> vocabulary = new LinkedHashMap<>();

    public static String indexDirectory = "CollectionIndex";

    public static int docNum;

    public static HashMap<String, String> currentQueryFilePaths = new HashMap<>();

    public static String[] preprocessQuery(String query)
    {
        var split = query.split(" ");
        var splitList = new ArrayList<String>();

        for (int i = 0; i < split.length; ++i)
        {
            split[i] = Stemmer.Stem(split[i].replaceAll("[^a-zA-Z0-9]", "")).toLowerCase(Locale.ROOT);

            if (vocabulary.containsKey(split[i]))
            {
                splitList.add(split[i]);
            }
        }

        return splitList.toArray(new String[splitList.size()]);
    }

    public static void calculateDocNum()
    {
        try
        {
            Path file = Paths.get(indexDirectory + "\\DocumentsFile.txt");

            // read all lines of the file
            long count = Files.lines(file).count();
            docNum = (int) count;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    e.getClass() + "\n" + e.getMessage(),
                    "Directory Exception",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static ArrayList<Float> calculateQueryVector(String[] query)
    {
        var queryVector = new ArrayList<Float>();

        // Find frequencies in query
        var freqs = new HashMap<String, Integer>();

        for (String word : query)
        {
            if (!freqs.containsKey(word))
            {
                freqs.put(word, 1);
            }
            else
            {
                // If word exists in map, increase the value (freq) by 1
                freqs.put(word, freqs.get(word) + 1);
            }
        }
        // Find max freq
        float maxFreq = Collections.max(freqs.values());

        // Creating vector
        int position = 0;
        for (String word : vocabulary.keySet())
        {
            position++;
            if (!freqs.containsKey(word))
            {
                queryVector.add(0f);
            }
            else
            {
                float df = vocabulary.get(word)[0];
                float idf = (float)(Math.log((float)docNum / df) / Math.log(2));
                float tf = (float)freqs.get(word) / maxFreq;

                float tfIdf = tf * idf;
                queryVector.add(tfIdf);
                System.out.println("position = " + position + ", df = " + df + ", idf = " + idf + ", tf = " + tf + ", tf-idf = " + tfIdf);

            }
        }

        return queryVector;

    }

    public static HashMap<String, ArrayList<Float>> calculateDocVector(HashMap<String, Float> docs)
    {
        var docVector = new HashMap<String, ArrayList<Float>>();
        var flagMap = new HashMap<String, Boolean>();

        var pointers = new long[vocabulary.size()];
        int j = 0;
        for (String word : vocabulary.keySet())
        {
            pointers[j++] = vocabulary.get(word)[1];
        }

        // Initialize hashmap with empty arraylists to prevent checking for null below
        for (String doc : docs.keySet())
        {
            docVector.put(doc, new ArrayList<>());
            flagMap.put(doc, false);
        }

        try
        {
            RandomAccessFile file = new RandomAccessFile(indexDirectory + "\\PostingFile.txt", "r");
            long fileSize = file.length();

            // For every term in vocabulary
            int seek = 0;
            for (String word : vocabulary.keySet())
            {
                // Number of documents that contain the term
                int df = (vocabulary.get(word)[0]).intValue();
                // Pointer to PostingFile.txt
                long pointer = vocabulary.get(word)[1];
                // Go to the specific position
                file.seek(pointer - 1);

                // Read as many lines as the document number
                long diff;
                if (seek >= pointers.length - 1)
                {
                    diff = fileSize - pointers[seek];
                }
                else
                {
                    diff = pointers[seek + 1] - pointers[seek];
                }
                seek++;

                byte[] arr = new byte[(int) (diff)];

                // Read N bytes
                file.readFully(arr);
                String section = new String(arr);

                for (int i = 0; i < df; ++i)
                {
                    int index =  section.indexOf('\n');
                    //System.out.println(" \n\nINDEX = [" + index + "]\n\n");

                    String line = "";
                    if (index != -1)
                    {
                        line = section.substring(0, index + 1);
                        section = section.substring(index + 1);
                    }
                    else
                    {
                        line = section;
                    }

                   // System.out.println(line);
                    //System.out.println("------------------------");

                    String[] splitLine = line.split(" ");

                    //TODO: change this to 0  if remove term in postingfile
                    if (docs.containsKey(splitLine[1]))
                    {
                        // Add TF to the vector
                        // TODO THIS TOO to 1
                        float tf = Float.parseFloat(splitLine[2]);
                        float idf = (float)(Math.log((float)docNum / df) / Math.log(2));

                        // Add tf idf weight to vector
                        var value = docVector.get(splitLine[1]);
                        value.add(tf * idf); //TODO PROSEXE tf idf

                        flagMap.put(splitLine[1], true);
                    }
                }

                // If any of the terms was not found, add zero to vector
                for (String doc : flagMap.keySet())
                {
                    if (!flagMap.get(doc))
                    {
                        var value = docVector.get(doc);
                        value.add(0f);
                    }
                }

                // Re disable flag
                for (String doc : docs.keySet())
                {
                    flagMap.put(doc, false);
                }

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return docVector;

    }

    public static LinkedHashMap<String, Float> rateDocs(HashMap<String, Float> docs, String[] query)
    {
        // LinkedHashMap keeps the order of insertion
        //                                 docID, score
        var ratedDocs = new LinkedHashMap<String, Float>();

        // Calculate the query vector
        var queryVector = calculateQueryVector(query);

        // Calculate vector of the documents found
        var docVectorMap = calculateDocVector(docs);

        for (String doc : docVectorMap.keySet())
        {
            var docVector = docVectorMap.get(doc);

            float scoreNumerator = 0;
            float sum1 = 0;
            float sum2 = 0;

            for (int i = 0; i < queryVector.size(); ++i)
            {
                scoreNumerator += (queryVector.get(i) * docVector.get(i));
                sum1 += Math.pow(queryVector.get(i), 2);
                sum2 += Math.pow(docVector.get(i), 2);
            }
            float scoreDenominator = (float)Math.sqrt(sum1 * sum2);

            float cosSim = scoreNumerator / scoreDenominator;

            // Put docID + score
            ratedDocs.put(doc, cosSim);
        }

        // Sort documents by score (sort HashMap by value)
        return ratedDocs.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e2, LinkedHashMap::new));

    }

    public static LinkedHashMap<String, Float> search(String query)
    {
        String[] fixedQuery = preprocessQuery(query);
        var relevantDocs = new HashMap<String, Float>();

        // Clear temporary file paths hashmap
        currentQueryFilePaths.clear();

        try
        {
            RandomAccessFile file = new RandomAccessFile(indexDirectory + "\\PostingFile.txt", "r");

            for (String str : fixedQuery)
            {
                // Continue if word does not exist in vocabulary
                if (!vocabulary.containsKey(str)) continue;

                long positionInPostingFile = vocabulary.get(str)[1];
                int df = vocabulary.get(str)[0].intValue();

                // Seek where vocabulary pointer points
                file.seek(positionInPostingFile - 1);

                // Read as many lines as the df
                for (int i = 0; i < df; ++i)
                {
                    String[] splitLine = file.readLine().split(" ");

                    //TODO change 1 to 0
                    String docID = splitLine[1];
                    float tf = Float.parseFloat(splitLine[2]);
                    long documentFilePointer = Long.parseLong(splitLine[splitLine.length - 1]);

                    RandomAccessFile documentsFile = new RandomAccessFile(indexDirectory + "\\DocumentsFile.txt", "r");

                    documentsFile.seek(documentFilePointer);

                    String filePath = documentsFile.readLine().split(" ")[1];

                    relevantDocs.put(docID, tf);

                    // We need this to save paths to display in query results
                    currentQueryFilePaths.put(docID, filePath);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    e.getClass() + "\n" + e.getMessage(),
                    "Directory Exception",
                    JOptionPane.ERROR_MESSAGE);
        }

        // If no relevant documents found
        if (relevantDocs.size() == 0) return null;

        // Return relevant docs
        return rateDocs(relevantDocs, fixedQuery);
    }

    public static void loadVocabulary()
    {
       String path = indexDirectory + "\\VocabularyFile.txt";
        try
        {
            List<String> allLines = Files.readAllLines(Paths.get(path));
            for (String line : allLines)
            {
                String[] split = line.split(" ");

                vocabulary.put(split[0], new Long[]{Long.parseLong(split[1]), Long.parseLong(split[2])});
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    e.getClass() + "\n" + e.getMessage(),
                    "Directory Exception",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void Initialize()
    {
        loadVocabulary();
        calculateDocNum();
    }

/*    public static void main(String[] args)
    {

        Initialize();

        var startTime = currentTimeMillis();
        var docs = search("Act@#&$ually acu.iti acu.iti acu.iti acus!on acus!on smoke");
        //var docs = search("zone ziraba ziprasidon");

        for (String doc : docs.keySet())
        {
            System.out.println(doc + " : " + docs.get(doc));

        }

        // Print the time elapsed in seconds
        float elapsedTime = (float) ((currentTimeMillis() - startTime) / 1000.0);
        System.out.println("   Time elapsed:    " + elapsedTime + " seconds.");
    }*/
}