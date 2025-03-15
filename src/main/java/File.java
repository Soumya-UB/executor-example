import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.attribute.FileTime;

@Getter
@Setter
@Builder
public class File {
    private String name;
    private long size;
    private FileTime createdTime;
    private FileTime lastUpdatedTime;
    private boolean isDir;
}
