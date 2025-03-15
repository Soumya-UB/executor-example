import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

public class FileWatcher implements Callable<Map<String, File>> {

    @Override
    public Map<String, File> call() {
        System.out.println("Started watching for files");
        Map<String, File> fileMap = new HashMap<>();
        try (Stream<Path> files = Files.list(Paths.get(FileAccessData.filePath))) {
            files.forEach(path -> {
                BasicFileAttributes attributes = null;
                try {
                    attributes = Files.readAttributes(path, BasicFileAttributes.class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                fileMap.put(path.toString(),
                        File.builder()
                                .name(path.toString())
                                .size(attributes.size())
                                .createdTime(attributes.creationTime())
                                .lastUpdatedTime(attributes.lastModifiedTime())
                                .isDir(attributes.isDirectory())
                                .build());
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileMap;
    }
}