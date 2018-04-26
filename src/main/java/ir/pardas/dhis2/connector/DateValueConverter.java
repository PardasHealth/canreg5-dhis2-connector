package ir.pardas.dhis2.connector;

import java.text.ParseException;

public interface DateValueConverter {
    String stringValue(Object Value) throws ParseException;
}
