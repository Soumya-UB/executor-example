import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FileProcessor implements Runnable {
    private ExecutorService executorService;
    private int numThread = 5;
    private Listener listener;
    private boolean allDone = false;

    public void registerEvent(Listener listener) {
        this.listener = listener;
    }
    @Override
    public void run() {
        System.out.println("Starting to process files");
        List<Future> fileDeletionFutures = new ArrayList<>();
        registerEvent(new ListenerImpl());
        executorService = Executors.newFixedThreadPool(numThread);
         while (FileAccessData.files.size() > 0) {
            File file = FileAccessData.files.poll();
            System.out.println("Processing new file:");
            System.out.println("Name:" + file.getName());
            System.out.println("isDir:" + file.isDir());
            System.out.println("Size:" + file.getSize());
            System.out.println("Creation time:" + file.getCreatedTime());
            System.out.println("Last update time:" + file.getLastUpdatedTime());
            FileAccessData.deleteQueue.add(file);
            fileDeletionFutures.add(executorService.submit(() -> this.listener.onEvent()));
         }
         while (fileDeletionFutures.stream().anyMatch(e -> !e.isDone())) {
             System.out.println("Task is still running...");
             try {
                 Thread.sleep(500);
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
         }
         System.out.println("All files have been processed");
         executorService.shutdown();
    }
}