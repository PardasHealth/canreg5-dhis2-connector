package ir.pardas.dhis2.connector;

import org.hisp.dhis.common.ValueType;

public class DictionaryElement {
    private String value;

    private String uid;
    private ValueType valueType;

    public DictionaryElement(String value) {
        this.value = value;
        uid = value.split(":")[0];
        valueType = ValueType.valueOf(value.split(":")[1]);
    }

    public String getUid() {
        return uid;
    }

    public ValueType getValueType() {
        return valueType;
    }
}
