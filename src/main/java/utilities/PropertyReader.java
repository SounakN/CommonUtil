package utilities;

import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

public class PropertyReader {


    // Public method for reading the property
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
    public static Properties loadAllProperties(String env) {
        Properties props = new Properties();
        URL resource = PropertyReader.class.getClassLoader().getResource("");
        Arrays.stream(new File(resource.toURI()).listFiles()).filter(x -> x.getName().endsWith(".properties")).forEach(file -> {
            props.putAll(getProperties(file.getName()));
        });
        Arrays.stream(Arrays.stream(new File(resource.toURI()).listFiles()).filter(x -> x.getName().equals("Dev")).findFirst().get().listFiles()).filter(x -> x.getName().endsWith(".properties")).forEach(file -> {
            props.putAll(getProperties("Dev" + File.separator + file.getName()));
        });
			/*dir = new File(System.getProperty("user.dir")+ "/src/test/resources/DataConfig/"+env);
			for (File file : dir.listFiles())
			{
				if (file.getName().endsWith((".properties")))
				{
					props.putAll(getProperties("/src/test/resources/DataConfig/" + env + "/"+ file.getName()));
					*//*props.putAll(getProperties( env + "/"+ file.getName()));*//*
				}
			}*/
        return props;
    }
}