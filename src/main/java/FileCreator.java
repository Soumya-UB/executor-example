import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.function.Supplier;

public class FileCreator implements Runnable {

    @Override
    public void run() {
        String timeStamp = Instant.EPOCH.toString();
        Path path = Paths.get(FileAccessData.filePath + "file" + timeStamp + ".txt");
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
