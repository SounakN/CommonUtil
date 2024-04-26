package utilities;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileUtilities {
    public static final String CREATED = "Created";
    public static final String EXISTING = "Existing";
    public static final String EXCEPTION = "Exception";
    public static final String NONEXISTENT = "Non-Existent";
    public static final String DELETED = "Deleted";
    public static final String NO_FILES = "No Files";

    public static String createFolder(String folderName) {
        try {
            Path pathRoot = new File(Objects.requireNonNull(FileUtilities.class.getClassLoader().getResource("")).getFile()).toPath().getParent().getParent();
            Path path = Paths.get(pathRoot.toString(), "src/test/resources", folderName);
            if (Files.exists(path)) {
                return EXISTING;
            } else {
                Files.createDirectory(path);
                return CREATED;
            }
        } catch (Exception e) {
            return EXCEPTION;
        }
    }

    public static String deleteFile(String path) {
        try {
            File directory = new File(path);
            if (!directory.exists()) {
                return NONEXISTENT;
            } else {
                File[] dirList = directory.listFiles();
                if (dirList.length > 0) {
                    for (File file : dirList) {
                        file.delete();
                    }
                    return DELETED;
                } else {
                    return NO_FILES;
                }
            }
        } catch (Exception e) {
            return EXCEPTION;
        }
    }

    public static String createFile(String path, String fileName) {
        try {
            File file = new File(path, fileName);
            if (file.createNewFile()) {
                return CREATED;
            } else {
                return EXISTING;
            }
        } catch (Exception e) {
            return EXCEPTION;
        }
    }
}
