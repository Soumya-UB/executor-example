executor-example is a practice project that utilizes Java ExecutorService to create a multi-threading system. One thread generates new files at a specific location, thus mimicing a producer. 
Another thread keeps polling for files at a repeated interval and when it finds a new files has been created, collects the metadata of the file and pushes to a queue.
Another thread keeps looking at the queue at repeated intervals and wheneven new item has been pushed to the queue, polls it and prints the details of the file, thus mimicing a consumer.
