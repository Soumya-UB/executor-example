import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ListenerImpl implements Listener {
    // private File file;

    public ListenerImpl() {
        // this.file = file;
    }
    @Override
    public void onEvent() {
        if (FileAccessData.deleteQueue.size() == 0) {
            System.out.println("Nothing to remove");
            return;
        }
        File file = FileAccessData.deleteQueue.poll();
        System.out.println("Starting to remove file: " + file.getName());
        removeFile(file.getName());
        FileAccessData.existingFiles.remove(file.getName());
    }

    private void removeFile(String filePath) {
        Path currentDir = Paths.get("").toAbsolutePath();
        Path currentPath = currentDir.resolve(filePath);
        try {
            Files.deleteIfExists(currentPath);
            System.out.println("deleted filePath: " + currentPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
