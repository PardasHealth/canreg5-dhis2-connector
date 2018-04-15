package ir.pardas.dhis2.connector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigUtil {
    private static Map<String, Config> configs;

    ConfigUtil() {
        this.configs = configs;
    }

    public static Properties read(String path) {
        if (configs.containsKey(path))
            return configs.get(path);
        Config properties = new Config();
        try {
            properties.load(ConfigUtil.class.getResourceAsStream(path));
            configs.put(path, properties);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static {
        configs = new HashMap<>();
    }
}
