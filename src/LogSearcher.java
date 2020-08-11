import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class LogSearcher {

    private ArrayList<String> logLines = new ArrayList<>();
    private ArrayList<String> searchResult = new ArrayList<>();
    private String needle;
    private String pathLogFile;
    private String pathOutput;

    public LogSearcher(String pathLogFile, String pathOutput){
        this.pathLogFile = pathLogFile;
        this.pathOutput = pathOutput;
    }


    public void findNeedles(ArrayList<String> needles) {
        LogSearcher logSearcher = this;
        logSearcher.readFile();

        for (String needle : needles) {
            logSearcher.setNeedle(needle);
            logSearcher.findResults();
            logSearcher.writeFile();
        }
    }


    public void setNeedle(String needle) {
        this.needle = needle;
    }


    public void readFile() {
        BufferedReader bufferedReader = null;
        {
            try {
                bufferedReader = new BufferedReader(new FileReader(pathLogFile));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
        {
            String sCurrentLine = null;
            while (true) {
                try {
                    if ((sCurrentLine = bufferedReader.readLine()) == null) break;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                logLines.add(sCurrentLine);
            }

        }
    }

    public void writeFile() {


        File file = null;
        FileWriter writer = null;

        try {
            String fileName = fileNameBasedOnDate();

            file = new File(pathOutput + fileName);

            if (file.createNewFile())
                System.out.println("      File created...");
            else
                System.out.println("      File already exists...");


            writer = new FileWriter(file);

            for (String logLine : searchResult) {
                writer.write(logLine + "\n");
            }

            System.out.println("Search for \""+needle+"\" is finished:\n" +
                    "you can find the results here:\n" + pathOutput + fileName);


            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public String fileNameBasedOnDate(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("y_m_d_HH_mm_ss");
        String dateString = dateFormatter.format(dateTime);
        return dateString+".txt";
    }

    public void findResults() {
        searchResult = new ArrayList<>();
        for (String s : logLines) {
            if (Pattern.matches(".+" + needle + ".+", s)) {
                searchResult.add(s);
            }

        }
    }


    public ArrayList<String> splitStringToArray(String string) {
        String[] stringArray = string.split(",");
        ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(stringArray));
        return stringList;
    }

}
