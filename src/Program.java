import gr.uoc.csd.hy463.*;
import mitos.stemmer.Stemmer;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

import static java.lang.System.currentTimeMillis;

public class Program
{
    // English and Greek stopwords saved in memory as ArrayLists
    public static ArrayList<String> stopwordsEn;
    public static ArrayList<String> stopwordsGr;

    // TreeMap to save unique words, sorted lexicographically by word
    public static TreeMap<String, HashMap<String, HashMap<String, Integer>>> uniqueWords = new TreeMap<>();

    // Save all .nxml files in a TreeMap = { key: file ID, value: file Path }, sorted lexicographically by fileID
    public static TreeMap<String, String> files = new TreeMap<>();

    // HashMap {key: file ID, value: content} to reduce IO bound
    public static HashMap<String, String> fileCache = new HashMap<>();


    // Functions
    public static ArrayList<String> readStopwords(String path)
    {
        BufferedReader reader;
        ArrayList<String> words = new ArrayList<>();
        try
        {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null)
            {
                words.add(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return words;
    }

    public static void addUniqueWordsByTagAndFile(String text, String fileID, String tag)
    {
        String delimiter = "\t\n\r\f ";

        if (text == null || text.equals(""))
            return;

        StringTokenizer tokenizer = new StringTokenizer(text, delimiter);

        while (tokenizer.hasMoreTokens())
        {
            // Remove punctuation marks and transform to lowercase
            String currentToken = tokenizer.nextToken().replaceAll("[^a-zA-Z0-9]", "").toLowerCase(Locale.ROOT);

            // Stemming the word
            currentToken = Stemmer.Stem(currentToken);

            // Check if current token is blank or a stopword
            if (currentToken.equals("") || stopwordsEn.contains(currentToken))
                continue;

            if (uniqueWords.containsKey(currentToken))
            {
                var fileMap = uniqueWords.get(currentToken);
                var tagsMap = fileMap.get(fileID);

                if (tagsMap == null)
                {
                    tagsMap = new HashMap<>();
                }

                Object tagCount = tagsMap.get(tag);
                int value = tagCount != null ? (int) tagCount : 0;
                tagsMap.put(tag, (value + 1));
                fileMap.put(fileID, tagsMap);
                uniqueWords.replace(currentToken, fileMap);
            }
            else
            {
                //                      K: fileID,   V: { K: tag, V: count }
                var fileMap = new HashMap<String, HashMap<String, Integer>>();
                var tagsMap = new HashMap<String, Integer>();
                tagsMap.put(tag, 1);
                fileMap.put(fileID, tagsMap);
                uniqueWords.put(currentToken, fileMap);
            }

        }

    }

    public static void getAllFilesInFolder(File folder)
    {
        for (File fileEntry : Objects.requireNonNull(folder.listFiles()))
        {
            if (fileEntry.isDirectory())
            {
                getAllFilesInFolder(fileEntry);
            }
            else
            {
                // Add file path in files HashMap
                files.put(fileEntry.getName(), fileEntry.getPath());
            }
        }
    }

    public static void addUniqueWordsForEveryFile() throws IOException
    {
        // Add unique words for every tag in every file
        for (String key : files.keySet())
        {
            File file = new File(files.get(key));
            NXMLFileReader xmlFile = new NXMLFileReader(file);

            // String pmcid = xmlFile.getPMCID();
            String title = xmlFile.getTitle();
            String abstr = xmlFile.getAbstr();
            String body = xmlFile.getBody();
            String journal = xmlFile.getJournal();
            String publisher = xmlFile.getPublisher();
            ArrayList<String> authors = xmlFile.getAuthors();
            HashSet<String> categories = xmlFile.getCategories();

            // add unique words for every tag
            addUniqueWordsByTagAndFile(title, key, "title");
            addUniqueWordsByTagAndFile(abstr, key, "abstract");
            addUniqueWordsByTagAndFile(body, key, "body");
            addUniqueWordsByTagAndFile(journal, key, "journal");
            addUniqueWordsByTagAndFile(publisher, key, "publisher");

            for (String author : authors)
            {
                addUniqueWordsByTagAndFile(author, key, "authors");
            }

            for (String category : categories)
            {
                addUniqueWordsByTagAndFile(category, key, "categories");
            }
        }

        for (String str : uniqueWords.keySet())
        {
            String value = uniqueWords.get(str).toString();

            System.out.println(str + " " + value);
        }
    }

    public static void loadFilesToMemory(int limit) throws IOException
    {
        int size = 0 ;
        for (String fileID : files.keySet())
        {
            // Open file from fileID (path of file in files Map)
            File file = new File(files.get(fileID));

            // Read file
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            StringBuilder content = new StringBuilder();
            String line;


            while ((line = in.readLine()) != null)
            {
                content.append(line.toLowerCase());
            }

            // Put pair in hashmap
            fileCache.put(fileID, content.toString());

            // Update size
            size += fileID.length() + content.length();

            // Break if limit of file size reaches (MB to Bytes)
            if (size >= limit * 1024 * 1024)
            {
                System.out.println("LIMIT" + size);
                return;
            }
        }

    }

    public static void createDirectory(String dirName)
    {
        // Create directory CollectionIndex
        File theDir = new File(dirName);
        if (!theDir.exists())
        {
            if (theDir.mkdirs())
            {
                System.out.println("\nDirectory " + dirName + " was created successfully.");
            }
            else
            {
                System.out.println("\nDirectory " + dirName + " could not be created.");
            }
        }
        else
        {
            System.out.println("\nDirectory " + dirName + " already exists, no need to create it again.");
        }
    }

    public static void writeVocabularyFile(ArrayList<Integer> wordPositions)
    {
        RandomAccessFile file;
        try
        {
            file = new RandomAccessFile("CollectionIndex\\VocabularyFile.txt", "rw");

            int termNum = 0;
            for (String word : uniqueWords.keySet())
            {
                // Calculate document frequency
                int df = uniqueWords.get(word).size();

                // Get word position (pointer) in PostingFile.txt --> O(1) complexity
                int pointer = wordPositions.get(termNum++);

                // Construct row
                String row = word + " " + df + " " + pointer + "\n";

                // Write row to file
                file.writeBytes(row);
            }

            file.close();

            System.out.println("\nVocabularyFile.txt was created successfully.");
        }
        catch (Exception e)
        {
            System.out.println("\nVocabularyFile.txt could not be created.");
            //e.printStackTrace();
        }
    }

    public static void writeDocumentsFile()
    {
        RandomAccessFile file;
        try
        {
            file = new RandomAccessFile("CollectionIndex\\DocumentsFile.txt", "rw");

            for (String fileID : files.keySet())
            {
                String filePath = Path.of(".").toRealPath() + "\\" + files.get(fileID);
                float vecLength = Evaluation.getVectorLength(fileID);

                String row = fileID + " " + filePath + " " + vecLength + "\n";
                file.writeBytes(row);
            }

            file.close();

            System.out.println("\nDocumentsFile.txt was created successfully.");
        }
        catch (Exception e)
        {
            System.out.println("\nDocumentsFile.txt could not be created.");
            //e.printStackTrace();
        }
    }

    public static ArrayList<Integer> writePostingFile() throws IOException
    {
        // Open file for write
        RandomAccessFile file;
        try
        {
            file = new RandomAccessFile("CollectionIndex\\PostingFile.txt", "rw");
            System.out.println("\nPostingFile.txt was created successfully.");
        }
        catch (Exception e)
        {
            System.out.println("\nPostingFile.txt could not be created.");
            // e.printStackTrace();
            return null;
        }

        // Get a map with max frequencies of all files
        var maxFreqs = Evaluation.calculateMaxFreqs();

        // Create arraylist of every term row position to use as pointer in VocabularyFile.txt
        var vocabularyPointers = new ArrayList<Integer>();

        // Initial position
        int termPosition = 1;

        // For every word in uniqueWords map
        for (String word : uniqueWords.keySet())
        {
            // Add term position to ArrayList to use it in VocabularyFile.txt
            vocabularyPointers.add(termPosition);

            // For every file that word exists in
            for (String fileID : uniqueWords.get(word).keySet())
            {
                // Calculate term frequency of term = word in file = fileID
                int freq = Evaluation.calculateFrequency(word, fileID);
                int maxFreq = maxFreqs.get(fileID);
                float tf = (float)freq / (float)maxFreq;

                var termPositionsInFile = calculateTermPositions(fileID, Stemmer.Stem(word));
                String row = word + " " + fileID + " " + tf + " " + termPositionsInFile + "\n";
                file.writeBytes(row);

                // Increase term position by 1 for every document
                termPosition++;
            }

            // Split words (terms)
            //file.writeBytes("------------------------------------------\n");
        }

        // Close file
        file.close();

        return vocabularyPointers;
    }

    public static ArrayList<Integer> calculateTermPositions(String fileID, String term) throws IOException
    {
        // Open file from fileID (path of file in files Map)
        File file = new File(files.get(fileID));

        StringBuilder content = new StringBuilder();

        // Check if file already exists in our cache (SUPER FAST!)
        if (fileCache.containsKey(fileID))
        {
            content.append(fileCache.get(fileID));
        }
        else // Old slow boring way
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            String line;
            while ((line = in.readLine()) != null)
            {
                content.append(line.toLowerCase());
            }

            in.close();
        }


        var positions = new ArrayList<Integer>();

        // Find all occurrences of word
        int previous = 0;
        for (int i = -1; (i = content.indexOf(term, i + 1)) != -1; i++)
        {
            positions.add(i + 1 - previous);
            previous = i + 1;
        }

        return positions;
    }

    public static void main(String[] args) throws Exception
    {
        // TODO (DONE) : B1) 1. Read tags and content of an XML file
        //                   2. Print [number of different words]
        //                   3. Print [each different word, [tag1 count1], [tag2 count2], ...]
        //                   4. Ignore stop words

        // TODO (DONE): B2) 1. Read all files in a directory
        //                  2. Print [word, [tag1 count1], [tag2 count2], ...]

        // TODO (DONE)  B3) 1. Stemming

        // TODO (DONE)  B4) 1. Create CollectionIndex folder
        //                  2. Create VocabularyFile.txt [word, document frequency (df)]

        // TODO (DONE)  B5) 1. Create DocumentsFile.txt [fileID, filePath, vector length (norma)]


        // Load stopwords from file to memory
        stopwordsEn = readStopwords("resources\\Stoplists\\stopwordsEn.txt");
        stopwordsGr = readStopwords("resources\\Stoplists\\stopwordsGr.txt");

        // Get all files recursively in all subfolders
        getAllFilesInFolder(new File("resources\\Collections"));

        // Initialize stemmer
        Stemmer.Initialize();

        // Start counting time
        var startTime = currentTimeMillis();

        // Save unique words
        addUniqueWordsForEveryFile();

        // Create file cache (limit in MB) TODO: GUI enter memory limit
        loadFilesToMemory(6);

        // Create CollectionIndex directory
        createDirectory("CollectionIndex");

        // Create PostingFile.txt
        var termPositions = writePostingFile();

        // Create VocabularyFile.txt
        writeVocabularyFile(termPositions);

        // Create DocumentsFile.txt
        writeDocumentsFile();

        // Print the number of unique words
        System.out.println("\nNumber of unique words: " + uniqueWords.size());

        // Print the time elapsed in seconds
        System.out.println("\nTime elapsed for indexing: " + (currentTimeMillis() - startTime) / 1000.0 + " seconds.");
    }
}

