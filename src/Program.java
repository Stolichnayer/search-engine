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

    public static void writeVocabularyFile()
    {
        // Create CollectionIndex directory
        createDirectory("CollectionIndex");

        RandomAccessFile file;
        try
        {
            file = new RandomAccessFile("CollectionIndex\\VocabularyFile.txt", "rw");

            for (String word : uniqueWords.keySet())
            {
                String row = word + " " + uniqueWords.get(word).size() + "\n";
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

        // Save
        addUniqueWordsForEveryFile();

        // Create VocabularyFile.txt
        writeVocabularyFile();

        // Create DocumentsFile.txt
        writeDocumentsFile();


        // Print the number of unique words
        System.out.println("\nNumber of unique words: " + uniqueWords.size());

        // Print the time elapsed in seconds
        System.out.println("\nTime elapsed for indexing: " + (currentTimeMillis() - startTime) / 1000.0 + " seconds.");
    }
}

