import gr.uoc.csd.hy463.*;
import java.io.*;
import java.util.*;

import static java.lang.System.currentTimeMillis;

public class Program
{
    public static ArrayList<String> stopwordsEn;
    public static ArrayList<String> stopwordsGr;
    public static HashMap<String, HashMap<String, HashMap<String, Integer>>> uniqueWords = new HashMap<>();

    // HashMap = { key: file ID, value: file Path }
    public static HashMap<String, String> files = new HashMap<>();

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
                int value = tagCount != null? (int)tagCount : 0;
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

    public static void AddUniqueWordsForEveryFile() throws IOException
    {
        // Add unique words for every tag in every file
        for (String key : files.keySet())
        {
            File file = new File(files.get(key));
            NXMLFileReader xmlFile = new NXMLFileReader(file);

            String pmcid = xmlFile.getPMCID();
            String title = xmlFile.getTitle();
            String abstr = xmlFile.getAbstr();
            String body = xmlFile.getBody();
            String journal = xmlFile.getJournal();
            String publisher = xmlFile.getPublisher();
            ArrayList<String> authors = xmlFile.getAuthors();
            HashSet<String> categories = xmlFile.getCategories();


            // add unique words for every tag
            addUniqueWordsByTagAndFile(title, key,"title");
            addUniqueWordsByTagAndFile(abstr, key,"abstract");
            addUniqueWordsByTagAndFile(body, key,"body");
            addUniqueWordsByTagAndFile(journal, key,"journal");
            addUniqueWordsByTagAndFile(publisher, key,"publisher");

            for (String author : authors)
            {
                addUniqueWordsByTagAndFile(author, key,"authors");
            }

            for (String category : categories)
            {
                addUniqueWordsByTagAndFile(category, key,"categories");
            }
        }

        for (String str : uniqueWords.keySet())
        {
            String value = uniqueWords.get(str).toString();

            System.out.println(str + " " + value);
        }
    }

    public static void main(String[] args) throws Exception
    {
        // TODO: B1) 1. Read tags and content of an XML file
        //           2. Print [number of different words]
        //           3. Print [each different word, [tag1 count1], [tag2 count2], ...]
        //           4. Ignore stop words
        //  DONE

        // TODO: B2) 1. Read all files in a directory
        //           2. Print [word, [tag1 count1], [tag2 count2], ...]


        // Load stopwords from file to memory
        stopwordsEn = readStopwords("resources\\Stoplists\\stopwordsEn.txt");
        stopwordsGr = readStopwords("resources\\Stoplists\\stopwordsGr.txt");

        // Get all files recursively in all subfolders
        getAllFilesInFolder(new File("resources\\Collections"));

        // Start counting time
        var startTime = currentTimeMillis();

        AddUniqueWordsForEveryFile();

        System.out.println("\nNumber of unique words: " + uniqueWords.size());

        System.out.println((currentTimeMillis() - startTime) / 1000.0 + " seconds");


    }
}
