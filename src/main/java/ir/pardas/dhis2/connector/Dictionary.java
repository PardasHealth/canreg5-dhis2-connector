package ir.pardas.dhis2.connector;

import org.hisp.dhis.dxf2.events.trackedentity.Attribute;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class Dictionary extends Config {

    public static final String DIC_TrackedEntityInstance = "TrackedEntityInstance";
    public static final String DIC_OrgUnit = "OrgUnit";
    public static final String DIC_TrackedEntityType= "TrackedEntityType";
    public static final String DIC_FIRSTNAME = "first-name";
    public static final String DIC_LASTNAME = "last-name";

    public String propertyFile() {
        return "/dictionary.properties";
    }

    public DictionaryElement getDictionaryElement(String attribute) {
        return new DictionaryElement(this.getProperty(attribute));
    }

    public Attribute createAttribute(String attribute, String value) {
        DictionaryElement element = getDictionaryElement(attribute);
        return new Attribute(element.getUid(), element.getValueType(), value);
    }


    public static String[] CSV_HEADER = new String[]{DIC_TrackedEntityInstance,DIC_OrgUnit,DIC_TrackedEntityType,DIC_FIRSTNAME, DIC_LASTNAME};
    public static String[] TrackedEntityAttributes = new String[]{DIC_FIRSTNAME, DIC_LASTNAME};
}
