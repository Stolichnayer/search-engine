import mitos.stemmer.Stemmer;
import gr.uoc.csd.hy463.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

public class Program
{
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


    public static void main(String[] args) throws Exception
    {
        // TODO: B1) 1. Read tags and content of an XML file
        //           2. Print [number of different words]
        //           3. Print [each different word, [tag1 count1], [tag2 count2], ...]
        //           4. Ignore stop words

        File file = new File("resources\\Collections\\MiniCollection\\diagnosis\\Topic_1\\0\\1852545.nxml");

        NXMLFileReader xmlFile = new NXMLFileReader(file);
        String pmcid = xmlFile.getPMCID();
        String title = xmlFile.getTitle();
        String abstr = xmlFile.getAbstr();
        String body = xmlFile.getBody();
        String journal = xmlFile.getJournal();
        String publisher = xmlFile.getPublisher();
        ArrayList<String> authors = xmlFile.getAuthors();
        HashSet<String> categories = xmlFile.getCategories();

/*        System.out.println("- PMC ID: " + pmcid);
        System.out.println("- Title: " + title);
        System.out.println("- Abstract: " + abstr);
        System.out.println("- Body: " + body);
        System.out.println("- Journal: " + journal);
        System.out.println("- Publisher: " + publisher);
        System.out.println("- Authors: " + authors);
        System.out.println("- Categories: " + categories);*/

        ArrayList<String> stopwordsEn = readStopwords("resources\\Stoplists\\stopwordsEn.txt");



    }
}
