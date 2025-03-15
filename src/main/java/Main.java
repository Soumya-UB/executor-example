import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class main {
    private static final int threadPoolSize = 5;
    private static final int maxRetry = 5;
    private static ExecutorService executorService;
    //TODO: Make it a producer consumer problem. Create a new class FileCreator to create the files.
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        BlockingQueue<File> files = new LinkedBlockingQueue<>();
        AtomicInteger fileCount = new AtomicInteger();
        List<Future> fileCreatorFutures = new ArrayList<>();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        executorService = Executors.newFixedThreadPool(threadPoolSize);
        // Create a thread that will create the initial files
        Thread thread = new Thread(() -> {
            for (int i = 0 ; i < FileAccessData.numInitialFiles ; i++) {
                createFiles(0);
            }
        });
        thread.start();
    }

    static void createFiles(int attempt) {
        if (attempt == maxRetry) {
            throw new RuntimeException("File couldn't be created after " + maxRetry + " attempts");
        }
        CompletableFuture.runAsync(new FileCreator(), executorService)
            .exceptionally(ex -> {
                System.out.println("File creation failed: " + ex.getMessage());
                createFiles(attempt + 1);
                return null;
            });
    }
}