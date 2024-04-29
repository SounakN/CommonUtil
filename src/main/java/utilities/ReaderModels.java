package utilities;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Objects;


@Slf4j
public class ReaderModels {
    @SneakyThrows
    public static BufferedReader convertFileIntoBufferedReader(String fileName) {
        try {
            return new BufferedReader(
                    new InputStreamReader(Objects.requireNonNull(ReaderModels.class.getClassLoader().getResourceAsStream(fileName))));
        } catch (Exception e) {
            log.info("Failed reading file with an exception  :: " + e.getMessage());
            return null;
        }

    }

    @SneakyThrows
    public static String convertFileToString(String fileName) {
        try (InputStream inputStream = ReaderModels.class.getClassLoader().getResourceAsStream(fileName)) {
            StringWriter stringWriter = new StringWriter();
            int i;
            while ((i = inputStream.read()) != -1) {
                stringWriter.write((char) i);
            }
            return stringWriter.toString();
        } catch (Exception e) {
            log.info("Failed reading file with an exception  :: " + e.getMessage());
            return null;
        }

    }

    @SneakyThrows
    public static String convertFileToJsonString(String fileName) {
        try (InputStream inputStream = ReaderModels.class.getClassLoader().getResourceAsStream(fileName)) {
            return new JSONParser().parse(new InputStreamReader(inputStream)).toString();
        } catch (Exception e) {
            log.info("Failed reading file with an exception  :: " + e.getMessage());
            return null;
        }

    }
}
