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
//TODO
                trackedEntityInstance.setTrackedEntityInstance(null);//Dictionary.DIC_TrackedEntityInstance
//TODO
                trackedEntityInstance.setOrgUnit(null);//Dictionary.DIC_OrgUnit
//TODO
                trackedEntityInstance.setTrackedEntityType(null);//Dictionary.DIC_TrackedEntityType


                trackedEntityInstance.getAttributes().add(dictionary.createAttribute(Dictionary.DIC_AGE, record.get(Dictionary.DIC_AGE)));
                trackedEntityInstance.getAttributes().add(dictionary.createAttribute(Dictionary.DIC_FIRSTN, record.get(Dictionary.DIC_FIRSTN)));
                trackedEntityInstance.getAttributes().add(dictionary.createAttribute(Dictionary.DIC_FATHNAME, record.get(Dictionary.DIC_FATHNAME)));
                trackedEntityInstance.getAttributes().add(dictionary.createAttribute(Dictionary.DIC_AGE, record.get(Dictionary.DIC_AGE)));
                trackedEntityInstance.getAttributes().add(dictionary.createAttribute(Dictionary.DIC_AGE, record.get(Dictionary.DIC_AGE)));
                trackedEntityInstance.getAttributes().add(dictionary.createAttribute(Dictionary.DIC_AGE, record.get(Dictionary.DIC_AGE)));
                trackedEntityInstance.getAttributes().add(dictionary.createAttribute(Dictionary.DIC_AGE, record.get(Dictionary.DIC_AGE)));
                trackedEntityInstance.getAttributes().add(dictionary.createAttribute(Dictionary.DIC_AGE, record.get(Dictionary.DIC_AGE)));

                try {
                    TrackedEntityInstances connector = new TrackedEntityInstances();
                    String respose = connector.post(trackedEntityInstance);
                    System.out.println(respose);

/*
                    TrackedEntityInstancesQuery queryConnector = new TrackedEntityInstancesQuery();
                    respose = queryConnector.get("ouMode=ALL&filter=" + dictionary.getDictionaryElement(Dictionary.DIC_FIRSTNAME).getUid() + ":EQ:Mahdi");
                    System.out.println(respose);
*/

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
