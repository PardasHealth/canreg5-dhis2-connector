package ir.pardas.dhis2.connector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config extends Properties {
    private Map<String,DictionaryElement> elements = new HashMap<>();

    public  String propertyFile(){
        return "/dhis2.properties";
    }

    public synchronized void load() throws IOException {
        super.load(Dictionary.class.getResourceAsStream(propertyFile()));
    }


}
