package testDataModels;

import lombok.Getter;
import lombok.Setter;

import java.io.*;

public class BaseReaderModel {

    public @Getter
    @Setter
    String datapath;
    public @Getter
    @Setter
    String filename;

    public BaseReaderModel() {
        datapath = System.getProperty("user.dir") + File.separator + "src/main/resources/TestData";
    }

    public Reader ReadFile() throws IOException {
        try {
            String pathnameFile = datapath + File.separator + filename + ".json";
            BufferedReader br = new BufferedReader(
                    new FileReader(pathnameFile));
            return br;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return null;
        }

    }
}
