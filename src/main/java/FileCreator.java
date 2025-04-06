import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

public class FileCreator implements Runnable {

    @Override
    public void run() {
        Long epochTime = Instant.now().toEpochMilli();
        Path currentDir = Paths.get("").toAbsolutePath();
        Path filePath = currentDir.resolve(FileAccessData.filePath + "file" + epochTime + ".txt");
        System.out.println("filePath: " + filePath);
        try {
            Files.createFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
