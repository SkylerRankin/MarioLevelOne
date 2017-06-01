package utilities;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
    
    private String path;
    
    public ReadFile(String file_path) {
        path = file_path;
    }
    
    private int readLines() throws IOException {
        FileReader file = new FileReader(path);
        BufferedReader bf = new BufferedReader(file);
        String line;
        int lines = 0;
        while ((line = bf.readLine()) != null) lines++;
        bf.close();
        return lines;
    }
    
    public String[] OpenFile() throws IOException {
        FileReader fr = new FileReader(path);
        BufferedReader textReader = new BufferedReader(fr);
        int lines = 0;
        try { lines = readLines(); }
        catch(IOException e) {}
        String[] textData = new String[lines];
        for (int i = 0; i < lines; ++i) {
            textData[i] = textReader.readLine();
        }
        textReader.close();
        return textData;
    }
    
}