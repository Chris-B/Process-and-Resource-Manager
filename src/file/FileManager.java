package file;

import java.io.*;

public class FileManager {

    public static boolean fromFile = true;

    static String iFile = "E:/input.txt";
    static String oFile = "E:/output.txt";

    public static BufferedReader reader;
    public static BufferedWriter writer;

    static {
        try {
            reader = new BufferedReader(fromFile ? new FileReader(iFile) : new InputStreamReader(System.in));
            writer = new BufferedWriter(new FileWriter(oFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write(String text) {
        if(!FileManager.fromFile)
            System.out.print(text);
        else {
            try {
                writer.write(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close() {
        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
