package GamePckg;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LevelData {

    final static int HEIGHT_IN_BLOCKS = 14;
    final static String LEVEL = "Level1.txt";

    public static String[] LEVEL1 = read(LEVEL);

    /*
     * 	Reading level objects from file.
     */
    public static String[] read(String fileName) {
        String[] lvl = new String[HEIGHT_IN_BLOCKS];
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader in = new BufferedReader(fr);
            try {
                String s;
                int i = 0;
                while ((s = in.readLine()) != null) {
                    lvl[i] = s;
                    i++;
                }
            } finally {
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return lvl;
    }

    public static String[][] levels = new String[][] {
        LEVEL1
    };
}