package sensorserver.utils;

import com.google.gson.Gson;

import java.io.*;
import java.util.stream.Collectors;

public class FileUtils {
    public String readFile(String path) throws FileNotFoundException {
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        String lines = fileReader.lines().collect(Collectors.joining());
        return lines;
    }

    public <T> T readJsonFile(String path, Class<T> type) throws FileNotFoundException {
        BufferedReader fileReader = new BufferedReader(new FileReader(path));
        T parsedObject = new Gson().fromJson(fileReader, type);
        return parsedObject;
    }

    public void writeToFile(String path, String content) throws IOException {
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(path));
        fileWriter.write(content);
        fileWriter.close();
    }
}
