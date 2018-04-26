package ir.pardas.dhis2.connector;

//import org.hisp.dhis.common.ValueType;

public class DictionaryElement {
    private String value;

    private String uid;
//    private ValueType valueType;
    private String valueType;

    public DictionaryElement(String value) {
        this.value = value;
        this.uid = value;
//        uid = value.split(":")[0];
        valueType = "TEXT";//.valueOf(value.split(":")[1]);
    }

    public String getUid() {
        return uid;
    }

    public String getValueType() {
        return valueType;
    }
}
