package ir.pardas.dhis2.connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.hisp.dhis.dxf2.events.trackedentity.TrackedEntityInstance;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TrackerPayloadTest {
    @Test
    public void testJson() {
        Dictionary dictionary = new Dictionary();
        try {
            dictionary.load();
            Path path = Paths.get(this.getClass().getResource("/demographic.csv").toURI());

            CSVParser parser = CSVFile.read(path, Dictionary.CSV_HEADER);

            int i = 0;
            for (CSVRecord record : parser) {
                i++;
                //Skip 1st line
                if(i==1)
                    continue;
                TrackedEntityInstance trackedEntityInstance = new TrackedEntityInstance();

                trackedEntityInstance.setTrackedEntityInstance(record.get(Dictionary.DIC_TrackedEntityInstance));
                trackedEntityInstance.setOrgUnit(record.get(Dictionary.DIC_OrgUnit));
                trackedEntityInstance.setTrackedEntityType(record.get(Dictionary.DIC_TrackedEntityType));

                trackedEntityInstance.getAttributes().add(dictionary.createAttribute(Dictionary.attribute, record.get(attribute)));

                try {
                    TrackedEntityInstances connector = new TrackedEntityInstances();
                    String respose = connector.post(trackedEntityInstance);
                    System.out.println(respose);

                    TrackedEntityInstancesQuery queryConnector = new TrackedEntityInstancesQuery();
                    respose = queryConnector.get("ouMode=ALL&filter=" + dictionary.getDictionaryElement(Dictionary.DIC_FIRSTNAME).getUid() + ":EQ:Mahdi");
                    System.out.println(respose);

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
