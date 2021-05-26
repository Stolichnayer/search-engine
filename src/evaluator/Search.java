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
    public static HashMap<String, Long> currentWordPosition = new HashMap<>();

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

            //System.out.println("vocabulary size: " + vocabulary.size());

            int a = 0;
            for (String word : vocabulary.keySet())
            {
                //System.out.println("Word: " + a++ + "of " + vocabulary.size());

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
                    //System.out.println(i + " of " + df);

                    int index =  section.indexOf('\n');

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

                    String[] splitLine = line.split(" ");
                    String fileName = splitLine[0];
                    float tf = Float.parseFloat(splitLine[1]);

                    //TODO: powo splitLine[1] --> splitLine[0]
                    if (docs.containsKey(fileName))
                    {
                        // Add TF to the vector
                        // TODO: powo splitLine[2] --> splitLine[1]
                        float idf = (float)(Math.log((float)docNum / df) / Math.log(2));

                        // Add tf idf weight to vector
                        // TODO: powo splitLine[1] --> splitLine[0]
                        var value = docVector.get(fileName);
                        value.add(tf * idf);

                        // TODO: powo splitLine[1] --> splitLine[0]
                        flagMap.put(fileName, true);

                        // Saving position of word in file
                        long position;
                        try
                        {
                            position = Long.parseLong(splitLine[2].replaceAll("[^\\d.]", ""));
                        }
                        catch (Exception e)
                        {
                            position = 0;
                        }
                        // Add word position to map
                        currentWordPosition.put(fileName, position);
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
        //System.out.println("Starting rateDocs");
        // LinkedHashMap keeps the order of insertion
        //                                 docID, score
        var ratedDocs = new LinkedHashMap<String, Float>();

        printCurrentMemory();

        // Calculate the query vector
        var queryVector = calculateQueryVector(query);

        //System.out.println("Start calculateDocVector(docs)...");
        // Calculate vector of the documents found
        var docVectorMap = calculateDocVector(docs);

        //System.out.println("Finished calculateDocVector(docs)");

        //System.out.println("Starting doc : docVectorMap..size: " + docVectorMap.size());

        int a = 0;
        for (String doc : docVectorMap.keySet())
        {
            //System.out.println(a++);

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

        //System.out.println("Finishing rateDocs...starting sorting");

        // Sort documents by score (sort HashMap by value)
        return ratedDocs.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e2, LinkedHashMap::new));

    }

    public static void printCurrentMemory()
    {
        long memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        //System.out.println(memory);
    }

    public static LinkedHashMap<String, Float> search(String query)
    {
        String[] fixedQuery = preprocessQuery(query);
        var relevantDocs = new HashMap<String, Float>();

        // Clear temporary file paths hashmap
        currentQueryFilePaths.clear();
        currentWordPosition.clear();

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

                    //TODO: powo
                    String docID = splitLine[0];
                    float tf = Float.parseFloat(splitLine[1]);
                    long documentFilePointer = Long.parseLong(splitLine[splitLine.length - 1]);

                    RandomAccessFile documentsFile = new RandomAccessFile(indexDirectory + "\\DocumentsFile.txt", "r");

                    documentsFile.seek(documentFilePointer);

                    String filePath = documentsFile.readLine().split(" ")[1];

                    relevantDocs.put(docID, tf);

                    // We need this to save paths to display in query results
                    currentQueryFilePaths.put(docID, filePath);
                }

                //System.out.println("Query term");
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

        //System.out.println(relevantDocs);

        //System.out.println("Finished search function, size: " + relevantDocs.size());

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
