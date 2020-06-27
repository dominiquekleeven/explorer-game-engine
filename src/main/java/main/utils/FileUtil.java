package main.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {

    private FileUtil() {
    }

    public static String loadAsString(String path){
        StringBuilder result = new StringBuilder();

        System.out.println("Reading file from path: " + path);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtil.class.getResourceAsStream(path)))){
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                result.append(line).append("\n");
            }

        } catch (IOException exc)
        {
            System.err.println(path + " file Path Not Found: " + exc);
        }


        return result.toString();
    }
}
