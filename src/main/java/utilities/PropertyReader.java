package utilities;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class PropertyReader {


    @SneakyThrows
    public static Properties getProperties(String fileName) {
        Properties props = new Properties();
        try {
            InputStream fs = PropertyReader.class.getClassLoader().getResourceAsStream(fileName);
            props.load(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    @SneakyThrows
    public static Properties loadAllProperties(String rootEnv) {
        Properties props = new Properties();
        Path path = new File(Objects.requireNonNull(PropertyReader.class.getClassLoader().getResource("")).getFile()).toPath();
        Files.list(path).filter(rootFile -> rootFile.getFileName().toString().endsWith(".properties")).forEach(file -> {
            props.putAll(getProperties(String.valueOf(file.getFileName())));
        });

        if (rootEnv != null) {
            if (Files.exists(Paths.get(new File(Objects.requireNonNull(PropertyReader.class.getClassLoader().getResource("")).getFile()).toString(), rootEnv))) {
                Files.list(Files.list(path).filter(rootFile -> rootFile.getFileName().toString().equals(rootEnv)).findFirst().get()).filter(childFile -> childFile.getFileName().toString().endsWith(".properties")).forEach(file -> {
                    props.putAll(getProperties(rootEnv + File.separator + file.getFileName()));
                });
            } else {
                log.info("The Environment folder does not exists");
            }
        } else {
            log.info("The Environment folder value has been sent as null");
        }
        return props;
    }
}