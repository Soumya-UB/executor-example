import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FileAccessData {
    final static String filePath = "files/";
    final static int numInitialFiles = 10;
    static Set<String> existingFiles = new HashSet();
    static BlockingQueue<File> files = new LinkedBlockingQueue<>();
}
