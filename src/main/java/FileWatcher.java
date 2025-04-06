import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

public class FileWatcher implements Runnable {
    @Override
    public void run() {
        System.out.println("Started watching for files");
        try (Stream<Path> files = Files.list(Paths.get(FileAccessData.filePath))) {
            files.forEach(path -> {
                if (!FileAccessData.existingFiles.contains(path.toString())) {
                    BasicFileAttributes attributes;
                    try {
                        attributes = Files.readAttributes(path, BasicFileAttributes.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    FileAccessData.files.add(File.builder()
                            .name(path.toString())
                            .size(attributes.size())
                            .createdTime(attributes.creationTime())
                            .lastUpdatedTime(attributes.lastModifiedTime())
                            .isDir(attributes.isDirectory())
                            .build());
                    FileAccessData.existingFiles.add(path.toString());
                } else {
                    System.out.println("File " + path + " has already been added to the queue");
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}