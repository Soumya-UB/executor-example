import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class main {
    private static final int threadPoolSize = 5;
    private static final int maxRetry = 10;
    private static ExecutorService executorService;
    public static void main(String[] args) {
        List<CompletableFuture> futures = new ArrayList<>();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        executorService = Executors.newFixedThreadPool(threadPoolSize);
        // Create a thread that will create the initial files
        Thread thread = new Thread(() -> {
            for (int i = 0 ; i < FileAccessData.numInitialFiles ; i++) {
                futures.add(createFiles(0));
            }
        });
        thread.start();
        FileWatcher watcher = new FileWatcher();
        scheduler.scheduleAtFixedRate(watcher, 2, 10, TimeUnit.SECONDS);
        FileProcessor processor = new FileProcessor();
        scheduler.scheduleAtFixedRate(processor, 5, 15, TimeUnit.SECONDS);
        Thread thread1 = new Thread(() -> {
            while (futures.stream().anyMatch(e -> !e.isDone())) {
                System.out.println("Task is still running...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("All files have been created");
            executorService.shutdown();
        });
        thread1.start();
    }

    static CompletableFuture<Void> createFiles(int attempt) {
        if (attempt == maxRetry) {
            throw new RuntimeException("File couldn't be created after " + maxRetry + " attempts");
        }
        return CompletableFuture.runAsync(new FileCreator(), executorService)
                .exceptionally(ex -> {
                 System.out.println("File creation failed: " + ex.getMessage());
                    System.out.println("Sleeping for 1 millisecond");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    createFiles(attempt + 1);
                 return null;
                });
    }
}