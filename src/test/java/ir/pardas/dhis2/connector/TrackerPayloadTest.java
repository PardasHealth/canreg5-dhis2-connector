package ir.pardas.dhis2.connector;

import com.eclipsesource.json.*;
import com.ibm.icu.text.SimpleDateFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class TrackerPayloadTest {

    Dictionary dictionary = new Dictionary();
    private static final Logger logger;

    static {
        InputStream config = TrackerPayloadTest.class.getResourceAsStream("/logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger = Logger.getLogger("TrackedEntity");
    }

    static class JallaliDateConverterDate implements DateValueConverter {

        @Override
        public String stringValue(Object value) throws ParseException {
            if (value == null) {
                return null;
            } else if (value instanceof Date) {
                SimpleDateFormat gDateformat = new SimpleDateFormat("yyyy-MM-dd", new Locale("en_US"));
                return gDateformat.format((Date) value);
            } else {
                SimpleDateFormat gDateformat = new SimpleDateFormat("yyyy-MM-dd", new Locale("en_US"));
                SimpleDateFormat jDateFormat = new SimpleDateFormat("yyyyMMdd", new Locale("fa_IR"));
                return gDateformat.format(jDateFormat.parse((String) value));
            }
        }
    }

    private JsonObject attributeValue(CSVRecord record, DateValueConverter converter, String attribute) throws ParseException {
        return new JsonObject()
                .add("attribute", dictionary.getDictionaryElement(attribute).getUid())
                .add("value", converter != null ? converter.stringValue(record.get(attribute)) : record.get(attribute));
    }

    private JsonObject attributeValue(CSVRecord record, String attribute) throws ParseException {
        return attributeValue(record, null, attribute);
    }

    @Test
    public void testJson() {
        try {
            String orgUni = "iq6Pt6QzM4d";
            String program = "LaWt5AR1tfl";
            dictionary.load();
            Path path = Paths.get(this.getClass().getResource("/breast1.csv").toURI());
            DateValueConverter dateConverter = new JallaliDateConverterDate();
            TrackedEntityInstances connector = new TrackedEntityInstances();

            CSVParser parser = CSVFile.read(path, Dictionary.CSV_HEADER);
            int recordNumber = 0;
            for (CSVRecord record : parser) {
                recordNumber++;
                //Skip 1st line
                if (recordNumber == 1)
                    continue;
                try {
                    JsonObject trackedEntity = new JsonObject()
                            .add("trackedEntityInstance", "IKjF4BZg3hb")
                            .add("orgUnit", orgUni)
                            .add("trackedEntityType", "MCPQUTHX1Ze")
                            .add("attributes", new JsonArray()
                                    .add(attributeValue(record, Dictionary.DIC_AGE))
                                    .add(attributeValue(record, Dictionary.DIC_FIRSTN))
                                    .add(attributeValue(record, Dictionary.DIC_FATHNAME))
                                    .add(attributeValue(record, Dictionary.DIC_FAMN))
                                    .add(attributeValue(record, Dictionary.DIC_NID))
                                    .add(attributeValue(record, Dictionary.DIC_NAtionality))
                                    .add(attributeValue(record, dateConverter, Dictionary.DIC_BIRTHD))
                                    .add(attributeValue(record, Dictionary.DIC_TELEPHONE))
                                    .add(attributeValue(record, Dictionary.DIC_MOBILEN))
                            )
                            .add("enrollments", new JsonArray()
                                    .add(new JsonObject()
                                            .add("orgUnit", orgUni)
                                            .add("program", program)
                                            .add("enrollmentDate", dateConverter.stringValue(record.get(Dictionary.DIC_INCID)))
                                            .add("incidentDate", dateConverter.stringValue(record.get(Dictionary.DIC_INCID)))
                                            .add("events", new JsonArray()
                                                    .add(new JsonObject()
                                                            .add("eventDate", "2013-09-17"))))
                            );

                    System.out.print(trackedEntity.toString());
                    String respose = connector.post(trackedEntity.toString());
                    JsonObject jsonObject = (JsonObject) Json.parse(respose);
                    System.out.print(jsonObject);

                } catch (Exception e) {
                    logger.severe("Record[" + recordNumber + "] message:" + e.getMessage());
                }
                break;
            }
        } catch (IOException e) {
            logger.severe("Error:" + e.getMessage());
        } catch (URISyntaxException e) {
            logger.severe("Error" + e.getMessage());
        }

    }
}
