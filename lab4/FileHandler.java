package lab4;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class FileHandler {

    public static void writeToFile(List<String> list, String filename) {
        try {
            FileWriter file = new FileWriter(filename, true);

            for (String s : list) {
                file.write(s);
            }

            file.close();
        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }

    }

    public static void createFile(String filename) {
            File file = new File(filename);
    }
}
