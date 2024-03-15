package utilities;

import java.io.File;

public class FileUtilities {
    public static final String CREATED = "Created";
    public static final String EXISTING = "Existing";
    public static final String EXCEPTION = "Exception";
    public static final String NONEXISTENT = "Non-Existent";
    public static final String DELETED = "Deleted";
    public static final String NOFILES = "No Files";
    public static String createFolder(String path, String folderName){
        try{

            File directory = new File(path,folderName);
            if (! directory.exists()){
                directory.mkdir();
                return CREATED;
            }else{
                return EXISTING;
            }
        }catch(Exception e){
            return EXCEPTION;
        }
    }
    public static String deleteFile(String path){
        try{

            File directory = new File(path);
            if (! directory.exists()){
                 return NONEXISTENT;
            }else{
                File[] dirList = directory.listFiles();
                if(dirList.length > 0){
                    for (File file : dirList) {
                        file.delete();
                    }
                    return DELETED;
                }else{
                    return NOFILES;
                }
            }
        }catch(Exception e){
            return EXCEPTION;
        }
    }

    public static String createFile(String path, String fileName){
        try{
            File file = new File(path,fileName);
            if (file.createNewFile()){
                return CREATED;
            }else{
                return EXISTING;
            }
        }catch(Exception e){
            return EXCEPTION;
        }
    }
}
