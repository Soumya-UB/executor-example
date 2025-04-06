import java.util.concurrent.Callable;

public class FileProcessor implements Runnable {

    @Override
    public void run() {
        System.out.println("Starting to process files");
         while (FileAccessData.files.size() > 0) {
            File file = FileAccessData.files.poll();
            System.out.println("Processing new file:");
            System.out.println("Name:" + file.getName());
            System.out.println("isDir:" + file.isDir());
            System.out.println("Size:" + file.getSize());
            System.out.println("Creation time:" + file.getCreatedTime());
            System.out.println("Last update time:" + file.getLastUpdatedTime());
         }
        System.out.println("All files have been processed");
    }
}